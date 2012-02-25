package org.noranj.formak.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DocumentTypeListChangedEventHandler extends EventHandler {
	void BusinessDocumentTypeListChanged(DocumentTypeListChangedEvent event);
}
