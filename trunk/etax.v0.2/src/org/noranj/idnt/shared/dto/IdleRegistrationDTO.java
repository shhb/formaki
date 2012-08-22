package org.noranj.idnt.shared.dto;


import java.io.Serializable;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20120823
 * @since 0.2.20120823
 * @change
 *
 */
public class IdleRegistrationDTO implements Serializable {

  private static final long serialVersionUID=893628125412312L;

  /** A unique identifier for the IdleRegistration. */
  private String id;
  
  private String securityQuestion;

  private String securityQuestionAnswer;

  public IdleRegistrationDTO(String id, String securityQuestion, String securityQuestionAnswer) {
    super();
    this.id = id;
    this.securityQuestion = securityQuestion;
    this.securityQuestionAnswer = securityQuestionAnswer;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSecurityQuestion() {
    return securityQuestion;
  }

  public void setSecurityQuestion(String securityQuestion) {
    this.securityQuestion = securityQuestion;
  }

  public String getSecurityQuestionAnswer() {
    return securityQuestionAnswer;
  }

  public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
    this.securityQuestionAnswer = securityQuestionAnswer;
  }

}
