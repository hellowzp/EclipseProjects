/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.dudewheresmyjar.index;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dudewheresmyjar.domain.JarFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/index-context.xml" })
public class IndexServiceImplTest {
   @Autowired
   IndexService indexService;

   @Test
   public void shouldAddItemToIndex() throws Exception {
      List<JarFile> found = indexService.findJarFiles("artifactId:spring");
      assertEquals(0, found.size());

      JarFile jarFile = new JarFile();
      jarFile.setGroupId("org.springframework");
      jarFile.setArtifactId("spring");
      jarFile.setVersion("2.5.4");

      indexService.addJarFile(jarFile);
      found = indexService.findJarFiles("artifactId:spring");
      assertEquals(1, found.size());
   }
}
