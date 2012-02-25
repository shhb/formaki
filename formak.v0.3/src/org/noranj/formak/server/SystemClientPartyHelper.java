package org.noranj.formak.server;


//import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.EnumSet;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.noranj.formak.server.domain.core.Party;
import org.noranj.formak.server.domain.sa.SystemClientParty;
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
 * @deprecated NOT COMPLETED> JUST COPIED 2012-FEB-02
 *             I THINK I DO NOT NEED THIS CLASS. INSTEAD USE DALHelper
 */
public class SystemClientPartyHelper extends PartyHelper<SystemClientParty> {
  
  
  /** Constructor, defining the PersistenceManagerFactory to use. */
  public SystemClientPartyHelper(PersistenceManagerFactory pmf, Class persistentClass) {
    super(pmf, persistentClass);  
  }

  
  /**
   * it gets different type of filters that only Sysclientparty knows about the,
   * @param partyRoleTypes indicates the list of party roles.
   * @return
   * @deprecated NOT COMPLETED!!!
   */
  public Collection<SystemClientParty> getSystemClientParties(EnumSet<PartyRoleType> partyRoleTypes) {
    
    Collection<SystemClientParty> entities = null;
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
      Collection<SystemClientParty> query_entities = (Collection<SystemClientParty>) q.execute();
      
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
 
  

}
