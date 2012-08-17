package org.noranj.core.shared.utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.20120811
 * @since 0.3
 * @change
 *  BA-12-Aug-11 Corrected formatDateTime and formatAmount formats
 */
public class Formatter {

  private static Logger logger = Logger.getLogger(Formatter.class.getName());
	
  /** All monetary values are stored in long with assumption of having 3 digits after decimal points.
   * For example 1.20 is stored as 1200.
   */
  final static int C_NUMBER_OF_DIGITS_AFTER_DECIMAL_IN_AMOUNTS = 3;
  
  //FIXME BA:12-MAR-05 Locale has not being used here.
  /**
   * 
   * @param milliseconds
   * @param format - NOT USED YET.
   * @param locale - NOT USED YET (GWT nOT SUPPORTED)
   * @return a formatted string that represents the date equivalent to the input parameter 'milliseconds'. 
   * 
   */
  public static String formatDate(long milliseconds, String fromat /*, Locale locale*/) {
    
    assert(milliseconds != 0) : "input date time can not be 0.";
    
    try {
      
      Date date = new Date(milliseconds);
      String month = "ERR";
      
      switch (date.getMonth()+1) {
        case 1: month = "JAN";break;
        
        case 2: month = "FEB";break;
        case 3: month = "MAR";break; 
        case 4: month = "APR";break; 
        case 5: month = "MAY";break; 
        case 6: month = "JUN";break; 
        case 7: month = "JUL";break; 
        case 8: month = "AUG";break; 
        case 9: month = "SEP";break; 
        case 10: month = "OCT";break; 
        case 11: month = "NOV";break; 
        case 12: month = "DEC";break; 
      }
      
      return((date.getYear() + 1900)+ "-" + month + "-" + date.getDate());
      
      /*
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(milliseconds);
      return(cal.get(Calendar.YEAR) + "-" + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale) + "-" + cal.get(Calendar.DATE));
      */
      
      //SimpleDateFormat formatter = new SimpleDateFormat(format);
      //formatter.setLenient(false);
      //return(formatter.format(new Date(milliseconds)));

      
    }catch(Exception ex) {
      ex.printStackTrace();
    }
    
    return("ERROR!");
  }
  
  public static String formatAmount(long amount) {
    
    assert(amount >= 0) : "input amount can not be less than 0.";
    
    try {
      /** NumberFormat is not supported by GWT. */
      //NumberFormat formatter = NumberFormat.getCurrencyInstance(GlobalSettings.getLocale());
      //formatter.setGroupingUsed(true);
      //formatter.setMinimumFractionDigits(2);
      //return(formatter.format(amount));
      return(String.valueOf((double)(Math.round((double)amount /10))/ 100)); /// there are three digits after decimal.
    }catch(Exception ex) {
      ex.printStackTrace(); ////FIXME
    }
    
    return("ERROR!");
  }
  
  //FIXME it is not LOCALIZED!!!
  /**
   * It finds the number of digits after the decimal point in 'amount'
   * then removes all non digit characters such as '.' or ','.
   * It calculates the number of 10 that must be multiplied to the amount.
   * It convert the amount to LONG and then multiply it by the calculated number in above.  
   * @param amount
   * @return
   */
  public static long convertToAmount(String amount) {
    
    assert(amount != null && !amount.equals("")) : "input amount can not be null or empty.";
    
    try {
      int pos = amount.indexOf(0x2E); // '.' the decimal point is 0x2E
      
      //FIXME it must remove any character from the amount and only leaves the digits
      char[] amountArray = new char[100];
      int j = 0 ;
      for(int i=0; i< amount.length(); ++i) {
        if (amount.charAt(i) > 0x2F && amount.charAt(i) < 0x3A) {
          amountArray[j++] = amount.charAt(i);
        }
      }
      
      
      String onlyDigitsAmount = new String(amountArray,0,j);
      
      int numberOfDigitsAfterDecimal = amount.length() - pos; // indicates the number of 10s
      int multiplyBy = 1;
      assert(C_NUMBER_OF_DIGITS_AFTER_DECIMAL_IN_AMOUNTS>=numberOfDigitsAfterDecimal) : "amount is ["+amount+"] - the number of digits after decimal point can not be more than " + C_NUMBER_OF_DIGITS_AFTER_DECIMAL_IN_AMOUNTS;
      for(int i=0; i<C_NUMBER_OF_DIGITS_AFTER_DECIMAL_IN_AMOUNTS-numberOfDigitsAfterDecimal; i++) {
        multiplyBy *=10;
      }
      return(Long.parseLong(onlyDigitsAmount) * multiplyBy); /// there are three digits after decimal.
    }catch(Exception ex) {
      ex.printStackTrace(); //FIXME
    }
    
    return(0);
  }
  
  /**
   * 
   * @param date
   * @return
   * @deprecated NOT IMPLEMENTED YET.
   */
  public static long convertToMilliseconds (String date) {
    //FIXME
    // this must be fixed.
    return(System.currentTimeMillis());
  }
  
}
