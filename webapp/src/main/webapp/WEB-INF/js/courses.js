function loadCoursesJs() {
    $("[name='unenroll']").on("click", function() {
        var courseId = $(this).data("course_id");
        var courseName = $(this).data("course_name");
        var inscriptionForm = $("#inscription_form");
        inscriptionForm.find("input[name='courseId']").val(courseId);
        inscriptionForm.find("input[name='courseName']").val(courseName);
        $(".modal-body span").text(courseId + " - " + courseName + "?");
    });

    $("#confirmAction").on("click", function() {
        $('#confirmationModal').modal('hide');
        $("#inscription_form").submit();
    });
}

