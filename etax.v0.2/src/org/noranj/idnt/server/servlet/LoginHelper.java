package org.noranj.idnt.server.servlet;



import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.noranj.core.server.DALHelper;
import org.noranj.core.server.JDOPMFactory;
import org.noranj.core.server.utils.ServletHelper;
import org.noranj.core.server.utils.ServletUtils;
import org.noranj.core.shared.Constants;
import org.noranj.idnt.server.SystemAdminHelper;
import org.noranj.idnt.server.domain.User;
import org.noranj.idnt.shared.dto.SystemUserDTO;

import com.google.appengine.api.NamespaceManager;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @change
 *  
 * BA-12-AUG-10 Added getSignupURL
 */
public class LoginHelper extends RemoteServiceServlet {
  
  private static final long serialVersionUID = 2888983680310646846L;

  private static Logger logger = Logger.getLogger(LoginHelper.class.getName());
  
  //FIXME Use this attribute to block users after certain times attempting to login to the system. */
  /** number of times a user can try to login before being blocked. */ 
  private static final int NUM_RETRIES = 5;
  
  /**
   * 
   * @param request
   * @return
   */
  static public String getApplitionURL(HttpServletRequest request) {

    if (ServletHelper.isDevelopment(request)) {
      if (System.getProperty(Constants.C_APPLICATION_URL_PROP_NAME)!=null) //FIXME add this to documentation
        return System.getProperty(Constants.C_APPLICATION_URL_PROP_NAME);
      return(Constants.C_DEVELOPMENT_URL);
    } else {
      return ServletUtils.getBaseUrl(request);
    }

  }

  //TODO SA 
  /**
   * This method returns the URL to sign up page where we let users sign up if they are not in Formak.
   * @param request
   * @return URL of the sign up page
   */
  static public String getSignupURL(HttpServletRequest request) {

    //FIXME the following code just copied from getApplicationURL and MUST BE FIXED.
    if (ServletHelper.isDevelopment(request)) {
      if (System.getProperty(Constants.C_APPLICATION_URL_PROP_NAME)!=null) //FIXME add this to documentation
        return System.getProperty(Constants.C_APPLICATION_URL_PROP_NAME)+ "/signup";
      return(Constants.C_DEVELOPMENT_URL);
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

    //BA:12-MAR-06 Added namespace
    String currentNameSpace = NamespaceManager.get();
    NamespaceManager.set(Constants.C_SYSTEM_ADMIN_NAMESPACE); 

    try {
      DALHelper<User> systemUserHelper = new DALHelper<User>(JDOPMFactory.getTxOptional(), User.class);
      
      User sysUser = systemUserHelper.getEntityById (userId, null /*Fetch group*/, 1); 
      
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
      
    } finally {
      //BA:12-AUG-06 Added namespace
      NamespaceManager.set(currentNameSpace); 
    }
    
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
    
    HttpSession session = req.getSession();
    if (session == null) {
      logger.info("Session is null...");
      return false;
    } 
    else {
      return(isLoggedIn(session));
    }
    
  }

  /**
   * 
   * @param session
   * @return
   */
  static private boolean isLoggedIn(HttpSession session) {
    
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
  
  /** 
   * 
   * @param req
   * @return
   */
  static public String getClientIdOfLoggedInUser(HttpServletRequest req) {

    if (req == null) {
      logger.info("Request is null..."); //TODO it must be logger.error
      return null;
    }
    else {
      HttpSession session = req.getSession();
      if (session == null) {
        logger.info("Session is null..."); //TODO it must be logger.error
        return null;
      } 
      else {
        if (isLoggedIn(session)) {
          String clientId = (String) session.getAttribute(Constants.C_CLIENT_ID_PROPERTY_NAME);
          if (clientId==null) {
            logger.info("clientId is not found for loggedin user ["+ session.getAttribute(Constants.C_USER_ID_PROPERTY_NAME) +"] and it should not happen.");
            return null;
          }
          return clientId;
        }
      }
      return null;
    }
    
  }
  
  /**
   * 
   * @param session
   * @param userEmailAddress
   * @return
   */
  public static User loginStarts(HttpSession session, String userEmailAddress /*UserAccount user*/) {
    
    logger.info("user ["+userEmailAddress+"] tries to login");
    
    User sysUser = SystemAdminHelper.getSystemUser(userEmailAddress); // googleUser.getName());
    
    if (sysUser!=null) {
      // update session if login was successful
      session.setAttribute(Constants.C_USER_ID_PROPERTY_NAME, String.valueOf(sysUser.getId()));
      session.setAttribute(Constants.C_CLIENT_ID_PROPERTY_NAME, String.valueOf(sysUser.getParentClientId()));
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
