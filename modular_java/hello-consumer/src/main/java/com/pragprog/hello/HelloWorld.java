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
import org.osgi.framework.ServiceReference;

import com.pragprog.hello.service.HelloService;

public class HelloWorld implements BundleActivator {

   public void start(BundleContext context) throws Exception {
      HelloService helloService = getHelloService(context);
      System.out.println(helloService.getHelloMessage());
   }

   public void stop(BundleContext context) throws Exception {
      HelloService helloService = getHelloService(context);
      System.out.println(helloService.getGoodbyeMessage());
   }

   private HelloService getHelloService(BundleContext context) {
      ServiceReference ref = context.getServiceReference(HelloService.class
                        .getName());

      HelloService helloService = (HelloService) context.getService(ref);
      return helloService;
   }
}
