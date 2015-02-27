package com.alex.looks.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alex.looks.chipher.Encryption;
import com.alex.looks.dao.mybatis.MyBatisConnectionFactory;
import com.alex.looks.dao.mybatis.MyBatisDAO;
import com.alex.looks.dao.mybatis.UtilMyBatis;
import com.alex.looks.models.DirectoryProduct;
import com.alex.looks.models.EntrancePrice;
import com.alex.looks.models.Provider;
import com.alex.looks.models.ReturnOrWriteOff;
import com.alex.looks.models.SoldProduct;
import com.alex.looks.models.User;
import com.fasterxml.jackson.core.JsonParser;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MyBatisDAO dao = new MyBatisDAO(MyBatisConnectionFactory.getSqlSessionFactory());
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		if (request.getParameterMap().containsKey("exit")) {
			session.setAttribute("status", "");
			session.setAttribute("fullName", "");
			response.sendRedirect("index.jsp");
			return;
		}
		
		Enumeration<String> l = session.getAttributeNames();
		String s;
		boolean isOk = false;
		while(l.hasMoreElements()) {
			if(l.nextElement().equals("status")) {
				if(session.getAttribute("status").equals("admin") || session.getAttribute("status").equals("adminfull")){
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
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			try{
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");
				String result="";
				
				if (request.getParameter("load").equals("all_users")) {
					result = allUsers(request);
				}
				
				if (request.getParameter("load").equals("entrance_price")) {
					result = entrancePrice();
				}
				
				if (request.getParameter("load").equals("sold_product")) {
					result = soldProduct();
				}
				
				if (request.getParameter("load").equals("return_product")) {
					result = returnProduct();
				}
				
				if (request.getParameter("load").equals("directory_product")) {
					result = DirectoryProduct();
				}
				if (request.getParameter("load").equals("edit_provider")) {
					result = editProvider();
				}
				
				if (request.getParameter("load").equals("update")) {
					result = updateUser(request);	
				}
				if (request.getParameter("load").equals("delete")) {
					result = deleteUser(request);
				}
				
				if (request.getParameter("load").equals("newuser")) {
					result = newUser(request);
				}
				
				if (request.getParameter("load").equals("newentrance")) {
					result = newEntrance(request);
				}
				if (request.getParameter("load").equals("show_new_data_sale")) {
					result = windowUpdateMessage(request);
				}
				
				if (request.getParameter("load").equals("inline_directory")) {
					result = inlineDirectory();
				}
				
				if (request.getParameter("load").equals("provider_entrance")) {
					result = providerEntrance();
				}
				
				if (request.getParameter("load").equals("inline_provider")) {
					result = inlineProvider();
				}
				if (request.getParameter("load").equals("new_provider")) {
				 String fullName = request.getParameter("name");
				 String adress = request.getParameter("adress");
				 String tel = request.getParameter("tel");
				 String site = request.getParameter("site");
				 String email = request.getParameter("mail");
				 String description = request.getParameter("description");
					Provider a = new Provider(fullName, adress, tel, site, email, description);
					result = newProvider(a);
				}
				
				if (request.getParameter("load").equals("inline_provider_all")) {
					result = inlineProvidersAll();
				}
				
				if (request.getParameter("load").equals("update_provider")) {
					result = updateProvider(request);
				}
				
				if (request.getParameter("load").equals("reserve_copy")) {
					UtilMyBatis g = new UtilMyBatis();
					try {
						g.reserve(new File(getServletContext().getRealPath("/") + "upload/alex_kras_new_copy_file_for_reserve_copy_dtfgmgorifjgurnfhdyrjfudk.xml"));
						System.out.println(new File(getServletContext().getRealPath("/") + "upload/alex_kras_new_copy_file_for_reserve_copy_dtfgmgorifjgurnfhdyrjfudk.xml"));
						result += "successfull";
					} catch (Exception e) {
						e.printStackTrace();
						result += "error";
					}
					
				}
				
				if (request.getParameter("load").equals("parse_load_reserve_copy")) {
					
					result = readReserve(new File(getServletContext().getRealPath("/") + "upload/admin_load_reserve_file/alex_kras_new_copy_file_for_reserve_copy_dtfgmgorifjgurnfhdyrjfudk.xml"), request);
										
				}
				
				if (request.getParameter("load").equals("download_data_to_db")) {
					
					result = downloadDataToDB(request);
				}

				response.getWriter().write(result);
				} finally{
					out.close();
				}
			
		}
	}

	
	private String updateProvider(HttpServletRequest request){
		String result = "";
		Provider a = new Provider(request.getParameter("fullnameprovider"), request.getParameter("adressprovider"), request.getParameter("telprovider"),
				request.getParameter("siteprovider"), request.getParameter("emailprovider"), request.getParameter("descriptionprovider"));
		dao.updateProvider(a);
		result = editProvider();
		return result;
	}
	
	private String newProvider(Provider a){
		dao.insertNewProvider(a);	
		String result = providerEntrance();
		return result;
	}
	
	private String allUsers(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		String result="";
		
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr bgcolor=\"#00FF00\">";
		result += "<th align=\"center\">ФИО</th>";
		result += "<th align=\"center\">Телефон</th>";
		result += "<th align=\"center\">Уровень доступа</th>";
		result += "<th align=\"center\">Логин</th>";
		result += "<th align=\"center\">Пароль</th>";
		result += "<th align=\"center\">Функция</th>";
		result += "</tr>";
		
		List<User> listUsers = dao.selectAllUsers();
		int n = 0;		
		String fullname = (String) session.getAttribute("fullName"); 
		for(User thisUser : listUsers){ 
				n++;
				result += "<tr align=\"center\">";
				result += "<td><label id=\"fullnametable"+n+"\">"+thisUser.getFullName()+"</label></td>";
				result += "<td><label id=\"teltable"+n+"\">"+thisUser.getTel()+"</label></td>";
				result += "<td><label id=\"statustable"+n+"\">"+thisUser.getStatus()+"</label></td>";
				if(thisUser.getStatus().equals("user") || thisUser.getFullName().equals(fullname) || session.getAttribute("status").equals("adminfull")){
					result += "<td><input type=\"text\" id=\"usernametable"+n+"\" value=\""+thisUser.getUsername()+"\" style=\"font-size : 20px;\" readonly></td>";
				}
				else{
					result += "<td><label id=\"usernametable"+n+"\">"+thisUser.getUsername()+"</label></td>";
				}
				if((thisUser.getStatus().equals("user")) || (session.getAttribute("status").equals("adminfull"))){
					result += "<td><input type=\"text\" name=\"passwordtable\""+n+"\" id=\"passwordtable"+n+"\" value=\""+thisUser.getPassword()+"\" style=\"font-size : 20px;\"></td>";
				}
				else{
					if(thisUser.getFullName().equals(fullname)){
						result += "<td><input type=\"text\" name=\"passwordtable\""+n+"\" id=\"passwordtable"+n+"\" value=\""+thisUser.getPassword()+"\" style=\"font-size : 20px;\"></td>";
					}
					else{
						result += "<td>*************</td>";
					}
				}
				result += "<td>";
				if(thisUser.getStatus().equals("user") || session.getAttribute("status").equals("adminfull")){
					result += "<input type=\"submit\" align=\"middle\" value=\"\" id=\"update"+n+"\" name=\"update\" class=\"update\" onclick=\"update_delete(this, 'update');\"/>";
					result += "<input type=\"submit\" align=\"middle\" value=\"\" id=\"delete"+n+"\" name=\"delete\" class=\"delete\" onclick=\"update_delete(this, 'delete');\"/>";
				}
				else{
					if(thisUser.getFullName().equals(fullname)){
						result += "<input type=\"submit\" align=\"middle\" value=\"\" id=\"update"+n+"\" name=\"update\" class=\"update\" onclick=\"update_delete(this, 'update');\"/>";
					}
					else{
						result += "Нет доступа!!!";
					}
				}
				result += "</td>";
				result += "</tr>";
		}
		result += "</table>";
	
		return result;
	}
	
	private String entrancePrice(){
		String result = "";
		double potracheno = 0;
		List<EntrancePrice> listEntrance = dao.selectAllEntrancePrice();
		if(listEntrance.size() != 0){
			result += "<table border=3 align=\"center\" class=\"table sortable\">";
			result += "<tr bgcolor=\"#00FF00\">";
			result += "<th align=\"center\">Имя<br />товара</th>";
			result += "<th align=\"center\">Фирма</th>";
			result += "<th align=\"center\">Артикул</th>";
			result += "<th align=\"center\">Кол-во</th>";
			result += "<th align=\"center\">Цена<br>закупки</th>";
			result += "<th align=\"center\">Сумма</th>";
			result += "<th align=\"center\">Цена<br>продажи</th>";
			result += "<th align=\"center\">Дата покупки</th>";
			result += "<th align=\"center\">Поставщик</th>";
			result += "</tr>";
			List<Provider> listProvider = dao.selectAllProvides();
			for(EntrancePrice list : listEntrance){
				potracheno +=(list.getCostEntrance()*list.getNumber());
				result += "<tr><td>"+list.getName()+"</td>";
				result += "<td>"+list.getFirm()+"</td>";
				result += "<td>"+list.getArticle()+"</td>";
				result += "<td>"+list.getNumber()+"</td>";
				result += "<td>"+list.getCostEntrance()+"</td>";
				result += "<td>"+(list.getCostEntrance()*list.getNumber())+"</td>";
				result += "<td>"+list.getCostOutPut()+"</td>";
				result += "<td>"+list.toStirngTimeUser()+"</td>";
				for(Provider i: listProvider){
					if(list.getProvider().equals(i.getFullName())){
						result += "<td><a class=\"modalbox\" href=\"#inline_providersAll"+i.getId()+"\">"+list.getProvider()+"</a></td>";
					}
				}
				result += "</tr>";
			}
			result += "</table><center><label>На покупку товара потрачено: "+potracheno+" грн.</label></center>";
		}
		else{
			result += "<p align=\"center\">Отсутствует соединение с БД - проверьте Ваше подключение к сети Интернет</p>";
		}
		
		return result;
	}
	
	private String soldProduct(){
		String result = "";
		Calendar c = Calendar.getInstance();
		Timestamp t = new Timestamp(c.getTimeInMillis());
		List<SoldProduct> listSold = dao.selectAllSoldProduct();
		if(listSold.size() != 0){
			result += "<table border=3 align=\"center\" class=\"table sortable\">";
			result += "<tr bgcolor=\"#00FF00\">";
			result += "<th align=\"center\">Имя<br />товара</th>";
			result += "<th align=\"center\">Фирма</th>";
			result += "<th align=\"center\">Артикул</th>";
			result += "<th align=\"center\">Кол-во</th>";
			result += "<th align=\"center\">Цена<br />продажи</th>";
			result += "<th align=\"center\">Скидка</th>";
			result += "<th align=\"center\">Сумма</th>";
			result += "<th align=\"center\">Время<br />Продажи</th>";
			result += "<th align=\"center\">Кто<br />продал/списал?</th>";
			result += "</tr>";
			double potracheno = 0;
			double discount = 0;
			for(SoldProduct list : listSold){
				potracheno += list.getSum();
				discount += list.getDiscount();
				if(list.getTime().getDate() == t.getDate()){
					result += "<tr style=\"background-color: #00FFFF;\">";
					result += "<td>"+list.getName()+"</td>";
					result += "<td>"+list.getFirm()+"</td>";
					result += "<td>"+list.getArticle()+"</td>";
					result += "<td>"+list.getNumber()+"</td>";
					result += "<td>"+list.getCostOutPut()+"</td>";
					result += "<td>"+list.getDiscount()+"</td>";
					result += "<td>"+list.getSum()+"</td>";
					result += "<td>"+list.toStirngTimeUser()+"</td>";
					result += "<td>"+list.getNameUserAdmin()+"</td>";
					result += "</tr>";
				}
				else{
					result += "<tr>";
					result += "<td>"+list.getName()+"</td>";
					result += "<td>"+list.getFirm()+"</td>";
					result += "<td>"+list.getArticle()+"</td>";
					result += "<td>"+list.getNumber()+"</td>";
					result += "<td>"+list.getCostOutPut()+"</td>";
					result += "<td>"+list.getDiscount()+"</td>";
					result += "<td>"+list.getSum()+"</td>";
					result += "<td>"+list.toStirngTimeUser()+"</td>";
					result += "<td>"+list.getNameUserAdmin()+"</td>";
					result += "</tr>";
				}
			}
			result += "</table>";
			result += "<center><label>Проданно на сумму: "+potracheno+"</label></center>";
			result += "<center><label>Сделанная скидка: "+discount+"</label></center>";
		}else{
			result += "<p align=\"center\">Отсутствует соединение с БД - проверьте Ваше подключение к сети Интернет</p>";
		}
		return result;
	}
	
	private String returnProduct(){
		String result = "";
		
		List<ReturnOrWriteOff> listReturn = dao.selectAllReturnOrWriteOff();
		if(listReturn.size() != 0){
			result += "<table border=3 align=\"center\" class=\"table sortable\">";
			result += "<tr bgcolor=\"#00FF00\">"; 
			result += "	<th align=\"center\">Имя<br />товара</th>";
			result += "<th align=\"center\">Фирма</th>";
			result += "<th align=\"center\">Артикул</th>";
			result += "<th align=\"center\">Кол-во</th>";
			result += "<th align=\"center\">Цена<br>продажи</th>";
			result += "<th align=\"center\">Время</th>";
			result += "<th align=\"center\">СПИСАНИЕ<br>ВОЗВРАТ</th>";
			result += "<th align=\"center\">Кто сделал ?</th>";
			result += "<th align=\"center\">Для кого<br>сделано</th>";
			result += "<th align=\"center\">Поставщик</th>";
			result += "</tr>";
			List<Provider> listProvider = dao.selectAllProvides();
			
			for(ReturnOrWriteOff li : listReturn){
				result += "<tr>";
				result += "<td>"+li.getName()+"</td>";
				result += "<td>"+li.getFirm()+"</td>";
				result += "<td>"+li.getArticle()+"</td>";
				result += "<td>"+li.getNumber()+"</td>";
				result += "<td>"+li.getCostOutPut()+"</td>";
				result += "<td>"+li.toStirngTimeUser()+"</td>";
				result += "<td>"+li.getDescription()+"</td>";
				result += "<td>"+li.getNameUserAdmin()+"</td>";
				result += "<td>"+li.getDescriptionForWhom()+"</td>";
				for(Provider i: listProvider){
					if(li.getProvider().equals(i.getFullName())){
						result += "<td><a class=\"modalbox\" href=\"#inline_providersAll"+i.getId()+"\">"+li.getProvider()+"</a></td>";
					}
				}
				result += "</tr>";
			}
			result += "</table>";
		}
		
		
		
		else{
			result += "<p align=\"center\">Отсутствует соединение с БД - проверьте Ваше подключение к сети Интернет</p>";
		}
		
		return result;
	}
	
	private String DirectoryProduct(){
		
		String result = "";
		List<DirectoryProduct> listDirectory = dao.selectAllDirectoryProduct();
		if(listDirectory.size() != 0){
			result += "<table border=3 align=\"center\" class=\"table sortable\">";
			result += "<tr bgcolor=\"#00FF00\">"; 
			result += "<th align=\"center\">Имя<br>товара</th>";
			result += "<th align=\"center\">Фирма</th>";
			result += "<th align=\"center\" axis=\"str\">Артикул</th>";
			result += "<th align=\"center\">Кол-во<br><a class=\"modalbox\" href=\"#inline_count\">Учет</a></th>";
			result += "<th align=\"center\">Цена<br>продажи</th>";
			result += "<th align=\"center\">Время<br>Обновления</th>";
			result += "<th align=\"center\">История</th>";
			result += "</tr>";
			
			List<Provider> listProvider = dao.selectAllProvides();
			
			
			int kDirectory=0;
			for(DirectoryProduct list : listDirectory){
				kDirectory = kDirectory + list.getNumber();
				
				result += "<tr>";
				result += "<td>"+list.getName()+"</td>";
				result += "<td>"+list.getFirm()+"</td>";
				result += "<td>"+list.getArticle()+"</td>";
				result += "<td>"+list.getNumber()+"</td>";
				result += "<td>"+list.getCostOutPut()+"</td>";
				result += "<td>"+list.toStirngTimeUser()+"</td>";
				result += "<td>";
				String line = list.getStory();
				String rez="";
				boolean allow = true;
				while(allow){
					line = line.substring(line.indexOf("@")+1, line.length());
					rez = line.substring(0, line.indexOf("@"))+" грн.|";
					line = line.substring(line.indexOf("@")+1, line.length());
					rez += line.substring(0, line.indexOf("@"))+" шт.|";
					line = line.substring(line.indexOf("@")+1, line.length());
					rez += line.substring(0, line.indexOf("@"))+"|";
					line = line.substring(line.indexOf("@")+1, line.length());
					for(Provider i: listProvider){
						if(line.substring(0, line.indexOf("@")).equals(i.getFullName())){
							rez += "<a class=\"modalbox\" href=\"#inline_providersAll"+i.getId()+"\">"+line.substring(0, line.indexOf("@"))+"</a>";
							line = line.substring(line.indexOf("@")+1, line.length());
							break;
						}
					}
					
				result += rez;
					if(line.indexOf("@") == -1){
							allow = false;
					}
					else{
						result += "<br />";
					}
				}
				result += "</td>";
				
				result += "</tr>";
			}			
			result += "</table>";
			result += "<br /><br />";
			result += "<p align=\"center\"><label>Общее количество товара в магазине - "+kDirectory+"</label></p>";
			
			
		}else{
			result += "<p align=\"center\">Отсутствует соединение с БД - проверьте Ваше подключение к сети Интернет</p>";
		}
		
		return result;
	}
	
	private String editProvider(){
		String result="";
		
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr border-style: ridge; bgcolor=\"#00FF00\">";
		result += "<th colspan=\"2\" align=\"center\">Информация</th>";
		result += "<th align=\"center\">Описание</th>";
		result += "<th align=\"center\">Функция</th>";
		result += "</tr>";
		
		List<Provider> listProviders = dao.selectAllProvides();
		int n = 0;		
		for(Provider i : listProviders){ 
				n++;
				result += "<tr  align=\"center\">";
				result += "<tr rowspan=\"2\">";
				result += "<td align=\"center\">ФИО</td>";
				result += "<td><input type=\"text\" id=\"fullnameprovider"+n+"\" value=\""+i.getFullName()+"\" class=\"txt6\" readonly></td>";
				result += "<td rowspan=\"5\"><textarea id=\"descriptionprovider"+n+"\" name=\"descriptionprovider"+n+"\" class=\"txtarea_edit\" style=\"font-size: 10;\">"+i.getDescription()+"</textarea></td>";
				result += "<td rowspan=\"5\"><input type=\"submit\" align=\"middle\" value=\"\" id=\"updateprovider"+n+"\" name=\"update\" class=\"update\" onclick=\"update_provider(this);\"/></td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">Адрес</td>";
				result += "<td><input type=\"text\" id=\"adressprovider"+n+"\" value=\""+i.getAdress()+"\" class=\"txt6\"></td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">Телефон</td>";
				result += "<td><input type=\"text\" id=\"telprovider"+n+"\" value=\""+i.getTel()+"\" class=\"txt6\" onkeydown=\"javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8) || (event.keyCode == 9) || (event.keyCode == 64))\">";
				result += "</td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">Сайт</td>";
				result += "<td><input type=\"text\" id=\"siteprovider"+n+"\" value=\""+i.getSite()+"\" class=\"txt6\"></td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">E-mail</td>";
				result += "<td><input type=\"text\" name=\"emailprovider\""+n+"\" id=\"emailprovider"+n+"\" value=\""+i.getEmail()+"\" class=\"txt6\"></td>";
				result += "</tr>";
				result += "</tr>";
				
				if(n != listProviders.size()){
					result += "<tr style=\"font-size: 10;\"><td><br></td><td>(другой номер писать через символ @)</td><td><br></td></tr>";
				}
				
		}
		result += "</table>";
		return result;
	}
	
	private String newEntrance(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		String nameEntrance = request.getParameter("name_entrance");
		String firmEntrance = request.getParameter("firm_entrance");
		String providerEntrance = request.getParameter("provider_entrance");
		System.out.println(providerEntrance);
		String articleEntrance = request.getParameter("article_entrance");
		String numberEntrance = request.getParameter("number_entrance");
		String costEntrance = request.getParameter("cost_entrance");
		String costOutPutEntrance = request.getParameter("cost_out_put_entrance");
		String timeEntrance = request.getParameter("time_entrance");
		String nameAdmin = (String)session.getAttribute("fullName");
		Timestamp time = Timestamp.valueOf(timeEntrance);
		EntrancePrice a = new EntrancePrice(nameEntrance, firmEntrance, providerEntrance, articleEntrance, Integer.parseInt(numberEntrance), Double.parseDouble(costEntrance), Double.parseDouble(costOutPutEntrance), time, nameAdmin);
		dao.insertNewEntrancePrice(a);
		String result = entrancePrice();
		 
		return result;
	}

	private String newUser(HttpServletRequest request){
		
		String fullname = request.getParameter("three_name") + " " + request.getParameter("first_name") + " " + request.getParameter("second_name");
		String tel = request.getParameter("tel");
		String username = request.getParameter("username_registration");
		String password = request.getParameter("password_registration");
		String status = "user";
		Encryption md5 = new Encryption(username, password);
		String hash = md5.encryptionMD5();
		User nUser = new User(fullname, tel, status, username, password, hash);
		dao.insertUser(nUser);
		nUser = null;
		String result = allUsers(request);
	
		return result;
	}
	
	private String deleteUser(HttpServletRequest request){
		
		String username = request.getParameter("username");
		User us = new User();
		us.setUsername(username);
		dao.deleteUser(us);
		String result = allUsers(request);
		
		return result;
	}
	
	private String updateUser(HttpServletRequest request){
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User us = new User();
		us.setUsername(username);
		us.setPassword(password);
		dao.updateUser(us);
		String result = allUsers(request);
		
		return result;
	}
	
	private String windowUpdateMessage(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		
		int sold = 0;
		List<SoldProduct> sp_old  = (List<SoldProduct>) session.getAttribute("listSold");
		List<SoldProduct> sp_new  = dao.selectAllSoldProduct();
		if((sp_new.size() - sp_old.size()) > 0){
			if((int)sp_new.get(0).getCostOutPut() != (int)sp_new.get(0).getDiscount()){
				sold = (sp_new.size() - sp_old.size());
			}
		}
		else{
			if((sp_new.size() - sp_old.size()) < 0){
				sold = (sp_new.size() - sp_old.size());
			}
		}
		session.removeAttribute("listSold");
		session.setAttribute("listSold", sp_new);
		int prewrite = 0;
		List<ReturnOrWriteOff> rp_old  = (List<ReturnOrWriteOff>) session.getAttribute("listReturn");
		List<ReturnOrWriteOff> rp_new  = dao.selectAllReturnOrWriteOff();
		
		if((rp_new.size() - rp_old.size()) > 0){
			int num = (rp_new.size() - rp_old.size());
			while(num-1 >= 0){
				if(rp_new.get(num-1).getDescription().equals("Списание Товара")){
					prewrite ++;
				}
				num--;
			}
		}
		session.removeAttribute("listReturn");
		session.setAttribute("listReturn", rp_new);
		String result = "";
		if(sold != 0){
			if(sold > 0){
				result += "<sold>"+sold+"</sold><return>0</return><prewrite>"+prewrite+"</prewrite>";
			}
			else{
				result += "<sold>0</sold><return>"+Math.abs(sold)+"</return><prewrite>"+prewrite+"</prewrite>";
			}
		}
		else{
			result += "<sold>0</sold><return>0</return><prewrite>"+prewrite+"</prewrite>";
		}
		return result;
	}
	
	private String inlineDirectory(){
		String result = "";
		int n = 0;
		List<DirectoryProduct> listDirectory = dao.selectAllDirectoryProduct();
		if(listDirectory.size() != 0){
			result += "<div id=\"inline\" class=\"inline\">";
			result += "<form id=\"contact\" name=\"contact\" action=\"#\" method=\"post\">";
			result += "<table border=3 align=\"center\" class=\"table sortable\">";
			result += "<tr bgcolor=\"#00FF00\">"; 
			result += "<th align=\"center\">Имя<br>товара</th>";
			result += "<th align=\"center\">Фирма</th>";
			result += "<th align=\"center\">Поставщик</th>";
			result += "<th align=\"center\">Артикул</th>";
			result += "<th align=\"center\">Добавить</th>";
			result += "</tr>";

			for(DirectoryProduct list : listDirectory){
				n++;
				result += "<tr>";
				List<Provider> listProvider = dao.selectAllProvides();
				result += "<td id=\"name_click"+n+"\">"+list.getName()+"</td>";
				result += "<td id=\"firm_click"+n+"\">"+list.getFirm()+"</td>";
				for(Provider i: listProvider){
					if(list.getProvider().equals(i.getFullName())){
						result += "<td id=\"provider_click"+n+"\"><a class=\"modalbox\" href=\"#inline_providersAll"+i.getId()+"\">"+list.getProvider()+"</a><input type=\"hidden\" id=\"provider_click_hidden"+n+"\" value=\""+i.getId()+"\"/></td>";

					}
				}
				result += "<td id=\"article_click"+n+"\">"+list.getArticle()+"</td>";
				result += "<td><center><button id=\"send"+n+"\" class=\"send\" name=\"send"+n+"\" onclick=\"return add_click_inline(this);\">+</button></center></td>";
				result += "</tr>";
			}
			result += "</table</form></div>";	
			
			
		}
		else{
			result += "<div id=\"inline\" class=\"inline\">";
			result += "<form id=\"contact\" name=\"contact\" action=\"#\" method=\"post\">";
			result += "В базе данных отсутствуют записи о товарах. Добавьте новый товар";
			result += "</form></div>";
			
		}
		
		return result;
	}
	
	private String inlineProvider(){
		String result = "";
		int n = 0;
		result += "<div id=\"create_inline_provider\" class=\"inline\">";
		result += "<form id=\"contact\" name=\"contact\" action=\"#\" method=\"post\">";
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		
		result += "<label for=\"name_provider_inline\" >ФИО:</label>";
		result += "<input type=\"text\" id=\"name_provider_inline\"  class=\"txt1\" >";
		result += "<br><label for=\"adress_provider_inline\">Адрес:</label>";
		result += "<input type=\"text\" id=\"adress_provider_inline\" class=\"txt2\" >";
		result += "<br><label for=\"tel_provider_inline\">Телефон:</label>";
		result += "<input type=\"text\" id=\"tel_provider_inline\" class=\"txt3\" onkeydown=\"javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8) || (event.keyCode == 9))\">";
		result += "<br><label for=\"site_provider_inline\" >Сайт:</label>";
		result += "<input type=\"text\" id=\"site_provider_inline\" class=\"txt4\" >";
		result += "<br><label for=\"mail_provider_inline\" >e-mail:</label>";
		result += "<input type=\"text\" id=\"mail_provider_inline\" class=\"txt5\" >";
		result += "<br><label for=\"description_provider_inline\" >доп. инфо.:</label>";
		result += "<textarea id=\"description_provider_inline\" name=\"description_provider_inline\" class=\"txtarea\" ></textarea>";
		result += "<br><center>";    
		result += "<button id=\"send_provider_inline\" class=\"send\" onclick=\"return newProvider_create();\">";
		result += "Записать нового Поставщика в БД</button></center></table></form></div>";
		return result;
	}
	
	private String providerEntrance(){
		String result = "";
		List<Provider> listProviders = dao.selectAllProvides();
		
		if(listProviders.size() != 0){
			result += "<option value='0'>Выберите Поставщика</option>";
			for(Provider list : listProviders){
				result += "<option value='"+list.getId()+"'>"+list.getFullName()+"</option>";
			}	
		}
		else{
			result += "<option value='0'>Добавьте Поставщиков</option>";
		}
		return result;
	}
	
	private String inlineProvidersAll(){
		String result = "";
		int n = 0;
		List<Provider> listProvider = dao.selectAllProvides();
		if(listProvider.size() != 0){
			for(Provider i : listProvider){
				result += "<div id=\"inline_providersAll"+i.getId()+"\" class=\"inline\">";
				result += "<form id=\"contact\" name=\"contact\" action=\"#\" method=\"post\">";
				result += "<table><tr><td>";
				result += "<label for=\"name_provider_inline_all\" >ФИО:</label>";
				result += "<input type=\"text\" id=\"name_provider_inline_all\" value=\""+i.getFullName()+"\" class=\"txt1\" readonly></td></tr>";
				result += "<tr><td><label for=\"adress_provider_inline_all\">Адрес:</label>";
				result += "<input type=\"text\" id=\"adress_provider_inline_all\" value=\""+i.getAdress()+"\" class=\"txt2\" readonly></td></tr>";
				result += "<tr><td><label for=\"tel_provider_inline_all\">Телефон:</label>";
				result += "<input type=\"text\" id=\"tel_provider_inline_all\" value=\""+i.getTel()+"\" class=\"txt3\" readonly></td></tr>";
				result += "<tr><td><label for=\"site_provider_inline_all\" >Сайт:</label>";
				result += "<input type=\"text\" id=\"site_provider_inline_all\" value=\""+i.getSite()+"\" class=\"txt4\" readonly></td></tr>";
				result += "<tr><td><label for=\"mail_provider_inline_all\" >e-mail:</label>";
				result += "<input type=\"text\" id=\"mail_provider_inline_all\" value=\""+i.getEmail()+"\" class=\"txt5\" readonly></td></tr>";
				result += "<tr><td><label for=\"description_provider_inline\" >доп. инфо.:</label>";
				result += "<textarea id=\"description_provider_inline_all\" name=\"description_provider_inline_all\" class=\"txtarea\" readonly>"+i.getDescription()+"</textarea></td></tr>";
				result += "</table></form></div>";
			}
		}
		
		return result;
	}

	
	private String readReserve(File f, HttpServletRequest request){
		
		String result = "";
		result += "<input type=\"submit\" onclick=\"recoveryTrue();\" value='Востановить все данные в базу' style=\"width: 100%; font-size: 35px;\">";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document doc = null;
		try {
			documentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = documentBuilder.parse(f);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		String titleEl = doc.getDocumentElement().getNodeName();
		
		NodeList nodeList = doc.getElementsByTagName("productEntrance");
		String ti = "";
		List<EntrancePrice> listEntranceReserve = new ArrayList<EntrancePrice>();
		for(int i = 0; i<nodeList.getLength(); i++){
			Element element = (Element) nodeList.item(i);
			EntrancePrice a = new EntrancePrice();
			a.setName(element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			a.setFirm(element.getElementsByTagName("firm").item(0).getChildNodes().item(0).getNodeValue());
			a.setProvider(element.getElementsByTagName("provider").item(0).getChildNodes().item(0).getNodeValue());
			a.setArticle(element.getElementsByTagName("article").item(0).getChildNodes().item(0).getNodeValue());
			a.setNumber(Integer.parseInt(element.getElementsByTagName("number").item(0).getChildNodes().item(0).getNodeValue()));
			a.setCostEntrance(Double.parseDouble(element.getElementsByTagName("costEntrance").item(0).getChildNodes().item(0).getNodeValue()));
			a.setCostOutPut(Double.parseDouble(element.getElementsByTagName("costOutPut").item(0).getChildNodes().item(0).getNodeValue()));
			ti = element.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue();
			Timestamp time = Timestamp.valueOf(ti);
			a.setTime(time);
			a.setNameAdmin(element.getElementsByTagName("nameAdmin").item(0).getChildNodes().item(0).getNodeValue());
			listEntranceReserve.add(a);		
		}		
		for(EntrancePrice i : listEntranceReserve){
			System.out.println(i.toStirng());
		}
		
		nodeList = doc.getElementsByTagName("productSold");
	
		List<SoldProduct> listSoldProductReserve = new ArrayList<SoldProduct>();
		int idNew = 0;
		for(int i = 0; i<nodeList.getLength(); i++){
			idNew++;
			Element element = (Element) nodeList.item(i);
			SoldProduct a = new SoldProduct();
			a.setId(idNew);
			a.setName(element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			a.setFirm(element.getElementsByTagName("firm").item(0).getChildNodes().item(0).getNodeValue());
			a.setArticle(element.getElementsByTagName("article").item(0).getChildNodes().item(0).getNodeValue());
			a.setNumber(Integer.parseInt(element.getElementsByTagName("number").item(0).getChildNodes().item(0).getNodeValue()));
			a.setCostOutPut(Double.parseDouble(element.getElementsByTagName("costOutPut").item(0).getChildNodes().item(0).getNodeValue()));
			a.setDiscount(Double.parseDouble(element.getElementsByTagName("discount").item(0).getChildNodes().item(0).getNodeValue()));
			a.setSum(Double.parseDouble(element.getElementsByTagName("sum").item(0).getChildNodes().item(0).getNodeValue()));
			ti = element.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue();
			Timestamp time = Timestamp.valueOf(ti);
			a.setTime(time);
			a.setNameUserAdmin(element.getElementsByTagName("nameUserAdmin").item(0).getChildNodes().item(0).getNodeValue());
			listSoldProductReserve.add(a);		
		}	
		
		for(SoldProduct i : listSoldProductReserve){
			System.out.println(i.toStirng());
		}
		
		nodeList = doc.getElementsByTagName("productDirectory");		
		List<DirectoryProduct> listDirectoryProductReserve = new ArrayList<DirectoryProduct>();
		for(int i = 0; i<nodeList.getLength(); i++){
			Element element = (Element) nodeList.item(i);
			DirectoryProduct a = new DirectoryProduct();
			a.setName(element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			a.setFirm(element.getElementsByTagName("firm").item(0).getChildNodes().item(0).getNodeValue());
			a.setProvider(element.getElementsByTagName("provider").item(0).getChildNodes().item(0).getNodeValue());
			a.setArticle(element.getElementsByTagName("article").item(0).getChildNodes().item(0).getNodeValue());
			a.setNumber(Integer.parseInt(element.getElementsByTagName("number").item(0).getChildNodes().item(0).getNodeValue()));
			a.setCostOutPut(Double.parseDouble(element.getElementsByTagName("costOutPut").item(0).getChildNodes().item(0).getNodeValue()));
			ti = element.getElementsByTagName("timeUpdate").item(0).getChildNodes().item(0).getNodeValue();
			Timestamp time = Timestamp.valueOf(ti);
			a.setTimeUpdate(time);
			a.setStory(element.getElementsByTagName("story").item(0).getChildNodes().item(0).getNodeValue());
			a.setNameAdmin(element.getElementsByTagName("nameAdmin").item(0).getChildNodes().item(0).getNodeValue());
			listDirectoryProductReserve.add(a);		
		}
		for(DirectoryProduct i : listDirectoryProductReserve){
			System.out.println(i.toStirng());
		}
			
		nodeList = doc.getElementsByTagName("Provider");		
		List<Provider> listProviderReserve = new ArrayList<Provider>();
		for(int i = 0; i<nodeList.getLength(); i++){
			Element element = (Element) nodeList.item(i);
			Provider a = new Provider();
			a.setId(Integer.parseInt(element.getElementsByTagName("idl").item(0).getChildNodes().item(0).getNodeValue()));
			a.setFullName(element.getElementsByTagName("fullname").item(0).getChildNodes().item(0).getNodeValue());
			a.setAdress(element.getElementsByTagName("adress").item(0).getChildNodes().item(0).getNodeValue());
			a.setTel(element.getElementsByTagName("tel").item(0).getChildNodes().item(0).getNodeValue());
			a.setSite(element.getElementsByTagName("site").item(0).getChildNodes().item(0).getNodeValue());
			a.setEmail(element.getElementsByTagName("email").item(0).getChildNodes().item(0).getNodeValue());
			a.setDescription(element.getElementsByTagName("description").item(0).getChildNodes().item(0).getNodeValue());
			listProviderReserve.add(a);		
		}	
		for(Provider i : listProviderReserve){
			System.out.println(i.toStirng());
		}
				
		nodeList = doc.getElementsByTagName("ReturnOrWriteOff");		
		List<ReturnOrWriteOff> listReturnOrWriteOffReserve = new ArrayList<ReturnOrWriteOff>();
		for(int i = 0; i<nodeList.getLength(); i++){
			Element element = (Element) nodeList.item(i);
			ReturnOrWriteOff a = new ReturnOrWriteOff();
			a.setName(element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
			a.setFirm(element.getElementsByTagName("firm").item(0).getChildNodes().item(0).getNodeValue());
			a.setProvider(element.getElementsByTagName("provider").item(0).getChildNodes().item(0).getNodeValue());
			a.setArticle(element.getElementsByTagName("article").item(0).getChildNodes().item(0).getNodeValue());
			a.setNumber(Integer.parseInt(element.getElementsByTagName("number").item(0).getChildNodes().item(0).getNodeValue()));
			a.setCostOutPut(Double.parseDouble(element.getElementsByTagName("costOutPut").item(0).getChildNodes().item(0).getNodeValue()));
			ti = element.getElementsByTagName("timeUpdate").item(0).getChildNodes().item(0).getNodeValue();
			Timestamp time = Timestamp.valueOf(ti);
			a.setTime(time);
			a.setNameUserAdmin(element.getElementsByTagName("nameUserAdmin").item(0).getChildNodes().item(0).getNodeValue());
			a.setDescription(element.getElementsByTagName("description").item(0).getChildNodes().item(0).getNodeValue());
			a.setDescriptionForWhom(element.getElementsByTagName("descriptionForWhom").item(0).getChildNodes().item(0).getNodeValue());
			listReturnOrWriteOffReserve.add(a);		
		}	
		for(ReturnOrWriteOff i : listReturnOrWriteOffReserve) {
			System.out.println(i.toStirng());
		}
		
		f.delete();
		
		
		result += "<br><br><br><p align=\"center\">Таблица - накладная по входному товару</p><br>";
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr bgcolor=\"#00FF00\">";
		result += "<th align=\"center\">Имя<br />товара</th>";
		result += "<th align=\"center\">Фирма</th>";
		result += "<th align=\"center\">Артикул</th>";
		result += "<th align=\"center\">Кол-во</th>";
		result += "<th align=\"center\">Цена<br>закупки</th>";
		result += "<th align=\"center\">Сумма</th>";
		result += "<th align=\"center\">Цена<br>продажи</th>";
		result += "<th align=\"center\">Дата покупки</th>";
		result += "<th align=\"center\">Поставщик</th>";
		result += "</tr>";
		for(EntrancePrice i : listEntranceReserve){
			result += "<tr><td>"+i.getName()+"</td>";
			result += "<td>"+i.getFirm()+"</td>";
			result += "<td>"+i.getArticle()+"</td>";
			result += "<td>"+i.getNumber()+"</td>";
			result += "<td>"+i.getCostEntrance()+"</td>";
			result += "<td>"+(i.getNumber()*i.getCostEntrance())+"</td>";
			result += "<td>"+i.getCostOutPut()+"</td>";
			result += "<td>"+i.toStirngTimeUser()+"</td>";
			result += "<td>"+i.getProvider()+"</td>";
			result += "</tr>";
		}

		result += "</table><br><br><br><p align=\"center\">Таблица Проданных товаров</p><br>";
		
		
		
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr bgcolor=\"#00FF00\">";
		result += "<th align=\"center\">Имя<br />товара</th>";
		result += "<th align=\"center\">Фирма</th>";
		result += "<th align=\"center\">Артикул</th>";
		result += "<th align=\"center\">Кол-во</th>";
		result += "<th align=\"center\">Цена<br />продажи</th>";
		result += "<th align=\"center\">Скидка</th>";
		result += "<th align=\"center\">Сумма</th>";
		result += "<th align=\"center\">Время<br />Продажи</th>";
		result += "<th align=\"center\">Кто<br />продал/списал?</th>";
		result += "</tr>";
		for(SoldProduct list : listSoldProductReserve){
			result += "<tr style=\"background-color: #00FFFF;\">";
			result += "<td>"+list.getName()+"</td>";
			result += "<td>"+list.getFirm()+"</td>";
			result += "<td>"+list.getArticle()+"</td>";
			result += "<td>"+list.getNumber()+"</td>";
			result += "<td>"+list.getCostOutPut()+"</td>";
			result += "<td>"+list.getDiscount()+"</td>";
			result += "<td>"+list.getSum()+"</td>";
			result += "<td>"+list.toStirngTimeUser()+"</td>";
			result += "<td>"+list.getNameUserAdmin()+"</td>";
			result += "</tr>";		
		}
		result += "</table><br><br><br><p align=\"center\">Таблица - Справочник товаров</p><br>";
		
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr bgcolor=\"#00FF00\">"; 
		result += "<th align=\"center\">Имя<br>товара</th>";
		result += "<th align=\"center\">Фирма</th>";
		result += "<th align=\"center\">Артикул</th>";
		result += "<th align=\"center\">Кол-во</th>";
		result += "<th align=\"center\">Цена<br>продажи</th>";
		result += "<th align=\"center\">Время<br>Обновления</th>";
		result += "<th align=\"center\">История</th>";
		result += "<th align=\"center\">Поставщик</th>";
		result += "</tr>";
		
		for(DirectoryProduct list : listDirectoryProductReserve){
			
			result += "<tr>";
			result += "<td>"+list.getName()+"</td>";
			result += "<td>"+list.getFirm()+"</td>";
			result += "<td>"+list.getArticle()+"</td>";
			result += "<td>"+list.getNumber()+"</td>";
			result += "<td>"+list.getCostOutPut()+"</td>";
			result += "<td>"+list.toStirngTimeUser()+"</td>";
			result += "<td>";
			String line = list.getStory();
			String rez="";
			boolean allow = true;
			while(allow){
				line = line.substring(line.indexOf("@")+1, line.length());
				rez = line.substring(0, line.indexOf("@"))+" грн.|";
				line = line.substring(line.indexOf("@")+1, line.length());
				rez += line.substring(0, line.indexOf("@"))+" шт.|";
				line = line.substring(line.indexOf("@")+1, line.length());
				rez += line.substring(0, line.indexOf("@"))+"|";
				line = line.substring(line.indexOf("@")+1, line.length());
				rez += line.substring(0, line.indexOf("@"));
				line = line.substring(line.indexOf("@")+1, line.length());
				result += rez;
				if(line.indexOf("@") == -1){
						allow = false;
				}
				else{
					result += "<br />";
				}
			}
			result += "</td>";
			result += "<td>"+list.getProvider()+"</td>";
			result += "</tr>";
		}			
		result += "</table><br><br><br><p align=\"center\">Таблица - Поставщиков</p><br>";
		
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr border-style: ridge; bgcolor=\"#00FF00\">";
		result += "<th colspan=\"2\" align=\"center\">Информация</th>";
		result += "<th align=\"center\">Описание</th>";
		result += "</tr>";
		
			
		for(Provider i : listProviderReserve){ 
				result += "<tr  align=\"center\">";
				result += "<tr rowspan=\"2\">";
				result += "<td align=\"center\">ФИО</td>";
				result += "<td><input type=\"text\" id=\"fullnameprovider\" value=\""+i.getFullName()+"\" class=\"txt6\" readonly></td>";
				result += "<td rowspan=\"5\"><textarea id=\"descriptionprovider\" name=\"descriptionprovider\" class=\"txtarea_edit\" style=\"font-size: 10;\" readonly>"+i.getDescription()+"</textarea></td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">Адрес</td>";
				result += "<td><input type=\"text\" id=\"adressprovider\" value=\""+i.getAdress()+"\" class=\"txt6\" readonly></td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">Телефон</td>";
				result += "<td><input type=\"text\" id=\"telprovider\" value=\""+i.getTel()+"\" class=\"txt6\" readonly>";
				result += "</td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">Сайт</td>";
				result += "<td><input type=\"text\" id=\"siteprovider\" value=\""+i.getSite()+"\" class=\"txt6\" readonly></td>";
				result += "</tr>";
				result += "<tr>";
				result += "<td align=\"center\">E-mail</td>";
				result += "<td><input type=\"text\" name=\"emailprovider\" id=\"emailprovider\" value=\""+i.getEmail()+"\" class=\"txt6\" readonly></td>";
				result += "</tr>";
				result += "</tr>";				
		}
		result += "</table><br><br><br><p align=\"center\">Таблица - Возврата и списания товаров</p><br>";
		
		
		result += "<table border=3 align=\"center\" class=\"table sortable\">";
		result += "<tr bgcolor=\"#00FF00\">"; 
		result += "	<th align=\"center\">Имя<br />товара</th>";
		result += "<th align=\"center\">Фирма</th>";
		result += "<th align=\"center\">Артикул</th>";
		result += "<th align=\"center\">Кол-во</th>";
		result += "<th align=\"center\">Цена<br>продажи</th>";
		result += "<th align=\"center\">Время</th>";
		result += "<th align=\"center\">СПИСАНИЕ<br>ВОЗВРАТ</th>";
		result += "<th align=\"center\">Кто сделал ?</th>";
		result += "<th align=\"center\">Для кого<br>сделано</th>";
		result += "<th align=\"center\">Поставщик</th>";
		result += "</tr>";
		
		for(ReturnOrWriteOff li : listReturnOrWriteOffReserve){
			result += "<tr>";
			result += "<td>"+li.getName()+"</td>";
			result += "<td>"+li.getFirm()+"</td>";
			result += "<td>"+li.getArticle()+"</td>";
			result += "<td>"+li.getNumber()+"</td>";
			result += "<td>"+li.getCostOutPut()+"</td>";
			result += "<td>"+li.toStirngTimeUser()+"</td>";
			result += "<td>"+li.getDescription()+"</td>";
			result += "<td>"+li.getNameUserAdmin()+"</td>";
			result += "<td>"+li.getDescriptionForWhom()+"</td>";
			result += "<td>"+li.getProvider()+"</td>";
			result += "</tr>";
		}
		result += "</table><br><br><br><br>";
		result += "<input type=\"submit\" onclick=\"recoveryTrue();\" value='Востановить все данные в базу' style=\"width: 100%; font-size: 35px;\">";
		
		HttpSession session = request.getSession(true);
		session.setAttribute("listEntranceReserve", listEntranceReserve);
		session.setAttribute("listSoldProductReserve", listSoldProductReserve);
		session.setAttribute("listDirectoryProductReserve", listDirectoryProductReserve);
		session.setAttribute("listProviderReserve", listProviderReserve);
		session.setAttribute("listReturnOrWriteOffReserve", listReturnOrWriteOffReserve);
		return result;
	}
	
	private String downloadDataToDB(HttpServletRequest request){
		String result = "";
		HttpSession session = request.getSession(true);
		List<EntrancePrice> listEntranceReserve  = (List<EntrancePrice>) session.getAttribute("listEntranceReserve");
		List<SoldProduct> listSoldProductReserve  = (List<SoldProduct>) session.getAttribute("listSoldProductReserve");
		List<DirectoryProduct> listDirectoryProductReserve  = (List<DirectoryProduct>) session.getAttribute("listDirectoryProductReserve");
		List<Provider> listProviderReserve  = (List<Provider>) session.getAttribute("listProviderReserve");
		List<ReturnOrWriteOff> listReturnOrWriteOffReserve  = (List<ReturnOrWriteOff>) session.getAttribute("listReturnOrWriteOffReserve");
		dao.deleteAllEntrance();
	
		for(EntrancePrice i : listEntranceReserve){
			dao.insertNewEntrancePrice(i);
		}
		
		dao.deleteAllSoldProduct();
		for(SoldProduct i : listSoldProductReserve){
			dao.insertNewSoldProduct(i);
		}
		
		dao.deleteAllDirectory();
		for(DirectoryProduct i : listDirectoryProductReserve){
			dao.insertNewDirectoryProduct(i);
		}
				
		dao.deleteAllProvider();
		for(Provider i : listProviderReserve){
			dao.insertNewProvider(i);
		}
		
		dao.deleteAllReturnOrWriteOff();
		for(ReturnOrWriteOff i : listReturnOrWriteOffReserve){
			dao.insertNewReturnOrWriteOff(i);
		}
		
		result = "successfull";
		return result;
	}
	
	
}










