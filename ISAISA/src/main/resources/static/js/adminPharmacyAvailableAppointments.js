$(document).ready(function () {

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    dermatologistsShowAdminPharmacy.show();

    var availableAppointments = $(".availableAppointments");
    availableAppointments.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/dermatologists/adminDermatologists",
        dataType: "json",
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";

                var btn = "<button class='btnCreateAvailableAppointmentForDermatologist' id = " + data[i]['id'] + ">Kreiraj slobodan termin</button>";
                row += "<td>" + btn + "</td>";

                $('#tableDermatologistsAdminPharmacy').append(row);
            }
        },
        error: function (jqXHR) {
            if(jqXHR.status === 403)
            {
                window.location.href="error.html";
            }
            if(jqXHR.status === 401)
            {
                window.location.href="error.html";
            }
        }
    });
});

$(document).on('click', '.btnCreateAvailableAppointmentForDermatologist', function () {
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    dermatologistsShowAdminPharmacy.hide();

    var availableAppointments = $(".availableAppointments");
    availableAppointments.show();

    var chooseDateAvailableAppointments = $(".chooseDateAvailableAppointments");
    chooseDateAvailableAppointments.show();

    var successfulAppointmentCreation = $(".successfulAppointmentCreation");
    successfulAppointmentCreation.hide();

    var unsuccessfulAppointmentCreation = $(".unsuccessfulAppointmentCreation");
    unsuccessfulAppointmentCreation.hide();

    var dermatologist_id = this.id;

    $(document).on('click', '#btnSubmitNewAvailableAppointment', function () {
        var beginofappointment = $("#dateBegin").val();
        var duration = $("#duration").val();
        var price = $("#price").val();

        console.log(beginofappointment, duration);

        var myJSON = formToJSON(dermatologist_id, beginofappointment, duration, price);

        $("#dateBegin").val("");
        $("#duration").val("");
        $("#price").val("");
        $('#responseMessageAppointmentCreation').val("");
        beginofappointment = Date.parse(beginofappointment);

        if(beginofappointment != null && beginofappointment > Date.now() && duration !== "" && price !== "" && duration > 0 && price > 0) {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/appointments/createAvailableAppointment",
                dataType: "json",
                contentType: "application/json",
                data: myJSON,
                beforeSend: function (xhr) {
                    if (localStorage.token) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                    }
                },
                success: function (data) {
                    console.log("SUCCESS", data);
                    var successfulAppointmentCreation = $(".successfulAppointmentCreation");
                    successfulAppointmentCreation.show();
                    var chooseDateAvailableAppointments = $(".chooseDateAvailableAppointments");
                    chooseDateAvailableAppointments.hide();
                },
                error: function (jqXHR) {
                    if (jqXHR.status === 403) {
                        window.location.href = "error.html";
                    }
                    if (jqXHR.status === 401) {
                        window.location.href = "error.html";
                    }
                    if (jqXHR.status === 500) {
                        console.log(jqXHR.responseText);
                        var unsuccessfulAppointmentCreation = $(".unsuccessfulAppointmentCreation");
                        unsuccessfulAppointmentCreation.show();
                        var chooseDateAvailableAppointments = $(".chooseDateAvailableAppointments");
                        chooseDateAvailableAppointments.hide();
                        var response = JSON.parse(jqXHR.responseText);
                        $('#responseMessageAppointmentCreation').append(response['message']);
                    }
                }
            });
        }
    });
});

function formToJSON(dermatologist_id, beginofappointment, duration, price) {
    return JSON.stringify(
        {
            "dermatologist" : dermatologist_id,
            "beginofappointment" : beginofappointment,
            "duration" : duration,
            "price" : price
        }
    );
}

$(document).on('click', '#btnCancelNewAvailableAppointment', function () {
    var chooseDateAvailableAppointments = $(".chooseDateAvailableAppointments");
    chooseDateAvailableAppointments.hide();

    var unsuccessfulAppointmentCreation = $(".unsuccessfulAppointmentCreation");
    unsuccessfulAppointmentCreation.hide();

    var successfulAppointmentCreation = $(".successfulAppointmentCreation");
    successfulAppointmentCreation.hide();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    dermatologistsShowAdminPharmacy.show();

});

$(document).on('click', '#btnReturnToChoosing1', function () {
    var unsuccessfulAppointmentCreation = $(".unsuccessfulAppointmentCreation");
    unsuccessfulAppointmentCreation.hide();

    var availableAppointments = $(".availableAppointments");
    availableAppointments.hide();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    dermatologistsShowAdminPharmacy.show();
});

$(document).on('click', '#btnReturnToChoosing', function () {
    var successfulAppointmentCreation = $(".successfulAppointmentCreation");
    successfulAppointmentCreation.hide();

    var availableAppointments = $(".availableAppointments");
    availableAppointments.hide();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    dermatologistsShowAdminPharmacy.show();
});