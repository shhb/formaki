package org.noranj.formak.shared.dto;


import java.io.Serializable;

import org.noranj.formak.server.domain.sa.UserProfile;
import org.noranj.formak.shared.type.ActivityType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since
 * @version 0.3.2012FEB28
 * @change
 *  BA-2012-FEB-28 Added two new attributes lastActive, lastLoginOn.
 */
public class SystemUserDTO implements Serializable {

  private static final long serialVersionUID = 85316669519477098L;

  private String id;

  private String firstName;
  
  private String lastName;
  
  private String emailAddress;
  
  //BA-2012-FEB-25 Using the Key to have an un-owned relationship.
  //private PartyDTO parentClient;
  private String parentClientId;

  private ActivityType activityType = ActivityType.Deactive; 

  /** last time user logged in. */
  private long lastLoginOn;
  
  /** 
   * last time user was active. 
   * @deprecated not sure if we need this one when we have lastLoginOn. BA-2012-FEB-28
   */
  private long lastActive;
  
  private UserProfileDTO profile;

  
  //////////////////////////////////////////////////////////////////
  /////
  //////////////////////////////////////////////////////////////////
  public SystemUserDTO() {
  }

  public SystemUserDTO(String id, String firstName, String lastName,
                      String emailAddress, String parentClientId, ActivityType activityType,
                      long lastLoginOn,
                      long lastActive,
                      UserProfileDTO profile) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.parentClientId = parentClientId;
    this.activityType = activityType;
    this.lastLoginOn = lastLoginOn;
    this.lastActive = lastActive;
    this.profile = profile;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public String getParentClientId() {
    return parentClientId;
  }

  public void setParentClient(String parentClientId) {
    this.parentClientId = parentClientId;
  }

  public ActivityType getActivityType() {
    return activityType;
  }

  public void setActivityType(ActivityType activityType) {
    this.activityType = activityType;
  }

  public long getLastLoginOn() {
    return lastLoginOn;
  }

  public void setLastLoginOn(long lastLoginOn) {
    this.lastLoginOn = lastLoginOn;
  }

  public UserProfileDTO getProfile() {
    return profile;
  }

  public void setProfile(UserProfileDTO profile) {
    this.profile = profile;
  }

  public long getLastActive() {
    return lastActive;
  }

  public void setLastActive(long lastActive) {
    this.lastActive = lastActive;
  }
  
  
}
