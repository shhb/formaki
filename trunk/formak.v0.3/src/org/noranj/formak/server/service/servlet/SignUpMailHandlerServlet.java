package org.noranj.formak.server.service.servlet;

import java.io.IOException; 
import java.util.Properties; 
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session; 
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

import org.noranj.formak.server.DAL1ToNHelper;
import org.noranj.formak.server.DALHelper;
import org.noranj.formak.server.SystemAdminHelper;
import org.noranj.formak.server.domain.core.MailMessage;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.service.JDOPMFactory;
import org.noranj.formak.server.service.SystemAdminServiceImpl;
import org.noranj.formak.server.utils.MailHelper;
import org.noranj.formak.server.utils.QueueHelper;
import org.noranj.formak.server.utils.Utils;
import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.GlobalSettings;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.type.ActivityType;

import com.google.appengine.api.NamespaceManager;

/**
 * All the emails sent to this servlet will be processed and the attachment or body is used to build a business document.
 *  * 
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
 * @since 0.3.20120320
 * @version 0.3.20120330
 * @change
 *
 */
@SuppressWarnings("serial")
public class SignUpMailHandlerServlet extends HttpServlet { 
    
  protected static Logger logger = Logger.getLogger(SignUpMailHandlerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
		try {
     
			logger.warning("Processing Signup Mail");
			Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null); 
      MimeMessage message = new MimeMessage(session, req.getInputStream());
      
      //TODO parse the message
      MailHelper mailhelper = new MailHelper();
      MailMessage mail = mailhelper.getMailMessage(message);

      try {
      	
	      if (mail.getSubject().equalsIgnoreCase("signup")) {
	        logger.info("A 'signup' email is received from email["+mail.getFrom()+"]");
	      	String userId = SystemAdminHelper.signupUser(mail);
	      	//XXX here
	      	QueueHelper.sendMailNotification(mail.getFrom(), "Congradulation and Welcome to Formak.", "The user is added successfully.User Id["+userId+"]"); //FIXME
	      } 
	      else {
	      	//errMsg = "subject must be one word without any quotes: signup";
		      logger.warning("A WRONG email is received from email["+mail.getFrom()+"]. The subject is ["+mail.getSubject()+"]");
	      	QueueHelper.sendMailNotification(mail.getFrom(), "Signup Failed - WRONG Subject", "Hi Daer Sir/Madam, If you are trying to sign up for Formak, you should correct the subject. It must be 'signup' as one word only without quotes."); //FIXME
	      }
	      
	      //if (errMsg!=null) {
	      	//TODO reply to sender with a notification email using a slow queue so they know the subject was wrong.
	      //}
      } catch (Exception ex){
      	logger.severe(ex.getMessage() + "trace is ["+Utils.stackTraceToString(ex)+"]");
      	QueueHelper.sendMailNotification(mail.getFrom(), "Signup Failed - An unexpected error", "Hi Daer Sir/Madam, signup process failed. Sorry for any inconvenient. We will find the problem and will fix it in 24 hours. If have not received any email from us, please, contact system adminitrator at 'sysadmin@noranj.com'."); //FIXME
    		QueueHelper.sendMailNotification(new InternetAddress(GlobalSettings.C_SYSADMIN_MAIL_ADDRESS, GlobalSettings.C_SYSADMIN_MAIL_PERSONAL), "Signup Failed - An unexpected error", "from["+mail.getFrom()+"] subject["+mail.getSubject()+"]body["+mail.getBody().getContent()+"]"); //FIXME
      }
      
		//} catch (MessagingException msgex){
		//	logger.severe("A ["+msgex.getClass()+"] happened ["+msgex.getMessage()+"]. The stack is ["+ Utils.stackTraceToString(msgex) +"]");
		} catch (Exception ex) {
			logger.severe("A ["+ex.getClass()+"] happened ["+ex.getMessage()+"]. The stack is ["+ Utils.stackTraceToString(ex) +"]");
		}
        
	}


}