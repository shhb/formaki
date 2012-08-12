package org.noranj.tax.shared.dto;

import java.io.Serializable;
import java.util.Set;

import org.noranj.tax.shared.type.ActivityType;
import org.noranj.tax.shared.type.PartyRoleType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3
 * @version 0.3.20120322
 */
public class PartyDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 8032558360718706251L;
  
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
   * A party can have one or more roles such as buyer and seller.
   * Or client and trading party.
   */
  private Set<PartyRoleType> roles;

  
  public PartyDTO() {
  }
  
  public PartyDTO(String id, String name, String logoURI, ActivityType activityType, Set<PartyRoleType> partyRoles) {
    
    super();
    this.id = id;
    this.name = name;
    this.logoURI = logoURI;
    this.activityType = activityType;
    this.roles = partyRoles;
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

  public Set<PartyRoleType> getRoles() {
    return roles;
  }

  public void setRoles(Set<PartyRoleType> roles) {
    this.roles = roles;
  }

  /** roles are separated by comma.
   * @since 0.3.20120322
   * @version 0.3.20120322
   */
  public void setRoles(String rolesStr) {
  	this.roles = PartyRoleType.convertToSet(rolesStr);
  }
  
}
