$(document).ready(function () {
    unesiPretragu = $(".unesiPretragu")
    unesiPretragu.show();
    var patientsShowAll = $(".patientsShowAll")
    patientsShowAll.show();
    var patientsShowSearch = $(".patientsShowSearch")
    patientsShowSearch.hide();
    var appointmentStart = $(".appointmentStart")
    appointmentStart.hide();
    var appoinmtentDoesNotExist = $(".appoinmtentDoesNotExist")
    appoinmtentDoesNotExist.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/patients/allSearchPatientsDerma",
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
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['city'] + "</td>";
                row += "<td>" + data[i]['phone'] + "</td>";
                $('#tablePatientsShow').append(row);

            }
        },
        error: function (data) {
            console.log("ERROR", data);
            //window.location.href = "error.html";
        }
    });
});

$(document).on('click', '#btnSearchPatient', function () {

    unesiPretragu = $(".unesiPretragu")
    unesiPretragu.show();
    var patientsShowAll = $(".patientsShowAll")
    patientsShowAll.hide();
    var patientsShowSearch = $(".patientsShowSearch")
    patientsShowSearch.show();
    var appointmentStart = $(".appointmentStart")
    appointmentStart.hide();
    var appoinmtentDoesNotExist = $(".appoinmtentDoesNotExist")
    appoinmtentDoesNotExist.hide();

    var searchParam = $("#patientSearch").val();
    searchParam = searchParam.split(" ");

    var myJSON = formToJSON(searchParam[0], searchParam[1])

    $('#searchPatient').empty();
    $('#tablepatientsShowSearch tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/patients/searchPatientsDerma",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);
            $('#searchPatient').append(searchParam[0], " ", searchParam[1]);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['city'] + "</td>";
                row += "<td>" + data[i]['phone'] + "</td>";


                var btn = "<button class='btnStartAppointment' id = " + data[i]['id'] + ">Zapocni pregled ako postoji</button>";
                row += "<td>" + btn + "</td>";
                $('#tablepatientsShowSearch').append(row);
            }

        },
        error: function (data) {
            //console.log("ERROR: ", data);
            window.location.href="error.html";
        }
    });

});

function formToJSON(firstName, lastName) {
    return JSON.stringify(
        {
            "firstName": firstName,
            "lastName": lastName
        }
    );
}

$(document).on('click', '.btnStartAppointment', function () {

    unesiPretragu = $(".unesiPretragu")
    unesiPretragu.hide();
    var patientsShowAll = $(".patientsShowAll")
    patientsShowAll.hide();
    var patientsShowSearch = $(".patientsShowSearch")
    patientsShowSearch.hide();
    var appointmentStart = $(".appointmentStart")
    appointmentStart.hide();
    var appoinmtentDoesNotExist = $(".appoinmtentDoesNotExist")
    appoinmtentDoesNotExist.hide();
    var id = this.id;
    //var idd = formToJSON(id);
    var idd= JSON.stringify({"id":id});
    //console.log(idd);
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/checkIfAppointmentExists",
        dataType: "json",
        contentType: "application/json",
        data: idd,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }},
        success: function (data) {
            console.log("SUCCESS : ", data);
            if( data['id'] != null) {
                console.log("SUCCESS : ", data);
                appointmentStart.show();
                var btnStart = "<button class='btnStart' id = " + data['id'] + ">Zapocni pregled</button>";
                var btnPenal = "<button class='btnPenal' id = " + data['id'] + ">Pacijent se nije pojavio</button>";
                var btnCancle = "<button class='btnCancle' id = " + data['id'] + ">Odustani od pregleda</button>";

                var row = "<tr>";
                row += "<td>" + btnStart + "</td>";
                row += "<td>" + btnPenal + "</td>";
                row += "<td>" + btnCancle + "</td>";

                $('#tableAppointmentExists').append(row);
            }
            },
        error: function () {
            appoinmtentDoesNotExist.show();
        }
    });
});

$(document).on('click', '.btnCancle', function () {
    window.location.href = "welcomeDermatologist.html";

});
$(document).on('click', '.btnPenal', function () {

    /*var patientsShowAll = $(".patientsShowAll")
    patientsShowAll.hide();
    var patientsShowSearch = $(".patientsShowSearch")
    patientsShowSearch.hide();
    var appointmentStart = $(".appointentStart")
    appointmentStart.hide();
    var appointentDoesNotExist = $(".appointentDoesNotExist")
    appointentDoesNotExist.hide();*/

    var id = this.id;
    var idd= JSON.stringify({"id":id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/penalPatient",
        dataType: "json",
        contentType: "application/json",
        data: idd,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }},
        success: function (data) {
            console.log("SUCCESS : ", data);
            window.location.href="welcomeDermatologist.html";

        },
        error: function () {
            window.location.href="error.html";

        }
    });
});

$(document).on('click', '.btnStart', function () {
    window.location.href="examinationDermatologist.html";

});