
//Prikaz svih farmaceuta
$(document).ready(function () {
    var changePrice = $(".changePrice");
    changePrice.hide();
    var changePriceMed = $(".changePriceMed");
    changePriceMed.hide();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/counselings/getCounselingsPharmacy",
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
                row += "<td>" + data[i]['pharmacist']['id'] + "</td>";
                row += "<td>" + data[i]['pharmacist']['firstName'] + "</td>";
                row += "<td>" + data[i]['pharmacist']['lastName'] + "</td>";
                row += "<td>" + data[i]['beginofappointment']+ "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";

                var btnChange = "<button class='btnChangePh' id = " + data[i]['id'] + "> Izmeni cenu </button>";
                row += "<td>" + btnChange + "</td>";

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

//Prikaz svih dermatologa

$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/appointments/getAppointmentsPharmacy",
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
                row += "<td>" + data[i]['dermatologist']['id'] + "</td>";
                row += "<td>" + data[i]['dermatologist']['firstName'] + "</td>";
                row += "<td>" + data[i]['dermatologist']['lastName'] + "</td>";
                row += "<td>" + data[i]['beginofappointment']+ "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";

                var btnChange = "<button class='btnChangeDerm' id = " + data[i]['id'] + "> Izmeni cenu </button>";
                row += "<td>" + btnChange + "</td>";

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

$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/medications/adminmedications",
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
                row += "<td>" + data[i]['code'] + "</td>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['beginPriceValidity'] + "</td>";
                row += "<td>" + data[i]['endPriceValidity']+ "</td>";
                row += "<td>" + data[i]['price'] + "</td>";

                var btnChange = "<button class='btnChangeMed' id = " + data[i]['id'] + "> Izmeni cenu </button>";
                row += "<td>" + btnChange + "</td>";

                $('#tableMedicationAdminPharmacy').append(row);
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


//Promena cene savetovanja
$(document).on('click', '.btnChangePh', function () {
    var changePrice = $(".changePrice");
    changePrice.show();

    var medicationShowAdminPharmacy = $(".medicationShowAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");

    medicationShowAdminPharmacy.hide();
    dermatologistsShowAdminPharmacy.hide();
    pharmacistsShowAdminPharmacy.hide();

    id = this.id;
});

$(document).on('click', '#btnSubmitPrice', function () {

    var changePrice = $("#changePrice").val();

    var myJSON = JSON.stringify({"id" : id, "price" : changePrice});

    if(changePrice > 0 && changePrice !== null) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/counselings/changeCounselingPrice",
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
                $(".changePrice").val("");
                window.location.href = "adminPharmacyPrices.html";
            },
            error: function (jqXHR) {
                if (jqXHR.status === 403) {
                    window.location.href = "error.html";
                }
                if (jqXHR.status === 401) {
                    window.location.href = "error.html";
                }
            }
        });
    }
});


//Promena cene pregleda
$(document).on('click', '.btnChangeDerm', function () {
    var changePrice = $(".changePrice");
    changePrice.show();

    var medicationShowAdminPharmacy = $(".medicationShowAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");

    medicationShowAdminPharmacy.hide();
    dermatologistsShowAdminPharmacy.hide();
    pharmacistsShowAdminPharmacy.hide();

    id = this.id;
});

$(document).on('click', '#btnSubmitPrice', function () {

    var changePrice = $("#changePrice").val();

    var myJSON = JSON.stringify({"id" : id, "price" : changePrice});

    if(changePrice > 0 && changePrice !== null) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/appointments/changeAppointmentPrice",
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
                $(".changePrice").val("");
                window.location.href = "adminPharmacyPrices.html";
            },
            error: function (jqXHR) {
                if (jqXHR.status === 403) {
                    window.location.href = "error.html";
                }
                if (jqXHR.status === 401) {
                    window.location.href = "error.html";
                }
            }
        });
    }
});


//Promena cene savetovanja
$(document).on('click', '.btnChangeMed', function () {
    var changePriceMed = $(".changePriceMed");
    changePriceMed.show();

    var changePrice = $(".changePrice");
    var medicationShowAdminPharmacy = $(".medicationShowAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");

    changePrice.hide();
    medicationShowAdminPharmacy.hide();
    dermatologistsShowAdminPharmacy.hide();
    pharmacistsShowAdminPharmacy.hide();

    id = this.id;
});

$(document).on('click', '#btnSubmitPriceMed', function () {

    var changePrice = $("#changePriceMed").val();
    var dateEndPrice = $("#dateEndPrice").val();

    var myJSON = JSON.stringify({"id" : id, "price" : changePrice, "end" : dateEndPrice});

    dateEndPrice = Date.parse(dateEndPrice);

    if(changePrice > 0 && changePrice !== null && dateEndPrice !== null && dateEndPrice > Date.now()) {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/medications/changeMedicationPrice",
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
                $("#changePriceMed").val("");
                $("#dateEndPrice").val("");
                window.location.href = "adminPharmacyPrices.html";
            },
            error: function (jqXHR) {
                if (jqXHR.status === 403) {
                    window.location.href = "error.html";
                }
                if (jqXHR.status === 401) {
                    window.location.href = "error.html";
                }
            }
        });
    }
});

