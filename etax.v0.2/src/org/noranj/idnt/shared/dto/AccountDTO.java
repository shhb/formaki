package org.noranj.idnt.shared.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.shared.type.AccountType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20120927
 * @since 0.2.2012
 * @change
 * 
 */
public class AccountDTO implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 6224939034974030263L;
  
  private String id;
  
  private String name;
  
  /**
   * URI of the logo.
   * It could be the link to the logo on their web site.
   */
  private String logoURI;
  
  /** indicates the status of party. 
   * The most use for the attribute is to find out whether it is "Active" or not.
   */
  private ActivityType activityType; 

  /**
   * A party can have one or more type such as buyer and seller.
   * Or client and trading party.
   */
  //private AccountType type;
  private AccountType type;

  /** 
   * list of users belong to the party.
   * All the users in this list share the same namespace.
   */
  private List<UserDTO> users;

  /**
   * 
   */
  public AccountDTO() {
  }
  
  /**
   * 
   * @param id
   * @param name
   * @param logoURI
   * @param activityType
   * @param partyTypes
   * @param users
   */
  public AccountDTO(String id, String name, String logoURI,
                              ActivityType activityType, 
                              AccountType partyTypes, 
                              List<UserDTO> users) {
    this.id = id;
    this.name = name;
    this.logoURI = logoURI;
    this.activityType = activityType;
    this.type = partyTypes;
    this.users = users;
  }
  
  /** 
   * @deprecated it is not a good idea to use such constructor 
   * because then other codes have to use the same field name as used here.
   * I don't think it is a good idea to define the named as constants. 2012-06-13 
   */
  public AccountDTO(Map<String, String> map) {
  	super();
  	setId(map.get("id"));
  	setName(map.get("name"));
  	setLogoURI(map.get("logoURI"));
  	if (map.get("activityType")!=null)
  		setActivityType(ActivityType.valueOf(map.get("activityType")));
  	else 
  		setActivityType(ActivityType.Deactive);
  	setType(map.get("accountType"));
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogoURI() {
    return logoURI;
  }

  public void setLogoURI(String logoURI) {
    this.logoURI = logoURI;
  }

  public ActivityType getActivityType() {
    return activityType;
  }

  public void setActivityType(ActivityType activityType) {
    this.activityType = activityType;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public void setType(String type) {
    this.type = AccountType.fromString(type);
  }

  /** type are separated by comma.
   * @since 0.3.20120322
   * @version 0.3.20120322
   * /
  public void setTypes(String typeStr) {
    this.type = AccountType.convertToSet(typeStr);
  }
  */
  
  public List<UserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<UserDTO> users) {
    this.users = users;
  }

  public void addUser(UserDTO userDTO) {
    if (users == null) {
      users = new ArrayList<UserDTO>();
    }
    
    this.users.add(userDTO);
  }
  
}
