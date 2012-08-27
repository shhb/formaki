package org.noranj.tax.client;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @version 0.3.2012AUG25
 * @since 0.3.2012AUG25
 * @change 
 */
 
public interface TopMenu<T> {

	
	public interface Presenter<T> {
		
	}
			
	void setPresenter(Presenter<T> presenter);
	
	Widget asWidget();

}