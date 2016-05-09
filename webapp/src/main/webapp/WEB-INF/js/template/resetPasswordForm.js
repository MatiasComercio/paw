function loadResetPasswordForm(nameAttr) { /*resetPasswordButton*/
    var resetPasswordButton = $("[name='" + nameAttr + "']");

    /* Reset Password Form Action Sequence */
    resetPasswordButton.on("click", function() {
        var dni = $(this).data("user_dni");
        var firstName = $(this).data("user_first_name");
        var lastName = $(this).data("user_last_name");
        var resetPasswordForm = $("#reset_password_form");
        resetPasswordForm.find("input[name='dni']").val(dni);
        resetPasswordForm.find("input[name='firstName']").val(firstName);
        resetPasswordForm.find("input[name='lastName']").val(lastName);
    });

    $("#resetPasswordFormConfirmAction").on("click", function() {
        $('#resetPasswordFormConfirmationModal').modal('hide');
        $("#reset_password_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#resetPasswordFormConfirmationModal').on('show.bs.modal', function(e){
        resetPasswordButton.one('focus', function(e){$(this).blur();});
    });
    /* /Reset Password Form Action Sequence */
}