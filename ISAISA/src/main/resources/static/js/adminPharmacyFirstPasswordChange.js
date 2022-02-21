$(document).on('click', '#btnSavePasswordAdmin', function () {

    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#chPassword").val();
    var repeatedPassword = $("#repeatPassword").val();

    var myJSON = formToJSON1(oldPassword, newPassword);

    console.log(myJSON);

    if (newPassword === repeatedPassword) {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/pharmacyadmins/change-password-firsttime",
            dataType: "json",
            contentType: "application/json",
            data: myJSON,
            beforeSend: function(xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function (data) {
                console.log("SUCCESS: ", data);
                var changePasswordAdmin = $(".changePasswordAdmin")
                changePasswordAdmin.hide();
                var passwordChangeSuccess = $(".passwordChangeSuccess")
                passwordChangeSuccess.show();

            },
            error: function (jqXHR, data) {
                if(jqXHR.status === 403)
                {
                    window.location.href="error.html";
                }
                if(jqXHR.status === 401)
                {
                    var changePasswordAdmin = $(".changePasswordAdmin")
                    changePasswordAdmin.hide();
                    var passwordChangeError = $(".passwordChangeError")
                    passwordChangeError.show();
                }
            }
        });
    }
    else {
        var changePasswordAdmin = $(".changePasswordAdmin")
        changePasswordAdmin.hide();
        var passwordChangeError = $(".passwordChangeError")
        passwordChangeError.show();
    }
});


function formToJSON1(oldPassword, newPassword) {
    return JSON.stringify(
        {
            "oldPassword" : oldPassword,
            "newPassword" : newPassword
        }
    );
}