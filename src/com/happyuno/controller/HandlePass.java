package com.happyuno.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.happyuno.model.GameTable;
import com.happyuno.model.Player;

/**
 * Servlet implementation class HandleGetCard
 */
@WebServlet("/HandlePass")
public class HandlePass extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandlePass() {
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String gameid=request.getParameter("tableId");
		String  playerid=request.getParameter("playerId");
		
		//调用牌局列表的application对象
		
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
		(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		
		//获取牌局
		
		GameTable gametable=gameTableMap.get(Integer.parseInt(gameid));
		
		//获取操作用户
		
		Player player=gametable.getCurrentGame().getPlayerFromId(Integer.parseInt(playerid));
		
		//摸一张牌
		
		boolean result = player.pass();
		if (result) {
			response.getWriter().write("{success:true}");
		} else {
			response.getWriter().write("{success:false}");
		}
	}

}
