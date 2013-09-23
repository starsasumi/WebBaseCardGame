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

package com.happyuno.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;
import com.happyuno.model.*;

/**
 * Servlet implementation class eventCenter
 */
@WebServlet("/event")
public class EventController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control","no-cache");
		
		int playerId = Integer.parseInt(request.getParameter("playerId").trim());
		int gameId = Integer.parseInt(request.getParameter("tableId").trim());
		
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap=
				(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		//获取牌局
		Game game = gameTableMap.get(gameId).getCurrentGame();
		
		ArrayList<JSONObject> eventsAL = game.getEventCenter().getEvents(game.getPlayerFromId(playerId));
		
		JSONArray events = new JSONArray();
		for (int i=0; i<eventsAL.size(); ++i) {
			events.put(eventsAL.get(i));
		}
		
		response.getWriter().write(events.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
