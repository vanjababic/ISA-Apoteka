//Svi korisnici
$(document).ready(function () {    // Čeka se trenutak kada je DOM(Document Object Model) učitan da bi JS mogao sa njim da manipuliše.

    var allPharmacies = $(".allPharmacies");
    var onePharmacy = $(".onePharmacy");

    allPharmacies.show();
    onePharmacy.hide();

    // ajax poziv
    $.ajax({
        type: "GET",                                                // HTTP metoda
        url: "http://localhost:8081/pharmacy/allpharmacies",                  // URL koji se gađa
        dataType: "json",                                           // tip povratne vrednosti
        success: function (data) {
            console.log("SUCCESS : ", data);                        // ispisujemo u konzoli povratnu vrednost
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
                var row = "<tr>";// kreiramo red za tabelu
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";

                var btnPharmacy = "<button class='btnPharmacy' id = " + data[i]['id'] + ">Vise o apoteci</button>";
                row += "<td>" + btnPharmacy + "</td>";

                $('#pharmacy').append(row);                        // ubacujemo kreirani red u tabelu čiji je id = employees
            }
            var role = localStorage.getItem('role');

            if(role==="patient") {
                ($(".btnPharmacy").setAttribute('disabled', 'false'));
            } else {
                ($(".btnPharmacy").setAttribute('disabled', 'true'));
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});
var idApoteke;

$(document).on('click', '#btnSearchMedication', function () {

    var searchParam = $("#searchMedication").val();


    var myJSON = formToJSON(searchParam)

    $('#tableMedicationsSearch').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacy/PharmaciesSearch",
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
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                $('#tableMedicationsSearch').append(row);
            }

        },
        error: function (data) {
            console.log("ERROR: ", data);
            window.location.href="error.html";
        }
    });

});
$(document).on('click', '#btnSearchMedication2', function () {

    var searchParam = $("#searchMedication2").val();


    var myJSON = formToJSON3(searchParam)

    $('#tableMedicationsSearch').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacy/PharmaciesSearchbyaddress",
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
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                $('#tableMedicationsSearch').append(row);
            }

        },
        error: function (data) {
            console.log("ERROR: ", data);

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

function formToJSON3(address) {
    return JSON.stringify(
        {
            "address": address

        }
    );
}
function formToJSON4(rating) {
    return JSON.stringify(
        {
            "rating": rating

        }
    );
}


$(document).on('click', '#btnSortbyPharmacy', function () {    // Čeka se trenutak kada je DOM(Document Object Model) učitan da bi JS mogao sa njim da manipuliše.
    // ajax poziv

    $('#pharmacy').empty();
    $.ajax({
        type: "GET",                                                // HTTP metoda
        url: "http://localhost:8081/pharmacy/allpharmaciessortbyname",                  // URL koji se gađa
        dataType: "json",                                           // tip povratne vrednosti
        success: function (data) {
            console.log("SUCCESS : ", data);                        // ispisujemo u konzoli povratnu vrednost
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
                var row = "<tr>";// kreiramo red za tabelu
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";

                $('#pharmacy').append(row);                        // ubacujemo kreirani red u tabelu čiji je id = employees
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#btnSortbyaddressPharmacy', function () {    // Čeka se trenutak kada je DOM(Document Object Model) učitan da bi JS mogao sa njim da manipuliše.
    // ajax poziv

    $('#pharmacy').empty();
    $.ajax({
        type: "GET",                                                // HTTP metoda
        url: "http://localhost:8081/pharmacy/allpharmaciessortbyaddress",                  // URL koji se gađa
        dataType: "json",                                           // tip povratne vrednosti
        success: function (data) {
            console.log("SUCCESS : ", data);                        // ispisujemo u konzoli povratnu vrednost
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
                var row = "<tr>";// kreiramo red za tabelu
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";

                $('#pharmacy').append(row);                        // ubacujemo kreirani red u tabelu čiji je id = employees
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });

});

$(document).on('click', '#btnSortbyocena', function () {    // Čeka se trenutak kada je DOM(Document Object Model) učitan da bi JS mogao sa njim da manipuliše.
    // ajax poziv

    $('#pharmacy').empty();
    $.ajax({
        type: "GET",                                                // HTTP metoda
        url: "http://localhost:8081/pharmacy/allpharmaciessortbyrating",                  // URL koji se gađa
        dataType: "json",                                           // tip povratne vrednosti
        success: function (data) {
            console.log("SUCCESS : ", data);                        // ispisujemo u konzoli povratnu vrednost
            for (i = 0; i < data.length; i++) {                     // prolazimo kroz listu svih zaposlenih
                var row = "<tr>";// kreiramo red za tabelu
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";

                $('#pharmacy').append(row);                        // ubacujemo kreirani red u tabelu čiji je id = employees
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });

});

//********************************************************************
//Deo za profil apoteke

$(document).on('click', '.btnPharmacy', function () {
    var allPharmacies = $(".allPharmacies");
    var onePharmacy = $(".onePharmacy");

    allPharmacies.hide();
    onePharmacy.show();
    idApoteke=this.id;
    var id = this.id;
    console.log(id);
    id = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacy",
        dataType: "json",
        contentType: "application/json",
        data: id,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);
            $('#pharmacyName').append(data['name']);
            $('#address1').append(data['address']);
            $('#description1').append(data['description']);
            $('#rating1').append(data['rating']);
        },
        error: function (data) {
            window.location.href = "error.html"
        }
    });

});

$(document).on('click', '.patientAllPromotions', function () {

    var id=idApoteke;
    id= JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/patients/promotion",
        dataType: "json",
        contentType: "application/json",
        data: id,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);
            alert("Uspesna pretplata na akciju");
            window.location.href = "pharmacies.html";
        },
        error: function (data) {
            window.location.href = "error.html";
        }
    });

});
$(document).on('click', '#btnFilterbyocena', function () {

    var searchParam = $("#searchbyrating").val();


    var myJSON = formToJSON4(searchParam)

    $('#tableMedicationsSearch').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacy/filterbyrating",
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
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                $('#tableMedicationsSearch').append(row);
            }

        },
        error: function (data) {
            console.log("ERROR: ", data);

        }
    });

});