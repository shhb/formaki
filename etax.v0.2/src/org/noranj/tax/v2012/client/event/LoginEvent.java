
package org.noranj.tax.v2012.client.event;

import org.noranj.idnt.shared.dto.UserDTO;

import com.google.gwt.event.shared.GwtEvent;

//TODO SA I added this one but not sure how exactly it will be used.
/**
 * 
 * @version 0.3.2012MAR01
 * @since 0.3.2012MAR01
 * @change
 *  BA:12-MAR-01 I replaced UserAccountDTO with SystemUserDTO
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {
  
  public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
  
  private final UserDTO user;

  public LoginEvent(UserDTO user) {
    this.user = user;
  }

  public UserDTO getUser() {
    return user;
  }

  @Override public Type<LoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(LoginEventHandler handler) {
    handler.onLogin(this);
  }
}
