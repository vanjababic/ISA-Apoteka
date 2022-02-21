$(document).ready(function () {

    $('#btnLogin').click(function () {

    	event.preventDefault();
        
	    var email = $("#email").val();
	    var password = $("#password").val();
	
	    var myJSON = formToJSON(email, password);
	
	    $.ajax({
	        type: "POST",
	        url: "http://localhost:8081/auth/login",
	        dataType: "json",
	        contentType: "application/json",
	        data: myJSON,
	        success: function (data) {

				if(data['enabled']===false){
					alert("Nije aktiviran nalog")
					window.location.href = "index.html";
				}

	            console.log(data);
	
	            alert(email + " je uspešno ulogovan");

				localStorage.setItem('token', data['accessToken']);
				localStorage.setItem('role', data['role']);

				if (data['role']==='adminpharmacy'){
					window.location.href = "adminPharmacyWelcome.html";
				}
				else if(data['role']==='patient'){
					window.location.href = "patientWelcome.html";
				}
				else if(data['role']==='dermatologist'){
					window.location.href = "checkLoginDermatologist.html";
				}
				else if(data['role']==='pharmacist') {
					window.location.href = "checkLoginPharmacist.html";
				}
				else if(data['role']==='adminsystem'){
					window.location.href = "adminSystemHomePage.html";
				}
				else if(data['role']==='supplier'){
					window.location.href = "supplierHomePage.html";
				}
				else{
					window.location.href = "error.html";
				}
	        },
	        error: function (data) {
	            console.log(data);
	            alert("Greska");
	        }
	    });
        
    })
})

function formToJSON(email, password) {
    return JSON.stringify(
        {
            "email": email,
            "password": password
        }
    );
}