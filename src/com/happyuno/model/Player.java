package com.happyuno.model;

import java.util.*;
/**
 * 玩家类，与系统的用户类（com.happyuno.entity.User）不同，只负责在游戏中作为玩家的虚拟而存在。
 * Controller 通过调用 public 方法来驱动游戏进行。
 * @author starsasumi
 *
 */
public class Player {
	private int id;
	private String name;
	private ArrayList<Card> cardList;
	
	// state
	private boolean uno;
	private Card playedWild;
	
	
	private Game game;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.uno = false;
		this.playedWild = null;
	}
	
/* * Getters & Setters * */
	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game
	 */
	protected void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置 Player Id，建议使用 User Id 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	/**
	 * 设置 Player Name，建议使用 User Name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	private ArrayList<Card> getCardList(){
		if (this.cardList == null) {
			this.cardList = new ArrayList<Card>();
		}
		return this.cardList;
	}

	/**
	 * @param cardList the cardList to set
	 */
	@SuppressWarnings("unused")
	private void setCardList(ArrayList<Card> cardList) {
		this.cardList = cardList;
	}
	
/* * tool methods * */
	
	/**
	 * 给用户增加一张手牌
	 * @param card
	 * @throws Exception 
	 */
	private void addCard(Card card) throws Exception {
		if (card != null) { 
			this.getCardList().add(card);
		} else {
			throw new Exception("Can't get card");
		}
	}
	
/* * Public methods, Controller is free to use. * */

	/**
	 * 用户摸 num 张牌
	 * @param num
	 * @return 是否成功获得牌
	 */
	public boolean drawCard(int num) {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i=0; i<num; ++i) {
			try {
				Card card = this.getGame().deal();
				this.addCard(card);
				cards.add(card);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		// 手牌数大于 1，取消 uno 状态。
		if (this.cardNum() > 1) {
			this.uno = false;
		}
		
		if (cards.size() == 1) {
			this.getGame().getEventCenter().notifyDrawCard(this, cards.get(0).getId());
		} else if (cards.size() > 1) {
			this.getGame().getEventCenter().notifyDrawMultipleCard(this, cards);
		}
		return true;
	}

	/**
	 * 用户出牌
	 * @param 所出的牌
	 * @return 出牌是否成功
	 */
	public boolean playCard(Card card) {
		if (this.getCardList().contains(card)) {
			if (this.game.handlePlayCard(this, card)) {
				this.getGame().getEventCenter().notifyPlayCard(this, card.getId());
				this.getCardList().remove(card);
				if (card.getColor() == Card.COLOR_WILD)
					this.playedWild = card;
				else
					this.playedWild = null;
				
				if (this.cardNum() == 0) {
					this.getGame().handleEndGame(this);
				}
				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 用户决定 wild 的颜色
	 * @param 决定的颜色
	 * @return 改色是否成功，在出 wild 后调用此函数才返回 true
	 */
	public boolean decideWildCardColor(int color){
		if (this.playedWild != null) {
			if (this.game.handleWild(this, color)) {
				this.getGame().getEventCenter().notifyColorChange(this.getId(), this.playedWild.getId(), color);
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 用户喊 Uno，手牌数不为 1 时什么都不发生
	 */
	public void sayUno() {
		if (this.getCardList().size() == 1) {
			this.uno = true;
			this.game.handleSayUno(this);
			this.getGame().getEventCenter().notifySayUno(this);
		}
	}
	
	/**
	 * 举报某玩家没有喊 Uno
	 * @param player
	 * @return 举报是否有效
	 */
	public boolean catchUno(Player player) {
		this.getGame().getEventCenter().notifyCatchUno(this, player);
		return this.game.handleCatchUno(this, player);
	}
	
	/**
	 * 跳过本次回合
	 * @return 跳过是否成功
	 */
	public boolean pass() {
		return this.game.handlePass(this);
	}
	
	/* * Satat Query Method, Controller is free to use. * */
	/**
	 * 是否已喊 Uno
	 * @return
	 */
	public boolean isUno() {
		return this.uno;
	}
	
	/**
	 * 返回手牌数
	 * @return 手牌数
	 */
	public int cardNum() {
		return getCardList().size();
	}
	
	/**
	 * 是否出了 Wild 牌
	 * @return
	 */
	public boolean isPlayedWild() {
		if (this.playedWild != null)
			return true;
		else
			return false;
	}
	
	/**
	 * 通过 Card ID 获得用户的牌实例，若不存在则返回 null。
	 * @param id
	 * @return
	 */
	public Card getCardFromId(int id) {
		Iterator<Card> cardIterator = this.getCardList().iterator();
		Card card = null;
		while (cardIterator.hasNext()) {
			card = cardIterator.next();
			if (card.getId() == id){
				return card;
			} else {
				card = null;
			}
		}
		return null;
	}
	
}
