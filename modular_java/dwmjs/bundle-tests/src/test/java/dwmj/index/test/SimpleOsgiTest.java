/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.index.test;

import org.junit.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;

import dwmj.index.IndexService;

public class SimpleOsgiTest extends AbstractConfigurableBundleCreatorTests {

   @Autowired
   IndexService indexService;
   
   @Override
   protected String[] getTestBundlesNames() {
      return new String[] {
        "com.dudewheresmyjar.dude, org.compass-project.compass, 2.1.1-SNAPSHOT",
        "com.dudewheresmyjar, domain, 1.0.0-SNAPSHOT",
        "com.dudewheresmyjar, index, 1.0.0-SNAPSHOT",
        "org.eclipse.equinox, cm, 3.2.0-v20070116",
        "org.eclipse.osgi, services, 3.1.200.v20070605",
        "org.springframework, spring-tx, 2.5.6",
    "org.ops4j.pax.logging, pax-logging-service, 1.3.0",
    "org.ops4j.pax.logging, pax-logging-api, 1.3.0",
        "junit, junit, 4.4"
        };
  }   
   

   
   public void testOsgiPlatformStarts() throws Exception {
      System.out.println(bundleContext.getProperty(Constants.FRAMEWORK_VENDOR));
      System.out
            .println(bundleContext.getProperty(Constants.FRAMEWORK_VERSION));
      System.out.println(bundleContext
            .getProperty(Constants.FRAMEWORK_EXECUTIONENVIRONMENT));
      
      System.out.println("=========================================");
      Bundle[] bundles = bundleContext.getBundles();
      for (Bundle bundle : bundles) {
         System.out.println(bundle.getSymbolicName());
      }
      System.out.println("=========================================");
      
      Assert.assertNotNull(indexService);
   }
}
