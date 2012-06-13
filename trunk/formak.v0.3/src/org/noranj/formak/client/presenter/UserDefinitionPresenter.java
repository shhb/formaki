package org.noranj.formak.client.presenter;

import java.util.HashSet;

import org.noranj.formak.client.service.SystemAdminServiceAsync;
import org.noranj.formak.client.view.UserDefinitionView;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.service.SystemAdminServiceImpl;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.dto.UserProfileDTO;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;


public class UserDefinitionPresenter implements Presenter,UserDefinitionView.Presenter<SystemUserDTO> {

	private final UserDefinitionView<SystemUserDTO> view;
	private final SystemAdminServiceAsync rpc;
	private final String id;
	
	public UserDefinitionPresenter(UserDefinitionView<SystemUserDTO> view, String id,SystemAdminServiceAsync rpc){
		 this.view = view;
		 this.id= id ; 
		 this.rpc = rpc;
		 bind();
	 }
	
	public void bind() {
		this.view.setPresenter(this);
		this.view.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
						doSave(new SystemUserDTO());		
			}
		});
		
		this.view.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign("#");
			}
		});
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	public void doSave(SystemUserDTO user) {
	    HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
	    roles.add(PartyRoleType.Buyer);
	    
		SystemClientPartyDTO parentClient = new SystemClientPartyDTO(null, "Noranj-Retailer", "http://retailer.noranj.com", ActivityType.Active, roles /*roles*/, null /*users*/);
	    rpc.addSystemClientParty(parentClient,new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fail To Save");
			}

			@Override
			public void onSuccess(String result) {
				view.getid().setValue(result); //ParentId store in id field on the view 
				
			}
		});
	    
	    parentClient.setId(view.getid().getValue());
	    
		user = new SystemUserDTO(null, this.view.getFirstName().getValue(),
													 this.view.getFirstName().getValue(), 
													 this.view.getEmailAddress().getValue() , 
													 parentClient.getId(), 
													 ActivityType.Active, 
													 System.currentTimeMillis(), 
													 System.currentTimeMillis(), 
													 new UserProfileDTO());
		rpc.addSystemUser(user, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				view.getid().setValue(result); //ChildId store in id field on the view and the Save proccess is finished.
			}
			
			@Override
			public void onFailure(Throwable caught) {
				//System.out.printf("Fail To Save");
				Window.alert("Fail To Save");
			}
		});
	}
}
