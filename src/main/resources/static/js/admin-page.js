$(document).ready(function() {
    console.log( "admin-page.js document loaded" );
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
            console.log("success");
            window.location.replace("http://localhost:8080/account");

            var token = status.getResponseHeader('Authorization');
            localStorage.setItem("token",token);
        },
        error: function (e) {
            console.log("error");
        },
        done : function(e) {
            console.log("done");
        }
    });
});


// var token = localStorage.getItem("token");
//
// console.log("token " + token);
var storedToken = localStorage.getItem("token");

$(document).ready(function() {
    console.log( "admin-page.js document loaded" );

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
});


function setPageData(data){

        for(var key in data) {

                var account = data[key];  // todo rename data
                var id = account.id;

                $( ".acc-items").append(
                    "<tr>" +
                    "<td>" + account.id +       "</td> " +
                    "<td>" + account.firstName +"</td>" +
                    "<td>" + account.nickname + "</td>" +
                    "<td>" + account.email +    "</td>" +
                    "<td>" + account.password + "</td>" +
                    "<td><button id="+ id + " type='button' class='btn btn-primary edit' data-toggle='modal' data-target='#editModal'>" + 'Edit' + "</button></td>" +
                    "<td><button id="+ id + " type='button' class='btn btn-primary del' >" + 'Delete' + "</button></td>" +
                    "<tr>"
                );

        }
}




// DELETE
$(document).on("click", ".del", function () {
    var id = $(this).attr('id');

    $.ajax({
            type: 'DELETE',
            url: '/api/accounts/delete/'+id,
            headers: {
                Authorization: 'Bearer ' + storedToken
            },
            data: 'id=' + id,
            success: () => { // arrow allows refer to this
                removeRowAfterDeleteRequest(this)
            },
    });
});


//remove row from all users when DELETE request successful
function removeRowAfterDeleteRequest(thisButton) {
    console.log("row removes from all users");
    $(thisButton).closest('tr').remove();
}

$(document).on("click", ".edit", function () {
    var id = $(this).attr('id');

    $.ajax({
        type: 'GET',
        url: '/api/accounts/'+id,
        headers: {
            Authorization: 'Bearer ' + storedToken
        },
        data: 'id=' + id,
        success: function (data) {
            populateDataToModal(data)
        }
    });

});

function populateDataToModal(data) {
    $('#id_edit').val(data.id);
    $('#first_name_edit').val(data.firstName);
    $('#nickname_edit').val(data.nickname);
    $('#email_edit').val(data.email);
    $('#password_edit').val(data.password);

    $.each(data.roles, function (index, value) {
       createRoleInput(value.roleName);
    });

}

function createRoleInput(roleName) {
        console.log("createRoleInput");
// add row
            //child exists
            var html = '';
            html += '<div id="inputFormRow">';
            html += '<div class="input-group mb-3">';
            html += '<input value='+ roleName +' type="text" name="title[]" class="form-control m-input" placeholder="Enter title" autocomplete="off">';
            html += '<div class="input-group-append">';
            html += '<button id="removeRow" type="button" class="btn btn-danger">Remove</button>';
            html += '</div>';
            html += '</div>';

        $('#newRow').append(html);
}

// remove row from update Modal window
$(document).on('click', '#removeRow', function () {
    $(this).closest('#inputFormRow').remove();
    console.log("remove unknown row")
});

// remove role row after close close window (avoid repeat role in each Edit modal window)
$(document).ready(function(){
    $('#editModal').on('hidden.bs.modal', function (e) {
        $('#newRow').children().remove();
    });
});

$(document).on("click", "#addRow", function () {
        createRoleInput();
});



//UPDATE
$(document).on("click", "#editButton", function () {

        var id = $('#id_edit').val();
        var fst_name = $("#first_name_edit").val();
        var nickname = $("#nickname_edit").val();
        var password = $("#password_edit").val();
        var email = $("#email_edit").val();

        var roles = [];

        $('#inputFormRow input').each(function () {
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
            type: "PUT",
            contentType: "application/json",
            url: '/api/accounts/update',
            headers: {
                Authorization: 'Bearer ' + storedToken
            },
            data: JSON.stringify(account),
            success: function(data){
                console.log("success");
                closeModal();
            },
            error: function (e) {
                console.log("error");
                closeModal();
            }
        });
});


function closeModal() {
  $( '#editModal' ).modal('toggle');
}

