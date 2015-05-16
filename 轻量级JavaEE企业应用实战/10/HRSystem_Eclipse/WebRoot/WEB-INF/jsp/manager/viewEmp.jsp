<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
   <title>查看本部门全部员工</title>
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="mgrheader.jsp"%>
<table width=780 align="center"
	background="<%=ctx%>/images/bodybg.jpg">
<tr>
<td>
<br>
<table width=80% border=0 align="center" cellpadding=0 cellspacing="1" bgcolor="#CCCCCC">
  <tr bgcolor="#e1e1e1" >
    <td colspan="3" ><div class="mytitle">您正在查看部门的全部员工</div></td> 
  </tr>
  <tr class="pt9" height="30">
    <td bgcolor="#FFFFFF"><b>员工名</b></td>
    <td bgcolor="#FFFFFF"><b>密码</b></td>
    <td bgcolor="#FFFFFF"><b>工资</b></td>
  </tr>
<s:iterator value="emps" status="index">  
 	<s:if test="#index.odd == true"> 
         <tr style="background-color:#cccccc" class="pt9" height="24">
    </s:if> 
    <s:else> 
         <tr class="pt9" height="24">
    </s:else>
    <td bgcolor="#FFFFFF"><s:property value="empName"/></td>
    <td bgcolor="#FFFFFF"><s:property value="empPass"/></td>
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