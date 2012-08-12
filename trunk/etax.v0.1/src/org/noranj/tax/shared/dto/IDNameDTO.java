package org.noranj.tax.shared.dto;

import java.io.Serializable;

/**
 * This is a generic bean to store and transfer any id and name data to UI to be displayed in list boxes.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class IDNameDTO implements Serializable {

  private String id;
  
  private String name;

  public IDNameDTO() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  public IDNameDTO(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
}
