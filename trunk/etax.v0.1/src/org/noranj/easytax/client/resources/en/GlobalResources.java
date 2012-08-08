package org.noranj.easytax.client.resources.en;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface GlobalResources extends ClientBundle {
	  public static final GlobalResources RESOURCE =  GWT.create(GlobalResources.class);

	  @Source("org/noranj/easytax/client/resources/en/images/logo.png")
	  ImageResource logo();
	  
	  @Source("org/noranj/easytax/client/resources/en/images/logo_small.png")
	  ImageResource logo_small();
	  
	  @Source("org/noranj/easytax/client/resources/en/images/propertyButton.png")
	  ImageResource propertyButton();
	  
	  @Source("org/noranj/easytax/client/resources/en/images/propertyButtonOn.png")
	  ImageResource propertyButtonOn();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/po.png")
	  ImageResource po();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/inv.png")
	  ImageResource inv();

	  @Source("org/noranj/easytax/client/resources/en/icons/black/layout.png")
	  ImageResource layout();
	
	  @Source("org/noranj/easytax/client/resources/en/icons/black/setting.png")
	  ImageResource setting();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/edit.png")
	  ImageResource edit();

	  @Source("org/noranj/easytax/client/resources/en/icons/black/home0.png")
	  ImageResource home0();

	  @Source("org/noranj/easytax/client/resources/en/icons/black/home.png")
	  ImageResource home();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/message.png")
	  ImageResource message();
	
	  @Source("org/noranj/easytax/client/resources/en/icons/black/star4.png")
	  ImageResource star4();

	  @Source("org/noranj/easytax/client/resources/en/icons/black/trash.png")
	  ImageResource trash();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/delete.png")
	  ImageResource delete();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/addrow.png")
	  ImageResource addRow();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/checkmark.png")
	  ImageResource checkmark();
	  
	  @Source("org/noranj/easytax/client/resources/en/icons/black/sprite.png")
	  ImageResource sprite();
	  
	  @Source("org/noranj/easytax/client/resources/en/css/GlobalStylesEn.css")
	  GlobalStylesheet globalStyles();

	  //FIXME SA 2012-02-10 
//	  @Source("org/noranj/easytax/client/resources/en/TextResource.txt")
//	  GlobalTextResouce bundle();		  
	
}