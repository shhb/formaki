package org.noranj.tax.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class TopMenuImpl<T> extends Composite implements TopMenu<T>{

	// This is used to hook the presenter to view
	private Presenter<T> presenter;
	
	private static TopMenuUiBinder uiBinder = GWT.create(TopMenuUiBinder.class);

	@UiTemplate("TopMenu.ui.xml")
	interface TopMenuUiBinder extends UiBinder<Widget, TopMenuImpl> {
	}

	public TopMenuImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	// This is used to hook the presenter to view
	@Override
	public void setPresenter(Presenter<T> presenter) {
		this.presenter = presenter;
	}

}
