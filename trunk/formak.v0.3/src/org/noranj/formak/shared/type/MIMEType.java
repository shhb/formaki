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

  public String getDescription() {
    return this.description;
  }
  
  public static MIMEType valueOfDescription (String description) {
    if("palin/text".equals(description))
        return(TXT);
    if ("plain/html".equals(description))
      return(HTML);
    if("plain/xml".equals(description))
      return(XML);
    if("image/jpeg".equals(description))
      return(JPEG);
    return(NotDefined);
  }
  
}
