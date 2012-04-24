package org.noranj.formak.shared.type;

import java.io.Serializable;

/**
 * We need both "unknown" and "Other" document type because other is a valid bizDoc type that is actually stored in data store.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public enum DocumentType implements Serializable {

  Unknown(0), 
  RequestForQuotation(10), Quotation(20), 
  PurchaseOrderResponse(30), PurchaseOrder(40), 
  Invoice(50), DispatchAdvice(60), 
  Item(70), Catalog(80),
  Other(99);

  private int code;

  DocumentType() {
    code = 0;
  }

  DocumentType(int code) {
    this.code = code;
  }

  /**
   * It returns the enum code equivalent to the string format of the code.
   * 
   * NOTE: Couldn't find a better way to implement it.
   * @param codeStr
   * @return
   */
  public static DocumentType fromCodeString(String codeStr) {
    
    switch (Integer.valueOf(codeStr)) {
      case 10:
        return(DocumentType.RequestForQuotation);
      case 20:
        return(DocumentType.Quotation);
      case 30:
        return (DocumentType.PurchaseOrderResponse);
      case 40:
        return (DocumentType.PurchaseOrder);
      case 50:
        return (DocumentType.Invoice);
      case 60:
        return (DocumentType.DispatchAdvice);
      case 70:
        return (DocumentType.Item);
      case 80:
        return (DocumentType.Catalog);
    	case 99:
    		return (DocumentType.Other);
      default:
        return (DocumentType.Unknown);
    } // switch

  } // toString

  /** converts the description to the DocumentType form.
   * For example if gets PurchaseOrder as input, 
   * it will return DocumentType.PurchaseOrder.
   * @param stringDocumentType
   * @return
   */
  public static DocumentType fromString(String stringDocumentType) {
	  
  	if (stringDocumentType!=null) {
	    	
	    if ( stringDocumentType.equalsIgnoreCase("RequestForQuotation"))
	      return(DocumentType.RequestForQuotation);
	    else if (stringDocumentType.equalsIgnoreCase("Quotation"))
	      return(DocumentType.Quotation);
	    else if (stringDocumentType.equalsIgnoreCase("PurchaseOrderResponse"))
	      return (DocumentType.PurchaseOrderResponse);
	    else if (stringDocumentType.equalsIgnoreCase("PurchaseOrder"))
	      return (DocumentType.PurchaseOrder);
	    else if (stringDocumentType.equalsIgnoreCase("Invoice"))
	      return (DocumentType.Invoice);
	    else if (stringDocumentType.equalsIgnoreCase("DispatchAdvice"))
	      return (DocumentType.DispatchAdvice);
	    else if (stringDocumentType.equalsIgnoreCase("Item"))
	      return (DocumentType.Item);
	    else if (stringDocumentType.equalsIgnoreCase("Catalog"))
	      return (DocumentType.Catalog);
	    else if (stringDocumentType.equalsIgnoreCase("Other"))
	      return (DocumentType.Other);
    
  	} 
  	return (DocumentType.Unknown);

	} // toString
  
  public String codeToString() {
    return String.valueOf(code);
  }

}
