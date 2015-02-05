(function($) {
	$.maximize = function() {
		var $body = $('body');
		
		// prevent scrollstart and scrollstop events from fireing in jquery mobile
		if ($.event.special.scrollstart) {
			$.event.special.scrollstart.enabled = false;
		}
		
		// expand the body so there's enough room for the address bar to scroll out of view
		$body.height(2 * window.innerHeight);
		
		setTimeout(function() {
			// scroll the window to hide the address bar
			window.scrollTo(0, 0);
			
			// shrink the body back down to window height
			$body.height(window.innerHeight);
		},20);
		
		// resume scrollstart and scrollstop events
		if ($.event.special.scrollstart) {
			setTimeout(function() {
				$.event.special.scrollstart.enabled = true;
			}, 150 );
		}
	};
})(jQuery);