$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/pharmacy/allpharmacies",
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
                row += "<td>" + data[i]['address'] + "</td>";
                row += "<td>" + data[i]['description'] + "</td>";
                row += "<td>" + data[i]['name'] + "</td>";
                row += "<td>" + data[i]['rating'] + "</td>";



                var btn = "<button class='btnRegisterAdminPharmacy' id = " + data[i]['id'] + ">Register Admin Pharmacy</button>";


                row += "<td>" + btn + "</td>";
                $('#pharmacies').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});


$(document).on('click', '.btnRegisterAdminPharmacy', function () {

    var pharmacyDiv = $(".pharmaciesall");
    pharmacyDiv.hide();
    var formaDiv=$(".adminForm");
    formaDiv.show();
    var id=this.id;

    $(document).on("submit", "form", function (event) {           // kada je submitovana forma za kreiranje novog zaposlenog
        event.preventDefault();
        var namee = $("#namee").val();
        var lastName = $("#lastName").val();
        var password = $("#password").val();
        var adress = $("#adress").val();
        var email = $("#email").val();
        var city = $("#city").val();
        var country = $("#country").val();
        var phoneNumber = $("#phoneNumber").val();
        var pharmacyID=id;

        var newUserJSON = formToJSON(namee,lastName, password,  adress, email, city, country, phoneNumber, pharmacyID);
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/systemadmins/signupAdminPharmacy",
            dataType: "json",
            contentType: "application/json",
            data: newUserJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function () {
                alert("success");
                window.location.href = "adminSystemHomePage.html";
            },
            error: function (error) {
                alert(error);
            }
        });
    });
});


function formToJSON(namee,lastName, password, adress, email, city, country, phoneNumber,pharmacyID) {
    return JSON.stringify(
        {
            "firstName": namee,
            "lastName": lastName,

            "password": password,

            "address": adress,
            "email": email,
            "city": city,
            "country": country,
            "phone": phoneNumber,
            "pharmacyID": pharmacyID

        }
    );
};