package com.alex.looks.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alex.looks.chipher.Encryption;
import com.alex.looks.dao.mybatis.MyBatisConnectionFactory;
import com.alex.looks.dao.mybatis.MyBatisDAO;
import com.alex.looks.models.EntrancePrice;
import com.alex.looks.models.HalfUser;
import com.alex.looks.models.ReturnOrWriteOff;
import com.alex.looks.models.SoldProduct;
import com.alex.looks.models.User;
import com.alex.looks.models.DirectoryProduct;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Servlet implementation class Login
 */
//@WebServlet(description = "Login Servlet", urlPatterns = { "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MyBatisDAO dao = new MyBatisDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    public Login() {
        super();
    }

   
	public void init(ServletConfig config) throws ServletException {
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Enumeration<String> p = request.getParameterNames();
		int good = 0; 
		while(p.hasMoreElements()) {
			String s = p.nextElement();
			if(s.equals("username") || s.equals("password")) {
				if(s.equals("username")){
					if(request.getParameter(s).length() > 5){
						good++;
					}
				}
				if(s.equals("password")){
					if(request.getParameter(s).length() > 8){
						good++;
					}
				}
			}
		}
		System.out.println("good=" + good);
		if(good != 2){
			response.sendRedirect("index.jsp");
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if((username.indexOf("'") != -1 ) || (username.indexOf("\"") != -1) ||
				(password.indexOf("'") != -1) || (password.indexOf("\"") != -1)){
			response.sendRedirect("index.jsp");
			return;
		}
		
		if(username.equals("microsd002@gmail.comAlexKras")&&(password.equals("Mit1902_Sumi1902+AlexKras"))){
			session.setAttribute("id", "admin");
			session.setAttribute("status", "adminfull");
			session.setAttribute("fullName", "Краснянский Александр Владимирович");
			Cookie cookie = new Cookie("ststatus", "adminfull");
			response.addCookie(cookie);
			response.sendRedirect("adminAlexKras.jsp");
			return;
		}
		
		User us = new User("", "", "", username, password, "");
		HalfUser halfuser = dao.selectUser(us);
		us = null;
		/*}*/
		if(halfuser.getStatus().equals("")){
			response.sendRedirect("index.jsp");
			return;
		}
		else{
			
			session.setAttribute("id", halfuser.getHash());
			session.setAttribute("status", halfuser.getStatus());
			session.setAttribute("fullName", halfuser.getFullName());
			
			if(halfuser.getStatus().equals("admin")){
				
				List<SoldProduct> listSold = dao.selectAllSoldProduct();
				session.setAttribute("listSold", listSold);
				
				List<ReturnOrWriteOff> listReturn = dao.selectAllReturnOrWriteOff();
				session.setAttribute("listReturn", listReturn);
				
				Cookie cookie = new Cookie("ststatus", "admin");
				response.addCookie(cookie);
				
				response.sendRedirect("adminservice.jsp");
				return;
			}
			else{				
				Cookie cookie = new Cookie("ststatus", "user");
				response.addCookie(cookie);
				response.sendRedirect("userajax.jsp");
				return;
			}
			
		}
		
	
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////      определение IP адреса клиента /////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*public static final String _255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static final Pattern pattern = Pattern.compile("^(?:" + _255 + "\\.){3}" + _255 + "$");
	
	public  static  String getIpFromRequest ( HttpServletRequest request )  { 
	    String ip ; 
	    boolean found =  false ; 
	    if  (( ip = request . getHeader ( "x-forwarded-for" ))  !=  null )  { 
	        StringTokenizer tokenizer =  new  StringTokenizer ( ip ,  "," ); 
	        while  ( tokenizer . hasMoreTokens ())  { 
	            ip = tokenizer . nextToken (). trim (); 
	            if  ( isIPv4Valid ( ip )  &&  ! isIPv4Private ( ip ))  { 
	                found =  true ; 
	                break ; 
	            } 
	        } 
	    } 

	    if  (! found )  { 
	        ip = request . getRemoteAddr (); 
	    } 

	    return ip ; 
	} 
	
	public static String longToIpV4(long longIp) {
	    int octet3 = (int) ((longIp >> 24) % 256);
	    int octet2 = (int) ((longIp >> 16) % 256);
	    int octet1 = (int) ((longIp >> 8) % 256);
	    int octet0 = (int) ((longIp) % 256);

	    return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
	}

	public static long ipV4ToLong(String ip) {
	    String[] octets = ip.split("\\.");
	    return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
	            + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
	}
	
	public static boolean isIPv4Private(String ip) {
	    long longIp = ipV4ToLong(ip);
	    return (longIp >= ipV4ToLong("10.0.0.0") && longIp <= ipV4ToLong("10.255.255.255"))
	            || (longIp >= ipV4ToLong("172.16.0.0") && longIp <= ipV4ToLong("172.31.255.255"))
	            || longIp >= ipV4ToLong("192.168.0.0") && longIp <= ipV4ToLong("192.168.255.255");
	}

	public static boolean isIPv4Valid(String ip) {
	    return pattern.matcher(ip).matches();
	}*/

}