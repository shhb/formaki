package org.noranj.formak.client.view;

import java.util.Collection;
import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.common.HasSelectedValue;
import org.noranj.formak.client.common.SelectOneListBox;
import org.noranj.formak.client.common.SelectOneListBox.OptionFormatter;
import org.noranj.formak.client.view.BusinessDocumentView.Presenter;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
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
	
	private T masterRowData;
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
	Label taxRatePercent;
	@UiField
	TextBox totalTaxAmount;
	@UiField
	TextArea shipTo;
	@UiField
	TextArea billTo;
	@UiField
	TextArea note;
	
	@UiField()
	SelectOneListBox<L> buyer;
	
	@UiFactory SelectOneListBox<IDNameDTO> initSelect(){
		return new SelectOneListBox<IDNameDTO>(new OptionFormatter<IDNameDTO>(){

			@Override
			public String getLabel(IDNameDTO option) {
				return option.getName();
			}

			@Override
			public String getValue(IDNameDTO option) {
				return option.getId();
			}});
		
	}

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
		StringBuilder sb = new StringBuilder();
		//editPurchaseOrderItemsTable.setWidget(0,1,new HTML("<div width='80px' style='border-bottom: 1px solid #E5E5E5'>#</div>"));
	  	sb.append("<table width='100%'>");
    	sb.append("<tr>");
		sb.append("<td width='20px'>Seq</td>");
		sb.append("<td width='80px'>ItemID</td>");
		sb.append("<td width='200px'>Description</td>");
		sb.append("<td width='80px'>Unit</td>");
		sb.append("<td width='80px'>Quantity</td>");
		sb.append("<td width='80px'>Price</td>");
		sb.append("<td width='80px'>Total</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan='5' style='border-top: 1px solid #E5E5E5'></td>");
		sb.append("</tr>");
		sb.append("</table>");
		editPurchaseOrderItemsTable.setWidget(0,1,new HTML(sb.toString()));
		//cancelButton.sinkEvents(Event.ONCLICK);
		int i ;
		for (i = 0; i < rowData.size(); ++i) {
			K k = rowData.get(i);
			for (int j = 0; j < columnDefinitions.size(); ++j) {
				ColumnDefinition<K> columnDefinition = columnDefinitions.get(j);
				editPurchaseOrderItemsTable.setWidget(i+1, j, columnDefinition.render(k));
			}
		}
		sb.delete(0, sb.length());
		sb.append("<table width='100%'>");
    	sb.append("<tr>");
    	sb.append("<td colspan='5' style='border-top: 1px solid #E5E5E5'></td>");
		sb.append("</tr>");
		sb.append("</table>");
		editPurchaseOrderItemsTable.setWidget(i+1,1,new HTML(sb.toString()));
	}

	public void setBuyerData(List<L> rowBuyerData){
		this.rowBuyerData = rowBuyerData;
		for (int i = 0; i < rowBuyerData.size(); ++i) {
			IDNameDTO row = (IDNameDTO) rowBuyerData.get(i);
			buyer.addItem(row.getName().toString(),row.getId().toString());
			
			}
		}
	
	
	@UiHandler("saveButton")
	void onSaveButtonClicked(ClickEvent event) {
		
	    if (presenter != null) {
	      presenter.onSaveMasterButtonClicked(masterRowData);
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
	
	public HasText getTaxRatePercent(){
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
	
	public HasSelectedValue<L> getBuyer(){
		return buyer;
	}
	
	public void setMasterRowData(T masterRowData ){
		this.masterRowData = masterRowData;
	}
	
	@Override //FIXME: 2012-2-15 ; SA:it does not work properly.
	public void onBrowserEvent(Event event) {
		 Window.alert(Integer.toString(DOM.eventGetType(event)));
	}
}
