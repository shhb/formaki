package org.noranj.formak.client.resources.en;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface GlobalResources extends ClientBundle {
	  public static final GlobalResources RESOURCE =  GWT.create(GlobalResources.class);

	  @Source("org/noranj/formak/client/resources/en/logo.png")
	  ImageResource logo();
	  
	  @Source("org/noranj/formak/client/resources/en/logo_small.png")
	  ImageResource logo_small();
	  
	  @Source("org/noranj/formak/client/resources/en/propertyButton.png")
	  ImageResource propertyButton();
	  
	  @Source("org/noranj/formak/client/resources/en/propertyButtonOn.png")
	  ImageResource propertyButtonOn();

	  @Source("org/noranj/formak/client/resources/en/GlobalStylesEn.css")
	  GlobalStylesheet globalStyles();
	  //FIXME SA 2012-02-10 
//	  @Source("org/noranj/formak/client/resources/en/TextResource.txt")
//	  GlobalTextResouce bundle();		  
	
}