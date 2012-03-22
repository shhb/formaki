package org.noranj.formak.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.noranj.formak.client.service.PartyService;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB21
 */
public class PartyServiceImpl  extends RemoteServiceServlet implements PartyService {

  	private static final long serialVersionUID = 1L;

    protected static Logger logger = Logger.getLogger(PartyServiceImpl.class.getName());

  	//FIXME PRO2
  /**
   * It returns the list of trading parties (ID and Name only) for the current user with the specified party role.
   * For example, list of buyers or sellers, ...
   * 
   * @param partnerRole
   * @return
   * @deprecated IT HAS NOT BEEN CIOMPLETED YET.
   */
  public List<IDNameDTO> getTradingPartiesIDName (PartyRoleType tradingPartyRole) {
    ArrayList<IDNameDTO> tps = new ArrayList<IDNameDTO>();
    tps.add(new IDNameDTO("1", "RParty1"));
    tps.add(new IDNameDTO("2", "RParty2"));
    return(tps);
  }

  //FIXME PRO2
  /**
   * It adds a new trading party to the list of trading parties of the current user's party.
   * The role of adding trading party is defined by the tradingParty.getRoleParty.
   * 
   * @param traidngParty
   * @deprecated IT HAS NOT BEEN CIOMPLETED YET.
   */
  public void addTradingParty (PartyDTO traidngParty) {
    
  }

}
