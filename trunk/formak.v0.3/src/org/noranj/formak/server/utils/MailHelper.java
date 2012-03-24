package org.noranj.formak.server.utils;

import java.io.InputStream;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.core.MailMessage;
import org.noranj.formak.shared.type.MIMEType;

/**
 * This class gets a MIMEMEssage and then put it in a simpler data structure named MailMessage. 
 * 
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * The code is based on comments at this link
 * http://www.coderanch.com/t/449220/java
 * /java/Getting-Multiple-Attachments-MIMEMessage
 * 
 * @author
 * @since 0.3.2012MAR21
 * @version 0.3.2012MAR21
 * @change
 * 
 */
public class MailHelper {

	private static Logger logger = Logger.getLogger(MailHelper.class.getName());

	
	/**
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static MailMessage getMailMessage(Message message) throws Exception  {
		
		MailMessage mailMessage = new MailMessage();
		mailMessage.setSubject(message.getSubject());
		Address[] addresses = message.getFrom();
		mailMessage.setFromAddress(addresses[0].toString()); // assuming the first one is the sender!!
		//TODO add the rest of information later
		
		mailMessage = handleMessage(message, mailMessage);
		return(mailMessage);
  }

	/**
	 * 
	 * @param message
	 * @param mailMessage
	 * @return
	 * @throws Exception
	 */
	private static MailMessage handleMessage(Message message, MailMessage mailMessage) throws Exception {
		
		Object content = message.getContent();

		if (content instanceof String) {
			logger.info("content is a String");
			// handle string
			if (mailMessage.getBody() == null) {
				logger.info("Found the BODY");
				mailMessage.setBody (new Attachment(((String) content).getBytes(), "body", MIMEType.TXT));
				logger.fine("body ["+(String) content+"]");
			}
			
		} else if (content instanceof Multipart) {
			logger.info("content is a multipart message");
			Multipart mp = (Multipart) content;
			mailMessage = handleMultipart(mp, mailMessage);
		}
		return(mailMessage);
		
	}

	/**
	 * It is called when the email is a multipart email.
	 * The class uses the first part as BODY of the email if
	 * there has been no BODY detected yet.
	 * 
	 * All parts are stored as attachment.
	 * 
	 * @param mp
	 * @param mailMessage
	 * @return
	 * @throws Exception
	 */
	private static MailMessage handleMultipart(Multipart mp, MailMessage mailMessage) throws Exception {
		int count = mp.getCount();

		int i = 0;
		if (count > i && (mailMessage.getBody() == null)) {
			logger.info("Found the BODY");
			BodyPart bp = mp.getBodyPart(i);
			mailMessage.setBody (new Attachment(((String) bp.getContent()).getBytes(), "body", MIMEType.valueOfWithEncoding(bp.getContentType())));
			logger.fine("body ["+(String) bp.getContent()+"]");
			++i;
		}
		
		for (; i < count; i++) {
			
			BodyPart bp = mp.getBodyPart(i);
			Object content = bp.getContent();

			if (bp.getDisposition() == Part.ATTACHMENT) {
				
				if (content instanceof String) {
					logger.info("\tcontent is ATTACHMENT-STRING - part ["+i+"] is ["+(String) content+"]");
					mailMessage.addAttachment(new Attachment(((String)content).getBytes(), bp.getFileName(), MIMEType.valueOfWithEncoding(bp.getContentType())));
				} 
				else if (content instanceof InputStream) {
					// handle input stream
					mailMessage.addAttachment(new Attachment(Utils.readAll((InputStream)content), bp.getFileName(), MIMEType.valueOfWithEncoding(bp.getContentType())));
					logger.info("\tcontent is ATTACHMENT-InputStream - part ["+i+"] is ["+String.valueOf(Utils.readAll((InputStream)content))+"]");
				} 
				else if (content instanceof Message) {
					Message message = (Message) content;
					mailMessage = handleMessage(message, mailMessage);
				} 
				else if (content instanceof Multipart) {
					Multipart mp2 = (Multipart) content;
					mailMessage = handleMultipart(mp2, mailMessage);
				}
				
			}
			else {
				logger.warning("part ["+i+"] is INLINE - ["+(String) bp.getContent()+"]");
				mailMessage.addAttachment(new Attachment(((String)content).getBytes(), (bp.getFileName()+"-INLINE-" + i), MIMEType.valueOfWithEncoding(bp.getContentType())));
				logger.info("\tcontent is String - part ["+i+"] is ["+(String) content+"]");
			}
		}
		return(mailMessage);
	}
}
