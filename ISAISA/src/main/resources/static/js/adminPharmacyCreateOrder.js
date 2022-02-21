$(document).ready(function () {

    var submitOrder = $(".submitOrder");
    submitOrder.hide()

    var errorOrder = $(".errorOrder");
    errorOrder.hide();

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
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['code'] + "</td>";
                row += "<td>" + data[i]['producer'] + "</td>";

                var checkboxSelect = "<input type='checkbox' class='checkboxSelect' id = " + data[i]['id'] + "> </input>";
                row += "<td>" + checkboxSelect + "</td>";

                var quantityInput = "<input type='number' class='quantityInput' min='1' value='1' disabled id = " + data[i]['id'] + "/>";
                row += "<td>" + quantityInput + "</td>";

                console.log("GG", quantityInput);

                $('#tableOrderMedicineShow').append(row);
            }

            $(".checkboxSelect").hide();
            $(".quantityInput").hide();
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

$(document).on('click', '.selectMedicineForOrder', function () {

    var checkboxSelect = $(".checkboxSelect");
    checkboxSelect.show();

    var quantityInput = $(".quantityInput");
    quantityInput.show();

    var submitOrder = $(".submitOrder");
    submitOrder.show();

});

$(document).on('click', '.checkboxSelect', function () {
    $('table [type="checkbox"]').each(function (id, chk) {
        if (chk.checked) {
            document.getElementById(chk.id + "/").disabled = false;
            document.getElementById(chk.id + "/").value = 1;
        } else {
            document.getElementById(chk.id + "/").disabled = true;
            document.getElementById(chk.id + "/").value = 1;
        }
    });
});


$(document).on('click', '#btnSubmitOrder', function () {

    var ids = [];
    var vals = [];

    $('table [type="checkbox"]').each(function(id, chk) {
        if (chk.checked) {
            ids.push(chk.id)
            var val = document.getElementById(chk.id+"/").value;
            vals.push(val);
            document.getElementById(chk.id+"/").value = 1;
            document.getElementById(chk.id+"/").disabled = true;
        }
    });

    var dateDeadline = $("#dateDeadline").val();

    var myJSON = formToJSON(ids, vals, dateDeadline);

    dateDeadline = Date.parse(dateDeadline);


    if (ids.length > 0 && dateDeadline > Date.now()){
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/orders/createOrder",
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
                var submitOrder = $(".submitOrder");
                submitOrder.hide();

                var checkboxSelect = $(".checkboxSelect");
                checkboxSelect.prop('checked', false);
                checkboxSelect.hide();

                var quantityInput = $(".quantityInput");
                quantityInput.val("");
                quantityInput.hide();
            },
            error: function (jqXHR, data) {
                if (jqXHR.status === 500) {
                    var errorOrder = $(".errorOrder");
                    errorOrder.show();

                    var response = JSON.parse(jqXHR.responseText);
                    $('#errorOrder').append(response['message']);

                    var submitOrder = $(".submitOrder");
                    submitOrder.hide()

                    var ordersShow = $(".ordersShow");
                    ordersShow.hide();
                } else {
                    console.log(data);
                    //window.location.href = "error.html";
                }
            }
        });
    }
});

function formToJSON(ids, vals, dateDeadline) {
    return JSON.stringify(
        {
            "med_ids" : ids,
            "amounts" : vals,
            "dateDeadline" : dateDeadline
        }
    );
}

$(document).on('click', '#btnCancelOrder', function () {
    var submitOrder = $(".submitOrder");
    submitOrder.hide();

    var checkboxSelect = $(".checkboxSelect");
    checkboxSelect.prop('checked', false);
    checkboxSelect.hide();

    var quantityInput = $(".quantityInput");
    quantityInput.val("");
    quantityInput.hide();
});

$(document).on('click', '#btnErrorOrder', function () {
    var errorOrder = $(".errorOrder");
    errorOrder.hide();

    var ordersShow = $(".ordersShow");
    ordersShow.show();

    var checkboxSelect = $(".checkboxSelect");
    checkboxSelect.prop('checked', false);
    checkboxSelect.hide();

    var quantityInput = $(".quantityInput");
    quantityInput.val("");
    quantityInput.hide();
});