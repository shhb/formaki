/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noranj.tax.client;

import org.noranj.tax.client.presenter.LoginPresenter;
//import org.noranj.tax.client.view.LoginView;
import org.noranj.idnt.shared.dto.IDNameDTO;
import org.noranj.idnt.shared.dto.SystemUserDTO;
import org.noranj.tax.client.helper.RPCCall;
import org.noranj.tax.client.presenter.Presenter;
import org.noranj.tax.client.presenter.UserDefinitionPresenter;
import org.noranj.tax.client.service.SystemAdminService;
import org.noranj.tax.client.service.SystemAdminServiceAsync;
import org.noranj.tax.client.view.LoginView;
import org.noranj.tax.client.view.LoginViewImpl;
import org.noranj.tax.client.view.UserDefinitionViewImpl;
import org.noranj.tax.shared.GlobalSettings;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tax implements EntryPoint {

  private static final TaxUiBinder uiBinder = GWT.create(TaxUiBinder.class);

  interface TaxUiBinder extends UiBinder<DockLayoutPanel, Tax> {
  }

  RootLayoutPanel root;

  private SimpleEventBus eventBus = new SimpleEventBus();

  private SystemAdminServiceAsync systemAdminService = GWT.create(SystemAdminService.class);

  private SystemUserDTO currentUser;

  private static Tax singleton;

  public static Tax get() {
    return singleton;
  }

  public SimpleEventBus getEventBus() {
    return eventBus;
  }

  public void onModuleLoad() {
    /*
     * singleton = this; DockLayoutPanel outer = uiBinder.createAndBindUi(this);
     * root = RootLayoutPanel.get(); root.clear(); root.add(outer);
     */
    singleton = this;

    getLoggedInUser();

  }

  private void getLoggedInUser() {

    new RPCCall<SystemUserDTO>() {

      @Override
      protected void callService(AsyncCallback<SystemUserDTO> cb) {
        systemAdminService.getLoggedInUserDTO(cb);
      }

      @Override
      public void onSuccess(SystemUserDTO loggedInUserDTO) {
        if (loggedInUserDTO == null) {
          // nobody is logged in
          if ("".equals(History.getToken())) {
            showLoginView();
          } else {
            SystemAdminServiceAsync rpc = GWT.create(SystemAdminService.class);
            UserDefinitionViewImpl<SystemUserDTO, IDNameDTO> userDefinitionView = new UserDefinitionViewImpl<SystemUserDTO, IDNameDTO>();
            Presenter presenter = new UserDefinitionPresenter(
                userDefinitionView, "", rpc);
            presenter.go(RootLayoutPanel.get());
          }

        } else {
          // user is logged in
          setCurrentUser(loggedInUserDTO);
          createUI();
        }
      }

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Error: " + caught.getMessage());
      }
    }.retry(GlobalSettings.C_RPC_CALL_RETRY);

  }

  public void showLoginView() {
    root = RootLayoutPanel.get();
    root.clear();
    LoginView<SystemUserDTO> view = new LoginViewImpl<SystemUserDTO>();
    LoginPresenter loginPresenter = new LoginPresenter(eventBus, view);
    loginPresenter.go(root);
  }

  void setCurrentUser(SystemUserDTO currentUser) {
    this.currentUser = currentUser;
  }

  private void createUI() {

    GWT.runAsync(new RunAsyncCallback() {
      @Override
      public void onFailure(Throwable reason) {
        Window.alert("Code download error: " + reason.getMessage());
      }

      @Override
      public void onSuccess() {
        goAfterLogin();
        // eventBus.fireEvent(new LoginEvent(currentUser));
      }
    });
  }

  private void goAfterLogin() {

    DockLayoutPanel outer = uiBinder.createAndBindUi(this);
    root = RootLayoutPanel.get();
    root.clear();
    root.add(outer);
    //HandlerManager eventBus = new HandlerManager(null);
    //AppController appViewer = new AppController(rpcService, eventBus);
   // appViewer.go(getMainPanel());
  }

  public void showSignUpView() {
    root = RootLayoutPanel.get();
    root.clear();
    /*SignUpPresenter signupPresenter = new SignUpPresenter(eventBus, new SignUpView());
    signupPresenter.go(root);*/
  }
 
  SystemUserDTO getCurrentUser() {
    return currentUser;
  }
}
