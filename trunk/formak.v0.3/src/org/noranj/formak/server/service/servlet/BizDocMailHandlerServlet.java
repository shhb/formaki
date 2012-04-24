package org.noranj.formak.server.service.servlet;


import java.io.IOException; 
import java.util.Properties; 
import java.util.logging.Logger;

import javax.mail.Session; 
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 
import javax.servlet.http.*; 

import org.noranj.formak.server.BusinessDocumentHelper;
import org.noranj.formak.server.SystemAdminHelper;
import org.noranj.formak.server.domain.core.MailMessage;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.utils.MailHelper;
import org.noranj.formak.server.utils.QueueHelper;
import org.noranj.formak.server.utils.Utils;
import org.noranj.formak.shared.GlobalSettings;
import org.noranj.formak.shared.type.DocumentType;

/**
 * All the emails sent to this servlet will be processed and the attachment or body is used to build a business document.
 * 
 * 
 * Assumption:</br>
 * <ol>
 * <li>Sender must be an authorized user, using their registered email address. 
 * <li>For sake of simplicity, in the first version, we assume the business documents are attached to the email.
 * Later, we will try to parse the body and look for business document data.
 * <li>Each email carries only one attachment at a time
 * <li>FAX goes to another handler that uses OCR although FAX comes as PDF file.
 * 
 * <p>
 * <b>Algorithm</b>
 * <ol>
 * <li>get the mail message
 * <li>authenticates the sender email address (using the first address in From)
 * <li>check the subject of the email to be a specific phrase word such as PurchaseOrder or Other or Invoice ...
 * <li>gets the attachment(s)
 * <li> check the size of document to not exceed the limit (i.e. 100 KB)
 * <li>store the document as attachment in its own type of data store (i.e. PO goes to PO, ...) and use the ID to track it.
 * <li>put the document ID, document Type and MIMETYPE in a queue that matches document type and MIMETYPE. For example PO and Text goes to one Queue when PO and PDF goes to another queue.
 * <li>sends a received confirmation/rejection email to sender with document ID and any other information that might be needed.
 * </ol>
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.2012MAR20
 * @version 0.3.2012APR19
 * @change
 *
 */
@SuppressWarnings("serial")
public class BizDocMailHandlerServlet extends HttpServlet { 
    
  protected static Logger logger = Logger.getLogger(BizDocMailHandlerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp) throws IOException { 
		try {
	     
			logger.warning("Processing received BizDoc Mail");
			Properties props = new Properties();
      
			//-/ Get the mail message using the Mail Session
			Session session = Session.getDefaultInstance(props, null); 
      MimeMessage message = new MimeMessage(session, req.getInputStream());
      
      //-/ Authenticate Sender
      assert(message.getFrom()[0]!=null);
      // Assuming the first address in the From is the actual email address of user.
      SystemUser systemUser = SystemAdminHelper.getSystemUser(MailHelper.convertAddressesToInternetAddresses(message.getFrom())[0]);
      
      if(systemUser == null) {
      	
      	/** It is important to make sure this block is doing less amount of work needed. we may get tons of spams and we don't let that slow down the system.
      	 * there might be some codes here for test but later they must be removed. */
      	
      	//FIXME maybe later we will not dump the email and send them through another process to see if it can be used. A slower queue perhaps. we don't want to lose the emails.
      	logger.warning("Email address is not registered. The email is dumped.");
      	
      	//FIXME better message body for the email 
      	InternetAddress internetAddress = MailHelper.convertAddressesToInternetAddresses(message.getFrom())[0];
      	QueueHelper.sendMailNotification(internetAddress, "Unregistered Email - Formak", "Dear Sir/Madam, We are sorry but we do not recognize your email address.\r\nIf you are a user of Formak system please, use your registered email to send your documents. If you are not a user, it takes only few minutes to sign up via email or web.\r\nHOW TO SIGN UP\r\nYou can just send an email to signup@.... with subject signup to be a user.\r\n"); //FIXME

      	//[TEMP]
      	//XXX Just to test the system. It should be latter removed. It is extra work and could slow down the whole system.
      	// we may get tons of spams every minute and must filter them quickly.
        MailMessage mail = MailHelper.getMailMessage(message);
      	QueueHelper.sendMailNotification(new InternetAddress(GlobalSettings.C_SYSADMIN_MAIL_ADDRESS, GlobalSettings.C_SYSADMIN_MAIL_PERSONAL), "Unregistered Email Address ", "from["+internetAddress.getAddress()+"] subject["+mail.getSubject()+"]body["+mail.getBody().getContent()+"]"); //FIXME

      }
      
      //Parse the message and convert it to an easier structure which is Noranj's MailMessage
      MailMessage mail = MailHelper.getMailMessage(message);

      try {
      	
        //-/ verify the subject is bizDocument type
      	DocumentType docType = DocumentType.fromString(mail.getSubject().trim());
	      if (docType != DocumentType.Unknown) {
	        logger.info("A 'BizDocument' of type ["+docType+"] is received from email["+mail.getFrom()+"]");
	      	
	        //-/Add BizDocument to data store
	        // I am not sure if it is a good idea to pass it to a queue to be stored. There is a lot of data to be passed. 
	        String documentID = BusinessDocumentHelper.storeAttachedDocuments(docType, systemUser, mail.getAttachments());
	        
	        //XXX here - APR-19 working on adding the document ID and document Type and MIMETYPE in another QUEUE for further processes.
	        // the question is if we have more than one attachments which process is going to look at them and find the business document.
	        // attachments could be any type and hard to say which one is business document. However, the PDF files could be considered as business document always.
	        // should BusinessDocumentHelper does the filtering? or MailHelper does that!>?!>!>!>
	        //QueueHelper.processDocument(docType, documentID, attachmentMIMEType);
	        
	        //-/ send confirmation email
	        QueueHelper.sendMailNotification(mail.getFrom(), "Received ["+docType+"]", "The document is saved with this ID ["+documentID+"]."); //FIXME
	      } 
	      else {
		      logger.warning("An email with WRONG subject is received from ["+mail.getFrom()+"]. The subject is ["+mail.getSubject()+"]. The email is ignored.");
	      	QueueHelper.sendMailNotification(mail.getFrom(), "Business Document Mail Failed - WRONG Subject", "Dear Sir/Madam,\r\nWe recieved a document with an unknown subject.\r\nThe email will not be stored."); //FIXME
	      }

	      ///XXX HERE modifying the log messages and emails
	      /// the next step is to save the documents.
	      
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