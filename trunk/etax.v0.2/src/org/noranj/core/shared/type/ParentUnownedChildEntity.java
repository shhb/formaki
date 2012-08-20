package org.noranj.core.shared.type;

import java.util.List;

/**
 * 
 * This interface must be implemented when a class has an unowned child entity.
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.2
 * @version 0.2
 */
public interface ParentUnownedChildEntity {
  
  void addChildId(String childId);
  List<String> getChildIds();
  
}
