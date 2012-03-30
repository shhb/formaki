package org.noranj.formak.shared;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.20120322
 * @since 0.3
 * @change
 *  BA:12-03-06 Added Client ID
 *  BA:12-03-22 Added two new constants. All used in LoginHelper.
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
  
  public static final String C_APPLICATION_URL_PROP_NAME = "applition.url"; //BA:12-03-22 Added
  
  public static final String C_DEVELOPMENT_URL="http://127.0.0.1:9888/Formak.html?gwt.codesvr=127.0.0.1:9997"; //BA:12-03-22 Added
  
  ///////////////////////////////////////////////////////////
  /// MAIL
  ///
	/** the property/parameter name used for messageBody. */
	public final static String C_MAIL_BODY_PROP_NAME = "body";
	
	/** the property/parameter name used for subject of the email. */
	public final static String C_MAIL_SUBJECT_PROP_NAME = "subject";

	/** the property/parameter name used for TO Address of the email. */
	public final static String C_MAIL_TO_ADDRESS_PROP_NAME = "to";
	
	/** the property/parameter name used for TO Address Display Name of the email. */
	public final static String C_MAIL_TO_DISPLAY_NAME_PROP_NAME = "toName";
}
