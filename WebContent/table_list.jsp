<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
 * Copyright 2013 Xiaxing Shi.
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
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="css/basic.css" />
<link rel="stylesheet" type="text/css" href="css/cards.css" />
<link rel="stylesheet" type="text/css" href="css/table_list.css" />
<%@page 
import='java.util.ArrayList'
import='java.util.HashMap'
import='com.happyuno.model.GameTable'
import='com.happyuno.entity.User'
%>
<%
HashMap<Integer, GameTable> gameTableMap=(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");

%>
</head>
<body>
	<div>
		<ul>
<% if (gameTableMap != null){
	int tatoNum = Integer.parseInt(application.getAttribute("tatoNum").toString());
	for (int i=1; i <= tatoNum ;i++){
		GameTable gt = gameTableMap.get(i);
		int curNum = gt.getUserList().size();
	%>
			<li data-tableid='<%=gt.getTableId() %>'>
			<form class='table_form' method='post' action='/happyuno/JoinGameTable' >
				<h3>
					<span class='table_max_num'><%=gt.getDefiNum() %></span>人局
				</h3>
				<p class='table_waiting'>
					人数：<span class='person_num'><%=curNum %></span>/<span class='table_max_num'><%=gt.getDefiNum() %></span>
				</p>
				<input type='hidden' name='tableId' value='<%=gt.getTableId() %>' />  
			</form>
			</li>
<%		}
	} %>

		</ul>
	</div>
	<div id='table_creater'>
	<form class="tableCreateForm" action="/happyuno/CreateGameTable"
		method="post" id="tableCreateForm">
			<input type="hidden" name="userId" id="userId"  value="" />
			<lable>牌桌人数：
			<select name="defiNum" id="defiNum">
				<option value="2">2人局</option>
				<option value="3">3人局</option>
				<option value="4">4人局</option>
				<option value="5">5人局</option>
				<option value="6">6人局</option>
			</select>
			</lable>
			<input class="button" type="submit" value="新建牌桌" tabindex="3">
	</form>
	</div>
	
<script src="js/jquery-1.10.2.min.js"></script>
<script>
$(document).ready(function(){
	$('.table_form').submit(function(){
		console.log('click submit');
	});
	$('ul').on('click','li',function(){
		$(this).find('form').submit();
	});
	$('li').each(function(){
		var $li = $(this);
		var cardId = $li.data('tableid') * 2 % 108;
		console.log(cardId);
		getCard(cardId, function(card){
			$li.children('form').prepend(card);
			console.log(card);
		});
	});
});

function getCard(id, callback) {
	var card;
	$.get('cards/all_cards.html', function(data) {
		console.log('done get card');
		card = $(data).filter('#card_' + id);
		callback(card);
	});
};
</script>
</body>
</html>