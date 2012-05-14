package org.noranj.formak.server.domain.core;

import java.io.Serializable;

import org.noranj.formak.shared.type.MIMEType;

/**
 * This is a sample class to store attachments such as product image file.
 * The file is serialized and stored as BLOB in data store.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3
 * @version 0.3.20120513
 * @change
 *  BA:12-05-13 Added toString
 */
public class Attachment implements Serializable {
  
  private static final long serialVersionUID = 2388154855666872387L;
  
  private byte[] content;
  private String name;
  private MIMEType mimeType;
  
   
  public Attachment() {
	  super();
  }
  
	public Attachment(byte[] content, String name, MIMEType mimeType) {
	  super();
	  this.content = content;
	  this.name = name;
	  this.mimeType = mimeType;
  }
	
	public byte[] getContent() {
    return content;
  }
	public String getContentAsString() {
    return String.valueOf(content);
  }

	public void setContent(byte[] content) {
    this.content = content;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public MIMEType getMimeType() {
    return mimeType;
  }
  public void setMimeType(MIMEType mimeType) {
    this.mimeType = mimeType;
  }
  
  public long getSize() {
    if (this.content!=null)
      return(content.length);
    return(0);
  }

  //BA:12-05-13 Added to print better log messages
  /**
   * 
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("name[");
    str.append(name);
    str.append("] size[");
    str.append(getSize());
    str.append("] mimetype[");
    str.append(mimeType);
    str.append("]");
    return(str.toString());
  }
  
}
