/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/

package dwmj.spider.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import dwmj.domain.JarFile;
import dwmj.index.IndexService;


public class MavenSpider {
   private static final Logger LOGGER = Logger.getLogger("MavenSpider");
   private JarFilePopulator[] jarFilePopulators = new JarFilePopulator[] {};
   private String repositoryUrl;
   private boolean active = true;
   private IndexService indexService;

   public MavenSpider(IndexService indexService) {
      this.indexService = indexService;
   }
   

   public void run() {
      try {
         active = true;
         crawl(repositoryUrl);
      }
      catch (Exception e) {
    	  LOGGER.log(Level.SEVERE,"There was some trouble crawling the repository:  "
                     + repositoryUrl);
    	  LOGGER.log(Level.SEVERE, e.getMessage());
      }
   }

   public void stop() {
      active = false;
   }

   public void setRepositoryUrl(String repositoryUrl) {
      this.repositoryUrl = repositoryUrl;
   }
   
   public void setJarFilePopulators(JarFilePopulator[] jarFilePopulators) {
      this.jarFilePopulators = jarFilePopulators;
   }

   private void crawl(final String baseUrl) throws Exception {
      if (!active) return;

      Parser parser = new HtmlParse().getParser();
      ParserCallback callback = new HTMLEditorKit.ParserCallback() {
         @Override
         public void handleStartTag(Tag tag, MutableAttributeSet attributeSet,
                     int pos) {
            if (tag == Tag.A) {
               handleLink(baseUrl, attributeSet);
            }
         }
      };

      URL repoUrl = new URL(baseUrl);
      InputStream repoIS = repoUrl.openConnection().getInputStream();
      parser.parse(new InputStreamReader(repoIS), callback, true);
      repoIS.close();
   }

   private void handleLink(final String baseUrl,
               MutableAttributeSet attributeSet) {
      String link = (String) attributeSet.getAttribute(HTML.Attribute.HREF);
      if (isOrdinaryJarFile(link)) {
         handleJarFile(baseUrl + link);
      } else if (isSubFolder(link)) {
    	 sleep(); // throttle requests to avoid beating up on the repository
         handleSubFolder(baseUrl + link);
      }
   }

   private boolean isSubFolder(String link) {
      // "maven" is only applicable to central
      return link.endsWith("/") && !link.startsWith("/maven")
                  && !link.equals("/") && !link.endsWith("../");
   }

   
   private void handleJarFile(String jarUrl) {
      // ...
      
      JarFile jarFile = new JarFile();
      jarFile.setRepository(repositoryUrl);
      jarFile.setRawUrl(jarUrl);
      if (jarUrl.replace(".class", "").endsWith("-SNAPSHOT")) {
         jarFile.setSnapshot(true);
      }

      if (hasPomFile(jarUrl)) {
         jarFile.setHasJavadoc(hasJavadocJar(jarUrl));
         jarFile.setHasSource(hasSourceJar(jarUrl));

         for (int i = 0; i < jarFilePopulators.length; i++) {
            jarFilePopulators[i].populate(jarFile, jarUrl);
         }

         
         if(jarFile.isIndexable()) {
            indexService.addJarFile(jarFile);
         }
         
      }
   }

   private boolean hasPomFile(String jarUrl) {
      return urlExists(jarUrl.replace(".jar", ".pom"));
   }

   private boolean hasSourceJar(String jarUrl) {
      return urlExists(jarUrl.replace(".jar", "-sources.jar"));
   }

   private boolean hasJavadocJar(String jarUrl) {
      return urlExists(jarUrl.replace(".jar", "-javadoc.jar"));
   }

   private boolean urlExists(String url) {
      InputStream is = null;
      try {
         URL sourceUrl = new URL(url);
         is = sourceUrl.openConnection().getInputStream();
         return true;
      }
      catch (Exception e) {
         return false;
      }
      finally {
         if (is != null) {
            try {
               is.close();
            }
            catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   }

   private boolean isOrdinaryJarFile(String link) {
      return link.endsWith(".jar") && !link.contains("-sources")
                  && !link.contains("-javadoc");
   }

   private void handleSubFolder(String url) {
      try {
         crawl(url);
      }
      catch (Exception e) {}
   }
   
   private void sleep() {
	   try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// do nothing
	}
   }

}


@SuppressWarnings("serial")
class HtmlParse extends HTMLEditorKit {
   @Override
   public HTMLEditorKit.Parser getParser() {
      return super.getParser();
   }
}

