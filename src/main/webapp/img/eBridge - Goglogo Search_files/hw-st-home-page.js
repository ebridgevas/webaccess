$(document).ready(function () {
    $("#overlay_bg").click(close_window);
});
var wd = $(window).width();
var ht = $(window).height();


function screen_opacity(boxArea) {
    $("#overlay_bg").css({
        "left": "0px",
        "top": "0px",
        "opacity": .8
    }).width(wd).height(ht).show();
}
function close_window() {
    $("#popup_window").animate({
        top: "25%",
        opacity: 0.10
    }, "normal", function () {
        $(this).hide().css({
            top: "15%"
        });
        $("#overlay_bg").hide();
    });
}
function setHomePage(browser) {
    screen_opacity();
    $("#popup_window").load("/set-home-page.asp?browser="+browser, function () {
        var n_wd = $("#popup_window").width();
        var n_ht = $("#popup_window").height();
        $(this).css({
            "left": ((wd / 3.8) - (n_wd /3)) + "px",
            "opacity": 0.10
        }).show().animate({
            top: "20%",
            opacity: 1.0
        }, "normal");
    });
}