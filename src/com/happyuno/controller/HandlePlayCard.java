/*
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
 */

package com.happyuno.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.happyuno.entity.maindeal;
import com.happyuno.model.*;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class handleplaycard
 */
@WebServlet("/HandlePlayCard")
public class HandlePlayCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandlePlayCard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//处理玩家出牌
		request.setCharacterEncoding("utf-8");
		String tableId = request.getParameter("tableId");
		String  playerid = request.getParameter("playerId");
		String cardid=request.getParameter("cardId");
		//调用牌局列表的application对象
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
		(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		//获取牌局
		GameTable gametable=gameTableMap.get(Integer.parseInt(tableId));
		//获取操作用户
		Player player=gametable.getCurrentGame().getPlayerFromId(new Integer(playerid));
		PrintWriter out = response.getWriter();
		//判断能否出牌
		if(player.playCard(player.getCardFromId(new Integer(cardid)))){
			//返回成功，否则失败,???为目标地址
			out.println("{success:'true'}");
		}else{
			//出牌失败，调用惩罚,摸两张牌···还需看是否有累积摸牌
			out.println("{success:'false'}");
		}
	}
}
