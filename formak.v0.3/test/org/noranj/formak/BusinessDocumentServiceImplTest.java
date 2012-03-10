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
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.server.service.BusinessDocumentServiceImpl;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class BusinessDocumentServiceImplTest {

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
  public void testGetListOfDocumentsCanBeCreated() {
    try {
      System.out.println("--------------------------------------------------------------------");
      
      PartyRoleType userPartyRole = PartyRoleType.Buyer;
      
      BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();

      Set<DocumentType> docCanBeCreatedList = service.getListOfDocumentsCanBeCreated(userPartyRole);
      
      for(DocumentType docType : docCanBeCreatedList) {
        System.out.printf("docType is [%s]\r\n", docType);
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  @Test
  public void testCreateDocument() {
    System.out.println("--------------------------------------------------------------------");
    try {

      makeTestDataDocument(DocumentType.PurchaseOrder, 10);
      makeTestDataDocument(DocumentType.Item, 10);

    } catch (Exception ex) {
      ex.printStackTrace();
      fail("AN exception happened " + ex.getMessage());
    }
    System.out.println("=====================================================================");
  }

  private void makeTestDataDocument(DocumentType docType, int numberOfDocsToBeCreated) {
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();
    String id;

    for (int i=0; i<numberOfDocsToBeCreated; ++i) {
      id = service.createDocument(docType);
      System.out.printf("[%s] document is created and its id is [%s]\r\n", docType, id);
    } //for
  }
  
  
  @Test
  public void testGetBusinessDocuments() {
    try {
      System.out.println("--------------------------------------------------------------------");
      
      DocumentStateType docState = DocumentStateType.Draft;
      
      printBusinessDocuments(DocumentType.PurchaseOrder, docState);
      
      printBusinessDocuments(DocumentType.Item, docState);
      
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
  private void printBusinessDocuments(DocumentType docType, DocumentStateType docStateType) throws Exception {
    
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();

    List<BusinessDocumentDTO> docList = service.getBusinessDocuments(docType, docStateType);
    
    if(docList == null)
      throw new Exception (String.format("There was no [%s] document in data store. The returned list is null", docType));
    
    int i=0;

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(byteArrayOutputStream);
    for(BusinessDocumentDTO bizDoc : docList) {
      os.writeObject(bizDoc);
      System.out.printf("bizDoc[%d] id[%s] Name is [%s]\r\n", ++i, bizDoc.getId(), bizDoc.getReceiverParty().getName()); //bizDoc.getReceiverParty().getName());
    }
    os.close();
    
    byte[] buf = byteArrayOutputStream.toByteArray();

    System.out.println("##########################################"+docType.toString()+"############################################");
    
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
    ObjectInputStream is = new ObjectInputStream(byteArrayInputStream);
    BusinessDocumentDTO bizDoc;
    while (is.available()>0) {
      bizDoc = (BusinessDocumentDTO) is.readObject();
      System.out.printf("bizDoc[%d] id[%s] Name is [%s]\r\n", ++i, bizDoc.getId(), bizDoc.getReceiverParty().getName()); //bizDoc.getReceiverParty().getName());
    }
    os.close();
    
  }
  
  @Test
  public void testDeleteBusinessDocumentByID() {
    try {
      System.out.println("--------------------------------------------------------------------");

      BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();
      List<BusinessDocumentDTO> bizDocs = service.getBusinessDocuments(DocumentType.PurchaseOrder, DocumentStateType.Draft); //FIXME the state is hard coded.
      if (bizDocs.size()==0) {
        fail("There is no PO to delete!!!");
      }
      Random rnd = new Random(0);
      int index = rnd.nextInt(bizDocs.size());
      
      BusinessDocumentDTO bizDocumentDTO = bizDocs.get(index);
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
  public void testSaveDocumentPUrchaseOrder() {
  	
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();

    String id = getRandomBusinessDocumentId(DocumentType.PurchaseOrder);
  	PurchaseOrderDTO purchaseOrderDTO =service.getPurchaseOrder(id); 
  	purchaseOrderDTO.setNote(id);
  	service.saveDocument(purchaseOrderDTO);
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
  public void testDeleteBusinessDocuments() {
    fail("has not been implemented!!");
    
    //List<BusinessDocumentDTO> deleteBusinessDocuments(DocumentType documentType, List<String> ids/*List<Long> ids*/); // throws ServiceCallFailed;
  }
  
  /**
   * It finds a random document among the existing documents and return its Id.
   * @param documentType
   * @return the id of the selected document.
   */
  private String getRandomBusinessDocumentId(DocumentType documentType) {
  	
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();
    List<BusinessDocumentDTO> bizDocs = service.getBusinessDocuments(documentType, DocumentStateType.Draft); //FIXME the state is hard coded.
    if (bizDocs.size()==0) {
      fail("There is no "+documentType.codeToString()+" to delete!!!");
    }
    Random rnd = new Random(0);
    int index = rnd.nextInt(bizDocs.size());
    
    BusinessDocumentDTO bizDocumentDTO = bizDocs.get(index);
    return(bizDocumentDTO.getId());

  }
  
}
