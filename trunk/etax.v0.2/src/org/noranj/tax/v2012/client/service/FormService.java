package org.noranj.tax.v2012.client.service;

import java.util.List;
import java.util.Map;

import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.2.20121001.2240
 * @since 0.2.20121001.2240
 */
@RemoteServiceRelativePath("formService")
public interface FormService extends RemoteService {

  /** 
   * It gets applicant DTO and uses the applicantID and type of TAX service to find 
   * the list of questions that are available to the applicant plus the answers to the questions.
   * NOTE: Answer to the questions basically are YES or NO.
   * 
   * @param applicant
   * @return
   */
  public Map<String, QuestionDTO> getListOfForms(ApplicantDTO applicant);

}
