<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.Enumeration"
    		import="java.util.List"
			import="com.alex.looks.models.DirectoryProduct"
			import="com.alex.looks.models.SoldProduct"
			import="com.alex.looks.models.EntrancePrice"
			import="com.alex.looks.models.User"
			import="java.sql.Timestamp"
			import="java.util.Calendar"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ADMIN</title>
<% int n = 0; %>
<link rel="stylesheet" type="text/css" href="css/admin_tabs.css">
<link rel="stylesheet" type="text/css" href="css/admin_registration.css">

<link rel="stylesheet" type="text/css" href="css/admin_edit_users.css">
<link rel="stylesheet" type="text/css" href="css/tabsort.css">
<script type="text/javascript" src="js/admin/registration.js"></script>
<script type="text/javascript" src="js/admin/create_new_entrance_write.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/toastmessage/css/jquery.toastmessage.css">
<script type="text/javascript" src="js/user/jquery.toastmessage.js"></script>

<link rel="stylesheet" type="text/css" media="all" href="form_send_admin/css/style.css">
<link rel="stylesheet" type="text/css" media="all" href="form_send_admin/fancybox/jquery.fancybox.css">
<link rel="stylesheet" href="form_send_admin/css/header.css" type="text/css">

<script type="text/javascript" src="form_send_admin/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript">
  function digitalWatch() {
    var date = new Date();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (hours < 10) hours = "0" + hours;
    if (minutes < 10) minutes = "0" + minutes;
    if (seconds < 10) seconds = "0" + seconds;
    document.getElementById("digital_watch").innerHTML = hours + ":" + minutes + ":" + seconds;
    setTimeout("digitalWatch()", 1000);
  }
</script>
</head>
<body class="body" onload="digitalWatch()">
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
	if(str != 'admin'){
		if(str != 'adminfull'){
			location.href='index.jsp';
		}
	}
});
</script>
<%
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
%>

	<div align="center">
		<table>
			<tr>
				<%int i=1; 
				while(i<3){ %>
				
				<td>
					<object width="150" height="128" align="center">
						<param name="movie" value="swf/blackcat.swf">
						<param name="quality" value="high">
						<param name="bgcolor" value="#FFFFFF">
						<param name="wmode" value="transparent">
						<embed src="swf/blackcat.swf" quality="high" bgcolor="#00FF7F"
							   width="150" height="180" align="center" wmode="transparent"
							   type="application/x-shockwave-flash">
					</object>
				</td>
				
				<% 
				if(i == 2){%>
				
				<%	break;
				}
				%>
				<td>
					<center>
						<input id="exitButton" type="submit" value="Выйти из учётной записи администратора" name="exit" onclick="deleteCookie()"/>
						<br><p onclick="startPage();" id="digital_watch" style="color: #f00; font-size: 120%; font-weight: bold;"></p>
					</center>
				</td>
				
				<%i++;
				}%>
			</tr>
		</table>
		
		<center><% if(session.getAttribute("status").equals("adminfull")){ %>
			<a href="adminAlexKras.jsp"><label>Здравствуйте <%=session.getAttribute("fullName")%></label></a>
		<% }
		else{%>
			<label>Здравствуйте <%=session.getAttribute("fullName")%></label>
		<% } %>
		</center>
	</div>
	<br />
	
	<div class="tabs">
		<input type="radio" class="tabs_radio" name="tabs" id="tab1" checked>
		<label for="tab1">Создать запись<br>пришедшего<br>товара</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab2" checked>
		<label for="tab2">Создать<br>нового<br>пользователя</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab3" checked>
		<label for="tab3">Редактирование<br>списка<br>пользователей</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab4" checked>
		<label for="tab4">Редактирование<br>списка<br>поставщиков</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab5" checked>
		<label for="tab5">Просмотр<br>пришедшего<br>товара</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab6" checked>
		<label for="tab6">Просмотр<br>проданного<br>товара</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab7" checked>
		<label for="tab7">Просмотр<br>Возвратов,<br />Cписаний</label>
		<input type="radio" class="tabs_radio" name="tabs" id="tab8" checked>
		<label for="tab8">Просмотр<br>справочника<br>товаров</label>
		
		<input type="radio" class="tabs_radio" name="tabs" id="tab9" checked>
		<label for="tab9">Резервное<br>копирование<br>базы данных</label>
		
		<input type="radio" class="tabs_radio" name="tabs" id="tab10" checked>
		<label for="tab10">Перейти<br>на страницу<br>пользователя</label>
		
		<section id="content1">
			<div id="login_container_entrance">
				<div id="form_container_entrance">
					<p class="login-text">Новая запись товара</p>
					<form name="create_new_entrance_write" action="#" method="post">
						<a class="modalbox" href="#inline">Добавить данные из списка</a><br><br>
						<input type="text" onFocus="if(this.value=='Наименование')this.value=''"
							   onblur="if(this.value=='')this.value='Наименование'" 
							   onkeyup="CheckNameEntrance()" name='name_entrance' 
							   class='text_inpu' AUTOCOMPLETE="off" id="name_entrance"/>
							   <span class="span"> - имя</span>
						
						<input type="text" onFocus="if(this.value=='Фирма')this.value=''"
							   onblur="if(this.value=='')this.value='Фирма'"
							   onkeyup="CheckFirmEntrance()" name='firm_entrance' 
							   class='text_inpu' AUTOCOMPLETE="off" id="firm_entrance"/>
						<span class="span">- фирма</span>
						<!--  <input type="text" onFocus="if(this.value=='Поставщик')this.value=''"
							   onblur="if(this.value=='')this.value='Поставщик'" 
							   onkeyup="CheckProviderEntrance()" name='provider_entrance' 
							    class='text_inpu' AUTOCOMPLETE="off" id="provider_entrance_text"/> -->
						<select id="provider_entrance"  class='text_inpu'></select>
						<span class="span">- поставщик</span><a class="modalbox" style="border: 2;" href="#create_inline_provider"><label style="font-size: 30;">Add</label></a>
						<input type="text" onFocus="if(this.value=='Артикул')this.value=''"
							   onblur="if(this.value=='')this.value='Артикул'"
							   onkeyup="CheckArticleEntrance()" name='article_entrance'
							   class='text_inpu' AUTOCOMPLETE="off" id="article_entrance"/>
						<span class="span">- артикул</span>
						<input type="text"
							   onFocus="if(this.value=='Количество')this.value=''"
							   onblur="if(this.value=='')this.value='Количество'"
							   onkeyup="CheckNumberEntrance()" name='number_entrance'
							   class='text_inpu' AUTOCOMPLETE="off" id="number_entrance"
							   onkeydown = "javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8) || (event.keyCode == 9))"/>
						<span class="span">- количество</span>
						<input type="text" 
							   onFocus="if(this.value=='Цена закупки')this.value=''"
							   onblur="if(this.value=='')this.value='Цена закупки'"
							   onkeyup="CheckCostEntrance()" name='cost_entrance'
							   class='text_inpu' AUTOCOMPLETE="off" id="cost_entrance"
							   onkeydown = "javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8) || (event.keyCode == 9))"/>
						<span class="span">- цена закупки</span>
						<input type="text"  
							   onFocus="if(this.value=='Цена продажи')this.value=''" 
							   onblur="if(this.value=='')this.value='Цена продажи'" 
							   onkeyup="CheckCostSell()" name='costout' 
							   class='text_inpu' AUTOCOMPLETE="off" id="cost_out_put_entrance"
							   onkeydown = "javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8) || (event.keyCode == 9))"/>
						<span class="span">- цена продажи</span>
							   <br />
							   
							   <% Calendar c = Calendar.getInstance();
							   Timestamp t = new Timestamp(c.getTimeInMillis());
							   String date="";
							   if(t.getMonth()+1 < 10){
							   		date = (t.getYear()+1900) + "-0" + (t.getMonth()+1);  
							   }
							   else{
								   date = (t.getYear()+1900) + "-" + (t.getMonth()+1);
							   }
							   if(t.getDate() < 10){
							   		date = date + "-0" + t.getDate(); 
							   }
							   else{
								   date = date + "-" + t.getDate();
							   }%>
						<input align="middle" type="date" name="calendar_entrance" value="<%=date %>"
							   min="2015-02-08"  id="time_entrance"  class='text_inpu'/>
						<span class="span">- дата покупки</span>
						<br />
					    <span id="name_entrance1" class='text_span'></span>
					    <br />
						<span id="firm_entrance1" class='text_span'></span>
						<br />
						<span id="provider_entrance1" class='text_span'></span>
						<br />
						<span id="article_entrance1" class='text_span'></span>
						<br />
						<span id="number_entrance1" class='text_span'></span>
					    <br />
						<span id="cost_entrance1" class='text_span'></span>
						<br />
						<span id="cost_out_put_entrance1" class='text_span'></span>
						
						<input type="submit" name='submit_entrance' value='' id='submit_entrance' onclick="return postz(this);" onfocus="out_focus(this)" class='submit'/>
						
						<!-- <a class="modalbox" style="border: 2;" href="#create_inline_provider">Добавить нового поставщика</a> -->
					</form>
				</div>
			</div>
		</section> 
       
        <section id="content2">
			<div id="login_container">
				<div id="form_container">
					<p class="login-text">Регистрация Пользователя</p>
					<form name="createuser" action="AdminServlet" method="post">
						<input type="text" onFocus="if(this.value=='Имя')this.value=''"
							   onblur="if(this.value=='')this.value='Имя'" 
							   onkeyup="CheckFirstName()" name='first_name' 
							   class='text_inpu' AUTOCOMPLETE="off" id='first_name'/>
						<span class="span"> - имя</span>
						<input type="text" onFocus="if(this.value=='Отчество')this.value=''"
							   onblur="if(this.value=='')this.value='Отчество'"
							   onkeyup="CheckSecondName()" name='second_name' 
							   class='text_inpu' AUTOCOMPLETE="off" id='second_name'/>
						<span class="span"> - отчество</span>
						<input type="text" onFocus="if(this.value=='Фамилия')this.value=''"
							   onblur="if(this.value=='')this.value='Фамилия'" 
							   onkeyup="CheckThreeName()" name='three_name' 
							   class='text_inpu' AUTOCOMPLETE="off" id='three_name' />
						<span class="span"> - фамилия</span>
						<input type="text" onFocus="if(this.value=='Телефон')this.value=''"
							   onblur="if(this.value=='')this.value='Телефон'"
							   onkeyup="CheckTel()" name='tel' onkeydown="javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8) || (event.keyCode == 9) || (event.keyCode == 64))"
							   class='text_inpu' AUTOCOMPLETE="off" id='tel'
							   onkeydown = "javascript: return ((event.keyCode>47)&&(event.keyCode<58) || (event.keyCode == 8))"/>
						<span class="span"> - телефон</span>
						<input type="text" onFocus="if(this.value=='Логин')this.value=''"
							   onblur="if(this.value=='')this.value='Логин'"
							   onkeyup="CheckLogin()" name='username_registration'
							   class='text_inpu' AUTOCOMPLETE="off" id='username_registration'/>
						<span class="span"> - логин</span>
						<input type="text" onFocus="if(this.value=='Пароль')this.value=''"
							   onblur="if(this.value=='')this.value='Пароль'"
							   onkeyup="CheckPassword()" name='password_registration'
							   class='text_inpu' AUTOCOMPLETE="off" id='password_registration'/>
						<span class="span"> - пароль</span>
						<br />
					    <span id="first_name1" class='text_span'></span>
					    <br />
						<span id="second_name1" class='text_span'></span>
						<br />
						<span id="three_name1" class='text_span'></span>
						<br />
						<span id="tel1" class='text_span'></span>
						<br />
						<span id="username_registration1" class='text_span'></span>
					    <br />
						<span id="password_registration1" class='text_span'></span>
						
						<input type="submit" name='sumbit_registration' value='' id='submit_registration' onclick="return newUser();" class='submit'/>
					</form>
				</div>
			</div>
		</section> 
		
		
		<section id="content3">
			<div class="page" id="all_users">
			
			</div>
		</section>
		
		
		<section id="content4">
			<div class="page" id="edit_provider">
				
			</div>
		</section>
		
		
		<section id="content5">
			<div class="page" id="entrance_price">
				
			</div>
		</section>
		
		<section id="content6">
			<div class="page" id="sold_product">
			
			</div>
		</section>
		
		
		<section id="content7">
			<div class="page" id="return_product">
				
			</div>
		</section>
		
		
		<section id="content8">
			<div class="page" id="directory_product">
				
			</div>
		</section>
		
		
		<section id="content9">
			<div id="recove_back" style="width: 100%;">
				<table width="100%">
					<tr>
						<td>
							<center><label for="sumbit_reserve_copy_db">Создать резервную копию БАЗЫ ДАННЫХ</label></center>
							<input type="submit" name='sumbit_reserve_copy_db' value='' id='sumbit_reserve_copy_db' onclick="return reserve();" class='submit'/>	
						</td>
						<td>
							<center><label for="sumbit_recovery_db">Востановить из резервной копии БАЗУ ДАННЫХ</label></center>
							<input type="submit" name='sumbit_recovery_db' value='' id='sumbit_recovery_db' onclick="return recovery_db_show();" class='submit'/>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="page" id="reserve_copy_db" style="display: none;">
			<center>
				
				<a id="download" href="upload/alex_kras_new_copy_file_for_reserve_copy_dtfgmgorifjgurnfhdyrjfudk.xml" style="display: none;">СКАЧАТЬ РЕЗЕРВНУЮ КОПИЮ БД</a>
			</center>
			</div>
			<div class="page" id="recovery_db" style="display: none;" align="center">
				<table width="60%" align="center">
					<tr>
						<td>
							<center>
								<label for="sumbit_load_file_reserve" id="label_load_file_reserve">Загрузить файл из этого компьютера</label>
								<input type="submit" name='sumbit_load_file_reserve' value='' id='sumbit_load_file_reserve' onclick="return sumbit_load_file_reserve();" class='submit'/>
							</center>
						</td>
						<td>
							<center>
								<label for="sumbit_load_data_reserve" id="label_load_data_reserve">Если файл был загружен - ПРОСМОТРЕТЬ ЕГО СОДЕРЖИМОЕ</label>
								<input type="submit" name='sumbit_load_data_reserve' value='' id='sumbit_load_data_reserve' onclick="return sumbit_load_data_reserve();" class='submit'/>
							</center>
						</td>
					</tr>
				</table>
				<form style="display: none; margin: 20px 45%;" action="Upload" method="post" id="form_upload" name="form_upload" enctype="multipart/form-data">
					<input name="data" type="file">
					<input type="submit" id="submit_upload_to_servlet"><br>
				</form> 
				
				
				<div id="result_download">
				
				</div>
			</div>
		</section>
		
		
		<section id="content10">
			<div class="page">
				<center>
				<p>переход на страницу<br />пользователя для продажи товара</p><br>
					<p>
						<a href="userajax.jsp">
							<img src="images/partner.png" width="300" height="252" border="0" alt="ПЕРЕЙТИ НА СТРАНИЦУ ПОЛЬЗОВАТЕЛЯ ДЛЯ ПРОДАЖИ ТОВАРА">
						</a>
					</p>
					
				</center>
			</div>
		</section>

	</div>
<div id="inline_directory">

</div>
<div id="inline_provi">
</div>
<div id="inline_provider_all">
</div>
<div id="resultinlinecountall">
</div>
 <%} %>
 <div id="empty" style="display: none;"></div>

</body>
<script type="text/javascript">
$(document).ready(function() {
	$(".modalbox").fancybox();
	$("#contact").submit(function() { return false; });

});
var data_reserve_downloading = true;
function recoveryTrue(){
	$("#empty").load("AdminServlet", {load: "download_data_to_db"});
	$("#sumbit_load_data_reserve").css({ display: "inline" });
	$("#sumbit_load_file_reserve").css({ display: "inline" });
	$("#label_load_data_reserve").css({ display: "inline" });
	$("#label_load_file_reserve").css({ display: "inline" });
	$("#recovery_db").css({ display: "none" });
	$("#result_download").val("");
	$("#recove_back").css({ display: "inline" });
	
	data_reserve_downloading = false;
}

function sumbit_load_data_reserve(){
	$("#sumbit_load_data_reserve").css({ display: "none" });
	$("#sumbit_load_file_reserve").css({ display: "none" });
	$("#label_load_data_reserve").css({ display: "none" });
	$("#label_load_file_reserve").css({ display: "none" });
	$("#result_download").css({ display: "inline" });
	$("#result_download").load("AdminServlet", {load: "parse_load_reserve_copy"});
	return false;
}

function sumbit_load_file_reserve(){
	$("#sumbit_load_file_reserve").css({ display: "none" });
	$("#sumbit_load_data_reserve").css({ display: "none" });
	$("#label_load_data_reserve").css({ display: "none" });
	$("#label_load_file_reserve").css({ display: "none" });
	$("#form_upload").css({ display: "inline" });
	return false;
}



function recovery_db_show(){
	$("#recove_back").css({ display: "none" });
	$("#recovery_db").css({ display: "inline" });
	return false;
}

function reserve(){
	$("#empty").load("AdminServlet", {load: "reserve_copy"}, function(){ 
		showStickySuccessToast();
		$("#download").css({ display: "inline" });
		$("#create_reserve").css({ display: "none" });
	});
	$("#recove_back").css({ display: "none" });
	$("#reserve_copy_db").css({ display: "inline" });
	return false;
	
}

function update_provider(el){
	var str = (el.id).replace(/\D+/g,"");
	var fullnameprovider = $("#fullnameprovider"+str).val();
	var adressprovider = $("#adressprovider"+str).val();
	var telprovider = $("#telprovider"+str).val();
	var siteprovider = $("#siteprovider"+str).val();
	var emailprovider = $("#emailprovider"+str).val();
	var descriptionprovider = $("#descriptionprovider"+str).val();
	if(adressprovider.length > 0 && telprovider.length > 0){
	$("#edit_provider").load("AdminServlet", {load: "update_provider", fullnameprovider: fullnameprovider, adressprovider :adressprovider,
		telprovider: telprovider, siteprovider: siteprovider, emailprovider: emailprovider, descriptionprovider : descriptionprovider}, function() {
		startPage();
		showSuccessToast2();
	});
	}
}

function time_now(){
	var d = new Date();
	var t = d.toLocaleTimeString();
	return t;
}


//setInterval(time_now, 1000);

function newProvider_create(){
	var name = $("#name_provider_inline").val();
	var adress = $("#adress_provider_inline").val();
	var tel = $("#tel_provider_inline").val();
	var site = $("#site_provider_inline").val(); 
	var mail = $("#mail_provider_inline").val();
	var description = $("#description_provider_inline").val();
	if(name.length > 2){
		$("#provider_entrance").load("AdminServlet", {load: "new_provider", name:name, adress:adress, tel:tel, site:site, mail:mail, description : description}, function() {
			setTimeout("$.fancybox.close()", 1000);
			startPage();
		});
	}else{
		alert("Данные введены не коректно");
	}
	return false;
} 

function add_click_inline(el){
	var str = (el.id).replace(/\D+/g,"");
	var name = $("#name_click"+str).text();
	var firm = $("#firm_click"+str).text();
	var provider = $("#provider_click"+str).text();
	var article = $("#article_click"+str).text();
	$("#name_entrance").val(name);
	$("#firm_entrance").val(firm);
	var objSel = document.create_new_entrance_write.provider_entrance;
	var n = $("#provider_click_hidden"+str).val();

	objSel.options[0].selected=false;
	objSel.options[n].selected=true;
	$("#article_entrance").val(article);
	setTimeout("$.fancybox.close()", 50);
	return false;
}

function out_focus(el){
		el.blur();
	}


function update_delete(el, string){
	var str = (el.id).replace(/\D+/g,"");
	var username = $("#usernametable"+str).val();
	var password = $("#passwordtable"+str).val();
	if(confirm("Вы действительно хотите "+string+" данного пользователя ???")){
		$("#all_users").load("AdminServlet", {load : string, username : username, password : password}, function(){
			if(string == 'delete'){
				setTimeout(showErrorToast, 1000);
			}
			else{
				setTimeout(showNoticeToast, 1000);
			}
		});		
	}
}

function startPage(){
	
	$("#all_users").load("AdminServlet", {load: "all_users"}, function() {
		$("#entrance_price").load("AdminServlet", {load: "entrance_price"},  function() {
			$("#sold_product").load("AdminServlet", {load: "sold_product"}, function() {
				$("#return_product").load("AdminServlet", {load: "return_product"}, function() {
					$("#directory_product").load("AdminServlet", {load: "directory_product"}, function() {
						$("#edit_provider").load("AdminServlet", {load: "edit_provider"}, function() {
							$("#inline_directory").load("AdminServlet", {load: "inline_directory"}, function() {
								$("#inline_provi").load("AdminServlet", {load: "inline_provider"}, function() {
									$("#provider_entrance").load("AdminServlet", {load: "provider_entrance"}, function() {
										$("#inline_provider_all").load("AdminServlet", {load: "inline_provider_all"}, function() {
											$("#resultinlinecountall").load("SellProductAjax", {load: "resultinlinecountall"}, setTimeout(showSuccessToastUpdatePage, 1000));
										});
									});
								});
							});
						});
					});
				});
			});
		}); 
	});		
}



window.onload = startPage();
setInterval(startPage, 150000);

function show_new_data_sale(){
	
	$.ajax({
		type: "POST",
		url: "AdminServlet",
		data: ({load : "show_new_data_sale"}),
		success: function(data){
			var sold = data.substring(data.indexOf("<sold>")+6, data.indexOf("</sold>"));
			var returnd = data.substring(data.indexOf("<return>")+8, data.indexOf("</return>"));
			var prewrite = data.substring(data.indexOf("<prewrite>")+10, data.indexOf("</prewrite>"));
			var f = false;
			if(data_reserve_downloading){
				if(sold > 0){
					while(sold != 0){
						showStickyNoticeToast();
						sold = sold - 1;
						f = true;
					}
				}
				if(returnd > 0){
					while(returnd != 0){
						showStickyErrorToast();
						returnd = returnd - 1;
						f = true;
					}
				}
				if(prewrite > 0){
					while(prewrite != 0){
						showStickyWarningToast();
						prewrite = prewrite - 1;
						f = true;
					}
				}
				if(f){
					startPage();
				}
			}
			else{
				data_reserve_downloading = true;
			}
		}
	});
}
setInterval(show_new_data_sale, 30000);

function postz(el){
	var name_entrance = $("#name_entrance").val();
	var firm_entrance = $("#firm_entrance").val();
	var objSel = document.create_new_entrance_write.provider_entrance;
	if ( objSel.selectedIndex == 0)
	{
	  alert("Выберите поставщика");
	}
	var provider_entrance =  objSel.options[objSel.selectedIndex].text;
	
	var article_entrance = $("#article_entrance").val();
	var number_entrance = $("#number_entrance").val();
	var cost_entrance = $("#cost_entrance").val();
	var cost_out_put_entrance = $("#cost_out_put_entrance").val();
	var time_entrance = $("#time_entrance").val() + " "+time_now();
	if((name_entrance.length > 2) && (firm_entrance.length > 1) &&
			(provider_entrance.length > 2) && (article_entrance.length > 0) &&
			(number_entrance.length > 0) && (cost_entrance.length > 0 &&
			(cost_out_put_entrance).length > 0)){
		
		$("#entrance_price").load("AdminServlet", {load : "newentrance", 
			name_entrance : name_entrance,
			firm_entrance : firm_entrance,
			provider_entrance : provider_entrance,
			article_entrance : article_entrance,
			number_entrance :  number_entrance,
			cost_entrance : cost_entrance,
			cost_out_put_entrance : cost_out_put_entrance,
			time_entrance : time_entrance
		}, function(){
			$("#directory_product").load("AdminServlet", {load: "directory_product"}, setTimeout(showSuccessToast, 300));
			$("#name_entrance").val("");
			$("#firm_entrance").val("");
			objSel.options[0].selected=true;
			$("#article_entrance").val("");
			$("#number_entrance").val("");
			$("#cost_entrance").val("");
			$("#cost_out_put_entrance").val("");
		});	
	}
	else{
		alert("Введите коректные данные");
	}
	return false;
}
	
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
		if((first_name.length > 1) && (second_name.length > 3) &&
				(three_name.length > 2) && (tel.length > 5) &&
				(username_registration.length > 5) && (password_registration.length > 7)){
			$("#all_users").load("AdminServlet", {load : "newuser", 
													first_name : first_name,
													second_name : second_name,
													three_name : three_name,
													tel : tel,
													username_registration :  username_registration,
													password_registration : password_registration
			}, setTimeout(showSuccessToast1, 1000));	
		}
		else{
			alert("Введите коректные данные");
		}
		return false;
	}
	
	
	function showSuccessToast() {
        $().toastmessage('showSuccessToast', "Информация о новом ТОВАРЕ УСПЕШНО записана в БАЗУ ДАННЫХ. ДАННЫЕ НА СТРАНИЦЕ ОБНОВЛЕНЫ");
    }
	
	function showSuccessToast1() {
        $().toastmessage('showSuccessToast', "ИНФОРМАЦИЯ О НОВОМ ПОЛЬЗОВАТЕЛЕ УСПЕШНО ЗАПИСАНА В БАЗУ ДАННЫХ. ДАННЫЕ НА СТРАНИЦЕ ОБНОВЛЕНЫ");
    }
	
	function showSuccessToast2() {
        $().toastmessage('showSuccessToast', "ИНФОРМАЦИЯ О ПОСТАВЩИКЕ И ДАННЫЕ НА СТРАНИЦЕ УСПЕШНО ОБНОВЛЕНЫ");
    }
	
	function showSuccessToastUpdatePage() {
        $().toastmessage('showSuccessToast', "ИНФОРМАЦИЯ НА СТРАНИЦЕ УСПЕШНО ОБНОВЛЕНА");
    }
	
	function showErrorToast() {
	    $().toastmessage('showErrorToast', "Информация О ПОЛЬЗОВАТЕЛЕ УДАЛЕНА из БАЗЫ ДАННЫХ. ДАННЫЕ НА СТРАНИЦЕ ОБНОВЛЕНЫ");
	}
	
	function showNoticeToast() {
        $().toastmessage('showNoticeToast', "Информация О ПОЛЬЗОВАТЕЛЕ И ДАННЫЕ НА СТРАНИЦЕ УСПЕШНО ОБНОВЛЕНЫ");
    }
	
	 function showStickyNoticeToast() {
	        $().toastmessage('showToast', {
	             text     : 'В МАГАЗИНЕ НОВАЯ ПРОДАЖА ТОВАРА',
	             sticky   : true,
	             position : 'top-right',
	             type     : 'notice',
	             closeText: '',
	             close    : function () {console.log("toast is closed ...");}
	        });
	    }
	   
	    function showStickyWarningToast() {
	        $().toastmessage('showToast', {
	            text     : 'В МАГАЗИНЕ ПРОИЗОШЛО СПИСАНИЕ ТОВАРА',
	            sticky   : true,
	            position : 'top-right',
	            type     : 'warning',
	            closeText: '',
	            close    : function () {
	                console.log("toast is closed ...");
	            }
	        });
	    }
	    
	    function showStickyErrorToast() {
	        $().toastmessage('showToast', {
	            text     : 'В МАГАЗИНЕ ПРОИЗОШЕЛ ВОЗВРАТ ТОВАРА',
	            sticky   : true,
	            position : 'top-right',
	            type     : 'error',
	            closeText: '',
	            close    : function () {
	                console.log("toast is closed ...");
	            }
	        });
	    }
/* 	
	function showWarningToast() {
	    $().toastmessage('showWarningToast', "В МАГАЗИНЕ НЕТ ДАННОГО ТОВАРА");
	} */

   
    function showStickySuccessToast() {
        $().toastmessage('showToast', {
            text     : 'Резервный файл создан. Вы можите скачать его нажав на ссылку \"СКАЧАТЬ РЕЗЕРВНУЮ КОПИЮ БД\"',
            sticky   : true,
            position : 'top-right',
            type     : 'success',
            closeText: '',
            close    : function () {
                console.log("toast is closed ...");
            }
        });
    }


</script>

</html>