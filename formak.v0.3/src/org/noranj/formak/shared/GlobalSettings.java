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
