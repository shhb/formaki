package org.noranj.formak.client.common;



import java.util.ArrayList;
import java.util.List;

import org.noranj.formak.server.domain.biz.PurchaseOrderItem;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.sun.java.swing.plaf.windows.resources.windows;

public class PurchaseOrderColumnDefinitionImpl extends ArrayList<ColumnDefinition<PurchaseOrderItemDTO>> {

    private static final long serialVersionUID = 1L;
	private static PurchaseOrderColumnDefinitionImpl instance = null;
	
	
	public static PurchaseOrderColumnDefinitionImpl getInstance(){
		if(instance==null)
			instance = new PurchaseOrderColumnDefinitionImpl();
		return instance ;
	}
	
	protected PurchaseOrderColumnDefinitionImpl(){
		this.add(new ColumnDefinition<PurchaseOrderItemDTO>(){
			public Widget render(PurchaseOrderItemDTO c){
				return new CheckBox();
			}
			public boolean isSelectable(){
				return true;
			}
		});
		this.add(new ColumnDefinition<PurchaseOrderItemDTO>(){
			public Widget render(PurchaseOrderItemDTO c){
		    	  //BA-2012-FEB-18 ItemID is the ID that is assigned by this system and is not what we want to show to user.
		    	  // users usually use UPC as itemID or they have their own ID.
		    	  // we can show GTIN here (which is the identifier of the item/product in user's system. 
		    	  //sb.append(c.getItemID());
				List<PurchaseOrderItemDTO> cs = new ArrayList<PurchaseOrderItemDTO>();
				cs.add(c) ;
//		         StringBuilder sb = new StringBuilder();
//		    	  sb.append("<table width='100%'>");
//		    	  sb.append("<tr>");
//		    	  sb.append("<td width='20px'><I>");
//		    	  EditTextCell editme = new EditTextCell();
//		    	  Label labelme = new Label();
//		    	  labelme.setText(Integer.toString(c.getSequenceHolder()));
//		       	  sb.append(labelme.toString());
//		       	  sb.append("</I></td>");
//		    	  sb.append("<td width='80px'>");
//		    	  sb.append(c.getGTIN());
//		    	  sb.append("</td>");
//		    	  sb.append("<td width='200px'>");
//		    	  sb.append(c.getDescription());
//		    	  sb.append("</td>");
//		    	  sb.append("<td width='80px'>");
//		    	  sb.append(c.getUom());
//		    	  sb.append("</td>");
//		    	  sb.append("<td width='80px'>");
//		    	  sb.append(Integer.toString(c.getQuantity()));
//		    	  sb.append("</td>");
//		    	  sb.append("<td width='80px'>");
//		    	  sb.append(c.getPrice());
//		    	  sb.append("</td>");
//		    	  sb.append("<td width='80px'>");
//		    	  sb.append("0000");
//		    	  sb.append("</td>");
//		    	  sb.append("</tr>");
//		    	  sb.append("</table>");
		          //return new HTML(sb.toString());		
		          CellTable<PurchaseOrderItemDTO> cellTable = new CellTable<PurchaseOrderItemDTO>();
		          Column<PurchaseOrderItemDTO,String> sequenceHolder = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

					@Override
					public String getValue(PurchaseOrderItemDTO object) {
						return Integer.toString(object.getSequenceHolder());
					}
	
				};
				  Column<PurchaseOrderItemDTO,String> gtnID = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

					@Override
					public String getValue(PurchaseOrderItemDTO object) {
						return object.getGTIN();
					}
				  };
				
		          Column<PurchaseOrderItemDTO,String> description = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {
					@Override
					public String getValue(PurchaseOrderItemDTO object) {
						return object.getDescription();
					}
				};
				
				  Column<PurchaseOrderItemDTO,String> uom = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

					@Override
					public String getValue(PurchaseOrderItemDTO object) {
						return object.getUom();
					}
				  };

				  Column<PurchaseOrderItemDTO,String> quantity = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

					@Override
					public String getValue(PurchaseOrderItemDTO object) {
						return Integer.toString(object.getQuantity());
					}
				  };
				  
				  Column<PurchaseOrderItemDTO,String> total = new Column<PurchaseOrderItemDTO, String>(new EditTextCell()) {

					@Override
					public String getValue(PurchaseOrderItemDTO object) {
						return Long.toString(object.getQuantity()*object.getPrice());
					}
				  };
				  
				  description.setFieldUpdater(new FieldUpdater<PurchaseOrderItemDTO,String>(){
					@Override
					public void update(int index, PurchaseOrderItemDTO object,String value) {
						object.setDescription(value);	
					}
				  });
				
				cellTable.addColumn(sequenceHolder);
				cellTable.setColumnWidth(sequenceHolder, 3, Unit.PCT);
				
				cellTable.addColumn(gtnID);
				cellTable.setColumnWidth(gtnID, 30, Unit.PCT);
				
				cellTable.addColumn(description);
				cellTable.setColumnWidth(description, 100, Unit.PCT);
				
				cellTable.addColumn(uom);
				cellTable.setColumnWidth(uom, 40, Unit.PCT);
				
				cellTable.addColumn(quantity);
				cellTable.setColumnWidth(quantity, 40, Unit.PCT);
				
				cellTable.addColumn(total);
				cellTable.setColumnWidth(total, 40, Unit.PCT);
				
				ListDataProvider<PurchaseOrderItemDTO> lda = new ListDataProvider<PurchaseOrderItemDTO>();
				lda.setList(cs);
				lda.addDataDisplay(cellTable);

				return cellTable;
		          
		}
		public boolean isSelectable(){
			return true;
		}
	});

	}
}
