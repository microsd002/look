window.onload = function() {
	document.getElementsByName("name_entrance")[0].focus();
}

function CheckNameEntrance() {
	text = document.getElementsByName("name_entrance")[0].value;
	outEl= document.getElementById("name_entrance1"), error = "";
	
	if (text === "") error = "* Введите название товара";
	else if (text.length < 3) error = "* Название товара должно быть больше 3 символов";
	
	outEl.textContent = error;
	if (error !== "") {
		outEl.style = "color: red";
		document.getElementsByName("name_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.style = "";
		document.getElementsByName("name_entrance")[0].style = "";
	}
}

function CheckFirmEntrance() {
	text = document.getElementsByName("firm_entrance")[0].value;
	outEl= document.getElementById("firm_entrance1");
	
	if (text.length < 2) {
		outEl.textContent = "* Фирма должна быть больше 2 символов";
		outEl.style = "color: red";
		document.getElementsByName("firm_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("firm_entrance")[0].style = "";
	}
}

function CheckProviderEntrance() {
	text = document.getElementsByName("provider_entrance")[0].value;
	outEl= document.getElementById("provider_entrance1");
	
	if (text.length < 3) {
		outEl.textContent = "* Поставщик должен быть больше 3 символов";
		outEl.style = "color: red";
		document.getElementsByName("provider_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("provider_entrance")[0].style = "";
	}
}

function CheckArticleEntrance() {
	text = document.getElementsByName("article_entrance")[0].value;
	outEl= document.getElementById("article_entrance1");
	
	if (text.length < 1) {
		outEl.textContent = "* Поле Артикул должен быть обязательно заполнено";
		outEl.style = "color: red";
		document.getElementsByName("article_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("article_entrance")[0].style = "";
	}
}

function CheckNumberEntrance() {
	text = document.getElementsByName("number_entrance")[0].value;
	outEl= document.getElementById("number_entrance1"), error = "";
	
	if (text === "") error = "* Поле Количество не может быть пустым";
	else if (text.length < 1) error = "* Поле Количество не может быть пустым";
	
	outEl.textContent = error;
	if (error !== "") {
		outEl.style = "color: red";
		document.getElementsByName("number_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.style = "";
		document.getElementsByName("number_entrance")[0].style = "";
	}
}

function CheckCostEntrance() {
	text = document.getElementsByName("cost_entrance")[0].value;
	outEl= document.getElementById("cost_entrance1");
	
	if (text.length < 1) {
		outEl.textContent = "* Поле Цена Закупки не может быть пустым";
		outEl.style = "color: red";
		document.getElementsByName("cost_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("cost_entrance")[0].style = "";
	}
}

function CheckCostSell() {
	text = document.getElementsByName("cost_out_put_entrance")[0].value;
	outEl= document.getElementById("cost_out_put_entrance1");
	
	if (text.length < 1) {
		outEl.textContent = "* Поле Цена Продажи не может быть пустым";
		outEl.style = "color: red";
		document.getElementsByName("cost_out_put_entrance")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("cost_out_put_entrance")[0].style = "";
	}
}

