package org.noranj.formak.client.service;

import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.exception.NotFoundException;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3-2012FEB20
 * 
 */
public interface SystemAdminServiceAsync {

  public void getSystemUser(String emailAddress, AsyncCallback<SystemUserDTO> callback); //; // throws ServiceCallFailed;

  /**
   * It adds a new user to the data store.
   * 
   * @param systemUser holds the data for the new user.
   */
  public void addSystemUser(SystemUserDTO systemUser, AsyncCallback<Void> callback); // the implementation throws NotFoundException;

}
