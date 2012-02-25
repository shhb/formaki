package org.noranj.formak.server.domain.sa;

import java.io.Serializable;

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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.3.2012FEB21
 */
@PersistenceCapable(detachable="true")
@Index(name="EMAIL", unique="true",  members={SystemUser.C_EMAIL_ADDRESS}) //BA-2012-FEB-23 Added to be able to search by emailAddress
@FetchGroups({
  @FetchGroup(name=SystemUser.C_FETCH_GROUP_PROFILE , members={@Persistent(name="profile")}),
  @FetchGroup(name=SystemUser.C_FETCH_GROUP_PARENT_CLIENT, members={@Persistent(name="parentClient")})})
public class SystemUser implements Serializable{

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
  private SystemClientParty parentClient;
  
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

  public SystemUser(String id, SystemClientParty parentClient, String firstName,
                    String lastName, String emailAddress, ActivityType activityType,
                    UserProfile profile/*, long version*/) {
    super();
    setId(id);
    this.parentClient = parentClient;
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
   * /
  public SystemUser(SystemUserDTO systemUserDTO, SystemClientParty parentClient) {
    this(systemUserDTO.getId(),
          parentClient,
          systemUserDTO.getFirstName(),
          systemUserDTO.getLastName(),
          systemUserDTO.getEmailAddress(),
          systemUserDTO.getActivityType(),
          new UserProfile(systemUserDTO.getProfile()));
  }
  */ 
  
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

  public SystemClientParty getParentClient() {
    return parentClient;
  }

  public void setParentClient(SystemClientParty parentClient) {
    this.parentClient = parentClient;
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
  
  public SystemUserDTO getSystemUserDTO () {
    
    //FIXME do we need to get Profile too? should it be in another bean or call? or we can fetch what we want and use the attributes carefully to not get NullPointerException.
    SystemUserDTO suDTO = new SystemUserDTO(getId(), firstName, lastName, emailAddress, 
                                            (parentClient!=null?parentClient.getPartyDTO():null), 
                                            activityType, 
                                            (profile!=null?profile.getUserProfileDTO():null));
    
    return(suDTO);
  }
  
}
