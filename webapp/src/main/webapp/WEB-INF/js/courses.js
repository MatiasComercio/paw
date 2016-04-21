function loadCoursesJs() {
/*    $("[name='unenroll']").on("click", function () {
        console.log("¿Seguro que quiere darse de baja de la materia: " + $(this).data("course_id") + "-" +
            $(this).data("course_name") + "?");
    });*/

    /* This was working, but added a modal to confirm action */
    $("[name='unenroll']").on("click", function() {
        // $("#courseInput").val($(this).attr("data-id")); +++xtodo: enable
    });

    $("#confirmAction").on("click", function() {
//         $("#inscription_form").submit(); +++xtodo: enable
        console.log("Confirmed");
        $('#confirmationModal').modal('hide');
    });
}

