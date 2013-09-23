<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>

<body>

<div class="main">
	<form class="createTable" action="/happyuno/CreateGameTable"
		method="post" id="createTableForm">
		<div>
			用户ID
			<input class="text" type="text" name="userId" id="userId"  tabindex="1" />
			<span id="userIdHint"></span>
		</div>
		<input class="button" type="submit" value="新建牌桌" tabindex="3">
		<span id="submitHint"></span>
	</form>
	
</div>
</body>
</html>