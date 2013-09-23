/*
 * Copyright 2013 Ruoke Sun.
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

import com.happyuno.entity.User;
import com.happyuno.model.Game;

/**
 * 先大概写一下，后期需要调整 import中的类
 */
//牌桌类，一个牌局最多6人对战
public class GameTable {
	//牌桌号
	private Integer tableId;
	//牌桌参与者
	private ArrayList<User> userList;
	//房主
	private User host;
	//牌桌上进行的游戏
	private Game currentGame;
	//房主设置的最大玩家数
	private Integer defiNum;
	//牌桌的当前状态：游戏等待中false，哟普西进行中true
	private boolean stateStart;
	
	//getters & setters
	public User getHost() {
		return host;
	}
	public void setHost(User host) {
		this.host = host;
	}
	public Game getCurrentGame() {
		return currentGame;
	}
	public void setCurrentGame(Game currentGame) {
		this.currentGame = currentGame;
	}	
	public Integer getTableId() {
		return tableId;
	}
	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
	public ArrayList<User> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	public boolean isStateStart() {
		return stateStart;
	}
	public void setStateStart(boolean stateStart) {
		this.stateStart = stateStart;
	}
	public Integer getDefiNum() {
		return defiNum;
	}
	public void setDefiNum(Integer defiNum) {
		this.defiNum = defiNum;
	}
	
	//构造函数
	public GameTable(){
		
	}
	public GameTable(Integer tableId,Integer setNum,User host){
		this.tableId=tableId;
		this.userList = new ArrayList<User>();
		this.host=host;
		this.currentGame=new Game();
		this.defiNum = setNum;
		this.stateStart = false;
	}

}
