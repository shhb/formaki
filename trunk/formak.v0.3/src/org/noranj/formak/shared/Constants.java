package org.noranj.formak.shared;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012MATR06
 * @since 0.3
 * @change
 *  BA:12-MAR-06 Added Client ID
 */

public class Constants {
  
  /* TODO
   * Q:is this class the best place to keep this attribute? shouldn't it be in SystemUser class? 
   * A: The domain class can not be sued because it is not accessible by client. The other choice would be DTO class but I think this class is better.  
  /** it is stored in session so we know who logged in. */
  public static final String C_USER_ID_PROPERTY_NAME = "userId";
  
  /* TODO
   * Q:is this class the best place to keep this attribute? shouldn't it be in SystemClientPatry class? 
   * A: The domain class can not be sued because it is not accessible by client. The other choice would be DTO class but I think this class is better.  
  /** it is stored in session so we can set the proper namespace. */
  public static final String C_CLIENT_ID_PROPERTY_NAME = "clientId";
  
  /** this is stored in session so we know the user logged in. */
  public static final String C_LOGGED_IN_FLAG_PROPERTY_NAME = "loggedin";
  
  /** */
  public static final String C_LOGGED_OUT = "logged out";

  /**
   * 
   */
  public static final String C_SYSTEM_ADMIN_NAMESPACE = "";
}
