/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.spider.internal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import dwmj.domain.JarFile;

public class PomBasedJarFilePopulator implements JarFilePopulator {

   public void populate(JarFile jarFile, String jarUrlSpec) {
      try {
         String pomUrlSpec = jarUrlSpec.replace(".jar", ".pom");   
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         dbf.setNamespaceAware(false);
         dbf.setValidating(false);
         DocumentBuilder docBuilder = dbf.newDocumentBuilder();
         Document document = docBuilder.parse(pomUrlSpec);
         
         XPathFactory xpathFactory = XPathFactory.newInstance();
         XPath xpath = xpathFactory.newXPath();
         
         jarFile.setGroupId(resolveValue(document, xpath, "groupId"));
         jarFile.setArtifactId(resolveValue(document, xpath, "artifactId"));
         jarFile.setVersion(resolveValue(document, xpath, "version"));
         
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   private String resolveValue(Document document, XPath xpath, String element) throws Exception {
      XPathExpression groupIdXPath = xpath.compile("string(project/" + element + ")");
      String value = groupIdXPath.evaluate(document);
      if(value == null || value.length() == 0) {
         groupIdXPath = xpath.compile("string(project/parent/" + element + ")");
         value = groupIdXPath.evaluate(document);
      }
      
      return value;
   }
}
