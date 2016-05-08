function setConfirmAction() {

    $("#confirmActionButton").on("click", function () {
        $('#confirmActionModal').modal('hide');
        $("#confirmActionForm").submit();
    });
}