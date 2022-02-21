$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/medications/getTypeMedication",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var name = data[i]['type'];
                $("#idDropDown").append("<option value='" + name + "'>" + name + "</option>");
            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });


    $.ajax({
        type: "GET",
        url: "http://localhost:8081/medications/allmedicationss",
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";

                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['type_med'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";



                $('#medication').append(row);
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});
$(document).on('click', '#btnSearchMedication', function () {

    var searchParam = $("#searchMedication").val();


    var myJSON = formToJSON(searchParam)

    $('#tableMedicationsSearch tbody').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/medications/MedicationsSearchh",
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
            $('#searchMedication').append(searchParam);

            for (i = 0; i < data.length; i++) {
                var row = "<tr>";

                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['type_med'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";


                var btn = "<button class='pharmacies' id = " + data[i]['id'] + ">Cene po apotekama</button>";
                var btn1 = "<button class='specs' id = " + data[i]['id'] + ">Specifikacija</button>";

                row += "<td>" + btn + "</td>";
                row += "<td>" + btn1 + "</td>";
                $('#tableMedicationsSearch').append(row);
            }

        },
        error: function (data) {
            console.log("ERROR: ", data);
            window.location.href="error.html";
        }
    });

});


$(document).on('click', '.pharmacies', function () {

    var pharmacies = $(".veliki")
    pharmacies.hide();
    var pharmacies = $(".mali")
    pharmacies.show();

    $('#tablePharmacies tbody').empty();

    var medicationId=this.id;

    var medicationJSON = formToJSON1(medicationId);
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/medications/getpharmaciesprice",
        dataType: "json",
        contentType: "application/json",
        data: medicationJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";

                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";


                $('#tablePharmacies').append(row);
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});

$(document).on('click', '.specs', function () {

    var pharmacies = $(".veliki")
    pharmacies.hide();
    var pharmacies = $(".specification")
    pharmacies.show();

    $('#medication tbody').empty();

    var medicationId=this.id;

    var medicationJSON = formToJSON1(medicationId);
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/medications/getOneMedication",
        dataType: "json",
        contentType: "application/json",
        data: medicationJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);

            var row = "<tr>";

            row += "<td>" + data['name'] + "</td>";
            row += "<td>" + data['type_med'] + "</td>";
            row += "<td>" + data['shape_med'] + "</td>";
            row += "<td>" + data['producer'] + "</td>";
            row += "<td>" + data['contraindication'] + "</td>";
            row += "<td>" + data['recommended_daily_intake'] + "</td>";


            $('#tableMedSpec').append(row);

        },
        error: function (error) {
            alert(error);
        }
    });
});


$(document).on('click', '.filter', function () {

    var type=$("#idDropDown :selected").val();
    var typeJSON=formToJSON2(type);
    $('#medication tbody').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/medications/filterType",
        dataType: "json",
        contentType: "application/json",
        data: typeJSON,
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
                row += "<td>" + data[i]['type_med'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";



                $('#medication').append(row);
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
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

function formToJSON1(medicationId) {
    return JSON.stringify(
        {
            "id": medicationId

        }
    );

}

function formToJSON2(type) {
    return JSON.stringify(
        {
            "type":type

        }
    );

}