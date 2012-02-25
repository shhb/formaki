package org.noranj.formak.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class DocumentTypeListChangedEvent extends GwtEvent<DocumentTypeListChangedEventHandler> {
	public static Type<DocumentTypeListChangedEventHandler> TYPE = new Type<DocumentTypeListChangedEventHandler>();
	private final String documentTypeCode;
	
	public DocumentTypeListChangedEvent(String documentTypeCode){
		this.documentTypeCode = documentTypeCode;
	}
	
	public String getDocumentTypeCode(){
		return documentTypeCode;
	}
	
	@Override
	public Type<DocumentTypeListChangedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DocumentTypeListChangedEventHandler handler) {
		handler.BusinessDocumentTypeListChanged(this);
	}

}
