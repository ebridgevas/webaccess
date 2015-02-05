$(function(){

	$('#service-command-pallet').slicknav({
        closeOnClick:true,
		label: '',
		duration: 1000,
		easingOpen: "easeOutBounce",
		prependTo:'#service-command-pallet-container'
	});

    $('.slicknav_nav .scroll, #demo1 .scroll').on('click', function() {
        var h = $('#nav').outerHeight();
        if (!$('#main').is(":visible")) {
            h = $('#slicknav .mm_btn').outerHeight();
        }
        var link = $('.slicknav_nav');
        $.smoothScroll({
            offset: -h,
            scrollTarget: link.hash
        });
        return false;
    });
});