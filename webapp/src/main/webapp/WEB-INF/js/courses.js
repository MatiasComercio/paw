function loadCoursesJs() {
    $("[name='unenroll']").on("click", function() {
        $("#courseInput").val($(this).data("course_id"));
    });

    $("#confirmAction").on("click", function() {
        $('#confirmationModal').modal('hide');
        $("#inscription_form").submit();
    });
}

