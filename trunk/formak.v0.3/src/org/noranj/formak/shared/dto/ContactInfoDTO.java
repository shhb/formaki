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

  private long id;
  private AddressDTO address;
  
  ///////////////////////////////////////////////////////
  
  /**
   * 
   * @param id
   * @param address
   */
  public ContactInfoDTO(long id, AddressDTO address) {
    super();
    this.id = id;
    this.address = address;
  }
  
  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public AddressDTO getAddress() {
    return address;
  }
  public void setAddress(AddressDTO address) {
    this.address = address;
  }

  
}
