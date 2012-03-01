package org.noranj.formak.client.service;

import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3-2012FEB28
 * @change
 *  BA-2012-FEB-28 Added two new methods.
 * 
 */
public interface SystemAdminServiceAsync {

  /**
   * 
   * @param emailAddress
   * @param callback
   * @deprecated NOT SURE IF THIS METHOD IS NEEDED BY CLIENT. REMOVE THIS COMMENT IF USED BY CLIENT.
   */
  public void getSystemUser(String emailAddress, AsyncCallback<SystemUserDTO> callback); //; // throws ServiceCallFailed;

  /**
   * It adds a new user to the data store.
   * 
   * @param systemUser holds the data for the new user.
   * The return value is the userId assigned to the user by the system.
   */
  public void addSystemUser(SystemUserDTO systemUser, AsyncCallback<String> callback); // the implementation throws NotFoundException;

  /**
   * 
   * @param callback
   */
  public void getLoggedInUserDTO(AsyncCallback<SystemUserDTO> callback);
  
  /**
   * 
   * @param callback
   * @throws NotLoggedInException
   */
  public void logout(AsyncCallback<Void> callback) throws NotLoggedInException;
  

}
