function loadfinalInscriptionForm(nameAttr) {
    /* Final Inscription Form Action Sequence */
    var loadfinalInscriptionFormButton = $("[name='" + nameAttr + "']");

    loadfinalInscriptionFormButton.on("click", function() {

        alert("asdsadsa");
        var inscriptionId = $(this).data("inscription_id");
        var courseName = $(this).data("course_name");
        var finalExamDate = $(this).data("finalExamDate");
        var vacancy = $(this).data("vacancy");

        var inscriptionForm = $("#inscription_form");
        inscriptionForm.find("input[name='id']").val(inscriptionId);
        inscriptionForm.find("input[name='courseName']").val(courseName);
        inscriptionForm.find("input[name='vacancy']").val(vacancy);
        inscriptionForm.find("input[name='finalExamDate']").val(finalExamDate);
    });

    $("#finalInscriptionFormConfirmAction").on("click", function() {
        $('#finalInscriptionFormConfirmationModal').modal('hide');
        $("#final_inscription_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#finalInscriptionFormConfirmationModal').on('show.bs.modal', function(e){
        loadfinalInscription.one('focus', function(e){$(this).blur();});
    });
    /* /Inscription Form Action Sequence */
}
