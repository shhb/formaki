package org.noranj.formak.client.service;



import java.util.List;
import java.util.Set;

import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
import org.noranj.formak.shared.dto.XBusinessDocumentDTOX;
import org.noranj.formak.shared.exception.NotFoundException;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * 
 * @changes
 * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
 */
@RemoteServiceRelativePath("businessDocument")
public interface BusinessDocumentService extends RemoteService {
  
    Set<DocumentType> getListOfDocumentsCanBeCreated(PartyRoleType userPartyRole); // throws ServiceCallFailed;

    DocumentType[] getListOfAllDocuments(PartyRoleType userPartyRole); // throws ServiceCallFailed;
	  
    //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
    //Long createDocument(DocumentType documentType); // throws ServiceCallFailed;
    String createDocument(DocumentType documentType); // throws ServiceCallFailed;
    String insertDocument(DocumentType documentType,PurchaseOrderDTO purchaseOrderDTO,List<PurchaseOrderItemDTO> purchaseOrderItemDTO) ; 
    //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
    void deleteDocument(DocumentType documentType, String id/*long id*/) throws NotFoundException; //, ServiceCallFailed;

    //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
    List<BusinessDocumentDTO> deleteBusinessDocuments(DocumentType documentType, List<String> ids/*List<Long> ids*/); // throws ServiceCallFailed;
	  
    List<BusinessDocumentDTO> getBusinessDocuments(DocumentType documentType, DocumentStateType documentStateType); // throws ServiceCallFailed;
    

    //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
    PurchaseOrderDTO getPurchaseOrder(String id/*long id*/); // throws ServiceCallFailed;
	  //Product getProduct(long id); // throws ServiceCallFailed;

    //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
    /** @deprecated Added to get going with the sample and need to be revised. BA-2012-JAN-28  */ 
    XBusinessDocumentDTOX getBusinessDocument(DocumentType documentType, String id/*long id*/); // throws ServiceCallFailed;

    /** @deprecated Added to get going with the sample and need to be revised. BA-2012-JAN-28  */ 
    XBusinessDocumentDTOX updateBusinessDocument(XBusinessDocumentDTOX businessDocument);
    
}
