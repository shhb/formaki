package org.noranj.formak.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.noranj.formak.server.DAL1ToNHelper;
import org.noranj.formak.server.DALHelper;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.exception.NotFoundException;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class SystemAdminServiceImpl {
  
  // XXX TEST
  /**
   * It uses the users email address to find its detail information.
   * The email address is used as the user ID to sign in to the system.
   * 
   * @param emailAddress
   * @return the system user that is associated with the emailAddress. If it can not find the user, it returns null.
   */
  public SystemUserDTO getSystemUser(String emailAddress) {
    
    assert(emailAddress!=null && emailAddress.length()>0);
    
    DALHelper<SystemUser> systemUserHelper = new DALHelper<SystemUser>(JDOPMFactory.getTxOptional(), SystemUser.class);
    
    SystemUser sysUser = systemUserHelper.getEntityByQuery(String.format("%s == '%s'", SystemUser.C_EMAIL_ADDRESS, emailAddress), /*filter*/ 
                                                            null /*ordering*/, null /*parameter*/, null /*value*/,
                                                            null,  /* PArentClient is no longer needs to be in Fatch Group because it is only a KEY    //new String[] {SystemUser.C_FETCH_GROUP_PARENT_CLIENT} , /* fetch groups */ 
                                                            1); /* max fetch depth */
    
    if (sysUser!=null)
      return(sysUser.getSystemUserDTO());
    else 
      return(null);
    
  }

  // XXX TEST
  /**
   * It uses the users email address to find its detail information.
   * The email address is used as the user ID to sign in to the system.
   * 
   * @param systemClientPartyDTO stores the party that we would like to get its users. If it is set to null, it gets all users.
   * @return the list of system users that belong to the client party. If no user is found, the list will be empty.
   */
  public List<SystemUserDTO> getSystemUsers(SystemClientPartyDTO systemClientPartyDTO) {
    
    DALHelper<SystemUser> systemUserHelper = new DALHelper<SystemUser>(JDOPMFactory.getTxOptional(), SystemUser.class);
    
    Collection<SystemUser> sysUsers = systemUserHelper.getEntities(); 
                                                            //(String.format("%s == '%s'", SystemUser.C_EMAIL_ADDRESS, emailAddress), /*filter*/ 
                                                            //null /*ordering*/, null /*parameter*/, null /*value*/,
                                                            //new String[] {SystemUser.C_FETCH_GROUP_PARENT_CLIENT} , /* fetch groups */ 
                                                            //1); /* max fetch depth */
    
    List<SystemUserDTO> sysUserList = new ArrayList<SystemUserDTO>();
    for (SystemUser su : sysUsers) {
      sysUserList.add(su.getSystemUserDTO());
    }
    return(sysUserList);
    
  }

  // XXX TEST
  /**
   * It adds a new party client to the data store.
   * 
   * @param systemClientParty holds the data for the new client.
   * @return the id that is generated and assigned to the client party.
   */
  public String addSystemClientParty(SystemClientPartyDTO systemClientParty) {

    DALHelper<SystemClientParty> systemClientHelper = new DALHelper<SystemClientParty>(JDOPMFactory.getTxOptional(), SystemClientParty.class);
    
    SystemClientParty newSystemClientParty = new SystemClientParty(systemClientParty);
    
    systemClientHelper.storeEntity(newSystemClientParty);
    
    return(newSystemClientParty.getId());
        
  }

  //XXX TEST
  /**
   * It adds a new user to the data store.
   * 
   * @param systemUserDTO holds the data for the new user.
   * @return the new ID assigned to the SystemUserDTO
   */
  public String addSystemUser(SystemUserDTO systemUserDTO) throws NotFoundException {

    DAL1ToNHelper<SystemClientParty, SystemUser> systemClientHelper = new DAL1ToNHelper<SystemClientParty, SystemUser>(JDOPMFactory.getTxOptional(), SystemClientParty.class, SystemUser.class);
    SystemUser sysUser = new SystemUser(systemUserDTO);
    systemUserDTO.setId(systemClientHelper.addChildEntity(sysUser));
    return(systemUserDTO.getId());
    
  }
  
 
}
