package org.noranj.tax.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @version 0.3.2012MAR01
 * @since 0.3.2012MAR01
 * @change 
 */
public interface LogoutEventHandler extends EventHandler {
  void onLogout(LogoutEvent event);
}
