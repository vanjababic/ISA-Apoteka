$(document).ready(function () {
    requestVacation = $(".requestVacation")
    requestVacation.show();
});

$(document).on('click', '#btnCancleVacation', function () {
    window.location.href="welcomeDermatologist.html";
});



$(document).on('click', '#btnRequestVacation', function () {

    var startOfVacation = $("#chStartOfVacation").val();
    var endOfVacation = $("#chEndOfVacation").val();
    var absence;// = $("#absence").checked();

    if(document.getElementById('absence').checked){
        absence=1;
    }else {
        absence=0;
    }
    console.log(startOfVacation, endOfVacation, absence)
    var myJSON = JSON.stringify({"beginVacation":startOfVacation, "endVacation":endOfVacation,"absence":absence});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/vacations/requestVacationDermatologist",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            window.location.href="welcomeDermatologist.html";
            alert('Zahtev poslat');

        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});



