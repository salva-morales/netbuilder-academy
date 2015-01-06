;
(function ($, window) {

    $("#skill-name" ).change(function() {
        console.log($("#skill-name" ).val());
        $.ajax({
            type: $('#gc-finder-form').attr('method'),
            url: "/bin/findGCs.json",
            data: {skill : $("#skill-name" ).val()},
            dataType: 'json',
            success: function (data) {
                if (data.length > 1) {
                    $('#list-gc__results').append("<li>data length > 1.</li>");
                } else {
                    $('#list-gc__results').append("<li>Sorry, no GCs found.</li>");
                }
            }
        }).fail(function () {
            $('#list-gc__results').append("<li>Sorry, unavailable to find GCs.</li>");
        });
    });
    
    
}(jQuery, window));
