package org.noranj.formak.server.domain.sa;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.noranj.formak.server.domain.core.Party;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.ParentEntity;
import org.noranj.formak.shared.type.PartyRoleType;

/**
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
@PersistenceCapable(detachable="true")
@FetchGroup(name=SystemClientParty.C_FETCH_GROUP_USERS, members={@Persistent(name="users")}) //this is used with pm.getFetchPlan().setMaxFetchDepth(n) to control how deep the data is retrieved up front (used in detaching). To get only the order, setMaxFetchDepth(0) and to get order and orderItems, setMaxFetchDepth(1).
public class SystemClientParty extends Party implements Serializable, ParentEntity {

  public static final String C_FETCH_GROUP_USERS = "users"; 
  //public static final String C_FETCH_GROUP_PROFILE = "profile"; 
  
  
  /** 
   * This parameter name is used by Authentication filter to add Client Id to request.
   * It is also used by Namespace filter or any other class that needs to retrieve clientId.
   * @deprecated this is used in namespace filter. but ha not been used yet. 2012-FEB-09.
   */
  @NotPersistent
  public static final String C_SYSTEM_CLIENT_PARTY_ID_PARAMETER_NAME = "systemClientPartyId";
  
  @NotPersistent
  private static final long serialVersionUID = 2767432443928429147L;
  
  /** 
   * list of users belong to the party.
   * All the users in this list share the same namespace.
   */
  @Persistent /*(mappedBy="parentClient") BA-2012-FEB-25 the relationship is no longer an owned relationship because we need to search SystemUsr directly. */
  @Element(dependent = "true") 
  private List<String> userIds;
  
  ////////////////////////////////////////////////
  //////                                    //////
  //////              METHODS               //////
  //////                                    //////
  ////////////////////////////////////////////////
  public SystemClientParty() {
    
  }
  /**
   * 
   * @param name
   * @param roles
   */
  public SystemClientParty(String name, String logoURI, ActivityType activityType, Set<PartyRoleType> roles) {
    super(name, logoURI, activityType, roles);
  }

  public SystemClientParty(SystemClientPartyDTO systemClientPartyDTO) {
    super(  systemClientPartyDTO.getName(), 
            systemClientPartyDTO.getLogoURI(), 
            systemClientPartyDTO.getActivityType(), 
            systemClientPartyDTO.getRoles());
  }
  
  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> usersIds) {
    this.userIds = userIds;
  }

  /** 
   * It adds the user to the list of users and return a pointer to the user in the list.
   * The link can be used to retrieve the ID (the id is assigned by data store).
   * @param systemUserDTO
   * @return
   */
  public void addUser(String userId) {
    if (userIds == null) {
      userIds = new ArrayList<String>();
    }
    this.userIds.add(userId);
  }
  
  @Override //ParentEntity
  public void addChildId(String childId) {
    addUser(childId);
  }
  
  @Override //ParentEntity
  public List<String> getChildIds() {
    return getUserIds();
  }

}
