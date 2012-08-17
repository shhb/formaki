
package org.noranj.idnt.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginGoogleServlet extends HttpServlet /*extends LoginSuperServlet*/ {
  
  private static final long serialVersionUID = -4565961422877273742L;
  private static Logger log = Logger.getLogger(LoginGoogleServlet.class.getName());
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String callbackURL = buildCallBackURL(request);
    
    UserService userService = UserServiceFactory.getUserService();
    
    String googleLoginUrl = userService.createLoginURL(callbackURL);
    
    log.info("Going to Google login URL: " + googleLoginUrl);
    
    response.sendRedirect(googleLoginUrl);
    
  }
  
  /**
   * 
   * @param request
   * @return
   * @modified 
   *  BA-2012-FEB-28 it is copied from a super login servlet.
   */
  private String buildCallBackURL(HttpServletRequest request) {
    
    StringBuffer requestURL = request.getRequestURL();
    
    String callbackURL = requestURL.toString() + "callback";
    
    log.info("*** callback url: " + callbackURL); //FIXME use messages
    
    return callbackURL;
  }
  
}