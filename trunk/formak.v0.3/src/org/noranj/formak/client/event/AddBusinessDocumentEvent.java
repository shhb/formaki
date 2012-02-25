package org.noranj.formak.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class AddBusinessDocumentEvent extends GwtEvent<AddBusinessDocumentEventHandler> {
  
  public static Type<AddBusinessDocumentEventHandler> TYPE = new Type<AddBusinessDocumentEventHandler>();
  
  @Override
  public Type<AddBusinessDocumentEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddBusinessDocumentEventHandler handler) {
    handler.onAddBusinessDocument(this);
  }
}
