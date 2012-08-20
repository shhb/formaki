package org.noranj.tax.client;


import org.noranj.tax.client.presenter.Presenter;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

public class AppController implements Presenter, ValueChangeHandler<String> {
  private final HandlerManager eventBus;
  private HasWidgets container;

  public AppController( HandlerManager eventBus) {
    this.eventBus = eventBus;
    //this.rpcService = rpcService;
    bind();
  }
  
  private void bind() {
    History.addValueChangeHandler(this);
    
    
  }

  //TODO SA review this one that I added. BA:12-MAR-01
  private void doLogout() {
    History.newItem("login");
  }
  
  public void go(final HasWidgets container) {
    this.container = container;
    
    if ("".equals(History.getToken())) {
      History.newItem("home");
    }
    else {
      History.fireCurrentHistoryState();
    }
  }

  public void onValueChange(ValueChangeEvent<String> event) {
      
      String token = event.getValue();
      
      if (token != null) {
        Presenter presenter = null;

        if (token.equals("home")) {

        }
        else if  (token.equals("signup")) { 
          Tax.get().showSignUpView();
        }
        else if (presenter != null) {
          presenter.go(container);
        }
       
      } // if (token != null)
      
    } /// onValueChange 
    
    
} //AppController