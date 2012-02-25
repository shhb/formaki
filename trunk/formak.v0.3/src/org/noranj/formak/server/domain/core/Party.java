package org.noranj.formak.server.domain.core;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.HashSet;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import org.noranj.formak.shared.dto.PartyDTO;
import org.noranj.formak.shared.type.ActivityType;
import org.noranj.formak.shared.type.PartyRoleType;


/**
 * 
 * NOTE a trading party might become a client party.
 *  
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * 
 */
@PersistenceCapable(detachable="true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION", //@Version is used to implement optimistic locking but we may use it for another purposes.
         extensions={@Extension(vendorName="datanucleus", key="field-name", value="version")})
@FetchGroup(name=Party.C_FETCH_GROUP_PROFILE , members={@Persistent(name="profile")}) 
public abstract class Party implements Serializable {

  @NotPersistent
  private static final long serialVersionUID = -4593281497301316373L;

  // these are fetch group names.
  public static final String C_FETCH_GROUP_PROFILE = "profile"; 
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;
  
  /** stores the name of party. */
  @Persistent
  private String name;
  
  @Persistent
  private ActivityType activityType = ActivityType.Deactive; 
  
  /**
   * URI of the logo.
   * It could be the link to the logo on their web site.
   */
  @Persistent
  private URI logoURI;
  
  /**
   * A party can have one or more roles such as buyer and seller.
   * Or client and trading party.
   */
  @Persistent(defaultFetchGroup="true")
  private Set<PartyRoleType> roles;

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
  
  /**
   * 
   * @param name
   * @param logoURI
   * @param activityType
   * @param partyRoles
   */
  public Party(String name, URI logoURI, ActivityType activityType, Set<PartyRoleType> partyRoles) {
    super();
    this.name = name;
    this.logoURI = logoURI;
    this.activityType = activityType;
    this.roles = partyRoles;
  }

  /**
   * 
   * @param name
   * @param logoURI
   * @param activityType
   * @param partyRoles
   */
  public Party(String name, String logoURI, ActivityType activityType, Set<PartyRoleType> partyRoles) {
    super();
    this.name = name;
    try {
      this.logoURI = (logoURI!=null)?new URI(logoURI) : null;
    } catch(URISyntaxException uriex) {
      uriex.printStackTrace(); //FIXME LOG BA-2012-FEB-25
    }
    this.activityType = activityType;
    this.roles = partyRoles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
  
  public Set<PartyRoleType> getRoles() {
    return roles;
  }
  
  /** this method is used to create PartyDTO. */
  public Set<PartyRoleType> cloneRoles() {
    HashSet<PartyRoleType> clonedRoles = new HashSet<PartyRoleType>();
    for (PartyRoleType role : this.roles) {
      clonedRoles.add(role);
    }
    return clonedRoles;
  }

  public void setRoles(Set<PartyRoleType> roles) {
    this.roles = roles;
  }

  public URI getLogoURI() {
    return logoURI;
  }

  public void setLogoURI(URI logoURI) {
    this.logoURI = logoURI;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
  /** creates DTO and fill it with the data. */ 
  public PartyDTO getPartyDTO() {
    PartyDTO partyDTO = new PartyDTO( id, name, logoURI.toString(), activityType, cloneRoles());
    return(partyDTO);
  }

  
} 
