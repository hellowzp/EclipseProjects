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

<%@page import="dwmj.domain.JarFile"%>
<html>
  <head><title>Dude, Where's My JAR?</title></head>
  
  <body>
    <form method="POST" action="search.htm">
      <input type="text" name="searchString"/>&nbsp;
          <input type="submit" value="Search"/>
    </form>

    <c:choose>
        <c:when test='${empty jarFileList}'>
          <h2>Where's your JAR, dude?</h2>
          <p>I couldn't find the JARs you were asking for.</p>
        </c:when>
        <c:otherwise>
    <h2>Here's your JAR(s), dude!</h2>
    <p>I found these JAR files:</p>

    <table border="1">
      <thead>
        <tr>
          <td><b>Repository</b></td>
          <td><b>Group</b></td>
          <td><b>Artifact</b></td>
          <td><b>Version</b></td>
          <td><b>Snapshot?</b></td>
          <td><b>Source?</b></td>
          <td><b>Javadoc?</b></td>
          <td><b>Symbolic Name</b></td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>

      <c:forEach items="${jarFileList}" var="jarFile">
        <tr>
          <td><c:out value="${jarFile.repository}" /></td>
          <td><c:out value="${jarFile.groupId}" /></td>
          <td><c:out value="${jarFile.artifactId}" /></td>
          <td><c:out value="${jarFile.version}" /></td>
          <td><c:out value="${jarFile.snapshot}" /></td>
          <td><c:out value="${jarFile.hasSource}" /></td>
          <td><c:out value="${jarFile.hasJavadoc}" /></td>
          <td><c:out value="${jarFile.bundleSymbolicName}" /></td>
          <td><a href="<c:out value="${jarFile.rawUrl}" />">Download</a></td>
        </tr>
      </c:forEach>

      </tbody>
    </table>
        </c:otherwise>
    </c:choose>
  </body>
</html>
