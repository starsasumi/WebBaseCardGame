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
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import org.json.JSONArray;
import org.json.JSONObject;

import com.happyuno.entity.User;
import com.happyuno.entity.maindeal;
import com.happyuno.model.GameTable;

@WebServlet("/JoinGameTable")
public class JoinGameTable extends HttpServlet {
	private static final long serialVersionUID = 1L;    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public JoinGameTable() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
    		throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		//玩家选中的牌桌号
		String tableId = (String)request.getParameter("tableId");
//		int tableId =Integer.parseInt(request.getSession().getAttribute("tableId").toString());
		

//		String setNum = request.getParameter("setNum").trim();
		
		
		//获得牌桌列表		
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
				(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		
		//根据获得的用户ID 找到user
		String userId = (String)request.getSession().getAttribute("userId");
		maindeal md=new maindeal();
		List dbUserList=md.query("from User as u where u.id='"+userId+"'");
		User curUser=(User)dbUserList.get(0);		
		
		//返回结果
		String resultMessage = "进入牌桌失败！";
		int index = Integer.parseInt(tableId);
		System.out.println(index);
		//获得房主创建的最大人数
		GameTable gametable = gameTableMap.get(index);
		if(gametable==null){
			System.out.println("true");
		}
		
		int defiNum = gametable.getDefiNum();
		
		//
		if(gametable.isStateStart() == false){ //当前牌局没开始
			if(gametable.getUserList().size() < defiNum){ //每一桌人数小于既定人数时
				if (!gametable.getUserList().contains(curUser)) {
					gametable.getUserList().add(curUser);
				}
				resultMessage="成功进入！";
			}
			else{
				resultMessage="对不起！牌桌已凑满人数！";
			}
		}
		else{
			resultMessage="对不起！牌桌已开始游戏！";
		}
	
		//更新
		application.setAttribute("gameTableMap",gameTableMap);
		
		//对下一个页面传参数
		HttpSession session = request.getSession();		
		session.setAttribute("tableId",index);
		session.setAttribute("userList", gameTableMap.get(index).getUserList());
				
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/waiting.jsp");
		dispatcher.forward(request, response);
		
//		PrintWriter out = response.getWriter();
//		out.print("用户ID");
//		out.println(userId);
//		out.print("信息:");
//		out.println(resultMessage);
//		out.print("当前人数");
//		out.println(gametable.getUserList().size());
		
	
			
    }
}
