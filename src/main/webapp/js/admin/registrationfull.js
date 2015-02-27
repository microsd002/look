window.onload = function() {
	document.getElementsByName("first_name")[0].focus();
}



function CheckFirstName() {
	text = document.getElementsByName("first_name")[0].value;
	outEl= document.getElementById("first_name_span"), error = "";

	if (text === "") error = "ПОЛЕ ИМЕНИ ПУСТОЕ";
	else if (text.length < 2) error = "* имя должно быть больше 2 символа";
	
	outEl.textContent = error;
	if (error !== "") {
		outEl.style = "color: red";
		document.getElementsByName("first_name")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.style = "";
		document.getElementsByName("first_name")[0].style = "";
	}
}

function CheckSecondName() {
	text = document.getElementsByName("second_name")[0].value;
	outEl= document.getElementById("second_name_span");
	
	if (text.length < 4) {
		outEl.textContent = "* отчество должно быть больше 4 символов";
		outEl.style = "color: red";
		document.getElementsByName("second_name")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("second_name")[0].style = "";
	}
}

function CheckThreeName() {
	text = document.getElementsByName("three_name")[0].value;
	outEl= document.getElementById("three_name_span");
	
	if (text.length < 3) {
		outEl.textContent = "* фамилия должна быть больше 3 символов";
		outEl.style = "color: red";
		document.getElementsByName("three_name")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("three_name")[0].style = "";
	}
}

function CheckTel() {
	text = document.getElementsByName("tel")[0].value;
	outEl= document.getElementById("tel_span");
	
	if (text.length < 6) {
		outEl.textContent = "* телефон должен быть больше 6 символов";
		outEl.style = "color: red";
		document.getElementsByName("tel")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("tel")[0].style = "";
	}
}

function CheckLogin() {
	text = document.getElementsByName("username_registration")[0].value;
	outEl= document.getElementById("username_registration_span"), error = "";
	
	if (text === "") error = "ПОЛЕ ЛОГИНА ПУСТОЕ";
	else if (text.length < 5) error = "* логин должен быть больше 5 символов";
	
	outEl.textContent = error;
	if (error !== "") {
		outEl.style = "color: red";
		document.getElementsByName("username_registration")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.style = "";
		document.getElementsByName("username_registration")[0].style = "";
	}
}

function CheckPassword() {
	text = document.getElementsByName("password_registration")[0].value;
	outEl= document.getElementById("password_registration_span");
	
	if (text.length < 8) {
		outEl.textContent = "* пароль должен быть больше 8 символов";
		outEl.style = "color: red";
		document.getElementsByName("password_registration")[0].style = "background-color: tomato; border-color: red";
	} else {
		outEl.textContent = "";
		outEl.style = "";
		document.getElementsByName("password_registration")[0].style = "";
	}
}

