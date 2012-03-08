package org.noranj.formak.client.common;

import java.util.ArrayList;

import org.noranj.formak.client.resources.en.GlobalResources;
import org.noranj.formak.shared.dto.BusinessDocumentDTO;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("serial")
public class BusinessDocumentsColumnDefinitionsImpl extends ArrayList<ColumnDefinition<BusinessDocumentDTO>> {
  
  private static BusinessDocumentsColumnDefinitionsImpl instance = null;
  
  public static BusinessDocumentsColumnDefinitionsImpl getInstance() {
    if (instance == null) {
      instance = new BusinessDocumentsColumnDefinitionsImpl();
    }
    
    return instance;
  }
  
  protected BusinessDocumentsColumnDefinitionsImpl() {
    
    this.add(new ColumnDefinition<BusinessDocumentDTO>() {
      
      public Widget render(BusinessDocumentDTO c) {
        return new CheckBox();
      }

      public boolean isSelectable() {
        return true;
      }
    });
    
    this.add(new ColumnDefinition<BusinessDocumentDTO>(){

    	public Widget render(BusinessDocumentDTO c) {
    		 final Image propertyButton = new Image(GlobalResources.RESOURCE.layout());
    		 propertyButton.setTitle("ClickMe!");
    		 propertyButton.addMouseOverHandler(new MouseOverHandler(){

				@Override
				public void onMouseOver(MouseOverEvent event) {
					propertyButton.setResource(GlobalResources.RESOURCE.edit());
				}
    			 
    		 });
    		 propertyButton.addMouseOutHandler(new MouseOutHandler(){

				@Override
				public void onMouseOut(MouseOutEvent event) {
					propertyButton.setResource(GlobalResources.RESOURCE.layout());
					
				}
    			 
    		 });
    		 return propertyButton;
	      }
    	
    	 public boolean isClickable() {
    	        return true;
    	 }
    }); 
    
    this.add(new ColumnDefinition<BusinessDocumentDTO>() {
      public Widget render(BusinessDocumentDTO c) {        
         StringBuilder sb = new StringBuilder();
    	  sb.append("<table width='800px' style='border-bottom: 1px solid #E5E5E5'>");
    	  sb.append("<tr>");
    	  sb.append("<td width='80px'><I>");
       	  sb.append(c.getReceiverParty().getName());
       	  sb.append("</I></td>");
    	  sb.append("<td width='80px'>");
    	  sb.append(c.getBizDocumentNumber());
    	  sb.append("</td>");
    	  sb.append("<td width='80px'>");
    	  sb.append(c.getMonetory());
    	  sb.append("</td>");
    	  sb.append("<td width='80px'>");
    	  sb.append(c.getImportantDate());
    	  sb.append("</td>");
    	  sb.append("<td width='200px'>");
    	  sb.append(c.getNote());
    	  sb.append("</td>");
    	  sb.append("</tr>");
    	  sb.append("</table>");
          return new HTML(sb.toString());
    	          
      }

     
    });
    
 
  }
}
