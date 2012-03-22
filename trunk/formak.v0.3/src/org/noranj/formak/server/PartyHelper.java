package org.noranj.formak.server;


import java.util.Collection;
import java.util.EnumSet;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.noranj.formak.server.domain.core.Party;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.PartyRoleType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @param <T>
 * @deprecated I THINK I DO NOT NEED THIS CLASS. INSTEAD USE DALHelper
 */
public class PartyHelper<T> {
  
  private static Logger logger = Logger.getLogger(PartyHelper.class.getName());
	
  PersistenceManagerFactory pmf;

  /* stores the CLASS of T */
  Class<T> persistentClass;
  
  /** Constructor, defining the PersistenceManagerFactory to use. */
  public PartyHelper(PersistenceManagerFactory pmf, Class persistentClass) {
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
   * 
   */
  public T getEntityById(Long id) {
    assert (id != null);

    T entity, detachedEntity= null;
    PersistenceManager pm = getPersistenceManager();
    
    pm.getFetchPlan().addGroup(Party.C_FETCH_GROUP_PROFILE);
                                              
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
   * @param partyRoleTypes indicates the list of party roles.
   * @return
   */
  public Collection<Party> getParties(EnumSet<PartyRoleType> partyRoleTypes) {
    
    Collection<Party> entities = null;
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
      Collection<Party> query_entities = (Collection<Party>) q.execute();
      
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
    //  ex.printStackTrace(); // FIXME
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
  }


  /**
   * @param id is the purchase order unique identifier assigned by the application.
   */
  public void deleteEntity (Long id) throws NotFoundException {
    assert (id != null);

    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();

    try {
      tx.begin();
      T o = (T) pm.getObjectById(persistentClass, id);
      pm.deletePersistent(o);
      tx.commit();
      //return (true);
    } catch (JDOObjectNotFoundException ex) {
      throw new NotFoundException(ex);
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
