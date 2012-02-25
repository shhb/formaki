package org.noranj.formak.client;

import org.noranj.formak.client.service.BusinessDocumentService;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;


public class Formak implements EntryPoint {

	interface FormakUiBinder extends UiBinder<DockLayoutPanel, Formak> {}
	
	private static final FormakUiBinder binder = GWT.create(FormakUiBinder.class);
	
	//private HandlerManager eventBus = new HandlerManager(null);
	
	RootLayoutPanel root;
	
	@UiField ScrollPanel folders; 
	
	@UiField ScrollPanel mainPanel;
	
	private static Formak singleton;
	
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

	public void onModuleLoad() {
		singleton = this;
		DockLayoutPanel outer = binder.createAndBindUi(this);
		root = RootLayoutPanel.get();
		root.clear();
		root.add(outer);
		BusinessDocumentServiceAsync rpcService = GWT
				.create(BusinessDocumentService.class);
		HandlerManager eventBus = new HandlerManager(null);
		AppController appViewer = new AppController(rpcService, eventBus);
		appViewer.go(getMainPanel());
		//appViewer.go(getFolders());
	}
}
