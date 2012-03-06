package org.noranj.formak.client.view;

import java.util.List;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.common.HasSelectedValue;
import org.noranj.formak.client.common.SelectOneListBox;
import org.noranj.formak.client.common.SelectOneListBox.OptionFormatter;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.ListDataProvider;

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

	@UiField() 
	CellTable<K> purchaseOrderItemsCellTable;
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
		
        CellTable<PurchaseOrderItemDTO> purchaseOrderItemsCellTable = new CellTable<PurchaseOrderItemDTO>();
        purchaseOrderItemsCellTable = (CellTable<PurchaseOrderItemDTO>) this.purchaseOrderItemsCellTable;
        Column<PurchaseOrderItemDTO,String> sequenceHolder = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return Integer.toString(object.getSequenceHolder());
			}

		};
		
		Column<PurchaseOrderItemDTO,String> gtnID = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
		
			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return object.getGTIN();
			}
		  };
		
		Column<PurchaseOrderItemDTO,String> description = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return object.getDescription();
			}
		};
		
		Column<PurchaseOrderItemDTO,String> uom = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
		
			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return object.getUom();
			}
		  };
		
		Column<PurchaseOrderItemDTO,String> quantity = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
		
			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return Integer.toString(object.getQuantity());
			}
		  };
		  
		Column<PurchaseOrderItemDTO,String> price = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
		
			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return Long.toString(object.getPrice());
			}
		  };
		  
		Column<PurchaseOrderItemDTO,String> total = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

	@Override
	public String getValue(PurchaseOrderItemDTO object) {
		return Long.toString(object.getQuantity()*object.getPrice());
	}
  };
  
  		description.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO,String>(){
	@Override
	public void update(int index, PurchaseOrderItemDTO object,String value) {
		object.setDescription(value);	
	}
  });

		sequenceHolder.setSortable(true);
  		purchaseOrderItemsCellTable.addColumn(sequenceHolder,new TextHeader("Seq"));
		purchaseOrderItemsCellTable.setColumnWidth(sequenceHolder, 1, Unit.PCT);
		
		gtnID.setSortable(true);
		purchaseOrderItemsCellTable.addColumn(gtnID,new TextHeader("GTN"));
		purchaseOrderItemsCellTable.setColumnWidth(gtnID, 1, Unit.PCT);
		
		description.setSortable(true);
		purchaseOrderItemsCellTable.addColumn(description,new TextHeader("description"));
		purchaseOrderItemsCellTable.setColumnWidth(description, 100, Unit.PX);
		
		uom.setSortable(true); 
		purchaseOrderItemsCellTable.addColumn(uom,new TextHeader("uom"));
		purchaseOrderItemsCellTable.setColumnWidth(uom, 100, Unit.PX);
		
		quantity.setSortable(true);
		purchaseOrderItemsCellTable.addColumn(quantity,new TextHeader("quantity"));
		purchaseOrderItemsCellTable.setColumnWidth(quantity, 1, Unit.PCT);
		
		price.setSortable(true);
		purchaseOrderItemsCellTable.addColumn(price,new TextHeader("price"));
		purchaseOrderItemsCellTable.setColumnWidth(price, 1, Unit.PCT);
		
		
		purchaseOrderItemsCellTable.addColumn(total,new TextHeader("total"));
		purchaseOrderItemsCellTable.setColumnWidth(total, 1, Unit.PCT);
			        	
		ListDataProvider<PurchaseOrderItemDTO> lda = new ListDataProvider<PurchaseOrderItemDTO>();
		ListHandler<PurchaseOrderItemDTO> sortHandler = new ListHandler<PurchaseOrderItemDTO>(lda.getList());
		purchaseOrderItemsCellTable.addColumnSortHandler(sortHandler);
		lda.setList((List<PurchaseOrderItemDTO>) rowData);
		lda.addDataDisplay(purchaseOrderItemsCellTable);
		
		
	}

	public void setBuyerData(List<L> rowBuyerData){
		this.rowBuyerData = rowBuyerData;
		for (int i = 0; i < rowBuyerData.size(); ++i) {
			IDNameDTO row = (IDNameDTO) rowBuyerData.get(i);
			buyer.addItem(row.getName().toString(),row.getId().toString());
			
			}
		}
	
//	//@UiHandler("saveButton")
//	void onSaveButtonClicked(ClickEvent event) {
//		
//	    if (presenter != null) {
//	      //presenter.onSaveMasterButtonClicked(masterRowData);
//	    }
//	  }
	
	public HasData<K> getPurchaseOrderItemsCellTable(){
		return purchaseOrderItemsCellTable;
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
	
	@Override
	public HasClickHandlers getSaveButton(){
		return saveButton;
	}
	
	public void setMasterRowData(T masterRowData ){
		this.masterRowData = masterRowData;
	}
	
	@Override //FIXME: 2012-2-15 ; SA:it does not work properly.
	public void onBrowserEvent(Event event) {
		 Window.alert(Integer.toString(DOM.eventGetType(event)));
	}
}
