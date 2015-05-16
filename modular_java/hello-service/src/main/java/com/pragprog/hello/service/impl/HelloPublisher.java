/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.pragprog.hello.service.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.pragprog.hello.service.HelloService;

public class HelloPublisher implements BundleActivator {
   private ServiceRegistration registration;

   public void start(BundleContext context) throws Exception {
      registration = context.registerService(HelloService.class.getName(),
                        new HelloImpl(), null);
   }

   public void stop(BundleContext context) throws Exception {
      registration.unregister();
   }
}
