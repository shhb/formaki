package org.noranj.tax.v2012.shared.dto;

import java.util.List;

import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.v2012.shared.type.TaxServiceType;

/**
 * This class extends UserDTO from Identity package.
 * The extra attributes are typeOfService,
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20121001.2240
 * @since 0.2.20121001.2240
 * @change
 *
 */
public class ApplicantDTO extends UserDTO {

  TaxServiceType taxServiceType= TaxServiceType.Unknown;
  
  List<QuestionSettingsDTO> questionSettings;

  public ApplicantDTO(UserDTO user, TaxServiceType taxServiceType, List<QuestionSettingsDTO> questionSettings) {
    super(user);
    this.taxServiceType = taxServiceType;
    this.questionSettings = questionSettings;
  }
  public ApplicantDTO(UserDTO user) {
    super(user);
    this.taxServiceType = TaxServiceType.Basic;
    this.questionSettings = null;
  }

  public TaxServiceType getTaxServiceType() {
    return taxServiceType;
  }

  public void setTaxServiceType(TaxServiceType taxServiceType) {
    this.taxServiceType = taxServiceType;
  }

  public List<QuestionSettingsDTO> getQuestionSettings() {
    return questionSettings;
  }

  public void setQuestionSettings(List<QuestionSettingsDTO> questionSettings) {
    this.questionSettings = questionSettings;
  }

}
