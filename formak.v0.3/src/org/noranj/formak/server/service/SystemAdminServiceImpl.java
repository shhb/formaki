package org.noranj.formak.server.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.noranj.formak.client.service.SystemAdminService;
import org.noranj.formak.server.DAL1ToNHelper;
import org.noranj.formak.server.DALHelper;
import org.noranj.formak.server.LoginHelper;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.exception.NotLoggedInException;

import com.google.appengine.api.NamespaceManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This service is sued by Admin.
 * Right now all the methods set the name space to Amdin before working with datastore
 * It may not be needed as we will limit access to the signup pages to only Admin users and
 * by default their name space is set to the proper namespace by filter.
 * 
 * Another idea would be to use SERVLETs in backend to do the amdin jobs and limit the access their using web.xml.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class SystemAdminServiceImpl extends RemoteServiceServlet implements SystemAdminService {
  
  private static final long serialVersionUID = -2326021829857086171L;

  protected static Logger logger = Logger.getLogger(SystemAdminServiceImpl.class.getName());
  
  /**
   * It uses the users email address to find its detail information.
   * The email address is used as the user ID to sign in to the system.
   * 
   * @param emailAddress
   * @return the system user that is associated with the emailAddress. If it can not find the user, it returns null.
   */
  public SystemUserDTO getSystemUser(String emailAddress) {
    
    assert(emailAddress!=null && emailAddress.length()>0);
    SystemUser sysUser = LoginHelper.getSystemUser(emailAddress);
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
    
  	//TODO it may not be needed
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE);
    
    assert(NamespaceManager.get().equals(Constants.C_SYSTEM_ADMIN_NAMESPACE)); //keep this line
    
    try {
    	
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

    } finally {
    	NamespaceManager.set(currentNameSpace);
    }
    
  }

  // XXX TEST
  /**
   * It adds a new party client to the data store.
   * 
   * @param systemClientParty holds the data for the new client.
   * @return the id that is generated and assigned to the client party.
   */
  public String addSystemClientParty(SystemClientPartyDTO systemClientParty) {
  	//TODO it may not be needed
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE);
    
    assert(NamespaceManager.get().equals(Constants.C_SYSTEM_ADMIN_NAMESPACE)); //keep this line
    
    try {
    	
    
	    DALHelper<SystemClientParty> systemClientHelper = new DALHelper<SystemClientParty>(JDOPMFactory.getTxOptional(), SystemClientParty.class);
	    
	    SystemClientParty newSystemClientParty = new SystemClientParty(systemClientParty);
	    
	    systemClientHelper.storeEntity(newSystemClientParty);
	    
	    return(newSystemClientParty.getId());
        
    } finally {
    	NamespaceManager.set(currentNameSpace);
    }
  }

  //XXX TEST
  /**
   * It adds a new user to the data store.
   * 
   * @param systemUserDTO holds the data for the new user.
   * @return the new ID assigned to the SystemUserDTO
   * 
   */
  public String addSystemUser(SystemUserDTO systemUserDTO) { //FIXME what will happen if it fails to add the user!!!

    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE); 

    try {
    	
	    DAL1ToNHelper<SystemClientParty, SystemUser> systemClientHelper = new DAL1ToNHelper<SystemClientParty, SystemUser>(JDOPMFactory.getTxOptional(), SystemClientParty.class, SystemUser.class);
	    SystemUser sysUser = new SystemUser(systemUserDTO);
	    systemUserDTO.setId(systemClientHelper.addChildEntity(sysUser));
	    return(systemUserDTO.getId());
	    
    } finally {
    	NamespaceManager.set(currentNameSpace);
    }
    
  }
  
  /**
   * it can be sign up by email and UI.
   * @return
   */
  public String signup(SystemClientPartyDTO systemClientParty, SystemUserDTO systemUser) {

    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE); 

    try {
    	
	  	DALHelper<SystemClientParty> systemClientHelper = new DALHelper<SystemClientParty>(JDOPMFactory.getTxOptional(), SystemClientParty.class);

	  	SystemClientParty newSystemClientParty;
	    
	  	if(systemClientParty.getId()!=null) {
	  		newSystemClientParty =systemClientHelper.getEntityById(systemClientParty.getId(), null, 1); 
	  		if (newSystemClientParty == null) {
	  			logger.severe("SystemClientparty["+systemClientParty.getId()+"] does not exist in the system.");
	  			return null; //FIXME here
	  		}
	  	}
	  	else {
	  		newSystemClientParty = new SystemClientParty(systemClientParty);
	  		systemClientHelper.storeEntity(newSystemClientParty);
	  	}
	  	
	    DAL1ToNHelper<SystemClientParty, SystemUser> systemClientHelper2 = new DAL1ToNHelper<SystemClientParty, SystemUser>(JDOPMFactory.getTxOptional(), SystemClientParty.class, SystemUser.class);
	    SystemUser sysUser = new SystemUser(systemUser);
	    systemUser.setId(systemClientHelper2.addChildEntity(sysUser));
	    return(systemUser.getId());
	    //XXX here
    } finally {
    	NamespaceManager.set(currentNameSpace);
    }
  }
  
  @Override
  public SystemUserDTO getLoggedInUserDTO() {
    HttpSession session = getThreadLocalRequest().getSession();
    SystemUserDTO userDTO = LoginHelper.getLoggedInUser(session);
    return userDTO;
  }

  @Override
  public void logout() throws NotLoggedInException {
    getThreadLocalRequest().getSession().invalidate();
    throw new NotLoggedInException("Logged out");
  }

 
}
