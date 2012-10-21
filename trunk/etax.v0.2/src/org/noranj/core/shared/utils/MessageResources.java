package org.noranj.core.shared.utils;


import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;


/**
 * NOTE the 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20121020.1120
 * @since 0.2.20121020.1120
 * @change
 *
 */
public abstract class MessageResources {

    static Logger log = Logger.getLogger(MessageResources.class.getName());

    /**
     * It stores the language such as en(English), fr(French), fa(Persian).
     * see ISO-639 standard
     */
    private String lang = null;

    /**
     * This field denotes the <code>Locale</code> country  parameter.
     * value of this field is one of ISO-3165 standard;
     * EN(United State), FR(France), IR(Iran).
     */
    private String country = null;

    /**
     * It stores the path and file name of bundle without the locale settings.
     * For example if the bundle name is messages_en.properties and stored in META-INF/resources
     * the resource value must be META-INF/resources/messages.
     */
    private String baseName = null;

    /**
     */
    private Locale locale;

    /**
     */
    private ResourceBundle messages = null;

    /**
     */
    public MessageResources(String language, String country, String bundle) {
      this.lang = language;
      this.country = country;
      this.baseName = bundle;
      initBundle();
    }

    /**
     * The <code>initBundle</code> initializes <code>Locale</code> onject
     * and loads ResourceBundle .
     */
    private void initBundle() {
        if (lang == null && country == null)
            locale = new Locale("en", "US");
        else
            locale = new Locale(lang, country);
        try {
            if (messages == null)
                messages = ResourceBundle.getBundle(baseName, locale);
        } catch (Exception e) {
            log.severe("Exception in initBundle :" + e.getMessage());
        }
    }

    /**
     * This method gets a localized message from default resource message file using a key.
     * if it can not find a message, it will return the input message without any changes.
     *
     * @param key 
     * @return localized message as found in resource file.
     */
    public String getMessage(String key) {
        String localizedMessage;
        try {
            localizedMessage = getResource().getString(key);
        } catch (Exception e) {
            localizedMessage = key;
            log.severe("Exception in getMessage(" + key + "):" + e.getMessage());
        }
        return localizedMessage;
    }
    public ResourceBundle getMessages() {
      return(messages);
    }
    
    protected abstract ResourceBundle getResource();

    public String toString() {
        return "\nBundleUtils{" +
            "\n  language='" + lang + "'" +
            "\n  country='" + country + "'" +
            "\n  bundle='" + baseName + "'" +
            "\n  locale='" + locale + "'" +
            "\n  messages='" + messages + "'" +
            "\n}\n";
    }
    
}
