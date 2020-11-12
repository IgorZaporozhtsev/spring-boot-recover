$( document ).ready(function() {
    console.log( "add-account.js - document loaded" );
});

function createAddRoleInput() {
    console.log( "add-account.js - createAddRoleInput" );
// add row
    //child exists
    var html = '';
    html += '<div id="inputAddFormRow">';
    html += '<div class="input-group mb-3">';
    html += '<input type="text" name="title[]" class="form-control m-input" placeholder="Enter title" autocomplete="off">';
    html += '<div class="input-group-append">';
    html += '<button id="removeAddRow" type="button" class="btn btn-danger">Remove</button>';
    html += '</div>';
    html += '</div>';

    $('#newAddRow').append(html);
}

$(document).on("click", "#createRow", function () {
    console.log( "create Row" );
    createAddRoleInput();
});

$(document).on('click', '#removeAddRow', function () {
    $(this).closest('#inputAddFormRow').remove();
});

$(document).on("click", "#addButton", function () {

    window.location.replace("http://localhost:8080/account");
    getAdminPageData();

    var id = $('#add_id').val();
    var fst_name = $("#add_firstName").val();
    var nickname = $("#add_nickname").val();
    var password = $("#add_password").val();
    var email = $("#add_email").val();

    var roles = [];
    // [ {roleName:ADMIN}, {authority: ADMIN} ]

    $('#inputAddFormRow input').each(function () {
        var role = $(this).val();
        roles.push(role);
    });

    var account = {
        "id": id,
        "firstName": fst_name,
        "nickname": nickname,
        "password": password,
        "email": email,
        "roles": roles
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/api/accounts/add',
        headers: {
            Authorization: 'Bearer ' + storedToken
        },
        data: JSON.stringify(account),
        success: function(data){
            console.log("success");
        },
        error: function (e) {
            console.log("error");
        },
        done : function(e) {
            console.log("done");
        }
    });
});


function getAdminPageData(){

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/api/accounts/getAccounts',
        headers: {
            Authorization: 'Bearer ' + storedToken
        },
        success: function(data){
            console.log("success");
            setPageData(data);
        },
        error: function (data) {
            console.log("error");
            console.log(data.responseJSON);
        }
    });
}