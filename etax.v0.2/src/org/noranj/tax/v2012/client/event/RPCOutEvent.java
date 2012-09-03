package org.noranj.tax.v2012.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @version 0.3.2012MAR01
 * @since 0.3.2012MAR01
 * @change
 */
public class RPCOutEvent extends GwtEvent<RPCOutEventHandler> {
  public static Type<RPCOutEventHandler> TYPE = new Type<RPCOutEventHandler>();


  @Override public Type<RPCOutEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(RPCOutEventHandler handler) {
    handler.onRPCOut(this);
  }
}
