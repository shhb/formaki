// Copyright 2010 Google Inc. All Rights Reserved.

package org.noranj.formak.server.service.servlet;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.noranj.formak.server.LoginHelper;
import org.noranj.formak.server.domain.sa.SystemClientParty;


/**
 * An example App Engine namespace setting filter.  
 * 
 * <p>This namespace filter provides for a number of strategies
 * as an example but is also careful not to override the
 * namespace where it has previously been set, for example, incoming 
 * task queue requests.
 * @deprecated NOT USED YET/
 */
public class NamespaceFilter implements Filter {

  private static Logger logger = Logger.getLogger(LoginFilter.class.getName());
  
  /**
   * Enumeration of namespace strategies.
   */
  enum NamespaceStrategy {
    
    /*
     * Use client ID as the namespace for all users in the same organization.
     */
    CLIENT_ID,
    
    /*
     * Use UserID as the namespace. 
     * It makes system unique for a single person and can not be shared later.
     */
    USER_ID,

    /*
     * Use the server name shown in the http request as the 
     * namespace name.
     */
    SERVER_NAME,
    
    /*
     * Use the Google Apps domain that was used to direct this
     * URL.
     * We use this namespace for super user's data such as users and clients of the system.
     * NOTE: an alternative to this could be SUPER_USER_ID option.
     */
    GOOGLE_APPS_DOMAIN,
    
    /*
     * Use empty namespace.
     * This could be used to store the global data used by all users.
     */
    EMPTY
    
  }
  
  /** The strategy to use for this instance of the filter. */
  private NamespaceStrategy strategy = NamespaceStrategy.EMPTY;
  
  /** The filter config. */
  private FilterConfig filterConfig;

  /**
   *  @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init(FilterConfig config) throws ServletException {
    
    this.filterConfig = config;
    String namespaceStrategy = config.getInitParameter("namespace-strategy");
    
    if (namespaceStrategy != null) {
      try {
        strategy = NamespaceStrategy.valueOf(namespaceStrategy);
      } catch (IllegalArgumentException exception) {
        // Badly configured namespace-strategy
        filterConfig.getServletContext().log(
            "web.xml filter config \"namespace-strategy\" setting " +
            "to \"" + namespaceStrategy + "\" is invalid. Select " +
            "from one of : " + 
            Arrays.asList(NamespaceStrategy.values()).toString());
        throw new ServletException(exception);
      }
    }
    
  }
  
  /**
   *  @see javax.servlet.Filter#destroy()
   */
  @Override
  public void destroy() {
    this.filterConfig = null;
  }

  /* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, 
   *     javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    
    /*
    System.out.println(System.currentTimeMillis());
    System.out.println("Server Name : " + request.getServerName());
    System.out.println("GoogleAppsNamespace : " + NamespaceManager.getGoogleAppsNamespace());
    */
    
    // If the NamespaceManager state is already set up from the
    // context of the task creator the current namespace will not
    // be null.  It's important to check that the current namespace
    // has not been set before setting it for this request.
    if (NamespaceManager.get() == null) {
      switch (strategy) {
        case CLIENT_ID : {
          System.out.println("Namespace is CLIENT_ID");
          String clientId = LoginHelper.getClientIdOfLoggedInUser((HttpServletRequest)request);
          if (clientId!=null) {
            NamespaceManager.set(clientId);
            System.out.println("Namespace is SET TO CLIENT_ID ["+clientId+"]");
          }
          else {
            //throw new IOException("Failed to set Namesapce.");
            System.out.println("Namespace is NOT SET because can not find required information. Probably user is not logged in.");
          }
          break;
        }
        
        case USER_ID : {
          NamespaceManager.set(UserServiceFactory.getUserService().getCurrentUser().getUserId());
          break;
        }

        case GOOGLE_APPS_DOMAIN : {
          NamespaceManager.set(NamespaceManager.getGoogleAppsNamespace());
          break;
        }

        case SERVER_NAME : {
          NamespaceManager.set(request.getServerName());
          break;
        }
        
        case EMPTY : {
          NamespaceManager.set("");
        }
      }
    }
    
    //FIXME Log
    System.out.println("namespace is set to : " + NamespaceManager.get());
    
    // chain into the next request
    chain.doFilter(request, response) ;
  }
  
}
