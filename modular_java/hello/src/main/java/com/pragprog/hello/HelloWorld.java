/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.pragprog.hello;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorld implements BundleActivator {
  public void start(BundleContext ctx) throws Exception {
    System.out.println("Hello World!");
  }
  
  public void stop(BundleContext ctx) throws Exception {
    System.out.println("Goodbye World!");
  }
}
