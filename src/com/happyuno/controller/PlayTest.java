package com.happyuno.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.happyuno.entity.User;
import com.happyuno.model.GameTable;
import com.happyuno.model.Player;

/**
 * Servlet implementation class PlayTest
 */
@WebServlet("/PlayTest")
public class PlayTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayTest() {
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
		User u1 = new User();
		
		HashMap<Integer, GameTable> gameTableMap = new HashMap<Integer, GameTable>();
		GameTable gameT = new GameTable(1,3,u1);//tableId = 1
		gameTableMap.put(gameT.getTableId(), gameT);
		
		int curPlayerId = Integer.parseInt((String) request.getParameter("curPlayerId")); 
		System.out.println(curPlayerId);
		ArrayList<Player> players = new ArrayList<Player>();
		Player p1 = new Player(1,"Tom");
		Player p2 = new Player(2,"Jerry");
		players.add(p1);
		players.add(p2);
		gameT.getCurrentGame().initGame(players);
		
//		Integer hostId = new Integer(gameT.getHost().getId());
		HttpSession session = request.getSession();
		session.setAttribute("tableId", 1);
		session.setAttribute("players", players);
		session.setAttribute("curPlayerId", curPlayerId);
		
		//更新牌桌列表
		ServletContext application= request.getSession().getServletContext();
		application.setAttribute("gameTableMap", gameTableMap);
		
		//tableMap上一共多少桌子
		application.setAttribute("tatoNum", gameTableMap.size());
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/table.jsp");
		dispatcher.forward(request, response);
	}

}
