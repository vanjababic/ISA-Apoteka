$(document).ready(function () {
    $('#pharmacies tbody').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/patients/allpharmaciespromotion",
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

                    var btn = "<button class='unsubscribe' id = " + data[i]['id'] + ">Odjava na akcije</button>";

                    row += "<td>" + btn + "</td>";
                    $('#pharmacies').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '.unsubscribe', function () {

        var pharmacyId=this.id;

        var pharmacyJSON = formToJSON(pharmacyId);
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/patients/unsubscribe",
            dataType: "json",
            contentType: "application/json",
            data: pharmacyJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function () {
                alert("Uspesna odjava akcije");
                window.location.href = "patientWelcome.html";
            },
            error: function (error, data) {
                console.log(data);
                alert(error);
            }
        });
    });



function formToJSON(pharmacyID) {
    return JSON.stringify(
        {
            "id":pharmacyID


        }
    );
};