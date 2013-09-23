package com.happyuno.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.happyuno.entity.User;
import com.happyuno.entity.maindeal;
import com.happyuno.model.GameTable;
import com.happyuno.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class startGame
 */
@WebServlet("/StartGame")
public class StartGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartGame() {
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
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		//获取tableId
		String tableId = (String)request.getParameter("tableId");

		System.out.println(tableId);
		//获取userId
		String userId = (String)request.getSession().getAttribute("userId");
			
		//获得牌桌列表
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
				(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		
		String resultMessage = "无法开始游戏！";
		
		//由User的信息生成Player
		ArrayList<Player> players = new ArrayList<Player>();
		
		int tindex = Integer.parseInt(tableId);
		GameTable gt=gameTableMap.get(tindex);
		ArrayList<User> userList =gt.getUserList();
		
		resultMessage="开局成功！";
		//将当前牌局的状态改为“游戏进行中”
		gt.setStateStart(true);			
		System.out.println(userList.size()+"changdu");
		for(int i = 0; i< userList.size(); i++){
			//设置Player的id,name与User相同
			System.out.println(i+"position");
			String curUserId = ((User) userList.get(i)).getId();
			System.out.println("UserId"+curUserId);
			int cUserId = Integer.parseInt(curUserId);
			String curUserName = ((User) userList.get(i)).getName();
			Player curPlayer = new Player(cUserId,curUserName);
			players.add(curPlayer);
		}
		gt.getCurrentGame().initGame(players);
		
		HttpSession session = request.getSession();
		session.setAttribute("tableId", tindex);
		session.setAttribute("players", players);
		session.setAttribute("curPlayerId", Integer.parseInt(userId));
		
		//更新牌桌列表
		application.setAttribute("gameTableMap", gameTableMap);
				
		//tableMap上一共多少桌子
		application.setAttribute("tatoNum", gameTableMap.size());
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/table.jsp");
		dispatcher.forward(request, response);
			
		
	}

}
