//Prikaz apoteke

//Prikaz svih dermatologa
$(document).ready(function () {
    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy");
    var addNewDermatologistAdminPharmacy= $(".addNewDermatologistAdminPharmacy");
    var successAddDermatologist = $(".successAddDermatologist");
    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    var errorAddDermatologist = $(".errorAddDermatologist");
    var errorDeleteDermatologist = $(".errorDeleteDermatologist");

    dermatologistsAdminPharmacy.show();
    dermatologistsShowAdminPharmacy.show();

    dermatologistsSearchAdminPharmacy.hide();
    addNewDermatologistAdminPharmacy.hide();
    successAddDermatologist.hide();
    dermatologistsFilterAdminPharmacy.hide();
    errorAddDermatologist.hide();
    errorDeleteDermatologist.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/dermatologists/adminDermatologists",
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
                var list = "<ul";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                //row += "<td>" + data[i]['pharmacy']['name'] + "</td>";

                for (var j = 0; j < data[i]['pharmacies'].length; j++) {
                    console.log(data[i]['pharmacies'][j]['name']);
                    list += "<li>" + data[i]['pharmacies'][j]['name'] + "</li>";
                }
                row += "<td>" + list+ "</td>";

                var btnRemove = "<button class='btnRemove' id = " + data[i]['id'] + "> Ukloni dermatologa </button>";
                row += "<td>" + btnRemove + "</td>";

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

//Pretraga
$(document).on('click', '.btnSearchDermatologistsAdminPharmacy', function () {

    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy");
    var addNewDermatologistAdminPharmacy= $(".addNewDermatologistAdminPharmacy");
    var successAddDermatologist = $(".successAddDermatologist");
    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    var errorDeleteDermatologist = $(".errorDeleteDermatologist");

    dermatologistsShowAdminPharmacy.hide();
    addNewDermatologistAdminPharmacy.hide();
    dermatologistsFilterAdminPharmacy.hide();
    errorDeleteDermatologist.hide();
    dermatologistsSearchAdminPharmacy.show();


    var searchParam = $(".searchDermatologistsAdminPharmacy").val();
    searchParam = searchParam.split(" ");

    var myJSON = formToJSON(searchParam[0], searchParam[1])

    console.log(myJSON);

    $('.searchDermatologistsAdminPharmacy').val("");
    $('#tableDermatologistsSearchAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/dermatologists/adminDermatologistsSearch",
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
                var list = "<ul>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";

                for (var j = 0; j < data[i]['pharmacies'].length; j++) {
                    console.log(data[i]['pharmacies'][j]['name']);
                    list += "<li>" + data[i]['pharmacies'][j]['name'] + "</li>";
                }

                row += "<td>" + list + "</td>";

                var btnRemove = "<button class='btnRemove' id = " + data[i]['id'] + "> Ukloni dermatologa </button>";
                row += "<td>" + btnRemove + "</td>";

                $('#tableDermatologistsSearchAdminPharmacy').append(row);
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


//Registracija dermatologa
$(document).on('click', '.btnAddDermatologistsAdminPharmacy', function () {

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy")
    dermatologistsShowAdminPharmacy.hide();

    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy")
    dermatologistsSearchAdminPharmacy.hide();

    var addNewDermatologistAdminPharmacy = $(".addNewDermatologistAdminPharmacy")
    addNewDermatologistAdminPharmacy.show();

    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    dermatologistsFilterAdminPharmacy.hide();

    var errorDeleteDermatologist = $(".errorDeleteDermatologist");
    errorDeleteDermatologist.hide();

});

$(document).on('click', '#btnAddSaveDermatologistAdminPharmacy', function () {


    var email = $("#email").val();
    var workingHoursFrom = $("#workingHoursFrom").val();
    var workingHoursUntil = $("#workingHoursUntil").val();

    var myJSON = formToJSON1(email, workingHoursFrom, workingHoursUntil);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/dermatologists/adminDermatologistsAdd",
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
            $("#email").val("");
            $("#workingHoursFrom").val("");
            $("#workingHoursUntil").val("");
            window.location.href="adminPharmacyDermatologists.html";
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
                var response = JSON.parse(jqXHR.responseText);
                document.getElementById('errorAddDerm').innerHTML = response['message'];
                var addNewDermatologistAdminPharmacy = $(".addNewDermatologistAdminPharmacy")
                addNewDermatologistAdminPharmacy.hide();
                var errorAddDermatologist = $(".errorAddDermatologist");
                errorAddDermatologist.show();
            }
        }
    });

});

function formToJSON1(email, workingHoursFrom, workingHoursUntil) {
    return JSON.stringify(
        {
            "email" : email,
            "beginOfWork" : workingHoursFrom,
            "endOfWork" : workingHoursUntil
        }
    );
}

$(document).on('click', '#returnToDermatologists', function () {

    var successAddDermatologist = $(".successAddDermatologist");
    successAddDermatologist.hide();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy")
    dermatologistsShowAdminPharmacy.show();

    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    dermatologistsFilterAdminPharmacy.hide();
});

$(document).on('click', '#errorAddDermatologists', function () {

    var errorAddDermatologist = $(".errorAddDermatologist");
    errorAddDermatologist.hide();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy")
    dermatologistsShowAdminPharmacy.show();

    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    dermatologistsFilterAdminPharmacy.hide();
});


//Filtriranje dermatologa
$(document).on('click', '.btnFilterDermatologistsAdminPharmacy', function () {

    var employeesAdminPharmacy = $(".employeesAdminPharmacy")
    employeesAdminPharmacy.hide();

    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy")
    dermatologistsAdminPharmacy.show();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy")
    dermatologistsShowAdminPharmacy.show();

    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy")
    dermatologistsSearchAdminPharmacy.hide();

    var addNewDermatologistAdminPharmacy= $(".addNewDermatologistAdminPharmacy")
    addNewDermatologistAdminPharmacy.hide();

    var successAddDermatologist = $(".successAddDermatologist");
    successAddDermatologist.hide();

    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    dermatologistsFilterAdminPharmacy.show();
});

$(document).on('click', '#btnSubmitFilterDermatologists', function () {

    var employeesAdminPharmacy = $(".employeesAdminPharmacy")
    employeesAdminPharmacy.hide();

    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy")
    dermatologistsAdminPharmacy.show();

    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy")
    dermatologistsShowAdminPharmacy.show();

    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy")
    dermatologistsSearchAdminPharmacy.hide();

    var addNewDermatologistAdminPharmacy= $(".addNewDermatologistAdminPharmacy")
    addNewDermatologistAdminPharmacy.hide();

    var successAddDermatologist = $(".successAddDermatologist");
    successAddDermatologist.hide();

    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    dermatologistsFilterAdminPharmacy.hide();

    var ratingOver = $("#ratingOver").val();
    var ratingUnder = $("#ratingUnder").val();

    var myJSON = formToJSON2(ratingOver, ratingUnder);

    $('#tableDermatologistsAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/dermatologists/adminDermatologistsFilter",
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
            for (var i = 0; i < data.length; i++) {
                var row = "<tr>";
                var list = "<ul>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                for (var j = 0; j < data[i]['pharmacies'].length; j++) {
                    console.log(data[i]['pharmacies'][j]['name']);
                    list += "<li>" + data[i]['pharmacies'][j]['name'] + "</li>";
                }

                row += "<td>" + list + "</td>";

                var btnRemove = "<button class='btnRemove' id = " + data[i]['id'] + "> Ukloni dermatologa </button>";
                row += "<td>" + btnRemove + "</td>";

                $('#tableDermatologistsAdminPharmacy tbody').append(row);
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

$(document).on('click', '#btnCancelFilterDermatologists', function () {
    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");
    dermatologistsFilterAdminPharmacy.hide();
});

//Brisanje dermatologa
$(document).on('click', '.btnRemove', function (){
    var modal = document.getElementById("modalDelete");
    modal.style.display = "block";

    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy");
    var addNewDermatologistAdminPharmacy= $(".addNewDermatologistAdminPharmacy");
    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");

    dermatologistsAdminPharmacy.hide();
    dermatologistsShowAdminPharmacy.hide();
    dermatologistsSearchAdminPharmacy.hide();
    addNewDermatologistAdminPharmacy.hide();
    dermatologistsFilterAdminPharmacy.hide();

    var id = this.id;
    var myJSON = JSON.stringify({"id" : id});

    console.log(myJSON);

    $(document).on('click', '#btnModalYes', function () {

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/dermatologists/dermatologistDelete",
            contentType: "application/json",
            data: myJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function (data) {
                console.log("SUCCESS", data);

                window.location.href="adminPharmacyDermatologists.html";
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

                    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
                    dermatologistsAdminPharmacy.hide();
                    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
                    dermatologistsShowAdminPharmacy.hide();

                    var errorDeleteDermatologist = $(".errorDeleteDermatologist");
                    errorDeleteDermatologist.show();

                    var response = JSON.parse(jqXHR.responseText);
                    document.getElementById('errorDeleteDermatologist').innerHTML = response['message'];
                }
            }


        });
    });

    $(document).on('click', '#btnModalNo', function () {
        var modal = document.getElementById("modalDelete");
        modal.style.display = "none";

        var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
        dermatologistsAdminPharmacy.show();
        var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
        dermatologistsShowAdminPharmacy.show();

    });
});

$(document).on('click', '#btnErrorDeleteDermatologist', function () {
    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
    dermatologistsAdminPharmacy.show();
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    dermatologistsShowAdminPharmacy.show();

    var errorDeleteDermatologist = $(".errorDeleteDermatologist");
    errorDeleteDermatologist.hide();
});