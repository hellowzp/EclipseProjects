/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.index.internal;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import dwmj.domain.JarFile;
import dwmj.index.IndexService;

public final class IndexServiceActivator implements BundleActivator {
   public void start(BundleContext bc) throws Exception {
      IndexService indexService = new IndexServiceImpl(getCompass());
      bc.registerService(IndexService.class.getName(), indexService, null);
   }

   public void stop(BundleContext bc) throws Exception {
   // nothing to do!
   }

   private Compass getCompass() {
      String tempDir = System.getProperty("java.io.tmpdir");
      CompassConfiguration config = new CompassConfiguration().setSetting(
                  CompassEnvironment.CONNECTION, tempDir + "/dudeindex")
                  .addClass(JarFile.class);

      return config.buildCompass();
   }
}
