$.get( "/api/accounts/getAccounts", function( data ) {
        $( ".result" ).html( data );
        setPageData(data);
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
                    "<td><button id="+ id + " type='button' class='btn btn-primary edit' data-toggle='modal' data-target='#exampleModal'>" + 'Edit' + "</button></td>" +
                    "<td><button id="+ id + " type='button' class='btn btn-primary del' >" + 'Delete' + "</button></td>" +
                    "<tr>"
                );

        }
}

$(document).ready(function(){
    $(".del").click(function () {
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
    })
});

$(document).ready(function () {
    $('.edit').on('click', function () {
        var id = $(this).attr('id');

        $.getJSON( "/api/accounts/"+id+"", function( data ) {
            $('#id_edit', ).val(data.id);
            $('#first_name_edit', ).val(data.firstName);
            $('#nickname_edit').val(data.nickname);
            $('#email_edit').val(data.email);

            $.each(data.roles, function (index, value) {
               createRoleInput(value.roleName);
            });
        });
    });
});


function createRoleInput(roleName) {
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
    $('#exampleModal').on('hidden.bs.modal', function (e) {
        $('#newRow').children().remove();
    });
});

$(document).ready(function(){
// add row
    $("#addRow").click(function () {

        createRoleInput();
    });

    // remove row
    $(document).on('click', '#removeRow', function () {
        $(this).closest('#inputFormRow').remove();
    })
});


$(document).ready(function(){
    $("#editButton").click(function(e) {

        e.preventDefault(); // avoid to execute the actual submit of the form.

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
            dataType: 'json',
            success: function (data) {
            },
            error: function (e) {
            }
        });

    });
});

