package com.happyuno.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;


import com.happyuno.entity.User;
import com.happyuno.entity.maindeal;
import com.happyuno.model.GameTable;

@WebServlet("/CreateGameTable")
public class CreateGameTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateGameTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response)
    		throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//获得房主设置的最大玩家数
		String setNum = request.getParameter("defiNum");
		int defiNum = Integer.parseInt(setNum);
		
		//获得牌桌列表
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
				(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		
//		HashMap<Integer, GameTable> gameTableMap = new HashMap<Integer, GameTable>;
		//根据获得的用户ID 找到user
		String userId = (String)request.getSession().getAttribute("userId");
		maindeal md=new maindeal();
		List dbUserList = md.query("from User as u where u.id='"+userId+"'");
		User curUser=(User)dbUserList.get(0);		
		
		//当前所有牌桌中，最大的编号
		Integer maxTable=(Integer)application.getAttribute("maxTable");
		if(maxTable==null){
			maxTable=0;
		}
		//创建第一个牌桌
		if(gameTableMap == null){
			//新建牌桌列表
			gameTableMap = new HashMap<Integer, GameTable>();
			//添加第一个牌桌
			GameTable gameTable = new GameTable(1,defiNum,curUser);
			maxTable=1;
			//添加牌桌的第一个人(庄家/房主）
			gameTable.getUserList().add(curUser);
			gameTableMap.put(gameTable.getTableId(), gameTable);
		}
		else{
			maxTable += 1;
			GameTable gameTable = new GameTable(maxTable,defiNum,curUser);			
			//添加牌桌的庄家（房主）
			gameTable.getUserList().add(curUser);
			gameTableMap.put(gameTable.getTableId(), gameTable);
		}
		
		//更新gameTableList,maxTable
		application.setAttribute("gameTableMap",gameTableMap);
		application.setAttribute("maxTable",maxTable);	
		
		//tableMap上一共多少桌子
		application.setAttribute("tatoNum", gameTableMap.size());

		//对下一个页面传参数
		HttpSession session = request.getSession();		
		session.setAttribute("tableId",maxTable);
		session.setAttribute("userList", gameTableMap.get(maxTable).getUserList());
	
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/waiting.jsp");
		dispatcher.forward(request, response);
			
//		PrintWriter out = response.getWriter();
//		out.print("用户ID");
//		out.println(userId);
//		out.print("最大桌号");
//		out.println(maxTable);
//		out.print("当前桌号");
//		out.println(gameTableMap.get(maxTable).getTableId());
    }
}
