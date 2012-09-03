package org.noranj.tax.v2012.client.service;


import org.noranj.core.shared.exception.NotFoundException;
import org.noranj.core.shared.exception.NotLoggedInException;
import org.noranj.idnt.shared.dto.ClientPartyDTO;
import org.noranj.idnt.shared.dto.UserDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3-2012FEB20
 */
@RemoteServiceRelativePath("systemAdmin")
public interface SystemAdminService extends RemoteService {

  /**
   * It uses the users email address to find its detail information.
   * The email address is used as the user ID to sign in to the system.
   * 
   * @param emailAddress
   * @return
   */
  public UserDTO getSystemUser(String emailAddress);

  /**
   * It adds a new user to the data store.
   * 
   * @param systemUser holds the data for the new user.
   */
  public String addSystemUser(UserDTO systemUser); // the implementation throws NotFoundException;
  
  public String addSystemClientParty(ClientPartyDTO systemClientParty);

  public String signup(ClientPartyDTO systemClientPartyDTO, UserDTO systemUserDTO);
  
  UserDTO getLoggedInUserDTO();
  
  void logout() throws NotLoggedInException;
  
}
