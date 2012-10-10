package org.noranj.tax.v2012.shared.dto;

import java.io.Serializable;
import java.util.List;


/**
 * It stores a tax service attribute including the id, list of questions, ....
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author BA
 * @version 0.2.20121010
 * @since 0.2.20121010
 * @change
 *
 */
public class TaxServiceDTO implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = -79269077877363992L;

  private long id;
  
  /** List of simplified questions Ids. */
  private List<Long> questionIds;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Long> getQuestionIds() {
    return questionIds;
  }

  public void setQuestionIds(List<Long> questionIds) {
    this.questionIds = questionIds;
  }

  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append(super.toString());
    str.append("id[");
    str.append(this.getId());
    str.append("]");
    str.append("QuestionIds{");
    for(Long id : questionIds) {
      str.append(id);
      str.append(",");
    }
    str.append("}\r\n");
    return(str.toString());
  }
}
