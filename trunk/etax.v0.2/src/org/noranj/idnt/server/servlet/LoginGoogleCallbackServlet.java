package org.noranj.idnt.server.servlet;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.core.shared.type.ActivityType;
import org.noranj.idnt.server.domain.User;
import org.noranj.idnt.server.service.SystemAdminServiceImpl;
import org.noranj.idnt.shared.dto.UserDTO;
import org.noranj.idnt.shared.dto.UserProfileDTO;
import org.noranj.tax.v2012.client.service.SystemAdminService;
import org.noranj.tax.v2012.client.service.SystemAdminServiceAsync;
import org.noranj.tax.v2012.shared.type.PartyRoleType;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings("serial") 
public class LoginGoogleCallbackServlet extends HttpServlet {
  
  private static Logger log = Logger.getLogger(LoginGoogleCallbackServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    
    Principal googleUser = request.getUserPrincipal();
    
    if (googleUser != null) {
      
      User sysUser = LoginHelper.loginStarts(request.getSession(), googleUser.getName());
      
      if (sysUser == null) { // user is not in Ra
        log.warning("user is not in the system - " + googleUser.getName());
        // user is not in the system, so we redirect them to the sign up page. //TODO SA review 
        //REM:SA:20121015:After discuss with BA, this line removed  
        doSaveAutomatic(googleUser);
        sysUser = LoginHelper.loginStarts(request.getSession(), googleUser.getName());

      }
      log.info("User id:" + sysUser.getId() + " email: " + sysUser.getEmailAddress() + " " + request.getUserPrincipal().getName()); 
    }
    response.sendRedirect(LoginHelper.getApplitionURL(request));
  }

	public void doSaveAutomatic( Principal googleUser) {
		HashSet<PartyRoleType> roles = new HashSet<PartyRoleType>();
	    roles.add(PartyRoleType.Buyer);
	    UserDTO user = new UserDTO("",
		                    "Guest",
							"User",
							googleUser.getName(),
							"",
		                    ActivityType.Active, 
							System.currentTimeMillis(), 
							System.currentTimeMillis(), 
							new UserProfileDTO());

	    SystemAdminServiceImpl systemAdminService = new SystemAdminServiceImpl();
	    user.setId(systemAdminService.signup(null, user));
		}
	}
