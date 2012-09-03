package org.noranj.tax.v2012.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PublicPlace extends Composite {

	private static PublicPlaceUiBinder uiBinder = GWT
			.create(PublicPlaceUiBinder.class);

	@UiTemplate("PublicPlace.ui.xml")
	interface PublicPlaceUiBinder extends UiBinder<Widget, PublicPlace> {
	}

	public PublicPlace() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
