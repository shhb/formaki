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
package org.noranj.tax.server.domain.core;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.noranj.tax.shared.dto.ProfileDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB21
 */
@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION", //@Version is used to implement optimistic locking but we may use it for another purposes.
         extensions={@Extension(vendorName="datanucleus", key="field-name", value="version")})
@FetchGroup(name=Profile.C_FETCH_GROUP_CONTACTINFO, members={@Persistent(name="contactInfo")}) 
public abstract class Profile {

  @NotPersistent
  private static final long serialVersionUID = -4593281497301316373L;

  // these are fetch group names.
  public static final String C_FETCH_GROUP_CONTACTINFO = "contactInfo"; 
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//  private Long id;
  private Key id;

  @Persistent
  private ContactInfo contactInfo; //FIXME this must be a class
  
  /** 
   * This attribute is the one that is used to store the version of the object in data store.
   * The versioning mechanism is used for optimistic locking but we might find other usage for it.
   */
  @Persistent
  private long version;

  ////////////////////////////////////////////////
  //////                                    //////
  //////              METHODS               //////
  //////                                    //////
  ////////////////////////////////////////////////
  public Profile() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   */
  //public Long getId() {
  public String getId() {
    return ((id!=null)?KeyFactory.keyToString(id):null);
  }

  //public void setId(Long id) {
  public void setId(String id) {
    this.id = (id!=null)?KeyFactory.stringToKey(id):null;
  }

  public ContactInfo getContactInfo() {
    return contactInfo;
  }

  public void setContactInfo(ContactInfo contactInfo) {
    this.contactInfo = contactInfo;
  }
  
  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
  /**
   * It creates a DTO and fills it with data of this object.
   * @return
   */
  public ProfileDTO getProfileDTO() {
    ProfileDTO profileDTO = new ProfileDTO(getId(), (contactInfo!=null?contactInfo.getContactInfoDTO():null));
    return(profileDTO);
  }
  
}
