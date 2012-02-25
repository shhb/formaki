package org.noranj.formak.client.common;


import java.util.ArrayList;
import java.util.List;

import org.noranj.formak.server.domain.biz.PurchaseOrderItem;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

public class BusinessDocumentsColumnDefinitionsFactory<T> {
	
  public static List<ColumnDefinition<BusinessDocumentDTO>>  getBusinessDocumentsColumnDefinitions() {
    return BusinessDocumentsColumnDefinitionsImpl.getInstance();
  }

  public static List<ColumnDefinition<BusinessDocumentDTO>>  getTestBusinessDocumentsColumnDefinitions() {
    return new ArrayList<ColumnDefinition<BusinessDocumentDTO>>();
  }
  
  public static List<ColumnDefinition<PurchaseOrderItemDTO>> getPurchaseOrderItemColumnDefinitions(){
	  return PurchaseOrderColumnDefinitionImpl.getInstance();
  }
}
