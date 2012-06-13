package org.noranj.formak.client.view;

import org.noranj.formak.client.view.EditPurchaseOrderView.Presenter;

import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class UserDefinitionViewImpl<T> extends Composite implements UserDefinitionView {

	private static UserDefinitionViewImplUiBinder uiBinder = GWT.create(UserDefinitionViewImplUiBinder.class);
	
	private Presenter<T> presenter;
	
	@UiTemplate("UserDefinitionView.ui.xml")
	interface UserDefinitionViewImplUiBinder extends UiBinder<Widget, UserDefinitionViewImpl> {
	}

	public UserDefinitionViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	TextBox id;
	
	@UiField
	TextBox firstName;
	
	@UiField
	TextBox lastName;
	
	@UiField
	TextBox emailAddress;
	
	@UiField
	Anchor saveButton;
	
	@UiField
	Anchor cancelButton;
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}
	
	@Override
	public HasValue<String> getid(){
		return id;
	}
	
	@Override
	public HasValue<String> getFirstName(){
		return firstName;
	}
	@Override
	public HasValue<String> getLastName(){
		return lastName;
	}
	@Override
	public HasValue<String> getEmailAddress(){
		return emailAddress;
	}
	@Override
	public HasClickHandlers getSaveButton(){
		return saveButton;
	}
	@Override
	public HasClickHandlers getCancelButton(){
		return cancelButton;
	}

	
}
