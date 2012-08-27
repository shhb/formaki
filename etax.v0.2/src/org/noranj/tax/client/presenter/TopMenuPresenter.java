package org.noranj.tax.client.presenter;

import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.client.TopMenu;


import com.google.gwt.user.client.ui.HasWidgets;

public class TopMenuPresenter implements Presenter  {

	public TopMenuPresenter(TopMenu<UserDTO> view){
		this.view = view;
	}
	private final TopMenu<UserDTO> view;
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		
	}

}
