/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.dudewheresmyjar.index;

import java.util.List;

import org.ops4j.pax.drone.api.ConnectorConfiguration;
import org.ops4j.pax.drone.connector.paxrunner.ConnectorConfigurationFactory;
import org.ops4j.pax.drone.connector.paxrunner.Platforms;
import org.ops4j.pax.drone.spi.junit.DroneTestCase;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.dudewheresmyjar.domain.JarFile;

public class IndexServiceBundleTest extends DroneTestCase {

   @Override
   protected ConnectorConfiguration configure() {
      return ConnectorConfigurationFactory
                        .create(this)
                        .setPlatform(Platforms.FELIX)
                        .addBundle("mvn:com.dudewheresmyjar/domain")
                        .addBundle("mvn:com.dudewheresmyjar/index")
                        .addBundle(
                                          "mvn:com.dudewheresmyjar.dude/org.compass-project.compass")
                        .addBundle("mvn:org.ops4j.pax.logging/pax-logging-api")
                        .addBundle(
                                          "mvn:org.ops4j.pax.logging/pax-logging-service");
   }

   public void testSomething() {
      BundleContext bundleContext = droneContext.getBundleContext();

      ServiceReference ref = bundleContext
                        .getServiceReference(IndexService.class.getName());
      assertNotNull("Service Reference is null", ref);
      try {
         IndexService indexService = (IndexService) bundleContext
                           .getService(ref);
         assertNotNull("Cannot find the service", indexService);

         JarFile jarFile = new JarFile();
         jarFile.setGroupId("com.habuma.foo");
         jarFile.setArtifactId("bar");
         jarFile.setVersion("1.2.3");
         jarFile.setRepository("http://www.habuma.com/maven2");
         indexService.addJarFile(jarFile);

         List<JarFile> found = indexService.findJarFiles("bar");
         assertEquals(1, found.size());
      }
      finally {
         bundleContext.ungetService(ref);
      }
   }
}
