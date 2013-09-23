<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
String curTable = session.getAttribute("tableId").toString();
int tableId=Integer.parseInt(curTable);
%>
</head>
<body>
<form method = post action="/happyuno/StartGame">
<input type='hidden' name='tableId' value='<%= tableId %>' />
<input type=submit value="开始游戏"></form>
<form method =post action="/happyuno/QuitGameTable">
<input type='hidden' name='tableId' value='<%= tableId %>' />
<input type=submit value="退出房间"></form>
</body>
</html>