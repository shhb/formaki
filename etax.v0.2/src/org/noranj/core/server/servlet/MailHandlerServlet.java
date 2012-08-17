package org.noranj.core.server.servlet;

import java.io.IOException; 
import java.util.Properties; 
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session; 
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

/**
 * This is a general MailHandler all the emails sent to the application are processed here.
 * 
 * Right now we do not process any general email and they all are dumped.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3
 * @version 0.3
 * @change
 *
 */
@SuppressWarnings("serial")
public class MailHandlerServlet extends HttpServlet { 
  
  protected static Logger logger = Logger.getLogger(MailHandlerServlet.class.getName());

	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
		try {
     
			Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null); 
      MimeMessage message = new MimeMessage(session, req.getInputStream());
      
      //TODO parse the message
      
		} catch (MessagingException msgex){
			//do something
		}
        
	}
	
}