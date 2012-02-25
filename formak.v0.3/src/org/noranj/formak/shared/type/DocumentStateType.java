package org.noranj.formak.shared.type;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum DocumentStateType implements Serializable, IsSerializable {

  Unknwon,
  Accepted,
  Acknowldged,
  Approved,
  Archived,
  Completed, //ready
  Deleted,
  Draft,
  Received,
  Rejected,
  Sent;
  
  DocumentStateType () {
    
  }
}
