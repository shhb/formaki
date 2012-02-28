package org.noranj.formak.server.domain.biz;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.noranj.formak.server.domain.biz.PurchaseOrderItem;
import org.noranj.formak.server.domain.core.Address;
import org.noranj.formak.server.domain.core.BusinessDocument;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;

//FIXME updating BusinessDocument fields. BA-2012-FEB-09
/**
 * NOTE: it must update fields in BusinessDoument when it is getting updated.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
@FetchGroup(name=PurchaseOrder.C_ITEMS_FETCH_GROUP_NAME, members={@Persistent(name="purchaseOrderItems")}) 
public class PurchaseOrder extends BusinessDocument implements Serializable {
  
  private static final long serialVersionUID = 64717699663153459L;

  public static final String C_ITEMS_FETCH_GROUP_NAME = "purchaseOrderItems";

  /** 
   * It looks like there many types of PO as listed in below. (the source is not reliable)
   * 1)Standard:This PO is created for one-time purchase of material.
   * 2)Blanket:In this PO delivery schedule are not known clearly.
   * 3)contract: In this PO material required are not specified.
   * 4)planned: It is a long term agreement po. In this PO it 
   * specifies materials,estimated costs, and tentative delivery schedules.
   * We are not going to add this attribute and will leave it for later.
   */
  //private String poType;
  
  /** 
   * Free text format defining the department that items belong to it.
   * for example, BOOK, CD/DVD, ...
   * Again, we are not going to implement it in this version. 
   * It is just added so we know about it and will revisit it later.
   */
  //private String department;
  
  /** A free text format so buyer can add any arbitrary text in it. 
   */
  @Persistent
  private String message;
  
  @Persistent(defaultFetchGroup="true")//without this attribute these are not fetched by default. Only primitive data types are fetched by default.
  @Embedded  
  private Address shipTo;
  
  /** 
   * it stores the billToAddress if it is not the same as shipTo.
   * NOTE: because there are two embedded objects in this class. the second one attributes must be renamed.
   */
  @Persistent(defaultFetchGroup="true") //without this attribute these are not fetched by default. Only primitive data types are fetched by default.
  @Embedded(members = {
      @Persistent(name="streetAddress", column="billToStreetAddress"),
      @Persistent(name="city", column="billToCity"),
      @Persistent(name="stateOrProvince", column="billToStateOrProvince"),
      @Persistent(name="postalCode", column="billToPostalCode")})
  private Address billTo;

  @Persistent(mappedBy="purchaseOrder")
  @javax.jdo.annotations.Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sequenceHolder asc")) // let DataNuclues handles the order/sequence of the line items. GAE can not do it.
  private List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<PurchaseOrderItem>();

  /** It indicates the percentage tax rate that applies to the total amount.
   * NOTE: it might be better to have multiple fields for tax because in some countries there are more than one rate.
   *///FIXME array of tax rates or amounts!!!
  @Persistent
  private byte taxRatePercent;

  /** The total amount of tax that applies to the PO. 
   * the tax amount is calculated based on a percentage of the total amount of PO.
   */
  @Persistent
  private long totalTaxAmount;
  
  @Persistent
  private Integer lastSequenceNumberUsed=0;
  

  /////////////////////////////////////////////////
  //////
  /////////////////////////////////////////////////
  
  public PurchaseOrder() {
    super();
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  ///////////////////////////////////////////////
  //////
  //////
  ///////////////////////////////////////////////
  public Address getShipTo() {
    return shipTo;
  }

  public void setShipTo(Address shipTo) {
    this.shipTo = shipTo;
  }

  public Address getBillTo() {
    return billTo;
  }

  public void setBillTo(Address billTo) {
    this.billTo = billTo;
  }

  public List<PurchaseOrderItem> getPurchaseOrderItems() {
    assert (this.purchaseOrderItems != null); // Called only during development/testing.
    return(this.purchaseOrderItems);
  }

  public void addPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem) {
    assert (this.purchaseOrderItems != null); // Called only during development/testing.
    assert (purchaseOrderItem != null); // Called only during development/testing.
    if (purchaseOrderItem.getSequenceHolder()==0)
      purchaseOrderItem.setSequenceHolder(++lastSequenceNumberUsed);
    this.purchaseOrderItems.add(purchaseOrderItem);
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
  public void setTotalTaxAmount(long totalTax) {
    this.totalTaxAmount = totalTax;
  }
  /**
   * 
   * @return
   */
  public PurchaseOrderDTO getPurchaseOrderDTO () {
    
    //BA-2012-FEB-18 Added the BusinessDocumentDTO to the constructor 
    PurchaseOrderDTO po = new PurchaseOrderDTO(super.getBusinessDocumentDTO());
    
    po.setBillTo(this.billTo.getAddressDTO());
    po.setShipTo(this.shipTo.getAddressDTO());
    for(PurchaseOrderItem item : this.purchaseOrderItems) {
      po.addPurchaseOrderItem(item.getPurchaseOrderItemDTO());
    }
    
    //BA-2012-FEB-18 Added two new attributes
    po.setTaxRatePercent(this.taxRatePercent);
    po.setTotalTaxAmount(this.totalTaxAmount);
    return(po);
  }
  
  /**
   * It creates a document of type of Order.
   * The Order document should be a attachments XML and added to Document object representing the Order.
   * @return
   * /
  public static long createDocument (long documentId) {
    it creates an XML representation for the order amd store it using the unique documentId.
    the empty XML is stored as attachment to the document.
    / *
    Order newOrder = new Order();
    newOrder.storeOrder();
    return(newOrder.getId());
    * /
    return(0);
  }
    */
  
}