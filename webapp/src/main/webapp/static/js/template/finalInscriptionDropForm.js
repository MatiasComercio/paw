function loadfinalInscriptionDropForm(nameAttr) {
    /* Final Inscription Form Action Sequence */
    var loadfinalInscriptionFormButton = $("[name='" + nameAttr + "']");

    loadfinalInscriptionFormButton.on("click", function() {
        var inscriptionId = $(this).data("inscription_id");
        var courseName = $(this).data("course_name");
        var finalExamDate = $(this).data("final_exam_date");
        var vacancy = $(this).data("vacancy");
        var courseCode = $(this).data("course_code");

        var inscriptionForm = $("#final_inscription_drop_form");
        inscriptionForm.find("input[name='id']").val(inscriptionId);
        inscriptionForm.find("input[name='courseName']").val(courseName);
        inscriptionForm.find("input[name='vacancy']").val(vacancy);
        inscriptionForm.find("input[name='finalExamDate']").val(finalExamDate);
        inscriptionForm.find("input[name='courseCode']").val(courseCode);
    });

    $("#finalInscriptionFormDropAction").on("click", function() {
        $('#finalInscriptionFormDropModal').modal('hide');
        $("#final_inscription_drop_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#finalInscriptionFormDropModal').on('show.bs.modal', function(e){
        loadfinalInscriptionFormButton.one('focus', function(e){$(this).blur();});
    });
    /* /Inscription Form Action Sequence */
}