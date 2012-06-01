package org.noranj.formak.client.view;

import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.common.HasSelectedValue;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.google.gwt.view.client.HasData;

public interface EditPurchaseOrderView<T,K,L> {

	public interface Presenter<T> {
		 public void onAddButtonClicked();
	}
	
	void setPresenter(Presenter<T> presenter);
	
	void setColumnDefinitions (List<ColumnDefinition<K>> columnDefinitions);
	
	HasValue<String> getId();
	
	HasValue<String> getPONumber();
	
	HasValue<String> getPODate();
	
	HasValue<String> getShipTo();
	
	HasValue<String> getBillTo();
	
	HasText getTaxRatePercent();
	
	HasValue<String> getTotalTaxAmount();
	
	HasSelectedValue<L> getBuyer();
	
	HasValue<String> getNote();
	
	HasClickHandlers getSaveButton();
	
	HasClickHandlers getAddNewRowButton();
	
	HasData getDataTable();
	
	void displayeTable();
	
	void setMasterData(T masterData);
	
	void setBuyerData(List<L> rowData);
	
	HasCellPreviewHandlers<K> getList();
	
	CellTable<K> getCellTable();
	
	Widget asWidget();

}
