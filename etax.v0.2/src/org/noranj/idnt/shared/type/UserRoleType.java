package org.noranj.idnt.shared.type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class defined the list of roles that a user can have.
 * 
 * NOTE: NOT SURE IF THE ROLES ARE EXCLUSIVE OR NOT.
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

  Applicant, // not sure if this role is needed.
  Advisor,
  Support,
  SystemAdmin,
  SuperAdmin,
  Management;
  
  UserRoleType () {
  }
  
  /** 
   * @param roles separated by comma.
   * @since 0.3.20120322
   * @version 0.3.20120322
   */
  public static Set<UserRoleType> convertToSet(String rolesStr) {
  	if (rolesStr == null) {
  		return(null);
  	}
  	return(convertToSet(rolesStr.split(",")));
  }
  
  /** 
   * @param roles are items of array.
   * @since 0.3.20120322
   * @version 0.3.20120322
   */
  public static Set<UserRoleType> convertToSet(String[] rolesArray) {
  	assert(rolesArray!=null);
  	HashSet<UserRoleType> roles = new HashSet<UserRoleType>(); //TODO is it the best implementation for Set??
  	for(String role : rolesArray) {
  		roles.add(UserRoleType.valueOf(role.trim()));
  	}
    return(roles);
  }

}
