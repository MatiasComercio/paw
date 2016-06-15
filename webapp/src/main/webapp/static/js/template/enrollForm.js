function loadEnrollForm(nameAttr) {
    /* Inscription Form Action Sequence */
    var enrollFormButton = $("[name='" + nameAttr + "']");

    enrollFormButton.on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var inscriptionForm = $("#inscription_form");
        inscriptionForm.find("input[name='courseId']").val(courseId);
        inscriptionForm.find("input[name='courseName']").val(courseName);
    });

    $("#enrollFormConfirmAction").on("click", function() {
        $('#enrollFormConfirmationModal').modal('hide');
        $("#inscription_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#enrollFormConfirmationModal').on('show.bs.modal', function(e){
        enrollFormButton.one('focus', function(e){$(this).blur();});
    });
    /* /Inscription Form Action Sequence */
}