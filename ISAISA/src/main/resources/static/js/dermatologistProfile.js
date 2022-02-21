$(document).ready(function () {

    var changeProfileDermatologist = $(".changeProfileDermatologist")
    changeProfileDermatologist.hide();

    var changePasswordDermatologist = $(".changePasswordDermatologist")
    changePasswordDermatologist.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var profileDermatologist = $(".profileDermatologist")
    profileDermatologist.show();

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

            $('#firstName').append(data['firstName']);
            $('#lastName').append(data['lastName']);
            $('#email').append(data['email']);
            $('#address').append(data['address']);
            $('#phone').append(data['phone']);
            $('#city').append(data['city']);
            $('#country').append(data['country']);
            $('#pharmacy').append(data['pharmacy']);
        },
        error: function (data) {
            window.location.href="error.html";
        }
    });
});

$(document).on('click', '#btnChangeProfileDermatologist', function () {

    var profileDermatologist = $(".profileDermatologist")
    profileDermatologist.hide();

    var changeProfileDermatologist = $(".changeProfileDermatologist")
    changeProfileDermatologist.show();

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
            $('#chFirstName:text').val(data['firstName']);
            $('#chLastName:text').val(data['lastName']);
            $('#chEmail:text').val(data['email']);
            $('#chAddress:text').val(data['address']);
            $('#chPhone:text').val(data['phone']);
            $('#chCity:text').val(data['city']);
            $('#chCountry:text').val(data['country']);
        },
        error: function (data) {
            window.location.href="error.html";
        }
    });

});

$(document).on('click', '#btnSaveProfileDermatologist', function () {

    var firstName = $("#chFirstName").val();
    var lastName = $("#chLastName").val();
    var address = $("#chAddress").val();
    var phone = $("#chPhone").val();
    var city = $("#chCity").val();
    var country = $("#chCountry").val();

    var myJSON = formToJSON(firstName, lastName, address, phone, city, country);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/dermatologists/dermatologistChange",
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
            alert("Izmene su sacuvane");

            window.location.href="dermatologistProfile.html";
        },
        error: function (data) {
            window.location.href="error.html";
        }
    });
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

$(document).on('click', '#btnCancelChanges', function () {
    var changeProfileDermatologist = $(".changeProfileDermatologist")
    changeProfileDermatologist.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordDermatologist = $(".changePasswordDermatologist")
    changePasswordDermatologist.hide();

    var profileDermatologist = $(".profileDermatologist")
    profileDermatologist.show();
});

//promena pass
$(document).on('click', '#btnChangePasswordDermatologist', function () {

    var profileDermatologist = $(".profileDermatologist")
    profileDermatologist.hide();

    var changeProfileDermatologist = $(".changeProfileDermatologist")
    changeProfileDermatologist.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordDermatologist = $(".changePasswordDermatologist")
    changePasswordDermatologist.show();

});

$(document).on('click', '#btnCancelPassword', function () {
    var changeProfileDermatologist = $(".changeProfileDermatologist")
    changeProfileDermatologist.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordDermatologist = $(".changePasswordDermatologist")
    changePasswordDermatologist.hide();

    var profileDermatologist = $(".profileDermatologist")
    profileDermatologist.show();
});

$(document).on('click', '#btnSavePasswordDermatologist', function () {

    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#chPassword").val();
    var repeatedPassword = $("#repeatPassword").val();

    var myJSON = formToJSON1(oldPassword, newPassword);

    console.log(myJSON);

    if (newPassword === repeatedPassword) {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/dermatologists/changepass",
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
                var changePasswordDermatologist = $(".changePasswordDermatologist")
                changePasswordDermatologist.hide();
                var passwordChangeSuccess = $(".passwordChangeSuccess")
                passwordChangeSuccess.show();
            },
            error: function (data) {
                console.log("ERROR: ", data)
                window.location.href="error.html";
            }
        });
    }
    else {
        var changePasswordDermatologist = $(".changePasswordDermatologist")
        changePasswordDermatologist.hide();
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