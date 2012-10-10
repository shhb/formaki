package org.noranj.tax.v2012.server.service;

import java.util.Map;
import java.util.logging.Logger;

import org.noranj.tax.v2012.client.service.FormService;
import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.2.20121001.2240
 * @version 0.2.20121001.2240
 * @change
 */
public class FormServiceImpl extends RemoteServiceServlet implements FormService { 
  
  private static final long serialVersionUID = -2326021829857086171L;

  protected static Logger logger = Logger.getLogger(FormServiceImpl.class.getName());

  /**
   * @deprecated HAS NOT BEEN IMPLEMENTED YET.
   */
  @Override
  public Map<String, QuestionDTO> getListOfForms(ApplicantDTO applicant) {
    return(null);
  }
}
