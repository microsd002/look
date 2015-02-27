<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.alex.looks.models.DirectoryProduct"
	import="com.alex.looks.models.SoldProduct"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/user.css">
<link rel="stylesheet" type="text/css" href="css/tabsort.css">

<script type="text/javascript" src="js/user/ir2.js"></script>
<script type="text/javascript" src="js/user/tabsort2.js"></script>
<script type="text/javascript">
ab2=0;ab3=0;x=1;y=1;
pw=174; // Ширина изображения в пикселях (указать)
ph=77; // Высота изображения в пикселях (указать)
 
function dvizhenie(){
var elem1 = document.getElementById("mydiv");
ww1 = elem1.offsetWidth;
hh1 = elem1.offsetHeight;
ll1 = 0;
tt1 = 0;
while (elem1){ll1 += elem1.offsetLeft;
   tt1 += elem1.offsetTop;
   elem1 = elem1.offsetParent;
}
 
ab2+=x;ab3+=y;
if(ab3>=hh1-ph){y=-1};
if(ab2>=ww1-pw){x=-2};
if(ab3<=0){y=2};
if(ab2<=0){x=1};
document.getElementById ("myimg").style.top = (tt1 + ab3 + document.body.scrollTop) + 'px';
document.getElementById ("myimg").style.left = (ll1 + ab2 + document.body.scrollLeft) + 'px';
} 

setInterval (dvizhenie, 50);

</script>

<%int n = 0; %>

<title>LOOKS</title>

</head>

<body class="body">
<%
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
response.sendRedirect("index.jsp");
}
else{
%>



<%
		List<DirectoryProduct> listDirectory = (List) session.getAttribute("listDirectory");
		List<SoldProduct> listSoldProductThisDay = (List) session.getAttribute("listSoldProductThisDay");
		if(session.getAttribute("status").equals("user")){	
%>
	
		<div style="background:gold;width:174;height:77" id=mydiv><img id=myimg src="images/logo2.png" style="position:absolute"></div>
		<br><br><br><br>
		<%} 
		if(session.getAttribute("status").equals("admin")){%>
		<div style="background:gold;width:174;height:77" id=mydiv>
			<a href="adminservice.jsp">
			<img id=myimg src="images/logo2.png" style="position:absolute">
			</a>
		</div>
		<br><br><br>
		<p style="color: red;">* для перехода обратно на страницу<br />администратора кликните на бегающее изображение</p>
		<%} %>
		
		
	
	<center><label style="text-align: middle">Здравствуйте <%=session.getAttribute("fullName")%></label></center>
	<div class="notebook">
		<input type="radio" class="notebook_radio" name="notebook1" id="notebook1_1" checked>
		<label for="notebook1_1">Показать весь товар</label>
		<input type="radio" class="notebook_radio" name="notebook1" id="notebook1_2">
		<label for="notebook1_2">Показать проданное за день</label>
		
		
<br><br>

		<div class="page">
			<table border=2 align="center" class="table sortable">
				<tr>
					<th align="center">Наименование<br>товара</td>
					<th align="center">Фирма</td>
					<th align="center" axis="str">Артикул</td>
					<th align="center">Количество</td>
					<th align="center">Цена</td>
					<th colspan="3" align="center">Продать</td>
				</tr>


				<%
			
			for(DirectoryProduct i : listDirectory){
				n++;
		%>
				<tr>
					<td align="center"><%=i.getName()%></td>
					<td align="center"><%=i.getFirm()%></td>
					<td align="center"><%=i.getArticle()%></td>
					<td align="center"><%=i.getNumber()%></td>
					<td align="center"><%=i.getCostOutPut()%></td>
					<td align="center">
						<form name="form<%=n%>" action="SellProduct" method="post">
							Кол-во: <input align="middle" type="number" min="1"
								max="<%=i.getNumber()%>" step="1" size="5" name="numb" value="1"/>
					</td>
					<td>
								 Скидка:<input align="middle" type="number" min="0" max="<%=i.getCostOutPut()%>"
								 step="5" name="discount" value="0" style="width: 40px"/>
					</td>
					<td><input align="middle" type="submit" name="SellOfProduct"
						value="Продать" />
						<input type="hidden" name="art" value="<%=i.getArticle()%>"/>
						</form></td>
				</tr>
				<% } %>
			</table>
		</div>
		<div class="page">
			<% if(listSoldProductThisDay.size() != 0){%>
			<table border=2 align="center">
				<tr>
					<td align="center">Наименование<br>товара
					</td>
					<td align="center">Фирма</td>
					<td align="center">Артикул</td>
					<td align="center">Количество</td>
					<td align="center">Цена</td>
					<td align="center">Скидка</td>
					<td align="center">Сумма</td>
					<td align="center">Время продажи</td>
					<td align="center">Продано</td>
				</tr>


				<%
				int discountNumber=0;
			for(SoldProduct i : listSoldProductThisDay){
				n++;
				if(i.getSum() == 0) discountNumber++;
				
		%>		
				<tr>
					<td align="center"><%=i.getName()%></td>
					<td align="center"><%=i.getFirm()%></td>
					<td align="center"><%=i.getArticle()%></td>
					<td align="center"><%=i.getNumber()%></td>
					<td align="center"><%=i.getCostOutPut()%></td>
					<td align="center"><%=i.getDiscount() %></td>
					<td align="center"><%=i.getSum() %></td>
					<td align="center"><%=i.toStirngTimeUser() %></td>
					<td align="center" width="130">
						<form name="form<%=n%>" action="SellProduct" method="post">
							<input align="middle" type="submit" name="OutOfProduct"
								value="Возврат товара"/> 
								<input type="hidden" name="art" value="<%=i.getArticle()%>">
								<input type="hidden" name="id" value="<%=i.getId() %>">
								<input type="hidden" name="number" value="<%=i.getNumber() %>">
						</form>
					</td>
				</tr>
				<% } %>
			</table>
			<% 
			double sum=0;
			double discount=0;
			for(SoldProduct i : listSoldProductThisDay){
				sum+=i.getSum();
				discount+=i.getDiscount();
			}
			%>
			<p align="center">Сегодня товар продан на сумму - <%=sum %> грн.
			<br>Сделанная скидка: - <%=discount %> грн.<br>
			<% 
			
			%>
			Сегодня списанно <%=discountNumber %> 
			<%if(discountNumber == 1){ %>
				товар:
			<%}
			else{
				if(discountNumber > 1 && discountNumber < 5){%>
				товара:
				<%}
				else{%>
				товаров:
				<%} %>
			<%} %>
			</p>
			<%
		} 
		else{%>
			<p align="center">За сегоднешний день не продано никакого товара</p>
			<%}%>
			<form name="exitform" action="SellProduct" method="get" style="text-align: center;">
				<input id="exitButton" type="submit"
		<%		if(session.getAttribute("status").equals("admin")){%>
				value="Выйти из учётной записи администратора" 
				<% }
				else{%>
				value="Выйти из учётной записи"
				<% } %> 
				name="exit"/>
			</form>
		</div>
		
		</div>
<%}%>
</body>

</html>