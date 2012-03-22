package org.noranj.formak.server.domain.association;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

import org.noranj.formak.server.service.servlet.MigrationServlet;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;


/**
 * This class provides the list of documents that a user can access.
 * The type and kind of access is defined based on the role that the party of user plays.
 * For example, a buyer party can send certain documents that may or may not be different from a buyer party.
 * 
 * 
 * It can be later persisted in database but for now I don't see any reason for store it in data store.
 * The data volume is so low and can be hard coded for now.
 * 
 * This module is designed to access the data using one RoleParty at a time. If a party plays two or more roles at the same time,
 * the caller method needs to decide which role is going to be passed. 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012MAR21
 * @since 0.1
 */
public class PartyRoleDocument implements Serializable {

  protected static Logger logger = Logger.getLogger(PartyRoleDocument.class.getName());

  private static final long serialVersionUID = -4332548052598644936L;

 public PartyRoleDocument(){}
  /** stores the list of documents that this role party can send to the other parties. 
   * this is the general list and might be customized later for individual parties. */
  private static EnumMap<PartyRoleType, EnumSet<DocumentType>> partyRoleDocumentSendable = new EnumMap<PartyRoleType, EnumSet<DocumentType>>(PartyRoleType.class);

  /** stores the list of documents that this role party can edit it no matter created by itself or by another party. 
   * this is the general list and might be customized later for individual parties. */
  private static EnumMap<PartyRoleType, EnumSet<DocumentType>> partyRoleDocumentEditable = new EnumMap<PartyRoleType, EnumSet<DocumentType>>(PartyRoleType.class);

  /** stores the list of documents that this role party can receive from the other parties. 
   * this is the general list and might be customized later for individual parties. */
  private static EnumMap<PartyRoleType, EnumSet<DocumentType>> partyRoleDocumentReceivable = new EnumMap<PartyRoleType, EnumSet<DocumentType>>(PartyRoleType.class);

  /** contains the both list of send and receive documents. */
  //BA-2012-FEB-03
  //private static EnumMap<PartyRoleType, EnumSet<DocumentType>> partyRoleDocuments = new EnumMap<PartyRoleType, EnumSet<DocumentType>>(PartyRoleType.class);
  private static EnumMap<PartyRoleType, DocumentType[]> partyRoleDocuments = new EnumMap<PartyRoleType, DocumentType[]>(PartyRoleType.class);

  // Initialize prototype deck
  static {
    
    // FIXME this should be set in Spring or being loaded from data store based on type of application/product.
    // user must be able to buy and add more documents to their business.
    // this is part of partner setup.
    partyRoleDocumentSendable.put(PartyRoleType.Buyer, EnumSet.of(DocumentType.RequestForQuotation, DocumentType.PurchaseOrder));
    partyRoleDocumentSendable.put(PartyRoleType.Seller, EnumSet.of(DocumentType.Quotation, DocumentType.PurchaseOrderResponse, DocumentType.Invoice, DocumentType.DispatchAdvice));

    partyRoleDocumentEditable.put(PartyRoleType.Buyer, EnumSet.of(DocumentType.RequestForQuotation, DocumentType.PurchaseOrder));
    partyRoleDocumentEditable.put(PartyRoleType.Seller, EnumSet.of(DocumentType.Quotation, DocumentType.PurchaseOrderResponse, DocumentType.Invoice, DocumentType.DispatchAdvice));

    partyRoleDocumentReceivable.put(PartyRoleType.Buyer, EnumSet.of(DocumentType.Quotation, DocumentType.PurchaseOrderResponse, DocumentType.Invoice, DocumentType.DispatchAdvice));
    partyRoleDocumentReceivable.put(PartyRoleType.Seller, EnumSet.of(DocumentType.RequestForQuotation, DocumentType.PurchaseOrder));
    
    //BA-2012-FEB-03 replaced EnumSet with Array of DocumentType 
    partyRoleDocuments.put(PartyRoleType.Buyer, new DocumentType[] {DocumentType.RequestForQuotation, DocumentType.PurchaseOrder, DocumentType.Quotation, DocumentType.PurchaseOrderResponse, DocumentType.Invoice, DocumentType.DispatchAdvice});
    //BA-2012-FEB-03 
    //partyRoleDocuments.put(PartyRoleType.Seller, EnumSet.of(DocumentType.RequestForQuotation, DocumentType.PurchaseOrder, DocumentType.Quotation, DocumentType.PurchaseOrderResponse, DocumentType.Invoice, DocumentType.DispatchAdvice));
    partyRoleDocuments.put(PartyRoleType.Seller, new DocumentType[] {DocumentType.RequestForQuotation, DocumentType.PurchaseOrder, DocumentType.Quotation, DocumentType.PurchaseOrderResponse, DocumentType.Invoice, DocumentType.DispatchAdvice});
    
  }

  //BA-2012-FEB-03
  //public Set<DocumentType> getAllDocuments(PartyRoleType partyRole) {
  public DocumentType[] getAllDocuments(PartyRoleType partyRole) {
    return(partyRoleDocuments.get(partyRole));
  }
  
  public Set<DocumentType> getDocumentsCanBeSent(PartyRoleType partyRole) {
    return(partyRoleDocumentSendable.get(partyRole));
  }
  
  public Set<DocumentType> getDocumentsCanBeReceived(PartyRoleType partyRole) {
    return(partyRoleDocumentReceivable.get(partyRole));
  }
  
  public Set<DocumentType> getDocumentsCanBeCreated(PartyRoleType partyRole) {
    return(getDocumentsCanBeSent(partyRole)); // all the documents that a buyer can sent, can be created by buyer. (for now) 
  }
  
  public Set<DocumentType> getDocumentsCanBeEdited(PartyRoleType partyRole) {
    return(partyRoleDocumentEditable.get(partyRole));
  }
  

}
