$(document).ready(function () {
    insertNumber = $(".insertNumber")
    insertNumber.show();
    reservationExists = $(".reservationExists")
    reservationExists.hide();
    reservationDoesNotExists = $(".reservationDoesNotExists")
    reservationDoesNotExists.hide();
});
var rezId;
$(document).on('click', '#btnSubmitReservation', function () {

    insertNumber = $(".insertNumber")
    insertNumber.show();
    reservationExists = $(".reservationExists")
    reservationExists.hide();
    reservationDoesNotExists = $(".reservationDoesNotExists")
    reservationDoesNotExists.hide();

    var reservation = $("#chReservation").val();
    var res = JSON.stringify({"id":reservation});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/reservations/checkIfReservationExists",
        dataType: "json",
        contentType: "application/json",
        data: res,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }},
        success: function (data) {
            console.log("SUCCESS : ", data);
            if( data['value'] == true) {
                rezId = reservation;
                console.log(rezId);
                console.log("SUCCESS : ", data);
                reservationExists.show();
            }else {
                reservationDoesNotExists.show();
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#btnGiveReservation', function () {

    var res = JSON.stringify({"id":rezId});
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/reservations/giveMedication",
        contentType: "application/json",
        data: res,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }},
        success: function () {
            console.log("SUCCESS : ");
            window.location.href="welcomePharmacist.html";
            alert('Lek je izdat');
        },
        error: function () {
            console.log("ERROR : ");
        }
    });


});

$(document).on('click', '#btnBack', function () {
    window.location.href="welcomePharmacist.html";
});
$(document).on('click', '#btnBackk', function () {
    window.location.href="welcomePharmacist.html";
});
$(document).on('click', '#btnBackkk', function () {
    window.location.href="welcomePharmacist.html";
});