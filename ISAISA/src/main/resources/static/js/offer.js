$(document).ready(function () {




    $.ajax({
        type: "GET",
        url: "http://localhost:8081/suppliers/allorders",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);

            for (i = 0; i < data.length; i++) {
               
                var row = "<tr>";
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['adminEmail'] + "</td>";
                row += "<td>" + data[i]['pharmacyName'] + "</td>";
                row += "<td>" + data[i]['pharmacyAddress'] + "</td>";
                row += "<td>" + data[i]['dateDeadline'] + "</td>";
                row += "<td>" + data[i]['statusSupplier'] + "</td>";
                row += "<td>" + data[i]['offer'] + "</td>";



                if(data[i]['statusSupplier']==='odobrena'){
                    var btn = "<button disabled>Ponuda je obradjena</button>";
                }
                else if(data[i]['statusSupplier']==='odbijena'){
                    var btn = "<button disabled>Ponuda je obradjena</button>";
                }
                else if ( data[i]['offer']==0) {
                    var btn = "<button class='btnGiveOffer' id = " + data[i]['id'] + ">Kreiraj ponudu</button>";
                }
                else {
                    var btn = "<button class='btnChangeOffer' id = " + data[i]['id'] + ">Izmeni ponudu</button>";
                }


                row += "<td>" + btn + "</td>";

                $('#orders').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });


});
$(document).on('click', '.btnGiveOffer', function () {

    var pharmacyDiv = $(".orderall");
    pharmacyDiv.hide();
    var formaDiv=$(".orderForm");
    formaDiv.show();
    var orderID=this.id;



    $(document).on("submit", "form", function (event) {

        event.preventDefault();
        var offer = $("#offer").val();
        var deliveryDate = $("#date").val();
        var offerJSON = formToJSON(offer,deliveryDate,orderID);

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/suppliers/addOffer",
            dataType: "json",
            contentType: "application/json",
            data: offerJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function () {
                alert("success");
                window.location.href = "supplier.html";
            },
            error: function (error) {
                alert(error);
            }
        });
    });
});

$(document).on('click', '.btnChangeOffer', function () {

    var pharmacyDiv = $(".orderall");
    pharmacyDiv.hide();
    var formaDiv=$(".orderChangeForm");
    formaDiv.show();
    var orderID=this.id;



    $(document).on("submit", "form", function (event) {

        event.preventDefault();
        var offer = $("#offer1").val();
        var deliveryDate = $("#date1").val();
        var offerJSON = formToJSON(offer,deliveryDate,orderID);

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/suppliers/changeoffer",
            dataType: "json",
            contentType: "application/json",
            data: offerJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function () {
                alert("success");
                window.location.href = "supplier.html";
            },
            error: function (error) {
                alert(error);
            }
        });
    });
});

$(document).on('click', '.btnFilter', function () {
    //var s = document.getElementById('selection');
    //var status=s.value;
    var status=$("#selection").val();
    var statusJSON=formToJSON1(status);
    $('#orders tbody').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/suppliers/filter",
        dataType: "json",
        contentType: "application/json",
        data: statusJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);

            for (i = 0; i < data.length; i++) {

                var row = "<tr>";
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['adminEmail'] + "</td>";
                row += "<td>" + data[i]['pharmacyName'] + "</td>";
                row += "<td>" + data[i]['pharmacyAddress'] + "</td>";
                row += "<td>" + data[i]['dateDeadline'] + "</td>";
                row += "<td>" + data[i]['statusSupplier'] + "</td>";
                row += "<td>" + data[i]['offer'] + "</td>";



                if(data[i]['statusSupplier']==='odobrena'){
                    var btn = "<button disabled>Ponuda je obradjena</button>";
                }
                else if(data[i]['statusSupplier']==='odbijena'){
                    var btn = "<button disabled>Ponuda je obradjena</button>";
                }
                else if ( data[i]['offer']==0) {
                    var btn = "<button class='btnGiveOffer' id = " + data[i]['id'] + ">Kreiraj ponudu</button>";
                }
                else {
                    var btn = "<button class='btnChangeOffer' id = " + data[i]['id'] + ">Izmeni ponudu</button>";
                }


                row += "<td>" + btn + "</td>";

                $('#orders').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });

});


function formToJSON1(status) {
    return JSON.stringify(
        {
            "status": status
        }
    );
};


function formToJSON(offerPrice,deliveryDate,orderID,supplier) {
    return JSON.stringify(
        {
            "offerPrice": offerPrice,
            "deliveryDate": deliveryDate,
            "orderID": orderID,

        }
    );
};