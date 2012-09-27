package org.noranj.idnt.shared.type;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class defines the list of roles that a user can have.
 * 
 * NOTE: I AM NOT SURE IF THE RULES SHOULD BE EXCLUSIVE OR NOT.
 *  
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @since 0.2.20120819
 * @version 0.2.20120819
 * @change
 *  
 */
public enum UserRoleType implements Serializable, IsSerializable {

  Viewer,
  Editor,
  Approver;
  
  UserRoleType () {
  }
  
}
