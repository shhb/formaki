package org.noranj.tax.v2012.client.service;

import java.util.Map;

import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;
import org.noranj.tax.v2012.shared.dto.QuestionSettingsDTO;
import org.noranj.tax.v2012.shared.type.QuestionCategoryType;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.2.20121001.2240
 * @since 0.2.20121001.2240
 */
@RemoteServiceRelativePath("questionService")
public interface QuestionService extends RemoteService {

  /** 
   * It gets applicant DTO and question category (income, deduction, ...) then uses the applicantID and type of TAX service to find 
   * the list of questions that are available to the applicant plus the settings (selected, hideForm, doneForm ...).
   * 
   * @param applicant
   * @param questionCategoryType defines the category of the questions such as Income. Deduction, ...
   * @return the list of question settings taht is returned as a Map. The key is the question ID and the value is the question settings.
   */
  public Map<String, QuestionSettingsDTO> getListOfQuestions(ApplicantDTO applicant, QuestionCategoryType questionCategoryType);

}
