/*
$(document).ready(function() {
    console.log( "login.js document loaded" );
});

$(document).on("click", "#signInButton", function () {
    console.log( "login.js signInButton" );

    var fst_name = $("#exampleInputUsername").val();
    var password = $("#exampleInputPassword").val();

     var credentials = {
         "username": fst_name,
         "password": password
     }

    $.ajax({
        cache: true,
        type: "POST",
        contentType: "application/json",
        url: '/login',
        data: JSON.stringify(credentials),
        success: function(data, textStatus, status){
            localStorage.setItem("token",data.token);
            console.log("success");
            console.log(status.getResponseHeader('Authorization'));
        },
        error: function (e) {
            console.log("error");
        },
        done : function(e) {
            console.log("done");
        }
    });
});



*/
