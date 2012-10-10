package org.noranj.tax.v2012.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.noranj.tax.v2012.shared.dto.TaxServiceDTO;

/**
 * It stores a Tax Service and related data such as list of questions, price, ...
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
@PersistenceCapable(detachable="true")
@FetchGroup(name=TaxService.C_FETCH_GROUP_QUESTIONS, members={@Persistent(name=TaxService.C_FETCH_GROUP_QUESTIONS)})
public class TaxService implements Serializable /*, ChildUnownedEntity*/ {
  
    @NotPersistent
    private static final long serialVersionUID = 2739249843928429147L;

    @NotPersistent
    public static final String C_FETCH_GROUP_QUESTIONS = "questions"; 

    /** A unique identifier for the TaxService. */
    @PrimaryKey
    private Long id;

    @Persistent(mappedBy="applicant", dependentElement="true")
    private List<Long> questionIds= new ArrayList<Long>();

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public List<Long> getQuestionIds() {
      return questionIds;
    }

    /** 
     * To make sure none of the Long object is used outside of the entity, 
     * they are cloned.
     * @return
     */
    public List<Long> cloneQuestionIds() {
      List<Long> cloned = new ArrayList<Long>();
      for (long id: questionIds) {
        cloned.add(new Long(id));
      }
      return cloned;
    }
    
    public void setQuestionIds(List<Long> questionIds) {
      this.questionIds = questionIds;
    }
    
    /** 
     *
     */
    public TaxServiceDTO getDTO() {
      TaxServiceDTO dto = new TaxServiceDTO();
      dto.setId(id);
      dto.setQuestionIds(cloneQuestionIds());
      return(dto);
    }
}
