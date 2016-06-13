function loadDeleteCourseForm(nameAttr) {
    /* Delete Course Form Action Sequence */
    var deleteCourseButton = $("[name='" + nameAttr + "']");

    deleteCourseButton.on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var formAction = $(this).data("form_action");
        var deleteCourseForm = $("#delete_course_form");
        deleteCourseForm.find("input[name='id']").val(courseId);
        deleteCourseForm.find("input[name='name']").val(courseName);
        deleteCourseForm.attr("action", formAction);
    });

    $("#deleteCourseFormConfirmAction").on("click", function() {
        $('#deleteCourseFormConfirmationModal').modal('hide');
        $("#delete_course_form").submit();
    });

    /* Remove focus on the modal trigger button */
    $('#deleteCourseFormConfirmationModal').on('show.bs.modal', function(e){
        deleteCourseButton.one('focus', function(e){$(this).blur();});
    });
    /* /Delete Course Form Action Sequence */
}