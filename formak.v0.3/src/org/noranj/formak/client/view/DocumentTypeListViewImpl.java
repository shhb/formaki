package org.noranj.formak.client.view;

import org.noranj.formak.client.resources.en.GlobalResources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
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
		businessDocumentTypeTree.setStyleName("margin: 20px 0px 0px 0px");
		
		for (int i = 0; i < setData.length; ++i) {
			StringBuilder sb = new StringBuilder();
			Image image = null;
			
			if(setData[i].toString()=="RequestForQuotation"){
				image = new Image(GlobalResources.RESOURCE.home0());
			}
			else if(setData[i].toString()=="PurchaseOrder"){
				image = new Image(GlobalResources.RESOURCE.po());
			}
			else if(setData[i].toString()=="Quotation"){
				image = new Image(GlobalResources.RESOURCE.layout());
			}
			else if(setData[i].toString()=="PurchaseOrderResponse"){
				image = new Image(GlobalResources.RESOURCE.home());
			}
			else if(setData[i].toString()=="Invoice"){
				image = new Image(GlobalResources.RESOURCE.inv());
			}
			else if(setData[i].toString()=="DispatchAdvice"){
				image = new Image(GlobalResources.RESOURCE.message());
			}
			sb.append("<span >" + image.toString());
			sb.append("</span><span style='margin:5px 0px 0px 0px;padding-left:10px;vertical-align:top'>" + setData[i].toString()+"</span>");
			TreeItem treeItem = new TreeItem(sb.toString());
			 //treeItem.addItem(blinkListLogo);
			 //treeItem.addItem(sb.toString());
			 //treeItem.setState(true); 
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