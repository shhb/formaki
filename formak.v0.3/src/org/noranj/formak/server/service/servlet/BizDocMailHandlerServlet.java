package org.noranj.formak.server.service.servlet;

import java.io.IOException; 
import java.util.Properties; 
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session; 
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

import org.noranj.formak.server.domain.core.MailMessage;
import org.noranj.formak.server.utils.MailHelper;
import org.noranj.formak.server.utils.Utils;

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
 * @since 0.3.2012MAR20
 * @version 0.3.2012MAR20
 * @change
 *
 */
@SuppressWarnings("serial")
public class BizDocMailHandlerServlet extends HttpServlet { 
    
  protected static Logger logger = Logger.getLogger(BizDocMailHandlerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
		try {
			
			logger.warning("Processing BizDoc Mail");

			Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null); 
      MimeMessage message = new MimeMessage(session, req.getInputStream());
      
      //TODO parse the message
      MailHelper mailhelper = new MailHelper();
      
      MailMessage mail = mailhelper.getMailMessage(message);
      
      ///TODO Add the message to a queue so another task will save the content to the data store. 
      
      logger.info("received an email - ["+mail.toString()+"]");
      
		//} catch (MessagingException msgex){
		//	logger.severe("A ["+msgex.getClass()+"] happened ["+msgex.getMessage()+"]. The stack is ["+ Utils.stackTraceToString(msgex) +"]");
		} catch (Exception ex) {
			logger.severe("A ["+ex.getClass()+"] happened ["+ex.getMessage()+"]. The stack is ["+ Utils.stackTraceToString(ex) +"]");
		}
        
	}
	
}