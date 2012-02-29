package org.noranj.formak.client.presenter;


import java.util.List;

import org.noranj.formak.client.common.SelectionModel;
import org.noranj.formak.client.helper.RPCCall;
import org.noranj.formak.client.service.BusinessDocumentService;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.service.PartyService;
import org.noranj.formak.client.service.PartyServiceAsync;
import org.noranj.formak.client.view.EditPurchaseOrderView;
import org.noranj.formak.shared.dto.AddressDTO;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.core.client.GWT;
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

	private final EditPurchaseOrderView<PurchaseOrderDTO,PurchaseOrderItemDTO,IDNameDTO> view;
	private final BusinessDocumentServiceAsync rpcService;
	private PartyServiceAsync rpcPartyService;
	private final String id;
	private PartyDTO partyDTO;
	
	public EditPurchaseOrderPresenter(BusinessDocumentServiceAsync rpcService , EditPurchaseOrderView<PurchaseOrderDTO,PurchaseOrderItemDTO,IDNameDTO> view,String id){
		this.rpcService = rpcService;
		this.view = view;	
		this.id = id;
		this.partyDTO = new PartyDTO();
		this.rpcPartyService = GWT.create(PartyService.class);
		bind();
	}
	
	public void bind() {
		rpcPartyService.getTradingPartiesIDName(PartyRoleType.Buyer,new AsyncCallback<List<IDNameDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}

			@Override
			public void onSuccess(List<IDNameDTO> result) {
				view.setBuyerData(result);
			}
		});
		
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
				view.getNote().setValue(result.getNote());
				IDNameDTO row = new IDNameDTO();
				 //FIXME: Shhb-2012-FEB-29 It should come from Result but the dummy data is not correct.
				row.setId("1");//result.getReceiverParty().getId());
				row.setName("TP1-Dummy");//row.setName(result.getReceiverParty().getName());
				view.getBuyer().setSelectedValue(row);
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
