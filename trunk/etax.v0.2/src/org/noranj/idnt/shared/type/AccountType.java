package org.noranj.idnt.shared.type;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum AccountType implements Serializable, IsSerializable {

  EndUSer,
  Consultant,
  Supprt,
  Sysadmin,
  SuperAdmin;
  
  
  AccountType() {
  }

  
}
