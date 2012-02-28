package org.noranj.formak.client.presenter;

import java.util.ArrayList;
import java.util.List;

import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.view.BusinessDocumentView;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.type.DocumentStateType;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import org.noranj.formak.client.common.SelectionModel;
import org.noranj.formak.client.event.AddBusinessDocumentEvent;
import org.noranj.formak.client.event.EditBusinessDocumentEvent;

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
 */
public class BusinessDocumentViewPresenter implements Presenter,BusinessDocumentView.Presenter<BusinessDocumentDTO> {

	private DocumentType documentType;
	private List<BusinessDocumentDTO> businessDocumentDetails;
	// BA-2012-FEB-03 Added the second data type (DocumentType)
	private final BusinessDocumentView<BusinessDocumentDTO, DocumentType> view;
	private final BusinessDocumentServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final SelectionModel<BusinessDocumentDTO> selectionModel;

	public BusinessDocumentViewPresenter(
			BusinessDocumentServiceAsync rpcService, HandlerManager eventBus,
			BusinessDocumentView<BusinessDocumentDTO, DocumentType> view, String documentTypeCode) { 
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.selectionModel = new SelectionModel<BusinessDocumentDTO>();
		this.view.setPresenter(this);
		fetchBusinessDocumentDetails(DocumentType.fromString(documentTypeCode));
	}

	private void fetchBusinessDocumentDetails(DocumentType documentType) {
		this.documentType  = documentType ;
		if(!documentType.equals(null)) {	// FXIME documentType, State???
		  rpcService.getBusinessDocuments(documentType,	DocumentStateType.Draft,new AsyncCallback<List<BusinessDocumentDTO>>() {
					public void onSuccess(
							List<BusinessDocumentDTO> result) {
						businessDocumentDetails = result;
						sortBusinessDocumentDetails();
						view.setRowData(businessDocumentDetails);
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error fetching BusinessDocument details");
					}
				});
		}		
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());

	}

	// FIXME this must be reviewed and the proper sort algorithm to be
	// implemented. plus the column name must be passed too.
	public void sortBusinessDocumentDetails() {

		// Yes, we could use a more optimized method of sorting, but the
		// point is to create a test case that helps illustrate the higher
		// level concepts used when creating MVP-based applications.
		//
		for (int i = 0; i < businessDocumentDetails.size(); ++i) {
			for (int j = 0; j < businessDocumentDetails.size() - 1; ++j) {
				// BA-2012-JAN-31 replaced getDisplayName() with
				// getBizDocumentNumber()
				// if
				// (businessDocumentDetails.get(j).getDisplayName().compareToIgnoreCase(businessDocumentDetails.get(j
				// + 1).getDisplayName()) >= 0) {
				if (businessDocumentDetails.get(j).getBizDocumentNumber().compareToIgnoreCase(businessDocumentDetails.get(j + 1).getBizDocumentNumber()) >= 0) {
					BusinessDocumentDTO tmp = businessDocumentDetails
							.get(j);
					businessDocumentDetails.set(j,
							businessDocumentDetails.get(j + 1));
					businessDocumentDetails.set(j + 1, tmp);
				}
			}
		}
	}

	// ///////////////////////////////////
	// BA-2012-JAN-27 Added the following methods
	public void onAddButtonClicked() {
		eventBus.fireEvent(new AddBusinessDocumentEvent());
	}

	public void onDeleteButtonClicked() {
		deleteSelectedBusinessDocuments();
	}

	@Override
	public void onBusinessDocumentTypeListChanged(String codeStr) {

	  this.documentType = DocumentType.fromCodeString(codeStr);
	  fetchBusinessDocumentDetails(this.documentType); 
	}

	/**
   * @changes
   * BA-2012-FEB-12 Changed the id from Long to Key type. It was needed to implement 1-N relationships.
	 */
	private void deleteSelectedBusinessDocuments() {
		List<BusinessDocumentDTO> selectedContacts = selectionModel
				.getSelectedItems();
		
		/* BA-2012-FEB-12 
		List<Long> ids = new ArrayList<Long>();

		for (int i = 0; i < selectedContacts.size(); ++i) {
			ids.add(new Long(selectedContacts.get(i).getId()));
		}
    */
    List<String> ids = new ArrayList<String>();

    for (int i = 0; i < selectedContacts.size(); ++i) {
      ids.add(selectedContacts.get(i).getId());
    }
		
		rpcService.deleteBusinessDocuments(DocumentType.PurchaseOrder, ids,
				new AsyncCallback<List<BusinessDocumentDTO>>() {
					public void onSuccess(
							List<BusinessDocumentDTO> result) {
						businessDocumentDetails = result;
						sortBusinessDocumentDetails();
						/*
						 * BA-2012-JAN-27 replaced the article code with this
						 * one. List<String> data = new ArrayList<String>(); for
						 * (int i = 0; i < result.size(); ++i) {
						 * data.add(businessDocumentDetails
						 * .get(i).getDisplayName()); } display.setData(data);
						 */
						view.setRowData(businessDocumentDetails);
					}

					public void onFailure(Throwable caught) {
						Window.alert("Error deleting selected contacts");
					}
				});
	}

	@Override
	public void onItemClicked(BusinessDocumentDTO selectedItem) {
		eventBus.fireEvent(new EditBusinessDocumentEvent(String.valueOf(selectedItem.getId()))); 
		// FIXME				// EditBusinessDocumentEvent()
													// must be long
													// BA-2012-JAN-31
	}

	@Override
	public void onItemSelected(BusinessDocumentDTO selectedItem) {
		if (selectionModel.isSelected(selectedItem)) {
			selectionModel.removeSelection(selectedItem);
		} else {
			selectionModel.addSelection(selectedItem);
		}
	}

} // BusinessDocumentsPresenter

