function loadDeleteCourseForm(nameAttr) {
    /* Delete Course Form Action Sequence */
    $("[name='" + nameAttr + "']").on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var deleteCourseForm = $("#delete_course_form");
        deleteCourseForm.find("input[name='id']").val(courseId);
        deleteCourseForm.find("input[name='name']").val(courseName);
        deleteCourseForm.attr("action", "/courses/" + courseId + "/delete");
    });

    $("#deleteCourseFormConfirmAction").on("click", function() {
        $('#deleteCourseFormConfirmationModal').modal('hide');
        $("#delete_course_form").submit();
    });
    /* /Delete Course Form Action Sequence */
}