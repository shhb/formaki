package org.noranj.easytax.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ControlMenu extends Composite {

	private static ControlMenuUiBinder uiBinder = GWT.create(ControlMenuUiBinder.class);

	interface ControlMenuUiBinder extends UiBinder<Widget, ControlMenu> {	}

	public ControlMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
