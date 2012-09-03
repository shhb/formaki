package org.noranj.tax.v2012.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.FetchGroups;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.server.domain.Profile;
import org.noranj.idnt.server.domain.User;
import org.noranj.idnt.server.domain.UserProfile;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.v2012.shared.type.SimplifiedCategoryType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * @author BA
 * @version 0.2.20120820
 * @since 0.2.20120820
 * @change
 */
@PersistenceCapable(detachable = "true")
// @ Index(name="EMAIL", unique="true", members={User.C_EMAIL_ADDRESS})
// //BA-2012-FEB-23 Added to be able to search by emailAddress
// @ FetchGroups({
// @ FetchGroup(name=User.C_FETCH_GROUP_PROFILE ,
// members={@Persistent(name="profile")}),
// @ FetchGroup(name=User.C_FETCH_GROUP_PARENT_CLIENT,
// members={@Persistent(name="parentClientId")})})
public class SimplifiedQuestion implements Serializable {

	@NotPersistent
	private static final long serialVersionUID = 2739249843928429147L;

	// public static final String C_FETCH_GROUP_PROFILE = "profile";
	// public static final String C_FETCH_GROUP_PARENT_CLIENT= "parentClient";

	/** A unique identifier for the user. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	// private Long id; // Child object can not have LONG key.
	private Key id; // Child object can not have LONG key.

	// @ Persistent
	// private SystemClientParty parentClient;
	// @ Extension(vendorName="datanucleus", key="gae.parent-pk", value="true")
	// private Key parentClientId;

	@Persistent
	private String question;

	@Persistent
	private SimplifiedCategoryType category;

	/**
	 * This attribute is the one that is used to store the version of the object
	 * in data store. The versioning mechanism is used for optimistic locking
	 * but we might find other usage for it.
	 */
	@Persistent
	private long version;

	// //////////////////////////////////////////////
	// //// //////
	// //// METHODS //////
	// //// //////
	// //////////////////////////////////////////////

	public SimplifiedQuestion(String id, String question,
			SimplifiedCategoryType category) {
		super();
		setId(id);
		this.question = question;
		this.category = category;
	}

	public String getId() {
		return ((id != null) ? KeyFactory.keyToString(id) : null);
	}

	public void setId(String id) {
		this.id = (id != null) ? KeyFactory.stringToKey(id) : null;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public SimplifiedCategoryType getSimplifiedCategoryType() {
		return category;
	}

	public void setSimplifiedCategoryType(SimplifiedCategoryType category) {
		this.category = category;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
