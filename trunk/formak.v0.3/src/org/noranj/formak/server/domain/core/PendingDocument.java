package org.noranj.formak.server.domain.core;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.shared.type.DocumentType;

/**
 * It stores all the pending documents. The document may have received by email or any other type of communication.
 * It is not clear what the document is and it needs more processing before being stored in any of the data stores.
 * For example, if the attached document is a PDF or image, we need to process them and find out what they are.
 * The documents are stored in BlobStore and in this data store we only save the key to the file in Blobstore.
 * 
 * There are queues that are looking at the objects in this datastore and get the files processed.
 * 
 * 
 * NOTE: later we may have more than one documents attached to an email. In that case, we can add PedningItems to this class
 * to store all related attached documents in one place.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.20120513
 * @version 0.3.20120513s
 * @change
 *
 */
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class PendingDocument extends BusinessDocument implements Serializable {

  protected static Logger logger = Logger.getLogger(PendingDocument.class.getName());
  
  private static final long serialVersionUID = 64657546435453459L;

  private DocumentType documentType;
  
  /////////////////////////////////////////////////
  //////
  /////////////////////////////////////////////////
  
  public PendingDocument() {
    super();
  }
  
}
