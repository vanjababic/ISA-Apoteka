$(document).ready(function () {

    var bio = $(".bio");
    var changeBio = $(".changeBio");

    bio.show();
    changeBio.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacy/pharmacyId",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);
            $('#pharmacyName').append(data['name']);
            $('#address').append(data['address']);
            $('#description').append(data['description']);
            $('#rating').append(data['rating']);
        },
        error: function (data) {
            window.location.href = "error.html"
        }
    });
});

$(document).on('click', '#changeBio', function (){
    var bio = $(".bio");
    var changeBio = $(".changeBio");

    bio.hide();
    changeBio.show();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacy/pharmacyId",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS: ", data);
            $('#chPharmacyName:text').val(data['name']);
            $('#chAddress:text').val(data['address']);
            $('#chDescription:text').val(data['description']);
        },
        error: function (data) {
            window.location.href = "error.html"
        }
    });
});

$(document).on('click', '#submitChangeBio', function () {

    var chPharmacyName = $("#chPharmacyName").val();
    var chAddress = $("#chAddress").val();
    var chDescription = $("#chDescription").val();

    var myJSON = JSON.stringify({"name": chPharmacyName, "address" : chAddress, "description" : chDescription});
    if(chPharmacyName !== "" && chAddress !== "" ) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/pharmacy/changePharmacy",
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
                window.location.href = "adminPharmacyPharmacy.html";
            },
            error: function (data) {
                console.log("ERROR: ", data);
                window.location.href = "error.html"
            }
        });
    }
});

$(document).on('click', '#cancelChangeBio', function () {
    var bio = $(".bio");
    var changeBio = $(".changeBio");

    bio.show();
    changeBio.hide();
});
