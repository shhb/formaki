package org.noranj.formak.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class DocumentTypeListViewImpl<T> extends Composite implements
		DocumentTypeListView<T> {

	private Presenter<T> presenter;

	@UiTemplate("DocumentTypeListView.ui.xml")
	interface BusinessDocumentListViewUiBinder extends
			UiBinder<Widget, DocumentTypeListViewImpl> {

	}

	@UiField
	Tree businessDocumentTypeTree;

	private static BusinessDocumentListViewUiBinder uiBinder = GWT
			.create(BusinessDocumentListViewUiBinder.class);

	public DocumentTypeListViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private T[] setData;

	@Override
	public void setPresenter(Presenter<T> presenter) {
		this.presenter = presenter;
	}
	
	public void setDocumentList(T[] setData) {

		businessDocumentTypeTree.clear();
		this.setData = setData;
		TreeItem treeItem = new TreeItem("Business Documents");
		for (int i = 0; i < setData.length; ++i) {

			 treeItem.addItem(setData[i].toString());
			 treeItem.setState(true); 
			 businessDocumentTypeTree.addItem(treeItem);

		}
		 businessDocumentTypeTree.addSelectionHandler(new SelectionHandler<TreeItem>() {

			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				  if (presenter != null) {
					  presenter.onBusinessDocumentTypeListChanged(event.getSelectedItem().getText());
				}
				
			}		 
			 
		 });		 
		 
		 
	}

//	// @UiHandler("businessDocumentTypeTree")
//	// void onBusinessDocumentTypeListChanged(SelectionHandler<TreeItem> event)
//	// {
//	 // if (presenter != null) {
//	 // presenter.onBusinessDocumentTypeListChanged();
//	 // }
//	// }
//
//	public Widget asWidget() {
//		return this;
//	}
}