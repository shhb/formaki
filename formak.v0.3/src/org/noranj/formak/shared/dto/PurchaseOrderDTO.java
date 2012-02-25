package org.noranj.formak.shared.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.LevelOfImportanceType;

/**
 * 
 * This is the list of attributes in U B L.
 * 
 * "ublExtensions",
 * "ublVersionID",
 * "customizationID",
 * "profileID",
 * "profileExecutionID",
 * "id",
 * "salesOrderID",
 * "copyIndicator",
 * "uuid",
 * "issueDate",
 * "issueTime",
 * "orderTypeCode",
 * "note",
 * "requestedInvoiceCurrencyCode",
 * "documentCurrencyCode",
 * "pricingCurrencyCode",
 * "taxCurrencyCode",
 * "customerReference",
 * "accountingCostCode",
 * "accountingCost",
 * "lineCountNumeric",
 * "validityPeriod",
 * "quotationDocumentReference",
 * "orderDocumentReference",
 * "originatorDocumentReference",
 * "catalogueReference",
 * "additionalDocumentReference",
 * "contract",
 * "signature",
 * "buyerCustomerParty",
 * "sellerSupplierParty",
 * "originatorCustomerParty",
 * "freightForwarderParty",
 * "accountingCustomerParty",
 * "delivery",
 * "deliveryTerms",
 * "paymentMeans",
 * "paymentTerms",
 * "transactionConditions",
 * "allowanceCharge",
 * "taxTotal",
 * "anticipatedMonetaryTotal"
 *
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class PurchaseOrderDTO extends BusinessDocumentDTO implements Serializable {
  
  private static final long serialVersionUID = 64717699663153459L;

  /* BA-2012-FEB-14 Added the following attributes. */
  //String department; //optional - see PurchaseOrder and the comment for department.
  //String poType; see PurchaseOrder and the comment for poType.
  //long requestedShippingDate; // included time!??!
  //long requestedDeliveryDate; // included time!??!
  //long requestedPickupDate;   // included time!??!
  
  private AddressDTO shipTo;
  
  /** 
   * it stores the billToAddressDTO if it is not the same as shipTo.
   * NOTE: because there are two embedded objects in this class. the second one attributes must be renamed.
   */
  private AddressDTO billTo;

  private List<PurchaseOrderItemDTO> purchaseOrderItems = new ArrayList<PurchaseOrderItemDTO>();

  private byte taxRatePercent;
  
  private long totalTaxAmount;
  
  private Integer lastSequenceNumberUsed=0;
  
  ///////////////////////////////////////////////
  //////
  //////
  ///////////////////////////////////////////////
  public PurchaseOrderDTO() {
    // TODO Auto-generated constructor stub
  }

  
  public PurchaseOrderDTO(BusinessDocumentDTO businessDocumentDTO) {
    
    super(
        businessDocumentDTO.getId(), businessDocumentDTO.getLevelOfImportance(), 
        businessDocumentDTO.getDocumentState(), businessDocumentDTO.getOriginatorParty(), 
        businessDocumentDTO.getReceiverParty(),
        businessDocumentDTO.getBizDocumentNumber(), 
        businessDocumentDTO.getMonetory(), 
        businessDocumentDTO.getImportantDate(), 
        businessDocumentDTO.getNote());
  }


  public String getPONumber() {
    return getBizDocumentNumber();
  }

  public String getPODate() {
    return getImportantDate();
  }

  public AddressDTO getShipTo() {
    return shipTo;
  }

  public void setShipTo(AddressDTO shipTo) {
    this.shipTo = shipTo;
  }

  public AddressDTO getBillTo() {
    return billTo;
  }

  public void setBillTo(AddressDTO billTo) {
    this.billTo = billTo;
  }

  public void addPurchaseOrderItem(PurchaseOrderItemDTO purchaseOrderItem) {
    assert (this.purchaseOrderItems != null); // Called only during development/testing.
    assert (purchaseOrderItem != null); // Called only during development/testing.
    if (purchaseOrderItem.getSequenceHolder()==0)
      purchaseOrderItem.setSequenceHolder(++lastSequenceNumberUsed);
    this.purchaseOrderItems.add(purchaseOrderItem);
  }

  public List<PurchaseOrderItemDTO> getPurchaseOrderItems() {
	return purchaseOrderItems;
  }

  public byte getTaxRatePercent() {
    return taxRatePercent;
  }

  public void setTaxRatePercent(byte taxRatePercent) {
    this.taxRatePercent = taxRatePercent;
  }

  public long getTotalTaxAmount() {
    return totalTaxAmount;
  }

  public void setTotalTaxAmount(long totalTaxAmount) {
    this.totalTaxAmount = totalTaxAmount;
  }

  public void setPurchaseOrderItems(List<PurchaseOrderItemDTO> purchaseOrderItems) {
	this.purchaseOrderItems = purchaseOrderItems;
  }
  
}
