/**
 * @author david@tekeshe.com
 *
 */

function Viewer() {

    this.menu = function() {

        return  " <ul id='menu' class='figures' style='width: 223px;background: #3f3f3f;float: right;margin-top:5px;margin-right:8px;'> " +
            "   <li id='data-bundle-menu-item' class='menu-item'> " +
            "      <figure style='display: inline-block;padding: 6px 12px;text-align: center;'>" +
            "        <img src='img/b-icon.png' width=48px height=56px alt='B'>" +
            "        <figcaption>Bundles</figcaption>" +
            "      </figure>" +
            "   </li>" +
            "   <li id='airtime-transfer-menu-item' class='menu-item'>" +
            "      <figure style='display: inline-block;padding: 6px 6px;text-align: center;'>" +
            "         <img src='img/t-icon.png' width=48px height=56px alt='T'>" +
            "         <figcaption>Transfer</figcaption>" +
            "      </figure>" +
            "   </li>" +
            "   <li id='voucher-recharge-menu-item' class='menu-item'>" +
            "      <figure style='display: inline-block;padding: 6px 6px;text-align: center;'>" +
            "         <img src='img/r-icon.png' width=48px height=56px alt='R'>" +
            "         <figcaption>Recharge</figcaption>" +
            "      </figure>" +
            "   </li>" +
            "   <li id='exit-menu-item' class='menu-item'>" +
            "      <figure style='display: inline-block;padding: 6px 12px;text-align: center;'>" +
            "         <img src='img/exit.png' width=48px height=56px alt='E'>" +
            "         <figcaption>Exit</figcaption>" +
            "      </figure>" +
            "   </li>" +
            "   <li id='settings-menu-item' class='menu-item'>" +
            "       <figure style='display: inline-block;padding: 6px 6px;text-align: center;'>" +
            "          <img src='img/settings-medium.png' width=44px height=48px alt='S'>" +
            "          <figcaption>Settings</figcaption>" +
            "       </figure>" +
            "   </li>" +
            " </ul>')";
    }

    /*
     * Footer initializer
     */
    this.controlPanel = function( textBoxVisible, textBoxPlaceholder, leftButtonVisible, leftButtonText ) {

        $('#footer').empty();

        $('#footer').append(
                "<div id='data-bundle-widgets' class='command-button' style='width: 100%;height: 46px;'>"+
                "   <ul style='margin-left: 0px;width: 100%;height: 46px;'>" +
                "      <li style='width: 70px; border-bottom: 0px solid lightgray;padding-left: 5px;padding-right: 10px;margin: 0px; vertical-align: middle; display: " + (leftButtonVisible ? "inline-block" : "none") + "'> " +
                "          <button id='helper-button' style='margin-top: 6px; padding-top: 0px; height: 36px;'>" +
                "              <span class='standard-font-black' id='left-button-text' style='padding-top: 5px;'>" + leftButtonText + "</span>" +
                "          </button> " +
                "      </li> " +
                "      <li style='width: calc(100% - " + (leftButtonVisible ? "70px" : "0px") + " - 70px - 10px); height:36px; border-bottom: 0px solid lightgray; vertical-align: middle;  background-color: #ffffff;padding-right: 0px;margin: 0px;margin-top: 5px; padding-top: 0px; display : " + ( textBoxVisible ? "inline-block" : "none") + "'> " +
                "          <input id='input-message' type='text' placeholder='" + textBoxPlaceholder + "' style='width: calc(100% - 15px); margin-left: 10px; margin-top: 3px;' data-role='none'>" +
                "      </li>" +
                "      <li style='width: 70px;text-align: right;border-bottom: 0px solid lightgray;height: 36px;padding-left: 10px;padding-right: 5px; margin-top: 5px; vertical-align: middle; display: inline-block'>" +
                "          <button id='send-button' style='margin-top: 0px; padding-top: 0px; width: 100%; height: 36px;'>" +
                "              <span class='standard-font-black' id='send-button-text' style='padding-top: 5px;'>Send</span>" +
                "          </button> " +
                "      </li>" +
                "   </ul>" +
                "</div>" );
    }

    this.leftButtonText = function( leftButtonText ) {
        $('#left-button-text').text( leftButtonText );
    }

    this.getInputMessage = function() {
       return $('#input-message').val();
    }

    this.inputMessage = function( inputMessage ) {
       $('#input-message').val( inputMessage );
    }

    this.inputMessagePlaceholder = function ( placeholder) {
        $('#input-message').attr( 'placeholder', placeholder );
        this.inputMessage("");
    }

    this.inputMessageType = function( type ) {
        $('#input-message').attr( 'type', type );
    }

    this.inputMessageEnabled = function(enable) {
        $('#input-message').attr(enable ? 'enable' : 'disable');
    }

    this.loginWindow = function() {

        /* command panel. */
        var textBoxVisibility = true;
        var textBoxPlaceholder = "Enter mobile number here";
        var leftButtonVisibility = true;
        var leftButtonText = "Register";
        this.controlPanel( textBoxVisibility, textBoxPlaceholder, leftButtonVisibility, leftButtonText );
        controller.registerUserIdValidationEventsHandlers();
        this.message(true, true, "What is your mobile number");

//        controller.registerInputHandler();
    }

    this.dataBundleView = function() {

        this.inputMessagePlaceholder("Select bundle above");
        this.inputMessage("");
        this.inputMessageType("text");
        this.leftButtonText("Cancel");

        this.message( true, true, this.dataBundlePurchaseForm() );
    }

    this.airtimeTransferView = function() {

        this.inputMessagePlaceholder("Enter amount here");
        this.inputMessage("");
        this.inputMessageType("text");
        this.leftButtonText("Cancel");
        this.message(true, true, "How much airtime do you want to transfer?");
    }

    this.voucherRechargeView = function() {

        this.inputMessagePlaceholder("Enter recharge voucher ");
        this.inputMessage("");
        this.inputMessageType("text");
        this.leftButtonText("Cancel");
        this.message(true, true, "Please enter your recharge voucher.");
    }

    this.dataBundlePurchaseForm = function() {

        var dataBundleForm = "";
        $.each(dataBundles, function(index, bundle) {
            var row = view.getDataBundleRow ( bundle.itemId, bundle.itemDescription, bundle.duration + " days", bundle.sellingPrice );
            dataBundleForm += row + "<br/>";
        });

        return dataBundleForm;
    }

    this.getDataBundleRow = function (itemId, itemDescription, duration, sellingPrice) {

        return  "<ul style='width: calc(100% - 0px); margin-left: 0px; margin-right: 0px; padding-left: 0px;background-color: lightgoldenrodyellow;list-style-type: none;'> " +
            "     <li style='width: 30px;text-align: left;border-bottom: 1px solid lightgray;padding-right: 5px;margin: 0px;background-color: lightgoldenrodyellow;display: inline'>" +
            "         <input class='dataBundle' type='radio' name='itemId' value='" + itemId + "' > " +
            "     </li> " +
            "     <li style='width: 160px;border-bottom: 1px solid lightgray;padding: 0px;margin-left: -5px;padding-left: 35px; background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;'>" + itemDescription + " data bundle<span></li> " +
            "     <li style='width: 60px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;'>" + duration + "</span></li> " +
            "     <li style='width: 40px;text-align: right;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;padding-right: 5px;background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;'>" + currencyFormat(sellingPrice) +  "</span></li> " +
            "     <li style='min-width: calc(100% - 30px - 160px - 60px - 40px);border-bottom: 1px solid lightgray;padding-right: 5px;margin: 0px;margin-left: -5px; background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;'>&nbsp;</span></li>" +
            " </ul> ";

    }

    this.clearWindow = function() {

        $(".ui-wrapper div .iscroll-content").empty();
    }

    this.populateWindow = function( content ) {
        this.clearWindow();
        $(".ui-wrapper div .iscroll-content").append( content );
        $('[data-role="content"]').trigger('create');
        $(".ui-wrapper").iscrollview("refresh");
    }

    this.message = function( isInBound, clearWindow, message ) {

        if ( clearWindow ) {
            this.clearWindow();
        }

        $(".ui-wrapper div .iscroll-content").append(
                " <div class=\"callout border-callout standard-font-black\" " +
                "     style=\"" +
                ( isInBound ? " margin-left: 10px; " : " margin-right: 10px; ") +
                ( clearWindow ? " margin-top: 50px; " : "margin-top: 5px;")+
                " padding-right: 10px; display:inline-block; " +
                ( isInBound ? " float: left " : " float: right ") +
    //           ( isRecieved ? " left: 0 " : " right: 0 ") +
                "        \">" +
                message +
                "   <b class=\"border-notch " +
                ( isInBound  ?  " notch " :  " notch-right "  ) +
                "    \"></b> " +
                ( isInBound ? "   <b class=\"notch\"></b> " : "   <b class=\"notch-right\"></b> " ) +
                " </div> " +
                " <div class=\"clear\"></div>" );
        $('[data-role="content"]').trigger('create');
        $(".ui-wrapper").iscrollview("refresh");
    }

    this.accountListView = function( accountList ) {

        this.clearWindow();

        var accountListingContainer =
            $(" <div id='account-listing-portlet' class='portlet' style='height: 100%; background-image: url(../../eBridgeApp/img/telecel-background-medium.png); margin-top: 50px; display: block;'> " +
            "   </div>");

        accountListingContainer.empty();

        var accountListing = "";
        $.each(accountList, function(index, account) {

            var expiryDate =  account.expiryDate; //.toString("d MMM yy");

            if (account.balance <= 0.00) {
                expiryDate = "&nbsp;";
            }

            if (account.walletDescription == "Internet browsing wallet") {
                account.walletDescription = "Data Bundles";
            }

            var accountRow =
                " <ul style='padding-left: 0px;padding-right: 0px; width: 100%'> " +
                "     <li style='min-width: 100px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;padding-left: 6px;'>" + account.walletDescription  + "<span></li> " +
                "     <li style='min-width: 50px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;padding-right: 3px;'>" + expiryDate + "</scan></li> " +
                "     <li style='min-width: 70px;text-align: right;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;padding-right: 3px;'>" + formatCurrencyOrData(account)  +  "</scan></li> " +
                "     <li style='width: calc(100% - 100px - 50px - 70px - 10px);border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;background-color: lightgoldenrodyellow;'><span style='font-weight: normal; font-size: 12px;'>&nbsp;</scan></li>" +
                " </ul> ";
            accountListing += accountRow + "<br/>";
        });
//        $(".ui-wrapper div .iscroll-content").append( accountListingContainer );
        this.message(true, true, accountListing );
    }

    this.settingsMenuView = function(  ) {

        this.clearWindow();

        var settingsMenuContainer =
            $(" <div id='account-listing-portlet' class='portlet' style='height: 100%; background-image: url(../../eBridgeApp/img/telecel-background-medium.png); margin-top: 50px; display: block;'> " +
                "   </div>");

        settingsMenuContainer.empty();

        settingsMenuContainer.append (
            $( " <div id='change-password' " +
               "     style='border-bottom: 1px solid lightgray;background-color: lightgoldenrodyellow;border-bottom: 1px solid #C6C6C6;padding-bottom: 10px;margin-left: 8px;margin-right: 8px;'>" +
               "     <span class='standard-font-black' style='font-weight: normal; font-size: 12px;padding-left: 6px;padding-top: 6px;'>Change Password</span> " +
               " </div> " +
               " <div id='delete-account'" +
               "      style='border-bottom: 1px solid lightgray;background-color: lightgoldenrodyellow;border-bottom: 1px solid #C6C6C6;padding-bottom: 10px;padding-top: 4px;margin-left: 8px;margin-right: 8px;'> " +
               "      <span class='standard-font-black' style='font-weight: normal; font-size: 12px;padding-left: 6px;padding-top: 3px;'>Delete Account</span> " +
               " </div>"));

        view.leftButtonText("Cancel");
        $(".ui-wrapper div .iscroll-content").append( settingsMenuContainer );
    }

    this.displayProgressBar = function() {

        this.clearWindow();

        var displayContainer =
            $(" <div id='progress-spinner' class='portlet' style='height: 100%; margin-top: 50px; display: block;'> " +
            "   </div>");

        displayContainer.empty();

        displayContainer.append(
            $( "<div class='progress-spinner-container' style='margin-left: 20px; padding-top: 40px;'> " +
               "   <span class='progress-spinner'></span> " +
               "   <span id='progress-text-1' class='standard-font-black'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Working</span>" +
               "   <span id='progress-text-2' class='standard-font-black'>... Please Wait ...</span>" +
               "</div>") );

        $('#index').css('background-image','none');
        $('#index').css('background-color','white');
        $(".ui-wrapper div .iscroll-content").append( displayContainer );
    }

    this.inputTextArea = function() {

        return " <div class='block'> " +
               "    <textarea id='input-text-area' style='width: 100%;'></textarea> " +
//               "    <div class='code ui-corner-all'> " +
//               "       <pre class='html'>&lt;!-- HTML --&gt;&lt;textarea id='input-text-area'&gt;&lt;/textarea&gt;</pre> " +
//               "    </div> " +
               " </div>";
    }
}