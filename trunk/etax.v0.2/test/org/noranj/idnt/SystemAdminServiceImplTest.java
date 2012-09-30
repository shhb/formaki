package org.noranj.idnt;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.server.domain.Account;
import org.noranj.idnt.server.service.SystemAdminServiceImpl;
import org.noranj.idnt.shared.dto.AccountDTO;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.v2012.server.Startup;

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

  /** if set to TRUE, it means there is no data in data store for the test and test data must be created.*/
  private boolean generateTestData = true;
  
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

  //@Test
  public void testGetUsers() throws Exception  {
    
    if (generateTestData) {
      Startup.makeTestDataUserRetailerParty();
      Startup.makeTestDataUserManufacturerParty();
      generateTestData = false;
    }

    try {
      System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
      
      SystemAdminServiceImpl service = new SystemAdminServiceImpl();

      List<UserDTO> sysUserList = service.getUsers(null);
      if (sysUserList != null) {
        for (UserDTO dto : sysUserList) {
          System.out.printf("*** User [%s] found. email is [%s] and account ID [%s]\r\n", dto.getFirstName(), dto.getFirstName(), dto.getId());
        }
      } 
      else {
        //fail(String.format("User not found", emailAddress));
        fail("no user is found");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
  }


  //@Test
  public void testGetUser() {
    try {
      System.out.println("--------------------------------------------------------------------");
      
      //String emailAddress = "babak@noranj.com";
      String emailAddress = "dalish@gmail.com";
      
      SystemAdminServiceImpl service = new SystemAdminServiceImpl();

      UserDTO sysUser = service.getUser(emailAddress);
      if (sysUser != null) {
        System.out.printf("User [%s] found. parentClient ID [%s]\r\n", emailAddress, sysUser.getAccountId());
      } 
      else {
        fail(String.format("User not found [%s]", emailAddress));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  @Test
  public void testSignup() {
    try {
      System.out.println("--------------------------------------------------------------------");
      
      String emailAddress = "dalish@gmail.com";
      
      SystemAdminServiceImpl service = new SystemAdminServiceImpl();
      UserDTO user = new UserDTO(null, null, emailAddress);
      if (service.signup(null, user)!=null) {
        UserDTO savedUser = service.getUser(emailAddress);
        if (savedUser!=null) {
          AccountDTO account = service.getAccount(savedUser.getAccountId());
          System.out.printf("User [%s] is signed up successfully email[%s]. The account is [%s]\r\n", user.toString(), emailAddress, account.toString());
        }
        else {
          fail(String.format("Signup failed because we can not retrieve the signed up user using their email address", savedUser.toString()));
        }
          
      } 
      else {
        fail(String.format("Failed to sign up user with email [%s]", emailAddress));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }
  
  //@ Test
  public void testCreateUser() {
    System.out.println("--------------------------------------------------------------------");
    
    try {
      
      Startup.makeTestDataUserRetailerParty();
      Startup.makeTestDataUserManufacturerParty();

    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  
  
  //@ Test
  public void testDeleteSystemUserByID() {
    try {
      System.out.println("--------------------------------------------------------------------");

      SystemAdminServiceImpl service = new SystemAdminServiceImpl();
      String emailAddress = "babak@noranj.com";
      UserDTO bizDocs = service.getUser(emailAddress); //FIXME the state is hard coded.
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
