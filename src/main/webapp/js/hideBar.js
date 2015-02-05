// Hide URL Bar for iOS and Android "level above next level"
// streamlined version without extension part
// slowly moving it to perfection...

MBP.hideUrlBar2 = function () {
    alert("in hideUrlBar2");
	if ( !location.hash ) {

        alert("doScrollTop ");
		doScrollTop = setInterval(function(){

//			if( document.body && !( ( pageYOffset || document.body.scrollTop ) > 20 )  ){
//				clearInterval( doScrollTop );
//				scrollTo( 0,1 );
//				pageYOffset = 0;
//				scrollTo( 0, ( pageYOffset === document.body.scrollTop ) ? 1 : 0 );
//			}

            if ( (window.document.body.clientHeight - 100) < window.innerHeight ) {
                alert("clientHeight ");
                var newHeight = window.innerHeight + 100 + "px";
                doc.getElementsByTagName('body')[0].style.height = newHeight;

            }

		}, 200 );
	}
};	
	