package org.noranj.formak.client.view;

import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.view.BusinessDocumentView.Presenter;
import org.noranj.formak.shared.dto.IDNameDTO;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

public interface EditPurchaseOrderView<T,K,L> {

	public interface Presenter<T> {
		 public void onSaveButtonClicked();
		 public void onAddButtonClicked();
		
	}
	
	void setPresenter(Presenter<T> presenter);
	
	void setColumnDefinitions (List<ColumnDefinition<K>> columnDefinitions);
	
	HasValue<String> getId();
	
	HasValue<String> getPONumber();
	
	HasValue<String> getPODate();
	
	HasValue<String> getShipTo();
	
	HasValue<String> getBillTo();
	
	HasValue<String> getTaxRatePercent();
	
	HasValue<String> getTotalTaxAmount();
	
	HasValue<String> getBuyer();
	
	HasValue<String> getNote();
	
	void setRowData(List<K> rowData);
	
	void setBuyerData(List<L> rowData);
	
	Widget asWidget();

}
