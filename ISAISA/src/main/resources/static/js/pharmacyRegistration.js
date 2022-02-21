$(document).on("submit", "form", function (event) {           // kada je submitovana forma za kreiranje novog zaposlenog
    event.preventDefault();
    var name = $("#name").val();
    var address = $("#address").val();
    var description = $("#description").val();


    var newUserJSON = formToJSON(name,address,description);
    $.ajax({
        type: "POST",
        url: "http://localhost:8081/systemadmins/signupPharmacy",
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



function formToJSON(name,address,description) {
    return JSON.stringify(
        {
            "name": name,
            "address": address,

            "description": description,


        }
    );
};