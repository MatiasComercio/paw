function loadGradeForm(nameAttr) {
    /* Grade Form Action Sequence */
    $("[name='" + nameAttr + "']").on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var modified = $(this).data("course_name");
        var inscriptionForm = $("#inscription_form");
        inscriptionForm.find("input[name='courseId']").val(courseId);
        inscriptionForm.find("input[name='courseName']").val(courseName);
    });

    $("#confirmAction").on("click", function() {
        $('#gradeFormConfirmationModal').modal('hide');
        $("#inscription_form").submit();
    });
    /* /Grade Form Action Sequence */
}
