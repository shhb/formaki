package org.noranj.formak.client.service;



import java.util.List;
import java.util.Set;

import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
import org.noranj.formak.shared.dto.XBusinessDocumentDTOX;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * 
 * @changes
 * 
 * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
 */
public interface BusinessDocumentServiceAsync {
  
  public void getListOfDocumentsCanBeCreated(PartyRoleType userPartyRole,AsyncCallback<Set<DocumentType>> callback); //; // throws ServiceCallFailed;

  public void getListOfAllDocuments(PartyRoleType userPartyRole,AsyncCallback<DocumentType[]> callback); //; // throws ServiceCallFailed;
  
  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
  //public void createDocument(DocumentType documentType,AsyncCallback<Long> callback); // throws ServiceCallFailed;
  public void createDocument(DocumentType documentType,AsyncCallback<String> callback); // throws ServiceCallFailed;
  public void insertDocument(PurchaseOrderDTO purchaseOrderDTO, AsyncCallback<String> callback); //BA:12-MAR-01
  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
  //public void deleteDocument(DocumentType documentType, long id, AsyncCallback<Void> callback); // throws ServiceCallFailed;
  public void deleteDocument(DocumentType documentType, String id, AsyncCallback<Void> callback); // throws ServiceCallFailed;
  
  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
  //public void deleteBusinessDocuments(DocumentType documentType, List<Long> ids, AsyncCallback<List<BusinessDocumentDTO>> callback); // throws ServiceCallFailed;
  public void deleteBusinessDocuments(DocumentType documentType, List<String> ids, AsyncCallback<List<BusinessDocumentDTO>> callback); // throws ServiceCallFailed;
  
  public void getBusinessDocuments(DocumentType documentType, DocumentStateType documentStateType, AsyncCallback<List<BusinessDocumentDTO>> callback); // throws ServiceCallFailed;

  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
  //public void getPurchaseOrder(long id,AsyncCallback<PurchaseOrderDTO> callback); // throws ServiceCallFailed;
  public void getPurchaseOrder(String id,AsyncCallback<PurchaseOrderDTO> callback); // throws ServiceCallFailed;
  //public void getProduct(long id,AsyncCallback<Product> callback); // throws ServiceCallFailed;

  //BA-2012-JAN-28 Added to get going with the sample and need to be revised.
  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
  //public void getBusinessDocument(DocumentType documentType, long id, AsyncCallback<XBusinessDocumentDTOX> callback); // throws ServiceCallFailed;
  public void getBusinessDocument(DocumentType documentType, String id, AsyncCallback<XBusinessDocumentDTOX> callback); // throws ServiceCallFailed;

  //BA-2012-JAN-28 Added to get going with the sample and need to be revised.
  public void updateBusinessDocument(XBusinessDocumentDTOX businessDocument, AsyncCallback<XBusinessDocumentDTOX> callback); // throws ServiceCallFailed;


}
