package org.noranj.formak.server.domain.biz;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;

import org.noranj.formak.server.domain.core.BusinessDocument;

/**
 * This class is used to store a RequestForQuotation.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
public class RequestForQuotation extends BusinessDocument implements Serializable {

  protected static Logger logger = Logger.getLogger(RequestForQuotation.class.getName());
	
  @NotPersistent
  private static final long serialVersionUID = 6257544276859036943L;
  
  ///////////////////////////////////////////////
  //// getter/setter methods
  ///////////////////////////////////////////////

  
}
