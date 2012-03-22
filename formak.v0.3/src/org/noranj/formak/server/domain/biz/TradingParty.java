package org.noranj.formak.server.domain.biz;


import java.net.URI;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;

import org.noranj.formak.server.domain.core.Party;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.PartyRoleType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
public class TradingParty extends Party {

  protected static Logger logger = Logger.getLogger(TradingParty.class.getName());
	
  @NotPersistent
  private static final long serialVersionUID = 183741034710481700L;

  /**
   * 
   * @param name
   * @param roles
   */
  public TradingParty(String name, String logoURI, ActivityType activityType, Set<PartyRoleType> roles) {
    super(name, logoURI, activityType, roles);
  }

}
