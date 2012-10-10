package org.noranj.tax.v2012.shared.dto;

import org.noranj.tax.v2012.shared.type.QuestionCategoryType;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.2012
 * @since 0.2.2012
 * @change
 *
 */
public class QuestionDTO {

  /** 
   * A unique identifier for the question.
   *  
   * NOTE: These IDs are not generated but are fixed values. 
   */
  private Long id;

  /**
   * It is the resource key that stores the question text.
   * It is used to get the localized question text and display it on screen.
   */
  private String questionRKey;


  /** it stores the category of question such as Income, Deduction, ...*/
  private QuestionCategoryType category;

  
  /**
   * It is the resource key that stores the localized description text for the question.
   */
  private String descriptionRKey;
  
  /**
   * This attribute is the one that is used to store the version of the object
   * in data store. The versioning mechanism is used for optimistic locking
   * but we might find other usage for it.
   */
  private long version;

  public QuestionDTO() {
    
  }

  public QuestionDTO(Long id, String questionRKey, QuestionCategoryType category, String descriptionRKey, long version) {
    super();
    this.id = id;
    this.questionRKey = questionRKey;
    this.category = category;
    this.descriptionRKey = descriptionRKey;
    this.version = version;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getQuestionRKey() {
    return questionRKey;
  }

  public void setQuestionRKey(String questionRKey) {
    this.questionRKey = questionRKey;
  }

  public QuestionCategoryType getCategory() {
    return category;
  }

  public void setCategory(QuestionCategoryType category) {
    this.category = category;
  }

  public String getDescriptionRKey() {
    return descriptionRKey;
  }

  public void setDescriptionRKey(String descriptionRKey) {
    this.descriptionRKey = descriptionRKey;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
  
}
