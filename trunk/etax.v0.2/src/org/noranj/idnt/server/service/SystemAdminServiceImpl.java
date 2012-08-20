package org.noranj.idnt.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.noranj.tax.client.service.SystemAdminService;
import org.noranj.core.server.DAL1ToNHelper;
import org.noranj.core.server.DALHelper;
import org.noranj.core.server.JDOPMFactory;
import org.noranj.core.shared.Constants;
import org.noranj.core.shared.exception.NotLoggedInException;
import org.noranj.idnt.server.SystemAdminHelper;
import org.noranj.idnt.server.domain.ClientParty;
import org.noranj.idnt.server.domain.User;
import org.noranj.idnt.server.servlet.LoginHelper;
import org.noranj.idnt.shared.dto.ClientPartyDTO;
import org.noranj.idnt.shared.dto.UserDTO;

import com.google.appengine.api.NamespaceManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This service is used by Admin.
 * Right now all the methods set the name space to Amdin before working with datastore
 * It may not be needed as we will limit access to the signup pages to only Admin users and
 * by default their name space is set to the proper namespace by filter.
 * 
 * NOTE: Another idea would be to use SERVLETs in backend to do the amdin jobs and limit the access their using web.xml.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.2.20120613.1740
 * @version 0.2.20120613.1740
 * @change
 *  BA:2012-06-13 Added signup
 */
public class SystemAdminServiceImpl extends RemoteServiceServlet implements SystemAdminService {//TODO:BA:2012-08-10: please check it 
  
  private static final long serialVersionUID = -2326021829857086171L;

  protected static Logger logger = Logger.getLogger(SystemAdminServiceImpl.class.getName());
  
  /**
   * It uses the users email address to find its detail information.
   * The email address is used as the user ID to sign in to the system.
   * 
   * @param emailAddress
   * @return the system user that is associated with the emailAddress. If it can not find the user, it returns null.
   */
  public UserDTO getSystemUser(String emailAddress) {
    
    assert(emailAddress!=null && emailAddress.length()>0);
    User sysUser = SystemAdminHelper.getSystemUser(emailAddress);
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
   * @param clientPartyDTO stores the party that we would like to get its users. If it is set to null, it gets all users.
   * @return the list of system users that belong to the client party. If no user is found, the list will be empty.
   */
  public List<UserDTO> getSystemUsers(ClientPartyDTO clientPartyDTO) {
    
  	//TODO it may not be needed
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE);
    
    assert(NamespaceManager.get().equals(Constants.C_SYSTEM_ADMIN_NAMESPACE)); //keep this line
    
    try {
    	
	    DALHelper<User> systemUserHelper = new DALHelper<User>(JDOPMFactory.getTxOptional(), User.class);
	    
	    Collection<User> sysUsers = systemUserHelper.getEntities(); 
	                                                            //(String.format("%s == '%s'", SystemUser.C_EMAIL_ADDRESS, emailAddress), /*filter*/ 
	                                                            //null /*ordering*/, null /*parameter*/, null /*value*/,
	                                                            //new String[] {SystemUser.C_FETCH_GROUP_PARENT_CLIENT} , /* fetch groups */ 
	                                                            //1); /* max fetch depth */
	    
	    List<UserDTO> sysUserList = new ArrayList<UserDTO>();
	    for (User su : sysUsers) {
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
  public String addSystemClientParty(ClientPartyDTO systemClientParty) {
  	//TODO it may not be needed
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE);
    
    assert(NamespaceManager.get().equals(Constants.C_SYSTEM_ADMIN_NAMESPACE)); //keep this line
    
    try {
    	
    
	    DALHelper<ClientParty> systemClientHelper = new DALHelper<ClientParty>(JDOPMFactory.getTxOptional(), ClientParty.class);
	    
	    ClientParty newSystemClientParty = new ClientParty(systemClientParty);
	    
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
  public String addSystemUser(UserDTO systemUserDTO) { //FIXME what will happen if it fails to add the user!!!

    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE); 

    try {
    	
	    DAL1ToNHelper<ClientParty, User> systemClientHelper = new DAL1ToNHelper<ClientParty, User>(JDOPMFactory.getTxOptional(), ClientParty.class, User.class);
	    User sysUser = new User(systemUserDTO);
	    systemUserDTO.setId(systemClientHelper.addChildEntity(sysUser));
	    return(systemUserDTO.getId());
	    
    } finally {
    	NamespaceManager.set(currentNameSpace);
    }
    
  }
  
  /**
   * It gets client party and user information then sign up the user for the specified party. 
   * @param systemClientPartyDTO stores the client information. 
   * If the id is not set, the assumption is that the party is a new party and will be added to the system.
   * The only required field in that case is party Name.
   * If it is passed as NULL, we assume the party doesn't exist so user's last name is used as party name.
   * @param systemUserDTO
   * @return
   * @since 0.2.20120613.1740
   * @version 0.2.20120613.1740
   */
  public String signup(ClientPartyDTO systemClientPartyDTO, UserDTO systemUserDTO) {
    
    if (systemClientPartyDTO==null) {
      //Adding a new party named as user's last name.
      systemClientPartyDTO= new ClientPartyDTO();
      systemClientPartyDTO.setName(systemUserDTO.getLastName());
    }
    
    return(SystemAdminHelper.signup(systemClientPartyDTO, systemUserDTO));
   
  }
  
  //TODO:BA:2012-08-10 It should change because it can't use a reference to client
 @Override
  public UserDTO getLoggedInUserDTO() {
    HttpSession session = getThreadLocalRequest().getSession();
    UserDTO userDTO = LoginHelper.getLoggedInUser(session);
    return userDTO;
  }

  @Override
  public void logout() throws NotLoggedInException {
    getThreadLocalRequest().getSession().invalidate();
    throw new NotLoggedInException("Logged out");
  }

 
}
