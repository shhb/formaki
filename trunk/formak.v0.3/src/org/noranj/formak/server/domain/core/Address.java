package org.noranj.formak.server.domain.core;

import java.io.Serializable;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.noranj.formak.shared.dto.AddressDTO;
import org.noranj.formak.shared.dto.PurchaseOrderDTO;

/**
 * NOTE: This is a sample of classes that are EmbededOnly.
 * 
 * This class stores an address.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
@EmbeddedOnly
public class Address implements Serializable{
  
   @NotPersistent
   private static final long serialVersionUID = 1867687879741150978L;

    @Persistent
    private String streetAddress;

    @Persistent
    private String city;

    @Persistent
    private String stateOrProvince;

    @Persistent
    private String postalCode;

    
    public Address() {
      super();
    }
    
    public Address(String streetAddress, String city, String stateOrProvince, String postalCode) {
      super();
      this.streetAddress = streetAddress;
      this.city = city;
      this.stateOrProvince = stateOrProvince;
      this.postalCode = postalCode;
    }

    public String getStreetAddress() {
      return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
      this.streetAddress = streetAddress;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getStateOrProvince() {
      return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
      this.stateOrProvince = stateOrProvince;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    /**
     * 
     * @return
     */
    public AddressDTO getAddressDTO () {
      
      AddressDTO address = new AddressDTO();
      address.setStreetAddress(this.streetAddress);
      address.setCity(this.city);
      address.setStateOrProvince(this.stateOrProvince);
      address.setPostalCode(this.postalCode);
      
      return(address);
    }

}