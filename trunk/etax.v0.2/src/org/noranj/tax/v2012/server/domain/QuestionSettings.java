package org.noranj.tax.v2012.server.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.tax.v2012.shared.dto.QuestionSettingsDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * It stores the data related to applicant's question settings.
 * 
 * NOTE: there is an owned relationship between Applicant and QuestionSettings.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20121011
 * @since 0.2.20121008
 * @change
 *
 */
@PersistenceCapable
public class QuestionSettings {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key id;

  /** to build relations ships. */
  @Persistent
  private Applicant applicant; //this is used to build the relationship.
  
  /** It stores the ID of the Simplified Question definition. */
  String questionId;
  
  /** it indicates whether the question is selected or not. */
  boolean selected;
  
  /** it indicates whether the data entry of the associated form is done or not. */
  boolean formDone;
  
  /** it indicates whether the associated form must be displayed or not. */
  boolean formHidden;

  public String getId() {
    if(id !=null)
      return KeyFactory.keyToString(id);
    return(null);
  }

  public void setId(String id) {
    if(!id.equals("0"))
      this.id = KeyFactory.stringToKey(id);
  }
  
  public Applicant getApplicant() {
    return applicant;
  }

  public void setApplicant(Applicant applicant) {
    this.applicant = applicant;
  }

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isFormDone() {
    return formDone;
  }

  public void setFormDone(boolean doneForm) {
    this.formDone = doneForm;
  }

  public boolean isFormHidden() {
    return formHidden;
  }

  public void setFormHidden(boolean formHidden) {
    this.formHidden = formHidden;
  }

  /**
   * It returns a DTO copy of the data.
   * @return
   */
  public QuestionSettingsDTO getDTO() {
    QuestionSettingsDTO dto = new QuestionSettingsDTO(  getId(), 
                                                        (applicant!=null?applicant.getId():null), 
                                                        questionId, 
                                                        selected, formDone, formHidden);
    return(dto);
  }
}
