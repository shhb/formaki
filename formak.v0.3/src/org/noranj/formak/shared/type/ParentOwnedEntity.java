package org.noranj.formak.shared.type;

import java.util.Set;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public interface ParentOwnedEntity {
  
  String getId();
  
  Set<String> getChildIds();
  
  void removeItem(String id);

}
