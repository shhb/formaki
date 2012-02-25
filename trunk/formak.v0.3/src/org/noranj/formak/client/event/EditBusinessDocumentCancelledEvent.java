package org.noranj.formak.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class EditBusinessDocumentCancelledEvent extends GwtEvent<EditBusinessDocumentCancelledEventHandler>{
  public static Type<EditBusinessDocumentCancelledEventHandler> TYPE = new Type<EditBusinessDocumentCancelledEventHandler>();
  
  @Override
  public Type<EditBusinessDocumentCancelledEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(EditBusinessDocumentCancelledEventHandler handler) {
    handler.onEditContactCancelled(this);
  }
}
