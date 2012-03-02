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
package org.noranj.formak.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @version 0.3.2012-MAR-01
 * @since 0.3.2012-MAR-01
 * @change
 *  
 */
public class LoginPresenter implements Presenter {
  
  public interface Display {
    HasClickHandlers getGoogleButton();

    /* BA:12-MAR-01 Commented out
    HasClickHandlers getTwitterButton();
    HasClickHandlers getFacebookButton();
    */
    
    Widget asWidget();
  }

  private final Display display;


  public LoginPresenter(SimpleEventBus eventBus, Display display) {
    this.display = display;
  }

  public void bind() {

    this.display.getGoogleButton().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        doLoginGoogle();
      }
    });

    /* BA:12-MAR-01 Commented out
    this.display.getTwitterButton().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        doLoginTwitter();
      }
    });

    this.display.getFacebookButton().addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        doLoginFacebook();
      }
    });
    */
  }

  public void go(final HasWidgets container) {
    container.clear();
    container.add(display.asWidget());
    bind();
  }

  private void doLoginGoogle() {
    Window.Location.assign("/logingoogle");
  }

  /*  BA:12-MAR-01 Commented out
  private void doLoginFacebook() {
    Window.Location.assign("/loginfacebook");
  }
  
  private void doLoginTwitter() {
    Window.Location.assign("/logintwitter");
  }
  */

}
