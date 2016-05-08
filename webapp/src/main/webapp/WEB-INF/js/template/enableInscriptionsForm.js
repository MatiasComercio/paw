function loadEnableInscriptionsForm() {

    $("#enableInscriptionsConfirmAction").on("click", function () {
        $('#enableInscriptionsConfirmationModal').modal('hide');
        $("#enableInscriptionsForm").submit();
    });
}