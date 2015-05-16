<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>电子打卡</title>
</head>
<body>
<%@include file="../header.jsp"%> 
<%@include file="empheader.jsp"%> 
<table width=780 align="center"
	background="<%=ctx%>/images/bodybg.jpg">
  <tr>
    <td colspan="3"><br><br><div class="mytitle">电子打卡系统</div></td>
  </tr>
  <tr>
    <td colspan="3">
	    <br><br><br>
<!-- 当punchIsValid为1、3时，可上班打卡 -->
<s:if test="punchIsValid==1 
	|| punchIsValid==3">
<s:form action="employeeCome">
	<s:submit key="come.punch"/>
</s:form>
</s:if>
		<br><br><br>
	</td>
  </tr>
  <tr>
    <td colspan="3">
<!-- 当punchIsValid为2、3时，可下班打卡 -->	
<s:if test="punchIsValid==2
	|| punchIsValid==3">
<s:form action="employeeLeave">
	<s:hidden value="leave"/>
	<s:submit  key="leave.punch"/>
</s:form>
</s:if>
		<br>
	</td>
  </tr>
</TABLE>
<%@include file="../footer.jsp"%> 
</body>
</html>
