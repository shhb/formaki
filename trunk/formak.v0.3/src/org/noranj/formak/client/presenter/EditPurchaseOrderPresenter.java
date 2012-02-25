package org.noranj.formak.client.presenter;


import org.noranj.formak.client.common.SelectionModel;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.view.EditPurchaseOrderView;
import org.noranj.formak.shared.dto.AddressDTO;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * 
 * @changes
 *  BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
 * 
 */
public class EditPurchaseOrderPresenter implements Presenter, EditPurchaseOrderView.Presenter<PurchaseOrderDTO> {

	private final EditPurchaseOrderView<PurchaseOrderDTO,PurchaseOrderItemDTO> view;
	private final BusinessDocumentServiceAsync rpcService;
	private final String id;
	
	public EditPurchaseOrderPresenter(BusinessDocumentServiceAsync rpcService , EditPurchaseOrderView<PurchaseOrderDTO,PurchaseOrderItemDTO> view,String id){
		this.rpcService = rpcService;
		this.view = view;	
		this.id = id;
		bind();
	}
	
	public void bind() {
	  
	  //FIXME the id is hard coded and it may not be correct value.
	  //BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
		rpcService.getPurchaseOrder(id, new AsyncCallback<PurchaseOrderDTO>(){
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(PurchaseOrderDTO result) {
				view.getId().setValue(result.getId());
				
				view.getPONumber().setValue(result.getPONumber());
				view.getPODate().setValue(result.getPODate());
				view.getShipTo().setValue( result.getShipTo().getStreetAddress()
						+ result.getShipTo().getCity().toString()
						+ result.getShipTo().getStateOrProvince() 
						+ result.getShipTo().getPostalCode());
				view.getBillTo().setValue(result.getBillTo().getStreetAddress()
						+ result.getBillTo().getCity().toString()
						+ result.getBillTo().getStateOrProvince() 
						+ result.getBillTo().getPostalCode());

				view.getTaxRatePercent().setValue(Byte.toString(result.getTaxRatePercent()));
				view.getTotalTaxAmount().setValue(Long.toString(result.getTotalTaxAmount()));
				view.setRowData(result.getPurchaseOrderItems());
			}
			 
		 }) ;
		//this.view = view;
		this.view.setPresenter(this);
	}	
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	@Override
	public void onSaveButtonClicked() {
		Window.alert("hee");
		
	}

	@Override
	public void onAddButtonClicked() {
		// TODO Auto-generated method stub
		
	}

}
