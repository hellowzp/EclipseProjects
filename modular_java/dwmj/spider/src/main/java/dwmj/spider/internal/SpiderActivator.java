/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.spider.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import dwmj.index.IndexService;

public final class SpiderActivator implements BundleActivator {
   private ServiceTracker indexServiceTracker;

   private static String[] REPOSITORIES = new String[] {
         "http://www.dudewheresmyjar.com/repo/" };

   private static JarFilePopulator[] POPULATORS = new JarFilePopulator[] {
      new PomBasedJarFilePopulator(), new JarContentBasedJarFilePopulator()
   };

   private final MavenSpider[] spiders = new MavenSpider[REPOSITORIES.length];

   public void start(BundleContext context) throws Exception {
      
      indexServiceTracker = new ServiceTracker(context, IndexService.class
                  .getName(), null);
      indexServiceTracker.open();
      

      for (int i = 0; i < REPOSITORIES.length; i++) {
         MavenSpider spider = new MavenSpider(indexServiceTracker);
         spider.setRepositoryUrl(REPOSITORIES[i]);
         spider.setJarFilePopulators(POPULATORS);
         
         Thread thread = new Thread(spider);
         thread.start();
      }
   }

   public void stop(BundleContext context) throws Exception {
      for (int i = 0; i < spiders.length; i++) {
         spiders[i].stop();
      }
      indexServiceTracker.close();
   }
}
