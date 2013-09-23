/*
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
 */

package com.happyuno.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.happyuno.model.Card;
import com.happyuno.model.Player;
/**
 * 游戏由此类的实例维持，一个牌桌一个实例。
 * 
 * 注意，本游戏通过 Player 类来驱动，Game 类中除了 initGame() 方法外，其余 public 方法都是为了调用方方便获得游戏状态而设置。
 * 而其余的 private、protected 方法均非为调用方设置，请勿通过改变代码来调用。
 * 
 * 游戏初始化方法：
 * 1. 建立一个存放玩家实例的 ArrayList<Player>。
 * 2. 建立一个 Game 实例。
 * 3. 将 ArrayList<Player> 作为参数调用 Game 实例 的 inmiFGame() 方法。
 * 4. 调用对应 Player 的方法来进行游戏。
 *  
 * @author starsasumi
 *
 */
public class Game {
	private ArrayList<Player> players;// 玩家列表
	private ArrayList<Card> cardStack;// 未摸牌堆
	private ArrayList<Card> usedCards;// 已出牌堆
	private EventCenter eventCenter;
	// states
	private int currentColor;
	private int currentMark;
	private int drawNum;
	private int currentPlayer;
	
	public Game() {
	}

/* * tool methods * */
	/**
	 * 洗牌
	 * @param cardList
	 */
	private void shuffle(ArrayList<Card> cardList){
		Collections.shuffle(cardList);
	}
	
	/**
	 * 当牌堆无牌且废牌堆有牌时，将废牌堆的牌放回牌堆，并重新洗牌。
	 */
	@SuppressWarnings("unchecked")
	private void reShuffle() {
		if (this.cardStack.isEmpty() && !(this.usedCards.isEmpty())) {
			this.cardStack = (ArrayList<Card>) this.usedCards.clone();
			this.usedCards.clear();
			this.shuffle(this.cardStack);
		}
	}

	/**
	 * 生成一副洗好的完整的牌
	 * @return
	 */
	private ArrayList<Card> initCardStack(){
		ArrayList<Card> cards = new ArrayList<Card>();
		ArrayList<Integer> colors 
			= new ArrayList<Integer>(Arrays.asList(Card.COLOR_RED, Card.COLOR_YELLOW, Card.COLOR_GREEN, Card.COLOR_BLUE, Card.COLOR_WILD));
		ArrayList<Integer> marks 
			= new ArrayList<Integer>(Arrays.asList(0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,Card.MARK_DRAW_TWO,Card.MARK_DRAW_TWO,Card.MARK_SKIP,Card.MARK_SKIP,Card.MARK_REVERSE,Card.MARK_REVERSE));
		Card c = null;
		int id=-1;	// card id
		
		for (int color : colors) {
			if (color != Card.COLOR_WILD){
				for (int mark : marks) {
					id++;
					if (mark < 10) {	// number card
						c = new Card();
						c.setId(id);
						c.setMark(mark);
						c.setColor(color);
					} else {	// function card
						c = new Card();
						c.setId(id);
						c.setColor(color);
						c.setMark(mark);
					}	// if (mark < 10)
					
					cards.add(c);
					c = null;
				}	// for (int mark : marks)
			} else {	// wild card
				for (int m=0;m<8;++m) {
					id++;
					int mark;
					if (m < 4) {
						mark = Card.MARK_WILD;
					} else {
						mark = Card.MARK_DRAW_FOUR;
					}
					
					c = new Card();
					c.setId(id);
					c.setColor(color);
					c.setMark(mark);
					cards.add(c);
					c = null;
				}
			}
		}
		
		this.shuffle(cards);
		return cards;
	}
	
	private void initPlayers(ArrayList<Player> players){
		this.players = players;
		this.currentPlayer = 0;

		Iterator<Player> playerIterator = this.players.iterator();
		while (playerIterator.hasNext()) {
			playerIterator.next().setGame(this);
		}
	}
	
	private void initColorAndNum() {
		this.changeColor((int) (4 * Math.random()));	// 0~3
		this.changeMark((int) (10 * Math.random()));	// 0~9
	}
	
	private void initDrawNum() {
		this.drawNum = 0;
	}
	
	private void initEventCenter(){
		this.eventCenter = new EventCenter(this.players);
	}
	
	private void initDraw(){
		Iterator<Player> playerIterator = this.players.iterator();
		while (playerIterator.hasNext()) {
			playerIterator.next().drawCard(7);
		}
	}
	
	private boolean hasPlayer(int playerId) {
		if (playerId < 0 || playerId >= this.players.size()) {
			return false;
		} else {
			return true;
		}
	}
	
	private void nextPlayer() {
		this.currentPlayer = (this.currentPlayer + 1) % this.players.size();
	}
	
	private boolean changeColor(int color) {
		switch (color) {
		case Card.COLOR_RED:
			this.currentColor = Card.COLOR_RED;
			break;
		case Card.COLOR_YELLOW:
			this.currentColor = Card.COLOR_YELLOW;
			break;
		case Card.COLOR_GREEN:
			this.currentColor = Card.COLOR_GREEN;
			break;
		case Card.COLOR_BLUE:
			this.currentColor = Card.COLOR_BLUE;
			break;
		default:
			return false;
		}
		return true;
	}
	
	private boolean changeMark(int mark) {
		switch (mark) {
		case Card.MARK_DRAW_TWO:
			this.handleDraw(2);
			break;
		case Card.MARK_SKIP:
			this.handleSkip();
			break;
		case Card.MARK_REVERSE:
			this.handleReverse();
			break;
		case Card.MARK_DRAW_FOUR:
			this.handleDraw(4);
			break;
		}
		
		if ((mark > 0) && (mark <= 14)) {
			this.currentMark = mark;
			return true;
		} else {
			return false;
		}
	}
	
	private void clearDrawNum() {
		this.drawNum = 0;
	}

	private boolean handleSkip() {
		this.nextPlayer();
		return true;
	}
	
	private boolean handleReverse() {
		if (this.hasPlayer(this.currentPlayer)) {
			Player player = this.players.get(this.currentPlayer);
			Collections.reverse(players);
			this.currentPlayer = this.players.indexOf(player);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean handleDraw(int draw) {
		this.drawNum += draw;
		return true;
	}
	
	private boolean isColorOK(Card card) {
		if (card.getColor() == this.getColor()) {
			return true;
		}
		if (card.getColor() == Card.COLOR_WILD) {
			return true;
		}
		return false;
	}
	
	private boolean isMarkOK(Card card) {
		if (card.getMark() == this.getMark()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 出错惩罚
	 * @param player
	 * @param result
	 * @return
	 */
	private boolean punish(Player player, boolean result) {
		if (!result) {
			player.drawCard(2);
		}
		
		return result;
	}
	
	/**
	 * 负责处理 +2、+4 牌的摸牌
	 * @param player
	 */
	private void drawPunish(Player player) {
		player.drawCard(this.getDrawNum());
		this.clearDrawNum();
	}
	
	/**
	 * 游戏结束的后续处理
	 * @param winner
	 */
	private void endGame(Player winner) {
		// Do something to end the game
		this.players.clear();
		this.players.add(winner);
		this.cardStack = null;
		this.usedCards = null;
		this.currentColor = -1;
		this.currentMark = -1;
		this.drawNum = 0;
		this.currentPlayer = -1;
		this.getEventCenter().notifyGameEnd(winner);
	}
	
	/* * protected methods, just for Player Object to use * */

	/**
	 * 从牌堆中**取出**一张牌
	 * @return 返回取出的卡片，如果 cardStack 与 usedCards 中同时无牌，则返回 null
	 */
	protected Card deal(){
		Card card = null;
		if (!this.cardStack.isEmpty()) {
			card = this.cardStack.remove(0);
		} else {
			this.reShuffle();
			// 重洗牌后不保证 this.cardStack 中有牌
			if (!this.cardStack.isEmpty()) {
				card = this.deal();
			}
		}
		return card;
	}

	/**
	 * 处理玩家出牌
	 * @param player
	 * @param card
	 * @return
	 */
	protected boolean handlePlayCard(Player player, Card card) {
		System.out.println(player.getId() + " : " + card.getColor() + " : " + card.getMark());
		// 判断出牌是否有效。
		if (this.players.indexOf(player) != this.currentPlayer) {
			System.out.println("#1");
			return this.punish(player, false);
		}
		// 颜色与标记都无效，则此牌无效
		if ((!this.isColorOK(card)) && (!this.isMarkOK(card))) {
			System.out.println("#2");
			System.out.println(this.getColor());
			System.out.println(this.isColorOK(card));
			System.out.println(this.isMarkOK(card));
			return this.punish(player, false);
		}
		
		// 临时记录历史状态
		int oldMark = this.getMark();
		
		// 根据出牌更新状态。
		this.changeColor(card.getColor());
		this.changeMark(card.getMark());
		this.nextPlayer();
		
		// Draw Punish
		if (this.getDrawNum() > 0) {
			System.out.println("draw :" + this.getDrawNum());
			// 本牌为普通牌
			if (this.getMark() <= 9) {
				System.out.println(" <= 9");
				this.drawPunish(player);
			} else if ((oldMark == Card.MARK_DRAW_FOUR) && (card.getMark() != Card.MARK_DRAW_FOUR)) {	// 上一次为 +4 牌，本次不为 +4 牌
				System.out.println(" not +4");
				this.drawPunish(player);
			}
		}
		
		// 最后出 Wild 的处理
		if ((player.cardNum() == 0) && (card.getMark() == Card.COLOR_WILD)) {
			player.drawCard(1);
		}
		
		System.out.println(player.getId() + " left " +player.cardNum());
		
		this.getEventCenter().nofityNextPlayer(this.getPlayers().get(currentPlayer));
		return true;
	}
	/**
	 * 判断玩家是否有权指定颜色
	 * @param player
	 * @param color
	 * @return
	 */
	protected boolean handleWild(Player player, int color) {
		if (player.isPlayedWild()) {
			return this.changeColor(color);
		} else {
			return this.punish(player, false);
		}
	}

	/**
	 * 处理玩家喊 Uno
	 * @param player
	 * @return
	 */
	protected boolean handleSayUno(Player player) {
		if (player.cardNum() != 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 处理玩家捉 Uno
	 * @param catcher
	 * @param target
	 * @return
	 */
	protected boolean handleCatchUno(Player catcher, Player target) {
		if ((target.cardNum() != 1) || target.isUno()) {
			return this.punish(catcher, false);
		} else {
			this.punish(target, false);
			return true;
		}
	}
	
	/**
	 * 处理玩家本局不出
	 * @param player
	 * @return
	 */
	protected boolean handlePass(Player player) {
		if (this.players.indexOf(player) == this.currentPlayer) {
			this.nextPlayer();
			// Draw Punish
			if (this.getDrawNum() > 0) {
					this.drawPunish(player);
			}
			this.getEventCenter().nofityNextPlayer(this.getPlayers().get(currentPlayer));
			return true;
		}
		
		return false;
	}
	
	protected boolean handleEndGame(Player player) {
		if (player.cardNum() == 0) {
			this.endGame(player);
			return true;
		}
		return false;
	}

	/* * public methods, Controller is free to use * */
	/**
	 * 初始化游戏，允许 2 到 6 人游戏。
	 * @return 初始化是否成功
	 */
	public boolean initGame(ArrayList<Player> players){
		if((players.size() >= 2) && (players.size() <= 6)) {
			this.cardStack = this.initCardStack();
			this.usedCards = new ArrayList<Card>();
			this.initPlayers(players);
			this.initColorAndNum();
			this.initDrawNum();
			this.initEventCenter();
			this.initDraw();
			this.getEventCenter().nofityInit(this.getColor(), this.getMark(), this.getPlayers().get(currentPlayer));
			System.out.println("==== init ====");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得当前颜色
	 * @return 颜色
	 */
	public int getColor() {
		return this.currentColor;
	}
	
	/**
	 * 获得当前标记
	 * @return 标记
	 */
	public int getMark() {
		return this.currentMark;
	}
	
	/**
	 * 获得当前出牌的玩家,若返回 -1 标明游戏结束。
	 * 注：可配合 getCurrentPlayer() 来获得当前玩家实例
	 * @return 当前出牌玩家的序号
	 */
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	/**
	 * 获得所有的玩家
	 * 注：可配合 getCurrentPlayer() 来获得当前玩家实例
	 * @return 所有玩家的 Unmodifiable List
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(this.players);
	}
	
	/**
	 * 获得当前累计罚摸牌数
	 * @return 累计罚摸牌数
	 */
	public int getDrawNum() {
		return this.drawNum;
	}
	
	/**
	 * 若游戏结束，返回赢家；否则，返回 null。
	 * @return 赢家或 null
	 */
	public Player getWinner() {
		if (this.getCurrentPlayer() == -1) {
			return this.players.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 返回发布消息的 EventCenter 实例，每桌一个
	 * @return
	 */
	public EventCenter getEventCenter() {
		return eventCenter;
	}
	
	/**
	 * 根据 player Id 返回 player，没有则 null
	 * @param playerId
	 * @return
	 */
	public Player getPlayerFromId(int playerId) {
		Player player = null;
		Player tmp = null;
		Iterator<Player> playerIterator = this.players.iterator();
		while (playerIterator.hasNext()) {
			tmp = playerIterator.next();
			if (tmp.getId() == playerId) {
				player = tmp;
				break;
			}
		}
		return player;
	}
}
