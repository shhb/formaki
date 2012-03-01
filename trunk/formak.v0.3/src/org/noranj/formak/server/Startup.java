package org.noranj.formak.server;

import java.util.HashSet;

import org.noranj.formak.server.service.SystemAdminServiceImpl;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.dto.UserProfileDTO;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.PartyRoleType;

/**
 * This is the first class that is called at the start up to load the settings and some sample data when needed.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class Startup {

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
    
    SystemClientPartyDTO parentClient = new SystemClientPartyDTO(null, "Noranj-Retailer", "http://retailer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
    
    id = service.addSystemClientParty(parentClient);
    parentClient.setId(id);
    
    System.out.printf("Party [%s] is created and its id is [%s]\r\n", parentClient.getName(), parentClient.getId());

    // Adding two users
    SystemUserDTO user = null;
    
    user = new SystemUserDTO(null, "Babak", "Retailer", "babak@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO() );
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());

    user = new SystemUserDTO(null, "Buyer", "Retailer", "buyer@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO() );
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
    SystemClientPartyDTO parentClient = new SystemClientPartyDTO(null/*id*/, "Noranj-Manufacturer", "http://manufacturer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
    id = service.addSystemClientParty(parentClient);
    parentClient.setId(id);

    System.out.printf("Party [%s] is created and its id is [%s]\r\n", parentClient.getName(), parentClient.getId());

    SystemUserDTO user = null;
    
    user = new SystemUserDTO(null, "Shahab", "Manufacturer", "shahab@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(),System.currentTimeMillis(),new UserProfileDTO());
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());

    user = new SystemUserDTO(null, "Seller", "Manufacturer", "seller@noranj.com", parentClient.getId(), ActivityType.Active, System.currentTimeMillis(), System.currentTimeMillis(), new UserProfileDTO());
    id = service.addSystemUser(user);
    user.setId(id);
    System.out.printf("User [%s] is created and its id is [%s] and the email is [%s]\r\n", user.getFirstName(), user.getId(), user.getEmailAddress());
    
  }

}
