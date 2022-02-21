//Prikaz svih dermatologa
$(document).ready(function () {
    var dermatologistsAdminPharmacy = $(".dermatologistsAdminPharmacy");
    var dermatologistsShowAdminPharmacy = $(".dermatologistsShowAdminPharmacy");
    var dermatologistsSearchAdminPharmacy = $(".dermatologistsSearchAdminPharmacy");
    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");

    dermatologistsAdminPharmacy.show();
    dermatologistsShowAdminPharmacy.show();
    dermatologistsSearchAdminPharmacy.hide();
    dermatologistsFilterAdminPharmacy.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/dermatologists/allDermatologists",
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
    var dermatologistsFilterAdminPharmacy = $(".dermatologistsFilterAdminPharmacy");

    dermatologistsShowAdminPharmacy.hide();
    dermatologistsFilterAdminPharmacy.hide();
    dermatologistsSearchAdminPharmacy.show();

    var searchParam = $(".searchDermatologistsAdminPharmacy").val();
    searchParam = searchParam.split(" ");

    var myJSON = formToJSON(searchParam[0], searchParam[1])

    console.log(myJSON);

    $('.searchDermatologistsAdminPharmacy').val("");
    $('#tableDermatologistsSearchAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/dermatologists/allDermatologistsSearch",
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

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacy/allpharmacies",
        dataType: "json",
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var option = "<option>" + data[i]['name'] + "</option>";
                $('#pharmacyName').append(option);
                console.log(option);
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
        url: "http://localhost:8081/dermatologists/allDermatologistsFilter",
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