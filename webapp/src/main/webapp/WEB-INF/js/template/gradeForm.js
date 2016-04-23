function loadGradeForm(nameAttr) {
    /* Grade Form Action Sequence */
    $("[name='" + nameAttr + "']").on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var gradeForm = $("#grade_form");
        gradeForm.find("input[name='courseId']").val(courseId);
        gradeForm.find("input[name='courseName']").val(courseName);
    });

    $("#gradeFormConfirmAction").on("click", function() {
        $('#gradeFormConfirmationModal').modal('hide');
        $("#grade_form").submit();
    });
    /* /Grade Form Action Sequence */
}
