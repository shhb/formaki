package org.noranj.formak.shared.type;

import java.io.Serializable;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public enum DocumentType implements Serializable {

  Unknown(0), // not sure if it is needed
  RequestForQuotation(10), Quotation(20), PurchaseOrderResponse(30), 
  PurchaseOrder(40), Invoice(50), DispatchAdvice(60), Item(70), Catalog(80);

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
      default:
        return (DocumentType.Unknown);
    } // switch

  } // toString

  public static DocumentType fromString(String stringDocumentType) {
	    
	    if ( stringDocumentType.equals("RequestForQuotation"))
	      return(DocumentType.RequestForQuotation);
	    else if (stringDocumentType.equals("Quotation"))
	      return(DocumentType.Quotation);
	    else if (stringDocumentType.equals("PurchaseOrderResponse"))
	      return (DocumentType.PurchaseOrderResponse);
	    else if (stringDocumentType.equals("PurchaseOrder"))
	      return (DocumentType.PurchaseOrder);
	    else if (stringDocumentType.equals("Invoice"))
	      return (DocumentType.Invoice);
	    else if (stringDocumentType.equals("DispatchAdvice"))
	      return (DocumentType.DispatchAdvice);
	    else if (stringDocumentType.equals("Item"))
	      return (DocumentType.Item);
	    else if (stringDocumentType.equals("Catalog"))
	      return (DocumentType.Catalog);
	    else
	    	return (DocumentType.Unknown);

	  } // toString
  public String codeToString() {
    return String.valueOf(code);
  }

}
