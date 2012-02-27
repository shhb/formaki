package org.noranj.formak.shared.dto;


/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB21
 */
public class ContactInfoDTO {

  private String id;
  private AddressDTO address;
  
  ///////////////////////////////////////////////////////
  
  /**
   * 
   * @param id
   * @param address
   */
  public ContactInfoDTO(String id, AddressDTO address) {
    super();
    this.id = id;
    this.address = address;
  }
  
  public ContactInfoDTO() {
    super();
    // TODO Auto-generated constructor stub
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public AddressDTO getAddress() {
    return address;
  }
  public void setAddress(AddressDTO address) {
    this.address = address;
  }

  
}
