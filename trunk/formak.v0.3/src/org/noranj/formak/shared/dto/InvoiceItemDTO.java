package org.noranj.formak.shared.dto;

import java.io.Serializable;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class InvoiceItemDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3613575945950009675L;

  //BA-2012-FEB-14 
  private String lineNumber;
  private String productID;
  private String customerProductID;
  private String upc;
  private String description;
  private String baseUnits;
  private String sellUnits;
  private String qtyShipped;
  private long unitPrice;
  private String unitWeight;
  private String warehouseCode;
  private String binCode;

  
}
