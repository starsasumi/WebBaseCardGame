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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.happyuno.entity.User;
import com.happyuno.entity.maindeal;

/**
 * Servlet implementation class RegistDeal
 */
@WebServlet("/HandleRegist")
public class HandleRegist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleRegist() {
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
		String id = request.getParameter("id");
	    String name=request.getParameter("name");
	    String pwd =  request.getParameter("password");
	    String pwd2 =  request.getParameter("password2");
	    String ad=request.getParameter("admail");
	    User u=new User();
	    maindeal md=new maindeal();
	    List a=md.query("from User as u where u.id='"+id+"'");
	    PrintWriter out = response.getWriter();
	    if(a.size()==0){
	    	if(pwd.equals(pwd2)){
		    	u.setId(id);
			    u.setName(name);
			    u.setPassword(pwd);
			    u.setAdm(ad);
			    u.setExp(100);
			    u.setGrade(0);
			    u.setMoney(100);
			    md.saveorupdate(u);
			    out.print("{success:true}");
		    }else{
		    	out.print("{success:false,meg:\"两次密码不一致！\"}");
		    }
	    }else{
	    	out.print("{success:false,meg:\"该账户已被注册！\"}");
	    }
	    
	}

}
