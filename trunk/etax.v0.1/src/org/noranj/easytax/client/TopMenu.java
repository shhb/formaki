package org.noranj.easytax.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TopMenu extends Composite {

	private static TopMenuUiBinder uiBinder = GWT.create(TopMenuUiBinder.class);

	interface TopMenuUiBinder extends UiBinder<Widget, TopMenu> {
	}

	public TopMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
