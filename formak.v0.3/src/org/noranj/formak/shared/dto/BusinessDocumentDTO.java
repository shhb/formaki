package org.noranj.formak.shared.dto;

import java.io.Serializable;

import org.noranj.formak.shared.GlobalSettings;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.LevelOfImportanceType;
import org.noranj.formak.shared.utils.Formatter;


/**
 * NOTE: this class is a temporary class with temporary name
 * (BusinessDocumentDetails) to make it easier to follow the sample code
 * provided by Google. it will be renamed and revised later.
 * 
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * @author
 * 
 * @changes
 *  BA-2012-JAN-31 Added the required attributes for View.
 *  BA-2012-FEB-12 Changed the key from Long to Key type. It was needed to implement 1-N relationships.
 */
public class BusinessDocumentDTO implements Serializable {

  private static final long serialVersionUID = 694507825650974877L;
  
  //BA-2012-FEB-12 Changed the key from Long to Key type. It was needed to implement 1-N relationships.
  //private long id;
  private String id;
  
	private LevelOfImportanceType levelOfImportance;

	/** it indicates the current state of the document. 
	 * for example draft*/
  private DocumentStateType documentState;

  /** originator name is the name of party that creates the document.
   * most of the time is also the sender.*/
	private PartyDTO originatorParty;

	/** receiver name is the name of party that receives and uses the document.
   */
  private PartyDTO receiverParty;
	
  private String bizDocumentNumber;
	
  private long monetory;
	
  private long importantDate;
  
  private String note;

  /** DO NOT DELETE THIS. OTHERWISE GWT CRASHES AND THROWS EXCEPTION ABOUT Serialization. */
	public BusinessDocumentDTO() {
	}
  
	public BusinessDocumentDTO( String id, /*long id,*/ //BA-2012-FEB-12 Changed the key from Long to Key type. It was needed to implement 1-N relationships.
                              String levelOfImportance,
                              String documentState,
                              PartyDTO originatorParty,
                              PartyDTO receiverParty,
                              String bizDocumentNumber,
                              String monetory,
                              String importantDate,
                              String note
                            ) {
	  this(id,
        LevelOfImportanceType.valueOf(levelOfImportance),
        DocumentStateType.valueOf(documentState),
        originatorParty,
        receiverParty,
        bizDocumentNumber,
        Formatter.convertToAmount(monetory),
        Formatter.convertToMilliseconds(importantDate),
        note
      );
	  
	} 

	 public BusinessDocumentDTO( String id, /*long id,*/ //BA-2012-FEB-12 Changed the key from Long to Key type. It was needed to implement 1-N relationships.
       LevelOfImportanceType levelOfImportance,
       DocumentStateType documentState,
       PartyDTO originatorParty,
       PartyDTO receiverParty,
       String bizDocumentNumber,
       long monetory,
       long importantDate,
       String note
	     ) {

	  this.id = id;
	  this.levelOfImportance = levelOfImportance;
	  this.documentState = documentState;
	  this.originatorParty = originatorParty;
    this.receiverParty = receiverParty ;
	  this.bizDocumentNumber = (bizDocumentNumber!=null)?bizDocumentNumber:"null";
	  this.monetory = monetory;
	  this.importantDate = (importantDate!=0)?importantDate: System.currentTimeMillis(); //FIXME this is not right. It is only for test when the data is not consistent.
	  this.note = (note!=null)?note:"null";

	}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLevelOfImportance() {
    return levelOfImportance.toString();
  }

  public void setLevelOfImportance(LevelOfImportanceType levelOfImportance) {
    this.levelOfImportance = levelOfImportance;
  }
  public void setLevelOfImportance(String levelOfImportance) {
    this.levelOfImportance = LevelOfImportanceType.valueOf(levelOfImportance);
  }

  public String getDocumentState() {
    return documentState.toString();
  }

  public void setDocumentState(DocumentStateType documentState) {
    this.documentState = documentState;
  }
  public void setDocumentState(String documentState) {
    this.documentState = DocumentStateType.valueOf(documentState);
  }

  public PartyDTO getOriginatorParty() {
    return originatorParty;
  }

  public void setOriginatorParty(PartyDTO originatorParty) {
    this.originatorParty = originatorParty;
  }

  public PartyDTO getReceiverParty() {
    return receiverParty;
  }

  public void setReceiverParty(PartyDTO receiverParty) {
    this.receiverParty = receiverParty;
  }

  public String getBizDocumentNumber() {
    return bizDocumentNumber;
  }

  public void setBizDocumentNumber(String bizDocumentNumber) {
    this.bizDocumentNumber = bizDocumentNumber;
  }

  public String getMonetory() {
    return Formatter.formatAmount(monetory);
  }

  public void setMonetory(long monetory) {
    this.monetory = monetory;
  }

  public String getImportantDate() {
    return Formatter.formatDate(importantDate, GlobalSettings.getDateFormat() /*, GlobalSettings.getLocale()*/);
  }

  public void setImportantDate(long importantDate) {
    this.importantDate = importantDate;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  /** 
   * @deprecated NOT COMPLETED YET.
   *
   */
  public String toString() {
    return("id["+getId()+"]");
  }
}
