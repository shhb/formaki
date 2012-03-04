package org.noranj.formak.client;

import org.noranj.formak.client.event.LoginEvent;
import org.noranj.formak.client.helper.RPCCall;
import org.noranj.formak.client.presenter.LoginPresenter;
import org.noranj.formak.client.service.BusinessDocumentService;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.service.SystemAdminService;
import org.noranj.formak.client.service.SystemAdminServiceAsync;
import org.noranj.formak.client.view.LoginView;
import org.noranj.formak.shared.GlobalSettings;
import org.noranj.formak.shared.dto.SystemUserDTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author SA
 * @version 0.3.2012MAR01
 * @since 0.1
 * @change 
 *  BA:12-MAR-01 Added getLoggedInuser, showLoginView, createUI, and modified onModuleLoad to look like the original.
 *               Added eventBus, and currentUser attributes to the class.
 */
public class Formak implements EntryPoint {

	interface FormakUiBinder extends UiBinder<DockLayoutPanel, Formak> {}
	
	private static final FormakUiBinder binder = GWT.create(FormakUiBinder.class);
	
	//private HandlerManager eventBus = new HandlerManager(null);
	
	RootLayoutPanel root;
	
	@UiField ScrollPanel folders; 
	
	@UiField ScrollPanel mainPanel;
	
	private static Formak singleton;

	//TODO SA look at this new attribute that I copied over.
  //BA:12-MAR-01 Added 
  private SystemUserDTO currentUser;

	//TODO SA look at this new attribute that I copied over.
	//BA:12-MAR-01 Added 
  private SimpleEventBus eventBus = new SimpleEventBus();

  
  //TODO SA look at this new attribute that I copied over.
  //BA:12-MAR-01 Added 
  // RPC services
  private SystemAdminServiceAsync systemAdminService = GWT.create(SystemAdminService.class);;
  
  
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public static Formak get() {
	    return singleton;
	  }

	ScrollPanel getMainPanel(){
		return 	mainPanel;
	}
	
	ScrollPanel getFolders(){
		return 	folders;
	}

	//BA:12-MAR-01 Added
  public SimpleEventBus getEventBus() {
    return eventBus;
  }

  public void onModuleLoad() {
    //pushServiceStreamFactory = (SerializationStreamFactory) PushService.App.getInstance();
    singleton = this;

    getLoggedInUser();
    
  }

  private void getLoggedInUser() {
    
    new RPCCall<SystemUserDTO>() {
      
      @Override protected void callService(AsyncCallback<SystemUserDTO> cb) {
        systemAdminService.getLoggedInUserDTO(cb);
      }

      @Override public void onSuccess(SystemUserDTO loggedInUserDTO) {
        if (loggedInUserDTO == null) {
          // nobody is logged in
          showLoginView();
        } else {
          // user is logged in
          setCurrentUser(loggedInUserDTO);
          createUI();
        }
      }

      @Override public void onFailure(Throwable caught) {
        Window.alert("Error: " + caught.getMessage());
      }
    }.retry(GlobalSettings.C_RPC_CALL_RETRY);

  }
  
  public void showLoginView() {
    root = RootLayoutPanel.get();
    root.clear();
    LoginPresenter loginPresenter = new LoginPresenter(eventBus, new LoginView());
    loginPresenter.go(root);
  }

  private void createUI() {

    GWT.runAsync(new RunAsyncCallback() {
      @Override public void onFailure(Throwable reason) {
        Window.alert("Code download error: " + reason.getMessage());
      }

      @Override public void onSuccess() {
        goAfterLogin();
        eventBus.fireEvent(new LoginEvent(currentUser));
      }
    });
  }

  //BA:12-MAR-01
  private void goAfterLogin() {

    DockLayoutPanel outer = binder.createAndBindUi(this);
    root = RootLayoutPanel.get();
    root.clear();
    root.add(outer);
    
    BusinessDocumentServiceAsync rpcService = GWT.create(BusinessDocumentService.class);
    //TODO SA review - the below line doesn't exist in original. It uses the class eventBus defined in class level. That is a SimpleEvenBus and not a HandleManager.
    // I am not going to change your code. But is good that you will review it and 
    // then we can have a call and discuss it. BA:12-MAR-01
    HandlerManager eventBus = new HandlerManager(null);
    AppController appViewer = new AppController(rpcService, eventBus);
    //TODO SA review the next line as well. the original code doesn't get any parameter but yours get one parameter. BA:12-MAR-01
    appViewer.go(getMainPanel());

    //TODO SA your code in general loads only one View but the other one loads two view at the same time.
    // Please, review the original code and see if any change in your code is required. BA:12-MAR-01
  }

  //BA:12-MAR-01 Added
  void setCurrentUser(SystemUserDTO currentUser) {
    this.currentUser = currentUser;
  }

  //BA:12-MAR-01
  SystemUserDTO getCurrentUser() {
    return currentUser;
  }
	
	
}
