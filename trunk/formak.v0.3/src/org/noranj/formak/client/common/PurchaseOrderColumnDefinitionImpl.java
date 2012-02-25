package org.noranj.formak.client.common;



import java.util.ArrayList;

import org.noranj.formak.server.domain.biz.PurchaseOrderItem;
import org.noranj.formak.shared.dto.PurchaseOrderItemDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;

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
            
            FlexTable flextable = new FlexTable();
			 
			 //////////////////////////////////////
			 int row = 1;
			 flextable.getRowFormatter().setStyleName(row, "");   
			 flextable.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);   
			 flextable.getCellFormatter().setWordWrap(row, 0, false);   
			 flextable.setText(row, 0, Integer.toString(c.getSequenceHolder()));  
			 flextable.getCellFormatter().setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);   
			 flextable.getCellFormatter().setWordWrap(row, 1, false);   
			 flextable.setText(row, 1, Integer.toString(c.getSequenceHolder()));   
			 flextable.setText(row, 2, Long.toString(c.getItemID())) ;
			 flextable.setText(row, 3, Integer.toString(c.getQuantity())) ;
			 flextable.getCellFormatter().setHorizontalAlignment(row, 3, HasHorizontalAlignment.ALIGN_LEFT);   
			 flextable.getCellFormatter().setWordWrap(row, 3, false);
			 Button submitButton= new Button("Submit");  
			 flextable.setWidget(row, 4,submitButton );
			// DOM.sinkEvents(self.getElement(), Event.ONCLICK);
			 flextable.sinkEvents(Event.ONCLICK);
			 //submitWorklow(submitButton,row, workflowMessageEvent);   
			 flextable.getRowFormatter().setVisible(row, true);   
			 submitButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// do something
				}
			});
	    	  return flextable;    
		}
		public boolean isSelectable(){
			return true;
		}
	});

	}
}
