var derivedData;

$.get( "/api/accounts/getAccounts", function( data ) {
        $( ".result" ).html( data );
        derivedData = data;
        setPageData(derivedData);
});



function setPageData(derivedData){

        for(key in derivedData) {

                var account = derivedData[key];
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
    $('.edit').one('click', function () {
        var id = $(this).attr('id');

        $.getJSON( "/api/accounts/"+id+"", function( data ) {
            $('#first_name_edit', ).val(data.firstName);
            $('#nickname_edit').val(data.nickname);
            $('#email_edit').val(data.email);

            $.each(data.roles, function (index, value) {
               createRoleInput(value.roleName);
            });
        });
    });
});

/*$(document).ready(function(){
    $('#exampleModal').on('hidden.bs.modal', function (e) {
        $('#newRow').remove();
    });
});*/

function createRoleInput(roleName) {
// add row
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

$(document).ready(function(){
// add row
    $("#addRow").click(function () {

        createRoleInput();

/*        var html = '';
        html += '<div id="inputFormRow">';
        html += '<div class="input-group mb-3">';
        html += '<input type="text" name="title[]" class="form-control m-input" placeholder="Enter title" autocomplete="off">';
        html += '<div class="input-group-append">';
        html += '<button id="removeRow" type="button" class="btn btn-danger">Remove</button>';
        html += '</div>';
        html += '</div>';

        $('#newRow').append(html);*/
    });

    // remove row
    $(document).on('click', '#removeRow', function () {
        $(this).closest('#inputFormRow').remove();
    })
});

