$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/reservations/reservedMedications",
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
                row += "<td>" + data[i]['patient']['firstName'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['name'] + "</td>";
                row += "<td>" + data[i]['medication']['name'] + "</td>";
                row += "<td>" + data[i]['dateofreservation'] + "</td>";
                row += "<td>" + data[i]['medicationtaken'] + "</td>";



                var btn = "<button class='btnCancelReservation' id = " + data[i]['id'] + ">Otkazi rezervaciju</button>";


                row += "<td>" + btn + "</td>";
                $('#reservedmedications').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});
$(document).on('click', '.btnCancelReservation', function(){
    var id=this.id;
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/reservations/cancelreservation",
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
            window.location.href = "patientWelcome.html";

        },
        error: function (error) {
            alert(error);
        }
    });

});
