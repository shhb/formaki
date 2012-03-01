package org.noranj.formak.server;


import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.noranj.formak.shared.exception.NotFoundException;

/**
 * MAKE THIS CHANGE IN THE CDOE
 * 
 * 
 * 
 *     {
      // update user info under transactional control
      PersistenceManager pm = PMF.getTxnPm();
      Transaction tx = pm.currentTransaction();
      try {
        for (int i = 0; i < NUM_RETRIES; i++) {
          tx = pm.currentTransaction();
          tx.begin();
          u = (UserAccount) pm.getObjectById(UserAccount.class, aUser.getId());
          String channelId = ChannelServer.createChannel(u.getUniqueId());
          u.setChannelId(channelId);
          u.setLastActive(new Date());
          u.setLastLoginOn(new Date());
          try {
            tx.commit();
            break;
          }
          catch (JDOCanRetryException e1) {
            if (i == (NUM_RETRIES - 1)) { 
              throw e1;
            }
          }
        } // end for
      } 
      catch (JDOException e) {
        e.printStackTrace();
        return null;
      } 
      finally {
        if (tx.isActive()) {
          logger.severe("loginStart transaction rollback.");
          tx.rollback();
        }
        pm.close();
      }
    } original sample code 
  
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @param <T>
 */
public class DALHelper<T> {
  
  PersistenceManagerFactory pmf;

  /* stores the CLASS of T */
  Class<T> persistentClass;
  
  /** Constructor, defining the PersistenceManagerFactory to use. */
  public DALHelper(PersistenceManagerFactory pmf, Class persistentClass) {
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
  public T getEntityById(String id, String[] fetchGroups, int maxFetchDepth) {
    assert (id != null);

    T entity, detachedEntity= null;
    PersistenceManager pm = getPersistenceManager();
    
    if (fetchGroups!=null) {
      for (String fetchGroup : fetchGroups) {
        pm.getFetchPlan().addGroup(fetchGroup);
      } 
    }
    
    pm.getFetchPlan().setMaxFetchDepth(maxFetchDepth); // To make sure attachment is loaded
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
   * The result of query should return only one value but if it doesn't the method returns the first item in the list.
   * 
   * NOTE: if any of the parameter is not used, pass null.
   * 
   * @param where
   * @param ordering
   * @param parameterDef
   * @param value
   * @return
   * @deprecated NOT TESTED YET
   */
  public T getEntityByQuery(String where, String ordering, String parameterDef, Object value, String[] fetchGroups, int maxFetchDepth) {
    
    assert(((parameterDef!=null) && (value!=null)) || (parameterDef==null) && (value==null));
    
    T entity = null;
    PersistenceManager pm = getPersistenceManager();
    
    if (fetchGroups!=null) {
      for (String fetchGroup : fetchGroups) {
        pm.getFetchPlan().addGroup(fetchGroup);
      } 
    }
    
    pm.getFetchPlan().setMaxFetchDepth(maxFetchDepth); // To make sure attachment is loaded
                                                       // and detached.

    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Query query = pm.newQuery(this.persistentClass);
      if (where!=null)
        query.setFilter(where); //"lastName == lastNameParam");
      if (ordering!=null)
        query.setOrdering(ordering); //"hireDate desc");
      if (parameterDef!=null)
      query.declareParameters(parameterDef); //"String lastNameParam");

      Collection<T> query_entities = null;
      
      if (value!=null)
        query_entities = (Collection<T>) query.execute(value);
      else
        query_entities = (Collection<T>) query.execute();
      
      if (!query_entities.isEmpty()) {
        // Detach the first object for use elsewhere
        entity = pm.detachCopy(query_entities.iterator().next());
      } 
      
      //FIXME what this line does and where it should be??? in finally ? after commit? or here!!  
      //query.closeAll();
      
      tx.commit();
    //} catch(Exception ex) {
    //  ex.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return entity;
  }
  

  /**
   * The result of query should return only one value but if it doesn't the method returns the first item in the list.
   * 
   * NOTE: if any of the parameter is not used, pass null.
   * NOTE: if you use parameters, the values must match the number of parameters used and defined.
   * 
   * @param filter
   * @param ordering
   * @param parametersDef
   * @param values
   * @return
   * @deprecated NOT TESTED YET
   */
  public T getEntityByQuery(String filter, String ordering, String parametersDef, Object[] values) {
    
    assert(((parametersDef!=null) && (values!=null)) || (parametersDef==null) && (values==null));
    
    T entity = null;
    PersistenceManager pm = getPersistenceManager();

    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Query query = pm.newQuery(this.persistentClass);
      query.setFilter(filter); //"lastName == lastNameParam");
      query.setOrdering(ordering); //"hireDate desc");
      query.declareParameters(parametersDef); //"String lastNameParam");

      Collection<T> query_entities = (Collection<T>) query.execute(values);
      
      if (!query_entities.isEmpty()) {
        // Detach the first object for use elsewhere
        entity = pm.detachCopy(query_entities.iterator().next());
      } 
      
      //FIXME what this line does and where it should be??? in finally ? after commit? or here!!  
      //query.closeAll();
      
      tx.commit();
    //} catch(Exception ex) {
    //  ex.printStackTrace();
    } finally {
      if (tx.isActive()) {
        tx.rollback();
      }
      pm.close();
    }
    return entity;
  }

  /**
   * The result of query should not be too large and the filter must be as simple as possible.
   * There are limitations on filters so keep them as simple as possible. 
   * See http://code.google.com/appengine/docs/java/datastore/jdo/queries.html#Introducing_Queries  
   * 
   * NOTE: if any of the parameter is not used, pass null.
   * NOTE: if you use parameters, the values must match the number of parameters used and defined.
   * 
   * @param filter
   * @param ordering
   * @param parametersDef
   * @param values
   * @return
   * @deprecated NOT TESTED YET
   */
  public Collection<T> getEntitiesByQuery(String filter, String ordering, String parametersDef, Object[] values) {
    
    Collection<T> entities = null;
    PersistenceManager pm = getPersistenceManager();

    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();

      Query query = pm.newQuery(this.persistentClass);
      query.setFilter(filter); //"lastName == lastNameParam");
      query.setOrdering(ordering); //"hireDate desc");
      query.declareParameters(parametersDef); //"String lastNameParam");

      Collection<T> query_entities = (Collection<T>) query.execute(values);
      
      // Detach our owner objects for use elsewhere
      entities = pm.detachCopyAll(query_entities);
      
      //FIXME what this line does and where it should be??? in finally ? after commit? or here!!  
      //query.closeAll();
      
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
   * NOTE: it should implement Cursor or adds Ranges to the query.
   *    http://code.google.com/appengine/docs/java/datastore/queries.html#Query_Cursors
   * @return
   * @deprecated NOT GOOD IDEA TO RETURN EVERYTHING. THERE SHOULD BE RANGE FOR THE QUERY.
   */
  public Collection<T> getEntities() {
    Collection<T> entities = null;
    PersistenceManager pm = getPersistenceManager();
    
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
  public void deleteEntity (Long id) {
    assert (id != null);

    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();

    try {
      tx.begin();
      T o = (T) pm.getObjectById(persistentClass, id);
      pm.deletePersistent(o);
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
