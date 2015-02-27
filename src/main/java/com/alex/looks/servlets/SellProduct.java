package com.alex.looks.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alex.looks.dao.mybatis.MyBatisConnectionFactory;
import com.alex.looks.dao.mybatis.MyBatisDAO;
import com.alex.looks.models.DirectoryProduct;
import com.alex.looks.models.SoldProduct;

public class SellProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MyBatisDAO dao = new MyBatisDAO(
			MyBatisConnectionFactory.getSqlSessionFactory());

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		Enumeration<String> l = session.getAttributeNames();
		String s;
		boolean isOk = false;
		while(l.hasMoreElements()) {
			if(l.nextElement().equals("status")) {
				if(session.getAttribute("status").equals("user") || session.getAttribute("status").equals("admin")){
					isOk = true;
					break;
				}
			}
		}

		if (!isOk) {
			session.setAttribute("status", "");
			response.sendRedirect("index.jsp");
		}
		else{
				
		if (request.getParameterMap().containsKey("SellOfProduct")) {
			
			String article = request.getParameter("art");
			String numb = request.getParameter("numb");
			String discount = request.getParameter("discount");
			SoldProduct a = new SoldProduct();
			a.setArticle(article);
			a.setNumber(Integer.parseInt(numb));
			a.setDiscount(Double.parseDouble(discount));
			
			List<DirectoryProduct> dp = new ArrayList<DirectoryProduct>();
			dp = (List<DirectoryProduct>) session.getAttribute("listDirectory");
			
			int kol=0;
			String provider = "";
			String story = "";
			for (DirectoryProduct i : dp) {
				if (i.getArticle().equals(article)) {
					a.setCostOutPut(i.getCostOutPut());
					a.setSum((a.getCostOutPut() * a.getNumber())
							- a.getDiscount());
					a.setName(i.getName());
					a.setFirm(i.getFirm());
					i.setNumber(i.getNumber() - Integer.parseInt(numb));
					kol=i.getNumber();
					provider = i.getProvider();
					story = i.getStory();
				}
			}
			Calendar c = Calendar.getInstance();
			Timestamp t = new Timestamp(c.getTimeInMillis());
			a.setTime(t);
			String nameUserAdmin = (String) session.getAttribute("fullName");
			a.setNameUserAdmin(nameUserAdmin);
			dao.insertNewSoldProduct(a);
			
			DirectoryProduct read = new DirectoryProduct(a.getName(), a.getFirm(), provider, a.getArticle(), kol, a.getCostOutPut(), a.getTime(), story, nameUserAdmin);
			dao.updateDirectoryProduct(read);
			if(read.getCostOutPut() == Double.parseDouble(discount)){
				read.setNumber(Integer.parseInt(numb));
				read.setStory("Списание товара");
				//dao.insertNewReturnOrWriteOff(read);
			}
			
			List<SoldProduct> sp = new ArrayList<SoldProduct>() ;
			List<SoldProduct> cash = dao.selectAllSoldProduct();
			
			for(SoldProduct i : cash){
				if(i.getTime().getDate() == t.getDate()){
					sp.add(i);
				}
			}
			session.setAttribute("listSoldProductThisDay", sp);
			dp.clear();
			dp = dao.selectAllDirectoryProduct();
			session.setAttribute("listDirectory", dp);
			
		}

		if (request.getParameterMap().containsKey("OutOfProduct")) {
			String article = request.getParameter("art");
			int id = Integer.parseInt(request.getParameter("id"));
			int number = Integer.parseInt(request.getParameter("number"));
						
			
			DirectoryProduct dirProd = new DirectoryProduct();
			dirProd.setArticle(article);
			/*dirProd = dao.selectOnTheArticleDirectoryProduct(article);*/  // 666666666666666666666666666666
			
			dirProd.setNumber(dirProd.getNumber() + number);
			dao.updateDirectoryProduct(dirProd);
			dao.deleteSoldProduct(id);
			dirProd.setNumber(number);
			dirProd.setStory("Возврат товара");
			//dao.insertNewReturnOrWriteOff(dirProd);
			
			List<DirectoryProduct> dp = dao.selectAllDirectoryProduct();
			
			List<SoldProduct> sp = new ArrayList<SoldProduct>();
			List<SoldProduct> cash = dao.selectAllSoldProduct();
			Calendar c = Calendar.getInstance();
			Timestamp t = new Timestamp(c.getTimeInMillis());
			
			for(SoldProduct i : cash){
				if(i.getTime().getDate() == t.getDate()){
					sp.add(i);
				}
			}
			
			session.setAttribute("listDirectory", dp);
			session.setAttribute("listSoldProductThisDay", sp);

		}

		response.sendRedirect("user.jsp");
		}
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		if (request.getParameterMap().containsKey("exit")) {
			session.setAttribute("status", "");
			session.setAttribute("fullName", "");
			response.sendRedirect("index.jsp");
		}
	}
}
