package org.noranj.formak.client.view;

import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.view.BusinessDocumentView.Presenter;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditPurchaseOrderViewImpl<T,K,L> extends Composite implements EditPurchaseOrderView<T,K,L>{

	
	@UiTemplate("EditPurchaseOrderView.ui.xml")
	interface EditPurchaseOrderViewUiBinder extends	UiBinder<Widget, EditPurchaseOrderViewImpl> {
	}

	private static EditPurchaseOrderViewUiBinder uiBinder = GWT.create(EditPurchaseOrderViewUiBinder.class);
	
	private Presenter<T> presenter;
	
	private List<ColumnDefinition<K>> columnDefinitions;
	
	private List<K> rowData;
	private List<L> rowBuyerData;
	
	public EditPurchaseOrderViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	TextBox id;
	@UiField
	TextBox poNumber;
	@UiField
	TextBox poDate;
	@UiField
	TextBox taxRatePercent;
	@UiField
	TextBox totalTaxAmount;
	@UiField
	TextArea shipTo;
	@UiField
	TextArea billTo;
	@UiField
	TextArea note;
	@UiField
	ListBox buyer;


	
	@UiField 
	FlexTable editPurchaseOrderItemsTable;
	@UiField 
	Anchor saveButton;
	@UiField 
	Anchor cancelButton;
	
	@Override
	public void setPresenter(EditPurchaseOrderView.Presenter<T> presenter) {
		this.presenter = presenter;
		
	}
	
	public void setColumnDefinitions(List<ColumnDefinition<K>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public void setRowData(List<K> rowData) {
		editPurchaseOrderItemsTable.removeAllRows();
		this.rowData = rowData;
		editPurchaseOrderItemsTable.setWidget(0, 0,	new Label("Col1"));
		editPurchaseOrderItemsTable.setWidget(0, 1,	new Label("Col2"));
		editPurchaseOrderItemsTable.setWidget(0, 2,	new Label("Col3"));
		editPurchaseOrderItemsTable.setWidget(0, 3,	new Label("Col4"));
		editPurchaseOrderItemsTable.setWidget(0, 4,	new Label("Col5"));
		editPurchaseOrderItemsTable.setWidget(0, 5,	new Label("Col6"));
		//cancelButton.sinkEvents(Event.ONCLICK);
		for (int i = 0; i < rowData.size(); ++i) {
			K k = rowData.get(i);
			for (int j = 0; j < columnDefinitions.size(); ++j) {
				ColumnDefinition<K> columnDefinition = columnDefinitions.get(j);
				editPurchaseOrderItemsTable.setWidget(i+1, j,columnDefinition.render(k));
			}
		}
	}

	public void setBuyerData(List<L> rowBuyerData){
		this.rowBuyerData = rowBuyerData;
		for (int i = 0; i < rowBuyerData.size(); ++i) {
			buyer.addItem("","");
		}
	}
	
	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event) {
	    if (presenter != null) {
	      presenter.onSaveButtonClicked();
	    }
	  }
	
	@Override
	public HasValue<String> getId() {
		return id;
	}

	public HasValue<String> getPONumber(){
		return poNumber;
	}
	
	public HasValue<String> getPODate(){
		return poDate;
	}
	
	public HasValue<String> getTaxRatePercent(){
		return taxRatePercent;
	}
	
	public HasValue<String> getTotalTaxAmount(){
		return totalTaxAmount ;
	}
	
	public HasValue<String> getShipTo(){
		return shipTo;
	}
	
	public HasValue<String> getBillTo(){
		return billTo;
	}

	public HasValue<String> getNote(){
		return note;
	}
	
	public HasValue<String> getBuyer(){
		return null ; //buyer.getItemText(buyer.getSelectedIndex());
	}
	
	@Override //FIXME: 2012-2-15 ; SA:it does not work properly.
	public void onBrowserEvent(Event event) {
		 Window.alert(Integer.toString(DOM.eventGetType(event)));
	}
}
