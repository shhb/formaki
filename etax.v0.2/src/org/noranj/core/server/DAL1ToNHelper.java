package org.noranj.core.server;


import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.noranj.core.shared.type.ChildUnownedEntity;
import org.noranj.core.shared.type.ParentUnownedChildEntity;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @param <T> parent class
 * @param <K> child class
 */
public class DAL1ToNHelper<T extends ParentUnownedChildEntity, K extends ChildUnownedEntity> {
  
	//TODO decide what kind of logger to use BA-2012-03-21 using static or dynamic persistentClass.logger???  
  private static Logger logger = Logger.getLogger(DAL1ToNHelper.class.getName());
	
  PersistenceManagerFactory pmf;

  /* stores the CLASS of T */
  Class<T> persistentClass;
  Class<K> childPersistentClass;
  
  /** Constructor, defining the PersistenceManagerFactory to use. */
  public DAL1ToNHelper(PersistenceManagerFactory pmf, Class persistentClass, Class childPersistentClass) {
    this.pmf = pmf;
    this.persistentClass = persistentClass;  
    this.childPersistentClass = childPersistentClass;  
  }

  /** Accessor for a PersistenceManager */
  protected PersistenceManager getPersistenceManager() {
    return pmf.getPersistenceManager();
  }

  /**
   * 
   * @param entity is a detached object.
   */
  public String addChildEntity(K child) {
    
    String childId = null;
    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    
    try {
      tx.begin();

      T parent = (T) pm.getObjectById(persistentClass, child.getParentId());
      
      pm.makePersistent(child);
      assert (parent!=null);
      //NO NEED TO DETACH!!! 
      ////Detach our owner objects for use elsewhere
      //detachedEntity = pm.detachCopy(entity);

      // It adds the child entity to the list of parent entity. 
      parent.addChildId(child.getId());

      pm.makePersistent(parent);
            
      tx.commit();
      
      childId = child.getId();
      
    //} catch (Exception ex) {
    //  ex.printStackTrace(); // FIXME
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return(childId);
  }

  // XXX HERE deleting child and update the parents.
  /**
   * @param id is the purchase order unique identifier assigned by the application.
   */
  public void deleteChildEntity (String childId) {
    assert (childId!= null);

    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();

    try {
      tx.begin();
      K child = (K) pm.getObjectById(childPersistentClass, childId);

      T parent = (T) pm.getObjectById(persistentClass, child.getParentId());
      
      List<String> childIds = parent.getChildIds();
      if (childIds!=null) {
        for (int i=0; i< childIds.size(); ++i) {
          if (childId == childIds.get(i)) {
            childIds.remove(i); // remove child from the list of childs
            pm.makePersistent(parent); // save the parent with the new list
            break;
          }
        } //for
      } // if
      
      pm.deletePersistent(child);
      tx.commit();
    //} catch (JDOObjectNotFoundException ex) {
    //  throw new NotFoundException(ex);
    //} catch (Exception ex) {
    //  ex.printStackTrace(); //FIXME it must throw another type of exception!!!!
      ///throw ex;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

  }

  // XXX HERE deleting parent and its child.
  /**
   * @param id is the purchase order unique identifier assigned by the application.
   */
  public void deleteParentEntity (String parentID) {
    assert (parentID != null);

    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();

    try {
      tx.begin();
      T parent = (T) pm.getObjectById(persistentClass, parentID);
      
      List<String> childIds = parent.getChildIds();
      if (childIds!=null) {
        K child = null;
        for (String childId : childIds) {
          child = pm.getObjectById(childPersistentClass, childId);
          pm.deletePersistent(child);
        } //for
      } // if
      pm.deletePersistent(parent);
      tx.commit();
    //} catch (JDOObjectNotFoundException ex) {
    //  throw new NotFoundException(ex);
    //} catch (Exception ex) {
    //  ex.printStackTrace(); //FIXME it must throw another type of exception!!!!
      ///throw ex;
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }

  } //deleteParentEntity
  
}
