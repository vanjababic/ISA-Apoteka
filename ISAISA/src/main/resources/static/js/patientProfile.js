$(document).ready(function () {

    var changeProfilePatient = $(".changeProfilePatient")
    changeProfilePatient.hide();

    var changePasswordPatient = $(".changePasswordPatient")
    changePasswordPatient.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var alergiepatient=$(".alergiePatient")
    alergiepatient.show();

    var profilePatient = $(".profilePatient")
    profilePatient.show();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/patients/patient",
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


//Promena profila
$(document).on('click', '#btnChangeProfilePatient', function () {

    var profilePatient = $(".profilePatient")
    profilePatient.hide();

    var changeProfilePatient = $(".changeProfilePatient")
    changeProfilePatient.show();
    
    var changePasswordPatient = $(".changePasswordPatient")
    changePasswordPatient.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var alergiepatient=$(".alergiePatient")
    alergiepatient.hide();


    var profilePatient = $(".profilePatient")
    profilePatient.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/patients/patient",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log(data);

            $('#chFirstName').append(data['firstName']);
            $('#chLastName').append(data['lastName']);
            $('#chAddress').append(data['address']);
            $('#chPhone').append(data['phone']);
            $('#chCity').append(data['city']);
            $('#chCountry').append(data['country']);


        },
        error: function (data) {
            window.location.href="error.html";
        }
    });
});

$(document).on('click', '#btnSaveProfilePatient', function () {

    var firstName = $("#chFirstName").val();
    var lastName = $("#chLastName").val();
    var address = $("#chAddress").val();
    var phone = $("#chPhone").val();
    var city = $("#chCity").val();
    var country = $("#chCountry").val();

    var myJSON = formToJSON(firstName, lastName, address, phone, city, country);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/patients/patientChangeInfo",
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

            window.location.href="patientProfile.html";
        },
        error: function () {
            window.location.href="error.html";
        }
    });
});

$(document).on('click', '#btnCancelChanges', function () {
    var profilePatient = $(".profilePatient")
    profilePatient.hide();

    var alergiepatient=$(".alergiePatient")
    alergiepatient.hide();

    var changeProfilePatient = $(".changeProfilePatient")
    changeProfilePatient.hide();

    var changePasswordPatient = $(".changePasswordPatient")
    changePasswordPatient.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var profilePatient = $(".profilePatient")
    profilePatient.hide();
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
$(document).on('click', '#btnChangePasswordPatient', function () {

    var profilePatient = $(".profilePatient")
    profilePatient.hide();

    var changeProfilePatient = $(".changeProfilePatient")
    changeProfilePatient.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();
    var alergiepatient=$(".alergiePatient")
    alergiepatient.hide();

    var changePasswordPatient = $(".changePasswordPatient")
    changePasswordPatient.show();

});


$(document).on('click', '#btnSavePasswordPatient', function () {

    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#chPassword").val();
    var repeatedPassword = $("#repeatPassword").val();

    var myJSON = formToJSON1(oldPassword, newPassword);

    console.log(myJSON);

    if (newPassword === repeatedPassword) {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/patients/change-password",
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
                var changePasswordPatient = $(".changePasswordPatient")
                changePasswordPatient.hide();
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
        var changePasswordPatient = $(".changePasswordPatient")
        changePasswordPatient.hide();
        var passwordChangeError = $(".passwordChangeError")
        passwordChangeError.show();
    }
});

$(document).on('click', '#btnCancelPassword', function () {
    var changeProfilePatient = $(".changeProfilePatient")
    changeProfilePatient.hide();

    var passwordChangeSuccess = $(".passwordChangeSuccess")
    passwordChangeSuccess.hide();

    var passwordChangeError = $(".passwordChangeError")
    passwordChangeError.hide();

    var changePasswordPatient = $(".changePasswordPatient")
    changePasswordPatient.hide();

    var profilePatient = $(".profilePatient")
    profilePatient.show();
});

$(document).on('click', '#btnSaveAlergie', function () {

    var allergiemedication = $("#allergiemedication").val();


    var myJSON = formToJSON2(allergiemedication);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/patients/allergiemedication",
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

            window.location.href="patientProfile.html";
        },
        error: function () {
            window.location.href="error.html";
        }
    });
});

function formToJSON1(oldPassword, newPassword) {
    return JSON.stringify(
        {
            "oldPassword" : oldPassword,
            "newPassword" : newPassword
        }
    );
}
function formToJSON2(allergiemedication) {
    return JSON.stringify(
        {
            "allergiemedication" : allergiemedication

        }
    );
}

