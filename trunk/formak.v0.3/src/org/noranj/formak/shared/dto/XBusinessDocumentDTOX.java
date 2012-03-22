package org.noranj.formak.shared.dto;

import java.io.Serializable;

import org.noranj.formak.shared.type.DocumentType;


/**
 * NOTE: this is the equivalent of Contact. 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class XBusinessDocumentDTOX implements Serializable {
  
  private String id; //Added manually and it was not in Contact!!!
  private String businessDocumentName; // firstName
  private String description; //lastName
  private String businessDocumentNumber; //emailAddress
  private String documentType; //emailAddress
  

  public XBusinessDocumentDTOX() {
    super();
    this.id = "NOT_ASSIGNED";
    this.businessDocumentName = "NOT_ASSIGNED";
    this.description = "NOT_ASSIGNED";
    this.businessDocumentNumber = "NOT_ASSIGNED";
    this.documentType = DocumentType.Other.toString();
  }

  public XBusinessDocumentDTOX(String id, String businessDocumentName, 
                              String description, String businessDocumentNumber,
                              String documentType) {
    super();
    this.id = id;
    this.businessDocumentName = businessDocumentName;
    this.description = description;
    this.businessDocumentNumber = businessDocumentNumber;
    this.documentType = documentType;
  }
  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBusinessDocumentName() {
    return businessDocumentName;
  }
  public void setBusinessDocumentName(String businessDocumentName) {
    this.businessDocumentName = businessDocumentName;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getBusinessDocumentNumber() {
    return businessDocumentNumber;
  }
  public void setBusinessDocumentNumber(String businessDocumentNumber) {
    this.businessDocumentNumber = businessDocumentNumber;
  }

  public String getDocumentType() {
    return documentType;
  }

  public void setDocumentType(String documentType) {
    this.documentType = documentType;
  }
  
}
