
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.alex.looks.models.DirectoryProduct"
	import="com.alex.looks.models.SoldProduct"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="refresh" content="15"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/user.css">
<link rel="stylesheet" type="text/css" href="css/tabsort.css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/toastmessage/css/jquery.toastmessage.css">
<link rel="stylesheet" type="text/css" media="all" href="form_send_user/css/style.css">
<link rel="stylesheet" type="text/css" media="all" href="form_send_user/fancybox/jquery.fancybox.css">
<link rel="stylesheet" href="form_send_user/css/header.css" type="text/css">

<script type="text/javascript" src="form_send_user/fancybox/jquery.fancybox.js"></script>
<script type="text/javascript" src="js/user/jquery.toastmessage.js"></script>
<script type="text/javascript">

</script>
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

<title>LOOKS</title>

</head>

<body class="body">

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
	if(str != 'user'){
		if(str != 'admin'){
			if(str != 'adminfull'){
				location.href='index.jsp';
			}
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

		if(session.getAttribute("status").equals("user")){	
%>
		
		<div style="background:gold;width:174;height:77" id=mydiv><img onclick="startPage();" id=myimg src="images/logo2.png" style="position:absolute">
		
		</div>
		<br><br><br>
		<%} 
		if(session.getAttribute("status").equals("admin") || session.getAttribute("status").equals("adminfull")){%>
		<div style="background:gold;width:174;height:77" id=mydiv>
			<a href="adminservice.jsp">
			<img id=myimg src="images/logo2.png" style="position:absolute">
			</a>
			
		</div>
		<br><br><br>
		<%} %>
	


	<div class="notebook">
		<input type="radio" class="notebook_radio" name="notebook1" id="notebook1_1" checked>
		<label for="notebook1_1">Показать весь товар</label>
		<input type="radio" class="notebook_radio" name="notebook1" id="notebook1_2">
		<label for="notebook1_2">Показать проданное за день</label>
		<label style="background-color: #F5F5DC; font-weight: 900; color: #00008B">Здравствуйте <%=session.getAttribute("fullName")%></label>
				[<input id="exitButton" type="submit"
		<%		if(session.getAttribute("status").equals("admin")){%>
				value="Выйти из учётной записи администратора" 
				<% }
				else{%>
				value="Выйти из учётной записи"
				<%} %> 
				name="exit" onclick="deleteCookie()">]
<br><br>

		<div class="page" id="resultdirectory">
			
		</div>
		<div class="page" id="sold">
			<div class="page" id="resultsoldthisday"></div>
			
			<div class="page" id="resultsoldproductall"></div>	
		</div>
		<br />
				
</div>



<div class="toast-container toast-position-top-right"></div>
<div id="resultinlinedirectory">

</div>
<div id="resultinlinesold">

</div>
<div id="resultinlinesoldall">

</div>

<div id="resultinlinecountall">
</div>
<input type="hidden" id="empty">

<script type="text/javascript">
/* function search_directory(type){
	var input_search_directory = "";
	switch(type){
	case "name":
		input_search_directory = $("#input_search_directory_name").val();
		break;
	case "firm":
		input_search_directory = $("#input_search_directory_firm").val();
		break;
	case "article":
		input_search_directory = $("#input_search_directory_article").val();
		break;
	}
	if(input_search_directory == ""){
		alert("1111");
	}
	$("#resultdirectory").load("SellProductAjax", {load: "search_directory", type : type, input_search_directory : input_search_directory}, function() {
		$("#resultinlinedirectory").load("SellProductAjax", {load: "resultinlinedirectory"})});
	return false;
} */


function startPage(){
	
	$("#resultdirectory").load("SellProductAjax", {load: "resultdirectory"}, function() {
		$("#resultinlinedirectory").load("SellProductAjax", {load: "resultinlinedirectory"},  function() {
			$("#resultsoldthisday").load("SellProductAjax", {load: "resultsoldthisday", numb_last: $("#numb_last1").val()}, function() {
				$("#resultinlinesold").load("SellProductAjax", {load: "resultinlinesold"}, function() {
					$("#resultsoldproductall").load("SellProductAjax", {load: "resultsoldproductall", numb_last: $("#numb_last2").val()}, function() {
						$("#resultinlinesoldall").load("SellProductAjax", {load: "resultinlinesoldall"}, function() {
							$("#resultinlinecountall").load("SellProductAjax", {load: "resultinlinecountall"});
						}
						);
					});
				});
			});
		});
	});		
}
window.onload = startPage();
//setInterval(startPage, 10000);   


	$(document).ready(function() {
		$(".modalbox").fancybox();
		$("#contact").submit(function() { return false; });
	
	});
	function postz(el, string) {

		var str = (el.id).replace(/\D+/g,"");
		elem = str;
		var name = $("#name"+str).val();
		var firm = $("#firm"+str).val();
		var article = $("#article"+str).val();
		var number = $("#number"+str).val();
		var costoutput = $("#costoutput"+str).val();
		var numbercold = $("#numbercold"+str).val().trim();
		var discount = $("#discount"+str).val().trim();
		var description = "";
		var sum = "0";
		var idn = "1";
		if(string === 'return'){
			sum = $("#sum"+str).val();
			idn = $("#id"+str).val();
			description = $("#description"+str).val().trim();
		}
		var mean = string;
		if(string === 'prewrite'){
			description = $("#description"+str).val().trim();
			string = 'to_sell';
			discount = costoutput;
		}
		
		var numbercoldlen = numbercold.length;
		var discountlen = discount.length;

		if(numbercoldlen < 1) {
			$("#numbercold"+str).addClass("error");
		}
		else if(numbercoldlen > 0){
			$("#numbercold"+str).removeClass("error");
		}
		
		if(discountlen < 1) {
			$("#discountlen"+str).addClass("error");
		}
		else if(discountlen > 0){
			$("#discountlen"+str).removeClass("error");
		}
		
		if((string == 'return' && description.length > 0) || (string == 'to_sell' && (description.length > 0 || costoutput != discount))){
			if(numbercoldlen > 0 && discountlen > 0) {
				$("#empty").load("SellProductAjax", {load: string,
					name:name, firm:firm, article:article,
					costoutput:costoutput, numbercold:numbercold,
					discount:discount, description:description,
					sum:sum, id:idn});
				setTimeout("$.fancybox.close()", 900);
				setTimeout(startPage, 1000);
				if(mean == "to_sell"){
					setTimeout(showSuccessToast, 1200); 
					$("#discount"+str).val("0");
					$("#description"+str).val("");
				}
				else{
					if(mean == "return"){
						setTimeout(showErrorToast, 1200);
						$("#description"+str).val("");
					}
					else{
						$("#discount"+str).val("0");
						$("#description"+str).val("");
						setTimeout(showNoticeToast, 1200);
					}
					
				}
			}
		}
		else{
			alert("При возврате или списании товара нужно указывать причину(описание)");
		}
		return false;
	}
	
	
	function showSuccessToast() {
        $().toastmessage('showSuccessToast', "ИНФОРМАЦИЯ О ПРОДАЖЕ УСПЕШНО ЗАНЕСЕНА В БАЗУ ДАННЫХ. ИНФОРМАЦИЮ НА СТРАНИЦЕ ОБНОВЛЕНО");
    }
	
	
	function showWarningToast() {
	    $().toastmessage('showWarningToast', "В МАГАЗИНЕ НЕТ ДАННОГО ТОВАРА");
	}

	function showErrorToast() {
	    $().toastmessage('showErrorToast', "ИНФОРМАЦИЯ О ВОЗВРАТЕ ЗАНЕСЕНА В БАЗУ ДАННЫХ. СПРАВОЧНИК ТОВАРОВ ОБНОВЛЁН");
	}
	
	function showNoticeToast() {
        $().toastmessage('showNoticeToast', "Информация О СПИСАНИИ УСПЕШНО ЗАНЕСЕНА В БАЗУ ДАННЫХ. ИНФОРМАЦИЮ НА СТРАНИЦЕ ОБНОВЛЕНО");
    }
</script>
<%}%>
</body>
</html>