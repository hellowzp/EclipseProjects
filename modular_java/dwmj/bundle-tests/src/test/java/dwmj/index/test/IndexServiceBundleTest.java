/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/

package dwmj.index.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.equinox;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.provision;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import dwmj.domain.JarFile;
import dwmj.index.IndexService;

@RunWith(JUnit4TestRunner.class)
public class IndexServiceBundleTest {
   @Inject
   private BundleContext bundleContext;
// ...

   

   @Configuration
   public static Option[] configuration()
   {
      return options(equinox(), provision(
          mavenBundle().groupId("org.ops4j.pax.logging").
                artifactId("pax-logging-service"),
          mavenBundle().groupId("org.ops4j.pax.logging").artifactId("pax-logging-api"),
          mavenBundle().groupId("com.dudewheresmyjar").artifactId("domain"),
          mavenBundle().groupId("com.dudewheresmyjar").artifactId("index"),
          mavenBundle().groupId("com.dudewheresmyjar.dude").
                artifactId("org.compass-project.compass").version("2.1.1-SNAPSHOT")
          ));
   }   


/*

   @Configuration
   public static Option[] configuration()
   {
      return options(equinox(), felix(), knopflerfish(), provision(
         ...
          ));
   }   

*/


   @Test
   public void bundleContextShouldNotBeNull() {
      assertNotNull(bundleContext);
   }   



    @Test
    public void shouldIndexAndFindAJarFileObject() throws Exception {
       IndexService indexService = retrieveIndexService();
             
       JarFile jarFile = new JarFile();
       jarFile.setRepository("http://repo1.maven.org/maven2");
       jarFile.setGroupId("com.dudewheresmyjar");
       jarFile.setArtifactId("domain");
       jarFile.setVersion("1.0.0");
       jarFile.setRawUrl(
             "http://repo1.maven.org/maven2/com/dudewheresmyjar/domain/1.0.0");
    
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



   private IndexService retrieveIndexService() throws InterruptedException {
      ServiceTracker tracker = new ServiceTracker(bundleContext, 
            IndexService.class.getName(), null);
      tracker.open();
      IndexService indexService = (IndexService) tracker.waitForService(5000);
      tracker.close();
      assertNotNull(indexService);
      return indexService;
   }


}

