/** 
 * Copyright 2010 Daniel Guermeur and Amy Unruh
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://connectrapp.appspot.com/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package org.noranj.formak.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

import org.noranj.formak.client.presenter.SignUpPresenter;

//TODO SA : need to implement this page later
/**
 * 
 * @version 0.3.20120322
 * @since 0.3.20120322
 * @change 
 *  BA:12-MAR-22 Copied from LoginView
 */
public class SignUpView extends Composite implements SignUpPresenter.Display {
  
	
  @UiField PushButton signUpButton;
  
  private static UserBadgeUiBinder uiBinder = GWT
      .create(UserBadgeUiBinder.class);

  interface UserBadgeUiBinder extends UiBinder<Widget, SignUpView> {
  }

  public SignUpView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  /* BA:12-03-01 Commented out
  @Override
  public HasClickHandlers getFacebookButton() {
    return facebookButton;
  }
  @Override
  public HasClickHandlers getTwitterButton() {
    return twitterButton;
  }
  */
  
  @Override
  public HasClickHandlers getSignUpButton() {
    return signUpButton;
  }

}
