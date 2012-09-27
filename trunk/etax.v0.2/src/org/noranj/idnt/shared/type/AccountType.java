package org.noranj.idnt.shared.type;


import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * NOTE: it might be a good idea to set a code to each type and use the codes to implement a hierarchical structure.
 * The code and its hierarchical structure might be useful when dealing with namespaces.
 *   
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20120927
 * @since 0.2.20120927
 * @change
 *
 */
public enum AccountType implements Serializable, IsSerializable {

  EndUSer,
  Consultant,
  Supprt,
  SysAdmin,
  Management,
  SuperAdmin,
  Unknown;

  // This is needed to store the attributes of this type in data store.
  AccountType() {
  }

  /** converts the description to the DocumentType form.
   * For example if gets PurchaseOrder as input, 
   * it will return DocumentType.PurchaseOrder.
   * @param stringAccountType
   * @return
   */
  public static AccountType fromString(String stringAccountType) {
    
    if (stringAccountType!=null) {
        
      if ( stringAccountType.equalsIgnoreCase("EndUser"))
        return(AccountType.EndUSer);
      else if (stringAccountType.equalsIgnoreCase("Consultant"))
        return(AccountType.Consultant);
      else if (stringAccountType.equalsIgnoreCase("Support"))
        return (AccountType.Supprt);
      else if (stringAccountType.equalsIgnoreCase("SysAdmin"))
        return (AccountType.SysAdmin);
      else if (stringAccountType.equalsIgnoreCase("Management"))
        return (AccountType.Management);
      else if (stringAccountType.equalsIgnoreCase("SuperAdmin"))
        return (AccountType.SuperAdmin);
    } 
    return (AccountType.Unknown);

  } // toString
  
}
