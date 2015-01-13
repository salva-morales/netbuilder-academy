;
(function ($, window) {

    $("#skill-name").change(function() {
		$("#skill_to_sort").html($("#skill-name").val());
		$('#list-gc__results').empty();
        $.ajax({
            type: $('#gc-finder-form').attr('method'),
            url: "/bin/findGCs.json",
            data: {skills : $("#skill-name" ).val()},
            dataType: 'json',
            success: function (data) {
                if (data.length > 0) {
                    for (i=0; i < data.length; i++) {
						 $('#list-gc__results').append("<li>"+data[i].name+"</li>");
                	}
                } else {
                    $('#list-gc__results').append("<li>Sorry, no GCs found.</li>");
                }
            }
        }).fail(function () {
            $('#list-gc__results').append("<li>Sorry, unavailable to find GCs.</li>");
        });
    });
    
    
}(jQuery, window));
