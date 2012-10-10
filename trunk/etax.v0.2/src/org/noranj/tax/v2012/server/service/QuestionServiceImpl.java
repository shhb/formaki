package org.noranj.tax.v2012.server.service;



import java.util.Map;
import java.util.logging.Logger;

import org.noranj.tax.v2012.client.service.QuestionService;
import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;
import org.noranj.tax.v2012.shared.dto.QuestionSettingsDTO;
import org.noranj.tax.v2012.shared.type.QuestionCategoryType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
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
public class QuestionServiceImpl extends RemoteServiceServlet implements QuestionService {//TODO:BA:2012-08-10: please check it 
  
  private static final long serialVersionUID = -2326021829857086171L;

  protected static Logger logger = Logger.getLogger(QuestionServiceImpl.class.getName());

  /**
   * @deprecated HAS NOT BEEN IMPLEMENTED YET.
   */
  @Override
  public Map<String, QuestionSettingsDTO> getListOfQuestions(ApplicantDTO applicant, QuestionCategoryType questionCategoryType) {
    // TODO HERE
    return null;
  }


}
