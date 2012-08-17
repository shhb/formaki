package org.noranj.idnt.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.noranj.idnt.shared.dto.ContactInfoDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB21
 */
@PersistenceCapable(detachable="true")
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION", //@Version is used to implement optimistic locking but we may use it for another purposes.
         extensions={@Extension(vendorName="datanucleus", key="field-name", value="version")})
//@  FetchGroup(name=Profile.C_FETCH_GROUP_CONTACTINFO, members={@Persistent(name="contactInfo")}) 
public class ContactInfo implements Serializable {

  @NotPersistent
  private static final long serialVersionUID = 2739249843928243243L;

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key id;
  
  /** stores the name of party. */
  @Persistent
  @Embedded
  private Address address;
  
  @Persistent
  private long version;

  ///////////////////////////////////
  //////       Methods          /////
  ///////////////////////////////////
  public ContactInfo() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   */
  //public Long getId() {
  public String getId() {
    return ((id!=null)?KeyFactory.keyToString(id):null);
  }

  //public void setId(Long id) {
  public void setId(String id) {
    this.id = (id!=null)?KeyFactory.stringToKey(id):null;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
  /**
   * It creates a DTOobject and fills it with the data of this object.
   * @return
   */
  public ContactInfoDTO getContactInfoDTO () {
    ContactInfoDTO contactInfoDTO = new ContactInfoDTO(getId(), (address!=null?address.getAddressDTO():null));
    
    return(contactInfoDTO);  
  }
  
}
