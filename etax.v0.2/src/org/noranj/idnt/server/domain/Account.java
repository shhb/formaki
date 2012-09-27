package org.noranj.idnt.server.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.core.shared.type.ParentUnownedChildEntity;
import org.noranj.idnt.shared.dto.AccountDTO;
import org.noranj.idnt.shared.type.AccountType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * An account contains a group of users who share data.
 * 
 * In TAX system, it represents a family or accounting firm.
 * In Formak, it represents a business company or organisation.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.2.20120926
 * @since 0.2
 * @change
 * 
 */
@PersistenceCapable(detachable="true")
@FetchGroup(name=Account.C_FETCH_GROUP_USERS, members={@Persistent(name="users")}) //this is used with pm.getFetchPlan().setMaxFetchDepth(n) to control how deep the data is retrieved up front (used in detaching). To get only the order, setMaxFetchDepth(0) and to get order and orderItems, setMaxFetchDepth(1).
public class Account implements Serializable, ParentUnownedChildEntity {

  // these are fetch group names.
  public static final String C_FETCH_GROUP_USERS = "users"; 
  public static final String C_FETCH_GROUP_PROFILE = "profile"; 
  
  /** 
   * This parameter name is used by Authentication filter to add Client Id to request.
   * It is also used by Namespace filter or any other class that needs to retrieve clientId.
   * @deprecated this is used in namespace filter but needs to be reviewed. 2012-SEP-26.
   */
  @NotPersistent
  public static final String C_ACCOUNT_ID_PARAMETER_NAME = "accountId";
  
  @NotPersistent
  private static final long serialVersionUID = 2767432443928429147L;

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key id;
  
  /** stores the name of party. */
  @Persistent
  private String name;
  
  @Persistent
  private ActivityType activityType = ActivityType.Deactive; 
  
  /**
   * URI of the logo in String format.
   * It could be the link to the logo on their web site.
   */
  @Persistent
  private String logoURI;
  
  /**
   * An account can have one type such as consultant, support, or super admin.
   */
  @Persistent(defaultFetchGroup="true")
  private AccountType type;

  /** 
   * This attribute is the one that is used to store the version of the object in data store.
   * The versioning mechanism is used for optimistic locking but we might find other usage for it.
   */
  @Persistent
  private long version;
  
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
  public Account() {
    
  }

  public Account(String name, String logoURI, ActivityType activityType, AccountType type) {
    this.name = name;
    this.logoURI = logoURI;
    this.activityType = activityType;
    this.type = type;
  }

  /** 
   * @deprecated BA:12-SEP-26 the UserIDs are missing from the constructor!!!!
   */ 
  public Account(AccountDTO accountDTO) {
    /*
    this(accountDTO.getName(), 
         accountDTO.getLogoURI(), 
         accountDTO.getActivityType(), 
         accountDTO.getRoles());
         */
    //FIXME BA:12-SEP-36 HOW ABOUT USER-IDs??!?!?
  }

  public String getId() {
    return ((id!=null)?KeyFactory.keyToString(id):null);
  }

  public void setId(String id) {
    this.id = (id!=null)?KeyFactory.stringToKey(id):null;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
  
  //BA:12-SEP-26 the account type is no longer a set. so no need to this method.
  /* * this method is used to create PartyDTO. * /
  public AccountType cloneRoles() {
    //HashSet<AccountType> clonedRoles = new HashSet<AccountType>();
    //for (AccountType role : this.role) {
    //  clonedRoles.add(role);
    //}
    return AccountType.Accounting; //FIXME
  }
  */
  
  public void setType(AccountType type) {
    this.type = type;
  }

  public String getLogoURI() {
    return logoURI;
  }

  public void setLogoURI(String logoURI) {
    this.logoURI = logoURI;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }

  /** this method is used to create AccountDTO. */
  public List<String> cloneUserIds() {
    List<String> clonedUserIds = new ArrayList<String>();
    for (String userId : this.userIds) {
      clonedUserIds.add(userId);
    }
    return clonedUserIds;
  }
  
  /** 
   * It adds the userId to the list of userIds.
   * 
   * @param userId
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

  /** creates DTO and fill it with the data.
   *  
   * BA:12-SEP-26 It is commented out because not sure if it is needed and do not know how to resolve the conflict between DTO and Entity. AccountDTO has the list of UserDTOs but here we have the list of UserIds.
   *
   *  
  public AccountDTO getPartyDTO() {
    
    AccountDTO partyDTO = new AccountDTO(getId(), name, logoURI.toString(), activityType, cloneRoles(), userIds);
    return(partyDTO);
  }
  */
}
