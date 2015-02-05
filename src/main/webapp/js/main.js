
var dataBundles;
var userSession;
var ebridgenav;

function main() {

    userSession = new UserSession();
    userSession.setIsUserLoggedOn( false );

    ebridgenav = $('#menu').ebridgenav({closeOnClick: true});
    showPagerFooter();

    var maxWidth = $(window).width();
    var messageComposer = $('<input id="message-composer" style="display: inline-block; width: calc(100% - 100px);">');
    var sendButton = $('<input onclick="login();" class="ebridge_send_button button-font" type="button" id="send-button" value="Send" style="display: inline-block;"> ');
//    $('#page-footer').append(messageComposer);
//    $('#page-footer').append(sendButton);

    var deviceWidth = 0;

    $(window).bind('resize', function () {

        var deviceWidth = $('[data-role="page"]').width();
        $('#message-composer').css('width',(deviceWidth - 100) + "px");

//        var deviceHeight = $('[data-role="page"]').height();
//        $('#content-pane').css('height', (deviceHeight) + "px");
//        alert("deviceHeight : " + deviceHeight);
//        alert("height : " + $('#content-pane').css('height'));
    }).trigger('resize');

    addMessage("What is your mobile number?", true);

    $('.ebridgenav_nav .scroll, #demo1 .scroll').on('click', function() {
        var h = $('#nav').outerHeight();
        if (!$('#main').is(":visible")) {
            h = $('#ebridgenav .mm_btn').outerHeight();
        }
        var link = $('.ebridgenav_nav');
        $.smoothScroll({
            offset: -h,
            scrollTarget: link.hash
        });
        return false;
    });
}