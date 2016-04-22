function loadCoursesJs() {
    $("[name='unenroll']").on("click", function() {
        $("#courseInput").val($(this).data("course_id"));
        $(".modal-body span").text($(this).data("course_id") + "-" + $(this).data("course_name") + "?");
    });

    $("#confirmAction").on("click", function() {
        $('#confirmationModal').modal('hide');
        $("#inscription_form").submit();
    });
}

