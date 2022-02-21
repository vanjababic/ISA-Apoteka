var examinationId = -1;

$(document).ready(function () {
    var examination = $(".examination")
    examination.show();
    var writeReport = $(".writeReport")
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication")
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment")
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/examinations/getAppointmentId",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            examinationId = data['id'];
            console.log(examinationId);
        },
        error: function () {
            window.location.href="error.html";
        }
    });
});

$(document).on('click', '#btnReport', function () {
    var examination = $(".examination")
    examination.hide();
    var writeReport = $(".writeReport")
    writeReport.show();
    var prescriptMedication = $(".prescriptMedication")
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment")
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    $(document).on('click', '#btnSubmitReport', function () {
        var report = $("#chReport").val();
        var myJSON = JSON.stringify({"id":examinationId, "report":report});

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/examinations/writeReport",
            dataType: "json",
            contentType: "application/json",
            data: myJSON,
            beforeSend: function(xhr) {
                if (localStorage.token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
                }
            },
            success: function (data) {
                var writeReport = $(".writeReport")
                writeReport.hide();
                var examination = $(".examination")
                examination.show();
                alert("Izmene su sacuvane");
            },
            error: function (data) {
                window.location.href="error.html";
            }
        });
    });
});

$(document).on('click', '#btnPrescript', function () {
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.show();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    $("#idDropDown").empty();

    var myJSON = JSON.stringify({"id":examinationId});
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/getMedicationsForPrescription",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log('Success', data);
            $("#idDropDown").append("<option> </option>");
            for (i = 0; i < data.length; i++) {
                /*var row = "<tr>";
                row += "<td>" + data[i]['name'] + "</td>";
                var btn = "<button class='btnButton' id = " + data[i]['name'] + ">Proveri</button>";
                row += "<td>" + btn + "</td>";
                $('#tableMeds').append(row);*/
                var name = data[i]['name'];
                $("#idDropDown").append("<option value='"+name+"'>"+name+"</option>");
            }
        },
        error: function (data) {
            console.log('Error', data);
        }
    });
});
var imeIzabranogLeka;

$(document).on('change', '#idDropDown', function () {
    //console.log('Something');
    var name = $("#idDropDown :selected").val();
    var myJSON = JSON.stringify({"id":examinationId, "name":name});
    imeIzabranogLeka = name;
    /*console.log(name, examinationId);
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();



    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/checkIfMedicationIsAvailable",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log('Success', data);

            var d = data['value'];
            console.log('d',d);
            if(d === true){
                medInPharmacy.show();
                /*var row = "<tr>";
                row += "<td>Lek je dostupan u apoteci.</td>";
                var btn = "<button class='btnSubmitPrescription' id = " + name + ">Potvrdi</button>";
                row += "<td>" + btn + "</td>";
                $('#tableMedInPharmacy').append(row);*/
            }
            else {
                medNotInPharmacy.show();
            }

        },
        error: function (data) {
            console.log('Error', data);
        }
    });
});

$(document).on('click', '#btnPrescriptMedication', function () {
    //console.log('Something');
    var duration = $("#chTherapyDuration").val();
    var myJSON = JSON.stringify({"id":examinationId, "name":imeIzabranogLeka,"duration":duration});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/savePrescription",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log('Success', data);
            var medInPharmacy = $(".medInPharmacy");
            medInPharmacy.hide();
            var prescriptMedication = $(".prescriptMedication");
            prescriptMedication.hide();
            var examination = $(".examination");
            examination.show();
            alert('Lek je prepisan');
        },
        error: function (data) {
            console.log('Error', data);
        }
    });
});

$(document).on('click', '#btnCanclePrescription', function () {
    console.log('opopo');
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});

$(document).on('click', '#btnCancle', function () {
    console.log('opopo');
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});

$(document).on('click', '#btnAlternative', function () {
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.show();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    $("#idAlternativeDropDown").empty();

    var myJSON = JSON.stringify({"id":examinationId, "name":imeIzabranogLeka});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/getAlternativeMedications",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            $("#idAlternativeDropDown").append("<option> </option>");
            console.log('Success', data);
            for (i = 0; i < data.length; i++) {
                /*var row = "<tr>";
                row += "<td>" + data[i]['name'] + "</td>";
                var btn = "<button class='btnButton' id = " + data[i]['name'] + ">Proveri</button>";
                row += "<td>" + btn + "</td>";
                $('#tableMeds').append(row);*/
                var name = data[i]['name'];
                $("#idAlternativeDropDown").append("<option value='"+name+"'>"+name+"</option>");
            }
        },
        error: function (data) {
            console.log('Error', data);
        }
    });

});


$(document).on('click', '#btnCancleAlternative', function () {
    console.log('opopo');
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});


$(document).on('change', '#idAlternativeDropDown', function () {
    //console.log('Something');
    var name = $("#idAlternativeDropDown :selected").val();
    var duration = $("#chTherapyDurationAlternative").val();
    var myJSON = JSON.stringify({"id":examinationId, "name":name,"duration":duration});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/savePrescription",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log('Success', data);
            var alternativeMedication = $(".alternativeMedication");
            alternativeMedication.hide();
            var examination = $(".examination");
            examination.show();
            alert('Lek je prepisan');
        },
        error: function (data) {
            console.log('Error', data);
        }
    });
});

$(document).on('click', '#btnNewAppointment', function () {
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.show();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});

$(document).on('click', '#btnSelectExistingAppointment', function () {
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.show();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    var myJSON = JSON.stringify({"id":examinationId});
    $('#tableExistingAppointments').empty();
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/existingAppointmentsDermatologist",
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
            for (i = 0; i < data.length; i++) {
                var row = "<tr>";
                row += "<td>" + data[i]['beginofappointment'] + "</td>";
                row += "<td>" + data[i]['endofappointment'] + "</td>";
                row += "<td>" + data[i]['price'] + "</td>";


                var btn = "<button class='btnRegisterAppointment' id = " + data[i]['id'] + ">Rezervisi termin</button>";


                row += "<td>" + btn + "</td>";
                $('#tableExistingAppointments').append(row);

            }
        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});

$(document).on('click', '#btnCancleScheduling', function () {
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});

$(document).on('click', '#btnCancleSchedulingFreeAppointments', function () {
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});

$(document).on('click', '.btnRegisterAppointment', function(){
    var appointmentId = this.id;
    var myJSON = JSON.stringify({"id":examinationId, "appointmentId":appointmentId});


    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/saveAppointmentDermatologist",
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log('Success:',data);
            alert("Termin je zakazan");
        },
        error: function (error) {
            alert(error);
        }
    });

});

$(document).on('click', '#btnCreateAppointment', function () {
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.show();

});

$(document).on('click', '#btnCancleCreatingAppointments', function () {
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();
});

$(document).on('click', '#btnCreateAppointmentNow', function () {

    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var howToScheduleAppointment = $(".howToScheduleAppointment")
    howToScheduleAppointment.hide();
    var existingAppointments = $(".existingAppointments")
    existingAppointments.hide();
    var createAppointment = $(".createAppointment")
    createAppointment.hide();

    var startOfAppointment = $("#chStartOfAppointment").val();
    var endOfAppointment = $("#chEndOfAppointment").val();

    //var price = $("#chPrice").val();
    var price = 3000;

    var myJSON = JSON.stringify({"id":examinationId, "startOfAppointment":startOfAppointment , "endOfAppointment":endOfAppointment, "price":price});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/appointments/createAppointmentDermatologist",
        dataType: "json",
        contentType: "application/json",
        data:myJSON,
        beforeSend: function (xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            if(data['value']==true) {
                console.log('Success:', data);
                alert("Termin je zakazan");
            }
            else {
                console.log('Success:', data);
                alert("Termin nije zakazan, zauzet je.");
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});

$(document).on('click', '#btnExaminationDone', function () {
    alert('Pregled je odradjen');
    window.location.href="welcomeDermatologist.html";

});






/*$(document).on('change', '#idDropDown', function () {
    //console.log('Something');
    var name = $("#idDropDown :selected").val();
    var myJSON = JSON.stringify({"id":examinationId, "name":name});
    console.log(name, examinationId);
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();


    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/checkIfMedicationIsAvailable",
        dataType: "json",
        contentType: "application/json",
        data: myJSON,
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log('Success', data);

            var d = data['value'];
            console.log('d',d);
            if(d === true){
                medInPharmacy.show();
                var row = "<tr>";
                row += "<td>Lek je dostupan u apoteci.</td>";
                var btn = "<button class='btnSubmitPrescription' id = " + name + ">Potvrdi</button>";
                row += "<td>" + btn + "</td>";
                $('#tableMedInPharmacy').append(row);
            }
            else {
                medNotInPharmacy.show();
                var row = "<tr>";
                row += "<td>Lek je nedostupan. Administrator apoteke je obavesten.</td>";
                var btn1 = "<button class='btnCancle'>Odustani od preporucivanja leka</button>";
                var btn2 = "<button class='btnAlternativa'>Pogledaj alternative</button>";
                row += "<td>" + btn1 + "</td>";
                row += "<td>" + btn2 + "</td>";
                $('#tableMedNotInPharmacy').append(row);
            }

        },
        error: function (data) {
            console.log('Error', data);
        }
    });
});*/

