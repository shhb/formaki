package org.noranj.tax.v2012.client.presenter;

import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.v2012.client.view.ITopMenu;


import com.google.gwt.user.client.ui.HasWidgets;

public class TopMenuPresenter implements Presenter  {

	public TopMenuPresenter(ITopMenu<UserDTO> view){
		this.view = view;
	}
	private final ITopMenu<UserDTO> view;
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		
	}

}
