//Load informacija o adminu
$(document).ready(function () {

    var changeProfileAdminPharmacy = $(".changeProfileAdminPharmacy")
    changeProfileAdminPharmacy.hide();

    var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
    changePasswordAdminPharmacy.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var profileAdminPharmacy = $(".profileAdminPharmacy")
    profileAdminPharmacy.show();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacyadmins/admin",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            $('#firstName').append(data['firstName']);
            $('#lastName').append(data['lastName']);
            $('#email').append(data['email']);
            $('#address').append(data['address']);
            $('#phone').append(data['phone']);
            $('#city').append(data['city']);
            $('#country').append(data['country']);
        },
        error: function (jqXHR) {
            if(jqXHR.status == 403)
            {
                window.location.href="error.html";
            }
            if(jqXHR.status == 401)
            {
                window.location.href="error.html";
            }
        }
    });
});

//Promena profila
$(document).on('click', '#btnChangeProfileAdminPharmacy', function () {
    var profileAdminPharmacy = $(".profileAdminPharmacy")
    profileAdminPharmacy.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changeProfileAdminPharmacy = $(".changeProfileAdminPharmacy")
    changeProfileAdminPharmacy.show();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacyadmins/admin",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            $('#chFirstName:text').val(data['firstName']);
            $('#chLastName:text').val(data['lastName']);
            $('#chAddress:text').val(data['address']);
            $('#chPhone:text').val(data['phone']);
            $('#chCity:text').val(data['city']);
            $('#chCountry:text').val(data['country']);
        },
        error: function (jqXHR) {
            if(jqXHR.status == 403)
            {
                window.location.href="error.html";
            }
            if(jqXHR.status == 401)
            {
                window.location.href="error.html";
            }
        }
    });
});

$(document).on('click', '#btnSaveProfileAdminPharmacy', function () {

    var firstName = $("#chFirstName").val();
    var lastName = $("#chLastName").val();
    var address = $("#chAddress").val();
    var phone = $("#chPhone").val();
    var city = $("#chCity").val();
    var country = $("#chCountry").val();

    var myJSON = formToJSON(firstName, lastName, address, phone, city, country);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacyadmins/adminChangeInfo",
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

            window.location.href="adminPharmacyProfile.html";
        },
        error: function (jqXHR) {
            if(jqXHR.status == 403)
            {
                window.location.href="error.html";
            }
            if(jqXHR.status == 401)
            {
                window.location.href="error.html";
            }
        }
    });
});

$(document).on('click', '#btnCancelChanges', function () {
    var changeProfileAdminPharmacy = $(".changeProfileAdminPharmacy")
    changeProfileAdminPharmacy.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
    changePasswordAdminPharmacy.hide();

    var profileAdminPharmacy = $(".profileAdminPharmacy")
    profileAdminPharmacy.show();
});

function formToJSON(firstName, lastName, address, phone, city, country) {
    return JSON.stringify(
        {
            "firstName" : firstName,
            "lastName" : lastName,
            "address" : address,
            "phone" : phone,
            "city" : city,
            "country" : country
        }
    );
}

//Promena lozinke
$(document).on('click', '#btnChangePasswordAdminPharmacy', function () {

    var profileAdminPharmacy = $(".profileAdminPharmacy")
    profileAdminPharmacy.hide();

    var changeProfileAdminPharmacy = $(".changeProfileAdminPharmacy")
    changeProfileAdminPharmacy.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
    changePasswordAdminPharmacy.show();

});

$(document).on('click', '#btnSavePasswordAdminPharmacy', function () {

    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#chPassword").val();
    var repeatedPassword = $("#repeatPassword").val();

    var myJSON = formToJSON1(oldPassword, newPassword);

    console.log(myJSON);

    if (newPassword === repeatedPassword && newPassword !== "") {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/pharmacyadmins/change-password",
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
                var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
                changePasswordAdminPharmacy.hide();
                var passwordChangeSuccess = $(".passwordChangeSuccess")
                passwordChangeSuccess.show();
            },
            error: function (jqXHR) {
                if(jqXHR.status === 500)
                {
                    var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
                    changePasswordAdminPharmacy.hide();
                    var passwordChangeError = $(".passwordChangeError")
                    passwordChangeError.show();
                }
                if(jqXHR.status === 401)
                {
                    window.location.href="error.html";
                }
            }
        });
    }
    else {
        var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
        changePasswordAdminPharmacy.hide();
        var passwordChangeError = $(".passwordChangeError")
        passwordChangeError.show();
    }
});

$(document).on('click', '#btnCancelPassword', function () {
    var changeProfileAdminPharmacy = $(".changeProfileAdminPharmacy")
    changeProfileAdminPharmacy.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
    changePasswordAdminPharmacy.hide();

    var profileAdminPharmacy = $(".profileAdminPharmacy")
    profileAdminPharmacy.show();
});

function formToJSON1(oldPassword, newPassword) {
    return JSON.stringify(
        {
            "oldPassword" : oldPassword,
            "newPassword" : newPassword
        }
    );
}

$(document).on('click', '#btnTryAgain', function () {
    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();
    var changePasswordAdminPharmacy = $(".changePasswordAdminPharmacy")
    changePasswordAdminPharmacy.show();
});
