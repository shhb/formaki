package org.noranj.idnt.server;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;

import org.noranj.core.server.DAL1ToNHelper;
import org.noranj.core.server.DALHelper;
import org.noranj.core.server.JDOPMFactory;
import org.noranj.core.server.domain.MailMessage;
import org.noranj.core.server.utils.Utils;
import org.noranj.core.shared.Constants;
import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.server.domain.ClientParty;
import org.noranj.idnt.server.domain.User;
import org.noranj.idnt.shared.dto.ClientPartyDTO;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.server.Startup;

import com.google.appengine.api.NamespaceManager;

/**
 * The methods in this class are used by services or servlets to implement services for System Admins.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3
 * @version 0.3
 * @change
 *
 */
public class SystemAdminHelper {

  protected static Logger logger = Logger.getLogger(SystemAdminHelper.class.getName());

	/**
	 * 
	 * @param mail
	 */
	public static String signupUser(MailMessage mail) {

		///XXX HERE 2012-AUG-10
		/**
		1- check emailAddress and if it is already a member, throw an exception or send an email or return message that the user is already a member.
		2- send email to notify sender about the status of their email.
		3- add organization (client) to the body of email in case someone knows their client name. use the name to find the client and add the current user to that client.
		3-1- another way could be using the domain of email address to search for the client.
		*/
		
		Map<String, String> dataFieldsFromBody = Utils.buildMap(mail.getBody().getContentAsString());
		
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Attributes in body are {");
			for(String key:dataFieldsFromBody.keySet()) {
				logger.fine("key=["+key+"] value=["+dataFieldsFromBody.get(key)+"]");
			} // for
			logger.fine("} Attributes in body");
		}
		
		if(dataFieldsFromBody.get("emailAddress")==null) {
		  dataFieldsFromBody.put("emailAddress", mail.getFrom().getAddress()); // to overwrite the emailAddress in the mail body
		}
		
		if(dataFieldsFromBody.get("name")==null) {
		  dataFieldsFromBody.put("name", "client-guest-" + System.currentTimeMillis());
    }
		
		
		dataFieldsFromBody.put("fullname", mail.getFrom().getPersonal());
		
		return(signupUser(dataFieldsFromBody));
	}

	/* FIXME
	 * It should get the data out of MAP and set the DTOs attributes one by one.
   * It should not use the deprecated constructor
   */

	/**
	 * @param dataFields
	 * @return
	 */
	public static String signupUser(Map<String, String> dataFields) {
    
	  ClientPartyDTO sysClientDTO = new ClientPartyDTO(dataFields);
    
    sysClientDTO.setActivityType(ActivityType.Active); // to make sure the user is active and can login.
    
    UserDTO sysUserDTO = new UserDTO(dataFields);
    
    sysUserDTO.setActivityType(ActivityType.Active); // to make sure the user is active and can login.
    
    //Extract First Name and Last Name from emailAddresFROM
    if (sysUserDTO.getFirstName()==null && sysUserDTO.getLastName()==null) {
  		sysUserDTO.setFirstName("Guest"); //TODO review 
  		sysUserDTO.setLastName(String.valueOf(System.currentTimeMillis()));
    }
		
    if (logger.isLoggable(Level.FINE)) {
			logger.fine("User DTO is ["+sysUserDTO.toString()+"]");
    }
    
    /** Sign up the user */
    String userId = signup(sysClientDTO, sysUserDTO);

    sysUserDTO.setId(userId);
    logger.info("A new user successfully signed up. userid["+sysUserDTO.getId()+"] email["+sysUserDTO.getEmailAddress()+"]");
    
		return(sysUserDTO.getId());
		
	}
	
  //TODO I am not sure about the parameters. BA-2012-03-22
  /**
   * it can be called by email or UI to sign up a new user.
   * @return userID generated by the system for the new user.
	 * @param systemClientPartyDTO - the only parameter set is client name.
	 * @param systemUserDTO - email address and names are required. The rest are bonus.
	 * @return
   * @since 0.3.20120322
   * @version @since 0.3.20120322
	 */
  public static String signup(ClientPartyDTO systemClientPartyDTO, UserDTO systemUserDTO) {

    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE); 

    try {
    	
	  	DALHelper<ClientParty> systemClientHelper = new DALHelper<ClientParty>(JDOPMFactory.getTxOptional(), ClientParty.class);

	  	ClientParty newSystemClientParty;
	    
	  	// check if client exist
	  	if(systemClientPartyDTO.getId()!=null) {
	  		newSystemClientParty =systemClientHelper.getEntityById(systemClientPartyDTO.getId(), null, 1); 
	  		if (newSystemClientParty == null) {
	  			logger.severe("SystemClientParty uses an id["+systemClientPartyDTO.getId()+"] that does not exist in the system.");
	  			return null; //FIXME here
	  		}
	  	}
	  	else { // add client if the id is null.
	  		newSystemClientParty = new ClientParty(systemClientPartyDTO);
	  		systemClientHelper.storeEntity(newSystemClientParty);
	  	}
	  	
	  	//link the user to its parent.
	  	systemUserDTO.setParentClientId(newSystemClientParty.getId());
	  	
	  	// save the user in the system
	    DAL1ToNHelper<ClientParty, User> systemClientHelper2 = new DAL1ToNHelper<ClientParty, User>(JDOPMFactory.getTxOptional(), ClientParty.class, User.class);
	    User sysUser = new User(systemUserDTO);
	    systemUserDTO.setId(systemClientHelper2.addChildEntity(sysUser));
	    return(systemUserDTO.getId());
	    
    } finally {
    	NamespaceManager.set(currentNameSpace);
    }
  }


  /**
   * It get a javax.mail.internet.InternetAddress as user's email address then look it up in 
   * data store to see if email address belongs to a valid user.
   * @param userEmail
   * @return
   */
  public static User getSystemUser(InternetAddress userEmail) {
  	String userEmailAddress = userEmail.getAddress();
  	logger.fine("Address is ["+userEmailAddress+"]");
  	return(getSystemUser(userEmailAddress));
  }
  
  /**
   * Uses email address to find user.
   * This method can be used for authentication.
   * 
   * @param userEmailAddress
   * @return
   * 
   */
  public static User getSystemUser(String userEmailAddress) {
    
    assert(userEmailAddress!=null && userEmailAddress.length()>0);
    
    //BA:12-MAR-06 Added namespace
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE);
    
    try {
      DALHelper<User> systemUserHelper = new DALHelper<User>(JDOPMFactory.getTxOptional(), User.class);
    
      User sysUser = null;
      int retry = 1; 
      do {
        sysUser = systemUserHelper.getEntityByQuery(String.format("%s == '%s'", User.C_EMAIL_ADDRESS, userEmailAddress), /*filter*/ 
                                                              null /*ordering*/, null /*parameter*/, null /*value*/,
                                                              null,  /* ParentClient is no longer needs to be in Fatch Group because it is only a KEY    //new String[] {SystemUser.C_FETCH_GROUP_PARENT_CLIENT} , /* fetch groups */ 
                                                              1); /* max fetch depth */
        
        --retry;
  
        if (sysUser == null && (userEmailAddress.equals("buyer@noranj.com") || userEmailAddress.equals("seller@noranj.com"))) {
          logger.info("LoginHelper - Try to create sample data.");
          try {
            Startup.makeTestDataUserRetailerParty(); //Buyer
            Startup.makeTestDataUserManufacturerParty(); // Seller
          } catch (Exception ex) {
            ex.printStackTrace();
            logger.warning("LoginHelper - Failed to create sample data. Abort the process.");
            retry = -1;// do not try any more 
          }
          
        }
        else {
          //found the user, end the loop
          retry = -1;
        }
        
      }while (retry>=0);
      
      return(sysUser);
    
    } finally {
      //BA:12-MAR-06 Added namespace
      NamespaceManager.set(currentNameSpace); 
    }

  }


		
} //class
