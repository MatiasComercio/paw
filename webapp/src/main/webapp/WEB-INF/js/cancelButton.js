function loadCancelButton(buttonId) {
    $("#"+ buttonId).on("click", function(e) {
        e.preventDefault();
        history.back();
    });
}

