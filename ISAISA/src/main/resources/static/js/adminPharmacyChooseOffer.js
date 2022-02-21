$(document).ready(function () {

    var ordersShow = $(".ordersShow");
    var offersShow = $(".offersShow");
    var acceptOffer = $(".acceptOffer");
    var deleteOrder = $(".deleteOrder");
    var changeOrder = $(".changeOrder");
    var filterShow = $(".filterShow");

    ordersShow.show();
    offersShow.hide();
    acceptOffer.hide();
    deleteOrder.hide();
    changeOrder.hide();
    filterShow.show();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/orders/ordersByPharmacy",
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
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['dateDeadline'] + "</td>";

                var btnOffers = "<button class='btnOffers' id = " + data[i]['id'] + ">Pogledaj ponude</button>";
                row += "<td>" + btnOffers + "</td>";

                var btnChangeOrder = "<button class='btnChangeOrder' id = " + data[i]['id'] + ">Izmeni narudzbenicu</button>";
                row += "<td>" + btnChangeOrder + "</td>";

                var btnDeleteOrder = "<button class='btnDeleteOrder' id = " + data[i]['id'] + ">Obrisi narudzbenicu</button>";
                row += "<td>" + btnDeleteOrder + "</td>";

                $('#tableOrdersShow').append(row);
            }
        },
        error: function (jqXHR) {
            if(jqXHR.status === 403) {
                window.location.href="error.html";
            }
            if(jqXHR.status === 401) {
                window.location.href="error.html";
            }
        }
    });
});

$(document).on('click', '.btnOffers', function () {
    var ordersShow = $(".ordersShow");
    var offersShow = $(".offersShow");
    var acceptOffer = $(".acceptOffer");
    var deleteOrder = $(".deleteOrder");
    var changeOrder = $(".changeOrder");
    var filterShow = $(".filterShow");

    ordersShow.hide();
    offersShow.show();
    acceptOffer.hide();
    deleteOrder.hide();
    changeOrder.hide();
    filterShow.hide();

    var id = this.id;

    console.log(id);

    id = JSON.stringify({"id" : id})

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/offers/offersByOrder",
        dataType: "json",
        contentType: "application/json",
        data: id,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);

            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['supplier']['id'] + "</td>";
                row += "<td>" + data[i]['supplier']['email'] + "</td>";
                row += "<td>" + data[i]['offerPrice'] + "</td>";
                row += "<td>" + data[i]['deliveryDate'] + "</td>";
                var btnCreateOffer = "<button class='btnAcceptOffer' id = " + data[i]['id'] + ">Prihvati ponudu</button>";
                row += "<td>" + btnCreateOffer + "</td>";

                $('#tableOffersShow').append(row);
            }
        },
        error: function (jqXHR) {
            if(jqXHR.status === 403) {
                window.location.href="error.html";
            }
            if(jqXHR.status === 401) {
                window.location.href="error.html";
            }
        }
    });
});

$(document).on('click', '.btnAcceptOffer', function () {

    var offersShow = $(".offersShow");
    offersShow.hide();

    var acceptOffer = $(".acceptOffer");
    acceptOffer.show();

    var filterShow = $(".filterShow");
    filterShow.hide();

    var id = this.id;

    console.log(id);

    id = JSON.stringify({"id" : id})

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/offers/acceptOffer",
        dataType: "json",
        contentType: "application/json",
        data: id,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);

            var successOrder = $(".successOrder");
            successOrder.show();
            var errorOrder = $(".errorOrder");
            errorOrder.hide();
        },
        error: function (jqXHR) {
            if(jqXHR.status === 500) {
                var errorOrder = $(".errorOrder");
                errorOrder.show();
                var successOrder = $(".successOrder");
                successOrder.hide();

                var response = JSON.parse(jqXHR.responseText);
                $('#errorOrder').append(response['message']);
            }
            else {
                window.location.href="error.html";
            }
        }
    });
});

$(document).on('click', '.btnReturnOffer', function () {
    var offersShow = $(".offersShow");
    var acceptOffer = $(".acceptOffer");
    var filterShow = $(".filterShow");

    offersShow.show();
    acceptOffer.hide();
    filterShow.hide();
});

$(document).on('click', '.btnDeleteOrder', function () {

    var ordersShow = $(".ordersShow");
    ordersShow.hide();

    var deleteOrder = $(".deleteOrder");
    deleteOrder.show();

    var filterShow = $(".filterShow");
    filterShow.hide();

    var id = this.id;
    id = JSON.stringify({"id" : id});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/orders/deleteOrder",
        contentType: "application/json",
        data: id,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS", data);

            var errorDeleteOrder = $(".errorDeleteOrder");
            errorDeleteOrder.hide();

            var successDeleteOrder = $(".successDeleteOrder");
            successDeleteOrder.show();
        },
        error: function (jqXHR, data) {
            if(jqXHR.status === 500) {
                console.log(data)

                var errorDeleteOrder = $(".errorDeleteOrder");
                errorDeleteOrder.show();

                var successDeleteOrder = $(".successDeleteOrder");
                successDeleteOrder.hide();

                var response = JSON.parse(jqXHR.responseText);
                $('#errorDeleteOrder').append(response['message']);
            }
            else {
                window.location.href="error.html";
            }
        }
    });

});

var id;
$(document).on('click', '.btnChangeOrder', function () {
    id = this.id;
    id1 = JSON.stringify({"id" : id});

    var ordersShow = $(".ordersShow");
    var changeOrder = $(".changeOrder");
    var errorDeleteOrder = $(".errorDeleteOrder");
    var successDeleteOrder = $(".successDeleteOrder");
    var submitOrder = $(".submitOrder");
    var errorChangeOrder = $(".errorChangeOrder");
    var filterShow = $(".filterShow");

    ordersShow.hide();
    changeOrder.show();
    errorDeleteOrder.hide();
    successDeleteOrder.hide();
    submitOrder.hide();
    errorChangeOrder.hide();
    filterShow.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/medications/allmedications",
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
                row += "<td class='medicineName'>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['code'] + "</td>";
                row += "<td>" + data[i]['producer'] + "</td>";

                var checkboxSelect = "<input type='checkbox' class='checkboxSelect' id = " + data[i]['id'] + "> </input>";
                row += "<td>" + checkboxSelect + "</td>";

                var quantityInput = "<input type='number' class='quantityInput' min='1' value='1' disabled id = " + data[i]['id'] + "/>";
                row += "<td>" + quantityInput + "</td>";

                console.log("GG", quantityInput);

                $('#tableOrderMedicineShow').append(row);
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

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/orders/orderById",
        dataType: "json",
        contentType: "application/json",
        data: id1,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            $("#dateDeadline").val("");
            $('table [type="checkbox"]').each(function(id, chk) {
                chk.checked = false;
                document.getElementById(chk.id+"/").value = 1;
                document.getElementById(chk.id+"/").disabled = true;

            });
            for (i = 0; i < data['meds'].length; i++) {
                $('#tableOrderMedicineShow tbody tr').each(function () {
                    var medicineName = $(this).find(".medicineName").html();
                    if (data['meds'][i]['name'] === medicineName) {
                        console.log(medicineName);
                        $(this).find(".checkboxSelect").prop('checked', true);

                        $('table [type="checkbox"]').each(function(id, chk) {
                            if(chk.checked) {
                                document.getElementById(chk.id + "/").disabled = false;
                                document.getElementById(chk.id + "/").value = data['amounts'][i];
                            }
                        });
                    }
                });
            }
            $("#dateDeadline").append(data['dateDeadline']);
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

$(document).on('click', '.checkboxSelect', function () {
    $('table [type="checkbox"]').each(function (id, chk) {
        if (chk.checked) {
            document.getElementById(chk.id + "/").disabled = false;
            //document.getElementById(chk.id + "/").value = 1;
        } else {
            document.getElementById(chk.id + "/").disabled = true;
            //document.getElementById(chk.id + "/").value = 1;
        }
    });
});

$(document).on('click', '#btnSubmitOrder', function () {

    var ids = [];
    var vals = [];

    $('table [type="checkbox"]').each(function (id, chk) {
        if (chk.checked) {
            ids.push(chk.id)
            var val = document.getElementById(chk.id + "/").value;
            vals.push(val);
            //document.getElementById(chk.id+"/").value = 1;
            //document.getElementById(chk.id+"/").disabled = true;
        }

    });

    var dateDeadline = $("#dateDeadline").val();

    var myJSON = formToJSON(ids, vals, dateDeadline, id);

    dateDeadline = Date.parse(dateDeadline);

    if (ids.length !== 0 && dateDeadline > Date.now()) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/orders/changeOrder",
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
                /*var submitOrder = $(".submitOrder");
                submitOrder.hide();

                var checkboxSelect = $(".checkboxSelect");
                checkboxSelect.prop('checked', false);
                checkboxSelect.hide();

                var quantityInput = $(".quantityInput");
                quantityInput.val("");
                quantityInput.hide();*/
                var changeOrder = $(".changeOrder");
                changeOrder.hide();
                var ordersShow = $(".ordersShow");
                ordersShow.show();
            },
            error: function (jqXHR, data) {
                if (jqXHR.status === 500) {
                    var errorOrder = $(".errorChangeOrder");
                    errorOrder.show();

                    var response = JSON.parse(jqXHR.responseText);
                    $('#errorOrder').append(response['message']);

                    alert("Doslo je do greske");

                    var submitOrder = $(".submitOrder");
                    submitOrder.hide()

                    var ordersShow = $(".ordersShow");
                    ordersShow.hide();
                }
                else {
                    window.location.href = "error.html";
                }
                console.log(data);
            }
        });
    }
});

function formToJSON(ids, vals, dateDeadline, id) {
    return JSON.stringify(
        {
            "med_ids" : ids,
            "amounts" : vals,
            "dateDeadline" : dateDeadline,
            "id" : id
        }
    );
}


$(document).on('click', '.btnReturnOrder', function () {
    window.location.href="adminPharmacyChooseOffer.html";
});

$(document).on('click', '#btnStatusOrder', function() {
    var status = document.getElementById("statusOrder").value;
    status = JSON.stringify({"status":status});
    $('#tableOrdersShow tbody').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/orders/orderByStatus",
        dataType: "json",
        contentType: "application/json",
        data: status,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {

            console.log("SUCCESS", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['dateDeadline'] + "</td>";

                var btnOffers = "<button class='btnOffers' id = " + data[i]['id'] + ">Pogledaj ponude</button>";
                row += "<td>" + btnOffers + "</td>";

                var btnChangeOrder = "<button class='btnChangeOrder' id = " + data[i]['id'] + ">Izmeni narudzbenicu</button>";
                row += "<td>" + btnChangeOrder + "</td>";

                var btnDeleteOrder = "<button class='btnDeleteOrder' id = " + data[i]['id'] + ">Obrisi narudzbenicu</button>";
                row += "<td>" + btnDeleteOrder + "</td>";

                $('#tableOrdersShow').append(row);
            }
        },
        error: function (jqXHR) {
            if(jqXHR.status === 403) {
                window.location.href="error.html";
            }
            if(jqXHR.status === 401) {
                window.location.href="error.html";
            }
        }
    });


});