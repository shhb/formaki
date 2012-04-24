package org.noranj.formak.server;


import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.utils.ServletUtils;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;

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
    	System.out.println("DEELEET: " + id);
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
   * 
   * @param docType
   * @param sysUser
   * @param attachment
   * @return
   */
  public static String storeAttachedDocuments (DocumentType docType, SystemUser sysUser, List<Attachment> attachment) {
  	
  	String documentID = "";
  	///XXX here APR-19 - working on knowing what the attachments are and then tryiing to save them so they can be processed by another queue later.
  	// 
  	// Loop on attached document and store them.
  	// Assume there is only one document and one attachment.
  	// need to see how the email works and if it puts the body of email as attachment too.
  	// or we may need to look for a specific attachment. For example, we don't want to save user's signatures.
  	
  	return(documentID);
  	
  }
  
}
