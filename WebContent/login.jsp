<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 * Copyright 2013 Xiaoqiang Xv.
 *
 * This file is part of WebBaseCardGame.
 *
 * WebBaseCardGame is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * WebBaseCardGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WebBaseCardGame.
 * 
 * If not, see <http://www.gnu.org/licenses/>.
 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录页面</title>
<link rel="stylesheet" type="text/css" href="css/basic.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
</head>
<body>
<div id='forms' >
<div id='login_form' >
<form  method ="post" action="/happyuno/HandleLogin">
用户名<input type=text name=id>
&nbsp;&nbsp;密码<input type=password name=password>
<input class='form_button' type=submit value="登录">
</form>
</div>
<div id='other_form' >
<form id='register_form' action="register.jsp">
没有账号立即注册<input class='form_button' type=submit value="注册"></form>
<form id='forget_form' action="register.jsp">
<input class='form_button' type=submit value="忘记密码"></form>
</div>
</div>
</body>
</html>