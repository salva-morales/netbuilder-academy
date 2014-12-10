(function($) {
    $(document).ready(function(){

    	// Function to auto-click the main checkbox if any of its input has a value
        $('.custom-value-filter').change(function () {
  			if ($(this).val().length > 0) {
				$(this).closest('.container').find('.main-filter-checkbox').attr('checked','checked');
            } else {
                $(this).closest('.container').find('.main-filter-checkbox').removeAttr('checked');
            }
		});

        // Function to select all children checkboxes if the main one is selected
    	$('.select-all-checkbox').on('click tap', function () {
            if ($(this).is(':checked')) {
                var id = $(this).attr('id');
                var checkboxes = $('[name="'+id+'"]');
                $(checkboxes).attr('checked','checked');
                $(this).closest('.container').find('.main-filter-checkbox').attr('checked','checked');
            }
   		 });

    	// Function to select the main checkbox if a child checkbox is selected AND uncheck select-all if child is unchecked
        $('.checkbox-value-filter').on('click tap', function () {
            if ($(this).is(':checked')) {
				$(this).closest('.container').find('.main-filter-checkbox').attr('checked','checked');
            } else {
				$(this).closest('div').find('.select-all-checkbox').removeAttr('checked');
            }
        });


        // Function to make required ALL date inputs if its main one is selected
        $('.main-filter-checkbox').on('click tap', function () {
			var type = $(this).attr('data-src');
            if ($(this).is(':checked')) {
                var offset = $(this).offset();
				//$(window).scrollTop( offset.top );
				$('html,body').animate({
		          scrollTop: offset.top
		        }, 1000);
                $(this).closest('.filter').find('select').attr('required','required');
            } else {
                $(this).closest('.filter').find('select').removeAttr('required','required');
            }

        });

        // Function for the animation of the below function
         $('.trigger-toggle').on('click', function () {
                var offset = $(this).offset();
//				$(window).scrollTop( offset.top );
				$('html,body').animate({
			          scrollTop: offset.top
			        }, 1000);
        });

         // Function to open and close filter field
        $(".trigger-toggle").click(function(){
            if($(this).children('span').hasClass('ee-icon-plus')){
                $('.open').parents('div').children('.trigger-toggle').children('span').removeClass('ee-icon-minus').addClass('ee-icon-plus');
                $('.open').slideToggle('slow').toggleClass('open'); 
                $(this).parents('div').children('.toggle').toggleClass('open').slideToggle("slow");
                $(this).children('span').removeClass('ee-icon-plus').addClass('ee-icon-minus');

            }else{
				$('.open').slideToggle('slow').toggleClass('open'); 
                $(this).children('span').removeClass('ee-icon-minus').addClass('ee-icon-plus');
            }
        });
    });
}(jQuery));