
package org.noranj.tax.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @version 0.3.2012Aug01
 * @since 0.3.2012Aug01
 * @change 
 */
public class LogoutEvent extends GwtEvent<LogoutEventHandler> {
  public static Type<LogoutEventHandler> TYPE = new Type<LogoutEventHandler>();

  public LogoutEvent() {
  }

  @Override public Type<LogoutEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(LogoutEventHandler handler) {
    handler.onLogout(this);
  }
}
