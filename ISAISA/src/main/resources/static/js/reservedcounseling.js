$(document).ready(function () {
    $('#reservedappointments').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/counselings/reservedcounseling",
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
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";
                row += "<td>" + data[i]['pharmacist']['firstName'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";


                var btn = "<button class='btnCancelAppointment' id = " + data[i]['id'] + ">Otkazi termin</button>";


                row += "<td>" + btn + "</td>";
                $('#reservedappointments').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});
$(document).on('click', '.btnCancelAppointment', function(){
    var id=this.id;
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/cancelcounseling",
        //dataType: "json",
        contentType: "application/json",
        data:id,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function () {
            alert("success");
            window.location.href="patientWelcome.html";

        },
        error: function (error) {
            alert(error);
        }
    });

});
$(document).on('click', '#btnSortbyprice', function(){
    $('#reservedappointments').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/counselings/reservedcounselingbyprice",
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
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";
                row += "<td>" + data[i]['pharmacist']['firstName'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";


                var btn = "<button class='btnCancelAppointment' id = " + data[i]['id'] + ">Otkazi termin</button>";


                row += "<td>" + btn + "</td>";
                $('#reservedappointments').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});
