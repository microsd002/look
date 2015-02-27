package com.alex.looks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alex.looks.chipher.Encryption;
import com.alex.looks.dao.mybatis.MyBatisConnectionFactory;
import com.alex.looks.dao.mybatis.MyBatisDAO;
import com.alex.looks.models.User;

public class AdminFullServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MyBatisDAO dao = new MyBatisDAO(
			MyBatisConnectionFactory.getSqlSessionFactory());

	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		Enumeration<String> l = session.getAttributeNames();
		String s;
		boolean isOk = false;
		while(l.hasMoreElements()) {
			if(l.nextElement().equals("status")) {
				if(session.getAttribute("status").equals("adminfull")){
					isOk = true;
					break;
				}
			}
		}
		
		
		if (!isOk) {
			session.removeAttribute("status");
			session.removeAttribute("fullName");
			response.sendRedirect("index.jsp");
			return;
		}
		else{
			if (request.getParameterMap().containsKey("exit")) {
				session.setAttribute("status", "");
				session.setAttribute("fullName", "");
				response.sendRedirect("index.jsp");
				return;
			}
	
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			try{
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				String result="";
				if (request.getParameter("load").equals("showuser")) {
					
					result = newLoad();	
				}
				
				if (request.getParameter("load").equals("newuser")) {
		
					String fullname = request.getParameter("three_name") + " " + request.getParameter("first_name") + " " + request.getParameter("second_name");
					String tel = request.getParameter("tel");
					String username = request.getParameter("username_registration");
					String password = request.getParameter("password_registration");
					String status = request.getParameter("status");
					Encryption md5 = new Encryption(username, password);
					String hash = md5.encryptionMD5();
					User nUser = new User(fullname, tel, status, username, password, hash);
					dao.insertUser(nUser);
					nUser = null;
					
					result = newLoad();	
				}
				
				if (request.getParameter("load").equals("update")) {
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					User us = new User();
					us.setUsername(username);
					us.setPassword(password);
					System.out.println(us.getUsername() + "  "+ us.getPassword());
					dao.updateUser(us);
					result = newLoad();	
				}
				if (request.getParameter("load").equals("delete")) {
					String username = request.getParameter("username");
					User us = new User();
					us.setUsername(username);
					dao.deleteUser(us);
					result = newLoad();	
				}
				
				
				response.getWriter().write(result);
			} finally{
				out.close();
			}
		}
	}
	
	private String newLoad(){
		List<User> listUsers = dao.selectAllUsers();
		String result = "";
		
		result += "<br><br>";
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr bgcolor=\"#00FFFF\">";
		result += "<th align=\"center\">ФИО</th>";
		result += "<th align=\"center\">Телефон</th>";
		result += "<th align=\"center\">Уровень доступа</th>";
		result += "<th align=\"center\">Логин</th>";
		result += "<th align=\"center\">Пароль</th>";
		result += "<th align=\"center\">Функция</th>";
		result += "</tr>";
		int n = 0;
		for(User i : listUsers){ 
			n++;
			result += "<tr align=\"center\">";
			result += "<td><label id=\"fullnametable"+n+"\">"+i.getFullName()+"</label></td>";
			result += "<td><label id=\"teltable"+n+"\">"+i.getTel()+"</label></td>";
			result += "<td><label id=\"statustable"+n+"\">"+i.getStatus()+"</label></td>";
			result += "<td><input type=\"text\" id=\"usernametable"+n+"\" value=\""+i.getUsername()+"\" style=\"font-size : 20px;\" readonly></td>";
			result += "<td><input type=\"text\" id=\"passwordtable"+n+"\" value=\""+i.getPassword()+"\" style=\"font-size : 20px;\"></td>";
			result += "<td>";
			result += "<input type=\"submit\" align=\"middle\" value=\"\" id=\"update"+n+"\" name=\"update\" id='update' class=\"update\" onclick=\"update_delete(this, 'update');\"/>";
			result += "<input type=\"submit\" align=\"middle\" value=\"\" id=\"delete"+n+"\" name=\"delete\" id='delete' class=\"delete\" onclick=\"update_delete(this, 'delete');\"/>";
			result += "</td>";
			result += "</tr>";
		}
		result += "</table>";
		
		return result;
	}

}
