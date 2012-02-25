package org.noranj.formak.client.view;

import com.google.gwt.user.client.ui.Widget;

import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;

public interface BusinessDocumentView<T, K> {
  
	public interface Presenter<T> {
	  public void onAddButtonClicked();
	  public void onDeleteButtonClicked();
	  public void onBusinessDocumentTypeListChanged(String inputString);
	  public void onItemClicked(T clickedItem);
	  public void onItemSelected(T selectedItem);
	}

	//BA-2012-JAN-27 Added because it was missing.
	void setColumnDefinitions ( List<ColumnDefinition<T>> columnDefinitions);
	
	void setPresenter(Presenter<T> presenter);

	void setRowData(List<T> rowData);

	void setDocumentList(K[] setData);
	
	Widget asWidget();
}
