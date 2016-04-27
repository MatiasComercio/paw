function loadSearch() {
    /* Search */
    $("#resetSearch").on("click", function() {
        var studentFilterForm = $("#student_filter_form");
        studentFilterForm.find("input[name='docket']").val(null);
        studentFilterForm.find("input[name='firstName']").val(null);
        studentFilterForm.find("input[name='lastName']").val(null);
        studentFilterForm.submit();
    });
    /* /Search */
}
