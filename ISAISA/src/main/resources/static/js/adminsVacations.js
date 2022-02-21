$(document).ready(function () {
    var vacationRequestsShow = $(".vacationRequestsShow");
    var approveVacation = $(".approveVacation");
    var denyVacation = $(".denyVacation");

    vacationRequestsShow.show();
    approveVacation.hide();
    denyVacation.hide();

    var role = localStorage.getItem('role');

    if (role === 'adminpharmacy'){
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/vacations/pharmacistRequests",
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
                    row += "<td>" + data[i]['pharmacist']['id'] + "</td>";
                    row += "<td>" + data[i]['pharmacist']['firstName'] + "</td>";
                    row += "<td>" + data[i]['pharmacist']['lastName'] + "</td>";
                    row += "<td>" + data[i]['dateBeginVacation'] + "</td>";
                    row += "<td>" + data[i]['dateEndVacation'] + "</td>";

                    var btnApprove = "<button class='btnApprove' id = " + data[i]['id'] + "> Odobri zahtev </button>";
                    row += "<td>" + btnApprove + "</td>";

                    var btnDeny = "<button class='btnDeny' id = " + data[i]['id'] + "> Odbij zahtev </button>";
                    row += "<td>" + btnDeny + "</td>";

                    $('#tableVacationRequests').append(row);
                }
            },
            error: function (jqXHR) {
                window.location.href = "error.html";
            }
        });
    }
    else if (role === 'adminsystem') {
        $.ajax({
            type: "GET",
            url: "http://localhost:8081/vacations/dermatologistRequests",
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
                    row += "<td>" + data[i]['dermatologist']['id'] + "</td>";
                    row += "<td>" + data[i]['dermatologist']['firstName'] + "</td>";
                    row += "<td>" + data[i]['dermatologist']['lastName'] + "</td>";
                    row += "<td>" + data[i]['dateBeginVacation'] + "</td>";
                    row += "<td>" + data[i]['dateEndVacation'] + "</td>";

                    var btnApprove = "<button class='btnApprove' id = " + data[i]['id'] + "> Odobri zahtev </button>";
                    row += "<td>" + btnApprove + "</td>";

                    var btnDeny = "<button class='btnDeny' id = " + data[i]['id'] + "> Odbij zahtev </button>";
                    row += "<td>" + btnDeny + "</td>";

                    $('#tableVacationRequests').append(row);
                }
            },
            error: function (jqXHR) {
                //window.location.href="error.html";
            }
        });
    }
});

$(document).on('click', '.btnApprove', function () {
    var vacationRequestsShow = $(".vacationRequestsShow");
    var approveVacation = $(".approveVacation");
    var denyVacation = $(".denyVacation");
    var successfulApproveVacation = $(".successfulApproveVacation");
    var unsuccessfulApproveVacation = $(".unsuccessfulApproveVacation");

    vacationRequestsShow.hide();
    approveVacation.show();
    denyVacation.hide();
    successfulApproveVacation.hide()
    unsuccessfulApproveVacation.hide();

    var id = this.id;
    id = JSON.stringify({"id" : id});
    var role = localStorage.getItem('role');

    if (role === 'adminpharmacy') {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/vacations/approvePharmacist",
            dataType: "json",
            contentType: "application/json",
            data: id,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function (data) {
                var successfulApproveVacation = $(".successfulApproveVacation");
                successfulApproveVacation.show();
            },
            error: function (jqXHR, data) {
                if(jqXHR.status === 500) {
                    var unsuccessfulApproveVacation = $(".unsuccessfulApproveVacation");
                    unsuccessfulApproveVacation.show();

                    var response = JSON.parse(jqXHR.responseText);
                    document.getElementById('unsuccessfulApproveVacation').innerHTML = response['message'];
                } else {
                    window.location.href = "error.html";
                }
            }
        });
    } else if (role === 'adminsystem') {
        $.ajax({
            type: "POST",
            url: "http://localhost:8081/vacations/approveDermatologist",
            dataType: "json",
            contentType: "application/json",
            data: id,
            beforeSend: function (xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function (data) {
                var successfulApproveVacation = $(".successfulApproveVacation");
                successfulApproveVacation.show();
            },
            error: function (jqXHR) {
                if(jqXHR.status === 500) {;
                    var unsuccessfulApproveVacation = $(".unsuccessfulApproveVacation");
                    unsuccessfulApproveVacation.show();

                    var response = JSON.parse(jqXHR.responseText);
                    document.getElementById('unsuccessfulApproveVacation').innerHTML = response['message'];
                } else {
                    window.location.href = "error.html";
                }
            }
        });
    }
});

$(document).on('click', '.btnDeny', function () {
    var vacationRequestsShow = $(".vacationRequestsShow");
    var approveVacation = $(".approveVacation");
    var denyVacation = $(".denyVacation");
    var formDenyVacation = $(".formDenyVacation");
    var successfulDenyVacation = $(".successfulDenyVacation");
    var unsuccessfulDenyVacation = $(".unsuccessfulDenyVacation");

    vacationRequestsShow.hide();
    approveVacation.hide();
    denyVacation.show();
    formDenyVacation.show();
    successfulDenyVacation.hide();
    unsuccessfulDenyVacation.hide();

    var id = this.id;

    $(document).on('click', '#btnSubmitDenial', function () {

        var reasonDenied = $("#reasonDenial").val();
        var myJSON = JSON.stringify({"id" : id, "reasonDenied" : reasonDenied});

        var role = localStorage.getItem('role');

        if (role === 'adminpharmacy') {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/vacations/denyPharmacist",
                dataType: "json",
                contentType: "application/json",
                data: myJSON,
                beforeSend: function (xhr) {
                    if (localStorage.token) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                    }
                },
                success: function (data) {
                    var successfulDenyVacation = $(".successfulDenyVacation");
                    successfulDenyVacation.show();
                    var formDenyVacation = $(".formDenyVacation");
                    formDenyVacation.hide();
                },
                error: function (jqXHR, data) {
                    if(jqXHR.status === 500) {
                        var unsuccessfulDenyVacation = $(".unsuccessfulDenyVacation");
                        unsuccessfulDenyVacation.show();

                        var formDenyVacation = $(".formDenyVacation");
                        formDenyVacation.hide();

                        var response = JSON.parse(jqXHR.responseText);
                        document.getElementById('unsuccessfulDenyVacation').innerHTML = response['message'];
                    } else {
                        window.location.href = "error.html";
                    }
                }
            });

        } else if (role === 'adminsystem') {
            $.ajax({
                type: "POST",
                url: "http://localhost:8081/vacations/denyDermatologist",
                dataType: "json",
                contentType: "application/json",
                data: myJSON,
                beforeSend: function (xhr) {
                    if (localStorage.token) {
                        xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                    }
                },
                success: function (data) {
                    var successfulDenyVacation = $(".successfulDenyVacation");
                    successfulDenyVacation.show();
                    var formDenyVacation = $(".formDenyVacation");
                    formDenyVacation.hide();
                },
                error: function (jqXHR, data) {
                    if(jqXHR.status === 500) {
                        var unsuccessfulDenyVacation = $(".unsuccessfulDenyVacation");
                        unsuccessfulDenyVacation.show();
                        var formDenyVacation = $(".formDenyVacation");
                        formDenyVacation.hide();

                        var response = JSON.parse(jqXHR.responseText);
                        document.getElementById('unsuccessfulDenyVacation').innerHTML = response['message'];
                    } else {
                        window.location.href = "error.html";
                    }
                }
            });
        }
    });
});

$(document).on('click', '#btnCancelDenial', function () {
    $("#reasonDenial").val("");

    var vacationRequestsShow = $(".vacationRequestsShow");
    var denyVacation = $(".denyVacation");

    vacationRequestsShow.show();
    denyVacation.hide();
});

$(document).on('click', '.returnToVacations', function () {
   window.location.href="adminsVacations.html";
});