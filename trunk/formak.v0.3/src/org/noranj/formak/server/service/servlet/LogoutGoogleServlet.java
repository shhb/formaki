package org.noranj.formak.server.service.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.formak.server.LoginHelper;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LogoutGoogleServlet extends HttpServlet {

	  private static final long serialVersionUID = -4565961422877273742L;
	  private static Logger log = Logger.getLogger(LoginGoogleServlet.class.getName());
	  
	  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		    UserService userService = UserServiceFactory.getUserService();
		    
		    request.getSession().invalidate();
		    		    
		    String googleLogoutUrl = userService.createLogoutURL(LoginHelper.getApplitionURL(request));
		    
		    log.info("Going to Google logout URL: " + googleLogoutUrl);
		    
		    response.sendRedirect(googleLogoutUrl);
	  }
}
