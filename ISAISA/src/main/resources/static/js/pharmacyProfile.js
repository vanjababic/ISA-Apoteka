//Id apoteke na koju je kliknuto
var id
//Prikaz info o apoteci
$(document).on('click', '.btnPharmacy', function () {
    var allPharmacies = $(".allPharmacies");
    var onePharmacy = $(".onePharmacy");
    var card = $(".card")
    var pharmacists = $(".pharmacists")
    var dermatologists = $(".dermatologists")
    var medicine = $(".medicine");
    var availableAppointments = $(".availableAppointments");

    allPharmacies.hide();
    onePharmacy.show();
    pharmacists.hide();
    card.show();
    dermatologists.hide();
    medicine.hide();
    availableAppointments.hide();

    id = this.id;
    console.log(id);
    var id1 = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacy",
        dataType: "json",
        contentType: "application/json",
        data: id1,
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

//*************************Farmaceuti
//Prikaz farmaceuta
$(document).on('click', '#patientAllPharmacists', function () {

    var card = $(".card")
    var pharmacists = $(".pharmacists")
    var dermatologists = $(".dermatologists")
    var medicine = $(".medicine");
    var availableAppointments = $(".availableAppointments");

    pharmacists.show();
    card.hide();
    dermatologists.hide();
    medicine.hide();
    availableAppointments.hide();

    var counseling = $(".counseling");
    counseling.hide();

    var id1 = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacists/allPharmacistsFromPharmacy",
        dataType: "json",
        contentType: "application/json",
        data: id1,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['name'] + "</td>";

                var btnMakeAppointment = "<button class='btnMakeAppointment' id = " + data[i]['id'] + "> Zakazi savetovanje </button>";
                row += "<td>" + btnMakeAppointment + "</td>";

                $('#tablePharmacistsAdminPharmacy').append(row);
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

//Zakazivanje
var idPh
$(document).on('click', '.btnMakeAppointment', function (){
    idPh = this.id;
    console.log("GGG", idPh);

    var counseling = $(".counseling");
    counseling.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");
    pharmacistsShowAdminPharmacy.hide()

});

$(document).on('click', '#btnCounseling', function () {
    var beginCounseling = $("#beginCounseling").val();

    var myJSON = JSON.stringify({"id" : idPh, "beginofappointment" : beginCounseling});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/createCounseling",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            alert("Uspesno zakazano savetovanje!");
            window.location.href="pharmacies.html";
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

//************************Dermatolozi

$(document).on('click', '#patientAllDermatologists', function () {

    var card = $(".card")
    var pharmacists = $(".pharmacists")
    var dermatologists = $(".dermatologists")
    var medicine = $(".medicine");
    var availableAppointments = $(".availableAppointments");

    pharmacists.hide();
    card.hide();
    dermatologists.show();
    medicine.hide();
    availableAppointments.hide();

    var id1 = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/dermatologists/dermatologistsByPharmacy",
        dataType: "json",
        contentType: "application/json",
        data: id1,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                var list = "<ul";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";

                for (var j = 0; j < data[i]['pharmacies'].length; j++) {
                    console.log(data[i]['pharmacies'][j]['name']);
                    list += "<li>" + data[i]['pharmacies'][j]['name'] + "</li>";
                }
                row += "<td>" + list+ "</td>";

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


//***********************Lekovi

$(document).on('click', '#patientAllMedicine', function () {
    var card = $(".card")
    var pharmacists = $(".pharmacists")
    var dermatologists = $(".dermatologists")
    var medicine = $(".medicine");
    var availableAppointments = $(".availableAppointments");

    pharmacists.hide();
    card.hide();
    dermatologists.hide();
    medicine.show();
    availableAppointments.hide();
    var id1 = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/medications/allMedicationsByPharmacy",
        dataType: "json",
        contentType: "application/json",
        data: id1,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['code'] + "</td>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['producer'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";

                var btnReserve = "<button class='btnReserve' id = " + data[i]['name'] + "> Rezervisi lek </button>";
                row += "<td>" + btnReserve + "</td>";

                $('#tableMedicineAdminPharmacy').append(row);
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

//Rezervacija
$(document).on('click', '.btnReserve', function () {
    var nameMed = this.id;

    $(document).on('click', '#btnMedicineReservation', function () {
        var dateReservation = $("#dateReservation").val();
        var myJSON = JSON.stringify({"id": id, "name":nameMed, "dateofreservation" : dateReservation});

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
                alert("Lek uspesno rezervisan");
                window.location.href="pharmacies.html";
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

});



//***********************Slobodni termini
$(document).on('click', '#patientAllAvailableAppointments', function () {
    var card = $(".card")
    var pharmacists = $(".pharmacists")
    var dermatologists = $(".dermatologists")
    var medicine = $(".medicine");
    var availableAppointments = $(".availableAppointments");

    pharmacists.hide();
    card.hide();
    dermatologists.hide();
    medicine.hide();
    availableAppointments.show();
    var id1 = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/availableAppointmentsByPharmacy",
        dataType: "json",
        contentType: "application/json",
        data: id1,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['dermatologist']['firstName'] + "</td>";
                row += "<td>" + data[i]['dermatologist']['lastName'] + "</td>";
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";

                var btnMake = "<button class='btnMake' id = " + data[i]['id'] + ">Zakazi</button>";
                row += "<td>" + btnMake + "</td>";

                $('#tableAppointmentsAdminPharmacy').append(row);
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

$(document).on('click', '.btnMake', function (){
    var idApp = this.id;

    var id1 = JSON.stringify({"id":idApp});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/reserveappointment",
        dataType: "json",
        contentType: "application/json",
        data: id1,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            alert("Uspesno zakazan termin!");
            window.location.href="pharmacies.html";
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
