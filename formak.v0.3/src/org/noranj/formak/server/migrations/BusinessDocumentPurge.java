package org.noranj.formak.server.migrations;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import com.google.appengine.api.datastore.Cursor;
import org.datanucleus.store.appengine.query.JDOCursorHelper;
import org.noranj.formak.server.domain.biz.Quotation;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.server.service.JDOPMFactory;

/**
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 * BusinessDocumentPurge is a migration to remove from the Datastore all BusinessDocument entities
 *  older than a given date.
 * @version 0.3.2012MAR19
 * @since 0.3.2012MAR19
 * @changes
 * 
 * @deprecated IT HAS NOT BEEN TESTED. BA-2012-MAR-20
 */
public class BusinessDocumentPurge implements Migration  {

  protected static Logger logger = Logger.getLogger(BusinessDocumentPurge.class.getName());
	
  @SuppressWarnings("unchecked")
  public Map<String, String> migrate_down(String cursorString, int range, Map<String,String> params) {

    Query q = null;
    Long priorl = null;
    List<BusinessDocument> results = null;
    int daysOld = 365; // default number of days older than which to purge
    Long ts = (new Date()).getTime();
    
    if (params != null) {
      try {
        String daysStr = (String) params.get("days");
        daysOld = Integer.parseInt(daysStr);
      }
      catch (Exception e1) {
        // e1.printStackTrace();
      }
      try {
        String tsstring = (String) params.get("ts");
        ts = Long.parseLong(tsstring);
      }
      catch (Exception e2) {
        // just catch the parse exception.
        // e2.printStackTrace();
      }
      priorl = ts - (1000 * (daysOld * 3600 * 24));
      //prior = new Date(priorl); BA-2012-MAR-20

    }
    PersistenceManager pm = JDOPMFactory.getTxOptional().getPersistenceManager();

    try {
      if (priorl != null) {
        q = pm.newQuery(BusinessDocument.class, "createdTS <= :d1");        
      }
      else {
        q = pm.newQuery(BusinessDocument.class);
      }
      if (cursorString != null) {
        Cursor cursor = Cursor.fromWebSafeString(cursorString);
        Map<String, Object> extensionMap = new HashMap<String, Object>();
        extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
        q.setExtensions(extensionMap);
      }
      q.setRange(0, range);
      if (priorl == null) {
        results = (List<BusinessDocument>) q.execute();
      }
      else {
        results = (List<BusinessDocument>) q.execute(priorl);
      }
      if (results.iterator().hasNext()) {
        for (BusinessDocument businessDocument : results) {
          pm.deletePersistent(businessDocument);
        }
        Cursor cursor = JDOCursorHelper.getCursor(results);
        cursorString = cursor.toWebSafeString();
      }
      else {
        // no results
        cursorString = null;
      }
    }
    finally {
      q.closeAll();
      pm.close();
    }
    Map<String,String> res = new HashMap<String,String>();
    res.put("cursor", cursorString);
    res.put("hours", ""+daysOld);
    res.put("ts", ""+ ts);
    return res;
  }

  // a no-op for this class
  public Map<String, String> migrate_up(String cs, int range, Map<String,String> params) {
    return null;
  }

}