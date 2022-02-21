$(document).ready(function () {
    $('#complaints tbody').empty();
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/systemadmins/allcomplaintspharmacy",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                if(data[i]['answered']===false){

                    var row = "<tr>";
                    row += "<td>" + data[i]['emailPatient'] + "</td>";
                    row += "<td>" + data[i]['nameHospital'] + "</td>";
                    row += "<td>" + data[i]['question'] + "</td>";
                    var btn = "<button class='btnReply' id = " + data[i]['id'] + ">Odgovori na zalbu</button>";


                    row += "<td>" + btn + "</td>";
                    $('#complaints').append(row);




                };



            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});


$(document).on('click', '.btnReply', function () {

    var pharmacyDiv = $(".complaintsall");
    pharmacyDiv.hide();
    var formaDiv=$(".complaintForm");
    formaDiv.show();
    var id=this.id;

    $(document).on("submit", "form", function (event) {           // kada je submitovana forma za kreiranje novog zaposlenog
        event.preventDefault();
        var reply = $("#reply").val();
        var complaintID=id;

        var newComplaintJSON = formToJSON(reply,complaintID);
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/systemadmins/reply",
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
                window.location.href = "adminSystemHomePage.html";
            },
            error: function (error) {
                alert(error);
            }
        });
    });
});


function formToJSON(reply,complaintID) {
    return JSON.stringify(
        {
            "reply": reply,
            "complaintID": complaintID

        }
    );
};