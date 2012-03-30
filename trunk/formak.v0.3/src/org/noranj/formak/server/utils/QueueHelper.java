package org.noranj.formak.server.utils;

import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions.Builder;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

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
  private static final String C_SLOW_QUEUE_NAME = "slow";
  private static final String C_LOWRISK_QUEUE_NAME = "low-fail";
  
	public void sendEmailNotification(InternetAddress to, String subject, String body) {

		logger.info("sending email notification to["+to+"] subject["+subject+"]");
  	
    Queue queue = QueueFactory.getQueue(C_FAST_QUEUE_NAME);
    queue.add(Builder.withUrl("/path?a=b&c=d").method(Method.POST));
    
  	logger.info("notification is sent successfully.");
  	
	}
	
}
