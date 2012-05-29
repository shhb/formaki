package org.noranj.formak.server;


import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.noranj.formak.shared.type.ChildOwnedEntity;
import org.noranj.formak.shared.type.ChildUnownedEntity;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentStatusType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.ParentOwnedEntity;
import org.noranj.formak.shared.type.ParentUnownedChildEntity;

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
public class BusinessDocumentNItemsHelper <T extends ParentOwnedEntity, K extends ChildOwnedEntity> {
  
	//TODO decide what kind of logger to use BA-2012-03-21 using static logger for BusinessDocumentHelper<T> or using persistentClass.logger??? 
  private static Logger logger = Logger.getLogger(BusinessDocumentNItemsHelper.class.getName());

  PersistenceManagerFactory pmf;

  /* stores the CLASS of T */
  Class<T> persistentClass;
  Class<K> childPersistentClass;
  
  /** Constructor, defining the PersistenceManagerFactory to use. */
  public BusinessDocumentNItemsHelper(PersistenceManagerFactory pmf, Class persistentClass, Class childPersistentClass) {
    this.pmf = pmf;
    this.persistentClass = persistentClass;  
    this.childPersistentClass = childPersistentClass;  
  }

  /** Accessor for a PersistenceManager */
  protected PersistenceManager getPersistenceManager() {
    return pmf.getPersistenceManager();
  }


  // XXX HERE 26 May, deleting child and update the parents.
  /**
   * @param id is the purchase order unique identifier assigned by the application.
   */
  public void updateEntity (T newEntity) {
    assert (newEntity!= null);
    Transaction tx = null;
    PersistenceManager pm = getPersistenceManager();

    Set<String> deletedChildIds = new HashSet<String>();

    try {
      
      T existingEntity = (T) pm.getObjectById(persistentClass, newEntity.getId()); //child.getParentId());

      Set<String> newEntityChildIds = newEntity.getChildIds();
      Set<String> existingChildIds = existingEntity.getChildIds();

      // loop on keys in data store
      for(String key : existingChildIds) {
        // if the key is not in current entity, it means it is deleted. 
        if(!newEntityChildIds.contains(key)) {
          deletedChildIds.add(key);
          //existingEntity.removeItem(key);
        }
      }

      // If I do not close the PM, the behavior of update is undefined as there are two entity attached to the same object.
      pm.close();
      pm = getPersistenceManager();
      tx = pm.currentTransaction();
      
      // by starting transaction here, there is a chance that some one else make changes in entity after being read.
      // it is OK as we just try to delete the keys that must be deleted and if they already deleted, we do not care.
      // Any way, later we need to find a way to lock the data while being updated.
      tx.begin();
      
      K child = null;//(K) pm.getObjectById(childPersistentClass, ""); //childID
      if (deletedChildIds!=null) {
        for (String childId : deletedChildIds) {
          try {
            // there is a chance that the child no longer exist!!! 
            child = (K) pm.getObjectById(childPersistentClass, childId);
            pm.deletePersistent(child);
          } catch (Exception e) {
            System.out.println("ERROR: Exception in updateEntity - ["+e.getMessage()+ "]\r\n******************************\r\n");
            e.printStackTrace();
          }
        }
      } 
      
      pm.makePersistent(newEntity);
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
  
}























