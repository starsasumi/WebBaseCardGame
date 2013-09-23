<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'register.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
用户注册
<br>
<br>
<form action="/happyuno/HandleRegist" method="post">
用户账号<input type=text name=id>
<br>
<br>
用户昵称 <input type=text name=name>
<br>
<br>
密 码设置 <input type=password name=password>
<br>
<br>
密 码重复 <input type=password name=password2>
<br>
<br>
注册邮箱 <input type=text name=admail>
<br>
<br>
验证码<input type=text name=key>
<br>
<br>
<input type=submit value="注册"></form>
</html>
