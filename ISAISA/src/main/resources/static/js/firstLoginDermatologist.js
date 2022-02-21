$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/dermatologists/dermatologist",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            if(data['flag']==0){
                console.log(data['flag'])
                window.location.href="changePassDermatologist.html";
            }
            if(data['flag']==1){
                console.log(data['flag'])
                window.location.href="welcomeDermatologist.html";
            }
        },
        error: function (data) {
            console.log('ERROR:', data);
            window.location.href="error.html";
        }
    });
});