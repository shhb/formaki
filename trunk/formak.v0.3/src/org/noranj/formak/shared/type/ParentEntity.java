package org.noranj.formak.shared.type;

import java.util.List;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public interface ParentEntity {
  
  void addChildId(String childId);
  List<String> getChildIds();
  
}
