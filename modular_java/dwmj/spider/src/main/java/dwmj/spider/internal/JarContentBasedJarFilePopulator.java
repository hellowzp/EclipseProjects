/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.spider.internal;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.Manifest;

import dwmj.domain.JarFile;

public class JarContentBasedJarFilePopulator implements JarFilePopulator {

   public void populate(JarFile jarFile, String jarUrlSpec) {
      try {
         URL jarUrl = new URL("jar:" + jarUrlSpec + "!/");
         JarURLConnection conn = (JarURLConnection) jarUrl.openConnection();
         java.util.jar.JarFile realJarFile = conn.getJarFile();
         populateFromManifestEntries(realJarFile.getManifest(), jarFile);
         populateFromEntries(realJarFile.entries(), jarFile);
      }
      catch (Exception e) {}
      finally {

      }
   }

   private void populateFromEntries(Enumeration<JarEntry> entries,
               JarFile jarFile) {

      Set<String> packageNames = new HashSet<String>();
      Set<String> classNames = new HashSet<String>();

      while (entries.hasMoreElements()) {
         JarEntry entry = entries.nextElement();
         String fileName = entry.getName();

         // we only care about real classes...not inner classes
         if (fileName.endsWith(".class") && !fileName.contains("$")) {
            packageNames.add(extractPackageFromFileName(fileName));
            classNames.add(fileName.replace(".class", "").replace('/', '.'));
         }
      }
      jarFile.setPackages(packageNames);
      jarFile.setClasses(classNames);
   }

   private void populateFromManifestEntries(Manifest manifest, JarFile jarFile) {
      Attributes attributes = manifest.getMainAttributes();
      jarFile.setBundleSymbolicName(attributes.getValue("Bundle-SymbolicName"));
   }

   private String extractPackageFromFileName(String fileName) {
      return fileName.substring(0, fileName.lastIndexOf("/")).replace('/', '.');
   }
}
