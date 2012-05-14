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
  Pending, // it is in the system but needs to be processed before being "Received"
  Received,
  Rejected,
  Sent;
  
  DocumentStateType () {
    
  }
}
