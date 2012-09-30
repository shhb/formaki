package org.noranj.idnt.server.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.core.server.domain.MailMessage;
import org.noranj.core.server.utils.MailHelper;
import org.noranj.core.server.utils.QueueHelper;
import org.noranj.core.server.utils.Utils;
import org.noranj.core.shared.Constants;
import org.noranj.idnt.server.SystemAdminHelper;
import org.noranj.tax.v2012.shared.GlobalSettings;

/**
 * All the emails sent to this servlet will be processed and the attachment or body is used to build a business document.
 *  
 * For sake of simplicity, in the first version, we assume the business documents are attached to the email.
 * Later, we will try to parse the body and look for business document data.
 * 
 * This servlet 
 * - authenticates the sender email address
 * - gets the attachments
 * - puts the attachment in a queue to be processed by another servlet.
 * - sends a received confirmation/rejection email to sender (optional)
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.20120810
 * @version 0.3.20120810
 * @change
 *	BA:2012-08-10 Commented doPost
 */
@SuppressWarnings("serial")
public class SignUpMailHandlerServlet extends HttpServlet { 
    
  protected static Logger logger = Logger.getLogger(SignUpMailHandlerServlet.class.getName());
	
  /**
   * This function processes the received email as described in the following steps:
   * 
   * <ol>
   * <li>get message
   * <li>verify the subject is signup
   * <li>sign up user by passing the body of email to signup() method.
   * <li>if successfully signed up, send confirmation to user
   * <li> send alert notification to user and sysadmin.
   * </ol>
   * 
   */
	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
		try {
     
			logger.warning("Processing Signup Mail");
			Properties props = new Properties();
      
			//1- get message using the Mail Session
			Session session = Session.getDefaultInstance(props, null); 
      MimeMessage message = new MimeMessage(session, req.getInputStream());
      
      //TODO parse the message
      MailHelper mailhelper = new MailHelper();
      MailMessage mail = mailhelper.getMailMessage(message);

      try {
      	
      	//2- verify the subject is signup
	      if (mail.getSubject().equalsIgnoreCase(Constants.C_SIGNUP_MAIL_SUBJECT)) {
	        logger.info("A 'signup' email is received from email["+mail.getFrom()+"]");
	      	
	        // 3- sign up the user
	        String userId = SystemAdminHelper.signupUser(mail);
	      	
	        // 4- send confirmation email
	        QueueHelper.sendMailNotification(mail.getFrom(), "Welcome to TAXLET.", "Your account is created successfully.\r\nYou can login to TAXLET using your Email Account provider login page.\r\nThe unique ID assigned to your account is ["+userId+"]."); //FIXME
	      } 
	      else {
		      logger.warning("A WRONG email is received from email["+mail.getFrom()+"]. The subject is ["+mail.getSubject()+"]");
	      	QueueHelper.sendMailNotification(mail.getFrom(), "Signup Failed - WRONG Subject", "Dear Sir/Madam,\r\nIf you are trying to sign up for Formak, you should correct the subject.\r\nIt must be 'signup' as one word only without quotes."); //FIXME
	      }
	      
      } catch (Exception ex){
      	logger.severe(ex.getMessage() + "trace is ["+Utils.stackTraceToString(ex)+"]");
      	QueueHelper.sendMailNotification(mail.getFrom(), "Signup Failed - An unexpected error", "Dear Sir/Madam, signup process failed. Sorry for any inconvenient. We will find the problem and will fix it in 24 hours.\r\nIf have not received any email from us in the next 24 hours, please, contact system adminitrator at 'sysadmin@noranj.com'."); //FIXME
    		QueueHelper.sendMailNotification(new InternetAddress(GlobalSettings.C_SYSADMIN_MAIL_ADDRESS, GlobalSettings.C_SYSADMIN_MAIL_PERSONAL), "Signup Failed - An unexpected error", "from["+mail.getFrom()+"] subject["+mail.getSubject()+"]body["+mail.getBody().getContent()+"]"); //FIXME
      }
      
		//} catch (MessagingException msgex){
		//	logger.severe("A ["+msgex.getClass()+"] happened ["+msgex.getMessage()+"]. The stack is ["+ Utils.stackTraceToString(msgex) +"]");
		} catch (Exception ex) {
			logger.severe("A ["+ex.getClass()+"] happened ["+ex.getMessage()+"]. The stack is ["+ Utils.stackTraceToString(ex) +"]");
		}
        
	}


}