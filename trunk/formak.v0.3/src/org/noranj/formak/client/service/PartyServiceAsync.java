package org.noranj.formak.client.service;

import java.util.List;

import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3-2012FEB20
 */
public interface PartyServiceAsync {

  /**
   * It returns the list of trading parties (ID and Name only) for the current user with the specified party role.
   * For example, list of buyers or sellers, ...
   * 
   * @param partnerRole
   * @return
   */
  public void getTradingPartiesIDName (PartyRoleType tradingPartyRole, AsyncCallback<List<IDNameDTO>> callback);

  /**
   * It adds a new trading party to the list of trading parties of the current user's party.
   * The role of adding trading party is defined by the tradingParty.getRoleParty.
   * 
   * @param traidngParty
   */
  public void addTradingParty (PartyDTO traidngParty, AsyncCallback callback);
  
  
}
