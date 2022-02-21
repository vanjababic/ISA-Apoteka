$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacists/pharmacist",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            if(data['flag']==0){
                console.log(data['flag'])
                window.location.href="changePassPharmacist.html";
            }
            if(data['flag']==1){
                console.log(data['flag'])
                window.location.href="welcomePharmacist.html";
            }
        },
        error: function (data) {
            console.log('ERROR:', data);
            window.location.href="error.html";
        }
    });
});