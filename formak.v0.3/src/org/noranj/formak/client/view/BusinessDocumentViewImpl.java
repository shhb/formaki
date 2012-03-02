package org.noranj.formak.client.view;

import java.awt.Color;
import java.util.List;

import javax.swing.border.LineBorder;

import org.noranj.formak.client.common.ColumnDefinition;
import org.noranj.formak.client.resources.en.GlobalResources;
import org.noranj.formak.shared.type.DocumentType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * @author
 * @param <T>
 * @modified BA-2012-JAN-27 renamed documentList to businessDocumentListTable. I
 *           added this UIField name in the XML file for table widget.
 *           SA-2012-FEB-27 renamed businessDocumentListTable to
 *           businessDocumentViewTable.
 */

public class BusinessDocumentViewImpl<T, K> extends Composite implements  BusinessDocumentView<T, K> {

	@UiTemplate("BusinessDocumentView.ui.xml")
	interface BusinessDocumentViewUiBinder extends	UiBinder<Widget, BusinessDocumentViewImpl> {
	}

	private static BusinessDocumentViewUiBinder uiBinder = GWT.create(BusinessDocumentViewUiBinder.class);

	@UiField
	ListBox businessDocumentTypeList;
	
	@UiField
	Anchor addButton;
	
	@UiField
	Anchor deleteButton;

	@UiField
	FlexTable businessDocumentViewTable; // it was businessDocumentListTable

	// This is used to hook the presenter to view
	private Presenter<T> presenter;

	private K[] setData;

	private List<T> rowData;

	// BA-2012-JAN-27 Added to use more complex model data structure
	private List<ColumnDefinition<T>> columnDefinitions;

	public BusinessDocumentViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	// This is used to hook the presenter to view
	@Override
	public void setPresenter(Presenter<T> presenter) {
		this.presenter = presenter;
	}

	public void setRowData(List<T> rowData) {
		businessDocumentViewTable.removeAllRows();
		this.rowData = rowData;
		StringBuilder sb = new StringBuilder();
		businessDocumentViewTable.setWidget(0,0,new CheckBox());
		businessDocumentViewTable.setWidget(0,1,new HTML("<div width='80px' style='border-bottom: 1px solid #E5E5E5'>#</div>"));
	  	sb.append("<table width='100%'>");
    	sb.append("<tr>");
		sb.append("<td width='80px'>ReceiverParty</td>");
		sb.append("<td width='80px'>DocumentNumber</td>");
		sb.append("<td width='80px'>Monetory</td>");
		sb.append("<td width='80px'>ImportantDate</td>");
		sb.append("<td width='200px'>Note</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan='5' style='border-top: 1px solid #E5E5E5'></td>");
		sb.append("</tr>");
		sb.append("</table>");
		businessDocumentViewTable.setWidget(0,2,new HTML(sb.toString()));
		//businessDocumentViewTable.setStyleName(GlobalResources.RESOURCE.globalStyles().flexTable());
		for (int i = 0; i < rowData.size(); ++i) {
			T t = rowData.get(i);
			for (int j = 0; j < columnDefinitions.size(); ++j) {
				ColumnDefinition<T> columnDefinition = columnDefinitions.get(j);
				businessDocumentViewTable.setWidget(i+1, j,	columnDefinition.render(t));

			}
		}
	}

	public void setDocumentList(K[] setData) {

		businessDocumentTypeList.clear();
		this.setData = setData;
		for (int i = 0; i < setData.length; ++i) {
			businessDocumentTypeList.addItem(setData[i].toString(), ((DocumentType)setData[i]).codeToString()); //FIXME (not big deal) it uses the DocumentType although the idea was to not use any specific data type and instead using K as template.
		}
		
	}

	// BA-2012-JAN-27 Added to use more complex model data structure
	public void setColumnDefinitions(List<ColumnDefinition<T>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
	// /// wire up the UI interactions within the BusinessDocumentList view via
	// the UiHandler annotation /////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
	@UiHandler("addButton")
	void onAddButtonClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onAddButtonClicked();
		}
	}

	@UiHandler("deleteButton")
	void onDeleteButtonClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onDeleteButtonClicked();
		}
	}

	
	@UiHandler("businessDocumentTypeList")
	void onBusinessDocumentTypeListChanged(ChangeEvent event)
	{ //com.google.gwt.event.dom.client.ChangeEvent
		if (presenter != null) {
			presenter.onBusinessDocumentTypeListChanged(businessDocumentTypeList.getValue(businessDocumentTypeList.getSelectedIndex()));
		}
	}

	@UiHandler("businessDocumentViewTable")
	void onTableClicked(ClickEvent event) {
		if (presenter != null) {
			HTMLTable.Cell cell = businessDocumentViewTable.getCellForEvent(event);

			if (cell != null) {
				if (shouldFireClickEvent(cell)) {
					presenter.onItemClicked(rowData.get(cell.getRowIndex()-1));
				}

				if (shouldFireSelectEvent(cell)) {
					presenter.onItemSelected(rowData.get(cell.getRowIndex()-1));

				}
			}
		}
	}

	private boolean shouldFireClickEvent(HTMLTable.Cell cell) {
		boolean shouldFireClickEvent = false;

		if (cell != null) {
			ColumnDefinition<T> columnDefinition = columnDefinitions.get(cell.getCellIndex());

			if (columnDefinition != null) {
				shouldFireClickEvent = columnDefinition.isClickable();
			}
		}

		return shouldFireClickEvent;
	}


	private boolean shouldFireSelectEvent(HTMLTable.Cell cell) {
		boolean shouldFireSelectEvent = false;

		if (cell != null) {
			ColumnDefinition<T> columnDefinition = columnDefinitions.get(cell.getCellIndex());

			if (columnDefinition != null) {
				shouldFireSelectEvent = columnDefinition.isSelectable();
			}
		}

		return shouldFireSelectEvent;
	}

}
