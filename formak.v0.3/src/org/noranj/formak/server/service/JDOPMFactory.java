package org.noranj.formak.server.service;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * A Singleton class that returns the persistence manager factory.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public final class JDOPMFactory {
	
    private static final PersistenceManagerFactory pmfTxOptionalInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    
    //private static final PersistenceManagerFactory pmfEventualReadShortDeadlineInstance = JDOHelper.getPersistenceManagerFactory("eventual-reads-short-deadlines");

    private JDOPMFactory() {}

    public static PersistenceManagerFactory getTxOptional() {
        return pmfTxOptionalInstance;
    }
    
    //public static PersistenceManagerFactory getEventualReadShortDeadLine() {
    //    return pmfEventualReadShortDeadlineInstance;
    //}
    
}