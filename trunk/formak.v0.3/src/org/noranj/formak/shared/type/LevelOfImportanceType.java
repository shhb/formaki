package org.noranj.formak.shared.type;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 * 
 * @author
 */
public enum LevelOfImportanceType implements Serializable, IsSerializable {

  High("Immidiate Attention"),
  Medium("Important"),
  Worthy("Better to Look At It"),
  Less("Look if you have time"),
  JustFYI("Just For Your Information"),
  NotImportant("Normal"),
  Junk("Junk");
    
  private String description;

  LevelOfImportanceType () {
    
  }
  
  LevelOfImportanceType(String newDescription) {
    this.setDescription(newDescription);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /** how can we make it multilingual!?!?! 
   * Ideas:
   * 1- Adding all languages to the code. LessAttractive 
   */
//  public String getDescription(Locale locale) {
//    /* it should check the locale and then return the proper text. */
//    return this.description;
//  }
  
  
}
