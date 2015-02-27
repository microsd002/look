package com.alex.looks.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alex.looks.dao.mybatis.MyBatisConnectionFactory;
import com.alex.looks.dao.mybatis.MyBatisDAO;
import com.alex.looks.models.DirectoryProduct;
import com.alex.looks.models.Provider;
import com.alex.looks.models.ReturnOrWriteOff;
import com.alex.looks.models.SoldProduct;

public class SellProductAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MyBatisDAO dao = new MyBatisDAO(
			MyBatisConnectionFactory.getSqlSessionFactory());	
	String inline = "";
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
				if(session.getAttribute("status").equals("user") || session.getAttribute("status").equals("admin") || session.getAttribute("status").equals("adminfull")){
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
				if (request.getParameter("load").equals("to_sell")) {
					
					result = toSellProduct(request);
				}
				if (request.getParameter("load").equals("return")) {
				
					result = ReturnProduct(request);
				}
				if (request.getParameter("load").equals("resultdirectory")) {
					int n = 0;
					result = allDirectoryProduct(n);
				}
				if (request.getParameter("load").equals("resultinlinedirectory")) {
					result = inline;
					inline = "";
				}
				if (request.getParameter("load").equals("resultsoldthisday")) {
					String n = request.getParameter("numb_last");
					result = soldProductThisDay(Integer.parseInt(n));
				}
				if (request.getParameter("load").equals("resultinlinesold")) {
					result = inline;
					inline = "";
				}
				if (request.getParameter("load").equals("resultsoldproductall")) {
					String n = request.getParameter("numb_last");
					result = soldProductAll(Integer.parseInt(n));
				}
				if (request.getParameter("load").equals("resultinlinesoldall")) {
					result = inline;
					inline = "";
				}
				if (request.getParameter("load").equals("resultinlinecountall")) {
					result = inlineCount();
				}
				
				
				/*if (request.getParameter("load").equals("search_directory")) {
					String str = (request.getParameter("input_search_directory"));
					String typ = (request.getParameter("type"));
					result = searchDirectoryProduct(str, typ);
					
				}*/
				
	
			response.getWriter().write(result);
			} finally{
				out.close();
			}
		}
	}
	
	
	private String inlineCount(){
		String result = "";
		
		List<DirectoryProduct> listDirectory = dao.selectAllDirectoryProduct();
		if(listDirectory.size() != 0){
			result += "<div id=\"inline_count\" class=\"inline\">";
			result += "<form id=\"contact\" name=\"contact\" action=\"#\" method=\"post\">";
			result += "<center>Учёт товара</center><br>";
			result += "<table border=3 align=\"center\" class=\"table sortable\">";
			result += "<tr bgcolor=\"#00FF00\">"; 
			result += "<th align=\"center\">Имя<br>товара</th>";
			result += "<th align=\"center\">Кол-во</th>";
			result += "</tr>";

			List<String> name = new ArrayList<String>();
			name.add(listDirectory.get(0).getName());
			int numb;
			int all = 0;
			for(DirectoryProduct i : listDirectory){
				numb = 0;
				for(String s : name){
					if(!s.equals(i.getName())){
						numb++;
					}
					
				}
				if(numb == name.size()){
					name.add(i.getName());
					numb = 0;
				}
			}
			for(String s : name){
				result += "<tr>";
				result += "<td>"+s+"</td>";
				int kol = 0;
				for(DirectoryProduct i : listDirectory){
					if(i.getName().equals(s)){
						kol += i.getNumber();
						all += i.getNumber();
					}
				}
				result += "<td>"+kol+"</td>";	
				result += "</tr>";
			}
			
		/*	result += "<tr>";
			result += "<td id=\"name_click"+n+"\">"+list.getName()+"</td>";
			result += "<td id=\"firm_click"+n+"\">"+list.getFirm()+"</td>";	
			result += "</tr>";*/
			result += "<tr><td style=\"background: ##1C1C1C;\"><br></td><td style=\"background: ##1C1C1C;\"><br></td></tr>";
			result += "<tr><td>ВСЕГО</td><td>"+all+"</td></tr>";
			result += "</table</form></div>";	
			
			
		}
		else{
			result += "<div id=\"inline\" class=\"inline\">";
			result += "<form id=\"contact\" name=\"contact\" action=\"#\" method=\"post\">";
			result += "<center>В базе данных отсутствуют записи о товарах.<br>Добавьте новый товар</center>";
			result += "</form></div>";
			
		}
		
		return result;
	}
	
	private String allDirectoryProduct(int n) {
		String result = "";
		List<DirectoryProduct> list = dao.selectAllDirectoryProduct();	
		result += "<table border=\"2\" align=\"center\" class=\"table sortable\">";
		result += "<tr>";
		result += "<th align=\"center\">Имя<br>товара</th>";
		result += "<th align=\"center\">Фирма<br></th>";
		result += "<th align=\"center\">Артикул</th>";
		result += "<th align=\"center\">Кол-во<br><a class=\"modalbox\" href=\"#inline_count\">Учет</a></th>";
		result += "<th align=\"center\">Цена</th>";
		result += "<th align=\"center\">Продать<input type=\"hidden\" id=\"numb_last1\" value=\""+list.size()+"\"/></th>";
		result += "</tr>";
		
		result += "<tr>";
		result += "<td align=\"center\">";
	/*	result += "<form name=\"search_form_directory\" action=\"#\" method=\"post\">";
		result += "<input type='text' id=\"input_search_directory_name\" AUTOCOMPLETE=\"off\" />";	
		result += "<input type=\"submit\" style=\"display: none;\" id='submit_directory_name' onclick=\"return search_directory(\"name\" );\" name='login'/>";
		result += "</form>";*/
		result += "</td>";
		result += "<td align=\"center\">";
/*		result += "<form name=\"search_form_directory\" action=\"#\" method=\"post\">";
		result += "<input type='text' id=\"input_search_directory_firm\" AUTOCOMPLETE=\"off\" />";	
		result += "<input type=\"submit\" style=\"display: none;\" id='submit_directory_firm' onclick=\"return search_directory(\"firm\" );\" name='login'/>";
		result += "</form>";
		result += "</td>";*/
		result += "<td align=\"center\">";
/*		result += "<form name=\"search_form_directory\" action=\"#\" method=\"post\">";
		result += "<input type='text' id=\"input_search_directory_article\" AUTOCOMPLETE=\"off\" />";	
		result += "<input type=\"submit\" style=\"display: none;\" id='submit_directory_article' onclick=\"return search_directory(\"article\" );\" name='login'/>";
		result += "</form>";*/
		result += "</td>";
		result += "<td align=\"center\">Количество</td>";
		result += "<td align=\"center\">Цена</td>";
		result += "<td align=\"center\">Продать<input type=\"hidden\" id=\"numb_last1\" value=\""+list.size()+"\"/></td>";
		result += "</tr>";
		
		
		
		for (DirectoryProduct i : list) {
			n++;
			result += "<tr>";
			result += "<td align=\"center\">";
			
			result += i.getName();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getFirm();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getArticle();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getNumber();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getCostOutPut();
			result += "</td>";
			if(i.getNumber() > 0){
				result += "<td><a id=\"link\" class=\"modalbox\" href=\"#inline"+n+"\">Продать</a>";
				result += "</td>";
				newInline(n, i, new SoldProduct(), "directory");
			}
			else{
				result += "<td><a class=\"modalbox\" href=\"javascript:showWarningToast();\">Продать</a>";
				result += "</td>";
			}
			result += "</tr>";
			
		}
		result += "</table>";
		
		return result;
	}	

private String soldProductThisDay(int n){
	
	String result="";
	List<SoldProduct> list = dao.selectAllSoldProduct();
	Calendar c = Calendar.getInstance();
	Timestamp t = new Timestamp(c.getTimeInMillis());
	int great = 0;
	
	result += "<p align=\"center\">История продаж за сегодня</p>";
	result += "<table border=\"2\" align=\"center\" class=\"table sortable\" style=\"table-layout: auto;\">";
	result += "<tr>";
	result += "<th align=\"center\">Имя<br>товара</th>";
	result += "<th align=\"center\">Фирма</th>";
	result += "<th align=\"center\">Артикул</th>";
	result += "<th align=\"center\">Кол-во</th>";
	result += "<th align=\"center\">Цена</th>";
	result += "<th align=\"center\">Скидка</th>";
	result += "<th align=\"center\">Сумма</th>";
	result += "<th align=\"center\">Время продажи</th>";
	result += "<th align=\"center\">Продано<input type=\"hidden\" id=\"numb_last2\" value=\""+(list.size()+n+1)+"\"/></th>";
	result += "</tr>";
	
	
	int discountNumber=0;
	double sum=0;
	double discount=0;
	
		for(SoldProduct i : list){
			if(i.getTime().getDate() == t.getDate()){
				n++;
				great++;
				result += "<tr style=\"width: auto\">";
				result += "<td align=\"center\">";
				result += i.getName();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.getFirm();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.getArticle();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.getNumber();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.getCostOutPut();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.getDiscount();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.getSum();
				result += "</td>";
				result += "<td align=\"center\">";
				result += i.toStirngTimeUser();
				result += "</td>";
				result += "<td><a class=\"modalbox\" href=\"#inline"+n+"\">Возврат товара</a>";
				result += "<input type=\"hidden\" id=\"id"+n+"\" value=\""+i.getId()+"\"/></td>";
				result += "</tr>";
				
				newInline(n, new DirectoryProduct(), i, "sold");
			
				sum+=i.getSum();
				discount+=i.getDiscount();
			
				if(i.getSum() == 0){
					discountNumber++;
				}
			}
			
		}
		result += "</table>";
		if(great == 0 && result.indexOf("<td>") < 0){
			result += "<br><p align=\"center\">За сегоднешний день не продано никакого товара</p>";
		}
		else{
			result += "<p align=\"center\">Сегодня товар продан на сумму - " + sum + " грн.<br>";
			result += "Сделанная скидка - " + discount + " грн.   и    Списано- " + discountNumber + " ";
			if(discountNumber == 1){
				result += "товар";
			}
			else{
				if(discountNumber > 1 && discountNumber < 5){
					result += "товара";
				}
				else{
					result += "товаров</p>";
				}
			}
		}
	return result;
}

private String soldProductAll(int n){

	String result="";
	List<SoldProduct> list = dao.selectAllSoldProduct();
	result += "<p align=\"center\">Вся история Продаж</p>";
	result += "<table border=\"2\" align=\"center\" class=\"table sortable\">";
	result += "<tr>";
	result += "<th align=\"center\">Имя<br>товара</th>";
	result += "<th align=\"center\">Фирма</th>";
	result += "<th align=\"center\">Артикул</th>";
	result += "<th align=\"center\">Кол-во</th>";
	result += "<th align=\"center\">Цена</th>";
	result += "<th align=\"center\">Скидка</th>";
	result += "<th align=\"center\">Сумма</th>";
	result += "<th align=\"center\">Время<br>продажи</th>";
	result += "<th align=\"center\">Продавец<br></th>";
	result += "<th align=\"center\">Продано<input type=\"hidden\" id=\"numb_last3\" value=\""+(list.size()+n+1)+"\"/></th>";
	result += "</tr>";
		for(SoldProduct i : list){
			n++;
			result += "<tr>";
			result += "<td align=\"center\">";
			result += i.getName();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getFirm();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getArticle();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getNumber();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getCostOutPut();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getDiscount();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getSum();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.toStirngTimeUser();
			result += "</td>";
			result += "<td align=\"center\">";
			result += i.getNameUserAdmin();
			result += "</td>";
			result += "<td><a class=\"modalbox\" href=\"#inline"+n+"\">Возврат товара</a>";
			result += "<input type=\"hidden\" id=\"id"+n+"\" value=\""+i.getId()+"\"/></td>";
			result += "</tr>";
			
			newInline(n, new DirectoryProduct(), i, "sold");
			
		}
		result += "</table>";
		return result;
}

	
	private void newInline(int i, DirectoryProduct el, SoldProduct els, String what){
		String result = "";
		if(what.equals("directory")){
			
			result += "<div id=\"inline" + i + "\" class=\"inline\">";//
			result += "<form id=\"contact"+ i + "\" name=\"contact" + i + "\" action=\"#\" method=\"post\">";
			result += "<label for=\"name"+ i + "\" >Наименование:</label>";
			result += "<input type=\"text\" id=\"name"+ i + "\" name=\"name"+ i + "\" value=\""+ el.getName() + "\" class=\"txt1\" readonly>";
			result += "<br><label for=\"firm"+ i + "\" >Фирма:</label>";
			result += "<input type=\"text\" id=\"firm"+ i + "\" name=\"firm"+ i + "\" value=\""+ el.getFirm() + "\" class=\"txt2\" readonly>";
			result += "<br><label for=\"article" + i + "\" >Артикул:</label>";
			result += "<input type=\"text\" id=\"article"+ i + "\" name=\"article"+ i + "\" value=\""+ el.getArticle() + "\" class=\"txt3\" readonly>";
			result += "<br><label for=\"number" + i + "\" >Кол-во в магазине:</label>";
			result += "<input type=\"text\" id=\"number"+ i + "\" name=\"number"+ i + "\" value=\""+ el.getNumber() + "\" class=\"txt4\" readonly>";
			result += "<br><label for=\"costoutput" + i + "\" >Цена:</label>";
			result += "<input type=\"text\" id=\"costoutput"+ i + "\" name=\"costoutput"+ i + "\" value=\""+ (int)el.getCostOutPut() + "\" class=\"txt5\" readonly>";
			result += "<br><label for=\"numbercold" + i + "\" >Кол-во продажи:</label>";
			result += "<input type=\"number\" min=\"1\" value=\"1\" step=\"1\" id="+"\"numbercold" + i + "\" name=\"numbercold"+i+"\" class=\"txt6\" AUTOCOMPLETE=\"off\">";
			result += "<br><label for=\"discount" + i + "\" >Скидка:</label>";
			result += "<input type=\"text\" value=\"0\" id=\"discount" + i + "\" name=\"discount"+i+"\" class=\"txt7\" AUTOCOMPLETE=\"off\">";
			result += "<br><label for=\"description" + i + "\" >Описание:</label>";
			result += "<input type=\"text\" min=\"0\" step=\"5\" id=\"description" + i + "\" name=\"description"+i+"\" class=\"txt10\" AUTOCOMPLETE=\"off\">";
			result += "<br><table><tr><td><center>";
			result += "<button id=\"send"+i+"\" class=\"send\" name=\"send"+i+"\" onclick=\"return postz(this, 'to_sell');\">";
			result += "Подтвердить продажу товара</button></td><td>";	
			result += "<button id=\"send"+i+"\" class=\"send\" name=\"send"+i+"\" onclick=\"return postz(this, 'prewrite');\">";
			result += "Списать товар</button>";
			result += "</center></td></tr></table></form></div>";
		}
		else{
			if(what.equals("sold")){
				result += "<div id=\"inline" + i + "\" class=\"inline\">";
				result += "<form id=\"contact"+ i + "\" name=\"contact" + i + "\" action=\"#\" method=\"post\">";
				result += "<label for=\"name" + i + "\" >Наименование:</label>";
				result += "<input type=\"text\" id=\"name"+ i + "\" name=\"name"+ i + "\" value=\""+ els.getName() + "\" class=\"txt1\" readonly>";
				result += "<br><label for=\"firm" + i + "\" >Фирма:</label>";
				result += "<input type=\"text\" id=\"firm"+ i + "\" name=\"firm"+ i + "\" value=\""+ els.getFirm() + "\" class=\"txt2\" readonly>";
				result += "<br><label for=\"article" + i + "\" >Артикул:</label>";
				result += "<input type=\"text\" id=\"article"+ i + "\" name=\"article"+ i + "\" value=\""+ els.getArticle() + "\" class=\"txt3\" readonly>";
				result += "<br><label for=\"numbercold" + i + "\" >Кол-во продажи:</label>";
				result += "<input type=\"text\" id=\"numbercold"+ i + "\" name=\"numbercold"+ i + "\" value=\""+ els.getNumber() + "\" class=\"txt6\" readonly>";
				result += "<br><label for=\"costoutput" + i + "\" >Цена:</label>";
				result += "<input type=\"text\" id=\"costoutput"+ i + "\" name=\"costoutput"+ i + "\" value=\""+ (int)els.getCostOutPut() + "\" class=\"txt5\" readonly>";
				result += "<br><label for=\"discount" + i + "\" >Скидка:</label>";
				result += "<input type=\"text\"  value=\""+els.getDiscount() +"\" id=\"discount"+i+"\" name=\"discount"+i+"\" class=\"txt7\" readonly>";
				result += "<br><label for=\"sum"+i+"\">Сумма:</label>";
				result += "<input type=\"text\"  value=\""+els.getSum()+"\" id=\"sum"+i+"\" name=\"sum"+i+"\" class=\"txt8\" readonly>";
				result += "<br><label for=\"time"+i+"\">Время продажи:</label>";
				result += "<input type=\"text\"  value=\""+els.toStirngTimeUser()+"\" id=\"time"+i+"\" name=\"time"+i+"\" class=\"txt9\" readonly>";
				result += "<input type=\"hidden\" id=\"id"+i+"\" name=\"id"+i+"\" value=\""+els.getId()+"\">";
				result += "<br><label for=\"description" + i + "\" >Описание:</label>";
				result += "<input type=\"text\" min=\"0\" step=\"5\" id=\"description" + i + "\" name=\"description"+i+"\" class=\"txt10\" AUTOCOMPLETE=\"off\">";
				result += "<br><center>";
				result += "<button id=\"send"+i+"\" class=\"send\" name=\"send"+i+"\" onclick=\"return postz(this, 'return');\">";
				result += "_____Подтвердить ВОЗВРАТ товара_____</button></center></form></div>";
			}
		}
		inline += result;
	}
	
	private String ReturnProduct(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		String name = request.getParameter("name");
		String firm = request.getParameter("firm");
		String article = request.getParameter("article");
		double costoutput = Double.parseDouble((request.getParameter("costoutput")));
		int numbercold = Integer.parseInt(request.getParameter("numbercold"));
		double discount = Double.parseDouble((request.getParameter("discount")));
		String descriptionForWhom = request.getParameter("description");
		String fullName = (String)session.getAttribute("fullName");
		DirectoryProduct b = dao.selectOnTheArticleFirmDirectoryProduct(new DirectoryProduct(firm, article));
		b.setNumber(b.getNumber()+numbercold);
		dao.updateDirectoryProduct(b);
		Calendar c = Calendar.getInstance();
		Timestamp t = new Timestamp(c.getTimeInMillis());
		ReturnOrWriteOff a = new ReturnOrWriteOff(name, firm, b.getProvider(), article, numbercold, costoutput, t, fullName, "Возврат товара", descriptionForWhom);

		b.setNumber(numbercold);
		dao.insertNewReturnOrWriteOff(a);
		int id = Integer.parseInt(request.getParameter("id"));
		dao.deleteSoldProduct(id);
		String result = "<p>Возвращено</p>";
		return result;
	}
	
	private String toSellProduct(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		String name = request.getParameter("name");
		String firm = request.getParameter("firm");
		String article = request.getParameter("article");
		System.out.println(article);
		double costoutput = Double.parseDouble((request.getParameter("costoutput")));
		int numbercold = Integer.parseInt(request.getParameter("numbercold"));
		double discount = Double.parseDouble((request.getParameter("discount")));
		String descriptionForWhom = request.getParameter("description");
		String fullName = (String)session.getAttribute("fullName");
		DirectoryProduct b = dao.selectOnTheArticleFirmDirectoryProduct(new DirectoryProduct(firm, article));
		b.setNumber(b.getNumber()-numbercold);
		dao.updateDirectoryProduct(b);
		if((int)costoutput ==(int)discount){
			Calendar c = Calendar.getInstance();
			Timestamp t = new Timestamp(c.getTimeInMillis());
			ReturnOrWriteOff a = new ReturnOrWriteOff(name, firm, b.getProvider(), article, numbercold, costoutput, t, fullName, "Списание Товара", descriptionForWhom);
			dao.insertNewReturnOrWriteOff(a);
		}
		SoldProduct a = new SoldProduct(name, firm, article, numbercold, costoutput, discount, ((costoutput*numbercold)-discount), fullName);
		dao.insertNewSoldProduct(a);
		String result = "<p>Продано</p>";
		return result;
	}
	
/*	private String searchDirectoryProduct(String str, String typ) {
		int n = 0;
		String result = "";
		
		
		
		
		
		return result;
	}	*/
	
	
	
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AJAX - POST");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AJAX - GET");
	}
	
}
