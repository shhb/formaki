package org.noranj.tax.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProgressBar extends Composite {

	private static ProgressBarUiBinder uiBinder = GWT.create(ProgressBarUiBinder.class);

	@UiTemplate("ProgressBar.ui.xml")
	interface ProgressBarUiBinder extends UiBinder<Widget, ProgressBar> {
	}

	public ProgressBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
