;
(function ($, window) {
    
    $("#add_skill").click(function(){
        $('#skill_list').append("<li><input type='text' class='skill-item' name='skills' placeholder='Java, AEM, CQ, JavaScript, HTML, CSS, C++'></li>");
    });
    
    $("#remove_skill").click(function(){
        $('#skill_list li:last').remove();
    });
    
    
}(jQuery, window));
