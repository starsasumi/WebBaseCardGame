package com.happyuno.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.happyuno.model.GameTable;

@WebServlet("/ViewTableInfo")
public class ViewTableInfo extends HttpServlet{
	private static final long serialVersionUID = 1L;    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTableInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
    		throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		//玩家选中的牌桌号
		String tableId = request.getParameter("tableId").trim();
		
		//获取TableMap
		ServletContext application= request.getSession().getServletContext();
		HashMap<Integer, GameTable> gameTableMap =
				(HashMap<Integer, GameTable>)application.getAttribute("gameTableMap");
		
		//返回什么
		application.setAttribute("gameTableMap",gameTableMap);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Xxx.jsp");
		dispatcher.forward(request, response);

    }

}
