package org.noranj.formak.server.service.servlet;

import java.io.IOException; 
import java.util.Properties; 
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session; 
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

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