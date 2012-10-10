package org.noranj.tax.v2012.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.tax.v2012.shared.dto.QuestionDTO;
import org.noranj.tax.v2012.shared.type.QuestionCategoryType;

/**
 * It stores the simplified questions used to implement the system. 
 * 
 * NOTE: Question is not using auto-generated IDs but their IDs are set at startup.
 * 
 * NOTE: question and its description are not stored in this entity but they come from a localized resource.
 * That is why instead of storing the question and description, this entity contains their ResourceKey.
 * This module, both source code and documentation, is in the Public Domain, and
 * comes with NO WARRANTY. See http://www.noranj.org for further information.
 * 
 * @author BA
 * @version 0.2.20121011
 * @since 0.2.20120820
 * @change
 */
@PersistenceCapable(detachable = "true")
public class Question implements Serializable {

	@NotPersistent
	private static final long serialVersionUID = 2739249843928429147L;


	/** 
	 * A unique identifier for the question.
	 *  
	 * NOTE: These IDs are not generated but assigned by developers. 
	 */
	@PrimaryKey
	private Long id; // Using long key means this entity can not have any group entity parent.

	/**
	 * It is the resource key that stores the question text.
	 * It is used to get the localized question text and display it on screen.
	 */
	@Persistent
	private String questionRKey;


	/** it stores the category of question. */
	@Persistent
	private QuestionCategoryType category;

	
  /**
   * It is the resource key that stores the localized description text for the question.
   */
  @Persistent
  private String descriptionRKey;
	
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

	public Question(long id, String questionRKey,
			QuestionCategoryType category) {
		super();
		setId(id);
		this.questionRKey = questionRKey;
		this.category = category;
	}

	public Long getId() {
		return (id);
	}

	public void setId(Long id) {
		this.id = id;
	}

  public void setId(long id) {
    this.id = new Long(id);
  }
  
	public String getQuestionRKey() {
		return questionRKey;
	}

	public void setQuestionRKey(String questionRKey) {
		this.questionRKey = questionRKey;
	}

	public QuestionCategoryType getCategoryType() {
		return category;
	}

	public void setCategoryType(QuestionCategoryType category) {
		this.category = category;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public QuestionDTO getDTO() {
	  QuestionDTO dto = new QuestionDTO(id, questionRKey, category, descriptionRKey, version);
	  return(dto);
	}
}
