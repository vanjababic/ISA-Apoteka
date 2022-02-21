//Prikaz svih farmaceuta
$(document).ready(function () {
    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy");
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");
    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy");
    var addNewPharmacistAdminPharmacy= $(".addNewPharmacistAdminPharmacy");
    var successAddPharmacist = $(".successAddPharmacist");
    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    var errorAddPharmacist = $(".errorAddPharmacist");
    var errorDeletePharmacist = $(".errorDeletePharmacist");

    pharmacistsAdminPharmacy.show();
    pharmacistsShowAdminPharmacy.show();

    errorDeletePharmacist.hide();
    pharmacistsSearchAdminPharmacy.hide();
    addNewPharmacistAdminPharmacy.hide();
    successAddPharmacist.hide();
    pharmacistsFilterAdminPharmacy.hide();
    errorAddPharmacist.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacists/adminPharmacists",
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
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['name'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['address'] + "</td>";

                var btnRemove = "<button class='btnRemove' id = " + data[i]['id'] + "> Ukloni farmaceuta </button>";
                row += "<td>" + btnRemove + "</td>";

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

//Pretraga
$(document).on('click', '.btnSearchPharmacistsAdminPharmacy', function () {
    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy")
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    var addNewPharmacistAdminPharmacy= $(".addNewPharmacistAdminPharmacy")
    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    var errorDeletePharmacist = $(".errorDeletePharmacist");

    pharmacistsShowAdminPharmacy.hide();
    addNewPharmacistAdminPharmacy.hide();
    pharmacistsFilterAdminPharmacy.hide();
    errorDeletePharmacist.hide();
    pharmacistsSearchAdminPharmacy.show();

    var searchParam = $(".searchPharmacistsAdminPharmacy").val();
    searchParam = searchParam.split(" ");

    var myJSON = formToJSON(searchParam[0], searchParam[1])

    console.log(myJSON);

    $('#searchPharmacistsAdminPharmacy').val("");
    $('#tablePharmacistsSearchAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacists/adminPharmacistsSearch",
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
            $('#searchPharmacists').append(searchParam[0], " ", searchParam[1]);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['name'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['address'] + "</td>";

                var btnRemove = "<button class='btnRemove' id = " + data[i]['id'] + "> Ukloni farmaceuta </button>";
                row += "<td>" + btnRemove + "</td>";

                $('#tablePharmacistsSearchAdminPharmacy').append(row);
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

function formToJSON(firstName, lastName) {
    return JSON.stringify(
        {
            "firstName": firstName,
            "lastName": lastName
        }
    );
}


//Registracija farmaceuta
$(document).on('click', '.btnAddPharmacistAdminPharmacy', function () {

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.hide();

    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.hide();

    var addNewPharmacistAdminPharmacy = $(".addNewPharmacistAdminPharmacy")
    addNewPharmacistAdminPharmacy.show();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();

    var errorDeletePharmacist = $(".errorDeletePharmacist");
    errorDeletePharmacist.hide();

});

$(document).on('click', '#btnAddSavePharmacistAdminPharmacy', function () {

    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var email = $("#email").val();
    var address = $("#address").val();
    var phone = $("#phone").val();
    var city = $("#city").val();
    var country = $("#country").val();
    var workingHoursFrom = $("#workingHoursFrom").val();
    var workingHoursUntil = $("#workingHoursUntil").val();

    var myJSON = formToJSON1(firstName, lastName, email, address, phone, city, country, workingHoursFrom, workingHoursUntil);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacists/adminPharmacistsAdd",
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
            $("#firstName").val("");
            $("#lastName").val("");
            $("#email").val("");
            $("#address").val("");
            $("#phone").val("");
            $("#city").val("");
            $("#country").val("");
            $("#workingHoursFrom").val("");
            $("#workingHoursUntil").val("");
            window.location.href="adminPharmacyPharmacists.html";
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
            if(jqXHR.status === 500)
            {
                var addNewPharmacistAdminPharmacy = $(".addNewPharmacistAdminPharmacy")
                addNewPharmacistAdminPharmacy.hide();
                var errorAddPharmacist = $(".errorAddPharmacist");
                errorAddPharmacist.show();
            }
        }
    });

});

function formToJSON1(firstName, lastName, email, address, phone, city, country, workingHoursFrom, workingHoursUntil) {
    return JSON.stringify(
        {
            "firstName" : firstName,
            "lastName" : lastName,
            "email" : email,
            "password" : "default",
            "address" : address,
            "phone" : phone,
            "city" : city,
            "country" : country,
            "beginofwork" : workingHoursFrom,
            "endofwork" : workingHoursUntil
        }
    );
}

$(document).on('click', '#returnToPharmacists', function () {

    var successAddPharmacist = $(".successAddPharmacist");
    successAddPharmacist.hide();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();
});

$(document).on('click', '#returnToPharmacistsError', function () {

    var errorAddPharmacist = $(".errorAddPharmacist");
    errorAddPharmacist.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();
});


//Filtriranje farmaceuta
$(document).on('click', '.btnFilterPharmacistAdminPharmacy', function () {

    var employeesAdminPharmacy = $(".employeesAdminPharmacy")
    employeesAdminPharmacy.hide();

    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy")
    pharmacistsAdminPharmacy.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.hide();

    var addNewPharmacistAdminPharmacy= $(".addNewPharmacistAdminPharmacy")
    addNewPharmacistAdminPharmacy.hide();

    var successAddPharmacist = $(".successAddPharmacist");
    successAddPharmacist.hide();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.show();
});

$(document).on('click', '#btnSubmitFilterPharmacists', function () {

    var employeesAdminPharmacy = $(".employeesAdminPharmacy")
    employeesAdminPharmacy.hide();

    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy")
    pharmacistsAdminPharmacy.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.hide();

    var addNewPharmacistAdminPharmacy= $(".addNewPharmacistAdminPharmacy")
    addNewPharmacistAdminPharmacy.hide();

    var successAddPharmacist = $(".successAddPharmacist");
    successAddPharmacist.hide();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();

    var ratingOver = $("#ratingOver").val();
    var ratingUnder = $("#ratingUnder").val();

    var myJSON = formToJSON2(ratingOver, ratingUnder);

    $('#tablePharmacistsAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacists/adminPharmacistsFilter",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
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
                row += "<td>" + data[i]['pharmacy']['address'] + "</td>";

                var btnRemove = "<button class='btnRemove' id = " + data[i]['id'] + "> Ukloni farmaceuta </button>";
                row += "<td>" + btnRemove + "</td>";

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

function formToJSON2(ratingOver, ratingUnder) {
    return JSON.stringify(
        {
            "ratingOver" : ratingOver,
            "ratingUnder" : ratingUnder
        }
    );
}

$(document).on('click', '#btnCancelFilterPharmacists', function () {
    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();
});

//Brisanje farmaceuta
$(document).on('click', '.btnRemove', function (){
    var modal = document.getElementById("modalDelete");
    modal.style.display = "block";

    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy");
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");
    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy");
    var addNewPharmacistAdminPharmacy= $(".addNewPharmacistAdminPharmacy");
    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");

    pharmacistsAdminPharmacy.hide();
    pharmacistsShowAdminPharmacy.hide();
    pharmacistsSearchAdminPharmacy.hide();
    addNewPharmacistAdminPharmacy.hide();
    pharmacistsFilterAdminPharmacy.hide();

    var id = this.id;
    var myJSON = JSON.stringify({"id" : id});

    console.log(myJSON);

    $(document).on('click', '#btnModalYes', function () {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/pharmacists/pharmacistDelete",
            contentType: "application/json",
            data: myJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function (data) {
                console.log("SUCCESS", data);

                window.location.href="adminPharmacyPharmacists.html";
            },
            error: function (jqXHR) {
                if (jqXHR.status === 403) {
                    window.location.href = "error.html";
                }
                if (jqXHR.status === 401) {
                    window.location.href = "error.html";
                }
                if (jqXHR.status === 500) {

                    var modal = document.getElementById("modalDelete");
                    modal.style.display = "none";

                    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy");
                    pharmacistsAdminPharmacy.hide();
                    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");
                    pharmacistsShowAdminPharmacy.hide();

                    var errorDeletePharmacist = $(".errorDeletePharmacist");
                    errorDeletePharmacist.show();

                    var response = JSON.parse(jqXHR.responseText);
                    document.getElementById('errorDeletePharmacist').innerHTML = response['message'];
                }
            }
        });
    });

    $(document).on('click', '#btnModalNo', function () {
        var modal = document.getElementById("modalDelete");
        modal.style.display = "none";

        var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy");
        pharmacistsAdminPharmacy.show();
        var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");
        pharmacistsShowAdminPharmacy.show();

    });
});

$(document).on('click', '#btnErrorDeletePharmacist', function () {
    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy");
    pharmacistsAdminPharmacy.show();
    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy");
    pharmacistsShowAdminPharmacy.show();

    var errorDeletePharmacist = $(".errorDeletePharmacist");
    errorDeletePharmacist.hide();
});