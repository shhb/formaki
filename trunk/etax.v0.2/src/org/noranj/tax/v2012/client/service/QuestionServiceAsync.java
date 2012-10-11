package org.noranj.tax.v2012.client.service;

import java.util.Map;

import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionSettingsDTO;
import org.noranj.tax.v2012.shared.type.QuestionCategoryType;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
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
public interface QuestionServiceAsync {

  /** 
   * It gets applicant DTO and uses the applicantID and type of TAX service to find 
   * the list of questions that are available to the applicant plus the answers to the questions.
   * 
   * NOTE: Answer to the questions basically are YES or NO.
   * 
   * @param applicant
   * @return
   */
  public void getListOfQuestionSettings(ApplicantDTO applicant, QuestionCategoryType /*income, deduction, province...)*/ simplifiedCategoryType, AsyncCallback<Map<Long, QuestionSettingsDTO>> callback);

}
