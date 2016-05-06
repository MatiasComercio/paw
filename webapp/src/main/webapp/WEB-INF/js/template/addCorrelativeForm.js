function loadCorrelativeForm(nameAttr) {
    /* Inscription Form Action Sequence */
    $("[name='" + nameAttr + "']").on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var correlativeId = $(this).data("correlative_id");
        var correlativeName = $(this).data("correlative_name");
        var correlativeForm = $("#correlative_form");
        correlativeForm.find("input[name='courseId']").val(courseId);
        correlativeForm.find("input[name='courseName']").val(courseName);
        correlativeForm.find("input[name='correlativeId']").val(correlativeId);
        correlativeForm.find("input[name='correlativeName']").val(correlativeName);

    });

    $("#correlativeFormConfirmAction").on("click", function() {
        $('#correlatveFormConfirmationModal').modal('hide');
        $("#correlative_form").submit();
    });
    /* /Inscription Form Action Sequence */
}