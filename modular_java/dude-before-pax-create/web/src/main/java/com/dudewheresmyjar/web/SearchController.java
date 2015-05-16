/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package com.dudewheresmyjar.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.dudewheresmyjar.index.IndexService;

@Controller
@RequestMapping("/search.htm")
public class SearchController extends AbstractController {

   @Override
   protected ModelAndView handleRequestInternal(HttpServletRequest request,
                     HttpServletResponse response) throws Exception {

      String searchString = request.getParameter("searchString");

      indexService.findJarFiles(searchString);
      return new ModelAndView("searchResults");
   }

   // injected -- consider autowiring later
   @Autowired
   private IndexService indexService;

   public void setIndexService(IndexService indexService) {
      this.indexService = indexService;
   }
}
