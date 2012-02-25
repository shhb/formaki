package org.noranj.formak;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
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
import org.noranj.formak.server.domain.core.SystemAdmin;
import org.noranj.formak.server.domain.sa.SystemClientParty;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.service.SystemAdminServiceImpl;
import org.noranj.formak.shared.dto.SystemAdminDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.exception.NotFoundException;
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
      
      String emailAddress = "";
      
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

      makeTestDataUser(PartyRoleType.Buyer, new SystemClientParty(name, logoURI, activityType, roles));
      makeTestDataUser(PartyRoleType.Buyer, 10);

    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  private void makeTestDataDocument(DocumentType docType, int numberOfDocsToBeCreated) {
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    String id;

    for (int i=0; i<numberOfDocsToBeCreated; ++i) {
      id = service.createDocument(docType);
      System.out.printf("[%s] document is created and its id is [%s]\r\n", docType, id);
    } //for
  }
  
  
  @Test
  public void testGetSystemAdmins() {
    try {
      System.out.println("--------------------------------------------------------------------");
      
      DocumentStateType docState = DocumentStateType.Draft;
      
      printSystemAdmins(DocumentType.PurchaseOrder, docState);
      
      printSystemAdmins(DocumentType.Item, docState);
      
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  /**
   * 
   * @param docType
   * @param docStateType
   * @throws Exception
   */
  private void printSystemAdmins(DocumentType docType, DocumentStateType docStateType) throws Exception {
    
    SystemAdminServiceImpl service = new SystemAdminServiceImpl();

    List<SystemAdminDTO> docList = service.getSystemAdmins(docType, docStateType);
    
    if(docList == null)
      throw new Exception (String.format("There was no [%s] document in data store. The returned list is null", docType));
    
    int i=0;

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
    for(SystemAdminDTO bizDoc : docList) {
      os.writeObject(bizDoc);
      System.out.printf("bizDoc[%d] id[%s] Name is [%s]\r\n", ++i, bizDoc.getId(), bizDoc.getReceiverParty().getName()); //bizDoc.getReceiverParty().getName());
    }
    os.close();
    
    byte[] buf = byteArrayOutputStream.toByteArray();

    System.out.println("##########################################"+docType.toString()+"############################################");
    
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
    ObjectInputStream is = new ObjectInputStream(byteArrayInputStream);
    SystemAdminDTO bizDoc;
    while (is.available()>0) {
      bizDoc = (SystemAdminDTO) is.readObject();
      System.out.printf("bizDoc[%d] id[%s] Name is [%s]\r\n", ++i, bizDoc.getId(), bizDoc.getReceiverParty().getName()); //bizDoc.getReceiverParty().getName());
    }
    os.close();
    
  }
  
  @Test
  public void testDeleteSystemAdminByID() {
    try {
      System.out.println("--------------------------------------------------------------------");

      SystemAdminServiceImpl service = new SystemAdminServiceImpl();
      List<SystemAdminDTO> bizDocs = service.getSystemAdmins(DocumentType.PurchaseOrder, DocumentStateType.Draft); //FIXME the state is hard coded.
      if (bizDocs.size()==0) {
        fail("There is no PO to delete!!!");
      }
      Random rnd = new Random(0);
      int index = rnd.nextInt(bizDocs.size());
      
      SystemAdminDTO bizDocumentDTO = bizDocs.get(index);
      System.out.println("Going to delete this PO : \r\n**** " + bizDocumentDTO.toString());
      service.deleteDocument(DocumentType.PurchaseOrder, bizDocumentDTO.getId());
      
      // Test if they are really deleted!!!!!
      try {
        PurchaseOrderDTO po = service.getPurchaseOrder(bizDocumentDTO.getId());
        fail("PurchaseOrder is not deleted.");
      } catch (JDOObjectNotFoundException ex) {
        // do nothing because it suppose to throw this exception. 
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }
  
  @Test
  public void testGetPurchaseOrder() {
    fail("has not been implemented!!");
  } 

  @Test
  public void testGetProduct() {
    fail("has not been implemented!!");
  } 

  @Test
  public void testDeleteSystemAdmins() {
    fail("has not been implemented!!");
    
    //List<SystemAdminDTO> deleteSystemAdmins(DocumentType documentType, List<String> ids/*List<Long> ids*/); // throws ServiceCallFailed;
  } 
}
