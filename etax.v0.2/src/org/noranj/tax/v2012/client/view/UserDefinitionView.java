package org.noranj.tax.v2012.client.view;

import java.util.List;

import org.noranj.idnt.shared.dto.IDNameDTO;
import org.noranj.tax.v2012.client.common.HasSelectedValue;
import org.noranj.tax.v2012.client.common.SelectOneListBox;
import org.noranj.tax.v2012.client.common.SelectOneListBox.OptionFormatter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class UserDefinitionView<T,K> extends Composite implements IUserDefinitionView<T,K> {

	private static UserDefinitionViewImplUiBinder uiBinder = GWT.create(UserDefinitionViewImplUiBinder.class);
	
	private Presenter<T,K> presenter;
	
	@UiTemplate("UserDefinitionView.ui.xml")
	interface UserDefinitionViewImplUiBinder extends UiBinder<Widget, UserDefinitionView> {
	}

	public UserDefinitionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Anchor saveButton;
	
	@UiField
	Anchor cancelButton;
	
	@UiField
	TextBox id;
	
	@UiField
	TextBox firstName;
	
	@UiField
	TextBox lastName;
	
	@UiField
	TextBox emailAddress;
	
	@UiField
	TextBox businessName;
	
	@UiField()
	SelectOneListBox<K> businessRole;
	
	@UiFactory
	SelectOneListBox<IDNameDTO> initSelect() {
		return new SelectOneListBox<IDNameDTO>(
				new OptionFormatter<IDNameDTO>() {

					@Override
					public String getLabel(IDNameDTO option) {
						return option.getName();
					}

					@Override
					public String getValue(IDNameDTO option) {
						return option.getId();
					}
				});

	}
	
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
	public HasValue<String> getBusinessName(){
		return businessName;
	}
	
	@Override
	public HasSelectedValue<K> getBusinessRole() {
		return businessRole;
	}
	
	@Override
	public void setBusinessRoleData(List<K> rowBusinessRoleData) {
		businessRole.setSelections(rowBusinessRoleData);
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
