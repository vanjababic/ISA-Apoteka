$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: "http://localhost:8081/medications/allmedications",
        dataType: "json",
        beforeSend: function(xhr) {
            if (localStorage.token) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.token);
            }
        },
        success: function (data) {
            console.log("SUCCESS : ", data);
            for (i = 0; i < data.length; i++) {
                var name = data[i]['name'];
                $("#idDropDown").append("<option value='" + name + "'>" + name + "</option>");
            }


        },
        error: function (data) {
            console.log("ERROR : ", data);
        }
    });
});



$(document).on("submit", "form", function (event) {
    event.preventDefault();

    var namee = $("#name").val();
    var code = $("#code").val();
    var type = $("#type_med").val();
    var shape = $("#shape").val();
    var ingredients = $("#ingredients").val();
    var producer = $("#producer").val();
    var contraindication = $("#contraindication").val();
    var recommended_daily_intake = $("#intake").val();
    var alternative = $("#idDropDown :selected").val();

    var newUserJSON =  formToJSON(namee,code,type,shape,ingredients,producer,contraindication,recommended_daily_intake,alternative);

    $.ajax({
        type: "POST",                                               // HTTP metoda je POST
        url: "http://localhost:8081/systemadmins/registerMedication",                 // URL na koji se šalju podaci
        dataType: "json",                                           // tip povratne vrednosti
        contentType: "application/json",                            // tip podataka koje šaljemo
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

function formToJSON(namee,code,type,shape,ingredients,producer,contraindication,recommended_daily_intake,alternative) {
    return JSON.stringify(
        {
            "name": namee,
            "code": code,

            "type_med": type,

            "shape_med": shape,
            "ingredients": ingredients,
            "producer": producer,
            "contraindication": contraindication,
            "recommended_daily_intake": recommended_daily_intake,
            "alternative":alternative

        }
    );
}