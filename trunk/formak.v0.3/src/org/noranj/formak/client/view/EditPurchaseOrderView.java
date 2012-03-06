package org.noranj.formak.client.view;

import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.common.HasSelectedValue;
import org.noranj.formak.shared.dto.IDNameDTO;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.HasRows;

public interface EditPurchaseOrderView<T,K,L> {

	public interface Presenter<T> {
		 //public void onSaveMasterButtonClicked(T masterRowData);
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
	
	HasData<K> getPurchaseOrderItemsCellTable();
	
	HasClickHandlers getSaveButton();
	
	void setRowData(List<K> rowData);
	
	void setMasterRowData(T masterRowData);
	
	void setBuyerData(List<L> rowData);
	
	Widget asWidget();

}
