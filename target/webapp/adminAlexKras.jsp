<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.Enumeration"%>
    <%@ page import="com.alex.looks.models.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<link rel="stylesheet" type="text/css" href="css/admin_registration.css">
<script type="text/javascript" src="js/admin/registrationfull.js"></script>
<link rel="stylesheet" type="text/css" href="css/user.css">
<link rel="stylesheet" type="text/css" href="css/admin_edit_users.css">
<link rel="stylesheet" type="text/css" href="css/tabsort.css">
<script type="text/javascript" src="js/jquery.min.js"></script>

<body>
<script type="text/javascript">
function deleteCookie() {
	delete_cookie("ststatus");
	return false;
}

function delete_cookie (cookie_name)
{
  var cookie_date = new Date ( );  // Текущая дата и время
  cookie_date.setTime ( cookie_date.getTime() - 1 );
  document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
  location.href='index.jsp';
}


function getCookie(name) {
	  var matches = document.cookie.match(new RegExp(
	    "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
	  ));
	  return matches ? decodeURIComponent(matches[1]) : undefined;
}

$(document).ready(function(){
	var str = getCookie('ststatus');
	if(str != 'adminfull'){
		location.href='index.jsp';
	}
});
</script>
<%
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
else{%>

<h1 align="center" style="color: green; border-color: red; background: yellow;">Привет Сань :-)<br>[<input id="exitButton" type="submit" value="Выйти отсюда к ..." name="exit" onclick="deleteCookie()">]</h1>
<div class="notebook">
		<input type="radio" class="notebook_radio" name="notebook1" id="notebook1_1" checked>
		<label for="notebook1_1">Регистрировать</label>
		<input type="radio" class="notebook_radio" name="notebook1" id="notebook1_2">
		<label for="notebook1_2">Просматривать</label>
		<a href="userajax.jsp" style="text-align : right;">Зайти на страницу Пользователя</a>
		<a href="adminservice.jsp">Зайти на страницу Администратора</a>



	<div class="page">
		<div id="login_container">
			<div id="form_container">
				<p class="login-text">Регистрация Администраторов</p>
				<form name="createuser" action="AdminFullServlet" method="post">
					<input type="text" onFocus="if(this.value=='Имя')this.value=''"
						   onblur="if(this.value=='')this.value='Имя'" 
						   onkeyup="CheckFirstName()" name='first_name' 
						   class='text_input' AUTOCOMPLETE="off" id="first_name" title="Имя"/>
					<input type="text" onFocus="if(this.value=='Отчество')this.value=''"
						   onblur="if(this.value=='')this.value='Отчество'"
						   onkeyup="CheckSecondName()" name='second_name' 
						   class='text_input' AUTOCOMPLETE="off" id="second_name" title="Отчество"/>
					
					<input type="text" onFocus="if(this.value=='Фамилия')this.value=''"
						   onblur="if(this.value=='')this.value='Фамилия'" 
						   onkeyup="CheckThreeName()" name='three_name' 
						   class='text_input' AUTOCOMPLETE="off"/ id="three_name" title="Фамилия"/>
					
					<input type="text" onFocus="if(this.value=='Телефон')this.value=''"
						   onblur="if(this.value=='')this.value='Телефон'"
						   onkeyup="CheckTel()" name='tel'
						   class='text_input' AUTOCOMPLETE="off" id="tel" title="Телефон"/>
					
					<input type="text" onFocus="if(this.value=='Логин')this.value=''"
						   onblur="if(this.value=='')this.value='Логин'"
						   onkeyup="CheckLogin()" name='username_registration'
						   class='text_input' AUTOCOMPLETE="off" id="username_registration" title="Имя пользователя"/>
					
					<input type="text" onFocus="if(this.value=='Пароль')this.value=''"
						   onblur="if(this.value=='')this.value='Пароль'"
						   onkeyup="CheckPassword()" name='password_registration'
						   class='text_input' AUTOCOMPLETE="off" id="password_registration"  title="Пароль"/>
					
					<p style="margin: 0px 130px 0 150px;"><input type="radio" name='status'
						   id="status_admin" CHECKED/><label for="status_admin">admin</label></p>
					<p style="margin: 0px 130px 0 150px;"><input type="radio" name='status'
						   id="status_user" /><label for="status_user">user</label></p>
					<br />
				    <span id="first_name_span" class='text_span'></span>
				    <br />
					<span id="second_name_span" class='text_span'></span>
					<br />
					<span id="three_name_span" class='text_span'></span>
					<br />
					<span id="tel_span" class='text_span'></span>
					<br />
					<span id="username_registration_span" class='text_span'></span>
				    <br />
					<span id="password_registration_span" class='text_span'></span>
					
					<input type="submit" name='sumbit_registration' value='' id='submit_registration' onclick="return newUser(this);" class='submit'/>
				</form>
			</div>
		</div>
	</div>

	<div class="page" id="showuser">
			
	</div>
</div>
<script type="text/javascript">

function update_delete(el, string){
	var str = (el.id).replace(/\D+/g,"");
	var username = $("#usernametable"+str).val();
	var password = $("#passwordtable"+str).val();
	if(confirm("Вы действительно хотите "+string+" данного пользователя ???")){
		$("#showuser").load("AdminFullServlet", {load : string, username : username, password : password});		
	}
}

function startPage(){
	$("#showuser").load("AdminFullServlet", {load : "showuser"});		
}
window.onload = startPage();
setTimeout(startPage, 60000);

function newUser(el){
	var first_name = $("#first_name").val();
	$("#first_name").val("");
	var second_name = $("#second_name").val();
	$("#second_name").val("");
	var three_name = $("#three_name").val();
	$("#three_name").val("");
	var tel = $("#tel").val();
	$("#tel").val("");
	var username_registration = $("#username_registration").val();
	$("#username_registration").val("");
	var password_registration = $("#password_registration").val();
	$("#password_registration").val("");
	var status = "";
	if($('input:radio[id=status_admin]:checked').val() == 'on'){
		status = 'admin';
	} 
	if($('input:radio[id=status_user]:checked').val() == 'on'){
		status = 'user';
	}  
	$("#status_admin").checked='true';
	if((first_name.length > 1) && (second_name.length > 3) &&
			(three_name.length > 2) && (tel.length > 5) &&
			(username_registration.length > 5) && (password_registration.length > 7)){
		$("#showuser").load("AdminFullServlet", {load : "newuser", 
												first_name : first_name,
												second_name : second_name,
												three_name : three_name,
												tel : tel,
												username_registration :  username_registration,
												password_registration : password_registration,
												status : status
		});	
	}
	else{
		alert("Введите коректные данные");
	}
	return false;
}
</script>
<% } %>
</body>
</html>