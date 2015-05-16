/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.dudewheresmyjar.spider;

import com.dudewheresmyjar.domain.JarFile;
import com.dudewheresmyjar.index.IndexService;

public class MavenRepositorySpider implements Runnable {
   private boolean keepSpidering = true;
   private Thread thread;

   public void startSpidering() throws Exception {
      thread = new Thread(this);
      thread.start();
   }

   public void stopSpidering() {
      keepSpidering = false;
   }

   private IndexService indexService;

   public void setIndexService(IndexService indexService) {
      this.indexService = indexService;
   }

   public void run() {
      while (keepSpidering) {
         try {
            indexService.addJarFile(new JarFile());

            Thread.sleep(2000);
         }
         catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
}
