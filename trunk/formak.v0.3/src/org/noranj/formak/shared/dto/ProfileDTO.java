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
public class ProfileDTO {

  private Long id;

  private ContactInfoDTO contactInfo;

  public ProfileDTO(Long id, ContactInfoDTO contactInfo) {
    super();
    this.id = id;
    this.contactInfo = contactInfo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ContactInfoDTO getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(ContactInfoDTO contactInfo) {
    this.contactInfo = contactInfo;
  }
  
}
