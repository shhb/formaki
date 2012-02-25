package org.noranj.formak.shared.exception;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class NotFoundException extends Exception { // DO not use GWT NotFoundException because GWT is for UI and we should not mix UI with backend.

  /**
   * 
   */
  private static final long serialVersionUID = 2095565017645574441L;

  public NotFoundException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public NotFoundException(String arg0, Throwable arg1) {
    super(arg0, arg1);
    // TODO Auto-generated constructor stub
  }

  public NotFoundException(String arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  public NotFoundException(Throwable arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  
}
