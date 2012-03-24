package org.noranj.formak.server;

import java.util.Map;
import java.util.logging.Logger;

import org.noranj.formak.server.domain.core.MailMessage;
import org.noranj.formak.server.service.SystemAdminServiceImpl;
import org.noranj.formak.server.service.servlet.SignUpMailHandlerServlet;
import org.noranj.formak.server.utils.Utils;
import org.noranj.formak.shared.dto.SystemClientPartyDTO;
import org.noranj.formak.shared.dto.SystemUserDTO;
import org.noranj.formak.shared.type.ActivityType;

/**
 * The methods in this class are used by services or servlets to implement services for System Admins.
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3
 * @version 0.3
 * @change
 *
 */
public class SystemAdminHelper {

  protected static Logger logger = Logger.getLogger(SystemAdminHelper.class.getName());

	/**
	 * 
	 * @param mail
	 */
	public static void signupUser(MailMessage mail) {
		
		logger.fine("Attributes in body are {");
		Map<String, String> dataFieldsFromBody = Utils.buildMap(mail.getBody().getContentAsString());
		
		for(String key:dataFieldsFromBody.keySet()) {
			logger.fine("key["+key+"] value=["+dataFieldsFromBody.get(key)+"]");
		} // for
		
    SystemClientPartyDTO sysClientDTO = new SystemClientPartyDTO(dataFieldsFromBody);
    
    sysClientDTO.setActivityType(ActivityType.Active); // to make sure the user is active and can login.
    if (sysClientDTO.getName()==null) {
    	sysClientDTO.setName("client-guest-" + System.currentTimeMillis());
    }
    
    SystemUserDTO sysUserDTO = new SystemUserDTO(dataFieldsFromBody);
    
    sysUserDTO.setActivityType(ActivityType.Active); // to make sure the user is active and can login.
    sysUserDTO.setEmailAddress(mail.getFromAddress()); // to overwrite the emailAddress in the mail body
    
    if (sysUserDTO.getFirstName()==null && sysUserDTO.getLastName()==null) {
    	sysUserDTO.setFirstName("Guest");
    	sysUserDTO.setLastName(String.valueOf(System.currentTimeMillis()));
    }

    SystemAdminServiceImpl service = new SystemAdminServiceImpl();
    sysUserDTO.setId(service.signup(sysClientDTO, sysUserDTO));
    logger.info("A new user successfully signed up. userid["+sysUserDTO.getId()+"] email["+sysUserDTO.getEmailAddress()+"]");
		
	}
		
} //class
