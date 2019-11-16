window.notify = function(message) {
    $.notify(message, {position: "bottom right"})
};

ajax = function (data, success) {
    $.ajax({
        type: "POST",
        dataType: "json",
        data: data,
        success: function(response) {
            success(response);
            if (response["redirect"] !== undefined) location.href = response["redirect"];
        }
    });
};