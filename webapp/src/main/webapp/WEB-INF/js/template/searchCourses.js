function loadSearch() {
    /* Search */
    $("#resetSearch").on("click", function() {
        var courseFilterForm = $("#course_filter_form");
        courseFilterForm.find("input[name='id']").val(null);
        courseFilterForm.find("input[name='name']").val(null);
        courseFilterForm.submit();
    });
    /* /Search */
}
