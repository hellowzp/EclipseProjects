/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.dudewheresmyjar.index.impl;

import java.util.ArrayList;
import java.util.List;

import org.compass.core.CompassHit;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;
import org.compass.spring.CompassDaoSupport;

import com.dudewheresmyjar.domain.JarFile;
import com.dudewheresmyjar.index.IndexService;

public class IndexServiceImpl extends CompassDaoSupport implements IndexService {

   public void addJarFile(JarFile jarFile) {
      getCompassTemplate().save(jarFile);
   }

   public List<JarFile> findJarFiles(String searchString) {
  	  System.out.println("================================================");
 	  System.out.println("================================================");
	  System.out.println("================================================");
	  System.out.println("SEARCHING FOR JARS...SEARCH STRING:  " + searchString);
	  System.out.println("================================================");
	  System.out.println("================================================");
	  System.out.println("================================================");
      CompassSession session = getCompass().openSession();
      CompassTransaction transaction = session.beginTransaction();
      CompassHits hits = getCompassTemplate().find(searchString);

      List<JarFile> jarFiles = new ArrayList<JarFile>(hits.getLength());
      for (CompassHit hit : hits) {
         jarFiles.add((JarFile) hit.getData());
      }

      transaction.commit();
      session.close();
      return jarFiles;
   }
}
