package org.noranj.tax.v2012.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants.DefaultStringValue;
import com.google.gwt.i18n.server.Message;

public interface Text extends Message {
	 public static final Text LANG = GWT.create(Text.class);
	 @DefaultStringValue("contactus")
	 String contactus();
	 
	 @DefaultStringValue("privacy")
	 String privacy();
	 
	 @DefaultStringValue("about")
	 String about();
	 
	 @DefaultStringValue("help")
	 String help();
}
