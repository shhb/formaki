package org.noranj.core.shared.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20121020.1200
 * @since 0.2.20121020.1200
 * @change
 *
 */
public class UIMessageResources extends MessageResources {
  
  private static ResourceBundle resource = null;
  
  public UIMessageResources(String language, String country, String bundle) {
    super(language, country, bundle);
  }

  public static void init(String language, String country, String baseName) {
    synchronized (UIMessageResources.class) {
      if (resource == null) {
        if (country == null)
          resource = ResourceBundle.getBundle(baseName, new Locale(language));
        else
          resource = ResourceBundle.getBundle(baseName, new Locale(language, country));
      }
    }
  }
  
  public static String getUIMessage(String key) {
    String localizedMessage;
    try {
        localizedMessage = resource.getString(key);
    } catch (Exception e) {
        localizedMessage = key;
        log.severe("Exception in getMessage(" + key + "):" + e.getMessage());
    }
    return localizedMessage;
  }
  
  @Override
  protected ResourceBundle getResource() {
    return(resource);
  }
  
  /** ONLY FOPR TEST UNIT */
  public static void setInstacnceNull() {
   // instance = null;
  }

}
