/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/14/14
 * Time: 9:13 PM
 * To change this template use File | Settings | File Templates.
 */

$(document).ready(function(){

//    MBP.scaleFix();
//    MBP.hideUrlBar();
//
//    $( window ).on( "orientationchange", function( event ) {
//        alert( "This device is in " + event.orientation + " mode!" );
//    });
//
//    /* Disable touch events. */
//    $(document).bind("touchmove",function(event){
//        event.preventDefault();
//    });

});

function forcePortrait() {
    $(window).resize(function() {
        detectLandscape("#index");
    });
}

function detectLandscape( mainDiv ) {
    if (screen.width > screen.height) {
        $(mainDiv).addClass("landscape_mode");
    }
    else {
        $(mainDiv).removeClass("landscape_mode");
    }
}