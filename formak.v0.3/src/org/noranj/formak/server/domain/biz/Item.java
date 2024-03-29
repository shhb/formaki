package org.noranj.formak.server.domain.biz;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.formak.server.domain.association.PartyRoleDocument;
import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.shared.type.DocumentType;

/**
 * This class is used to store a Product.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
public class Item extends BusinessDocument implements Serializable {

  protected static Logger logger = Logger.getLogger(Item.class.getName());

  @NotPersistent
  private static final long serialVersionUID = 6257544276859036943L;
  
  @Persistent(serialized = "true")
  private Attachment imageFile;

  
  ///////////////////////////////////////////////
  //// getter/setter methods
  ///////////////////////////////////////////////

  public long getGTIN() {
    return 0;
  }

  public void setGTIN(long gtin) {
    //this.id = gtin;
  }
  
  
}
