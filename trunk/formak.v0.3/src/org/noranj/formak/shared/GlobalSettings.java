package org.noranj.formak.shared;


/**
 * This class returns all global settings such date format or number of  
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class GlobalSettings {

  /** indicates the number of times that a call should be retried before giving up. */
  public final static int C_RPC_CALL_RETRY = 3;
  
  private static String dateFormat = "YYYY-MM-DD"; 
  private static String dateTimeFormat = "YYYY-MM-DD HH:mm:SS";
  
  private static String currencyFormat = "###,###,###,###.00";
 
  /** indicates the maximum number of files that can be attached to an email. */
  public static int C_MAX_NUMBER_OF_ATTACHED_FILES_TO_EMAIL = 10;
  
  /** Max number of recipient addresses that can be set in one email. */
  public static int C_MAX_NUMBER_OF_RECIPIENTS = 128;
  
  public final static String C_MAIL_FROM_ADDRESS = "formak@noranj.com";
  public final static String C_MAIL_FROM_PERSONAL = "Noranj.com Admin";

  public final static String C_SYSADMIN_MAIL_ADDRESS = "sysadmin@noranj.com";
  public final static String C_SYSADMIN_MAIL_PERSONAL = "SystemAdmin Noranj";
 
  public static String getCurrencyFormat () {
    return(currencyFormat);
  }

  public  static String getDateFormat () {
    return(dateFormat);
  }

  /** GWT - NOT SUPPORTED 
  public  static Locale getLocale () {
    return(Locale.US);
  }
  */
  
  public static String getDateTimeFormat () {
    return(dateTimeFormat);
  }
/*
  public static Locale getLocale() {
    return(Locale.US);
  }
  */
}
