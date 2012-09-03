package org.noranj.tax.v2012.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * 
 * @version 0.3.2012MAR01
 * @since 0.3.2012MAR01
 * @change
 */
public class RPCInEvent extends GwtEvent<RPCInEventHandler> {
  public static Type<RPCInEventHandler> TYPE = new Type<RPCInEventHandler>();


  @Override public Type<RPCInEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(RPCInEventHandler handler) {
    handler.onRPCIn(this);
  }
}
