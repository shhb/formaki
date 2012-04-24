package org.noranj.formak.server.domain.sa;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.FetchGroups;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import org.noranj.formak.server.domain.core.Profile;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.ChildEntity;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB28
 * @change
 *  BA-2012-FEB-28 Added two new attributes lastActive, lastLoginOn.
 */
@PersistenceCapable(detachable="true")
@Index(name="EMAIL", unique="true",  members={SystemUser.C_EMAIL_ADDRESS}) //BA-2012-FEB-23 Added to be able to search by emailAddress
@FetchGroups({
  @FetchGroup(name=SystemUser.C_FETCH_GROUP_PROFILE , members={@Persistent(name="profile")}),
  @FetchGroup(name=SystemUser.C_FETCH_GROUP_PARENT_CLIENT, members={@Persistent(name="parentClientId")})})
public class SystemUser implements Serializable, ChildEntity {

  @NotPersistent
  private static final long serialVersionUID = 2739249843928429147L;

  public static final String C_FETCH_GROUP_PROFILE = "profile"; 
  public static final String C_FETCH_GROUP_PARENT_CLIENT= "parentClient"; 

  
  /** A unique identifier for the user. */
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  //private Long id; // Child object can not have LONG key.
  private Key id; // Child object can not have LONG key.
  
  @Persistent
  //private SystemClientParty parentClient;
  @Extension(vendorName="datanucleus", key="gae.parent-pk", value="true") 
  private Key parentClientId;
  
  @Persistent
  private String firstName;

  @Persistent
  private String lastName;

  @Persistent
  private String emailAddress;
  public final static String C_EMAIL_ADDRESS = "emailAddress"; 
  
  @Persistent
  private ActivityType activityType = ActivityType.Deactive; 

  @Persistent
  private long lastActive;
  
  @Persistent
  private long lastLoginOn;
  
  @Persistent
  private UserProfile profile;
  
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

  public SystemUser(String id, String parentClientId, String firstName,
                    String lastName, String emailAddress, ActivityType activityType,
                    UserProfile profile/*, long version*/) {
    super();
    setId(id);
    setParentClientId(parentClientId);
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.activityType = activityType;
    this.profile = profile;
    //this.version = version;
  }

  /**
   * 
   * @param systemUserDTO
   * @param parentClient
   * @deprecated NOT NEEDED AND NOT USED. REMOVE THE TAG IF NEEDED. It makes the code complicated.
   */
  public SystemUser(SystemUserDTO systemUserDTO) {
    
    this(systemUserDTO.getId(),
          systemUserDTO.getParentClientId(),
          systemUserDTO.getFirstName(),
          systemUserDTO.getLastName(),
          systemUserDTO.getEmailAddress(),
          systemUserDTO.getActivityType(),
          new UserProfile(systemUserDTO.getProfile()));
    
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

  public String getParentClientId() {
    return ((this.parentClientId!=null)?KeyFactory.keyToString(this.parentClientId):null);
  }

  public void setParentClientId(String parentClientId) {
    this.parentClientId = (parentClientId!=null)?KeyFactory.stringToKey(parentClientId):null;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getFullName() {
    return firstName +" "+ lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public ActivityType getActivityType() {
    return activityType;
  }

  public void setActivityType(ActivityType activityType) {
    this.activityType = activityType;
  }

  public long getLastActive() {
    return lastActive;
  }

  public void setLastActive(long lastActive) {
    this.lastActive = lastActive;
  }

  public long getLastLoginOn() {
    return lastLoginOn;
  }

  public void setLastLoginOn(long lastLoginOn) {
    this.lastLoginOn = lastLoginOn;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(UserProfile userProfile) {
    this.profile = userProfile;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
  /**
   * 
   * @return
   */
  public SystemUserDTO getSystemUserDTO () {
    
    //FIXME do we need to get Profile too? should it be in another bean or call? or we can fetch what we want and use the attributes carefully to not get NullPointerException.
    SystemUserDTO suDTO = new SystemUserDTO(getId(), firstName, lastName, emailAddress, 
                                            getParentClientId(), 
                                            activityType, 
                                            lastActive,
                                            lastLoginOn,
                                            null // can not use Profile because it is not fetched 
                                            /*(profile!=null?profile.getUserProfileDTO():null)*/ //FIXME
                                            );
    
    return(suDTO);
  }

  /** This is implemented so we can use DAL1ToNHelper. */
  @Override  //ChildEntity
  public String getParentId() {
    return getParentClientId();
  }
  
}
