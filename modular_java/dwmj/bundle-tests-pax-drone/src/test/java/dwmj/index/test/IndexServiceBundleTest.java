/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/

package dwmj.index.test;

import static org.ops4j.pax.drone.connector.paxrunner.GenericConnector.create;
import static org.ops4j.pax.drone.connector.paxrunner.GenericConnector.createBundleProvision;
import static org.ops4j.pax.drone.connector.paxrunner.GenericConnector.createRunnerContext;

import java.util.List;

import org.ops4j.pax.drone.api.DroneConnector;
import org.ops4j.pax.drone.connector.paxrunner.Platforms;
import org.ops4j.pax.drone.spi.junit.DroneTestCase;
import org.osgi.util.tracker.ServiceTracker;

import dwmj.domain.JarFile;
import dwmj.index.IndexService;

public class IndexServiceBundleTest extends DroneTestCase {

   @Override
   protected DroneConnector configure() {
      return create(createRunnerContext(), createBundleProvision()
                 .addBundle("mvn:org.ops4j.pax.logging/pax-logging-service")
                 .addBundle("mvn:org.ops4j.pax.logging/pax-logging-api")
                 .addBundle("mvn:com.dudewheresmyjar/domain")
                 .addBundle("mvn:com.dudewheresmyjar/index")
                 .addBundle("mvn:com.dudewheresmyjar.dude/org.compass-project.compass/2.1.1-SNAPSHOT")
                 ).setPlatform(Platforms.EQUINOX);
   }

   

   
   public void testBundleContextExists() {
      assertNotNull(bundleContext);
   }

   

   
   public void testIndexService() throws Exception {
      ServiceTracker tracker = new ServiceTracker(bundleContext, IndexService.class.getName(), null);
      tracker.open();
      IndexService indexService = (IndexService) tracker.waitForService(10000);
      tracker.close();

      assertNotNull(indexService);

      JarFile jarFile = new JarFile();
      jarFile.setRepository("http://repo1.maven.org/maven2");
      jarFile.setGroupId("com.dudewheresmyjar");
      jarFile.setArtifactId("domain");
      jarFile.setVersion("1.0.0");
      jarFile.setRawUrl("http://repo1.maven.org/maven2/com/dudewheresmyjar/domain/1.0.0");

      indexService.addJarFile(jarFile);

      List<JarFile> foundJarFiles = indexService.findJarFiles("domain");
      assertEquals(1, foundJarFiles.size());

      JarFile foundJarFile = foundJarFiles.get(0);
      assertEquals(jarFile.getRepository(), foundJarFile.getRepository());
      assertEquals(jarFile.getGroupId(), foundJarFile.getGroupId());
      assertEquals(jarFile.getArtifactId(), foundJarFile.getArtifactId());
      assertEquals(jarFile.getVersion(), foundJarFile.getVersion());

      indexService.removeJarFile(foundJarFile);
      foundJarFiles = indexService.findJarFiles("domain");
      assertEquals(0, foundJarFiles.size());
   }
   

   
}


