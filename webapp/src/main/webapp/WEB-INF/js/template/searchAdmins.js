function loadAdminSearch() {
    /* Search */
    $("#resetSearch").on("click", function() {
        var adminFilterForm = $("#admin_filter_form");
        adminFilterForm.find("input[name='dni']").val(null);
        adminFilterForm.find("input[name='firstName']").val(null);
        adminFilterForm.find("input[name='lastName']").val(null);
        adminFilterForm.submit();
    });
    /* /Search */
}
