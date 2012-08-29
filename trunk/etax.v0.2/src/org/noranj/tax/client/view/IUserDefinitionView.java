package org.noranj.tax.client.view;

import java.util.List;

import org.noranj.tax.client.common.HasSelectedValue;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;


public interface IUserDefinitionView<T,K> {
	
	public interface Presenter<T,K>{};
	
	void setPresenter(Presenter<T,K> presenter);
	
	HasClickHandlers getSaveButton();
	
	HasClickHandlers getCancelButton();
	
	HasValue<String> getid();
	
	HasValue<String> getFirstName();
	
	HasValue<String> getLastName();
	
	HasValue<String> getEmailAddress();

	HasValue<String> getBusinessName();
	
	HasSelectedValue<K> getBusinessRole();
	
	void setBusinessRoleData(List<K> rowBusinessRoleData);
		
	Widget asWidget();

	
	
}
