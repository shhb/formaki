package org.noranj.formak.server.domain.sa;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.noranj.formak.server.domain.core.Profile;
import org.noranj.formak.shared.dto.UserProfileDTO;

/**
 * It HAS NOT BEEN IMPLEMENTED YET
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB21
 * @deprecated HAS NOT BEEN IMPLEMENTED YET
 */
@PersistenceCapable(detachable="true")
public class UserProfile extends Profile {

  /** @deprecated In the future when we will have different themes, user can select their favorite theme. */ 
  @Persistent
  private long themeID;


  public UserProfile() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UserProfile(UserProfileDTO userProfile) {
    super();
    //TODO implement the body 
  }
  
  /**
   * 
   * @return
   */
  public UserProfileDTO getUserProfileDTO() {
    UserProfileDTO userProfileDTO = new UserProfileDTO();
    return(userProfileDTO);
  }

  public long getThemeID() {
    return themeID;
  }

  public void setThemeID(long themeID) {
    this.themeID = themeID;
  }

}
