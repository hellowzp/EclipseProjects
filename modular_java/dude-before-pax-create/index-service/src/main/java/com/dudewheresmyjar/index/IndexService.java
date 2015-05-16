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

import com.dudewheresmyjar.domain.JarFile;

public interface IndexService {
   void addJarFile(JarFile jarFile);

   List<JarFile> findJarFiles(String searchString);
}
