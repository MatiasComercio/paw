function loadCoursesJs() {
    $("[name='unenroll']").on("click", function () {
        console.log("¿Seguro que quiere darse de baja de la materia: " + $(this).attr("data-course_id") + "-" +
            $(this).attr("data-course_name") + "?");
    });
}

