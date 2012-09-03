package org.noranj.tax.v2012.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @version 0.3.2012AUG19
 * @since 0.3.2012AUG19
 * @change 
 */
public interface ILoginView<T> {
	
	public interface Presenter<T> {
		public void onGoogleButtonClicked();
	}
	
	HasClickHandlers getGoogleButton(); 
		
	void setPresenter(Presenter<T> presenter);
	
	Widget asWidget();
  }