package org.noranj.formak.server.domain.biz;

import java.util.logging.Logger;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.formak.server.domain.association.PartyRoleDocument;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
public class PurchaseOrderItem {

  protected static Logger logger = Logger.getLogger(PurchaseOrderItem.class.getName());
	
  //Apparently a child object can not have a PK
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key id;

  public String getId() {
	  return KeyFactory.keyToString(id);
  }

  public void setId(String id) {
	  this.id = KeyFactory.stringToKey(id);
  }

  @Persistent
  private PurchaseOrder purchaseOrder; //this is used to build the relationship.
  
  @Persistent
  private int sequenceHolder; // it is used only to store order of the line items and not necessary increments by one.
  
  /** This is the ID as defined and assigned by this system to the Item. 
   * Don't take this as GTIN which is the item global identification. */ 
  @Persistent
  private long itemID; // not sure to store the id or the actual link to Item?!?!
  
  @Persistent
  private int quantity;

  /** Unit price for the item.
   * all monetary are stored as long with 3 digits decimal point. */
  @Persistent
  private long price;

  /** Unit Of Measure */
  @Persistent
  private String uom; //optional
  
  /** buyer can put its own item ID in order.
   * this helps buyer to find the item in their system faster and easier.
   */
  @Persistent
  private String buyerItemID; //optional
  
  /** free format text that describes the line item.
   * it could be item name or description.
   */
  @Persistent
  private String description; //optional
  
  /** Global Trade Item Number. */
  @Persistent
  private String gtin; //upc can be extracted from GTIN.

  /////////////////////////////////////////////
  /////
  /////////////////////////////////////////////
  
  /**
   * 
   */
  public PurchaseOrderItem() {
    super();
    //TODO A-2012-02-23 : BA generated constructor stub

  }

  public PurchaseOrderItem(/*PurchaseOrder purchaseOrder,*/ int sequenceHolder, long itemID, String gtin, String buyerItemID, String description, String uom, int quantity, long price) {
    
    super();
    //this.purchaseOrder = purchaseOrder;
    this.sequenceHolder = sequenceHolder;
    this.itemID = itemID;
    this.gtin = gtin;
    this.buyerItemID = buyerItemID;
    this.description = description;
    this.uom = uom;
    this.price = price;
    this.quantity = quantity;

  }
//TODO: SA:2012-03-06 / The new constructor is provided for copy the purchase items but it needs to review. 
  public PurchaseOrderItem(/*PurchaseOrder purchaseOrder,*/String id, int sequenceHolder, long itemID, String gtin, String buyerItemID, String description, String uom, int quantity, long price) {
	    
	    super();
	    //this.purchaseOrder = purchaseOrder;
	    setId(id);
	    this.sequenceHolder = sequenceHolder;
	    this.itemID = itemID;
	    this.gtin = gtin;
	    this.buyerItemID = buyerItemID;
	    this.description = description;
	    this.uom = uom;
	    this.price = price;
	    this.quantity = quantity;

	  }
  
  public PurchaseOrder getPurchaseOrder() {
    return purchaseOrder;
  }

  public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
    this.purchaseOrder = purchaseOrder;
  }

  public int getSequenceHolder() {
    return sequenceHolder;
  }

  public void setSequenceHolder(int sequenceHolder) {
    this.sequenceHolder = sequenceHolder;
  }
 
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public long getItemID() {
    return itemID;
  }

  public void setItemID(long itemID) {
    this.itemID = itemID;
  }

  public String getUOM() {
    return uom;
  }

  public void setUOM(String uom) {
    this.uom = uom;
  }

  public String getBuyerItemID() {
    return buyerItemID;
  }

  public void setBuyerItemID(String buyerItemID) {
    this.buyerItemID = buyerItemID;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getGTIN() {
    return gtin;
  }

  public void setGTIN(String gtin) {
    this.gtin = gtin;
  }

  public String toString() {
    // see this link http://groups.google.com/group/google-web-toolkit/browse_thread/thread/d18118d7fd4e8c26?pli=1
    StringBuilder strb = new StringBuilder();
    strb.append("id[");
    strb.append(id);
    strb.append("] sequence[");
    strb.append(sequenceHolder);
    strb.append("] GTIN[");
    strb.append(gtin);
    strb.append("] itemID[");
    strb.append(itemID);
    strb.append("] buyerItemID[");
    strb.append(buyerItemID);
    strb.append("] description[");
    strb.append(description);
    strb.append("] uom[");
    strb.append(uom);
    strb.append("] price[");
    strb.append(price);
    strb.append("] qty[");
    strb.append(quantity);
    strb.append("]");
    return strb.toString();
  }
  
  /**
   * 
   * @return
   */
  public PurchaseOrderItemDTO getPurchaseOrderItemDTO () {

    PurchaseOrderItemDTO poItem = new PurchaseOrderItemDTO();
    poItem.setId(getId());
    poItem.setSequenceHolder(this.sequenceHolder);
    poItem.setGTIN(this.gtin);
    poItem.setItemID(this.itemID);
    poItem.setBuyerItemID(this.buyerItemID);
    poItem.setDescription(this.description);
    poItem.setUOM(this.uom);
    poItem.setPrice(this.price);
    poItem.setQuantity(quantity);
    
    return(poItem);
    
  }

}
