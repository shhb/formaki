package org.noranj.formak.client.view;


import com.google.gwt.user.client.ui.Widget;

public interface HomeMainView<T> {
	
	public interface Presenter<T> {
		 public void onAddButtonClicked();
	}
	
	void setPresenter(Presenter<T> presenter);
	
	Widget asWidget();
}
