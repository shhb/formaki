package org.noranj.formak.client.event;

import org.noranj.formak.shared.type.DocumentType;

import com.google.gwt.event.shared.GwtEvent;

public class AddBusinessDocumentEvent extends GwtEvent<AddBusinessDocumentEventHandler> {
	private final DocumentType documentType;

	public AddBusinessDocumentEvent(DocumentType documentType) {
		this.documentType = documentType;
	}

	public static Type<AddBusinessDocumentEventHandler> TYPE = new Type<AddBusinessDocumentEventHandler>();

	@Override
	public Type<AddBusinessDocumentEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddBusinessDocumentEventHandler handler) {
		handler.onAddBusinessDocument(this);
	}

	public DocumentType getDocumentType() {
		return documentType;
	}
	
	
}
