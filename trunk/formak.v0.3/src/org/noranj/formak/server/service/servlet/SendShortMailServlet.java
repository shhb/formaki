package org.noranj.formak.server.service.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
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
public class SendShortMailServlet extends HttpServlet { 

  protected static Logger logger = Logger.getLogger(SendShortMailServlet.class.getName());
	
  /**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
     
			logger.info("Processing send short mails");
			Properties props = new Properties();
      Session mailSession = Session.getDefaultInstance(props, null);
      String msgBody = req.getParameter(Constants.C_MAIL_BODY_PROP_NAME);

      try {

      	if (logger.isLoggable(Level.FINE))
      		logger.fine("preparing the message.");

        Message msg = new MimeMessage(mailSession);

      	if (logger.isLoggable(Level.FINE))
      		logger.fine("created MimeMessage. Adding from["+new InternetAddress(GlobalSettings.C_MAIL_FROM_ADDRESS, GlobalSettings.C_MAIL_FROM_PERSONAL)+"]");
       
      	msg.setFrom(new InternetAddress(GlobalSettings.C_MAIL_FROM_ADDRESS, GlobalSettings.C_MAIL_FROM_PERSONAL));

      	if (logger.isLoggable(Level.FINE))
      		logger.fine("adding recepients - TO["+new InternetAddress(req.getParameter(Constants.C_MAIL_TO_ADDRESS_PROP_NAME), req.getParameter(Constants.C_MAIL_TO_PERSONAL_PROP_NAME))+"]");
      	
      	msg.addRecipient(Message.RecipientType.TO,
                         new InternetAddress(req.getParameter(Constants.C_MAIL_TO_ADDRESS_PROP_NAME), req.getParameter(Constants.C_MAIL_TO_PERSONAL_PROP_NAME)));
      
      	if (logger.isLoggable(Level.FINE))
      		logger.fine("adding subject["+req.getParameter(Constants.C_MAIL_SUBJECT_PROP_NAME)+"]");
      	msg.setSubject(req.getParameter(Constants.C_MAIL_SUBJECT_PROP_NAME)); //"Your Example.com account has been activated");

      	if (logger.isLoggable(Level.FINE))
      		logger.fine("adding msgBody["+msgBody+"]");
      	msg.setText(msgBody);
        
      	if (logger.isLoggable(Level.FINE))
      		logger.fine("message is ready to be sent. from["+msg.getFrom()+"] to["+msg.getAllRecipients()+"] subject["+msg.getSubject()+"]");
      	
        Transport.send(msg);

      	if (logger.isLoggable(Level.FINE))
      		logger.fine("message is sent without any exception");
        
      } catch (AddressException e) {
      	logger.severe("Failed to send mail because of an AddressException["+e.getMessage()+"] [" + Utils.stackTraceToString(e)+"]");
      } catch (MessagingException e) {
      	logger.severe("Failed to send mail because of an MessageException["+e.getMessage()+"] [" + Utils.stackTraceToString(e)+"]");
      }

	} // doPost
	
}
