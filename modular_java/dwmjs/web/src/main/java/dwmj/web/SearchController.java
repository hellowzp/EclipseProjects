/***
 * Excerpted from "Modular Java",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
***/
package dwmj.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dwmj.domain.JarFile;
import dwmj.index.IndexService;

@Controller
@RequestMapping("/search.htm")
public class SearchController {

   @RequestMapping(method = RequestMethod.GET)
   public String showSearchForm(String searchString, ModelMap model) {
      if(searchString != null) return doSearch(searchString, model);
	  return "searchForm";
   }


   @RequestMapping(method = RequestMethod.POST)
   public String doSearch(String searchString, ModelMap model) {
      
	  List<JarFile> matches = indexService.findJarFiles(searchString);
	  model.addAttribute(matches);
      return "searchResults";
   }

   @Autowired
   IndexService indexService;
}
