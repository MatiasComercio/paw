function loadFinalGradeForm(nameAttr) {
    /* Grade Form Action Sequence */
    var gradeFormButton = $("[name='" + nameAttr + "']");

    gradeFormButton.on("click", function () {
        var docket = $(this).data("docket");
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var courseCodId = $(this).data("course_code");
        var url = $(this).data("url");
        var gradeForm = $("#final_grade_form");
        gradeForm.find("input[name='docket']").val(docket);
        gradeForm.find("input[name='courseId']").val(courseId);
        gradeForm.find("input[name='courseName']").val(courseName);
        gradeForm.find("input[name='courseCodId']").val(courseCodId);
        gradeForm.attr("action", url);


    });

    $("#finalGradeFormConfirmAction").on("click", function () {
        $('#finalGradeFormConfirmationModal').modal('hide');
        $("#final_grade_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#finalGradeFormConfirmationModal').on('show.bs.modal', function (e) {
        gradeFormButton.one('focus', function (e) {
            $(this).blur();
        });
    });
    /* /Grade Form Action Sequence */
}