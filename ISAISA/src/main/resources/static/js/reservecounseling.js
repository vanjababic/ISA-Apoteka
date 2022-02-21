$(document).on('click', '#btnSearchbyDate', function(){

    var freepharmacy= $(".freepharmacy");
    freepharmacy.show();

    var beginofappointment= $("#beginofappointment").val();
    var myJSON = formToJSON(beginofappointment);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/pharmacybydate",
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            alert("success");
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>"
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";


                var btn = "<button class='btnChoosePharmacy' id = " + data[i]['id'] + ">Izaberi apoteku</button>";
                row += "<td>" + btn + "</td>";
                $('#tablePharmacy').append(row);
            }
        },
        error: function (error) {
            alert(error);
        }
    });

});
function formToJSON(beginofappointment) {
    return JSON.stringify(
        {
            "beginofappointment": beginofappointment

        }
    );
}
function formToJSON2(beginofappointment,id) {
    return JSON.stringify(
        {
            "beginofappointment": beginofappointment,
            "id":id

        }
    );
}
var pharmacyid;
$(document).on('click', '.btnChoosePharmacy', function(){
    pharmacyid=this.id;
    var freepharmacy= $(".freepharmacy");
    freepharmacy.show();
    var freepharmacist=$(".freepharmacist");
    freepharmacist.show();
    var id=this.id;
    var beginofappointment= $("#beginofappointment").val();
    var myJSON = formToJSON2(beginofappointment,id);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/pharmacistbypharmacy",
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            alert("success");
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>"
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['firstName'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";


                var btn = "<button class='btnChoosePharmacist' id = " + data[i]['id'] + ">Izaberi farmaceuta</button>";
                row += "<td>" + btn + "</td>";
                $('#tablePharmacist').append(row);
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});
$(document).on('click', '.btnChoosePharmacist', function(){

    var freepharmacy= $(".freepharmacy");
    freepharmacy.show();
    var freepharmacist=$(".freepharmacist");
    freepharmacist.show();
    var id=this.id;
    var beginofappointment= $("#beginofappointment").val();
    var myJSON = formToJSON2(beginofappointment,id);

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/createCounseling",
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            alert("success");
            console.log("SUCCESS : ", data);

        },
        error: function (error) {
            alert(error);
        }
    });
});

$(document).on('click', '#btnSortbyocena', function () {    // Čeka se trenutak kada je DOM(Document Object Model) učitan da bi JS mogao sa njim da manipuliše.
    // ajax poziv
    var freepharmacy= $(".freepharmacy");
    freepharmacy.show();

    var beginofappointment= $("#beginofappointment").val();
    var myJSON = formToJSON(beginofappointment);
    $('#tablePharmacy').empty();
    $.ajax({
        type: "POST",                                                // HTTP metoda
        url: "http://localhost:8081/counselings/sortpharmacy",                  // URL koji se gađa
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            alert("success");
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>"
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['name'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";


                var btn = "<button class='btnChoosePharmacy' id = " + data[i]['id'] + ">Izaberi apoteku</button>";
                row += "<td>" + btn + "</td>";
                $('#tablePharmacy').append(row);
            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });

});

$(document).on('click', '#btnSortbyocenapharmacist', function(){
    var freepharmacy= $(".freepharmacy");
    freepharmacy.show();
    var id=this.id;
    var beginofappointment= $("#beginofappointment").val();
    var myJSON = formToJSON2(beginofappointment,pharmacyid);
    $('#tablePharmacist').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/sortpharmacistbypharmacy",
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            alert("success");
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var row = "<tr>"
                row += "<td>" + data[i]['id'] + "</td>";
                row += "<td>" + data[i]['firstName'] + "</td>";     // ubacujemo podatke jednog zaposlenog u polja
                row += "<td>" + data[i]['lastName'] + "</td>";
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";


                var btn = "<button class='btnChoosePharmacist' id = " + data[i]['id'] + ">Izaberi farmaceuta</button>";
                row += "<td>" + btn + "</td>";
                $('#tablePharmacist').append(row);
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});