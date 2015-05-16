<!--
 ! Excerpted from "Modular Java",
 ! published by The Pragmatic Bookshelf.
 ! Copyrights apply to this code. It may not be used to create training material, 
 ! courses, books, articles, and the like. Contact us if you are in doubt.
 ! We make no guarantees that this code is fit for any purpose. 
 ! Visit http://www.pragmaticprogrammer.com/titles/cwosg for more book information.
-->

<%@ page info="Search results page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<%@page import="dwmj.domain.JarFile"%>
<html>
  <head>
      <title>Dude, Where's My JAR?</title>
      <link href="css/dude.css" rel="stylesheet" type="text/css">
      <script src="jquery/jquery.js"></script>
      <script>
      $(document).ready(function(){
      
          $('.summaryRow').click(function() { 
              $(this).siblings().slideToggle("slow");
          });
          
          $('.downloadLink').click(function() {});
      });
      </script>
  </head>
  
  <body>
    <form method="POST" action="search.htm">
      <a href="search.htm"><img src="images/dwmj-logo-50.png" 
          align="middle" border="0"/></a>
      <input type="text" name="searchString" size="55" 
          value="${param.searchString}" />&nbsp;
      <input type="submit" value="    Find My JAR    " />
    </form>

    <div class="resultsHeader">
      Dude, I found <c:out value="${fn:length(jarFileList)}" /> JARs
    </div>
    <c:choose>
      <c:when test='${empty jarFileList}'>
        <div style="text-align:center;">
          <h2>Where's your JAR, dude?</h2>
          <p>I couldn't find any JARs that match "<c:out 
              value="${param.searchString}" />".</p>
          <p>Try searching again with new search criteria.</p>
        </div>
      </c:when>
      <c:otherwise>    
        <br/>
        <div class="resultsList">
          <c:forEach items="${jarFileList}" var="jarFile">
<!-- ... -->              

            <div class="entry firstEntry">
              <table class="summaryRow">
                <tr>
                  <td width="50%"><c:out value="${jarFile.groupId}" />/<c:out 
                      value="${jarFile.artifactId}" />/<c:out 
                      value="${jarFile.version}" /></td>
                  <td width="50%" align="right"><a href="<c:out 
                      value="${jarFile.rawUrl}" />" class="downloadLink">
                      Download</a></td>
                </tr>
              </table>
              <div class="detailsRow">
              <table style="font-size:small; width:100%;">
                <tr>
                    <td width="60%">
                      <table width="100%" style="font-size:small;">
                        <tr>
                          <td width="25%" class="label">Repository:</td>
                          <td colspan="3"><c:out value="${jarFile.repository}" />
                        </tr>
                        <tr>
                          <td width="25%" class="label">OSGi Bundle?</td>
                          <td width="5%">${empty jarFile.bundleSymbolicName ? 
                              "No" : "Yes"}</td>
                          <td width="35%" class="label">Bundle-SymbolicName:</td>
                          <td width="35%"><c:out value=
                              "${empty jarFile.bundleSymbolicName ? 
                              'N/A' : jarFile.bundleSymbolicName}" /></td>
                        </tr>
                        <tr>
                          <td width="25%" class="label">Has source?</td>
                          <td width="5%"><c:out value='${jarFile.hasSource ? 
                              "Yes" : "No"}' /></td>
                          <td width="35%" class="label">Has JavaDoc?</td>
                          <td width="35%"><c:out value='${jarFile.hasJavadoc ? 
                              "Yes" : "No"}' /></td>
                        </tr>
                      </table>
                    </td>
                    <td width="40%" style="border-left:1px dotted #999999;
                        padding-left:10px;">
                      <span style="font-weight:bold;">Maven:</span>
                      <div><pre>
&lt;dependency&gt;
  &lt;groupId&gt;<c:out value="${jarFile.groupId}" />&lt;/groupId&gt;
  &lt;artifactId&gt;<c:out value="${jarFile.artifactId}" />&lt;/artifactId&gt;
  &lt;version&gt;<c:out value="${jarFile.version}" />&lt;/version&gt;
&lt;/dependency&gt;
                      </pre></div>
                      <span style="font-weight:bold;">Ivy:</span>
                      <div><pre>
&lt;dependency org="<c:out value="${jarFile.groupId}" />" 
            name="<c:out value="${jarFile.artifactId}" />" 
            rev="<c:out value="${jarFile.version}" />" /&gt;
                      </pre></div>
                      
                    </td>
                </tr>                  
              </table>
              </div>
            </div>

          </c:forEach>
        </div>    
      </c:otherwise>
    </c:choose>
  </body>
</html>

