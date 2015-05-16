/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.spider.naive;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import dwmj.index.IndexService;
import dwmj.spider.internal.JarFilePopulator;

public final class SpiderActivator implements BundleActivator {
   private ServiceReference serviceReference;

   private static String[] REPOSITORIES = new String[] {
         "http://repo1.maven.org/maven2/",
         "http://repo.compass-project.org/org/" };

   private static JarFilePopulator[] POPULATORS = new JarFilePopulator[] {};

   private final MavenSpider[] spiders = new MavenSpider[REPOSITORIES.length];

   public void start(BundleContext context) throws Exception {
      serviceReference = 
         context.getServiceReference(IndexService.class.getName());  // (1) 

      IndexService indexService = 
         (IndexService) context.getService(serviceReference);  // (2)

      for (int i = 0; i < REPOSITORIES.length; i++) {
         Thread thread = new Thread(new MavenSpider(REPOSITORIES[i],
                     indexService, POPULATORS));
         thread.start();
      }
   }

   public void stop(BundleContext context) throws Exception {
      for (int i = 0; i < spiders.length; i++) {
         spiders[i].stop();
      }
      context.ungetService(serviceReference);
   }
}
