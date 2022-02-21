//Prikazi sve farmaceute
$(document).ready(function () {

    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy")
    pharmacistsAdminPharmacy.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.hide();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacists/allPharmacists",
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

$(document).on('click', '.btnSearchPharmacistsAdminPharmacy', function () {

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.hide();

    var addNewPharmacistAdminPharmacy= $(".addNewPharmacistAdminPharmacy")
    addNewPharmacistAdminPharmacy.hide();

    var pharmacistsSearchAdminPharmacy= $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.show();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();

    var searchParam = $(".searchPharmacistsAdminPharmacy").val();
    searchParam = searchParam.split(" ");

    var myJSON = formToJSON(searchParam[0], searchParam[1])

    $('.searchPharmacists').empty();
    $('#tablePharmacistsSearchAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacists/allPharmacistsSearch",
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
            $('.searchPharmacists').append(searchParam[0], " ", searchParam[1]);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['name'] + "</td>";
                row += "<td>" + data[i]['pharmacy']['address'] + "</td>";
            }
            $('#tablePharmacistsSearchAdminPharmacy').append(row);
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

$(document).on('click', '.btnFilterPharmacistAdminPharmacy', function () {

    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy")
    pharmacistsAdminPharmacy.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.hide();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.show();

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

$(document).on('click', '#btnSubmitFilterPharmacists', function () {

    var employeesAdminPharmacy = $(".employeesAdminPharmacy")
    employeesAdminPharmacy.hide();

    var pharmacistsAdminPharmacy = $(".pharmacistsAdminPharmacy")
    pharmacistsAdminPharmacy.show();

    var pharmacistsShowAdminPharmacy = $(".pharmacistsShowAdminPharmacy")
    pharmacistsShowAdminPharmacy.show();

    var pharmacistsSearchAdminPharmacy = $(".pharmacistsSearchAdminPharmacy")
    pharmacistsSearchAdminPharmacy.hide();

    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();

    var ratingOver = $("#ratingOver").val();
    var ratingUnder = $("#ratingUnder").val();
    var pharmacyName = $("#pharmacyName").val();

    var myJSON = formToJSON2(ratingOver, ratingUnder, pharmacyName);

    $('#tablePharmacistsAdminPharmacy tbody').empty();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/pharmacists/allPharmacistsFilter",
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

function formToJSON2(ratingOver, ratingUnder, pharmacyName) {
    return JSON.stringify(
        {
            "ratingOver" : ratingOver,
            "ratingUnder" : ratingUnder,
            "pharmacyName" : pharmacyName
        }
    );
}

$(document).on('click', '#btnCancelFilterPharmacists', function () {
    var pharmacistsFilterAdminPharmacy = $(".pharmacistsFilterAdminPharmacy");
    pharmacistsFilterAdminPharmacy.hide();
});