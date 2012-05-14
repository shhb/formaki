package org.noranj.formak.shared.type;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public enum MIMEType {

  TXT("palin/text"),
  HTML("plain/html"),
  XML("plain/xml"),
  JPEG("image/jpeg"),
  NotDefined("");
  
  private String description;

  private MIMEType(String newDescription) {
    this.description = newDescription;
  }

  public String toString() {
    return this.description;
  }
  
  public static MIMEType valueOfWithEncoding (String description) {
  	
  	if (description!=null) {
	    if(description.startsWith("text/palin"))
	        return(TXT);
	    if (description.startsWith("text/html"))
	      return(HTML);
	    if(description.startsWith("text/xml"))
	      return(XML);
	    if(description.startsWith("image/jpeg"))
	      return(JPEG);
  	}
  	
    return(NotDefined);
  }
  
}
