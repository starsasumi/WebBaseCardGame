<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method ="post" action="/happyuno/PlayTest">
	<input type='hidden' id='curPlayerId' name='curPlayerId'  value='1' />
	<input type=submit value="测试游戏 1" />
</form>
<form method ="post" action="/happyuno/PlayTest">
	<input type='hidden' id='curPlayerId' name='curPlayerId' value='2' />
	<input type=submit value="测试游戏 2" />
</form>
</body>
</html>