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

  private UserProfileDTO profile;

  
  //////////////////////////////////////////////////////////////////
  /////
  //////////////////////////////////////////////////////////////////
  public SystemUserDTO() {
  }

  public SystemUserDTO(String id, String firstName, String lastName,
                      String emailAddress, String parentClientId, ActivityType activityType,
                      UserProfileDTO profile) {
    super();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.parentClientId = parentClientId;
    this.activityType = activityType;
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

  public UserProfileDTO getProfile() {
    return profile;
  }

  public void setProfile(UserProfileDTO profile) {
    this.profile = profile;
  }
  
  
}
