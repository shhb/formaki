package org.noranj.formak.server.utils;

import freemarker.template.Template;
import freemarker.template.Configuration;

import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Map;
import java.util.logging.Logger;

import org.noranj.formak.server.service.servlet.NamespaceFilter;

/**
 * 
 * 
 * This module, both source code and documentation, is in the Public Domain, and comes with NO WARRANTY.
 * See http://www.noranj.org for further information.
 *
 * @author
 */
public class FreeMarkerUtil {

  private static Logger logger = Logger.getLogger(FreeMarkerUtil.class.getName());
	
  /**
   *  Process a template using FreeMarker and print the results
   * @param datamodel
   * @param templatePathPrefix something like /META-INF or /META-INF/templates
   * @param templateName
   * @throws Exception
   */
  static public void freemarkerDo(Map datamodel, String templatePathPrefix, String templateName) throws Exception {

    Configuration cfg = new Configuration();
    cfg.setClassForTemplateLoading(FreeMarkerUtil.class, templatePathPrefix);
    Template tpl = cfg.getTemplate(templateName);
    OutputStreamWriter output = new OutputStreamWriter(System.out);

    tpl.process(datamodel, output);
  }

  
  public static void main(String[] args) {
    Properties data = new Properties();

    // Read the data file and process the template using FreeMarker
    try {
      data.load(new FileInputStream("C:/awork/ajava2/gprj/formak.v0.3/data/test.dat"));
      FreeMarkerUtil.freemarkerDo(data, "/META-INF/templates", "testcp.ftl");
    } catch (Exception e) {
      System.out.println(e.getLocalizedMessage());
    }
  }


  
}
