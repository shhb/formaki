package org.noranj.formak.server.domain.biz;

import java.util.logging.Logger;

import javax.jdo.annotations.PersistenceCapable;

import org.noranj.formak.server.domain.association.PartyRoleDocument;
import org.noranj.formak.server.domain.core.Profile;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
public class PartyProfile extends Profile {

  protected static Logger logger = Logger.getLogger(PartyProfile.class.getName());
	
  public PartyProfile() {
    super();
    // TODO Auto-generated constructor stub
  }
  
}
