package org.noranj.formak.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

import org.noranj.formak.client.view.HomeMainView;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;


public class HomeMainPresenter implements Presenter, HomeMainView.Presenter<PurchaseOrderDTO> {
	
	private final HomeMainView<PurchaseOrderDTO> view;
	
	public HomeMainPresenter(HomeMainView<PurchaseOrderDTO> view){
		this.view = view;
		this.view.setPresenter(this);
	}
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}
	@Override
	public void onAddButtonClicked() {
		// TODO Auto-generated method stub
		
	}
}
