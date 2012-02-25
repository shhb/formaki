package org.noranj.formak.client.presenter;

import org.noranj.formak.client.event.DocumentTypeListChangedEvent;
import org.noranj.formak.client.service.BusinessDocumentServiceAsync;
import org.noranj.formak.client.view.DocumentTypeListView;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;
import org.noranj.formak.shared.type.DocumentType;
import org.noranj.formak.shared.type.PartyRoleType;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class DocumentTypeListPresenter implements Presenter,DocumentTypeListView.Presenter<DocumentType> {
	
	private DocumentType documentType;
	private final BusinessDocumentServiceAsync rpcService;
	private final DocumentTypeListView<DocumentType> view;
	private final HandlerManager eventBus;
	public DocumentTypeListPresenter(BusinessDocumentServiceAsync rpcService,
			DocumentTypeListView<DocumentType> view,
			HandlerManager eventBus){
		this.rpcService = rpcService;
		this.view = view;
		this.eventBus = eventBus;
		this.view.setPresenter(this);
		fetchListOfAllDocuments();
	}
			
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
	}

	private void fetchListOfAllDocuments() {

		// FIXME role type must come from session. it must filter based on
		// user's party role Type
		rpcService.getListOfAllDocuments(PartyRoleType.Buyer,
				new AsyncCallback<DocumentType[]>() {

					public void onFailure(Throwable caught) {
						Window.alert("Error fetching DocumentRoleType details");
					}

					public void onSuccess(DocumentType[] result) {
						view.setDocumentList(result);
						
						//fetchBusinessDocumentDetails(result[0]);
					}
				});
	}

	@Override
	public void onBusinessDocumentTypeListChanged(String documentTypeCode) {
		eventBus.fireEvent(new DocumentTypeListChangedEvent(documentTypeCode));
		
	}
}
