package org.noranj.core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.noranj.core.shared.utils.MessageResources;
import org.noranj.core.shared.utils.UIMessageResources;


public class TestMessageResources {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    UIMessageResources.setInstacnceNull();
    MessageResources messageResources = null; //UIMessageResources.getInstance ("fr", "CA", "META-INF/resources/appresource");
  }

  @After
  public void tearDown() throws Exception {
  }


  //@Test
  public void testGetMessageString() {
    MessageResources messageResources =null; //UIMessageResources.getInstance();
    if (messageResources.getMessage("test.key")==null)
      fail("Not found the message");
    System.out.printf(messageResources.getMessage("test.key"), 10);
    System.out.println("\r\n----------------------------------------------");
  }

  @Test
  public void testGetMessageStringFR_CA() {
   
    System.out.println("\r\n----------------------------------------------");
    if (UIMessageResources.getUIMessage("test.key")==null)
      fail("Not found the message");
    System.out.printf(UIMessageResources.getUIMessage("test.key"), 10);
    System.out.println("\r\n----------------------------------------------");
  }

  //@Test
  public void testGetMessageStringString() {
    fail("Not yet implemented");
  }

}
