function but(){
	if(document.getElementById("LoginErr").style.display == "none" &&
			document.getElementById("PassErr").style.display == "none"){
		document.getElementById("login").style.display = "inline";
	}
	else{
		document.getElementById("login").style.display = "none";
	}
}

function CheckLogin(f) {
	if (f.value.length < 6){
		document.getElementById("LoginErr").style.display = "inline";
	}
    else{
    	document.getElementById("LoginErr").style.display = "none";
    }
	but();
}

function CheckPass(f) {
	if (f.value.length < 8){
		document.getElementById("PassErr").style.display = "inline";
	}
    else{
    	document.getElementById("PassErr").style.display = "none";
    }
	but();
}

