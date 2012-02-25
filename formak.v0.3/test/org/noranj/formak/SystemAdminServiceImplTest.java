package org.noranj.formak;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.noranj.formak.server.domain.biz.PurchaseOrder;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.service.SystemAdminServiceImpl;
import org.noranj.formak.shared.dto.ProfileDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.dto.UserProfileDTO;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class SystemAdminServiceImplTest {

  private final static LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    helper.setUp();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    helper.tearDown();
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetSystemUser() {
    try {
      System.out.println("--------------------------------------------------------------------");
      
      String emailAddress = "babak@noranj.com";
      
      SystemAdminServiceImpl service = new SystemAdminServiceImpl();

      SystemUserDTO sysUser = service.getSystemUser(emailAddress);
      
      System.out.printf("System user found. parentClient ID [%d]\r\n", sysUser.getParentClient().getId());
      
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  @Test
  public void testCreateUser() {
    System.out.println("--------------------------------------------------------------------");
    
    try {
      
      HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
      roles.add(PartyRoleType.Buyer);
      
      makeTestDataUserRetailerParty();
      makeTestDataUserManufacturerParty();

    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  /**
   * 
   */
  private void makeTestDataUserRetailerParty() {
    
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    
    //Adding Retailer Party
    HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
    roles.add(PartyRoleType.Buyer);
    SystemClientPartyDTO parentClient = new SystemClientPartyDTO(0, "Noranj-Retailer", "http://retailer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
    service.addSystemClientParty(parentClient);

    // Adding two users
    SystemUserDTO user = null;
    
    user = new SystemUserDTO(0L, "Babak", "Retailer", "babak@noranj.com", parentClient, ActivityType.Active, new UserProfileDTO());
    service.addSystemUser(user);
    System.out.printf("User [%s] is created and its id is [%d]\r\n", user.getFirstName(), user.getId());

    user = new SystemUserDTO(0L, "Buyer", "Retailer", "buyer@noranj.com", parentClient, ActivityType.Active, new UserProfileDTO());
    service.addSystemUser(user);
    System.out.printf("User [%s] is created and its id is [%d]\r\n", user.getFirstName(), user.getId());
    
  }
  
  /**
   * 
   */
  private void makeTestDataUserManufacturerParty() {
    
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    
    //Adding Retailer Party
    HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
    roles.add(PartyRoleType.Seller);
    SystemClientPartyDTO parentClient = new SystemClientPartyDTO(0, "Noranj-Manufacturer", "http://manufacturer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
    service.addSystemClientParty(parentClient);

    SystemUserDTO user = null;
    
    user = new SystemUserDTO(0L, "Shahab", "Manufacturer", "shahab@noranj.com", parentClient, ActivityType.Active, new UserProfileDTO());
    service.addSystemUser(user);
    System.out.printf("User [%s] is created and its id is [%d]\r\n", user.getFirstName(), user.getId());

    user = new SystemUserDTO(0L, "Seller", "Manufacturer", "seller@noranj.com", parentClient, ActivityType.Active, new UserProfileDTO());
    service.addSystemUser(user);
    System.out.printf("User [%s] is created and its id is [%d]\r\n", user.getFirstName(), user.getId());
    
  }
  
  
  //@ Test
  public void testDeleteSystemUserByID() {
    try {
      System.out.println("--------------------------------------------------------------------");

      SystemAdminServiceImpl service = new SystemAdminServiceImpl();
      String emailAddress = "babak@noranj.com";
      SystemUserDTO bizDocs = service.getSystemUser(emailAddress); //FIXME the state is hard coded.
      if (bizDocs!=null) {
        fail("There is no user to delete!!!"); //FIXME MSG add email
      }
      
      /** NOT DONE YET
      service.deleteDocument(DocumentType.PurchaseOrder, bizDocumentDTO.getId());
      
      // Test if they are really deleted!!!!!
      try {
        PurchaseOrderDTO po = service.getPurchaseOrder(bizDocumentDTO.getId());
        fail("PurchaseOrder is not deleted.");
      } catch (JDOObjectNotFoundException ex) {
        // do nothing because it suppose to throw this exception. 
      }
      */
      
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    
    System.out.println("=====================================================================");
  }
  
}
