$( document ).ready(function() {
    console.log( "document loaded" );

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/api/accounts/getAccounts',
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

        for(key in data) {

                var account = data[key];
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

$(document).on("click", ".del", function () {
    var id = $(this).attr('id');

    $.ajax({
            type: 'DELETE',
            url: '/api/accounts/delete/'+id,
            data: 'id=' + id,
            success: function (data) {
                    //reload page
                    location.reload();
            }
    });
});

$(document).on("click", ".edit", function () {
        var id = $(this).attr('id');
        $.get( "/api/accounts/"+id, function( data ) {
            populateDataToModal(data);
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

// remove role row after close close window (avoid repeat role in each Edit modal window)
$(document).ready(function(){
    $('#editModal').on('hidden.bs.modal', function (e) {
        $('#newRow').children().remove();
    });
});

$(document).on("click", "#addRow", function () {
        createRoleInput();
});
// remove row
$(document).on('click', '#removeRow', function () {
    $(this).closest('#inputFormRow').remove();
});


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
            data: JSON.stringify(account),
            success: function(data){
                console.log("success");
                closeModal();
            },
            error: function (e) {
                console.log("error");
                closeModal();
            },
            done : function(e) {
                console.log("done");
                closeModal();
            }
        });
});


function closeModal() {
  $( '#editModal' ).modal('toggle');
}

