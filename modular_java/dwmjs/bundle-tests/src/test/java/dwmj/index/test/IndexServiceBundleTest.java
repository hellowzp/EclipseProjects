/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.index.test;

import org.junit.Test;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;

public class IndexServiceBundleTest extends AbstractConfigurableBundleCreatorTests {
   
   @Test
   public void testNothing() {}
   
//   @Autowired
//   private IndexService indexService;
//
//   @Override
//   protected Manifest getManifest() {
//      Manifest mf = super.getManifest();
//      System.out.println("=======================");
//      System.out.println(mf.getAttributes(Constants.BUNDLE_SYMBOLICNAME));
//      System.out.println("=======================");
//      return mf;
//}
//   
//   @Override
//   protected String[] getTestBundlesNames() {
//      return new String[] {
//        "org.springframework.osgi,spring-osgi-extender, 1.2.0-m2",
//        "org.springframework.osgi,spring-osgi-core, 1.2.0-m2",
//        "org.springframework.osgi, spring-osgi-io, 1.2.0-m2",
//        "com.dudewheresmyjar.dude, org.compass-project.compass, 2.1.1-SNAPSHOT",
//        "com.dudewheresmyjar, domain, 1.0.0-SNAPSHOT",
//        "com.dudewheresmyjar, index, 1.0.0-SNAPSHOT",
////        "org.ops4j.pax.logging, pax-logging-service, 1.3.0",
////        "org.ops4j.pax.logging, pax-logging-api, 1.3.0"
////        "org.springframework, spring-aop, 2.5.6",
////        "org.springframework, spring-beans, 2.5.6",
////        "org.springframework, spring-context, 2.5.6",
////        "org.springframework, spring-core, 2.5.6",
////        "org.springframework, spring-tx, 2.5.6",
////        "org.aopalliance, com.springsource.org.aopalliance, 1.0.0",
////        "org.springframework.osgi, cglib-nodep.osgi, 2.1.3-SNAPSHOT"
//        };
//  }   
//   
//   
//   
//   public void testIndexServiceExists() {
//      assertNotNull(indexService);
////      assertTrue(true);
//   }
//
////   public void testIndexService() throws Exception {
////      ServiceTracker tracker = new ServiceTracker(bundleContext, IndexService.class.getName(), null);
////      tracker.open();
////      IndexService indexService = (IndexService) tracker.waitForService(10000);
////      tracker.close();
////
////      assertNotNull(indexService);
////
////      JarFile jarFile = new JarFile();
////      jarFile.setRepository("http://repo1.maven.org/maven2");
////      jarFile.setGroupId("com.dudewheresmyjar");
////      jarFile.setArtifactId("domain");
////      jarFile.setVersion("1.0.0");
////      indexService.addJarFile(jarFile);
////
////      List<JarFile> foundJarFiles = indexService.findJarFiles("1.0.0");
////      assertEquals(1, foundJarFiles.size());
////
////      JarFile foundJarFile = foundJarFiles.get(0);
////      assertEquals(jarFile.getRepository(), foundJarFile.getRepository());
////      assertEquals(jarFile.getGroupId(), foundJarFile.getGroupId());
////      assertEquals(jarFile.getArtifactId(), foundJarFile.getArtifactId());
////      assertEquals(jarFile.getVersion(), foundJarFile.getVersion());
////
////      indexService.removeJarFile(foundJarFile);
////      foundJarFiles = indexService.findJarFiles("domain");
////      assertEquals(0, foundJarFiles.size());
////   }
}

