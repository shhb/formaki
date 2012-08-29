package org.noranj.tax.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class TopMenu<T> extends Composite {

	// This is used to hook the presenter to view

	
	private static TopMenuUiBinder uiBinder = GWT.create(TopMenuUiBinder.class);

	@UiTemplate("TopMenu.ui.xml")
	interface TopMenuUiBinder extends UiBinder<Widget, TopMenu> {
	}

	public TopMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}


}