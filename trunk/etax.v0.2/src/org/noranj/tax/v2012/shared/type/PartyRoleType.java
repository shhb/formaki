package org.noranj.tax.v2012.shared.type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * It defines the role of parties in general. 
 * In TAX system, the roles can be Family and Accounting.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.2
 * @version 0.2.20120924
 * @change
 *  BA-12-08-11 Added new method convertToSet.
 * @deprecated PartyRoleType no longer is used. Party/ClientParty are renamed to Account. 12-SEP-26
 * 
 */
public enum PartyRoleType implements Serializable, IsSerializable {

  Unknown, // not sure if this role is needed.
  Applicant, 
  Accounting,
  Buyer,
  Seller,
  Manufacturer,
  Supplier;
  
  PartyRoleType () {
  }
  
  /** 
   * @param roles separated by comma.
   * @since 0.2.20120324
   * @version 0.2.20120922
   */
  public static Set<PartyRoleType> convertToSet(String rolesStr) {
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
  public static Set<PartyRoleType> convertToSet(String[] rolesArray) {
  	assert(rolesArray!=null);
  	HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>(); //TODO is it the best implementation for Set??
  	for(String role : rolesArray) {
  		roles.add(PartyRoleType.valueOf(role.trim()));
  	}
    return(roles);
  }

}
