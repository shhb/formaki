package org.noranj.formak.server.domain.biz;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.formak.server.domain.association.PartyRoleDocument;
import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.shared.type.DocumentType;

/**
 * This class is used to store a DespatchAdvice.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
public class DispatchAdvice extends BusinessDocument implements Serializable {

  protected static Logger logger = Logger.getLogger(DispatchAdvice.class.getName());
	
  @NotPersistent
  private static final long serialVersionUID = 6257544276859036943L;
  
  
  ///////////////////////////////////////////////
  //// getter/setter methods
  ///////////////////////////////////////////////
  
  
}
