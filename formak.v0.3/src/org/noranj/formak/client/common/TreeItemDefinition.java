package org.noranj.formak.client.common;

import org.noranj.formak.shared.type.DocumentType;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;
import com.google.gwt.cell.client.Cell ;
public class TreeItemDefinition implements TreeViewModel{
	private static class DocumentCell extends AbstractCell<DocumentType> {

		private final String displayText;
		
		public DocumentCell(String displayText){
			this.displayText = displayText;
		}
		
		@Override
		public void render(Context context,	DocumentType value, SafeHtmlBuilder sb) {
			sb.appendEscaped(value.toString());
			
		}
	
	}
	public TreeItemDefinition(SelectionModel<DocumentType> selectionModel){
		this.selectionModel = selectionModel;
		this.documentTypeDataProvider = null;
	}
	private final SelectionModel<DocumentType> selectionModel;	
	private final ListDataProvider<DocumentType> documentTypeDataProvider;
	
	
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		return new DefaultNodeInfo<DocumentType>(documentTypeDataProvider, new DocumentCell(""));
	}
		
	@Override
	public boolean isLeaf(Object value) {
		return value instanceof DocumentType;
	}
	
}
