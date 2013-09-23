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

package com.happyuno.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.happyuno.entity.User;
import com.happyuno.model.GameTable;

/**
 * Servlet implementation class quitGameTable
 */
@WebServlet("/QuitGameTable")
public class QuitGameTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuitGameTable() {
        super();
        // TODO Auto-generated constructor stub
    }


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
		
		//获取userId
		String userId = (String)request.getSession().getAttribute("userId");

		//获得牌桌列表
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
				(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		
		int tindex = Integer.parseInt(tableId);
		GameTable gt=gameTableMap.get(tindex);
		ArrayList<User> userList =gt.getUserList();
		int index = 0 ;
		for (int i = 0; i < userList.size(); i++){
			if(userList.get(i).getId().equals(userId)){
				index = i;
			}
		}
		
		//如果退出的是房主,将userList里的第一个人设为host
		if(userList.get(index).equals(gt.getHost())){
			gt.setHost(gt.getUserList().get(	//房主的下一个人成为host
					(index+1)%userList.size()));
		}
		
		//删除用户
		userList.remove(index);
			
		//当前Table中没有玩家时，将Table从List中删除
		if(gt.getUserList().size() == 0){
			gameTableMap.remove(gt);
		}
							
		application.setAttribute("gameTableMap",gameTableMap);
		
		//tableMap上一共多少桌子
		application.setAttribute("tatoNum", gameTableMap.size());
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/table_list.jsp");
		dispatcher.forward(request, response);
		
//		PrintWriter out = response.getWriter();
//		out.print("用户ID：");
//		out.println(userId);
//		out.print("房主:");
//		out.println(gt.getHost().getName());
//		out.print("当前人数");
//		out.println(gt.getUserList().size());

	}

}
