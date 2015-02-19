/**
* @author david@tekeshe.com
*
*/

var viewer = {

    init : function() {
        viewer.hide();
        $('#contentCol').css('background-color', 'white');
        viewer.loginPortletVisible( true );

    },

    hide : function() {
        $('.widget').each(function(index, item){
            $(this).css('display', 'none');
        });
    },

    headerVisible : function ( visible ) {
        $('#pagelet_bluebar').css('display', (visible ? 'block' : 'none'));
    },

    rightColumnVisible : function ( visible ) {
        $('#right-column').css('display', (visible ? 'block' : 'none'));
    },

    loginPortletVisible : function ( visible ) {

        $('#auth-password-pane').css('display', 'none');
        $('#left-column').css('width', '100%');
        $('#login-portlet').css('display', ( visible ? 'block' : 'none'));
        $('#globalContainer').css('padding-left', '0px');
        $('._4_7u').css('margin-left', '0px');
        $('#columns').css('margin-left','0px');
        if ( visible ) {
            controller.userIdEventsHandlers();
            viewer.placeholder($('#auth-email'), 'Mobile Number');
            $('#auth-email').focus();
        }
    },

    registrationPortletVisible : function( visible ) {

        viewer.hide();
        $('#contentCol').css('background-color', 'white');
        $('#auth-password-pane').css('display', 'none');
        $('#left-column').css('width', '100%');
        $('#registration-portlet-container').css('display', ( visible ? 'block' : 'none'));
        viewer.registrationPortlet();
        $('#globalContainer').css('padding-left', '0px');
        $('._4_7u').css('margin-left', '0px');
        $('#columns').css('margin-left','0px');
    },

    showOrdinaryUserViews : function() {

        userSession.userRole = 'USER';

        console.log("showOrdinaryUserViews ...");

        viewer.hide();
        viewer.headerVisible( true );
        viewer.rightColumnVisible( true );

//        $('._4_7u').css('margin-left', '305px');

        $('#left-column').css('width', '502px');
        $('#right-column').css('width', '502px');
        $('#columns').css('margin-left','calc((100% - 1004px)/2)');

        viewer.showWelcomeMessage();
//        viewer.progressBarVisible(true, "Test message");
        controller.dropDownMenuEventHandlers();
//        viewer.hideRegistrationPortlet();
        viewer.accountListPortletVisible( true );
        viewer.airtimeTransferPortlet();
        viewer.voucherRechargePortlet( true );
        //viewer.whatsappDataBundlePortletVisible(true);
        //viewer.facebookDataBundlePortletVisible(true);

        controller.dataBundleOfferListing(0, 30);

        //viewer.dataBundlePortletVisible( true);

        viewer.transactionListPortletVisible( true, userSession.getUserId() );
        //viewer.minimizeDataBundlePortlet();
        viewer.minimizeAirtimeTransferPortlet();
        viewer.minimizeVoucherRechargePortlet();

        viewer.progressBarVisible(false, "");
    },

    progressBarVisible : function ( visible, message ) {

        console.log("progressBarVisible : " + visible + " - " + message );
        $('#blueBarNAXAnchor').css('pointer-events', (visible ? 'none' : 'auto'));
        $('#contentCol').css('pointer-events', (visible ? 'none' : 'auto'));
        $('#footer').css('pointer-events', (visible ? 'none' : 'auto'));
        $('#contentCol').css('opacity', (visible ? '0.3' : '1.0'));
        $('#progress-message').text( visible ? message + " ..." : "");
        $('#progress-portlet').css('display', (visible ? 'block' : 'none'));
    },

    showCustomerCareAgentViews : function () {

        userSession.userRole = 'AGENT';

        viewer.hide();
        viewer.headerVisible( true );
        viewer.rightColumnVisible( true );
        $('#columns').css('margin-left','calc((100% - 1004px)/2)');
        $('#left-column').css('width', '502px');
        $('#right-column').css('width', '502px');

        viewer.showWelcomeMessage();
        controller.dropDownMenuEventHandlers();

        viewer.registrationPortlet();
        viewer.accountListPortletVisible( false );
        viewer.subscriberListPortletVisible( true );
        //viewer.whatsappDataBundlePortletVisible(true);
        //viewer.facebookDataBundlePortletVisible(true);
        //viewer.dataBundlePortletVisible( true);

        controller.dataBundleOfferListing(0, 30);

        viewer.airtimeTransferPortlet();
        viewer.voucherRechargePortlet( true );
        viewer.transactionListPortletVisible( true, userSession.getUserId() );
        viewer.minimizeAccountListingPortlet();
        //viewer.minimizeDataBundlePortlet();
        viewer.minimizeAirtimeTransferPortlet();
        viewer.minimizeVoucherRechargePortlet();

        viewer.progressBarVisible(false, "");
    },

    hideUserIdEntry : function() {
        $('#user-id-input-pane').css('display', 'none');
        $('#register-command-pane').css('display', 'none');
    },

    showUserIdEntry : function() {

        $('#user-id-input-pane').css('display', 'inline-block');
        $('#register-command-pane').css('display', 'inline-block');
        $('#settings-button-container').css('display', 'none');
        $('#email').css('background-color','white');
        $('#email').css('color','grey');
        $('#email').attr('placeholder','Mobile number');
        $('#email').val('');
        $('#email').prop('disabled', false);
        $('#register-command').text('Not registered?');
        $('#email').focus();
//        controller.registerUserIdValidationEventsHandlers();
        controller.registerUserIdEventsHandlers();

    },

    disableUserIdEntry : function() {
        $('#register-command').text('Change mobile number?');
        $('#register-command').css('margin-left','0px');
        $('#email').prop('disabled', true);
        $('#email').css('background-color','firebrick');
        $('#email').css('color','white');
        $('#email').css('border','none');
        $('#user-id-input-pane').css('background-color','firebrick');
    },

    hidePasswordEntry : function() {
        $('#password-input-pane').css('display', 'none');
        $('#forgot-password-pane').css('display', 'none');
    },

    showPasswordEntry : function( securityTokenPrompt ) {
//        $('#password-input-pane').css('display', 'inline-block');
//        $('#pass').attr('placeholder', securityTokenPrompt );
//        $('#pass').val('');
//        $('#forgot-password-pane').css('display', 'inline-block');
        $('#auth-password-pane').css('display', 'block');
        $('#auth-password').attr('placeholder', securityTokenPrompt );
        $('#auth-password').val('');
    },

    showWelcomeMessage : function() {

        console.log("Showing welcome message ...");
        viewer.hideUserIdEntry();
        viewer.hidePasswordEntry();
        $('#login_form').css('display', 'block');
        $('#login_form').css('width', '100%');
        $('#user-name').text(userSession.getFirstName() +  " " + userSession.getLastName() + ", " + toShortMobileNumberFormat(userSession.getUserId()));
        $('#user-name').css('color', 'white');
        $('#user-name-pane').css('display', 'inline-block');
        $('#loginButton').attr('value', 'Log Out');
        $('#settings-button-container').css('display', 'inline-block');
        $('#login_form').css('padding-top', '0px');

        controller.registerLogoutEvent();
        controller.registerSettingsEvent();
    },

    hideWelcomeMessage : function() {
        $('#user-name-pane').css('display', 'none');
        $('#settings-button-container').css('display', 'none');
    },

    statusPortlet : function ( ) {

        var portlet = $('#status-portlet');

        portlet.css('display', 'block');

        portlet.empty();
//        portlet.append($('<div class="widget-head">')
//            .append($('<h3 class="portlet-header">FATAL ERROR</h3>')));

        var content = $('<div id="registration-pane" class="boxed-container">');

        content.append('<div class="status-bar" style="display: none;"><img id="status-bar-icon" src="../img/nack.jpeg" class="nack"><span id="system-error">&nbsp;</span></div>');

        portlet.append( content );
    },

    hideStatus : function() {
        $('#status-portlet').css('display', 'none');
    },

    registrationMessage : function() {

        return $("<div class='boxed-container' style='padding: 14px;'> <b>Dear Subscriber,</b><br/><br/>" +
            " To use this web portal, please register your number. </br></br>" +
            " To Register, please collect a form, fill in your details </br>" +
            " and submit the form to your nearest Telecel branch </br>" +
            " together with a copy of your ID. </div>");
    },


    registrationHowToPortlet : function() {
        var portlet =  $('#registration-howto-portlet');
        portlet.empty();

        portlet.css('display', 'block');

        portlet.append($('<div class="widget-head">')
            .append($('<div id="airtime-transfer-portlet-resize-icon"></div>'))
            .append($('<h3 class="portlet-header">How to register</h3>')));

        var contentPane = $('<div id="registration-howto-pane"></div>');

        contentPane.append(viewer.registrationMessage());

        portlet.append( contentPane );
        var buttons = $('<ul class="standard-ul"></ul>')
            .append($('<li class="standard-li"><button id="register-howto-cancel-button" class="button" type="button">OK</button></li>'));
//            .append($('<li class="standard-li"><button id="transfer-process-button" class="button" type="button">OK</button></li>'));


        portlet.append( $('<div class="button-pane clear-fix ">').append( buttons) );
    },

    hideRegistrationHowToPortlet : function() {
        $('#registration-howto-portlet').css('display', 'none');
    },

    subscriberListPortletVisible : function( visible ) {
        $('#subscriber-list-portlet').css('display', ( visible ? 'block' : 'none'));
        if ( visible == true ) {
            controller.subscriberListing(0, 1000);
        }
    },

    accountListPortletVisible : function( visible ) {
        $('#account-listing-portlet').css('display','block');
//        if ( visible == true ) {
            controller.accountListing(0, 20);
//        }
    },

    whatsappDataBundlePortletVisible : function( visible ) {

        if ( ! userSession.isUserLoggedOn() ) {
            viewer.loginPortletVisible();
        }

        $('#whatsapp-data-bundle-purchase-portlet').css('display', ( visible ? 'block' : 'none'));
//        if ( visible == true ) {
        controller.dataBundleOfferListing(0, 20);
//        }
    },

    facebookDataBundlePortletVisible : function( visible ) {

        if ( ! userSession.isUserLoggedOn() ) {
            viewer.loginPortletVisible();
        }

        $('#facebook-data-bundle-purchase-portlet').css('display', ( visible ? 'block' : 'none'));
//        if ( visible == true ) {
        controller.dataBundleOfferListing(0, 20);
//        }
    },

    dataBundlePortletVisible : function( visible ) {

        if ( ! userSession.isUserLoggedOn() ) {
            viewer.loginPortletVisible();
        }

        $('#data-bundle-purchase-portlet').css('display', ( visible ? 'block' : 'none'));
//        if ( visible == true ) {
            controller.dataBundleOfferListing(0, 30);
//        }
    },

    transactionListPortletVisible : function( visible, mobileNumber ) {

        $('#transaction-list-portlet').css('display', ( visible ? 'block' : 'none'));
        if ( visible == true ) {
            controller.transactionListing(0, 1000, mobileNumber);
        }
    },

    registrationPortlet : function ( ) {

        var portlet = $('#agent-registration-portlet');
        viewer.agentRegistrationPortletVisibility( true );
        portlet.empty();

        if (userSession.userRole == "AGENT") {
            $('#agent-registration-portlet').css('width', '100%');
            $('#agent-registration-portlet').css('margin-left','0px');
        } else {
            $('#agent-registration-portlet').css('width', '450px');
            $('#agent-registration-portlet').css('margin-left','calc((100% - 450px)/2)');
            $('#agent-registration-portlet').css('margin-bottom','20px');
        }

        portlet.append($('<div class="widget-head">')
            .append($('<div id="registration-portlet-resize-icon" class="minimize-icon"></div>'))
            .append($('<h3 class="portlet-header"><span id="subscriber-registration-header">Subscriber Registration</span></h3>')));

        var content = $('<div id="registration-pane" class="boxed-container">');

        content.append('<div class="register-status" style="display: none;height: 38px;"><img id="register-status-icon" src="../img/nack.jpeg" class="nack">&nbsp;<span id="register-status"></span></div>');

        content.append('<div class="label"><label>Mobile number</label></div>');
        content.append('<div class="input-container" style="padding-top: 8px;"><input class="input" id="mobile-number" type="text"></div>');
        content.append('<div class="label"><label>First name</label></div>');
        content.append('<div class="input-container"><input class="input" id="first-name" type="text"></div>');
        content.append('<div class="label"><label>Last name</label></div>');
        content.append('<div class="input-container"><input class="input" id="last-name" type="text"></div>');
        content.append('<div class="label"><label>Email Address</label></div>');
        content.append('<div class="input-container"><input class="input" id="email-address" type="text"></div>');
        content.append('<div class="label"><label>Security Question</label></div>');
        content.append('<div class="input-container"><input class="input" id="security-question" type="text"></div>');
        content.append('<div class="label"><label>Security Answer</label></div>');
        content.append('<div class="input-container"><input class="input" id="security-answer" type="text"></div>');
        content.append('<div class="label"><label>SMS Capable</label></div>');
        content.append('<div class="input-container"><input id="sms-capable" type="checkbox" checked></div>');

        if ( userSession.userRole == "AGENT") {
            content.append('<div class="label"><label>Subscriber Role</label></div>');
            content.append('<div class="input-container"><select id="role"><option>User</option><option>Agent</option><option>Administrator</option></select></div>');
        }
        var buttons = $('<ul class="standard-ul"></ul>')
            .append($('<li class="standard-li"><button id="search-subscriber-button" class="button" type="button">Search</button></li>'))
            .append($('<li class="standard-li"><button id="delete-register-button" class="button" type="button">Delete</button></li>'))
            .append($('<li class="standard-li"><button id="send-activation-code-button" class="button" type="button">Send Code</button></li>'))
            .append($('<li class="standard-li"><button id="register-cancel-button" class="button" type="button">Cancel</button></li>'))
            .append($('<li class="standard-li"><button id="register-process-button" class="button" type="button">Register</button></li>'));

        content.append( $('<div class="button-pane clear-fix " style="border-top: 1px solid #d3d6db;">').append( buttons) );

        portlet.append(content);

        $('#registration-pane').css('padding-top','4px');
        $('#delete-register-button').css('display', 'none');
        $('#send-activation-code-button').css('display', 'none');
        $('#register-cancel-button').css('display', 'none');
        $('.status').css('display','none');

        $('#sms-capable').prop('checked', true);

        controller.registerEventHandlers();
    },

    displaySubscriber : function( subscriber ) {

        $('#mobile-number').val( toShortMobileNumberFormat( subscriber.mobileNumber ) );
        $('#first-name').val( subscriber.firstName );
        $('#last-name').val( subscriber.surname);
        $('#email-address').val( subscriber.emailAddress);
        $('#security-question').val( subscriber.securityQuestion );
        $('#security-answer').val( subscriber.securityAnswer );
        $('#sms-capable').prop('checked', true);

        $('#subscriber-registration-header').text("SUBSCRIBER - " + toShortMobileNumberFormat(subscriber.mobileNumber));
        $('#mobile-number').prop('disabled', true);
        $('#mobile-number').css('background', 'lightgoldenrodyellow');
        if ( subscriber.status.toLowerCase() != "active") {

            $('.register-status').css('display', 'block');

            var narrative = "This account is not activated yet.";
            if (subscriber.status.toLowerCase() == "locked" ) {
                narrative = "To unlock account, press 'Send Code'";
            }

            viewer.displayError(
                $('.register-status'),
                $('#register-status'),
                $('#register-status-icon'),
                narrative
                );
        } else {
            $('#register-status').text('');
            $('.register-status').css('display', 'none');
        }

        $('#register-process-button').text('Close');
        $('#register-process-button').unbind();
        $('#register-process-button').click(function(){
           viewer.registrationPortlet();
           viewer.deselectSubscriber();
        });

        $('#delete-register-button').css('display', 'inline-block');
        $('#delete-register-button').text('Delete');
        $('#delete-register-button').unbind();
        $('#delete-register-button').click(function(){
            controller.deleteSubscriber( toStandardMobileNumberFormat($('#mobile-number').val()));
        });

        $('#send-activation-code-button').css('display', 'inline-block');
        $('#send-activation-code-button').text('Send Code');
        $('#send-activation-code-button').unbind();
        $('#send-activation-code-button').click(function(){
            controller.resendActivationCode( toStandardMobileNumberFormat($('#mobile-number').val()) );
        });

        controller.editEventListener();
    },

    deselectSubscriber : function() {
        $('#subscriber-list').dataTable().$('tr.selected').removeClass('selected');
    },

    settingsPortlet : function ( portlet ) {

        $('#settings-portlet').css('display', 'block');

        portlet.empty();
        portlet.append($('<div class="widget-head">')
            .append($('<div id="registration-portlet-resize-icon"></div>'))
            .append($('<h3 class="portlet-header">Settings</h3>')));

        var content = $('<div id="registration-pane" class="boxed-container">');

        content.append('<div class="settings-status" style="display: none;"><img id="settings-status-icon" src="../img/nack.jpeg" class="nack"><span id="settings-status">&nbsp;</span></div>');

        content.append('<div class="label"><label>Mobile number</label></div>');
        content.append('<div class="input-container"><input class="input" id="mobile-number" type="text"></div>');
        content.append('<div class="label"><label>First name</label></div>');
        content.append('<div class="input-container"><input class="input" id="first-name" type="text"></div>');
        content.append('<div class="label"><label>Last name</label></div>');
        content.append('<div class="input-container"><input class="input" id="last-name" type="text"></div>');
        content.append('<div class="label"><label>Email Address</label></div>');
        content.append('<div class="input-container"><input class="input" id="email-address" type="text"></div>');
        content.append('<div class="label"><label>Security Question</label></div>');
        content.append('<div class="input-container"><input class="input" id="security-question" type="text"></div>');
        content.append('<div class="label"><label>Security Answer</label></div>');
        content.append('<div class="input-container"><input class="input" id="security-answer" type="text"></div>');
        content.append('<div class="label"><label>SMS Capable</label></div>');
        content.append('<div class="input-container"><input id="sms-capable" type="checkbox" checked></div>');
        content.append('<div class="label"><label>Subscriber Role</label></div>');
        content.append('<div class="input-container role"><select id="role"><option value="user">User</option><option value="agent">Agent</option><option value="administrator">Administrator</option></select></div>');
        content.append('<div class="label"><label>Change Password To</label></div>');
        content.append('<div class="input-container"><input class="input" id="new-password" type="password"></div>');

//        content.append('<div class="new-password"><input id="new-password" type="text" placeholder="new password (optional)"></div>');
//        content.append('<div class="repeat-new-password"><input id="repeat-new-password" type="text" placeholder="repeat new password"></div>');

        portlet.append(content);

        var buttons = $('<ul class="standard-ul"></ul>')
            .append($('<li class="standard-li"><button id="settings-cancel-button" class="button" type="button">Cancel</button></li>'))
            .append($('<li class="standard-li"><button id="settings-delete-button" class="button" type="button">Delete</button></li>'))
            .append($('<li class="standard-li"><button id="settings-save-button" class="button" type="button">Save</button></li>'));

        portlet.append( $('<div class="button-pane clear-fix ">').append( buttons) );

        $('#mobile-number').val(toStandardMobileNumberFormat(userSession.getUserId()));
        $('#mobile-number').prop('disabled', true);
        $('#mobile-number').css('background-color','lightgrey');
        $('#first-name').val( userSession.getFirstName());
        $('#last-name').val( userSession.getLastName());
        $('#security-question').val( userSession.getSecurityQuestion() + "?");
        $('#security-answer').val( userSession.getSecurityAnswer());
        $('#email-address').val(userSession.getUserEmail());
        $('#sms-capable').prop('checked', userSession.getNotificationAgent() == 'SMS');
//        var selector = "'.role option[value=" + userSession.getUserRole().toLowerCase() + "]'";
//        alert("selector : " + selector );
        $("div.role select").val( userSession.getUserRole().toLowerCase());

        controller.settingsEventHandlers();
    },

    hideAccountListingPortlet : function() {
        $('#account-listing-portlet').css('display', 'none');
    },

    showAccountListingPortlet : function() {
        $('#account-listing-portlet').css('display', 'block');
    },

    hideSettingsPortlet : function() {
        $('#settings-portlet').css('display', 'none');
    },

    showRegistrationPortlet : function() {
        $('#registration-portlet').css('display', 'block');
    },

    agentRegistrationPortletVisibility : function(visible) {
        $('#agent-registration-portlet').css('display', (visible ? 'block' : 'none'));
    },

    hideRegistrationPortlet : function() {
        $('#registration-portlet').css('display', 'none');
    },

    minimizeRegistrationPortlet : function() {
        $('#registration-portlet-resize-icon').removeClass('maximize-icon');
        $('#registration-portlet-resize-icon').removeClass('minimize-icon');
        $('#registration-portlet-resize-icon').addClass('minimize-icon');
        $('#registration-pane').css('display','none');
    },

    maximizeRegistrationPortlet : function() {
        $('#registration-portlet-resize-icon').removeClass('minimize-icon');
        $('#registration-portlet-resize-icon').removeClass('minimize-icon');
        $('#registration-portlet-resize-icon').addClass('maximize-icon');
        $('#registration-pane').css('display','block');
    },

    registrationEventHandlers : function() {

        $('#registration-portlet-resize-icon').click(function(){

            if ( $('#registration-pane').css('display') == 'none' ) {
                viewer.maximizeRegistrationPortlet();
            } else {
                viewer.minimizeRegistrationPortlet();
            }
        });
    },

    showAll : function() {
        $('#registration-portlet').css('display', 'block');
        $('#data-bundle-purchase-portlet').css('display', 'block');
        $('#airtime-transfer-portlet').css('display', 'block');
        $('#voucher-recharge-portlet').css('display', 'block');
    },

    hideAll : function() {
        $('#registration-portlet').css('display', 'none');
        $('#data-bundle-purchase-portlet').css('display', 'none');
        $('#airtime-transfer-portlet').css('display', 'none');
        $('#voucher-recharge-portlet').css('display', 'none');
    },

    accountListingPortlet : function ( ) {

        var portlet  = $('#account-listing-portlet');
        viewer.showAccountListingPortlet();

        portlet.empty();

        portlet.append($('<div class="widget-head">')
            .append($('<div id="account-listing-portlet-resize-icon"></div>'))
            .append($('<h3 class="portlet-header">MY ACCOUNTS</h3>')));

        var account = function(bundleId, productDesc, windowPeriod, itemPrice) {

            var item = $('<li class="active-li data-bundle-item dataBundle standard-tr">');

            item.append(  $('<div class="bundle-size">')
                .append( $('<span id="product-desc">')
                    .text(productDesc) ) );

            item.append($('<div class="bundle-window-period">')
                .append($('<span id="window-period">')
                    .text("expires " + windowPeriod)));

            item.append( $('<div id="bundle-price" class="bundle-price"></div>')
                .text(itemPrice));

            return item;
        };

        var content = $('<ul id="account-listing" class="widget-content decorated-ul">');

        content.append('<div class="data-bundle-purchase-status" style="display: none;"><span id="data-bundle-purchase-status"></span></div>');

        content.append( account( "1", "Main Account", "25 October 2014", "$13.45") );
        content.append( account( "2", "Data Account", "30 October 2014", "144 MB" ) );
        content.append( account( "3", "Bonus Account", "30 October 2014", "30 MB" ) );

        content.append('<div class="data-bundle-confirmation-password" style="display: none;"><input id="data-bundle-confirmation-password" type="password" placeholder="enter your password to confirm"></div>');

        var buttons = $('<ul class="standard-ul"></ul>')
            .append($('<li class="standard-li"><button id="account-listing-refresh-button" class="button" type="button">Refresh</button></li>'));

        content.append( $('<div class="button-pane clear-fix" style="border-left: 0px;border-right: 0px;">').append( buttons) );

        portlet.append(content);
        portlet.append($('<div class="bottomBorder"></div>'));

        userSession.setProduct( undefined );
        controller.accountListingEventHandlers();
    },

    minimizeAccountListingPortlet : function() {
        $('#account-listing-portlet-resize-icon').removeClass('maximize-icon');
        $('#account-listing-portlet-resize-icon').removeClass('minimize-icon');
        $('#account-listing-portlet-resize-icon').addClass('minimize-icon');
        $('#account-listing-portlet-resize-icon').css('display', 'block');
        $('#account-listing').css('display','none');
    },

    maximizeAccountListingPortlet : function() {
        $('#account-listing-portlet-resize-icon').removeClass('minimize-icon');
        $('#account-listing-portlet-resize-icon').removeClass('maximize-icon');
        $('#account-listing-portlet-resize-icon').addClass('maximize-icon');
        $('#account-listing-portlet-resize-icon').css('display', 'block');
        $('#account-listing').css('display','block');
    },

    transactionHistoryVisible : function( visible, mobileNumber ) {
        $('#transaction-list-portlet').css('display', ( visible ? 'block' : 'none'));
        if ( visible == true ) {
            controller.transactionListing("0", "20", mobileNumber);
        }
    },

    dataBundlePortlet : function ( portlet ) {

        portlet.empty();

        portlet.append($('<div class="widget-head">')
                        .append($('<div id="data-bundle-portlet-resize-icon"></div>'))
                        .append($('<h3 class="portlet-header">Data Bundle Offers</h3>')));

        var bundle = function(bundleId, productDesc, windowPeriod, itemPrice) {

            var item = $('<li class="active-li data-bundle-item dataBundle standard-tr">');

            item.append(  $('<div class="bundle-size">')
                   .append( $('<span id="product-desc">')
                   .text(productDesc) ) );

            item.append($('<div class="bundle-window-period">')
                   .append($('<span id="window-period">')
                   .text("expires after " + windowPeriod)));

            item.append( $('<div id="bundle-price" class="bundle-price"></div>')
                           .text(itemPrice));

            item.append( $('<div class="radio-button"></div>')
                            .attr("id", bundleId));
            return item;
        };

        var content = $('<ul id="data-bundle-offers" class="widget-content decorated-ul">');

        content.append('<div class="data-bundle-purchase-status" style="display: none;"><span id="data-bundle-purchase-status"></span></div>');

        content.append( bundle("1", "5 MB data bundle", "30 days", "50c") );
        content.append( bundle("2", "10 MB data bundle", "30 days", "$1.00" ) );
        content.append( bundle("3", "400 MB data bundle", "60 days", "$10.00" ) );
        content.append( bundle("4", "400 MB data bundle", "60 days", "$10.00" ) );
        content.append( bundle("5", "400 MB data bundle", "60 days", "$10.00" ) );
        content.append( bundle("6", "400 MB data bundle", "60 days", "$10.00" ) );
        content.append( bundle("7", "400 MB data bundle", "60 days", "$10.00" ) );
        content.append( bundle("8", "400 MB data bundle", "60 days", "$10.00" ) );

        content.append('<div class="data-bundle-beneficiary-mobile" style="display: none;"><input id="data-bundle-beneficiary-mobile" type="text" placeholder="enter mobile number to topup"></div>');
        content.append('<div class="is-topping-up-own-phone" style="display: none;"><input id="is-topping-up-own-phone" type="checkbox">  <span id="my-mobile-phone">mine</span></div>');

        content.append('<div id="data-bundle-purchase-confirmation" style="width: 100%; display: none;align-content: center;padding-top: 8px;padding-bottom: 8px;padding-left: 14px; border-top: solid 0.1px;"><span id="data-bundle-purchase-confirmation-message"/><input id="data-bundle-purchase-confirmed" type="radio" name="data-bundle-purchase-confirmation" value="data-bundle-purchase-confirmed" style="margin-left: 10px;margin-right: 10px;">Yes<input id="data-bundle-purchase-cancelled" type="radio" name="data-bundle-purchase-confirmation" value="data-bundle-purchase-cancelled" style="margin-left: 10px;margin-right: 10px;">No</div>');

        content.append('<div class="data-bundle-confirmation-password" style="display: none;"><input id="data-bundle-confirmation-password" type="password" placeholder="enter your password to confirm"></div>');

        var buttons = $('<ul class="standard-ul"></ul>')
                .append($('<li class="standard-li"><button id="cancel-button" class="button" type="button" style="display: none;">Cancel</button></li>'))
                .append($('<li class="standard-li"><button id="data-bundle-purchase-button" class="button" type="button">Buy</button></li>'));

        content.append( $('<div class="button-pane clear-fix" style="border-left: 0px;border-right: 0px;">').append( buttons) );

        portlet.append(content);

        portlet.append($('<div class="bottomBorder"></div>'));

        userSession.setProduct( undefined );
        controller.dataBundleEventHandlers();
    },

    minimizeRegistrationPortlet : function() {

        $('#registration-portlet-resize-icon').removeClass('maximize-icon');
        $('#registration-portlet-resize-icon').removeClass('minimize-icon');
        $('#registration-portlet-resize-icon').addClass('minimize-icon');
        $('#registration-pane').css('display','none');
    },

    maximizeRegistrationPortlet : function() {

        $('#registration-portlet-resize-icon').removeClass('maximize-icon');
        $('#registration-portlet-resize-icon').removeClass('minimize-icon');
        $('#registration-portlet-resize-icon').addClass('maximize-icon');
        $('#registration-pane').css('display','block');
    },

    minimizeDataBundlePortlet : function(dataBundleReSizeIcon, dataBundlePortlet, dataBundleList) {
        dataBundleReSizeIcon.removeClass('maximize-icon');
        dataBundleReSizeIcon.removeClass('minimize-icon');
        dataBundleReSizeIcon.addClass('minimize-icon');
        dataBundlePortlet.css('display', 'block');
        dataBundleList.css('display','none');
    },

    maximizeDataBundlePortlet : function(dataBundleReSizeIcon, dataBundlePortlet, dataBundleList) {
        viewer.minimizeRegistrationPortlet();
        viewer.maximizeAccountListingPortlet();
        dataBundleReSizeIcon.removeClass('maximize-icon');
        dataBundleReSizeIcon.removeClass('minimize-icon');
        dataBundleReSizeIcon.addClass('maximize-icon');
        dataBundlePortlet.css('display', 'block');
        dataBundleList.css('display','block');
    },

    minimizeSubscriberListingPortlet : function() {

        $('#subscriber-list-portlet-resize-icon').removeClass('maximize-icon');
        $('#subscriber-list-portlet-resize-icon').removeClass('minimize-icon');
        $('#subscriber-list-portlet-resize-icon').addClass('minimize-icon');
        $('#subscriber-list-pane').css('display','none');
    },

    maximizeSubscriberListingPortlet : function() {

        $('#subscriber-list-portlet-resize-icon').removeClass('maximize-icon');
        $('#subscriber-list-portlet-resize-icon').removeClass('minimize-icon');
        $('#subscriber-list-portlet-resize-icon').addClass('maximize-icon');
        $('#subscriber-list-pane').css('display','block');
    },

    airtimeTransferPortlet : function ( ) {

        if ( ! userSession.isUserLoggedOn() ) {
            viewer.loginPortletVisible();
        }

        var portlet = $('#airtime-transfer-portlet');
        portlet.empty();

        portlet.append($('<div class="widget-head">')
            .append($('<div id="airtime-transfer-portlet-resize-icon"></div>'))
            .append($('<h3 class="portlet-header">Airtime Transfer</h3>')));

        var contentPane = $('<div id="airtime-transfer-pane"></div>');
        var content = $('<div class="boxed-container">');

        content.append('<div class="airtime-transfer-status" style="display: none;"><span id="airtime-transfer-status"></span></div>');

        var txnPane = $('<ul></ul>');
        //txnPane.append('<div id="airtime-payment-method" style="width: 100%; display: block;align-content: center;padding-top: 8px;padding-bottom: 8px;padding-left: 30px; border-top: solid 0.1px;">Payment Method: <input id="airtime-transfer" type="radio" name="payment-method" value="transfer-airtime" style="margin-left: 10px;margin-right: 10px;">Airtime Transfer<input id="buy-using-telecash" type="radio" name="payment-method" value="pay-by-telecash" style="margin-left: 40px;margin-right: 10px;">Pay by Telecash</div>');
        txnPane.append('<li class="transfer-amount" style="display: none;"><input id="transfer-amount" type="text" placeholder="Enter Amount"></li>');
        txnPane.append('<li class="transfer-beneficiary-mobile" style="display: none;"><input id="transfer-beneficiary-mobile" type="text" placeholder="Beneficiary Mobile"></li>');
        txnPane.append('<div class="is-purchase-for-own-phone" style="display: none;"><input id="is-purchase-for-own-phone" type="checkbox">  <span id="purchase-for-own-phone">mine</span></div>');

        txnPane.append('<div id="airtime-transfer-confirmation" style="width: 100%; display: none;align-content: center;padding-top: 8px;padding-bottom: 8px;padding-left: 14px; border-top: solid 0.1px;"><span id="transaction-confirmation-message"/><input id="airtime-transfer-confirmed" type="radio" name="airtime-transfer-confirmation" value="airtime-transfer-confirmed" style="margin-left: 10px;margin-right: 10px;">Yes<input id="airtime-transfer-cancelled" type="radio" name="airtime-transfer-confirmation" value="airtime-transfer-cancelled" style="margin-left: 10px;margin-right: 10px;">No</div>');

        content.append( txnPane );

        content.append('<div class="transfer-confirmation-password" style="display: none;"><input id="transfer-confirmation-password" type="password" placeholder="Enter Password"></div>');

        contentPane.append(content);

        var buttons = $('<ul class="standard-ul"></ul>')
            .append($('<li class="standard-li"><button id="transfer-cancel-button" class="button" type="button" style="display: none;">Cancel</button></li>'))
            .append($('<li class="standard-li"><button id="transfer-process-button" class="button" type="button">Buy</button></li>'));

        contentPane.append( $('<div class="button-pane clear-fix ">').append( buttons) );
        portlet.append(contentPane);

        $('.transfer-confirmation-password').css('display', 'none');
        $('#transfer-cancel-button').css('display', 'none');
        $('#buy-using-telecash').attr("checked", false);
        $('#transfer-airtime').attr("checked", false);

        $("input[name='payment-method']").change(function(){
            userSession.setPaymentMethod($('#buy-using-telecash').is(':checked') ? "TELECASH" : "AIRTIME");
            controller.airtimeTransferEventHandlers( $('#buy-using-telecash').is(':checked') ? "TELECASH" : "AIRTIME" );
        });
        controller.airtimeTransferEventHandlers( "AIRTIME" );

//        controller.downloadBalanceTransferSettings();
    },


    downloadBalanceTransferSettings : function() {
        var url =  HTTP_URL +
            "/billing-platform?" +
            "service-command=airtime-transfer-settings";

        var promise = $.getJSON(url, function( response ) {
            controller.processDataBundleResponse(response);
        });

        promise.fail( function( xmlHttpRequest, status, error ) {
            controller.processDataBundleResponse(
                    "abort" == status ? "Data bundle purchase timed out." : xmlHttpRequest.responseText);
        });

        setTimeout( function() { promise.abort(); }, HTTP_TIMEOUT );
    },


    minimizeAirtimeTransferPortlet : function () {

        $('#airtime-transfer-portlet-resize-icon').removeClass('maximize-icon');
        $('#airtime-transfer-portlet-resize-icon').removeClass('minimize-icon');
        $('#airtime-transfer-portlet-resize-icon').addClass('minimize-icon');
        $('#airtime-transfer-portlet').css('display', 'block');
        $('#airtime-transfer-pane').css('display', 'none');
    },

    maximizeAirtimeTransferPortlet : function() {

        viewer.minimizeRegistrationPortlet();
        viewer.maximizeAccountListingPortlet();

        $('#airtime-transfer-portlet-resize-icon').removeClass('minimize-icon');
        $('#airtime-transfer-portlet-resize-icon').removeClass('minimize-icon');
        $('#airtime-transfer-portlet-resize-icon').addClass('maximize-icon');
        $('#airtime-transfer-portlet').css('display', 'block');
        $('#airtime-transfer-pane').css('display', 'block');
    },

    voucherRechargePortlet : function ( ) {

        if ( ! userSession.isUserLoggedOn() ) {
            viewer.loginPortletVisible();
        }

        var portlet = $('#voucher-recharge-portlet');

        portlet.empty();

        portlet.append($('<div class="widget-head">')
            .append($('<div id="voucher-recharge-portlet-resize-icon"></div>'))
            .append($('<h3 class="portlet-header">Voucher Recharge</h3>')));

        var voucherRechargePane = $('<div id="voucher-recharge-pane"></div>');
        var content = $('<div class="boxed-container">');

        content.append('<div class="voucher-recharge-status" style="display: none;"><span id="voucher-recharge-status"></span></div>');

        var txnPane = $('<ul></ul>');
        txnPane.append('<li class="recharge-voucher"><input id="recharge-voucher" type="text" placeholder="Enter Voucher"></li>');
        txnPane.append('<li class="voucher-recharge-beneficiary-mobile"><input id="voucher-recharge-beneficiary-mobile" type="text" placeholder="Beneficiary Mobile"></li>');
        txnPane.append('<div class="is-recharging-own-phone" style="display: inline-block;"><input id="is-recharging-own-phone" type="checkbox">  <span id="recharge-mobile-phone">mine</span></div>');
        content.append( txnPane );

        content.append('<div id="voucher-recharge-confirmation" style="width: 100%; display: none;align-content: center;padding-top: 8px;padding-bottom: 8px;padding-left: 14px; border-top: solid 0.1px;"><span id="voucher-recharge-confirmation-message"/><input id="voucher-recharge-confirmed" type="radio" name="voucher-recharge-confirmation" value="voucher-recharge-confirmed" style="margin-left: 10px;margin-right: 10px;">Yes<input id="voucher-recharge-cancelled" type="radio" name="voucher-recharge-confirmation" value="voucher-recharge-cancelled" style="margin-left: 10px;margin-right: 10px;">No</div>');

        content.append('<div class="voucher-recharge-confirmation-password"><input id="voucher-recharge-confirmation-password" type="password" placeholder="Enter Password"></div>');

        voucherRechargePane.append(content);

        var buttons = $('<ul class="standard-ul"></ul>')
            .append($('<li class="standard-li"><button id="voucher-recharge-cancel-button" class="button" type="button">Cancel</button></li>'))
            .append($('<li class="standard-li"><button id="voucher-recharge-process-button" class="button" type="button">Recharge</button></li>'));

        voucherRechargePane.append( $('<div class="button-pane clear-fix ">').append( buttons) );

        portlet.append(voucherRechargePane);

        $('.voucher-recharge-confirmation-password').css('display', 'none');
        $('#voucher-recharge-cancel-button').css('display', 'none');

        controller.voucherRechargeEventHandlers();
    },


    minimizeVoucherRechargePortlet : function () {
        $('#voucher-recharge-portlet-resize-icon').removeClass('maximize-icon');
        $('#voucher-recharge-portlet-resize-icon').removeClass('minimize-icon');
        $('#voucher-recharge-portlet-resize-icon').addClass('minimize-icon');
        $('#voucher-recharge-portlet').css('display', 'block');
        $('#voucher-recharge-pane').css('display', 'none');
    },

    maximizeVoucherRechargePortlet : function() {

        viewer.minimizeRegistrationPortlet();
        viewer.maximizeAccountListingPortlet();

        $('#voucher-recharge-portlet-resize-icon').removeClass('maximize-icon');
        $('#voucher-recharge-portlet-resize-icon').removeClass('minimize-icon');
        $('#voucher-recharge-portlet-resize-icon').addClass('maximize-icon');
        $('#voucher-recharge-portlet').css('display', 'block');
        $('#voucher-recharge-pane').css('display', 'block');
    },

    hideErrorPanel : function(messageClass, messageBox) {
        messageBox.text( '' );
        messageClass.css('display', 'none');
    },

    displayError : function(  messageClass, messageBox, messageIcon, message ) {

        messageBox.text( message );
        messageIcon.prop('src','../img/nack.jpeg');
        messageIcon.removeClass('ack');
        messageIcon.addClass('nack');
//        messageClass.css('height', '26px');
        messageClass.css('display', 'block');
        messageClass.css('color', 'red');
    },

    displayInfo : function( messageClass, messageBox, messageIcon, message ) {

        messageBox.text( message );
        messageBox.css('vertical-align', 'middle');
        messageIcon.prop('src','../img/ack.jpg');
        messageIcon.removeClass('nack');
        messageIcon.addClass('ack');
//        messageClass.css('height', '32px');
        messageClass.css('display', 'block');
        messageClass.css('color', 'green');
    },

    placeholder : function(element, text) {
        element.attr('placeholder', text);
        element.val('');
    }
}
