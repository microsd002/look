
<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/index/vaildation.js"></script>

<title>Вход</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
<%
session.removeAttribute("status");
session.removeAttribute("fullName");
%>
</head>

<body>
<div id="login_container">
	<div id="form_container">
		<p class="login-text">Авторизация на сайте</p>
    	<form name="loginform" action="login" method="post">
        	<input type='text' onFocus="if(this.value=='Логин')this.value=''"
        			onblur="if(this.value=='')this.value='Логин'"  
        			onkeyup="CheckLogin(this)" name='username'  id='username'
        			class='text_input' value="Логин" AUTOCOMPLETE="off"/>
			<input type="password" onFocus="if(this.value=='Пароль')this.value=''"
					onblur="if(this.value=='')this.value='Пароль'"
					onkeyup="CheckPass(this)" name='password' id='password'
					class='text_input' value="Пароль" AUTOCOMPLETE="off"/>
					
			<input type="submit" value='' id='login' name='login'/>
			<br />
			<span id="LoginErr" style="color: red" class='text_span'>* Логин должен содержать не меньше 6 символов</span>
			<br />
			<span id="PassErr" style="color: red" class='text_span'>* Пароль должен содержать не меньше 8 символов</span>
		</form>
	</div>
</div>
<center style="font-size: 20;">
	© 2015 <a href="https://vk.com/krasnyanskiy_alex" target="_blank">Краснянский Александр</a>, Беззубенко Владимир, Столяренко Святослав 
</center>

</body>
</html>