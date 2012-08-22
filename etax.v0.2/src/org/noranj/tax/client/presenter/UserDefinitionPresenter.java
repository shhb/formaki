package org.noranj.tax.client.presenter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.shared.dto.IDNameDTO;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.idnt.shared.dto.UserProfileDTO;
import org.noranj.tax.client.service.SystemAdminServiceAsync;
import org.noranj.tax.client.view.UserDefinitionView;
//import org.noranj.tax.shared.dto.SystemClientPartyDTO;
import org.noranj.tax.shared.type.PartyRoleType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;


public class UserDefinitionPresenter implements Presenter,UserDefinitionView.Presenter<UserDTO,IDNameDTO> {

	private final UserDefinitionView<UserDTO,IDNameDTO> view;
	private final SystemAdminServiceAsync rpc;
	private final String id;
	
	
	public UserDefinitionPresenter(UserDefinitionView<UserDTO,IDNameDTO> view, String id, SystemAdminServiceAsync rpc){
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
						doSave(new UserDTO());	//FIXME [SA:] why you are passing an object without any reference to it? why not passing null!! why passing any parameter at all?!?!? 	
			}
		});
		
		this.view.getCancelButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign("#");
			}
		});
	
		//TODO [SA:] this must come from a service defined in server side. (BA:2012-08-21)
		List<IDNameDTO> partyRoleTypeList = new ArrayList<IDNameDTO>();
		partyRoleTypeList.add(new IDNameDTO("1","Buyer"));
		partyRoleTypeList.add(new IDNameDTO("2","Seller"));
		partyRoleTypeList.add(new IDNameDTO("3","Supplier"));
		partyRoleTypeList.add(new IDNameDTO("4","Manufacturer"));
		partyRoleTypeList.add(new IDNameDTO("5","Unknown"));
		this.view.setBusinessRoleData(partyRoleTypeList);
		
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	/**
	 * It saves the form to data storage.
	 * @param user - why passing this parameter?? it is not carrying any data into the method!!
	 */
	public void doSave(UserDTO user) {
	  
	  //BA:2012-JUN-14 this must be set in UI by showing a list box for now 9single choice. */
    HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
    roles.add(PartyRoleType.Buyer);
    
/*	  SystemClientPartyDTO parentClient = new SystemClientPartyDTO(null, 
																  this.view.getBusinessName().getValue(), 
																  "http://retailer.noranj.com", 
																  ActivityType.Active, 
																  roles roles, 
																  null users);*/
	    
    user = new UserDTO(this.view.getid().getValue(), //BA:2012-08-22 Fixed [TAX-22]. The value passed was hard coded "". It must come from VIEW if there is a field for it in that VEIW. I am going to also change the constructor to treat the empty string as NULL. See [TAX-22] for more details.
	                       this.view.getFirstName().getValue(),
												 this.view.getLastName().getValue(), 
												 this.view.getEmailAddress().getValue() , 
												 null, //BA:2012-JUN-14 parentClient.getId(), 
												 ActivityType.Active, 
												 System.currentTimeMillis(), 
												 System.currentTimeMillis(), 
												 new UserProfileDTO());

	rpc.signup(null, user, new AsyncCallback<String>() {
    
                @Override
                public void onSuccess(String result) {
                    view.getid().setValue(result); //ChildId store in id field on the view and the Save process is finished.
                    Window.Location.assign("/logingoogle");
                }
                
                @Override
                public void onFailure(Throwable caught) {
                 //System.out.printf("Fail To Save");
                  Window.alert("Fail To Save");
                }
	           });
	}
}
