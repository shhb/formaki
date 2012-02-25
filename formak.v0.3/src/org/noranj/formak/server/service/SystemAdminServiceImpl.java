package org.noranj.formak.server.service;

import org.noranj.formak.server.DALHelper;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.shared.dto.SystemUserDTO;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class SystemAdminServiceImpl {

  /**
   * It uses the users email address to find its detail information.
   * The email address is used as the user ID to sign in to the system.
   * 
   * @param emailAddress
   * @return
   */
  public SystemUserDTO getSystemUser(String emailAddress) {
    
    assert(emailAddress!=null && emailAddress.length()>0);
    
    DALHelper<SystemUser> systemUserHelper = new DALHelper<SystemUser>(JDOPMFactory.getTxOptional(), SystemUser.class);
    
    SystemUser sysUser = systemUserHelper.getEntityByQuery(String.format("%s == '%s'", SystemUser.C_EMAIL_ADDRESS, emailAddress), /*filter*/ 
                                                            null /*ordering*/, null /*parameter*/, null /*value*/,
                                                            new String[] {SystemUser.C_FETCH_GROUP_PARENT_CLIENT} , /* fetch groups */ 
                                                            1); /* max fetch depth */
    
    //XXX what if sysUser is null
    assert(sysUser!=null);
    
    return(sysUser.getSystemUserDTO());
  }

  /**
   * It adds a new user to the data store.
   * 
   * @param systemUser holds the data for the new user.
   */
  public void addSystemUser(SystemUserDTO systemUser) {

    DALHelper<SystemClientParty> systemClientHelper = new DALHelper<SystemClientParty>(JDOPMFactory.getTxOptional(), SystemClientParty.class);
    
    SystemClientParty sysClientParty = systemClientHelper.getEntityById(systemUser.getParentClient().getId(), 
                                                                        new String[] {SystemClientParty.C_FETCH_GROUP_USERS}, 
                                                                        1 /* Fetch depth */);
    // It adds the user to the list of users for sysClientParty and reset the systemUser.parentClient to sysClientParty. 
    sysClientParty.addUser(systemUser); 
    
    systemClientHelper.storeEntity(sysClientParty);
    
  }
  
}
