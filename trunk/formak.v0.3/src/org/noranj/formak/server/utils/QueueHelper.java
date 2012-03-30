package org.noranj.formak.server.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;

import org.noranj.formak.shared.Constants;
import org.noranj.formak.shared.GlobalSettings;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * It gets tasks and put them in queues. The idea is to simplify how we are using the queues. 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 * @since 0.3.20120329
 * @version 0.3.20120329
 * @change
 *
 */
public class QueueHelper {

  private static final Logger logger = Logger.getLogger( QueueHelper.class.getName() );

  private static final String C_FAST_QUEUE_NAME = "fast";
  private static final String C_FAST_QUEUE_BASE_URL =  "/_ah/queue/fast";
  
  private static final String C_SLOW_QUEUE_NAME = "slow";
  private static final String C_SLOW_QUEUE_BASE_URL = "/_ah/queue/slow";
  
  private static final String C_LOWRISK_QUEUE_NAME = "lowrisk";
  private static final String C_LOWRISK_QUEUE_BASE_URL = "/_ah/queue/lowrisk";
  
  private static final String C_SENDMAIL_URL = "/sendmail";
  
  /**
   * 
   * @param to
   * @param subject
   * @param body
   */
	public static void sendMailNotification(InternetAddress to, String subject, byte[] body) {

		logger.info("Adding send mail notification task to queue.");
		
		if(logger.isLoggable(Level.FINE)) {
			logger.fine("queue ["+C_FAST_QUEUE_NAME+"] at ["+C_FAST_QUEUE_BASE_URL+ C_SENDMAIL_URL+"] toAddress["+to+"] subject["+subject+"] body of mail notification task is ["+String.valueOf(body)+"]");
		}
		
    Queue queue = QueueFactory.getQueue(C_FAST_QUEUE_NAME);
    
		if(logger.isLoggable(Level.FINE))
			logger.fine("Got the "+C_FAST_QUEUE_NAME+"queue");
    
		queue.add(Builder.withUrl(C_FAST_QUEUE_BASE_URL + C_SENDMAIL_URL).param(Constants.C_MAIL_FROM_ADDRESS_PROP_NAME, GlobalSettings.C_MAIL_FROM_ADDRESS).param(Constants.C_MAIL_TO_ADDRESS_PROP_NAME, to.getAddress()).param("subject", subject).param(Constants.C_MAIL_BODY_PROP_NAME, String.valueOf(body)).method(Method.POST));
    
  	logger.info("send mail notification task is added to queue successfully.");
  	
	}
	
}
