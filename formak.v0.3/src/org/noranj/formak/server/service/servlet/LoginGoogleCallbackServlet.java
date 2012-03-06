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
package org.noranj.formak.server.service.servlet;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.formak.server.LoginHelper;
import org.noranj.formak.server.domain.sa.SystemUser;
import org.noranj.formak.shared.dto.SystemUserDTO;

@SuppressWarnings("serial") 
public class LoginGoogleCallbackServlet extends HttpServlet {
  
  private static Logger log = Logger.getLogger(LoginGoogleCallbackServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    
    Principal googleUser = request.getUserPrincipal();
    
    if (googleUser != null) {
      
      SystemUser sysUser = LoginHelper.loginStarts(request.getSession(), googleUser.getName());
      
      if (sysUser == null) { // user is not in Formak
        log.warning("user is not in the system - " + googleUser.getName());
        // user is not in the system, so we redirect them to the sign up page. //TODO SA review 
        response.sendRedirect(LoginHelper.getSignupURL(request));
        return;
      }
      else {
        log.info("User id:" + sysUser.getId() + " email: " + sysUser.getEmailAddress() + " " + request.getUserPrincipal().getName());
      }
    }
    response.sendRedirect(LoginHelper.getApplitionURL(request));
  }
}
