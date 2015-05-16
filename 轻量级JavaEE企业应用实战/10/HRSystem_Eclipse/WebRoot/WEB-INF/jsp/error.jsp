<%@ page contentType="text/html; charset=gb2312" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>出错提示页</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
</head>
<body>
<%@include file="header.jsp"%> 
<table width=780 align="center"
	background="<%=ctx%>/images/bodybg.jpg">
  <tr> 
  <td height="39"><br>
  <div align="center"><font color="#FF0000" size="+1"><b>系统处理过程中发生了一个错误，信息如下：</b></font></div>
  </td>
  </tr>
  <tr>
    <td height="315">
<a href="http//www.leegang.org">
<s:property value="exception.message"/>
</a>
</td>
  </tr>
  <tr>
    <td height="315" valign=top><div align="center" class="error"><%=exception%></div></td>
  </tr>
  <tr>
    <td><div align="center"><font size="0" color="#333333">请您先核对输入，如果再次出现该错误，请登录<a href="http//www.leegang.org">http//www.leegang.org</a>寻求答案，谢谢。</font></div><br></td>
  </tr>
</table>
<%@include file="footer.jsp"%> 
</body>
</html>

