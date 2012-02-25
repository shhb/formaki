package org.noranj.formak.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BusinessDocumentDeletedEventHandler extends EventHandler {
  void onContactDeleted(BusinessDocumentDeletedEvent event);
}
