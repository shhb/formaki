package org.noranj.formak.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.noranj.formak.client.service.BusinessDocumentService;
import org.noranj.formak.server.BusinessDocumentHelper;
import org.noranj.formak.server.domain.association.PartyRoleDocument;
import org.noranj.formak.server.domain.biz.Catalog;
import org.noranj.formak.server.domain.biz.DispatchAdvice;
import org.noranj.formak.server.domain.biz.Invoice;
import org.noranj.formak.server.domain.biz.Item;
import org.noranj.formak.server.domain.biz.PurchaseOrder;
import org.noranj.formak.server.domain.biz.PurchaseOrderItem;
import org.noranj.formak.server.domain.biz.PurchaseOrderResponse;
import org.noranj.formak.server.domain.biz.Quotation;
import org.noranj.formak.server.domain.biz.RequestForQuotation;
import org.noranj.formak.server.domain.core.Address;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
import org.noranj.formak.shared.dto.XBusinessDocumentDTOX;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.LevelOfImportanceType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * 
 * @changes
 *  BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
 */
public class BusinessDocumentServiceImpl  extends RemoteServiceServlet /*- The API package 'datastore_v3' or call 'BeginTransaction()' was not found.*/ implements BusinessDocumentService {

  
  /**
   * 
   */
  private static final long serialVersionUID = 4800671361243161205L;

  
  /** 
   * There is a helper for each document type.
   * In order to save time, we keep them in hash and instead of creating a new one, we will reuse it.
   */
  @SuppressWarnings("rawtypes")
  private static Hashtable<DocumentType, BusinessDocumentHelper> businessDocumentHelpersHash = null;
  
  
  @SuppressWarnings("rawtypes")
  public BusinessDocumentServiceImpl(){

    if (this.businessDocumentHelpersHash == null) {
      intializeBusinessDocumentHelpersHash();
    }
    
  }
  
  /** it creates and cache the helpers. */
  private synchronized void intializeBusinessDocumentHelpersHash () {
    if (BusinessDocumentServiceImpl.businessDocumentHelpersHash == null) {
      Hashtable<DocumentType, BusinessDocumentHelper> bizDocHelpersHash = null; 
      bizDocHelpersHash = new Hashtable<DocumentType, BusinessDocumentHelper> ();
      bizDocHelpersHash.put(DocumentType.PurchaseOrder, new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class));
      bizDocHelpersHash.put(DocumentType.Item, new BusinessDocumentHelper<Item>(JDOPMFactory.getTxOptional(), Item.class));
      bizDocHelpersHash.put(DocumentType.Catalog, new BusinessDocumentHelper<Catalog>(JDOPMFactory.getTxOptional(), Catalog.class));
      bizDocHelpersHash.put(DocumentType.DispatchAdvice, new BusinessDocumentHelper<DispatchAdvice>(JDOPMFactory.getTxOptional(), DispatchAdvice.class));
      bizDocHelpersHash.put(DocumentType.Invoice, new BusinessDocumentHelper<Invoice>(JDOPMFactory.getTxOptional(), Invoice.class));
      bizDocHelpersHash.put(DocumentType.PurchaseOrderResponse, new BusinessDocumentHelper<PurchaseOrderResponse>(JDOPMFactory.getTxOptional(), PurchaseOrderResponse.class));
      bizDocHelpersHash.put(DocumentType.Quotation, new BusinessDocumentHelper<Quotation>(JDOPMFactory.getTxOptional(), Quotation.class));
      bizDocHelpersHash.put(DocumentType.RequestForQuotation, new BusinessDocumentHelper<RequestForQuotation>(JDOPMFactory.getTxOptional(), RequestForQuotation.class));
      BusinessDocumentServiceImpl.businessDocumentHelpersHash = bizDocHelpersHash;
    } 
      
  }
  /**
   * @param userPartyRole stores the role of the user's party.
   */
  @Override
  public Set<DocumentType> getListOfDocumentsCanBeCreated( PartyRoleType userPartyRole) {
    PartyRoleDocument partyRoleDocument = new PartyRoleDocument();
    return(partyRoleDocument.getDocumentsCanBeCreated(userPartyRole));
  }

  /**
   * @param userPartyRole stores the role of the user's party.
   */
  @Override
  //BA-2012-FEB-03
  //public Set<DocumentType> getListOfAllDocuments( PartyRoleType userPartyRole) {
  public DocumentType[] getListOfAllDocuments( PartyRoleType userPartyRole) {
    PartyRoleDocument partyRoleDocument = new PartyRoleDocument();
    return(partyRoleDocument.getAllDocuments(userPartyRole));
  }

  //FIXME  SA:2012-Mar-01 Check it please as logical matter . 
  
  
   /* 1- PurchaseOrderDTO tells you the type of document is PurchaseOrder so what is the point of passing documentType parameter!!!
    * 2- PurchaseOrdeDTO has a list of POItems. The items must be added to that object (PO). SO there is no need to pass them as a separate parameters.
  public String insertDocument(DocumentType documentType,PurchaseOrderDTO purchaseOrderDTO,List<PurchaseOrderItemDTO> purchaseOrderItemDTO) {
  so the method should be 

  public String insertDocument(PurchaseOrderDTO purchaseOrderDTO) {
  
  Body of the method:
  1- The only document that the method can process is PurchaseOrder so there is no need to have a switch!!!

  assert (documentType!=null) : "documentType can not be null.";
  switch (documentType) {
      case PurchaseOrder: 
        {
        PurchaseOrder po = new PurchaseOrder();
          po.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
          po.setName("Added New PO at " + System.currentTimeMillis());
          po.setBizDocumentNumber(purchaseOrderDTO.getBizDocumentNumber());
          po.setState(DocumentStateType.Draft);
          po.setImportantDate(System.currentTimeMillis());
          po.setImportantDateDescription("Created At");
          po.setMonetory(Long.parseLong(purchaseOrderDTO.getMonetory()));
          po.setNote(purchaseOrderDTO.getNote());
          //
          po.setBillTo(new Address(purchaseOrderDTO.getBillTo().getStreetAddress(),purchaseOrderDTO.getBillTo().getCity(),purchaseOrderDTO.getBillTo().getStateOrProvince(),purchaseOrderDTO.getBillTo().getPostalCode()));
          po.setShipTo(new Address(purchaseOrderDTO.getShipTo().getStreetAddress(),purchaseOrderDTO.getShipTo().getCity(),purchaseOrderDTO.getShipTo().getStateOrProvince(),purchaseOrderDTO.getShipTo().getPostalCode()));
          for( PurchaseOrderItemDTO row : purchaseOrderItemDTO){
            po.addPurchaseOrderItem(new PurchaseOrderItem(0,row.getItemID(),row.getGTIN(),row.getBuyerItemID(),row.getDescription(),row.getUom(),row.getQuantity(),row.getPrice()));
          }
    
          BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
          businessDocumentHelper.storeEntity(po);
          return(po.getId());
        }
  }
  return "";
  }
  * must be something like this.
  */
  //TODO SA take a look at this codes
  ///XXX TEST it has not been tested.
  public String insertDocument(PurchaseOrderDTO purchaseOrderDTO) {
    assert (purchaseOrderDTO!=null) : "purchaseOrderDTO can not be null.";

    PurchaseOrder po = new PurchaseOrder(purchaseOrderDTO);

    BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(DocumentType.PurchaseOrder);
    businessDocumentHelper.storeEntity(po); //FIXME BA:12-MAR-01 what happens if there is an error.
    return(po.getId());
    
  }
  
  /**
   * 
   */
  @Override
  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
  //public Long createDocument(DocumentType documentType) {
  public String createDocument(DocumentType documentType) {
    
    assert (documentType!=null) : "documentType can not be null.";
    
    switch (documentType) {
      case PurchaseOrder: 
        {
        //FIXME
        PurchaseOrder po = new PurchaseOrder();
        po.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
        po.setName("Added New PO at " + System.currentTimeMillis());
        po.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
        po.setState(DocumentStateType.Draft);
        po.setImportantDate(System.currentTimeMillis());
        po.setImportantDateDescription("Created At");
        po.setMonetory(System.currentTimeMillis()%100);
        po.setNote("There is no where to run.");
        //BA-2012-FEB-10 Added ONLY PO attributes
        po.setBillTo(new Address(System.currentTimeMillis()%1000 + " Strt", "Ctyi", "AZ", String.valueOf(System.currentTimeMillis()%100000)));
        po.setShipTo(new Address((System.currentTimeMillis()*3%1000) + " Strt", "Ctyi", "AZ", String.valueOf(System.currentTimeMillis()*3%100000)));
        po.addPurchaseOrderItem(new PurchaseOrderItem(/*po,*/ 0 /* must be 0 to be set automatically */, System.currentTimeMillis()%10000000, "GTIN#" +System.currentTimeMillis()%10000000000L, "BY#" + System.currentTimeMillis()%10000000, "item " + System.currentTimeMillis()%10000000, "EA", 10, 201990));
        po.addPurchaseOrderItem(new PurchaseOrderItem(/*po,*/ 0 /* must be 0 to be set automatically */, System.currentTimeMillis()%10000000, "GTIN#" +System.currentTimeMillis()%10000000000L, "BY#" + System.currentTimeMillis()%10000000, "item " + System.currentTimeMillis()%10000000, "EA", 11, 211990));
        po.addPurchaseOrderItem(new PurchaseOrderItem(/*po,*/ 0 /* must be 0 to be set automatically */, System.currentTimeMillis()%10000000, "GTIN#" +System.currentTimeMillis()%10000000000L, "BY#" + System.currentTimeMillis()%10000000, "item " + System.currentTimeMillis()%10000000, "CA", 12, 221990));
        po.addPurchaseOrderItem(new PurchaseOrderItem(/*po,*/ 0 /* must be 0 to be set automatically */, System.currentTimeMillis()%10000000, "GTIN#" +System.currentTimeMillis()%10000000000L, "BY#" + System.currentTimeMillis()%10000000, "item " + System.currentTimeMillis()%10000000, "CA", 13, 231990));
        po.addPurchaseOrderItem(new PurchaseOrderItem(/*po,*/ 0 /* must be 0 to be set automatically */, System.currentTimeMillis()%10000000, "GTIN#" +System.currentTimeMillis()%10000000000L, "BY#" + System.currentTimeMillis()%10000000, "item " + System.currentTimeMillis()%10000000, "CA", 14, 241990));
        
        //BA-2012-JAN-29
        //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
        BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
        businessDocumentHelper.storeEntity(po);
        return(po.getId());
        }
      case Item: //BA-2012-FEB-18 Product is renamed to Item.
      {
        //FIXME
        Item product = new Item();
        product.setName("Added New Item at " + System.currentTimeMillis());
        product.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.Less:LevelOfImportanceType.JustFYI);
        product.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
        product.setState(DocumentStateType.Draft);
        product.setImportantDate(System.currentTimeMillis());
        product.setImportantDateDescription("Created At");
        product.setMonetory(System.currentTimeMillis()%100);
        product.setNote("There is no where to run.");
        //BusinessDocumentHelper<Product> businessDocumentHelper = new BusinessDocumentHelper<Product>(JDOPMFactory.getTxOptional(), Product.class);
        BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
        businessDocumentHelper.storeEntity(product);
        return(product.getId());
      } 
      case RequestForQuotation: 
      {
      //FIXME
      RequestForQuotation requestForQuotation = new RequestForQuotation();
      requestForQuotation.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
      requestForQuotation.setName("Added New RequestForQuotation at " + System.currentTimeMillis());
      requestForQuotation.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
      requestForQuotation.setState(DocumentStateType.Draft);
      requestForQuotation.setImportantDate(System.currentTimeMillis());
      requestForQuotation.setImportantDateDescription("Created At");
      requestForQuotation.setMonetory(System.currentTimeMillis()%100);
      requestForQuotation.setNote("There is no where to run.");
      //BA-2012-JAN-29
      //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
      BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
      businessDocumentHelper.storeEntity(requestForQuotation);
      return(requestForQuotation.getId());
      }
      case Quotation: 
      {
      //FIXME
      Quotation quotation = new Quotation();
      quotation.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
      quotation.setName("Added New Quotation at " + System.currentTimeMillis());
      quotation.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
      quotation.setState(DocumentStateType.Draft);
      quotation.setImportantDate(System.currentTimeMillis());
      quotation.setImportantDateDescription("Created At");
      quotation.setMonetory(System.currentTimeMillis()%100);
      quotation.setNote("There is no where to run.");
      //BA-2012-JAN-29
      //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
      BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
      businessDocumentHelper.storeEntity(quotation);
      return(quotation.getId());
      }
      case PurchaseOrderResponse: 
      {
      //FIXME
      PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse ();
      purchaseOrderResponse.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
      purchaseOrderResponse.setName("Added New POResponse at " + System.currentTimeMillis());
      purchaseOrderResponse.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
      purchaseOrderResponse.setState(DocumentStateType.Draft);
      purchaseOrderResponse.setImportantDate(System.currentTimeMillis());
      purchaseOrderResponse.setImportantDateDescription("Created At");
      purchaseOrderResponse.setMonetory(System.currentTimeMillis()%100);
      purchaseOrderResponse.setNote("There is no where to run.");
      //BA-2012-JAN-29
      //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
      BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
      businessDocumentHelper.storeEntity(purchaseOrderResponse);
      return(purchaseOrderResponse.getId());
      }
      case Catalog: 
      {
      //FIXME
      Catalog catalog = new Catalog();
      catalog.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
      catalog.setName("Added New Catalog at " + System.currentTimeMillis());
      catalog.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
      catalog.setState(DocumentStateType.Draft);
      catalog.setImportantDate(System.currentTimeMillis());
      catalog.setImportantDateDescription("Created At");
      catalog.setMonetory(System.currentTimeMillis()%100);
      catalog.setNote("There is no where to run.");
      //BA-2012-JAN-29
      //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
      BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
      businessDocumentHelper.storeEntity(catalog);
      return(catalog.getId());
      }
      case DispatchAdvice: 
      {
      //FIXME
      DispatchAdvice dispatchAdvice = new DispatchAdvice();
      dispatchAdvice.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
      dispatchAdvice.setName("Added New DispatchAdvice at " + System.currentTimeMillis());
      dispatchAdvice.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
      dispatchAdvice.setState(DocumentStateType.Draft);
      dispatchAdvice.setImportantDate(System.currentTimeMillis());
      dispatchAdvice.setImportantDateDescription("Created At");
      dispatchAdvice.setMonetory(System.currentTimeMillis()%100);
      dispatchAdvice.setNote("There is no where to run.");
      //BA-2012-JAN-29
      //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
      BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
      businessDocumentHelper.storeEntity(dispatchAdvice);
      return(dispatchAdvice.getId());
      }
      case Invoice: 
      {
      //FIXME
      Invoice invoice = new Invoice();
      invoice.setLevelOfImportance((System.currentTimeMillis()/3==0)?LevelOfImportanceType.High:LevelOfImportanceType.Junk);
      invoice.setName("Added New Invoice at " + System.currentTimeMillis());
      invoice.setBizDocumentNumber(String.valueOf(System.currentTimeMillis()%100000));
      invoice.setState(DocumentStateType.Draft);
      invoice.setImportantDate(System.currentTimeMillis());
      invoice.setImportantDateDescription("Created At");
      invoice.setMonetory(System.currentTimeMillis()%100);
      invoice.setNote("There is no where to run.");
      //BA-2012-JAN-29
      //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
      BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
      businessDocumentHelper.storeEntity(invoice);
      return(invoice.getId());
      }
      
    } // switch
    
    return ""; //FIXME it should be an exception and not ""
  }

  @Override
  public List<BusinessDocumentDTO> getBusinessDocuments(DocumentType documentType,
                                                            DocumentStateType documentStateType) {
    
    if (documentType.equals(DocumentType.Unknown)) {
        System.out.println("*** ERROR: getBusinessDocuments can not accept an unknown DocumentType as input parameter.") ;
        return(new ArrayList<BusinessDocumentDTO>()); //FIXME it should be handled better
    }
    
    boolean done = false;
    List<BusinessDocumentDTO> bizDocDetailsList = null;
    Collection<BusinessDocument> bizDocCollection = null;
    //BA-2012-JAN-29 generalized
    //BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
    BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
    
    while(bizDocDetailsList==null) {

      //FIXME BA - it should be reviewed and fixed (for test only)
      bizDocCollection = businessDocumentHelper.getEntities(documentStateType);

      //FIXME this is only for test
      // IF there is no test data, add some 
      if (bizDocCollection.isEmpty()) {
        makeTestDataDocument(documentType, 10); 
      } 
      else {
        bizDocDetailsList = new ArrayList<BusinessDocumentDTO>();
        for(BusinessDocument bizDoc : bizDocCollection) {
          bizDocDetailsList.add(bizDoc.getBusinessDocumentDTO()); //BA-2012-JAN-31
          /*bizDocDetailsList.add(new BusinessDocumentDetailsDTO(bizDoc.getId(), bizDoc.getLevelOfImportance(), "draft" / * bizDoc.getState() * /, 
                                    / * new PartyDTO(1001, "originator")* / "originator", /*new PartyDTO(2001, "receiver")* / "receiver", 
                                    bizDoc.getBizDocumentNumber(), bizDoc.getMonetory(), 
                                    bizDoc.getImportantDate(), bizDoc.getNote()
                                    ));*/
          
        }
      } // else
      
    } //while
    return(bizDocDetailsList);
  }


  @Override
  //public PurchaseOrderDTO getPurchaseOrder(long id) {
  public PurchaseOrderDTO getPurchaseOrder(String id) {
    BusinessDocumentHelper<PurchaseOrder> businessDocumentHelper = new BusinessDocumentHelper<PurchaseOrder>(JDOPMFactory.getTxOptional(), PurchaseOrder.class);
    //BA-2012-FEB-09
    //return(businessDocumentHelper.getEntityById(id););
    PurchaseOrder po = businessDocumentHelper.getEntityById(id,PurchaseOrder.C_ITEMS_FETCH_GROUP_NAME);
    return(po.getPurchaseOrderDTO());
  }

  /*
  @Override
  public Product getProduct(long id) {
    BusinessDocumentHelper<Product> businessDocumentHelper = new BusinessDocumentHelper<Product>(JDOPMFactory.getTxOptional(), Product.class);
    return(businessDocumentHelper.getEntityById(id));
  } 
  */

  @Override
  //public void deleteDocument(DocumentType documentType, long id) throws NotFoundException {
  public void deleteDocument(DocumentType documentType, String id) throws NotFoundException {
    BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
    businessDocumentHelper.deleteEntity(id);
  }

  
  /**
   * @deprecated IT IS A QUICK IMPLEMENTATION OF THE METHOD. The return data must be reviewed!! do we need that?s 
   */
  @Override
  //public List<BusinessDocumentDTO> deleteBusinessDocuments( DocumentType documentType, List<Long> ids) { //BA-2012-JAN-31 List<String> ids
  public List<BusinessDocumentDTO> deleteBusinessDocuments( DocumentType documentType, List<String> ids) { //BA-2012-JAN-31 List<String> ids
    
    BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
    
    businessDocumentHelper.deleteEntities(ids);

    //FIXME what is the stateType >!?!?! it should be passed with parameters too.
    return(getBusinessDocuments(documentType, DocumentStateType.Unknwon));
    
  }

  /**
   * It returns the business document of type documentType.
   * Each document is stored in its own table (group in data source). 
   * 
   * @param documentType
   * @param id uniquely identifies a document of type documentType.
   */
  @Override
  //public XBusinessDocumentDTOX getBusinessDocument(DocumentType documentType, long id) {
  public XBusinessDocumentDTOX getBusinessDocument(DocumentType documentType, String id) {
    BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(documentType);
    BusinessDocument bizDoc = (BusinessDocument) businessDocumentHelper.getEntityById(id);
    return(new XBusinessDocumentDTOX(bizDoc.getId().toString(), bizDoc.getName(), bizDoc.getNote(), bizDoc.getBizDocumentNumber(), DocumentType.PurchaseOrder.toString()));
  }

  /**
   * 
   * @changes 
   * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
   */
  @Override
  public XBusinessDocumentDTOX updateBusinessDocument(XBusinessDocumentDTOX businessDocument) {
    
    BusinessDocumentHelper businessDocumentHelper = BusinessDocumentServiceImpl.businessDocumentHelpersHash.get(businessDocument.getDocumentType());
    assert(businessDocumentHelper !=null) : "DocumentType is invalid ["+businessDocument.getDocumentType()+"]" ;
    //BusinessDocument bizDoc = (BusinessDocument ) businessDocumentHelper.getEntityById(new Long(businessDocument.getId()));
    BusinessDocument bizDoc = (BusinessDocument ) businessDocumentHelper.getEntityById(businessDocument.getId());
    
    bizDoc.setName(businessDocument.getBusinessDocumentName());
    bizDoc.setNote(businessDocument.getDescription());
    bizDoc.setBizDocumentNumber(businessDocument.getBusinessDocumentNumber());
    businessDocumentHelper.storeEntity(bizDoc);
    
    //FIXME read it again although it may not be needed because storeEnity update it or we just saved the value>?!>!>!>
    //bizDoc = (BusinessDocument ) businessDocumentHelper.getEntityById(new Long(businessDocument.getId()));
    bizDoc = (BusinessDocument ) businessDocumentHelper.getEntityById(businessDocument.getId());
    return(new XBusinessDocumentDTOX(bizDoc.getId().toString(), bizDoc.getName(), bizDoc.getNote(), bizDoc.getBizDocumentNumber(), businessDocument.getDocumentType()));

  }
  
  //BA-2012-JAN-29 Added to create dummy test data.
  private void makeTestDataDocument(DocumentType docType, int numberOfDocsToBeCreated) {
    //BusinessDocumentServiceImpl service = new BusinessDocumentServiceImpl();
    //long id;
    String id;
    
    for (int i=0; i<numberOfDocsToBeCreated; ++i) {
      //id = service.createDocument(docType);
      id = createDocument(docType);
      System.out.printf("[%s] document is created and its id is [%s]\r\n", docType, id);
    } //for
  }

  
}
