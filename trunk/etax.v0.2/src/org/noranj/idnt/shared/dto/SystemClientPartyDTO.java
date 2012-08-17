package org.noranj.idnt.shared.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.tax.shared.type.PartyRoleType;


/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class SystemClientPartyDTO extends PartyDTO {

  /**
   * 
   */
  private static final long serialVersionUID = 6224939034974030263L;
  
  /** 
   * list of users belong to the party.
   * All the users in this list share the same namespace.
   */
  private List<SystemUserDTO> users;

  /**
   * 
   */
  public SystemClientPartyDTO() {
    
  }
  
  /**
   * 
   * @param id
   * @param name
   * @param logoURI
   * @param activityType
   * @param partyRoles
   * @param users
   */
  public SystemClientPartyDTO(String id, String name, String logoURI,
                              ActivityType activityType, Set<PartyRoleType> partyRoles, 
                              List<SystemUserDTO> users) {
    super(id, name, logoURI, activityType, partyRoles);
    this.users = users;
  }
  
  /** 
   * @deprecated it is not a good idea to use such constructor 
   * because then other codes have to use the same field name as used here.
   * I don't think it is a good idea to define the named as constants. 2012-06-13 
   */
  public SystemClientPartyDTO(Map<String, String> map) {
  	super();
  	setId(map.get("id"));
  	setName(map.get("name"));
  	setLogoURI(map.get("logoURI"));
  	if (map.get("activityType")!=null)
  		setActivityType(ActivityType.valueOf(map.get("activityType")));
  	else 
  		setActivityType(ActivityType.Deactive);
  	setRoles(map.get("partyRoles"));
  }

  public List<SystemUserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<SystemUserDTO> users) {
    this.users = users;
  }

  public void addUser(SystemUserDTO systemUserDTO) {
    if (users == null) {
      users = new ArrayList<SystemUserDTO>();
    }
    
    this.users.add(systemUserDTO);
  }
  
}
