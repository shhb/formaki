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
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
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
  public void testSaveDocumentPurchaseOrder() {
  	
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();


    String idx = getRandomBusinessDocumentId(DocumentType.PurchaseOrder);
  	PurchaseOrderDTO purchaseOrderDTO =service.getPurchaseOrder(idx); 

  	
    System.out.println("---------------------- STEP - 1 Change an attribute in entity ----------------");
  	purchaseOrderDTO.setNote("JJJ" + idx);
  	
    System.out.println("---------------------- STEP - 2 Change a value in PO Item ----------------");
    List<PurchaseOrderItemDTO> poItems = purchaseOrderDTO.getPurchaseOrderItems();
    int poItemChangedIndex=-1;
    if(poItems.size()>0) {
      poItemChangedIndex = getRandom(0, poItems.size());
      PurchaseOrderItemDTO poItemDTO = purchaseOrderDTO.getPurchaseOrderItems().get(poItemChangedIndex);
      poItemDTO.setGTIN("1234567890111");
      System.out.println(purchaseOrderDTO.toString());
    }
    else {
      System.out.println("THERE IS NO ITEM TO UPDATE!!!!");
    }
      
    System.out.println("---------------------- STEP - 3 Add a new PO Item ----------------");
    PurchaseOrderItemDTO newPOItemDTO = new PurchaseOrderItemDTO();
    newPOItemDTO.setId("0");
    newPOItemDTO.setGTIN("1234567890222");
    newPOItemDTO.setDescription("Added item in test");
    newPOItemDTO.setSequenceHolder(0);
    purchaseOrderDTO.addPurchaseOrderItem(newPOItemDTO);
    System.out.println(purchaseOrderDTO.toString());
    
    System.out.println("---------------------- STEP - 3+1 Delete a PO Item ----------------");
    
    poItems = purchaseOrderDTO.getPurchaseOrderItems();
    int poItemDeletedIndex=-1;
    if(poItems.size()>0) {
      do {
        poItemDeletedIndex = getRandom(0, poItems.size());
      } while(poItemDeletedIndex == poItemChangedIndex);
      poItems.remove(poItemDeletedIndex);
      System.out.println(purchaseOrderDTO.toString());
    }
    else {
      System.out.println("THERE IS NO ITEM TO DELETE!!!!");
    }
    
    
    System.out.println("---------------------- STEP - 4 Save PO and Items ----------------");
  	service.saveDocument(purchaseOrderDTO);
  	
  	
    System.out.println("---------------------- STEP - 5 verify Results ----------------");
  	
    PurchaseOrderDTO purchaseOrderReadAgainDTO =service.getPurchaseOrder(idx); 
    // Check the attribute has been updated.
    if(!purchaseOrderReadAgainDTO.getNote().equals(purchaseOrderDTO.getNote()))
      fail("the save failed the NOTE value has not changed!!! It should be["+purchaseOrderDTO.getNote()+"] but it is ["+purchaseOrderReadAgainDTO.getNote()+"]");

    // Check the attribute of an item has been updated
    if(purchaseOrderReadAgainDTO.getPurchaseOrderItems().size()>poItemChangedIndex && 
        purchaseOrderDTO.getPurchaseOrderItems().size()> poItemChangedIndex && 
        !purchaseOrderReadAgainDTO.getPurchaseOrderItems().get(poItemChangedIndex).getGTIN().equals(purchaseOrderDTO.getPurchaseOrderItems().get(poItemChangedIndex).getGTIN())
      )
      fail("the save failed the GTIN of item["+poItemChangedIndex+"] value has not changed!!! It should be["+purchaseOrderDTO.getPurchaseOrderItems().get(poItemChangedIndex).getGTIN()+"] but it is ["+
        purchaseOrderReadAgainDTO.getPurchaseOrderItems().get(poItemChangedIndex).getGTIN()+"]");
    
    // check if the new item has been added.
    boolean found = false;
    for(PurchaseOrderItemDTO poItemDTO : purchaseOrderReadAgainDTO.getPurchaseOrderItems()) {
      if (poItemDTO.getGTIN()!= null && poItemDTO.getGTIN().equals(newPOItemDTO.getGTIN())) {
        found = true;
        break;
      }
    }
    if(!found) {
      fail("The new item has not been added.");
    }
    
    System.out.println("=====================================================================");
  	
  }

  @Test
  public void testGetPurchaseOrder() {
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();
    System.out.println("--------------------------------------------------------------------");
    String id = getRandomBusinessDocumentId(DocumentType.PurchaseOrder);
    PurchaseOrderDTO purchaseOrderDTO =service.getPurchaseOrder(id); 
    System.out.println(purchaseOrderDTO.toString());
    System.out.println("=====================================================================");
  } 

  @Test
  public void testDeletePurchaseOrderItem() {
    BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();
    System.out.println("--------------------------------------------------------------------");
    String id = getRandomBusinessDocumentId(DocumentType.PurchaseOrder);
    PurchaseOrderDTO purchaseOrderDTO =service.getPurchaseOrder(id); 
    System.out.println("---------------------- STEP - 1 Before delete ----------------------");
    System.out.println(purchaseOrderDTO.toString());
    
    List<PurchaseOrderItemDTO> poItems = purchaseOrderDTO.getPurchaseOrderItems();
    if(poItems.size()>0) {
      int rndIndex = getRandom(0, poItems.size());
      purchaseOrderDTO.getPurchaseOrderItems().remove(rndIndex);
      System.out.println("---------------------- STEP - 2 After delete in DTO ----------------");
      System.out.println(purchaseOrderDTO.toString());
      service.saveDocument(purchaseOrderDTO);
    }
    else {
      System.out.println("THERE IS NO ITEM TO DELETE!!!!");
    }
      
    PurchaseOrderDTO purchaseOrderReadAgainDTO =service.getPurchaseOrder(id); 
      
    System.out.println("---------------------- STEP - 3 After saved and read fromdata store-");
    System.out.println(purchaseOrderReadAgainDTO.toString());
    System.out.println("=====================================================================");
  }
  
  //@ Test
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
    Random rnd = new Random(System.currentTimeMillis());
    int index = rnd.nextInt(bizDocs.size());
    
    BusinessDocumentDTO bizDocumentDTO = bizDocs.get(index);
    return(bizDocumentDTO.getId());

  }

  private int getRandom(int lowerBound, int upperBound) {
    Random rnd = new Random(System.currentTimeMillis());
    int index = rnd.nextInt(upperBound-lowerBound);
    return(index+lowerBound);
  }
  
}
