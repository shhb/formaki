package org.noranj.idnt.server.servlet;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.idnt.server.domain.User;

@SuppressWarnings("serial") 
public class LoginGoogleCallbackServlet extends HttpServlet {
  
  private static Logger log = Logger.getLogger(LoginGoogleCallbackServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    
    Principal googleUser = request.getUserPrincipal();
    
    if (googleUser != null) {
      
      User sysUser = LoginHelper.loginStarts(request.getSession(), googleUser.getName());
      
      if (sysUser == null) { // user is not in Formak
        log.warning("user is not in the system - " + googleUser.getName());
        // user is not in the system, so we redirect them to the sign up page. //TODO SA review 
        response.sendRedirect(LoginHelper.getSignupURL(request)+"#signup");
        return;
      }
      else {
        log.info("User id:" + sysUser.getId() + " email: " + sysUser.getEmailAddress() + " " + request.getUserPrincipal().getName());
      }
    }
    response.sendRedirect(LoginHelper.getApplitionURL(request));
  }
}
