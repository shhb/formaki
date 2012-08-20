package org.noranj.tax.shared.type;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * It defines the type of tax services that are presented by the system.
 * Right now it is defined an an enum but later it can be implemented as an object in data store.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.2.20120820
 * @version 0.2.20120820
 * @change
 * 
 */
public enum TaxServiceType implements Serializable, IsSerializable {

	Student,
	Basic,
	Investment,
	Intermediate,
	Premier,
	Family;

	TaxServiceType () {
	}
  
  /** 
   * @param services separated by comma.
   * @since 0.3.20120322
   * @version 0.3.20120322
   */
  public static Set<TaxServiceType> convertToSet(String taxServiceStr) {
  	if (taxServiceStr == null) {
  		return(null);
  	}
  	return(convertToSet(taxServiceStr.split(",")));
  }
  
  /** 
   * @param tax services are items of array.
   * @since 0.3.20120322
   * @version 0.3.20120322
   */
  public static Set<TaxServiceType> convertToSet(String[] taxServicesArray) {
  	assert(taxServicesArray!=null);
  	HashSet<TaxServiceType> taxServices = new HashSet<TaxServiceType>(); //TODO is it the best implementation for Set??
  	for(String role : taxServicesArray) {
  		taxServices.add(TaxServiceType.valueOf(role.trim()));
  	}
    return(taxServices);
  }

}
