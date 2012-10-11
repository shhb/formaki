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
   * It gets applicant information and question category (income, deduction, ...) then uses the applicantID to retrieve 
   * the list of questions that are available to the applicant. 
   * Each value in returned map is the settings such as available, selected, formHidden, formDone ...
   * 
   * @param applicant stores the applicant information.
   * @param questionCategoryType defines the category of the questions such as Income, Deduction, Province Specific,...
   * @return the list of question settings that is returned as a Map. The key is the question ID and the value is the question settings.
   */
  public Map<Long, QuestionSettingsDTO> getListOfQuestionSettings(ApplicantDTO applicant, QuestionCategoryType questionCategoryType);

}
