package org.noranj.formak.server.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.20120321
 * @version 0.3.20120321
 * @change
 *
 */
public class MailMessage implements Serializable {
	
  private static final long serialVersionUID = -2525988726950171264L;
  
	private String fromAddress;
	private String[] recepientAddresses;
	private String[] cc;
	private String[] bcc; //??

	private String subject;
	
	private Attachment body;
	private List<Attachment> attachments;
	
	public MailMessage() {
		body = null;
		attachments = new ArrayList<Attachment>();
	}
	
	public String getFromAddress() {
  	return fromAddress;
  }
	public void setFromAddress(String from) {
  	this.fromAddress = from;
  }
	public String[] getRecepientAddresses() {
  	return recepientAddresses;
  }
	public void setRecepientAddresses(String[] recepients) {
  	this.recepientAddresses = recepients;
  }
	public String[] getCc() {
  	return cc;
  }
	public void setCc(String[] cc) {
  	this.cc = cc;
  }
	public String[] getBcc() {
  	return bcc;
  }
	public void setBcc(String[] bcc) {
  	this.bcc = bcc;
  }
	public String getSubject() {
  	return subject;
  }
	public void setSubject(String subject) {
  	this.subject = subject;
  }
	public Attachment getBody() {
  	return body;
  }
	public void setBody(Attachment body) {
  	this.body = body;
  }
	public List<Attachment> getAttachments() {
  	return attachments;
  }
	public void addAttachment(Attachment attachment) {
  	this.attachments.add(attachment);
  }

	/** returns a everything except
	 * - full body (it truncates the body if exceeds 256 chars) 
	 * - the attachments 
	 */
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		str.append("subject[");
		str.append(getSubject());
		str.append("]");
		str.append("from[");
		str.append(getFromAddress());
		str.append("");
		str.append("numberOfAttchments[");
		str.append(getAttachments().size());
		str.append("]");
		
		return(str.toString());
		
	}
}
