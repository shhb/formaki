package org.noranj.formak.server.service.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.noranj.formak.server.domain.sa.SystemClientParty;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @deprecated NOT NEEDED - Use LoginFilter instead
 */
public class AuthenticationFilter implements Filter {
	


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request,ServletResponse response, FilterChain chain) throws IOException, ServletException {
	  
		HttpServletRequest rq = (HttpServletRequest)request;
		HttpServletResponse rs = (HttpServletResponse)response;
		
	    //session check
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    if (rq.getSession().getAttribute(SystemClientParty.C_SYSTEM_CLIENT_PARTY_ID_PARAMETER_NAME)== null ||
	    	rq.getSession().getAttribute(SystemClientParty.C_SYSTEM_CLIENT_PARTY_ID_PARAMETER_NAME)==user.getUserId() )  { // if client Id is not found
	    	//HttpServletResponseWrapper rsw = new HttpServletResponseWrapper((HttpServletResponse) response);
	    	String uri = rq.getRequestURI();
	        
	    	/* FIXME temporary commented out.
	    	 UserManagmentImpl userManagmentImpl= new UserManagmentImpl();
	        String forwardPath = userManagmentImpl.login(uri,rq);
	        //rq.getRequestDispatcher(forwardPath).forward(rq, rs);
	        rs.sendRedirect(forwardPath);
          System.out.print("Forward From: "+uri+", To: " + forwardPath);
	        */
	    }
	    else {
	        try { chain.doFilter(request, response); }
	        catch (Exception e) {
	        	System.out.print("error" + e);
	            throw new RuntimeException(e);
	        }
	    }
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
