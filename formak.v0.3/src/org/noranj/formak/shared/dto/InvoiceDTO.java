package org.noranj.formak.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class InvoiceDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5256016567581253275L;

  //BA-2012-FEb-14
  private String orderNumber;
  private String tradingPartner;
  private String customerPO;
  private String orderStatus;
  private String termsCode;
  private int discountPercent;
  private int discountDays;
  private long discountDate;
  private int netDueDays;
  private long netDueDate;
  private String termsDescription;
  private String pickSlipNumber;
  //private String invoiceNumber; bizDocumentNumber!!!
  private long shipDate;
  private long invoiceDate; // importantDate
  private long orderDate;
  private String requestedDeliveryDate;
  private String customerID;
  private String shipVia;
  private long netTotal;
  private long salesTax;
  private String freight;
  private String miscellaneous;
  private AddressDTO shipTo;
  
  private List<InvoiceItemDTO> invoiceItems;
  
}
