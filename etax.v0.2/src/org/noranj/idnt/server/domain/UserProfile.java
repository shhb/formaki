/** 
 * Copyright 2011 Noranj.org
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://noranj.org/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package org.noranj.idnt.server.domain;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.noranj.idnt.shared.dto.UserProfileDTO;

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
