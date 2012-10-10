package org.noranj.tax.v2012.shared.dto;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.tax.v2012.server.domain.Applicant;

import com.google.appengine.api.datastore.Key;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20121009
 * @since 0.2.20121009
 * @change
 *
 */
public class QuestionSettingsDTO implements Serializable {
  
  /**
   */
  private static final long serialVersionUID = 4909600202852027805L;

  private String id;

  /** to build relations ships. */
  private String applicantId; //this is used to build the relationship.
  
  /** It stores the ID of the Simplified Question definition. */
  String questionId;
  
  /** it indicates whether the question is selected or not. */
  boolean selected;
  
  /** it indicates whether the data entry of the associated form is done or not. */
  boolean formDone;
  
  /** it indicates whether the associated form must be displayed or not. */
  boolean formHidden;

  /**
   */
  public QuestionSettingsDTO(String id, String applicantId, String questionId, 
                              boolean selected, 
                              boolean formDone,
                              boolean formHidden) {
    super();
    this.id = id;
    this.applicantId = applicantId;
    this.questionId = questionId;
    this.selected = selected;
    this.formDone = formDone;
    this.formHidden = formHidden;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getApplicantId() {
    return applicantId;
  }

  public void setApplicantId(String applicantId) {
    this.applicantId = applicantId;
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

  public void setFormDone(boolean formDone) {
    this.formDone = formDone;
  }

  public boolean isFormHidden() {
    return formHidden;
  }

  public void setFormHidden(boolean formHidden) {
    this.formHidden = formHidden;
  }
  
}
