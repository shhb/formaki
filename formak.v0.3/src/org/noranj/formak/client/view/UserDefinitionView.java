package org.noranj.formak.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;


public interface UserDefinitionView<T> {
	
	public interface Presenter<T>{};
	
	void setPresenter(Presenter<T> presenter);
	
	HasClickHandlers getSaveButton();
	
	HasClickHandlers getCancelButton();
	
	HasValue<String> getid();
	
	HasValue<String> getFirstName();
	
	HasValue<String> getLastName();
	
	HasValue<String> getEmailAddress();

	Widget asWidget();
	
}
