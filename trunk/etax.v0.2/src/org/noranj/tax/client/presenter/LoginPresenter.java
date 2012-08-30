package org.noranj.tax.client.presenter;

import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.client.view.ILoginView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * 
 * @version 0.3.2012-Aug-19
 * @since 0.3.2012-Aug-19
 * @change
 * 
 */
public class LoginPresenter implements Presenter {
	
	private final ILoginView<UserDTO> view;

	public LoginPresenter(SimpleEventBus eventBus, ILoginView<UserDTO> view) {
		this.view = view;
		bind();
	}

	public void bind() {

		this.view.getGoogleButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLoginGoogle();
			}
		});

		/*
		 * BA:12-AUG-19 Commented out
		 * this.view.getTwitterButton().addClickHandler(new ClickHandler() {
		 * public void onClick(ClickEvent event) { doLoginTwitter(); } });
		 * 
		 * this.display.getFacebookButton().addClickHandler(new ClickHandler() {
		 * public void onClick(ClickEvent event) { doLoginFacebook(); } });
		 */
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		bind();
	}

	private void doLoginGoogle() {
		Window.Location.assign("/logingoogle");
	}

	/*
	 * BA:12-MAR-01 Commented out private void doLoginFacebook() {
	 * Window.Location.assign("/loginfacebook"); }
	 * 
	 * private void doLoginTwitter() { Window.Location.assign("/logintwitter");
	 * }
	 */

}
