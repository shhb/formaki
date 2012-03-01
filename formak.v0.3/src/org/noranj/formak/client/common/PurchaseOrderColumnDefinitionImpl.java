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
		         StringBuilder sb = new StringBuilder();
		    	  sb.append("<table width='100%'>");
		    	  sb.append("<tr>");
		    	  sb.append("<td width='20px'><I>");
		       	  sb.append(Integer.toString(c.getSequenceHolder()));
		       	  sb.append("</I></td>");
		    	  sb.append("<td width='80px'>");
		    	  sb.append(Long.toString(c.getItemID()));
		    	  sb.append("</td>");
		    	  sb.append("<td width='80px'>");
		    	  sb.append(Integer.toString(c.getQuantity()));
		    	  sb.append("</td>");
		    	  sb.append("<td width='80px'>");
		    	  sb.append(c.getPrice());
		    	  sb.append("</td>");
		    	  sb.append("<td width='200px'>");
		    	  sb.append(c.getDescription());
		    	  sb.append("</td>");
		    	  sb.append("</tr>");
		    	  sb.append("</table>");
		          return new HTML(sb.toString());			
		}
		public boolean isSelectable(){
			return true;
		}
	});

	}
}
