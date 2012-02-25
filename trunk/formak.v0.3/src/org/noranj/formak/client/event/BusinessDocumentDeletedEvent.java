package org.noranj.formak.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BusinessDocumentDeletedEvent extends GwtEvent<BusinessDocumentDeletedEventHandler>{
  public static Type<BusinessDocumentDeletedEventHandler> TYPE = new Type<BusinessDocumentDeletedEventHandler>();
  
  @Override
  public Type<BusinessDocumentDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BusinessDocumentDeletedEventHandler handler) {
    handler.onContactDeleted(this);
  }
}
