<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
   <title>员工查看自己的工资</title>
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="empheader.jsp"%>
<table width=780 align="center"
	background="<%=ctx%>/images/bodybg.jpg">
<tr>
<td>
<br>
<table width=80% border=0 align="center" cellpadding=0 cellspacing="1" bgcolor="#CCCCCC">
  <tr bgcolor="#e1e1e1" >
    <td colspan="5" ><div class="mytitle">当前用户：<s:property value="#session.user"/></div></td> 
  </tr>
  <tr class="pt9" height="30">
    <td bgcolor="#FFFFFF"><b>发薪月份</b></td>
    <td bgcolor="#FFFFFF"><b>薪水</b></td>
  </tr>
<s:iterator value="salarys" status="index">  
 	<s:if test="#index.odd == true"> 
         <tr style="background-color:#cccccc" class="pt9" height="24">
    </s:if> 
    <s:else> 
         <tr class="pt9" height="24">
    </s:else>
    <td bgcolor="#FFFFFF"><s:property value="payMonth"/></td>
    <td bgcolor="#FFFFFF"><s:property value="amount"/></td>
</tr>
</s:iterator>
</table>
</td>
</tr>
</table>
<%@include file="../footer.jsp"%>
</body>
</html>