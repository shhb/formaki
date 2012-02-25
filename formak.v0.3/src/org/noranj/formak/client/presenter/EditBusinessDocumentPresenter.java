package org.noranj.formak.client.presenter;

import org.noranj.formak.client.event.BusinessDocumentUpdatedEvent;
import org.noranj.formak.client.event.EditBusinessDocumentCancelledEvent;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.shared.dto.XBusinessDocumentDTOX;
import org.noranj.formak.shared.type.DocumentType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback; 
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Window;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * 
 * @changes
 *  BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
 */
public class EditBusinessDocumentPresenter implements Presenter{  
  
  public interface Display {
    HasClickHandlers getSaveButton();
    HasClickHandlers getCancelButton();
    HasValue<String> getBusinessDocumentName(); //it was ghetFirstName
    HasValue<String> getDescription(); // it was getLastName()
    HasValue<String> getBusinessDocumentNumber(); // it was getEmailAddress()
    HasValue<String> getId(); //BA-2012-JAN-28 Added
    
    Widget asWidget();
  }
  
  private XBusinessDocumentDTOX contact;
  private final BusinessDocumentServiceAsync rpcService;
  private final HandlerManager eventBus;
  private final Display display;
  
  public EditBusinessDocumentPresenter(BusinessDocumentServiceAsync rpcService, HandlerManager eventBus, Display display) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.contact = new XBusinessDocumentDTOX();
    this.display = display;
    bind();
  }
  
  public EditBusinessDocumentPresenter(BusinessDocumentServiceAsync rpcService, HandlerManager eventBus, Display display, String id) {
    this.rpcService = rpcService;
    this.eventBus = eventBus;
    this.display = display;
    bind();
    
    //FIXME BA
    //rpcService.getBusinessDocument(DocumentType.PurchaseOrder, new Long(id), new AsyncCallback<XBusinessDocumentDTOX>() {
    rpcService.getBusinessDocument(DocumentType.PurchaseOrder, id, new AsyncCallback<XBusinessDocumentDTOX>() {
      public void onSuccess(XBusinessDocumentDTOX result) {
        contact = result;
        EditBusinessDocumentPresenter.this.display.getId().setValue(contact.getId());
        EditBusinessDocumentPresenter.this.display.getBusinessDocumentName().setValue(contact.getBusinessDocumentName());
        EditBusinessDocumentPresenter.this.display.getDescription().setValue(contact.getDescription());
        EditBusinessDocumentPresenter.this.display.getBusinessDocumentNumber().setValue(contact.getBusinessDocumentName());
      }
      
      public void onFailure(Throwable caught) {
        Window.alert("Error retrieving contact");
      }
    });
    
  }
  
  public void bind() {
    this.display.getSaveButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        doSave();
      }
    });

    this.display.getCancelButton().addClickHandler(new ClickHandler() {   
      public void onClick(ClickEvent event) {
        eventBus.fireEvent(new EditBusinessDocumentCancelledEvent());
      }
    });
  }

  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
  }

  private void doSave() {
    contact.setBusinessDocumentName(display.getBusinessDocumentName().getValue());
    contact.setDescription(display.getDescription().getValue());
    contact.setBusinessDocumentNumber(display.getBusinessDocumentNumber().getValue());
    
    rpcService.updateBusinessDocument(contact, new AsyncCallback< XBusinessDocumentDTOX >() {
        public void onSuccess( XBusinessDocumentDTOX  result) {
          eventBus.fireEvent(new BusinessDocumentUpdatedEvent(result));
        }
        public void onFailure(Throwable caught) {
          Window.alert("Error updating contact");
        }
    });
  }
  
}
