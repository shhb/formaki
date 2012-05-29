package org.noranj.formak.server;


import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.noranj.formak.server.domain.biz.RequestForQuotation;
import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.server.domain.core.PendingDocument;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.service.JDOPMFactory;
import org.noranj.formak.server.utils.FileServiceHelper;
import org.noranj.formak.server.utils.ServletUtils;
import org.noranj.formak.server.utils.Utils;
import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentStatusType;
import org.noranj.formak.shared.type.DocumentType;

import com.google.appengine.api.NamespaceManager;
import com.google.gwt.core.client.GWT;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @param <T>
 * 
 * @changes
 *  BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
 */
public class BusinessDocumentHelper<T> {
  
	//TODO decide what kind of logger to use BA-2012-03-21 using static logger for BusinessDocumentHelper<T> or using persistentClass.logger??? 
  private static Logger logger = Logger.getLogger(BusinessDocumentHelper.class.getName());

  PersistenceManagerFactory pmf;

  /* stores the CLASS of T */
  Class<T> persistentClass;
  
  /** Constructor, defining the PersistenceManagerFactory to use. */
  public BusinessDocumentHelper(PersistenceManagerFactory pmf, Class persistentClass) {
    this.pmf = pmf;
    this.persistentClass = persistentClass;  
  }

  /** Accessor for a PersistenceManager */
  protected PersistenceManager getPersistenceManager() {
    return pmf.getPersistenceManager();
  }

  // FIXME it is not complit3ed. it doesn't cover the not found cases.
  /**
   * @param is
   *          is the primary key for order and is a long integer.
   * @changes 
   * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
   */
  //public T getEntityById(Long id) {
  public T getEntityById(String id) {
    assert (id != null);

    T entity, detachedEntity= null;
    PersistenceManager pm = getPersistenceManager();
    
    pm.getFetchPlan().addGroup(BusinessDocument.C_ATTACHMENT_FETCH_GROUP_NAME);
                                              
    pm.getFetchPlan().setMaxFetchDepth(1); // To make sure attachment is loaded
                                           // and detached.

    Transaction tx = pm.currentTransaction();
    try {

      tx.begin();

      entity = (T) pm.getObjectById(persistentClass, id);

      // FIXME what happens if the object is not found???

      // Detach our owner objects for use elsewhere
      detachedEntity = pm.detachCopy(entity);
      
      tx.commit();

    //} catch (Exception ex) {
    //  ex.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    } // finally

    return detachedEntity;
  } // getEntityById

  
  //SA-2012-FEB-13 Added for PO but could be used by other documents too.
  /**
   * 
   * @param id
   * @param fetchGroup defines the name of a fetch group that must be added to default fetch groups.
   * @return
   */
  public T getEntityById(String id,String fetchGroup) { //FIXME it is better to make it an array of String so we can pass a list of fetch groups when they are more than one.
	    assert (id != null);

	    T entity, detachedEntity= null;
	    PersistenceManager pm = getPersistenceManager();
	    
	    pm.getFetchPlan().addGroup(BusinessDocument.C_ATTACHMENT_FETCH_GROUP_NAME);
	    pm.getFetchPlan().addGroup(fetchGroup);
	                                              
	    pm.getFetchPlan().setMaxFetchDepth(1); // To make sure attachment is loaded
	                                           // and detached.

	    Transaction tx = pm.currentTransaction();
	    try {

	      tx.begin();

	      entity = (T) pm.getObjectById(persistentClass, id);

	      // FIXME what happens if the object is not found???

	      // Detach our owner objects for use elsewhere
	      detachedEntity = pm.detachCopy(entity);
	      
	      tx.commit();

	    //} catch (Exception ex) {
	    //  ex.printStackTrace();
	    } finally {
	      if (tx.isActive()) {
	        tx.rollback();
	      }
	      pm.close();
	    } // finally

	    return detachedEntity;
	  } // getEntityById


  /**
   * 
   * @return
   */
  public Collection<T> getEntities() {
    Collection<T> entities = null;
    PersistenceManager pm = getPersistenceManager();
    
    // make sure orderItems are loaded and will be detached.
    pm.getFetchPlan().setMaxFetchDepth(1); // FIXME I am looking for a way to
                                           // make sure orderItems are not
                                           // loaded and not detached. I set it
                                           // to 0 and it threw exception.

    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Query q = pm.newQuery(persistentClass);
      
      Collection<T> query_entities = (Collection<T>) q.execute();
      
      // Detach our owner objects for use elsewhere
      entities = pm.detachCopyAll(query_entities);
      
      tx.commit();
    //} catch(Exception ex) {
    //  ex.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return entities;
  }
  

  /**
   * 
   * @return
   */
  public Collection<BusinessDocument> getEntities(DocumentStateType documentStateType) {
    
    Collection<BusinessDocument> entities = null;
    PersistenceManager pm = getPersistenceManager();
    
    // make sure orderItems are loaded and will be detached.
    pm.getFetchPlan().setMaxFetchDepth(1); // FIXME I am looking for a way to
                                           // make sure orderItems are not
                                           // loaded and not detached. I set it
                                           // to 0 and it threw exception.

    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Query q = pm.newQuery(persistentClass);
      
      //FIXME add filter
      //!?!?!? need an index on DocumentStateType and then add it to query!!!!
      Collection<BusinessDocument> query_entities = (Collection<BusinessDocument>) q.execute();
      
      // Detach our owner objects for use elsewhere
      entities = pm.detachCopyAll(query_entities);
      
      tx.commit();
    //} catch(Exception ex) {
    //  ex.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return entities;
  }
  
 

  /**
   * 
   * @param entity is a detached object.
   */
  public void storeEntity(T entity) {
    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      pm.makePersistent(entity);

      tx.commit();
    //} catch (Exception ex) {
    //  ex.printStackTrace(); //FIXME
    } finally { //XXX
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
  }


  /**
   * @param id is the purchase order unique identifier assigned by the application.
   * 
   * @changes 
   * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
   */
  //public void deleteEntity (Long id) { // throws NotFoundException {
  public void deleteEntity (String id) { // throws NotFoundException {
    assert (id != null);

    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();

    try {
      tx.begin();
      T o = (T) pm.getObjectById(persistentClass, id);
      pm.deletePersistent(o);
      tx.commit();
      //FIXME
    //} catch (JDOObjectNotFoundException ex) {
    //  throw new NotFoundException(ex);
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

  }

  /**
   * @param id is the purchase order unique identifier assigned by the application.\
 * @throws Exception 
   * 
   * @changes 
   * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
   */
  //public void deleteEntities (List<Long> ids) { //throws NotFoundException {
  public void deleteEntities (List<String> ids)  { //throws NotFoundException {
    assert (ids != null);

    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();

    try {
      tx.begin();
      for (/*Long*/ String id : ids) {
        if(logger.isLoggable(Level.FINE))
          logger.fine("DELETE: " + id);
        T o = (T) pm.getObjectById(persistentClass, id);
        pm.deletePersistent(o);
      } // for
      tx.commit();

    //FIXME
    //} catch (JDOObjectNotFoundException ex) { 
    //  throw new NotFoundException(ex);
     } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

  }

  /**
   * It stores the attached document in BlobStroe and creates a PendingDocument for it.
   * The blobstore key is stored in PendingDocument so it can be found and processed later.
   * 
   * <li> Set NameSpace
   * <li> Store attachments in blobstore
   * <li> Add new PendingDocument to dataStore
   * <li> Reset Namespace to original value
   * 
   * NOTE: the pending documents are stored in different namespaces, it is important to provide namespace (SystemUser.parentClientID)
   * to the processing queue so they can access the documents. Otherwise, no one can see them but the user.
   * 
   * @param docType
   * @param originatorUser - they sent the document.
   * @param receiverUser - the document is sent to them.
   * @param attachments
   * @return the key .
   */
  public static PendingDocument storeAttachedDocument (DocumentType docType, SystemUser originatorUser, SystemUser receiverUser, Attachment attachment) throws IOException {
    
    assert(originatorUser!=null) : "originatorUser can not be null. This is the originator of the document and can not be NULL.";
    assert(receiverUser!=null) : "receoverUser can not be null. This is the receiver of the document and can not be NULL.";
    assert(attachment!=null) : "the method stores attachment and if there is none, we should not be in this method.";
    
    if(logger.isLoggable(Level.INFO)) {
      logger.info("storeAttachedDocument: DocumentType["+docType+"] originator[" + originatorUser +"] receiver[" + receiverUser+"] Attachment["+attachment+"]");
    }
    
    //store current namespace
    //set namesapce to SystemUser.parentClientID
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(receiverUser.getParentClientId()); // store the document in user's namespace
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("storeAttachedDocument: current was["+currentNameSpace+"] set namespace to["+receiverUser.getParentClientId()+"]");
    }

    // used to add new PendingDocument objects
    PendingDocument pendingDocument=null;
    // stores the blobkey for future references
    String blobKeyStr=null;

    try {

      // get business document helper to help us with storing and reading document 
      BusinessDocumentHelper<PendingDocument> businessDocumentHelper = new BusinessDocumentHelper<PendingDocument>(JDOPMFactory.getTxOptional(), PendingDocument.class);

      blobKeyStr = FileServiceHelper.writeToBlobFile(docType, originatorUser, receiverUser, attachment);
      if (logger.isLoggable(Level.FINE)) {
        logger.fine("storeAttachedDocument: wrote the file blobkey["+blobKeyStr+"]");
      }

      pendingDocument = new PendingDocument();
      pendingDocument.setCreatedTS(System.currentTimeMillis());
      pendingDocument.setOriginatorPartyID(originatorUser.getParentClientId());// it is assumed the originator sends the document to themselves
      pendingDocument.setReceiverPartyID(receiverUser.getParentClientId());// it is assumed the originator sends the document to themselves
      pendingDocument.setBizDocumentNumber(blobKeyStr);
      pendingDocument.setState(DocumentStateType.Received);
      pendingDocument.setNote("Attachment stored in blobstore. BizDocNumber is the key.");
      
      businessDocumentHelper.storeEntity(pendingDocument);
      
      if (logger.isLoggable(Level.FINE)) {
        logger.fine("storeAttachedDocument: pending document["+pendingDocument.toString()+"]");
      }

      
    } catch (Exception ex) {
      //logger.severe("Failed to store attachment in blobstore due to an exception ["+ex.getMessage()+"]. Stack Trace["+Utils.stackTraceToString(ex)+"]");
      throw new IOException("Failed to store attachment in blobstore due to an exception ["+ex.getMessage()+"]", ex);
    } finally {
      if (blobKeyStr!=null && pendingDocument==null) {
        /// delete the attachment from blobstore.
        FileServiceHelper.deleteBlobFile(blobKeyStr);
      }
      NamespaceManager.set(currentNameSpace);
    }
    
    return(pendingDocument);

  }
  
}
