function loadCancelButton(buttonId) {
    $("#"+ buttonId).on("click", function() {
        history.back();
    });
}

