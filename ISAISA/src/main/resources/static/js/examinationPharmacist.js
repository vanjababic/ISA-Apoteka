var examinationId = -1;

$(document).ready(function () {
    var examination = $(".examination")
    examination.show();
    var writeReport = $(".writeReport")
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication")
    prescriptMedication.hide();
    /*var scheduleNewAppointment = $(".scheduleNewAppointment")
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();

    $.ajax({
        type: "GET",
        url: "http://localhost:8081/examinations/getCounselingId",
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
    /*var scheduleNewAppointment = $(".scheduleNewAppointment")
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();

    $(document).on('click', '#btnSubmitReport', function () {
        var report = $("#chReport").val();
        var myJSON = JSON.stringify({"id":examinationId, "report":report});

        $.ajax({
            type: "POST",
            url: "http://localhost:8081/examinations/writeReportPharmacist",
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
    /*var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();

    $("#idDropDown").empty();

    var myJSON = JSON.stringify({"id":examinationId});
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/getMedicationsForPrescriptionPharmacist",
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
        url: "http://localhost:8081/examinations/checkIfMedicationIsAvailablePharma",
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
        url: "http://localhost:8081/examinations/savePrescriptionPharmacist",
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
    /*var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();
});



$(document).on('click', '#btnCancle', function () {
    console.log('opopo');
    var examination = $(".examination");
    examination.show();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    /*var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();
});


$(document).on('click', '#btnAlternative', function () {
    var examination = $(".examination");
    examination.hide();
    var writeReport = $(".writeReport");
    writeReport.hide();
    var prescriptMedication = $(".prescriptMedication");
    prescriptMedication.hide();
    /*var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.show();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();

    $("#idAlternativeDropDown").empty();

    var myJSON = JSON.stringify({"id":examinationId, "name":imeIzabranogLeka});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/getAlternativeMedicationsPharmacist",
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
    /*var scheduleNewAppointment = $(".scheduleNewAppointment");
    scheduleNewAppointment.hide();*/
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();
});



$(document).on('change', '#idAlternativeDropDown', function () {
    //console.log('Something');
    var name = $("#idAlternativeDropDown :selected").val();
    var duration = $("#chTherapyDurationAlternative").val();
    var myJSON = JSON.stringify({"id":examinationId, "name":name,"duration":duration});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/examinations/savePrescriptionPharmacist",
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
    var medInPharmacy = $(".medInPharmacy");
    medInPharmacy.hide();
    var medNotInPharmacy = $(".medNotInPharmacy");
    medNotInPharmacy.hide();
    var alternativeMedication = $(".alternativeMedication");
    alternativeMedication.hide();
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.show();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();
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
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();
});

$(document).on('click', '#btnCreateCounseling', function () {
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
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.show();

});

$(document).on('click', '#btnCancleCreatingCounseling', function () {
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
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();
});



$(document).on('click', '#btnCreateCounselingNow', function () {

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
    var ScheduleCounseling = $(".ScheduleCounseling")
    ScheduleCounseling.hide();
    var createCounseling = $(".createCounseling")
    createCounseling.hide();

    var startOfCounseling = $("#chStartOfCounseling").val();
    var endOfCounseling = $("#chEndOfCounseling").val();

    //var price = $("#chPrice").val();
    var price = 3000;

    var myJSON = JSON.stringify({"id":examinationId, "startOfAppointment":startOfCounseling , "endOfAppointment":endOfCounseling, "price":price});

    $.ajax({
        type: "POST",
        url: "http://localhost:8081/counselings/createCounselingPharmacist",
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
                alert("Termin nije zakazan. Zauzet je.");
            }
        },
        error: function (error) {
            alert(error);
        }
    });
});

$(document).on('click', '#btnExaminationDone', function () {
    alert('Pregled je odradjen');
    window.location.href="welcomePharmacist.html";

});
