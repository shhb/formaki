package org.noranj;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.InputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: aliti
 * Date: 6/27/12
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class UploadServlet extends HttpServlet {

	private static final Logger log =
		      Logger.getLogger(UploadServlet.class.getName());

		  public void doPost(HttpServletRequest req, HttpServletResponse res)
		      throws ServletException, IOException {
		    try {
		      ServletFileUpload upload = new ServletFileUpload();
		      res.setContentType("text/plain");

		      FileItemIterator iterator = upload.getItemIterator(req);
		      while (iterator.hasNext()) {
		        FileItemStream item = iterator.next();
		        InputStream stream = item.openStream();

		        if (item.isFormField()) {
		          log.warning("Got a form field: " + item.getFieldName());
		        } else {
		          log.warning("Got an uploaded file: " + item.getFieldName() +
		                      ", name = " + item.getName());
		          
		          //TODO:DA:2012-8-6: It should add some code for store the file on GAE file store
		          int len;
		          byte[] buffer = new byte[8192];
		          while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
		            res.getOutputStream().write(buffer, 0, len);
		          }
		        }
		      }
		    } catch (Exception ex) {
		      throw new ServletException(ex);
		    }
		  }

}
