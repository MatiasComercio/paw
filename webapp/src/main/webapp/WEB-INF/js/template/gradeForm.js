function loadGradeForm(nameAttr) {
    /* Grade Form Action Sequence */
    var gradeFormButton = $("[name='" + nameAttr + "']");

    gradeFormButton.on("click", function() {
        var id = $(this).data("id");
        var docket = $(this).data("docket");
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var modified = $(this).data("modified");
        var url = $(this).data("url");
        var gradeForm = $("#grade_form");
        gradeForm.find("input[name='id']").val(id);
        gradeForm.find("input[name='docket']").val(docket);
        gradeForm.find("input[name='courseId']").val(courseId);
        gradeForm.find("input[name='courseName']").val(courseName);
        gradeForm.find("input[name='modified']").val(modified);
        gradeForm.attr("action", url);

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
