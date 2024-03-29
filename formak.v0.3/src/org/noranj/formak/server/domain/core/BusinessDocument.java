package org.noranj.formak.server.domain.core;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.FetchGroups;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.noranj.formak.server.domain.biz.Quotation;
import org.noranj.formak.server.domain.core.Attachment;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentStatusType;
import org.noranj.formak.shared.type.LevelOfImportanceType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * see also FetchGroup at http://www.datanucleus.org/products/accessplatform_3_0/jdo/fetchgroup.html 
 * @author
 * 
 * @changes
 * 
 * BA-2012-FEB-12 Changed the key from Long to Key type. It was needed to implement 1-N relationships.
 * BA-2012-MAR-20 Added createdTS to store the timestamp of the creation time. It is mostly used by Admin.
 * One of the usage is to purge the documents or archive them based on their createdTS.
 */
@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION", //@Version is used to implement optimistic locking but we may use it for another purposes.
         extensions={@Extension(vendorName="datanucleus", key="field-name", value="version")})
@FetchGroups({
        @FetchGroup(name=BusinessDocument.C_ATTACHMENT_FETCH_GROUP_NAME , members={@Persistent(name="xmlBizDocument")}), 
        @FetchGroup(name=BusinessDocument.C_TAGS_FETCH_GROUP_NAME, members={@Persistent(name="tags")}),
        @FetchGroup(name=BusinessDocument.C_LABELS_FETCH_GROUP_NAME, members={@Persistent(name="labels")})
        }) //this is used with pm.getFetchPlan().setMaxFetchDepth(n) to control how deep the data is retrieved up front (used in detaching). To get only the order, setMaxFetchDepth(0) and to get order and orderItems, setMaxFetchDepth(1).
public abstract class BusinessDocument implements Serializable {

  @NotPersistent
  private static final long serialVersionUID = 6289943276859036943L;

  protected static Logger logger = Logger.getLogger(BusinessDocument.class.getName());
  
  // these are fetch group names.
  public static final String C_ATTACHMENT_FETCH_GROUP_NAME = "attachment"; 
  public static final String C_TAGS_FETCH_GROUP_NAME = "tags"; 
  public static final String C_LABELS_FETCH_GROUP_NAME = "labels"; 
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  //BA-2012-FEB-12 Changed the key from Long to Key type. It was needed to implement 1-N relationships.
  //private Long id;
  protected Key id;
  
  /** stores business document numbers such as order number, invoice number, or GTIN (global trade item number for product) */
  @Persistent
  protected String bizDocumentNumber;
  
  /** Each document can have a name. 
   * For Product, it is the product name.
   * For Party, it is the party name.
   * For Invoice?!!
   * For Order?!!! 
   * For Quotation?!?!?
   */
  @Persistent
  protected String name;

  /** 
   * It stores an important date for the document.
   * the interpretation of the date could be different for each document.
   * it could be different based on what state the document is in it!!!
   * 
   * For Product, it is expiryDate of product if it is perishable (any other date to be interested??)
   * For Inventory, it could be last inventory date or the next one.
   * For Party, started business with the client of the system. (how about the client? it could be the date the client started using this service. 
   * For Invoice, payment due date.
   * For Order, price lock out expiry date (the price is guaranteed for that period).
   * For Quotation, price lock out expiry date (the price is guaranteed for that period).
   * For DespatchNotice, expected arriving of goods.
   * 
   * NOTE: an idea is to allow user decide which date (from the document) they are interested to store in this field.
   * In that case, we need to keep the description of that date.
   * 
   */
  @Persistent
  protected long importantDate;

  /**
   * defines what is the date that stored in importantDate attribute in above.
   * by default, it is "creation date" or something like that. A simple and small word to add it in front of date or in another column.
   */
  @Persistent
  protected String importantDateDescription;


  /** 
   * It stores an important monetary attribute of the document.
   * For Order, it can be totalAmount of order.
   * For Invoice, it can be total amount of invoice.
   * For Product, it can be unit price.
   * For Party, it can be Account Balance (how much they owe the client or how much we owe them)
   * For Inventory, might be 0. ?!?!?!?
   * ...
   * */
  @Persistent
  protected long monetory;

  
  /**
   * Free format texts that user can add to the document.
   * they work like categories in Product. A document can have more than one tags.
   * Tags could show a hierarchy of categories or tags. (to be confirmed if it is possible).  
   */
  @Persistent
  protected Set<String> tags;

  /**
   * Free format text that are used to add a undefined status or state to document.
   * For example, 
   */
  @Persistent
  protected Set<String> labels;
  
  
  @Persistent
  protected LevelOfImportanceType levelOfImportance;

  /**
   * @deprecated I am not sure about this one yet!!
   * what could it be?!?
   */
  @Persistent
  protected DocumentStatusType status;
  
  /** indicates the current state of document.
   * The history of the document is stored in HistoryOfStateDocument (or WFHistoryOfStateDocument).
   */
  @Persistent
  protected DocumentStateType state;

  /** originator name is the name of party that creates the document.
   * most of the time is also the sender.*/
  @Persistent
  //private Party originatorParty;
  private String originatorPartyID;//FIXME it also could be an embedded PartySummary class.

  /** receiver name is the name of party that receives and uses the document.
   */
  @Persistent
  //private PartyDTO receiverParty;
  private String receiverPartyID; //FIXME it also could be an embedded PartySummary class.

  /** user can add a note. */
  @Persistent
  protected String note;

  /** 
   * This attribute is the one that is used to store the version of the object in data store.
   * The versioning mechanism is used for optimistic locking but we might find other usage for it.
   * 
   * QUESTION: this is the version of Document object in data store. Can we use it for Attachment too?
   * Attached XML file may not be at the same version as Document object in data store.
   * We may update Document object in data store (for example change the status of Document) but 
   * not changing anything in attached XML document. Maybe it is better to add one to Attachment class too.
   * But then we need to handle it manually and DataStore can not do anything for us.
   */
  @Persistent
  protected long version;

  /** it stores the creation date and time for the BusinessDocument or the date time the object added to data store. */
  @Persistent
  protected long createdTS;

  /**
   * The details of business document is stored in form of a XML file in this attribute.
   */
  @Persistent(serialized = "true")
  protected Attachment xmlBizDocument;

  public BusinessDocument() {
    
  }
  
  ///////////////////////////////////////////////
  //// getter/setter methods
  ///////////////////////////////////////////////

  //public Long getId() {
  public String getId() {
    return KeyFactory.keyToString(id);
  }


  //public void setId(Long id) {
  public void setId(String id) {
    this.id = KeyFactory.stringToKey(id);
  }


  public String getBizDocumentNumber() {
    return bizDocumentNumber;
  }


  public void setBizDocumentNumber(String bizDocumentNumber) {
    this.bizDocumentNumber = bizDocumentNumber;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public long getImportantDate() {
    return importantDate;
  }


  public void setImportantDate(long importantDate) {
    this.importantDate = importantDate;
  }


  public String getImportantDateDescription() {
    return importantDateDescription;
  }


  public void setImportantDateDescription(String importantDateDescription) {
    this.importantDateDescription = importantDateDescription;
  }


  public long getMonetory() {
    return monetory;
  }


  public void setMonetory(long monetory) {
    this.monetory = monetory;
  }


  public Set<String> getTags() {
    return tags;
  }


  public void setTags(Set<String> tags) {
    this.tags = tags;
  }


  public Set<String> getLabels() {
    return labels;
  }


  public void setLabels(Set<String> labels) {
    this.labels = labels;
  }


  public LevelOfImportanceType getLevelOfImportance() {
    return levelOfImportance;
  }


  public void setLevelOfImportance(LevelOfImportanceType levelOfImportance) {
    this.levelOfImportance = levelOfImportance;
  }


  public DocumentStatusType getStatus() {
    return status;
  }


  public void setStatus(DocumentStatusType status) {
    this.status = status;
  }


  public DocumentStateType getState() {
    return state;
  }


  public void setState(DocumentStateType state) {
    this.state = state;
  }

  public String getOriginatorPartyID() {
    return originatorPartyID;
  }

  public void setOriginatorPartyID(String originatorPartyID) {
    this.originatorPartyID = originatorPartyID;
  }

  public String getReceiverPartyID() {
    return receiverPartyID;
  }

  public void setReceiverPartyID(String receiverPartyID) {
    this.receiverPartyID = receiverPartyID;
  }


  public String getNote() {
    return note;
  }


  public void setNote(String note) {
    this.note = note;
  }


  public long getVersion() {
    return version;
  }


  public void setVersion(long version) {
    this.version = version;
  }


  public long getCreatedTS() {
  	return createdTS;
  }

	public void setCreatedTS(long createdTS) {
  	this.createdTS = createdTS;
  }

	public Attachment getXmlBizDocument() {
    return xmlBizDocument;
  }


  public void setXmlBizDocument(Attachment xmlBizDocument) {
    this.xmlBizDocument = xmlBizDocument;
  }

  //////////////////////////
  /**
   * Convert the domain object to DTO
   * @return
   */
  public BusinessDocumentDTO getBusinessDocumentDTO () {
    //FIXME [P1] Party information does not exist in business document table.
    HashSet sellerPartyRoles = new HashSet();
    sellerPartyRoles.add(PartyRoleType.Seller); 
    HashSet buyerPartyRoles = new HashSet();
    buyerPartyRoles.add(PartyRoleType.Buyer); 
    return(new BusinessDocumentDTO(getId(), getLevelOfImportance(), getState(), new PartyDTO(originatorPartyID, "OParty1", null, ActivityType.Active, (Set)sellerPartyRoles), new PartyDTO(receiverPartyID, "RParty2", null/*logo*/, ActivityType.Active, (Set)buyerPartyRoles), getBizDocumentNumber(), getMonetory(), getImportantDate(), getNote()));
  }
  
} // Document
