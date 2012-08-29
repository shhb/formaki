package org.noranj.tax.client.view;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @version 0.3.2012AUG25
 * @since 0.3.2012AUG25
 * @change 
 */
 
public interface IControlMenu<T> {

	
		public interface Presenter<T> {	}
				
		void setPresenter(Presenter<T> presenter);
		
		Widget asWidget();

	}