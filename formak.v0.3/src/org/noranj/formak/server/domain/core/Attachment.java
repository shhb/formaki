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
 */
public class Attachment implements Serializable {
  
  private static final long serialVersionUID = 2388154855666872387L;
  
  private byte[] content;
  private String name;
  private MIMEType mimeType;
  
  public byte[] getContent() {
    return content;
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

}
