package org.noranj.core.shared.type;

import java.io.Serializable;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public enum ActivityType implements Serializable {
  
  Active,
  Deactive,
  Blocked,
  Idel;

  
  ActivityType() {
  }

}
