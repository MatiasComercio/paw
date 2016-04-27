function loadDeleteStudentForm(nameAttr) {
    /* Delete Student Form Action Sequence */
    $("[name='" + nameAttr + "']").on("click", function() {
        var docket = $(this).data("student_docket");
        var firstName = $(this).data("student_first_name");
        var lastName = $(this).data("student_last_name");
        var deleteStudentForm = $("#delete_student_form");
        deleteStudentForm.find("input[name='docket']").val(docket);
        deleteStudentForm.find("input[name='firstName']").val(firstName);
        deleteStudentForm.find("input[name='lastName']").val(lastName);
        deleteStudentForm.attr("action", "/students/" + docket + "/delete");
    });

    $("#deleteStudentFormConfirmAction").on("click", function() {
        $('#deleteStudentFormConfirmationModal').modal('hide');
        $("#delete_student_form").submit();
    });
    /* /Delete Student Form Action Sequence */
}