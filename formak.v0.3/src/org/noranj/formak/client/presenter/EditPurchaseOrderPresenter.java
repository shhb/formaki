package org.noranj.formak.client.presenter;

import java.util.ArrayList;
import java.util.List;

import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.service.PartyService;
import org.noranj.formak.client.service.PartyServiceAsync;
import org.noranj.formak.client.view.EditPurchaseOrderView;
import org.noranj.formak.shared.dto.AddressDTO;
import org.noranj.formak.shared.dto.IDNameDTO;
import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.LevelOfImportanceType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * @author
 * 
 * @changes BA-2012-FEB-12 Changed the id from Long to Key type. It was needed
 *          to implement 1-N relationships.
 * 
 */
public class EditPurchaseOrderPresenter implements Presenter,EditPurchaseOrderView.Presenter<PurchaseOrderDTO> {

	private final EditPurchaseOrderView<PurchaseOrderDTO, PurchaseOrderItemDTO, IDNameDTO> view;
	private final BusinessDocumentServiceAsync rpcService;
	private PartyServiceAsync rpcPartyService;
	private final String id;
	private PartyDTO partyDTO;
	private PurchaseOrderDTO purchaseOrderDTO;

	public EditPurchaseOrderPresenter(BusinessDocumentServiceAsync rpcService,EditPurchaseOrderView<PurchaseOrderDTO, PurchaseOrderItemDTO, IDNameDTO> view,
			String id) {
		this.rpcService = rpcService;
		this.view = view;
		this.id = id;
		this.partyDTO = new PartyDTO();
		this.rpcPartyService = GWT.create(PartyService.class);
		bind();
	}

	public void bind() {
		this.view.setPresenter(this);
		this.view.getSaveButton().addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				doSave(purchaseOrderDTO);
			}
			
		});
			//FIXME:SA:2012-03-09 Use logged in users partyRule
		rpcPartyService.getTradingPartiesIDName(PartyRoleType.Buyer,
				new AsyncCallback<List<IDNameDTO>>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log(caught.getMessage());
					}

					@Override
					public void onSuccess(List<IDNameDTO> result) {
						view.setBuyerData(result);
					}
				});

		if(!id.equals("")){
			// FIXME the id is hard coded and it may not be correct value.
			// BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to
			// implement 1-N relationships.
			rpcService.getPurchaseOrder(id, new AsyncCallback<PurchaseOrderDTO>() {
				@Override
				public void onFailure(Throwable caught) {
					GWT.log(caught.getMessage());
				}
	
				@Override
				public void onSuccess(PurchaseOrderDTO result) {
					purchaseOrderDTO = result;
					view.setMasterData(result);
					view.getId().setValue(result.getId());
	
					view.getPONumber().setValue(result.getPONumber());
					view.getPODate().setValue(result.getPODate());
					view.getShipTo().setValue(result.getShipTo().getStreetAddress()
									+ result.getShipTo().getCity().toString()
									+ result.getShipTo().getStateOrProvince()
									+ result.getShipTo().getPostalCode());
					view.getBillTo().setValue(result.getBillTo().getStreetAddress()
									+ result.getBillTo().getCity().toString()
									+ result.getBillTo().getStateOrProvince()
									+ result.getBillTo().getPostalCode());
					view.getNote().setValue(result.getNote());
					IDNameDTO row = new IDNameDTO();
					row.setId(result.getReceiverParty().getId()); 
					row.setName(result.getReceiverParty().getName());
					view.getBuyer().setSelectedValue(row);
					view.getTaxRatePercent().setText(Byte.toString(result.getTaxRatePercent()));
					view.getTotalTaxAmount().setValue(Long.toString(result.getTotalTaxAmount()));
					view.setRowData(result.getPurchaseOrderItems());
				}
	
			});
		}
		else{
			purchaseOrderDTO = new PurchaseOrderDTO();
			view.setRowData(purchaseOrderDTO.getPurchaseOrderItems());
		}
		}
	

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}


	public void doSave(PurchaseOrderDTO purchaseOrderDTO) {
			purchaseOrderDTO.setId(view.getId().getValue());
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setCity("Vancouver");
			addressDTO.setPostalCode("V6R2B8");
			addressDTO.setStateOrProvince("BC");
			addressDTO.setStreetAddress("Jervis ST");
			purchaseOrderDTO.setBillTo(addressDTO);
			purchaseOrderDTO.setShipTo(addressDTO);
			purchaseOrderDTO.setBizDocumentNumber(view.getPONumber().getValue());
			purchaseOrderDTO.setNote(view.getNote().getValue());
			PartyDTO partyDTO = new PartyDTO();
			partyDTO.setId(view.getBuyer().getSelectedValue().getId()); 
			partyDTO.setName(view.getBuyer().getSelectedValue().getName());
			purchaseOrderDTO.setReceiverParty(partyDTO);
			purchaseOrderDTO.setTaxRatePercent(Byte.parseByte(view.getTaxRatePercent().getText()));
			purchaseOrderDTO.setTotalTaxAmount(2);
			rpcService.saveDocument(purchaseOrderDTO, new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							GWT.log(caught.getMessage());

						}

						@Override
						public void onSuccess(String result) {
							view.getId().setValue((result.toString()));
							Window.alert("The Save operation is completed."+ result.toString());
						}

					});
		
	}

	@Override
	public void onAddButtonClicked() {
		// TODO Auto-generated method stub

	}

}
