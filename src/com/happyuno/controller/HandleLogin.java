package com.happyuno.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.happyuno.entity.*;

/**
 * Servlet implementation class deallogin
 */
@WebServlet("/HandleLogin")
public class HandleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleLogin() {
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
		String id = request.getParameter("id");
	    String pwd =  request.getParameter("password"); 
	    PrintWriter out = response.getWriter();
	    maindeal md=new maindeal();
	    List a=md.query("from User as u where u.id='"+id+"' and u.password='"+pwd+"'");
	    HttpSession session = request.getSession();
	        if(a.size()==1){
	            if(session.getAttribute("userId")==null){
	            	out.println("{success:true}");
	               session.setAttribute("userId",id);}
	            else if(id.equals(session.getAttribute("userId").toString())){
	                out.println("{success: false,meg:\"您已经登录该系统!\"}");}
	            else {
	            	session.setAttribute("userId",id);
	                out.println("{success:true}");
	                }
	            //RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/c2.jsp");
	            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/table_list.jsp");
    		    dispatcher.forward(request, response);
	       }else {
	            out.println("{success: false,meg:\"用户名或密码错误!\"}");
	       }
	}

}
