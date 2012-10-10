package org.noranj.tax.v2012.client.service;

import java.util.List;
import java.util.Map;

import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.tax.v2012.shared.dto.ApplicantDTO;
import org.noranj.tax.v2012.shared.dto.QuestionDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * NOTE: I removed all ServiceCallFailed exception because they are not necessary and GWT already has a mechanism to catch the exceptions
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @version 0.2.20121001.2240
 * @since 0.2.20121001.2240
 * @change
 * 
 */
public interface FormServiceAsync {

    public void getListOfForms(ApplicantDTO applicant, AsyncCallback<Map<String, QuestionDTO>> callback);

}
