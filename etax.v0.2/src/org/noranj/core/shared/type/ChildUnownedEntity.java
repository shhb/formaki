package org.noranj.core.shared.type;

/**
 * 
 * This interface must be implemented when a class is an unowned child entity.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.2
 * @version 0.2
 */
public interface ChildUnownedEntity {

  public String getId();
  public String getParentId();
  
}
