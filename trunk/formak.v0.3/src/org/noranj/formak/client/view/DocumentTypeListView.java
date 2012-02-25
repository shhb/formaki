package org.noranj.formak.client.view;

import org.noranj.formak.client.view.BusinessDocumentView.Presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

public interface DocumentTypeListView<T> {
	
	public interface Presenter<T> {
		public void onBusinessDocumentTypeListChanged(String documentTypeCode);
		
	}
	
	Widget asWidget();
	
	void setPresenter(Presenter<T> presenter);
	
	void setDocumentList(T[] setData);
}
