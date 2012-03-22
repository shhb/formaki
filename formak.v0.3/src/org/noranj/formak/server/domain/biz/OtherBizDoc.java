package org.noranj.formak.server.domain.biz;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.noranj.formak.server.domain.core.BusinessDocument;


/**
 * This class is used to store the documents that have been received but can not be recognized as any of the known document types.
 * 
 * The idea is to extract as much as information possible and present it to users to define the type of document and 
 * manually convert the document to one of the known document types.
 *  
 * NOTE: it must update fields in BusinessDoument when it is getting updated.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.2012MAR21
 * @version 0.3.2012MAR21
 * 
 */
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class OtherBizDoc extends BusinessDocument implements Serializable {

  protected static Logger logger = Logger.getLogger(PurchaseOrder.class.getName());
	
  private static final long serialVersionUID = 64717699663153459L;

  public OtherBizDoc() {
    super();
  }
  
  /**
   * 
   * @param bizDoc
   */
  //public OtherBizDoc(OtherBizDocDTO bizDoc) {
  //  super();
  //}

}
