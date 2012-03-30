package org.noranj.formak.server.service.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.formak.server.utils.MailHelper;
import org.noranj.formak.server.utils.Utils;
import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.GlobalSettings;


/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.20120329
 * @version 0.3.20120329
 * @change
 *
 */
public class SendShortMailServlet {

  protected static Logger logger = Logger.getLogger(SignUpMailHandlerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
     
			logger.warning("Processing Signup Mail");
			Properties props = new Properties();
      Session mailSession = Session.getDefaultInstance(props, null);
      String msgBody = req.getParameter(Constants.C_MAIL_BODY_PROP_NAME);

      try {
      	
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(GlobalSettings.C_FROM_ADDRESS, GlobalSettings.C_FROM_ADDRESS_DISPLAY_NAME));
        msg.addRecipient(Message.RecipientType.TO,
                         new InternetAddress(req.getParameter(Constants.C_MAIL_TO_ADDRESS_PROP_NAME)/*"babak@noranj.com"*/, req.getParameter(Constants.C_MAIL_TO_DISPLAY_NAME_PROP_NAME)/*"Mr. Babak"*/));
        msg.setSubject(req.getParameter(Constants.C_MAIL_SUBJECT_PROP_NAME)); //"Your Example.com account has been activated");
        msg.setText(msgBody);
        Transport.send(msg);

      } catch (AddressException e) {
      	logger.severe("Failed to send mail because of an AddressException["+e.getMessage()+"] [" + Utils.stackTraceToString(e)+"]");
      } catch (MessagingException e) {
      	logger.severe("Failed to send mail because of an MessageException["+e.getMessage()+"] [" + Utils.stackTraceToString(e)+"]");
      }

	} // doPost
	
}
