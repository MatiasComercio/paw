function loadEnableInscriptionsForm() {

    var inscriptionButton = $("[name='inscriptionButton']");

    $("#enableInscriptionsConfirmAction").on("click", function () {
        $('#enableInscriptionsConfirmationModal').modal('hide');
        $("#enableInscriptionsForm").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#enableInscriptionsConfirmationModal').on('show.bs.modal', function(e){
        inscriptionButton.one('focus', function(e){$(this).blur();});
    });
}