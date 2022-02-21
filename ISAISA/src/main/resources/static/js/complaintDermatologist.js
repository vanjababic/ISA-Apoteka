$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/patients/allcomplaintdermatologist",
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
                row += "<td>" + data[i]['firstName'] + "</td>";
                row += "<td>" + data[i]['email'] + "</td>";



                var btn = "<button class='btnComplaint' id = " + data[i]['id'] + ">Pisi zalbu</button>";


                row += "<td>" + btn + "</td>";
                $('#complaints').append(row);

            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '.btnComplaint', function () {

    var pharmacyDiv = $(".complaintsall");
    pharmacyDiv.hide();
    var formaDiv=$(".complaintForm");
    formaDiv.show();
    var id=this.id;

    $(document).on("submit", "form", function (event) {           // kada je submitovana forma za kreiranje novog zaposlenog
        event.preventDefault();
        var reply = $("#complaint").val();
        var adminID=id;

        var newComplaintJSON = formToJSON(reply,adminID);
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/patients/complaintDermatologist",
            dataType: "json",
            contentType: "application/json",
            data: newComplaintJSON,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function () {
                alert("success");
                window.location.href = "patientWelcome.html";
            },
            error: function (error) {
                alert(error);
            }
        });
    });
});


function formToJSON(reply,adminID) {
    return JSON.stringify(
        {
            "reply": reply,
            "complaintID": adminID

        }
    );
};