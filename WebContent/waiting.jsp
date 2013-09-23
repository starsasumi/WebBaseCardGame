<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css" href="css/basic.css" />
<link rel="stylesheet" type="text/css" href="css/cards.css" />
<link rel="stylesheet" type="text/css" href="css/waiting.css" />
<%@page 
import='java.util.ArrayList'
import='java.util.HashMap'
import='com.happyuno.model.GameTable'
import='com.happyuno.entity.User'
%>
<%
HashMap<Integer, GameTable> gameTableMap=(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
int tableId = Integer.parseInt(session.getAttribute("tableId").toString());
%>
</head>
<body>
	<div>
		<ul>
<% if (gameTableMap != null){
	ArrayList<User> userList = gameTableMap.get(tableId).getUserList();
	int tatoUser = userList.size();
	
	for (int i=0; i < tatoUser ;i++){	
	%>

			<li data-userid='<%= userList.get(i).getId() %>'>
				<h3>
					<span class='user_name'><%= userList.get(i).getName() %></span>
				</h3>
				<p>
					<span class='user_id'><%= userList.get(i).getId() %></span>
				</p>
			</li>
<%		}
	} %>
		</ul>
	</div>

<%
ArrayList<User> userList = gameTableMap.get(tableId).getUserList();
if(userList.size()== gameTableMap.get(tableId).getDefiNum()) {
//	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/StartGame");
//	dispatcher.forward(request, response);
%>
<div id='game_start'>
	<div id='exit'>X</div>
	<div id='game_start_button'><p id='game_start_inf'>Start Game</p></div>
	<form class="game_start_form" action="/happyuno/StartGame" method="post" id="game_start_form">
			<input type="hidden" name="userId" id="userId"  value="" />
			<input type='hidden' name='tableId' value='<%= tableId %>' />
	</form>
	<form class="exit_form" action="" method="post" id="exit_form">
			<input type="hidden" name="userId" id="userId"  value="" />
	</form>
	</div>
<%}%>
<script src="js/jquery-1.10.2.min.js"></script>
<script>
$(document).ready(function(){
	$('li').each(function(){
		var $li = $(this);
		var cardId = $li.data('userid') * 2 % 108;
		console.log(cardId);
		getCard(cardId, function(card){
			$li.prepend(card);
			console.log(card);
		});
	});
	$('#game_start_button').click(function(){
		$('#game_start_form').submit();
	});
	
	$('#exit').click(function(){
		$('#exit_form').submit();
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