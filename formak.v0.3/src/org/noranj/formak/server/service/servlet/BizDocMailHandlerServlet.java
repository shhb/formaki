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
import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.core.MailMessage;
import org.noranj.formak.server.domain.core.PendingDocument;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.utils.MailHelper;
import org.noranj.formak.server.utils.QueueHelper;
import org.noranj.formak.server.utils.Utils;
import org.noranj.formak.shared.GlobalSettings;
import org.noranj.formak.shared.type.DocumentType;

/**

 * All the emails sent to this servlet will be processed and the attachment(s) or body is used to build a business document.
 * 
 * NOTE: the sender is the originator and receiver of document. Other cases must be implemented with another handler or email address.
 * 
 * It checks MIME-TYPE of attachment(s) and based on the type of attachment, it decides what to do with it.
 * If the type is a known type, it stores the attachment(s) in blobstore and sends the blobkey to the corresponding queue to be processed.
 * It sends an email notification back to sender and confirms the receive of the email.
 * It also prints the blobstore key in that email for later tracking.
 * THE QUESTION IS "do we need to record the keys in data store to make sure we are not losing anything?"
 * A: They can be stored in <b>PendingDocument</b> but they must be deleted when we are done with them.
 * It is good because both user and system admin have access to the table and are aware of the number of documents (attachments) 
 * received and pending. As soon as the document is processed, it is removed from PendingDocument and stored in its
 * corresponding datastore. Note the attachments are stored in blobstore and only their key stored in PendingDocumnet.
 * Also, the objects in PendingDocument are stored in each user's specific namesapce. So the key and user's namespace (clientID) must be
 * passed to the processing queue so the queue handler can find the PendingDocument object and from there it can find the attachment.
 *   
 * 
 * NOTE: For final version, the QUQUE must be PULL queue because we have not that much control over the process time of attachments.
 * They are assumed to be batch and they are being processed in sequence.
 * 
 * The attachments can be of one of these types 
 * <li>PALIN/TEXT
 * <li>plain/html
 * <li>plain/xml
 * <li>image/jpeg
 * PDF, PNG ????
 *  
 * Assumption:</br>
 * <ol>
 * <li>The sender is the originator of the document.
 * <li>The sender is the receiver of the document. (other cases must be reviewed. To allow any one sends document with email, they must be registered as the users' TP.)
 * <li>Sender must be an authorized user, using their registered email address. 
 * <li>For sake of simplicity, in the first version, we assume the business documents are attached to the email.
 * Later, we will try to parse the body and look for business document data.
 * <li>Each email carries only one attachment at a time
 * <li>FAX goes to another handler that uses OCR although FAX comes as PDF file. 2012-05-11 not sure about this. 
 * 
 * <p>
 * <b>Algorithm</b>
 * <ol>
 * <li>get the mail message
 * <li>authenticates the sender email address (using the first address in From)
 * <li>check the subject of the email to be a specific phrase word such as PurchaseOrder or Other or Invoice ...
 * <li>gets the attachment(s)
 * <li>check the size of attachment to not exceed the limit (i.e. 100 KB or 10 MB)
 * <li>store the attachment in blobstore
 * <li>save the blobstore key, document Type, MIME-TYPE in PendingDocument data store (use systemUser.parentClientID to store it in correct namespace)
 * <li>put the PendingDocument key, SystemUser (at least parentClientID to find namesapce) in a queue that matches document type and MIMETYPE. For example PO and Text goes to one Queue when PO and PDF goes to another queue.
 * <li>send a received confirmation/rejection email to sender with PendingDocument key and any other information that might be needed.
 * </ol>
 * 
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
      	//XXX Just to test the system. It should be later removed. It is extra work and could slow down the whole system.
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
	      	
	        
	        PendingDocument pendingDocument = null;
	        for (Attachment attached : mail.getAttachments()) {
  	        //-/Add BizDocument to data store
  	        pendingDocument = BusinessDocumentHelper.storeAttachedDocument(docType, systemUser /*originator*/, systemUser /*receiver*/, attached);
  	        
  	        //-/Put at least the pendingDocumentID and systemUser.parentClientID in the queue to be processed later.
            QueueHelper.processDocument(docType, pendingDocument, systemUser);
  	        
  	        
  	        //XXX here - APR-19 working on adding the document ID and document Type and MIMETYPE in another QUEUE for further processes.
  	        // the question is if we have more than one attachments which process is going to look at them and find the business document.
  	        // attachments could be any type and hard to say which one is business document. However, the PDF files could be considered as business document always.
  	        // should BusinessDocumentHelper does the filtering? or MailHelper does that!>?!>!>!>
  	        
  	        //-/ send confirmation email
  	        QueueHelper.sendMailNotification(mail.getFrom(), "Received ["+docType+"]", "The document is saved and pending to be processed with this ID ["+pendingDocument.getId()+"]."); //FIXME

	        }
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