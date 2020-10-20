$(document).ready(function() {
    console.log( "login.js document loaded" );

    var token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwZXRlciIsImF1dGhvcml0aWVzIjpbeyJpZCI6MTQ2LCJyb2xlTmFtZSI6IkFETUlOIiwiYXV0aG9yaXR5IjoiQURNSU4ifSx7ImlkIjoxNDcsInJvbGVOYW1lIjoiVVNFUiIsImF1dGhvcml0eSI6IlVTRVIifV0sImlhdCI6MTYwMzAzMzI4OCwiZXhwIjoxNjA0MTgxNjAwfQ.YO0bnSKEW95ppQzXbT0dfxZsSGp0UUoHeOQpb1m8glqKgSpRjw8LyYNIJ48DgCDYSbSM4KlLO0MsXN_sd89eyg";
    localStorage.setItem('token', token);



});

function setJwtToken(token) {
    localStorage.setItem(token);
}

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
            localStorage.setItem('token', token);
            console.log("success");
            setJwtToken(data.token);
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



