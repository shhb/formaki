package org.noranj.tax.v2012.server;

import java.util.HashSet;
import java.util.logging.Logger;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.server.service.SystemAdminServiceImpl;
import org.noranj.idnt.shared.dto.AccountDTO;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.idnt.shared.dto.UserProfileDTO;
import org.noranj.tax.v2012.shared.type.PartyRoleType;


/**
 * This is the first class that is called at the start up to load the settings and some sample data when needed.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class Startup {

  private static Logger logger = Logger.getLogger(Startup.class.getName());
	
  //TODO it is static and public only for test purpose as it is used in LoginHelper to create some test data. BA-2012-FEB-29 
  /**
   * 
   */
  public static void makeTestDataUserRetailerParty() throws Exception {
    
    String id = null;
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    
    //Adding Retailer Party
    HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
    roles.add(PartyRoleType.Buyer);
    
    AccountDTO parentClient = new AccountDTO(null, "Noranj-Retailer", "http://retailer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
    
    id = service.addSystemClientParty(parentClient);
    parentClient.setId(id);
    
    System.out.printf("Party [%s] is created and its id is [%s]\r\n", parentClient.getName(), parentClient.getId());

    // Adding two users
    UserDTO user = null;
    
    user = new UserDTO(null, "Babak", "Retailer", "babak@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO() );
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());

    user = new UserDTO(null, "Buyer", "Retailer", "buyer@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO() );
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());
    
  }
  
  //TODO it is static and public only for test purpose as it is used in LoginHelper to create some test data. BA-2012-FEB-29 
  /**
   * 
   */
  public static void makeTestDataUserManufacturerParty() throws Exception {
    
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    String id = null;
    
    //Adding Retailer Party
    HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
    roles.add(PartyRoleType.Seller);
    AccountDTO parentClient = new AccountDTO(null/*id*/, "Noranj-Manufacturer", "http://manufacturer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
    id = service.addSystemClientParty(parentClient);
    parentClient.setId(id);

    System.out.printf("Party [%s] is created and its id is [%s]\r\n", parentClient.getName(), parentClient.getId());

    UserDTO user = null;
    
    user = new UserDTO(null, "Shahab", "Manufacturer", "shahab@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(),System.currentTimeMillis(),new UserProfileDTO());
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());

    user = new UserDTO(null, "Seller", "Manufacturer", "seller@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO());
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());
    
  }

}
