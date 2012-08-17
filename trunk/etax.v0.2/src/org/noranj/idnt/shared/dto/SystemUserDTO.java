package org.noranj.idnt.shared.dto;


import java.io.Serializable;
import java.util.Map;

import org.noranj.core.shared.type.ActivityType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since
 * @version 0.3.2012AUG10
 * @change
 *  BA-2012-AUG-11 Added two new attributes lastActive, lastLoginOn.
 */
public class SystemUserDTO implements Serializable {

  private static final long serialVersionUID = 85316669519477098L;

  private String id;

  private String firstName;
  
  private String lastName;
  
  private String emailAddress;
  
  //BA-2012-AUG-11 Using the Key to have an un-owned relationship.
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

  /**
   * mapString format is like a properties file.
   * 
   * @param mapString
   * @since 0.3.2012081
   * @version 0.3.2012081
   */
  public SystemUserDTO(Map<String, String> map) {
  	super();
  	
  	setId(map.get("id"));
  	setFirstName(map.get("firstName"));
  	setLastName(map.get("lastName"));
    setEmailAddress(map.get("emailAddress"));
    setParentClientId(map.get("parentClientId"));
    
    if (map.get("activityType")!=null)
    	setActivityType(ActivityType.valueOf(map.get("activityType")));
    else
    	setActivityType(ActivityType.Deactive); //FIXME find out what is the correct default value.
    
    try {
    	setLastLoginOn(Long.parseLong(map.get("lastLoginOn")));
    } catch(Exception ex) {
    	setLastLoginOn(System.currentTimeMillis());
    }
    try {
    	setLastActive(Long.parseLong(map.get("lastActive")));
    } catch(Exception ex) {
    	setLastLoginOn(System.currentTimeMillis());
    }
    
    if (getFirstName()==null && getLastName()==null) {
      setNames(map.get("fullName")); // extract the first name and last name from the email
    }

    //FIXME needs to be reviewed!!!
    this.profile = new UserProfileDTO();
    
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
  /**
   * First Name and Last Name
   * @param fullName
   */
  public void setNames(String fullName) {
  	
  	setFirstName(null);
  	setLastName(null);
  	
  	if(fullName!=null) {
  		int pos = fullName.indexOf(32); //space
  		if (pos>0) {
  			setFirstName(fullName.substring(0, pos));
  			setLastName(fullName.substring(pos+1));
  		}
  		else {
  			setFirstName(fullName);
  			setLastName("");
  		}
  	} 
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

  public void setParentClientId(String parentClientId) {
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
  
  public String toString() {
  	
  	StringBuilder str = new StringBuilder();
  	str.append("First Name[");
  	str.append(getFirstName());
  	str.append("] Last Name[");
  	str.append(getFirstName());
  	str.append("] Email[");
  	str.append(getEmailAddress());
  	str.append("]");
  	
  	return(str.toString());
  }
}
