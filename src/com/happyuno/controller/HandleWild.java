package com.happyuno.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.happyuno.model.Card;
import com.happyuno.model.GameTable;
import com.happyuno.model.Player;

/**
 * Servlet implementation class handleWild
 */
@WebServlet("/HandleWild")
public class HandleWild extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleWild() {
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
		request.setCharacterEncoding("utf-8");
		String gameid=request.getParameter("tableId");
		String  playerid=request.getParameter("playerId");
		String color=request.getParameter("color");
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
		(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		//获取牌局
		GameTable gametable=gameTableMap.get(Integer.parseInt(gameid));
		//出牌调用，处理后还需传递
		Player player=gametable.getCurrentGame().getPlayerFromId(Integer.parseInt(playerid));
		PrintWriter out = response.getWriter();
		if(player.decideWildCardColor(Integer.parseInt(color))){
			//返回成功，否则失败,???为目标地址
			out.println("{success:true}");
		}else{
			out.println("{success:false}");
		}
	}

}
