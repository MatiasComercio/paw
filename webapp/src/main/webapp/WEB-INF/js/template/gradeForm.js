function loadGradeForm(nameAttr) {
    /* Grade Form Action Sequence */
    var gradeFormButton = $("[name='" + nameAttr + "']");

    gradeFormButton.on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var modified = $(this).data("modified");
        var gradeForm = $("#grade_form");
        gradeForm.find("input[name='courseId']").val(courseId);
        gradeForm.find("input[name='courseName']").val(courseName);
        gradeForm.find("input[name='modified']").val(modified);

        var grade = $(this).data("grade");
        if (grade != null){
            gradeForm.find("input[name='grade']").val(grade);
            gradeForm.find("input[name='oldGrade']").val(grade);
        }


    });

    $("#gradeFormConfirmAction").on("click", function() {
        $('#gradeFormConfirmationModal').modal('hide');
        $("#grade_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#gradeFormConfirmationModal').on('show.bs.modal', function(e){
        gradeFormButton.one('focus', function(e){$(this).blur();});
    });
    /* /Grade Form Action Sequence */
}
