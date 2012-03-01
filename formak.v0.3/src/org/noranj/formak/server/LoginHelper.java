/** 
 * Copyright 2010 Daniel Guermeur and Amy Unruh
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://connectrapp.appspot.com/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package org.noranj.formak.server;



import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.noranj.formak.SystemAdminServiceImplTest;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.server.service.JDOPMFactory;
import org.noranj.formak.server.utils.ServletHelper;
import org.noranj.formak.server.utils.ServletUtils;
import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.dto.SystemUserDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @modified
 *   
 */
public class LoginHelper extends RemoteServiceServlet {
  
  private static final long serialVersionUID = 2888983680310646846L;

  private static Logger logger = Logger.getLogger(LoginHelper.class.getName());
  private static final int NUM_RETRIES = 5;
  
  /**
   * 
   * @param request
   * @return
   */
  static public String getApplitionURL(HttpServletRequest request) {

    if (ServletHelper.isDevelopment(request)) {
      if (System.getProperty("applition.url")!=null) //FIXME add this to documentation
        return System.getProperty("applition.url");
      return("http://127.0.0.1:9888/Formak.html?gwt.codesvr=127.0.0.1:9997");
    } else {
      return ServletUtils.getBaseUrl(request);
    }

  }

  /**
   * 
   * @param session
   * @return
   */
  public static SystemUserDTO getLoggedInUser(HttpSession session) {

    if (session == null)
      return null; // user not logged in

    String userId = (String) session.getAttribute(Constants.C_USER_ID_PROPERTY_NAME); //FIXME document this attribute
    if (userId == null)
      return null; // user not logged in

    DALHelper<SystemUser> systemUserHelper = new DALHelper<SystemUser>(JDOPMFactory.getTxOptional(), SystemUser.class);
    
    SystemUser sysUser = systemUserHelper.getEntityById (userId, null /*Fetch group*/, 1); 
    
    if (sysUser!=null) {
      
      //TODO do we need to do this? think about how to do it and the impact on performance! BA-2012-FEB-29
      // the original code used the attached domain object to update it attribute and it does both fetch and update in one trabnsaction.
      /**
        sysUser.setLastActive(System.currentTimeMillis());
        systemUserHelper.storeEntity(sysUser);
      */

      return(sysUser.getSystemUserDTO());
    }
    else 
      return(null);
    
  }

  //TODO review and improve it BA-2012-FEB-28
  /** 
   * 
   * @param req
   * @return
   */
  static public boolean isLoggedIn(HttpServletRequest req) {

    if (req == null)
      return false;
    else {
      HttpSession session = req.getSession();
      if (session == null) {
        logger.info("Session is null...");
        return false;
      } else {
        Boolean isLoggedIn = (Boolean) session.getAttribute(Constants.C_LOGGED_IN_FLAG_PROPERTY_NAME);
        if(isLoggedIn == null){
          logger.info("Session found, but did not find loggedin attribute in it: user not logged in");
          return false;
        } else if (isLoggedIn){
          logger.info("User is logged in...");
          return true;
        } else {
          logger.info("User not logged in");
          return false;
        }
      }
    }
    
  }

  /**
   * 
   * @param userEmailAddress
   * @return
   */
  public static SystemUser getSystemUser(String userEmailAddress) {
    
    assert(userEmailAddress!=null && userEmailAddress.length()>0);
    
    DALHelper<SystemUser> systemUserHelper = new DALHelper<SystemUser>(JDOPMFactory.getTxOptional(), SystemUser.class);
    
    SystemUser sysUser = null;
    int retry = 1; 
    do {
      sysUser = systemUserHelper.getEntityByQuery(String.format("%s == '%s'", SystemUser.C_EMAIL_ADDRESS, userEmailAddress), /*filter*/ 
                                                            null /*ordering*/, null /*parameter*/, null /*value*/,
                                                            null,  /* ParentClient is no longer needs to be in Fatch Group because it is only a KEY    //new String[] {SystemUser.C_FETCH_GROUP_PARENT_CLIENT} , /* fetch groups */ 
                                                            1); /* max fetch depth */
      
      --retry;

      if (sysUser == null && (userEmailAddress.equals("buyer@noranj.com") || userEmailAddress.equals("seller@noranj.com"))) {
        logger.info("LoginHelper - Try to create sample data.");
        try {
          Startup.makeTestDataUserRetailerParty(); //Buyer
          Startup.makeTestDataUserManufacturerParty(); // Seller
        } catch (Exception ex) {
          ex.printStackTrace();
          logger.warning("LoginHelper - Failed to create sample data. Abort the process.");
          retry = -1;// do not try any more 
        }
        
      }
      
    }while (retry>=0);
    
    return(sysUser);
  }

  /**
   * 
   * @param session
   * @param userEmailAddress
   * @return
   */
  public static SystemUser loginStarts(HttpSession session, String userEmailAddress /*UserAccount user*/) {
    
    logger.info("user ["+userEmailAddress+"] tries to login");
    
    SystemUser sysUser = getSystemUser(userEmailAddress); // googleUser.getName());
    
    if (sysUser!=null) {
      // update session if login was successful
      session.setAttribute(Constants.C_USER_ID_PROPERTY_NAME, String.valueOf(sysUser.getId()));
      session.setAttribute(Constants.C_LOGGED_IN_FLAG_PROPERTY_NAME, true);
    }
    
    return sysUser;

    /** FIXME implement this so it updates these two attributes 
     * Another way to implement it is to rename getSystemUser to be login() and set the attributes in that method.
     * pay attention to performance and the number of updates/fetch per login.
     * 
    sysUser.setLastActive(System.currentTimeMillis());
    sysUser.setLastLoginOn(System.currentTimeMillis());
    
    systemAdminServiceImpl.updateSystemUser(sysUser);
    
    original sample code
    {
      // update user info under transactional control
      PersistenceManager pm = PMF.getTxnPm();
      Transaction tx = pm.currentTransaction();
      try {
        for (int i = 0; i < NUM_RETRIES; i++) {
          tx = pm.currentTransaction();
          tx.begin();
          u = (UserAccount) pm.getObjectById(UserAccount.class, aUser.getId());
          String channelId = ChannelServer.createChannel(u.getUniqueId());
          u.setChannelId(channelId);
          u.setLastActive(new Date());
          u.setLastLoginOn(new Date());
          try {
            tx.commit();
            // update session if successful
            session.setAttribute("userId", String.valueOf(u.getId()));
            session.setAttribute("loggedin", true);
            break;
          }
          catch (JDOCanRetryException e1) {
            if (i == (NUM_RETRIES - 1)) { 
              throw e1;
            }
          }
        } // end for
      } 
      catch (JDOException e) {
        e.printStackTrace();
        return null;
      } 
      finally {
        if (tx.isActive()) {
          logger.severe("loginStart transaction rollback.");
          tx.rollback();
        }
        pm.close();
      }
    } original sample code 
    */
  }

}
