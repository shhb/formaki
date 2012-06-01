package org.noranj.formak.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class HomeMainViewImpl<T> extends Composite implements HomeMainView<T> {


	@UiTemplate("HomeMainView.ui.xml")
	interface HomeViewUiBinder extends UiBinder<Widget, HomeMainViewImpl> {	}
	
	private static HomeViewUiBinder uiBinder = GWT.create(HomeViewUiBinder.class);
	
	private Presenter<T> presenter;
	
	@Override
	public void setPresenter(HomeMainView.Presenter<T> presenter) {
		this.presenter = presenter;

	}
	
	public HomeMainViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
