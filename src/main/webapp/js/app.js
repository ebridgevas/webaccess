(function($) {
    "use strict";

//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", false);
//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", false);
//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", false);
//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", false);
//    addMessage("What is your mobile number?", false);
//    addMessage("What is your mobile number?", true);
//    addMessage("What is your mobile number?", true);

//    var tipPercent = 15.0;
//
//    var calcTip = function() {
//        var billAmt = Number( $('#billAmount').val() );
//        var tipAmt =  billAmt * tipPercent/100 ;
//        var totalAmt = billAmt + tipAmt;
//        $('#tipAmount').text('$' + tipAmt.toFixed(2));
//        $('#totalAmount').text('$' + totalAmt.toFixed(2));
//    };
//
//    var saveSettings = function() {
//        try {
//            var tipPct = parseFloat( $('#tipPercentage').val() );
//            localStorage.setItem('tipPercentage', tipPct);
//            tipPercent = tipPct;
//            window.history.back();
//        } catch (ex) {
//            alert('Tip percentage must be a decimal value');
//        }
//    };
//
//    $( document ).on( "ready", function(){
//        $('#calcTip').on('click', calcTip);
//        $('#saveSettings').on('click', saveSettings);
//        var tipPercentSetting = localStorage.getItem('tipPercentage');
//        if (tipPercentSetting) {
//            tipPercent = parseFloat(tipPercentSetting);
//        }
//        $('#tipPercentage').val(tipPercent);
//    });
//
//    $( document ).on( "deviceready", function(){
//        StatusBar.overlaysWebView( false );
//        StatusBar.backgroundColorByName("gray");
//    });

    $('body').bind('hideOpenMenus', function(){
        $("ul:jqmData(role='menu')").find('li > ul').hide();
    });
    var menuHandler = function(e) {
        $('body').trigger('hideOpenMenus');
        $(this).find('li > ul').show();
        e.stopPropagation();
    };
    $("ul:jqmData(role='menu') li > ul li").click(function(e) {
        $('body').trigger('hideOpenMenus');
        e.stopPropagation();
    });
    $('body').delegate("ul:jqmData(role='menu')",'click',menuHandler);
    $('body').click(function(e){
        $('body').trigger('hideOpenMenus');
    });
}
)(jQuery);

