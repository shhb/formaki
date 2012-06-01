package org.noranj.formak.client;


import java.util.List;

import org.noranj.formak.client.common.BusinessDocumentsColumnDefinitionsFactory;
import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.event.AddBusinessDocumentEvent;
import org.noranj.formak.client.event.AddBusinessDocumentEventHandler;
import org.noranj.formak.client.event.DocumentTypeListChangedEvent;
import org.noranj.formak.client.event.DocumentTypeListChangedEventHandler;
import org.noranj.formak.client.event.EditBusinessDocumentEvent;
import org.noranj.formak.client.event.EditBusinessDocumentEventHandler;
import org.noranj.formak.client.event.LogoutEvent;
import org.noranj.formak.client.event.LogoutEventHandler;
import org.noranj.formak.client.presenter.DocumentTypeListPresenter;
import org.noranj.formak.client.presenter.BusinessDocumentViewPresenter;
import org.noranj.formak.client.presenter.EditBusinessDocumentPresenter;
import org.noranj.formak.client.presenter.EditPurchaseOrderPresenter;
import org.noranj.formak.client.presenter.HomeMainPresenter;
import org.noranj.formak.client.presenter.Presenter;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.view.DocumentTypeListViewImpl;
import org.noranj.formak.client.view.BusinessDocumentViewImpl;
import org.noranj.formak.client.view.EditBusinessDocumentView;
import org.noranj.formak.client.view.EditPurchaseOrderViewImpl;
import org.noranj.formak.client.view.HomeMainView;
import org.noranj.formak.client.view.HomeMainViewImpl;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
import org.noranj.formak.shared.type.DocumentType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private DocumentType documentType; 
  private String documentTypeCode;
  private String businessDocumentId;
  private final HandlerManager eventBus;
  private final BusinessDocumentServiceAsync rpcService; 
  private HasWidgets container;

  private BusinessDocumentViewImpl<BusinessDocumentDTO, DocumentType> businessDocumentView = null;
  private EditPurchaseOrderViewImpl<PurchaseOrderDTO,PurchaseOrderItemDTO,IDNameDTO> editPurchaseOrderView = null;
  private DocumentTypeListViewImpl<DocumentType> businessDocumentListView = null;
  private HomeMainViewImpl<PurchaseOrderDTO> homeView = null;

  //BA-2012-JAN-27 Added because it was missing from the code and sample codes.
  private List<ColumnDefinition<BusinessDocumentDTO>> businessDocumentsColumnDefinitions;
  private List<ColumnDefinition<PurchaseOrderItemDTO>> purchaseOrderItemColumnDefinition;
  
  
  public AppController(BusinessDocumentServiceAsync rpcService, HandlerManager eventBus) {
    this.eventBus = eventBus;
    this.rpcService = rpcService;
    bind();
  }
  
  private void bind() {
    History.addValueChangeHandler(this);
    eventBus.addHandler(AddBusinessDocumentEvent.TYPE , new AddBusinessDocumentEventHandler() {
		
		@Override
		public void onAddBusinessDocument(AddBusinessDocumentEvent event) {
			addBusinessDocument(event.getDocumentType());
			
		}
	});
    
    eventBus.addHandler(DocumentTypeListChangedEvent.TYPE, new DocumentTypeListChangedEventHandler() {
  		@Override
  		public void BusinessDocumentTypeListChanged(DocumentTypeListChangedEvent event) {
  			doChangeDocumentTypeList(event.getDocumentTypeCode());
  		}
    }); 
    
    eventBus.addHandler(EditBusinessDocumentEvent.TYPE, new EditBusinessDocumentEventHandler() {
  		@Override
  		public void onEditBusinessDocument(EditBusinessDocumentEvent event) {
  			doEditBusinessDocument(event.getId());
  		}
    });
    
    //TODO SA - review this event that I just added. BA:12-MAR-01
    //TODO BA - doesn't need it anymore. 
    eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
      @Override public void onLogout(LogoutEvent event) {
        GWT.log("AppController: Logout event received");
        doLogout();
      }
    });
    
  }

  private void addBusinessDocument(DocumentType documentType){
	  this.documentType = documentType;
	  History.newItem("addpo");
  }
  
  private void doEditBusinessDocument(String id) {
	  businessDocumentId = id;
	    History.newItem("editpo");
	  }
  
  private void doChangeDocumentTypeList(String id) {
	  documentTypeCode = id;
	  
	    History.newItem("listbytree" + id);
	  }

  //TODO SA review this one that I added. BA:12-MAR-01
  private void doLogout() {
    History.newItem("login");
  }
  
  public void go(final HasWidgets container) {
    this.container = container;
    
    if ("".equals(History.getToken())) {
      History.newItem("list");
    }
    else {
      History.fireCurrentHistoryState();
    }
  }

  public void onValueChange(ValueChangeEvent<String> event) {
      
      String token = event.getValue();
      
      if (token != null) {
        Presenter presenter = null;
        Presenter menuPresenter = null ;
        if (token.startsWith("listbytree")) {
          // lazily initialize our views, and keep them around to be reused
          //
          if (businessDocumentView == null) {
            //BA-2012-FEB-03 Added the second data type (DocumentType)
            //businessDocumentView = new BusinessDocumentViewImpl<BusinessDocumentDetailsDTO>();
            businessDocumentView = new BusinessDocumentViewImpl<BusinessDocumentDTO, DocumentType>();
            if (businessDocumentsColumnDefinitions == null) {
              businessDocumentsColumnDefinitions = BusinessDocumentsColumnDefinitionsFactory.getBusinessDocumentsColumnDefinitions();
            }
            businessDocumentView.setColumnDefinitions(businessDocumentsColumnDefinitions);
          }
          
          //BA-2012-FEB-03 Added the second data type
          //BA-2012-JAN-27 this line is moved here from outside of the IF (token.equals("list")) block.
          //presenter = new BusinessDocumentViewPresenter(rpcService, eventBus, businessDocumentView);
          presenter = new BusinessDocumentViewPresenter(rpcService, eventBus, businessDocumentView,documentTypeCode);
          //container = Formak.get().getMainPanel();
        }
        else if (token.equals("list")){
        	
         	menuPresenter = new DocumentTypeListPresenter(rpcService,new DocumentTypeListViewImpl<DocumentType>(),eventBus);
         	menuPresenter.go(Formak.get().getFolders());
         
        }
        else if (token.equals("add")) {
          presenter = new EditBusinessDocumentPresenter(rpcService, eventBus, new EditBusinessDocumentView());
        }
        else if (token.equals("edit")) {
          presenter = new EditBusinessDocumentPresenter(rpcService, eventBus, new EditBusinessDocumentView(), event.getValue() /*assuming this is the id*/);
        }
        else if (token.equals("editpo")) {
        	editPurchaseOrderView = new EditPurchaseOrderViewImpl<PurchaseOrderDTO,PurchaseOrderItemDTO,IDNameDTO>();
        	if (purchaseOrderItemColumnDefinition==null){
        	  purchaseOrderItemColumnDefinition = BusinessDocumentsColumnDefinitionsFactory.getPurchaseOrderItemColumnDefinitions();
        	}
        	editPurchaseOrderView.setColumnDefinitions(purchaseOrderItemColumnDefinition); 
        	
          presenter = new EditPurchaseOrderPresenter(rpcService, editPurchaseOrderView,businessDocumentId);
        }
        else if (token.equals("addpo")) {
        	editPurchaseOrderView = new EditPurchaseOrderViewImpl<PurchaseOrderDTO,PurchaseOrderItemDTO,IDNameDTO>();
        	if (purchaseOrderItemColumnDefinition==null){
        	  purchaseOrderItemColumnDefinition = BusinessDocumentsColumnDefinitionsFactory.getPurchaseOrderItemColumnDefinitions();
        	}
        	editPurchaseOrderView.setColumnDefinitions(purchaseOrderItemColumnDefinition); 
        	
          presenter = new EditPurchaseOrderPresenter(rpcService, editPurchaseOrderView,"");
        }
        else if (token.equals("login")) { //TODO SA Added this. BA:12-MAR-01
          Formak.get().showLoginView();
          return;
	      } else if (token.equals("signup")) { //TODO SA Added this. BA:12-MAR-22
	        Formak.get().showSignUpView();
	        return;
	    }
	    else if (token.equals("logout")){
	    	Window.Location.assign("/logoutgoogle");
	    }
	    else if (token.equals("home")){
	    	homeView = new HomeMainViewImpl<PurchaseOrderDTO>();
	    	 presenter = new HomeMainPresenter(homeView);
	    }
        if (presenter != null) {
          presenter.go(container);
        }
       
      } // if (token != null)
      
    } /// onValueChange 
    
    
} //AppController