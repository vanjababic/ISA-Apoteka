$(document).ready(function () {
    $('#tableExaminatedPatients').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/examinations/getExaminatedPatientsDermatologist",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['date'] + "</td>";

                $('#tableExaminatedPatients').append(row);
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#btnSortName', function () {

    $('#tableExaminatedPatients').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/examinations/getExaminatedPatientsDermatologistSortName",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['date'] + "</td>";

                $('#tableExaminatedPatients').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#btnSortLastName', function () {

    $('#tableExaminatedPatients').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/examinations/getExaminatedPatientsDermatologistSortLastName",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['date'] + "</td>";

                $('#tableExaminatedPatients').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});


$(document).on('click', '#btnSortDate', function () {

    $('#tableExaminatedPatients').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/examinations/getExaminatedPatientsDermatologistSortDate",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['date'] + "</td>";

                $('#tableExaminatedPatients').append(row);
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#btnBack', function () {
    window.location.href="welcomeDermatologist.html";

});