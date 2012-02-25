package org.noranj.formak.shared.type;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PartyRoleType implements Serializable, IsSerializable {

  Unknown, // not sure if this role is needed.
  Buyer,
  Seller,
  Manufacturer,
  Supplier;
  
  PartyRoleType () {
  }
}
