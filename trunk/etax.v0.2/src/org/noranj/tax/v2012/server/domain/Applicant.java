package org.noranj.tax.v2012.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.noranj.idnt.server.domain.User;
import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionSettingsDTO;
import org.noranj.tax.v2012.shared.type.TaxServiceType;

/**
 * The main entity for applicants. It extends User entity and contains attributes related to TAX service.
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
@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
@FetchGroup(name=Applicant.C_QUESTIONS_FETCH_GROUP_NAME, members={@Persistent(name="questions")}) 
public class Applicant extends User {
  
  public final static String C_QUESTIONS_FETCH_GROUP_NAME = "questions";
  
  /**
   * It indicates the type of service the applicant signed up currently.
   */
  TaxServiceType taxServiceType = TaxServiceType.Basic;
  
  @Persistent(mappedBy="applicant", dependentElement="true")
  ////@Order(extensions= @Extension(vendorName="datanucleus", key="list-ordering", value="sqID asc")) 
  private List<QuestionSettings> questionsSettings= new ArrayList<QuestionSettings>();
  
  /**
   * This is the ID of the last page user visited.
   * It is used to show user the same page when login to the system.
   * The ID is used by UI to some how find the right page and display it.
   * NOTE: the ID could be the VIEW class name in client package.
   * When adding a new applicant, it must be set to SimplifiedQuestion page/view.
   */
  @Persistent
  long lastVistiedPageID /*= SIMPLIFIED_QUESTION_ID */;

  public Applicant(ApplicantDTO applicantDTO) {
    super(applicantDTO);
    // TODO Auto-generated constructor stub
  }
  
  
  /** 
   *
   */
  public ApplicantDTO getDTO() {
    ApplicantDTO dto = new ApplicantDTO(super.getDTO());
    dto.setId(getId());
    List<QuestionSettingsDTO> newQS = new ArrayList<QuestionSettingsDTO>();
    for(QuestionSettings qs : questionsSettings)
      newQS.add(qs.getDTO());
    dto.setQuestionSettings(newQS);
    return(dto);
  }

}
