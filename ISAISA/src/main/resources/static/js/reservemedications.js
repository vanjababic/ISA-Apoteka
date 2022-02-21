$(document).on('click', '#btnSearchMedication', function () {

    var name = $("#name").val();


    var myJSON = formToJSON(name)

    $('#tableMedicationsSearch').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacy/PharmaciesSearchforReservations",
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
           
            for (i = 0; i < data.length; i++) {
                var row = "<tr>"
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";

                var btn = "<button class='btnReserveMedication' id = " + data[i]['id'] + ">Reserve Medication</button>";
                row += "<td>" + btn + "</td>";
                $('#tableMedicationsSearch').append(row);
            }

        },
        error: function (data) {
            console.log("ERROR: ", data);
            window.location.href="error.html";
        }
    });

});

$(document).on('click', '.btnReserveMedication', function () {

    var name = $("#name").val();
    var id=this.id;
    var dateofreservation=$("#dateofreservation").val();


    var myJSON = formToJSON2(name,id,dateofreservation)


    $.ajax({
        type: "POST",
        url: "http://localhost:8081/reservations/createReservation",
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
            alert("uspesno rezervisan lek");
        },
        error: function (data) {
            console.log("ERROR: ", data);
            window.location.href="error.html";
        }
    });

});

function formToJSON(name) {
    return JSON.stringify(
        {
            "name": name

        }
    );
}

function formToJSON2(name,id,dateofreservation) {
    return JSON.stringify(
        {
            "name": name,
            "dateofreservation":dateofreservation,
            "id":id

        }
    );
}