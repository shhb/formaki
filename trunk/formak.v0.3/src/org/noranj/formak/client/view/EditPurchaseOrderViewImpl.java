package org.noranj.formak.client.view;

import java.util.ArrayList;
import java.util.List;

import org.noranj.formak.client.common.BusinessDocumentsColumnDefinitionsFactory;
import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.common.HasSelectedValue;
import org.noranj.formak.client.common.SelectOneListBox;
import org.noranj.formak.client.common.SelectOneListBox.OptionFormatter;
import org.noranj.formak.client.resources.en.GlobalResources;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasCellPreviewHandlers;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class EditPurchaseOrderViewImpl<T, K, L> extends Composite implements EditPurchaseOrderView<T, K, L> {

	@UiTemplate("EditPurchaseOrderView.ui.xml")
	interface EditPurchaseOrderViewUiBinder extends
			UiBinder<Widget, EditPurchaseOrderViewImpl> {
	}

	private static EditPurchaseOrderViewUiBinder uiBinder = GWT
			.create(EditPurchaseOrderViewUiBinder.class);

	private Presenter<T> presenter;

	private List<ColumnDefinition<K>> columnDefinitions;

	private T masterData;
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
	
	@UiFactory
	SelectOneListBox<IDNameDTO> initSelect() {
		return new SelectOneListBox<IDNameDTO>(
				new OptionFormatter<IDNameDTO>() {

					@Override
					public String getLabel(IDNameDTO option) {
						return option.getName();
					}

					@Override
					public String getValue(IDNameDTO option) {
						return option.getId();
					}
				});

	}
	
	@UiField()
	CellTable<K> purchaseOrderItemsCellTable;
	
	@UiField
	Anchor saveButton;
	
	@UiField
	Anchor cancelButton;
	
	@UiField
	Anchor addNewRowButton;
	
	@Override
	public void setPresenter(EditPurchaseOrderView.Presenter<T> presenter) {
		this.presenter = presenter;

	}

	@Override
	public HasValue<String> getId() {
		return id;
	}

	public HasValue<String> getPONumber() {
		return poNumber;
	}

	public HasValue<String> getPODate() {
		return poDate;
	}

	public HasText getTaxRatePercent() {
		return taxRatePercent;
	}

	public HasValue<String> getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public HasValue<String> getShipTo() {
		return shipTo;
	}

	public HasValue<String> getBillTo() {
		return billTo;
	}

	public HasValue<String> getNote() {
		return note;
	}

	public HasSelectedValue<L> getBuyer() {
		return buyer;
	}

	@Override
	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	@Override
	public HasClickHandlers getAddNewRowButton() {
		return addNewRowButton;
	}

	
	public void setMasterData(T masterData) {
		this.masterData = masterData;
	}

	@Override
	// XXX:SA: 2012-2-15:it does not work properly.
	public void onBrowserEvent(Event event) {
		Window.alert(Integer.toString(DOM.eventGetType(event)));
	}

	public void setColumnDefinitions(List<ColumnDefinition<K>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public void setBuyerData(List<L> rowBuyerData) {
		buyer.setSelections(rowBuyerData);
	}

	public void displayeTable() {
		
		CellTable<PurchaseOrderItemDTO> purchaseOrderItemsCellTable = (CellTable<PurchaseOrderItemDTO>) this.purchaseOrderItemsCellTable;
		purchaseOrderItemsCellTable.setWidth("100%", true);

		Column<PurchaseOrderItemDTO, ImageResource> remove = new Column<PurchaseOrderItemDTO, ImageResource>(new ImageResourceCell()){
			@Override
			public ImageResource getValue(PurchaseOrderItemDTO object) {

				return GlobalResources.RESOURCE.delete();
			}
		};
				
		remove.setCellStyleNames(GlobalResources.RESOURCE.globalStyles().clickable());
		Column<PurchaseOrderItemDTO, String> sequenceHolder = new Column<PurchaseOrderItemDTO, String>(	new TextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return Integer.toString(object.getSequenceHolder());
			}

		};

		Column<PurchaseOrderItemDTO, String> gtin = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return object.getGTIN();
			}
		};

		Column<PurchaseOrderItemDTO, String> description = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return object.getDescription();
			}
		};

		Column<PurchaseOrderItemDTO, String> uom = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return object.getUom();
			}
		};

		Column<PurchaseOrderItemDTO, String> quantity = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return String.valueOf(object.getQuantity());
			}
		};

		Column<PurchaseOrderItemDTO, String> price = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
					return String.valueOf(object.getPrice());
			}

		};

		Column<PurchaseOrderItemDTO, String> total = new Column<PurchaseOrderItemDTO, String>(new TextCell()) {

			@Override
			public String getValue(PurchaseOrderItemDTO object) {
				return Long.toString(object.getQuantity() * object.getPrice());
			}
		};

		description.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO, String>() {
			@Override
			public void update(int index, PurchaseOrderItemDTO object, String value) {
				object.setDescription(value);
			}
		});
		gtin.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO, String>(){
			@Override
			public void update(int index, PurchaseOrderItemDTO object, String value) {
				object.setGTIN(value);
			}
		});
		quantity.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO, String>(){
			@Override
			public void update(int index, PurchaseOrderItemDTO object, String value) {
				object.setQuantity(Integer.valueOf(value));
			}
		});
		price.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO, String>(){
			@Override
			public void update(int index, PurchaseOrderItemDTO object, String value) {
				object.setPrice(Long.valueOf(value));
			}
		});
		uom.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO, String>(){
			@Override
			public void update(int index, PurchaseOrderItemDTO object, String value) {
				object.setUOM(value);
			}
		});
			
		purchaseOrderItemsCellTable.addColumn(sequenceHolder, new TextHeader("ID"));
		purchaseOrderItemsCellTable.setColumnWidth(sequenceHolder, 10, Unit.PX);

		
		purchaseOrderItemsCellTable.addColumn(gtin, new TextHeader("GTIN"));
		purchaseOrderItemsCellTable.setColumnWidth(gtin, 50, Unit.PX );

		
		purchaseOrderItemsCellTable.addColumn(description, new TextHeader("Desc"));
		purchaseOrderItemsCellTable.setColumnWidth(description, 50, Unit.PX);

		
		purchaseOrderItemsCellTable.addColumn(uom, new TextHeader("UOM"));
		purchaseOrderItemsCellTable.setColumnWidth(uom, 15, Unit.PX);

		
		purchaseOrderItemsCellTable.addColumn(quantity, new TextHeader("Quantity"));
		purchaseOrderItemsCellTable.setColumnWidth(quantity, 20, Unit.PX);

		
		purchaseOrderItemsCellTable.addColumn(price, new TextHeader("Price"));
		purchaseOrderItemsCellTable.setColumnWidth(price, 30, Unit.PX);

		purchaseOrderItemsCellTable.addColumn(total, new TextHeader("Total"));
		purchaseOrderItemsCellTable.setColumnWidth(total, 40, Unit.PX);

		purchaseOrderItemsCellTable.addColumn(remove,new TextHeader(""));
		purchaseOrderItemsCellTable.setColumnWidth(remove, 20, Unit.PX);
		
	
	}

	public HasData getDataTable(){
		return purchaseOrderItemsCellTable;
	}
    
	@Override
    public HasCellPreviewHandlers<K> getList() {
	    return purchaseOrderItemsCellTable;
	}
    
	public CellTable<K> getCellTable(){
		return this.purchaseOrderItemsCellTable;
	}
}
