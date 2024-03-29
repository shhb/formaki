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
public class UserDTO implements Serializable {

  protected static final long serialVersionUID = 85316669519477098L;

  private String id;

  private String firstName;
  
  private String lastName;
  
  private String emailAddress;
  
  //BA-2012-AUG-11 Using the Key to have an un-owned relationship.
  //private PartyDTO account;
  private String accountId;

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
  public UserDTO() {
  }

  /**
   * This method is used when we really don't know anything about the user.
   * @param firstName
   * @param lastName
   * @param emailAddress
   */
  public UserDTO (String firstName, 
                  String lastName,
                  String emailAddress) {
  
    this(null, firstName, lastName, emailAddress, 
      null, 
      null,
      0,
      0,
      null);    
  }

  /**
   * 
   * @param id
   * @param firstName
   * @param lastName
   * @param emailAddress
   * @param accountId
   * @param activityType
   * @param lastLoginOn
   * @param lastActive
   * @param profile
   */
  public UserDTO(String id, String firstName, String lastName,
                      String emailAddress, String accountId, ActivityType activityType,
                      long lastLoginOn,
                      long lastActive,
                      UserProfileDTO profile) {
    super();
    //BA:2012-08-22 fixed [TAX-22]
    //this.id = id;
    this.id = (id!=null && !id.equals("")?id:null); // null and empty string means the user is a new entity. [TAX-22]
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailAddress = emailAddress;
    this.accountId = accountId;
    this.activityType = activityType;
    this.lastLoginOn = lastLoginOn;
    this.lastActive = lastActive;
    this.profile = profile;
  }

  /**
   * 
   * @param user
   */
  public UserDTO(UserDTO user) {
    super();
    setId (user.getId());
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.emailAddress = user.getEmailAddress();
    this.accountId = user.getAccountId();
    this.activityType = user.getActivityType();
    this.lastLoginOn = user.getLastLoginOn();
    this.lastActive = user.getLastActive();
    this.profile = user.getProfile();
  }
  
  /**
   * mapString format is like a properties file.
   * 
   * @param mapString
   * @since 0.2.2012081
   * @version 0.2.2012081
   */
  public UserDTO(Map<String, String> map) {
  	super();
  	
  	setId(map.get("id"));
  	setFirstName(map.get("firstName"));
  	setLastName(map.get("lastName"));
    setEmailAddress(map.get("emailAddress"));
    setAccountId(map.get("accountId"));
    
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
    //BA:12-10-11
    this.id = (id!=null && !id.equals("")?id:null); // null and empty string means the user is a new entity. [TAX-22]
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

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
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
  	str.append(getLastName());
  	str.append("] Email[");
  	str.append(getEmailAddress());
  	str.append("]");
  	
  	return(str.toString());
  }
}
