package org.noranj.formak.client.event;


import org.noranj.formak.shared.dto.XBusinessDocumentDTOX;

import com.google.gwt.event.shared.GwtEvent;

public class BusinessDocumentUpdatedEvent extends GwtEvent<BusinessDocumentUpdatedEventHandler>{
  public static Type<BusinessDocumentUpdatedEventHandler> TYPE = new Type<BusinessDocumentUpdatedEventHandler>();
  private final XBusinessDocumentDTOX updatedBusinessDocument;
  
  public BusinessDocumentUpdatedEvent(XBusinessDocumentDTOX updatedBusinessDocument) {
    this.updatedBusinessDocument = updatedBusinessDocument;
  }
  
  public XBusinessDocumentDTOX getUpdatedContact() { return updatedBusinessDocument; }
  

  @Override
  public Type<BusinessDocumentUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BusinessDocumentUpdatedEventHandler handler) {
    handler.onBusinessDocumentUpdated(this);
  }
}
