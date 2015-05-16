/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.dudewheresmyjar.web;

import com.dudewheresmyjar.index.IndexService;

public class Kicker {
   public void kickIt() {
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("KICKING IT!");
      indexService.findJarFiles("FIND ME A JAR");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
      System.out.println("* * * * * * * * * * * * * * * * * * * * * *");
   }

   private IndexService indexService;

   public void setIndexService(IndexService indexService) {
      this.indexService = indexService;
   }
}
