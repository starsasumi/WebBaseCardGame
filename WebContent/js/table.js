var playerPosition = new Array(6);
for ( var i = 0; i < 6; ++i) {
	playerPosition[i] = 0;
}

var card_Id = '';
var cardColor = '';
var cardMark = '';
var currentTableId = '';
var currentPlayerId = '';
var currentPlayName = '';

$(document).ready(function() {
	var $window = $(window);
	var $player0 = $('#player_0_cards');
	var $color_panel = $('#color_panel');
	var $warning = $('#warning');

	$window.resize(function() {
		beCenter($player0, $window);
		beCenter($('#uno'), $window);
		beCenter($color_panel, $window);
		beCenter($warning, $window);
	});
	beCenter($warning, $window);
	beCenter($color_panel, $window);
	beCenter($player0, $window);
	beCenter($('#uno'), $window);
	initializeStack();
	initializeOthers();

//	console.log($("body").data("tableid"));
//	console.log($("body").data("playerids"));

	othersShow();

	// 玩家喊 UNO
	$('#say_uno').on('click', function() {
		// var player_name = 'star';
		unoAnimation(currentPlayName);
		log("catch uno");
		log(currentTableId);
		$.post("/happyuno/HandleSayUno", {
			"tableId" : currentTableId,
			"playerId" : currentPlayerId
		});

	});
	// 玩家捉 UNO
	$('.other_player_card').on('click', '.card_cover', function() {
		var player2Id = $(this).closest('.other_player').data('playerid');
		log(player2Id);
		$.post("/happyuno/HandleCatchUno", {
			"tableId" : currentTableId,
			"catcher" : currentPlayerId,
			"target" : player2Id
		}, function(data) {
			switch (data.success) {
			case "true": // 成功
				break;
			case "false": // 不成功
				punishAnimation(2);
				break;
			}
		});
	});

	// 玩家出牌
	$player0.on('click', '.card', function() {
		var card_toplay = $(this);
		var card_Id = card_toplay.data('cardid');

		if (card_toplay.hasClass('wild')) { // 是万能牌或+4牌选色
			$color_panel.fadeIn(400);
			$color_panel.unbind().on('click', '.circle', function() {
				var $selected_color = $(this).data('color');
				

				$color_panel.fadeOut();
				log(card_Id);
				$.post("/happyuno/HandlePlayCard", {
					"tableId" : currentTableId,
					"playerId" : currentPlayerId,
					"cardId" : card_Id
				    }, function(data) {
				    	log(data);
						$.post("/happyuno/HandleWild", {
							"tableId" : currentTableId,
							"playerId" : currentPlayerId,
							"color" : $selected_color
						});
				});

			});
		} else {// 其他普通牌
			$.post("/happyuno/HandlePlayCard", {
				"tableId" : currentTableId,
				"playerId" : currentPlayerId,
				"cardId" : card_Id
			});
		}
	});

	// 玩家主动摸牌
	$('#card_stack').on('click', '#card_stack_button', function() {
		$.post("/happyuno/HandleGetCard", {
			"tableId" : currentTableId,
			"playerId" : currentPlayerId
		});
	});

	// pass
	$('#pass').on('click', function() {
		$.post("/happyuno/HandlePass", {
			"tableId" : currentTableId,
			"playerId" : currentPlayerId
		});
	});

	oTimer = window.setInterval("queryHandle()", 1500);
});

// 惩罚动画
function punishAnimation(i) {
	log('punish')
	switch (i) {
	case 1:
		$('.inf2').text('出牌错误,罚两张牌');
		break;
	case 2:
		$('.inf2').text('抓 UNO');
		break;
	case 3:
		$('.inf2').text('被抓 UNO');
		break;
	case 4:
		$('.inf2').text('现在是红色');
		break;
	case 5:
		$('.inf2').text('现在是黄色');
		break;
	case 6:
		$('.inf2').text('现在是绿色');
		break;
	case 7:
		$('.inf2').text('现在是蓝色');
		break;
	case 8:
		$('.inf2').text('游戏结束，你赢啦');
		break;
	case 9:
		$('.inf2').text('游戏结束，你输啦');
		break;
	}
	$('#warning').fadeIn(300, function() {
		setTimeout(function() {
			$('#warning').fadeOut(300)
		}, '1000');
	});
};
// +1张牌动画
function dealOneCardAnimation(id) {
	dealCardAnimation(function() {
		getCard(id, function(card) {
			card.appendTo($('#player_0_cards')).fadeIn(500);
			beCenter($("#player_0_cards"), $(window));
		});
	});
};
// 直接加牌
function directDealCardAnimation(id) {
	getCard(id, function(card) {
		card.appendTo($('#player_0_cards')).fadeIn(600);
		beCenter($("#player_0_cards"), $(window));
	});
}
// 部分摸牌动画
function dealCardAnimation(callback) {
	$('#back').css({
		"z-index" : "299"
	});
	$('#front').css({
		"z-index" : '199'
	});
	$('#back').transition({
		rotate : '+=60deg',
		x : '+=100'
	}, 600, 'ease');
	$('#front').transition({
		rotate : '+=60deg',
		x : '+=100'
	}, 600, 'ease');
	$('#s4').transition({
		rotate : '+=30deg',
		x : '+=45',
		y : '+=10'
	}, 600, 'ease');
	$('#s3').transition({
		rotate : '-=5deg',
		x : '-=10',
		y : '-=15'
	}, 600, 'ease');
	$('#s2').transition({
		rotate : '-=45deg',
		x : '-=40',
		y : '-=60'
	}, 600, 'ease');
	$('#s1').transition({
		rotate : '-=70deg',
		x : '-=43',
		y : '-=95'
	}, 600, 'ease');
	$('#back').transition({
		rotate : '+=30deg',
		x : '+=50'
	}, 600).css({
		'-webkit-backface-visibility' : 'hidden'
	});
	$('#front').transition({
		rotate : '+=30deg',
		x : '+=50'
	}, 600);
	$('#s1').transition({
		rotate : '+=70deg',
		x : '+=43',
		y : '+=95',
		delay : 500
	}, 500, 'ease');
	$('#s2').transition({
		rotate : '+=45deg',
		x : '+=40',
		y : '+=60',
		delay : 500
	}, 500, 'ease');
	$('#s3').transition({
		rotate : '+=5deg',
		x : '+=10',
		y : '+=15',
		delay : 500
	}, 500, 'ease');
	$('#s4').transition({
		rotate : '-=30deg',
		x : '-=45',
		y : '-=10',
		delay : 500
	}, 500, 'ease');
	$('#back').transition({
		perspective : '200px',
		rotate3d : '1,1,0,180deg'
	}, 300, function() {
		$('#back').remove();
		getCard(108, function(card) {
			card.attr("id", "back").insertBefore('#card_stack_button');
		});
	});
	$('#front').transition({
		perspective : '1000px',
		rotateX : '-180deg'
	}, 1).transition({
		perspective : '200px',
		rotate3d : '1,1,0,180deg'
	}, 500).transition({
		perspective : '200px',
		rotate3d : '1,1,0,180deg'
	}, 500).fadeOut(500, function() {
		$('#front').remove();
		callback();
		$('#card_stack_button').show();
	});
};
// 排版其他玩家显示
function othersShow() {
	currentTableId = $("body").data("tableid");
	var playerids = $("body").data("playerids");
	currentPlayerId = playerids[0].playerId;
	currentPlayerName = playerids[0].playerName;

	playerPosition[0] = currentPlayerId;

	$('#player_0').data('playerids', currentPlayerId);
	switch (playerids.length) {
	case 2:
		$('.other_player').not('#player_3').hide();
		$('#player_3').data('playerid', playerids[1].playerId).find(
				'.player_id').text(playerids[1].playerName);
		playerPosition[3] = playerids[1].playerId;
		break;
	case 3:
		$('.other_player').not('#player_2, #player_4').hide();
		$('#player_2').data('playerid', playerids[1].playerId).find(
				'.player_id').text(playerids[1].playerName);
		playerPosition[2] = playerids[1].playerId;
		$('#player_4').data('playerid', playerids[2].playerId).find(
				'.player_id').text(playerids[2].playerName);
		playerPosition[4] = playerids[2].playerId;
		break;
	case 4:
		$('.other_player').filter('#player_2, #player_4').hide();
		$('#player_1').data('playerid', playerids[1].playerId).find(
				'.player_id').text(playerids[1].playerName);
		playerPosition[1] = playerids[1].playerId;
		$('#player_3').data('playerid', playerids[2].playerId).find(
				'.player_id').text(playerids[2].playerName);
		playerPosition[3] = playerids[2].playerId;
		$('#player_5').data('playerid', playerids[3].playerId).find(
				'.player_id').text(playerids[3].playerName);
		playerPosition[5] = playerids[3].playerId;
		break;
	case 5:
		$('#player_3').hide();
		$('#player_1').data('playerid', playerids[1].playerId).find(
				'.player_id').text(playerids[1].playerName);
		playerPosition[1] = playerids[1].playerId;
		$('#player_2').data('playerid', playerids[2].playerId).find(
				'.player_id').text(playerids[2].playerName);
		playerPosition[2] = playerids[2].playerId;
		$('#player_4').data('playerid', playerids[3].playerId).find(
				'.player_id').text(playerids[3].playerName);
		playerPosition[4] = playerids[3].playerId;
		$('#player_5').data('playerid', playerids[4].playerId).find(
				'.player_id').text(playerids[4].playerName);
		playerPosition[5] = playerids[4].playerId;
		break;
	case 6:
		$('#player_1').data('playerid', playerids[1].playerId).find(
				'.player_id').text(playerids[1].playerName);
		playerPosition[1] = playerids[1].playerId;
		$('#player_2').data('playerid', playerids[2].playerId).find(
				'.player_id').text(playerids[2].playerName);
		playerPosition[2] = playerids[2].playerId;
		$('#player_3').data('playerid', playerids[3].playerId).find(
				'.player_id').text(playerids[3].playerName);
		playerPosition[3] = playerids[3].playerId;
		$('#player_4').data('playerid', playerids[4].playerId).find(
				'.player_id').text(playerids[4].playerName);
		playerPosition[4] = playerids[4].playerId;
		$('#player_5').data('playerid', playerids[5].playerId).find(
				'.player_id').text(playerids[5].playerName);
		playerPosition[5] = playerids[5].playerId;
		break;
	}
}
// 初始化其他玩家牌堆
function initializeOthers(callback) {

	var OtherPlayer = "<div class='other_player_card inactive'></div><div class='info'><span class='player_id'></span> 剩余: <span class='remain_cards'>0</span></div>";
	$('.other_player').empty().append(OtherPlayer);
	getCard(108, function(card) {
		$('.other_player_card').append(card);
		if (callback != null) {
			callback();
		}
	});
}
// 初始化摸牌堆
function initializeStack() {
	$card_stack = $('#card_stack');
	$card_stack.empty();
	getCard(
			108,
			function(card) {
				card.attr("id", "s1").appendTo($card_stack);
				getCard(
						108,
						function(card) {
							card.attr("id", "s2").appendTo($card_stack);
							getCard(
									108,
									function(card) {
										card.attr("id", "s3").appendTo(
												$card_stack);
										getCard(
												108,
												function(card) {
													card
															.attr("id", "s4")
															.appendTo(
																	$card_stack);
													getCard(
															108,
															function(card) {
																card
																		.attr(
																				"id",
																				"back")
																		.appendTo(
																				$card_stack);
																$card_stack
																		.append("<div class='card' id='card_stack_button'></div>");

															});
												});
									});
						});
			});
}
// UNO动画
function unoAnimation(name) {
	$('#uno').children('#uno_player').text(name);
	$('#uno').fadeIn(300, function() {
		setTimeout(function() {
			$('#uno').fadeOut(300)
		}, '1000');
	});
}
// 出牌动画
function playCardsAnimation($change, callback) {
	var $used_card = $('#used_card');
	var pos = $change.offset();
	var pos_des = $used_card.children().last().offset();
	var new_card = $change.clone();
	$change.css({
		"top" : pos.top,
		"left" : pos.left,
		"position" : 'absolute'
	});
	$change.animate({
		"top" : pos_des.top,
		"left" : pos_des.left
	}, 1000, function() {
		$('#used_card').children().removeAttr('style');
		$change.remove().removeAttr('style');
		$used_card.append($change);

		if ($used_card.children().length >= 6) {
			$used_card.children().first().remove();
		}
		if (callback != null) {
			callback();
		}
	});
}
// 他人出牌
function othersPlayCards(Card_Id, Player_Id) {
	getCard(Card_Id, function(card) {
		log("other play card animate");
		var $otherplayer = $('#player_' + Player_Id + ' .other_player_card');
		card.appendTo($otherplayer);
		setTimeout(function() {
			log('#card_' + Card_Id);
			playCardsAnimation($('#card_' + Card_Id));
		}, "500");
		/* 卡牌剩余数-1 */
		var $remain_cards = $('#player_' + Player_Id + ' .info').children(
				'.remain_cards');
		var remain_num = $remain_cards.text();
		$remain_cards.text(eval(remain_num + '-1'));
	});
}
// wild改色动画
function changeColorAnimation(id, color) {
	log("change color animation");
	var $wildcolorcard = $('#card_' + id).filter('.wild').last();
	log($wildcolorcard);
	var colors = ['red', 'yellow', 'green', 'blue'];
	log(colors[color]);
	$wildcolorcard.removeClass('wild').addClass(colors[color]).transition({
		scale : 1
	}, 300, function() {
		$('#used_card').children().removeAttr('style');
	});

}
// 在 $container 中添加任意张牌
function appendRandomCards($container, num, callback) {
	log('before get');
	$.get('cards/all_cards.html', function(data) {
		log('get complete and before append');
		for ( var i = 0; i < num; ++i) {
			log('before append card ' + i);
			$container.append($(data).filter(
					'#card_' + parseInt(108 * Math.random())));
			log('append card ' + i);
		}
		log('finish append');
		callback();
	});
	log('width: ' + $container.width());
};

// 依据 ID 获得某张牌并作为参数传入 callback(card) 函数
function getCard(id, callback) {
	var card;
	$.get('cards/all_cards.html', function(data) {
		log('done get card');
		card = $(data).filter('#card_' + id);
		callback(card);
	});
};
// 添加id=front牌
function addFrontCard(id, callback) {
	getCard(id, function(card) {
		card.attr("id", "front");
		$('#back').before(card);
		callback();
	});
}
// 居中
function beCenter($element, $container) {
	$element.css({
		left : parseInt((($container.width() - $element.outerWidth()) / 2)),
	});
};

function log(msg) {
	console.log(msg);
};

function queryHandle() {
	$.getJSON("/happyuno/event", {
		"tableId" : currentTableId,
		"playerId" : currentPlayerId
	}, function(data) {
		log(data);
		$.each(data,
				function(key, val) {
					switch (val.type) {
					case 1:
						log("play card");
						if (currentPlayerId != val.playerId) {
							log('others play card');
							othersPlayCards(val.cardId,
									getPlayerPosition(val.playerId));
						} else {
							log('player0 play card');
							playCardsAnimation($('#card_' + val.cardId));
						}
						break;
					case 2:
						log("draw card");
						if (currentPlayerId == val.playerId) {
							log('draw a card');
							$('#card_stack_button').hide();

							addFrontCard(val.cardId, function() {
								dealOneCardAnimation(val.cardId);
							});
						} else {
							/* 卡牌剩余数+1 */
							var $remain_cards = $(
									'#player_'
											+ getPlayerPosition(val.playerId)
											+ ' .info').children(
									'.remain_cards');
							var remain_num = $remain_cards.text();
							$remain_cards.text(eval(remain_num + '+1'));
						}
						break;
					case 3:
						log('change color');
						if (currentPlayerId == val.playerId) {
							changeColorAnimation(val.cardId, val.color);
						} else {
							log(eval(4+val.color));
							punishAnimation(eval(4+val.color));
						}
						break;
					case 4:
						log('say uno');
						break;
					case 5:
						log('catch uno');
						if (currentPlayerId == val.catcherId) {
							punishAnimation(2);
						} else if (currentPlayerId == val.targetId) {
							punishAnimation(3);
						}
						break;
					case 6:
						log('game end');
						if (currentPlayerId == val.winnerId) {
							punishAnimation(8);
						} else  {
							punishAnimation(9);
						}
						break;
					case 7:
						log('Draw Multiple Card');
						var cards = val.cards;
						if (val.playerId == currentPlayerId) {
							for (var i =0; i < cards.length; i++) {
								directDealCardAnimation(cards[i]);
							}
						} else {
							log(cards.length);
							/* 卡牌剩余数+n */
							var $remain_cards = $(
									'#player_'
											+ getPlayerPosition(val.playerId)
											+ ' .info').children(
									'.remain_cards');
							var remain_num = $remain_cards.text();
							$remain_cards.text(eval(remain_num +'+' +cards.length));
						}
						break;
					case 8:
						log('Next Player');
						if (val.playerId == currentPlayerId) {
							alert("到你啦！");
						}
						break;
					case 9:
						log('Init Game');
						$('#player_0_cards').empty();
						$('.remain_cards').text('7');
						var $cardInit = $('#card_init');
						var colors = ["red", "yellow", "green", "blue"];
						$cardInit.addClass(colors[val.color]);
						$cardInit.children().text(val.num);
						break;
					};
				});
		// window.clearInterval(oTimer);
	});
};

function getPlayerPosition(id) {
	var position = -1;
	for ( var i = 0; i < 6; i++) {
		if (playerPosition[i] == id) {
			position = i;
			break;
		}
	}
	return position;
}