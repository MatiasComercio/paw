function loadGradeForm(nameAttr) {
    /* Grade Form Action Sequence */
    $("[name='" + nameAttr + "']").on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var modified = $(this).data("modified");
        var gradeForm = $("#grade_form");
        gradeForm.find("input[name='courseId']").val(courseId);
        gradeForm.find("input[name='courseName']").val(courseName);
        gradeForm.find("input[name='modified']").val(modified);
    });

    $("#gradeFormConfirmAction").on("click", function() {
        $('#gradeFormConfirmationModal').modal('hide');
        $("#grade_form").submit();
    });
    /* /Grade Form Action Sequence */
}
