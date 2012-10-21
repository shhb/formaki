package org.noranj.tax.v2012.server.service;


import java.util.Map;
import java.util.logging.Logger;

import org.noranj.core.server.DALHelper;
import org.noranj.core.server.JDOPMFactory;
import org.noranj.core.shared.exception.ServiceCallFailed;
import org.noranj.tax.v2012.client.service.QuestionService;
import org.noranj.tax.v2012.server.domain.Question;
import org.noranj.tax.v2012.shared.Constants;
import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;
import org.noranj.tax.v2012.shared.dto.QuestionSettingsDTO;
import org.noranj.tax.v2012.shared.type.QuestionCategoryType;

import com.google.appengine.api.NamespaceManager;
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
  public Map<Long, QuestionSettingsDTO> getListOfQuestionSettings(ApplicantDTO applicant, QuestionCategoryType questionCategoryType) {
    // TODO HERE
    // it should get list of questions and build the map.
    // the question IDs carry the question category type.
    // so as building the map the questions are being filtered.
    // we also need to use CacheMem to look up the data before going to data store.
    // the question is what needs to be added to cacheMem and where the cache is being used.
    return null;
  }

  /**
   * It gets a question DTO and add it to the data store.
   * If the question already exists, it will overwrite it.
   * 
   * NOTE: questions belong to SysAdmin namespace
   * NOTE: This is not accessible to Client.
   * 
   * @param question
   * @throws ServiceCallFailed if for any reason it can not add the question.
   */
  public long addQuestion(QuestionDTO question) throws ServiceCallFailed {
    //TODO it may not be needed
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE);
    
    assert(NamespaceManager.get().equals(Constants.C_SYSTEM_ADMIN_NAMESPACE)); //keep this line
    
    try {
    
      DALHelper<Question> questionHelper = new DALHelper<Question>(JDOPMFactory.getTxOptional(), Question.class);
      
      Question newQuestion = new Question(question);
      
      questionHelper.storeEntity(newQuestion);
      
      return(newQuestion.getId());
      
    } finally {
      NamespaceManager.set(currentNameSpace);
    }
    
  }
  
}
