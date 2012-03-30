package org.noranj.formak.server.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.type.MIMEType;

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
 *	BA:2012-03-29 changed the addresses from String to InternetAddress.
 */
public class MailMessage implements Serializable {
	
  private static final long serialVersionUID = -2525988726950171264L;
  
  private InternetAddress from;
	private InternetAddress[] to;
	private InternetAddress[] cc;
	private InternetAddress[] bcc; //??

	private String subject;
	
	private Attachment body;
	private List<Attachment> attachments;
	
	/**
	 * 
	 */
	public MailMessage() {
		body = null;
		attachments = new ArrayList<Attachment>();
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @param body
	 */
	public MailMessage(InternetAddress from, InternetAddress[] to, byte[] body) {
		this.body = new Attachment(body, Constants.C_MAIL_BODY_PROP_NAME, MIMEType.TXT);
		this.from = from;
		this.to = to;
		this.attachments = new ArrayList<Attachment>();
	}
	
	public String getFromStr() {
  	return from.toString(); //TODO do we need to worry about encoding>?!>!>
  }
	public InternetAddress getFrom() {
  	return this.from;
  }
	public void setFrom (String fromStr) throws Exception {
		this.from = null;
		InternetAddress[] addresses = InternetAddress.parse(fromStr);
		if ((addresses !=null) & (addresses.length>0)) {
			this.from = addresses[0];
		}
  }
	public void setFrom (InternetAddress from) {
  	this.from = from;
  }
	public void setFrom (Address[] fromArray) throws Exception {
		this.from = null;
		if (fromArray!=null)
			setFrom(InternetAddress.toString(fromArray));
  }
	public String getToStr() {
  	return InternetAddress.toString(to);
  }
	public InternetAddress[] getTo() {
  	return to;
  }
	public void setTo(InternetAddress[] recepients) {
  	this.to = recepients;
  }
	/*
	public void addTo(InternetAddress recepients) {
  	if (this.to==null) {
  		this.to = new InternetAddress[GlobalSettings.C_MAX_NUMBER_OF_RECIPIENTS];
  	}
		this.to;
  }
  */
	public InternetAddress[] getCc() {
  	return cc;
  }
	public void setCc(InternetAddress[] cc) {
  	this.cc = cc;
  }
	public InternetAddress[] getBcc() {
  	return bcc;
  }
	public void setBcc(InternetAddress[] bcc) {
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
		
		return(toShortString());
		
	}
	
	/**
	 * 
	 * @return
	 */
	public String toShortString() {
		StringBuilder str = new StringBuilder();
		str.append("subject[");
		str.append(getSubject());
		str.append("] from[");
		str.append(getFromStr());
		str.append("] to[");
		str.append(getToStr());
		str.append("] numberOfAttchments[");
		str.append(getAttachments().size());
		str.append("]");
		
		return(str.toString());
		
	}

}
