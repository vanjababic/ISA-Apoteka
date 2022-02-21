$(document).ready(function () {
    $('#tableCalendar').empty();

    header = $(".header")
    header.show();
});

$(document).on('click', '#btnCalendarWeek', function () {

    $('#tableCalendar').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/appointments/getAppointmentsWeek",
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
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";

                $('#tableCalendar').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });

});

$(document).on('click', '#btnCalendarMonth', function () {

    $('#tableCalendar').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/appointments/getAppointmentsMonth",
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
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";

                $('#tableCalendar').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });

});

$(document).on('click', '#btnCalendarYear', function () {

    $('#tableCalendar').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/appointments/getAppointmentsYear",
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
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";

                $('#tableCalendar').append(row);

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