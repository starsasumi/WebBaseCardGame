package com.happyuno.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An Event Center
 * 
 * @author starsasumi
 *
 */
public class EventCenter {
	public static final int EVENT_PLAY_CARD = 1;
	public static final int EVENT_DRAW_CARD = 2;
	public static final int EVENT_COLOR_CHANGE = 3;
	public static final int EVENT_SAY_UNO = 4;
	public static final int EVENT_CATCH_UNO = 5;
	public static final int EVENT_GAME_END = 6;
	public static final int EVENT_DRAW_MULTIPLE_CARD = 7;
	public static final int EVENT_NEXT_PLAYER = 8;
	public static final int EVENT_INIT = 9;
	
	private ArrayList<Player> players;
	private HashMap<Integer, ArrayList<JSONObject>> inboxs;
	
	protected EventCenter(ArrayList<Player> players) {
		this.setPlayers(players);
	}
	
	protected void init() {
		this.inboxs = new HashMap<Integer, ArrayList<JSONObject>>(this.getPlayers().size());
		
		Iterator<Player> playerIterator = this.getPlayers().iterator();
		while (playerIterator.hasNext()) {
			this.inboxs.put(playerIterator.next().getId(), new ArrayList<JSONObject>());
		}
	}

	/**
	 * @param players the players to set
	 */
	protected void setPlayers(ArrayList<Player> players) {
		this.players = players;
		this.init();
		
	}
	
	/**
	 * 为玩家添加一个事件
	 * @param player
	 * @param event
	 * @return
	 */
	private synchronized boolean addEvent(Player player, JSONObject event) {
		return this.inboxs.get(player.getId()).add(event);
	}
	/**
	 * 为所有玩家添加一个事件
	 * @param event
	 * @return
	 */
	private synchronized boolean addEvent(JSONObject event) {
		Iterator<Player> playerIterator = this.getPlayers().iterator();
		while(playerIterator.hasNext()) {
			this.addEvent(playerIterator.next(), event);
		}
		
		return true;
	}
	
	protected boolean notifyPlayCard(Player player, int cardId) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_PLAY_CARD);
			event.put("playerId", player.getId());
			event.put("cardId", cardId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean notifyDrawCard(Player player, int cardId) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_DRAW_CARD);
			event.put("playerId", player.getId());
			event.put("cardId", cardId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean notifyColorChange(int playerId, int cardId, int color) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_COLOR_CHANGE);
			event.put("playerId", playerId);
			event.put("cardId", cardId);
			event.put("color", color);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean notifySayUno(Player player) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_SAY_UNO);
			event.put("playerId", player.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean notifyCatchUno(Player catcher, Player target) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_CATCH_UNO);
			event.put("catcherId", catcher.getId());
			event.put("targetId", target.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean notifyGameEnd(Player winner) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_GAME_END);
			event.put("winnerId", winner.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean notifyDrawMultipleCard(Player player, ArrayList<Card> cards) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_DRAW_MULTIPLE_CARD);
			event.put("playerId", player.getId());
			JSONArray cardJson = new JSONArray();
			for (int i=0; i<cards.size(); ++i) {
				cardJson.put(cards.get(i).getId());
			}
			event.put("cards", cardJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean nofityNextPlayer(Player player) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_NEXT_PLAYER);
			event.put("playerId", player.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	protected boolean nofityInit(int color, int num, Player player) {
		JSONObject event = new JSONObject();
		try {
			event.put("type", EventCenter.EVENT_INIT);
			event.put("color", color);
			event.put("num", num);
			event.put("playerId", player.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.addEvent(event);
		return true;
	}
	
	/* * Public methods, Controller is free to use * */
	/**
	 * 返回玩家所应获得的消息。玩家不存在时返回 null
	 * @param player
	 * @return
	 */
	public synchronized ArrayList<JSONObject> getEvents(Player player) {
		ArrayList<JSONObject> result = null;
		if (this.getPlayers().contains(player)) {
			result = this.inboxs.get(player.getId());
			this.inboxs.put(player.getId(), new ArrayList<JSONObject>());
		}
		return result;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
}
