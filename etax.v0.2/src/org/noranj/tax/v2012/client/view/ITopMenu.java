package org.noranj.tax.v2012.client.view;

import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @version 0.3.2012AUG25
 * @since 0.3.2012AUG25
 * @change 
 */
 
public interface ITopMenu<T> {

	
		public interface Presenter<T> {	}
				
		void setPresenter(Presenter<T> presenter);
		
		Widget asWidget();

	}
