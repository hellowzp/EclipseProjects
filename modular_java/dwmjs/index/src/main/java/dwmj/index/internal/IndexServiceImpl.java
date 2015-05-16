/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.index.internal;

import java.util.ArrayList;
import java.util.List;

import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;

import dwmj.domain.JarFile;
import dwmj.index.IndexService;

public class IndexServiceImpl implements IndexService {
   private final Compass compass;

   public IndexServiceImpl(Compass compass) {
      this.compass = compass;
   }

   public void addJarFile(JarFile jarFile) {
      CompassSession session = null;
      CompassTransaction transaction = null;
      try {
         session = compass.openSession();
         transaction = session.beginTransaction();
         
         session.create(jarFile);
         transaction.commit();
      }
      catch (CompassException e) {
         if (transaction != null) {
            transaction.rollback();
         }
      }
      finally {
         if (session != null) {
            session.close();
         }
      }
   }

   public List<JarFile> findJarFiles(String searchString) {
      CompassSession session = compass.openSession();
      CompassTransaction transaction = session.beginTransaction();
      CompassHits hits = session.find(searchString);

      List<JarFile> jarFiles = new ArrayList<JarFile>(hits.getLength());
      for (CompassHit hit : hits) {
         jarFiles.add((JarFile) hit.getData());
      }

      transaction.commit();
      session.close();
      return jarFiles;
   }

   public void removeJarFile(JarFile jarFile) {
      CompassSession session = null;
      CompassTransaction transaction = null;
      try {
         session = compass.openSession();
         transaction = session.beginTransaction();
         session.delete(jarFile);
         transaction.commit();
      }
      catch (CompassException e) {
         if (transaction != null) {
            transaction.rollback();
         }
      }
      finally {
         if (session != null) {
            session.close();
         }
      }
   }
}