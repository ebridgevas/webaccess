/**
 * Created by david on 8/22/14.
 */

var userSession;
//var HTTP_URL = "http://localhost:8080/televas-httpbridge-1.0/";
//var HTTP_URL = "http://196.2.77.14/televas-httpbridge-1.0";
var HTTP_URL;

$(document).ready(function(){

    HTTP_URL = "http://196.2.77.14/televas-httpbridge-1.0";

    $.ajaxSetup({ cache: false });

    userSession = new UserSession();
    userSession.setIsUserLoggedOn( false );

    // Assume admin
    $('#rss-feeds').css('display', 'none');
    $('#subscriber-register').css('display', 'none');
    $('#subscriber-first-name').focus();

    $('#email').val('');

    controller.init();
    viewer.init();

//    controller.registerUserIdValidationEventsHandlers();

//    ebridgePortletManager.init();

//    setTimeout( initTwitter(), 5000 );

});

