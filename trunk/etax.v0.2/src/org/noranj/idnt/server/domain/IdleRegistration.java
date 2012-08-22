package org.noranj.idnt.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.idnt.shared.dto.IdleRegistrationDTO;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * This class is used to store the registration that made by an applicant on behalf of another person.
 * An email is sent to registered user and they need to login to the system and then answer the security question
 * as stored in this entity.
 * If they answer the question correctly, their registration will be active.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.2.20120823
 * @since 0.2.20120823
 * @change
 */
@PersistenceCapable(detachable="true")
@Index(name="EMAIL", unique="true",  members={User.C_EMAIL_ADDRESS})
public class IdleRegistration implements Serializable {

  @NotPersistent
  private static final long serialVersionUID=893628125412312L;

  /** A unique identifier for the IdleRegistration. */
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key id; // Child object can not have LONG key.
  
  @Persistent
  private String securityQuestion;

  @Persistent
  private String securityQuestionAnswer;

  /**
   * 
   * @param idleRegistrationDTO
   */
  public IdleRegistration(IdleRegistrationDTO idleRegistrationDTO) {
    super();
    setId(idleRegistrationDTO.getId());
    this.securityQuestion = idleRegistrationDTO.getSecurityQuestion();
    this.securityQuestionAnswer = idleRegistrationDTO.getSecurityQuestionAnswer();
  }

  public String getId() {
    return ((id!=null)?KeyFactory.keyToString(id):null);
  }

  public void setId(String id) {
    this.id = (id!=null)?KeyFactory.stringToKey(id):null;
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
