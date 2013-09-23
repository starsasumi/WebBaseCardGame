<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
import="java.util.ArrayList"
import="com.happyuno.model.Player"
import="org.json.*"
%>
<%
int tableId = (Integer) session.getAttribute("tableId");
ArrayList<Player> playerList = (ArrayList<Player>) session.getAttribute("players");
int curPlayerId = (Integer) session.getAttribute("curPlayerId");
int playerNum = 0;
JSONArray playerIds = new JSONArray();
if (playerList !=  null) {
	playerNum = playerList.size();
	System.out.println(playerNum);
	Player tmp = null;
	for (int i=0; i<playerNum; ++i) {
		System.out.println(i);
		tmp = playerList.get(i);
		System.out.println(tmp);
		if (tmp.getId() == curPlayerId) {
			for (int j=0; j<playerNum; ++j) {
				JSONObject playerTmp = new JSONObject();
				playerTmp.put("playerId", tmp.getId());
				playerTmp.put("playerName", tmp.getName());
				playerIds.put(playerTmp);
				i++;
				System.out.println(i % playerNum);
				tmp = playerList.get(i % playerNum);
				System.out.println(tmp);
			}
			System.out.println("================");
			break;
		}
	}
	System.out.println(playerIds.toString());
}
%>
<!DOCTYPE html>
<head>
<link rel="stylesheet" type="text/css" href="css/cards.css" />
<link rel="stylesheet" type="text/css" href="css/table.css" />
<meta charset="utf-8" />
</head>
<body data-tableId='<%= tableId %>' data-playerIds='<%= playerIds %>' data-currentPlayerId='<%= curPlayerId %>>' >
<div id='card_stack'></div>
<div id='used_card' class="inactive">
<div class='card' id='card_init'>
	<div class='card_logo'></div>
	<div class='card_pattern'></div>
</div>
</div>
<div id='player_0' data-playerId=''>
	<div class='cards' id='player_0_cards'></div>
	<button id='say_uno'>UNO!</button>
	<button id='pass'>PASS</button>
</div>
<div id='player_1' class="other_player" data-playerId=''>
</div>
<div id='player_2' class="other_player" data-playerId=''>
</div>
<div id='player_3' class="other_player" data-playerId=''>
</div>
<div id='player_4' class="other_player" data-playerId=''>
</div>
<div id='player_5' class="other_player" data-playerId=''>
</div>
<div id='exit'>X</div>

<div id='uno'><p class='triangle-right'>UNO!!</p><span id='uno_player'></span></div>
<div id='color_panel'>
	<div><p class='inf'>请选择下列四种颜色之一</p></div>
	<div id='color_choose'>
	<div class='red circle' id='red' data-color='0'></div>
	<div class='yellow circle' id='yellow' data-color='1'></div>
	<div class='green circle' id='green' data-color='2'></div>
	<div class='blue circle' id='blue' data-color='3'></div>	
	</div>	
</div>
<div id='warning'>
	<div><p class='inf2'></p></div>
	<div id='warning_container'><div class='warning_icon'><p id='exclamatory'>!</p></div></div>	
</div>
<!-- WARNNING: Javascript Begin -->
<script src="js/jquery-1.10.2.min.js"></script>
<script src="js/jquery.transit.min.js"></script>
<script src="js/table.js"></script>
<script>
</script>
</body>
</html>