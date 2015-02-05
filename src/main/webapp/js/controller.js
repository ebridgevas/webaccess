
var HTTP_URL = "/televas-httpbridge-1.0/";
var HTTP_TIMEOUT = 5000;
var TEST_DELAY   = 0;

var SERVICE_COMMANDS;
var MONTHS;
var subscriberPackage;
var DATA_BUNDLES;

var subscriberList;

var subscribersMap;

var dataBundles = new Object();

var controller = {

    init : function() {
        controller.initProperties();
    },

    dropDownMenuEventHandlers : function() {

        $('#profile-notification').text('');
        $('#profile-notification').css('display', 'none');
        $('#new-password-pane').css('display', 'none');
        $('#new-password').val('');

        $('#confirmation-password-pane').css('display', 'none');
        $('#confirmation-password').val('');

        $('#delete-profile-pane').css('display', 'block');
        $('#change-password-button-pane').css('display', 'block');

        $('#user-img').unbind();
        $('#user-img').click(function(){
            $('#drop-down-menu').toggle();

            if ( $('#drop-down-menu').is(':visible') ) {
                $('#subscriber-name').text(userSession.firstName + " " + userSession.lastName);
                $('#profile-mobile-number').text(toShortMobileNumberFormat( userSession.getUserId() ) );
                controller.signOutEvenHandler();
                $('#content').css('opacity', '0.3');
            } else {
                $('#drop-down-menu').unbind();
                $('#content').css('opacity', '1.0');
            }
        });

    },

    signOutEvenHandler : function() {
        $('#sign-out').click(function(){
            $('#content').css('opacity', '1.0');
            viewer.init();
        });

        $('#change-password-button').click(function(){
            $('#delete-profile-pane').css('display', 'none');

            $('#new-password-pane').css('display', 'block');
            $('#new-password-pane').css('width', '110px;');
            $('#new-password').css('width', '108px;');

            $('#confirmation-password-pane').css('display', 'block');
            $('#confirmation-password-pane').css('width', '108px;');
            $('#confirmation-password-pane').css('padding-right', '4px;');
            $('#confirmation-password').css('width', '106px;');
            $('.a-input-text').css('padding-right', '0px');
            $('.gb_ga').css('margin-left', '0px');
            $('#change-picture').css('margin-top', '28px');
            $('#profile-mobile-number').css('margin-bottom', '0px');
            $('#new-password').focus();

            $('#sign-out').unbind();
            $('#sign-out').text('Cancel');
            $('#sign-out').click(function() {
                $('#new-password-pane').css('display', 'none');
                $('#new-password').val('');
                $('#confirmation-password-pane').css('display', 'none');
                $('#confirmation-password').val('');
                $('#delete-profile-pane').css('display', 'block');
                $('#sign-out').text('Sign Out');
                $('#change-picture').css('margin-top', '15px');
                $('#profile-mobile-number').css('margin-bottom', '45px');
                $('#profile-notification-pane').css('display', 'none');
                $('#profile-notification').text('');
                controller.signOutEvenHandler();
            });

            $('#change-password-button').unbind();
            $('#change-password-button').click(function() {
                console.log("change password button clicked");
                var hasErrors = false;
                if ( ! isValidPassword( $('#new-password').val() ) ) {
                    hasErrors = true;
                    $('#new-password').props('placeholder', 'Invalid new password');
                    $('#new-password').val('');
                }
                console.log("valid new password " + hasErrors );

                if ( ! isValidPassword( $('#confirmation-password').val() ) ) {
                    hasErrors = true;
                    $('#confirmation-password').prop('placeholder', '* Current password *');
                    $('#confirmation-password').val('');
                }
                console.log("valid confirmation password " + hasErrors );

                if ( (! hasErrors) && $('#confirmation-password').val() != userSession.getUserPassword() ) {
                    hasErrors = true;
                    $('#confirmation-password').prop('placeholder', '* Wrong password *');
                    $('#confirmation-password').val('');
                }

                console.log("correct confirmation password " + hasErrors );

                if ( (! hasErrors) && $('#confirmation-password').val() == $('#new-password').val() ) {
                    hasErrors = true;
                    $('#new-password').props('placeholder', 'Not new password');
                    $('#new-password').val('');
                }
                console.log("correct new password " + hasErrors );

                if (! hasErrors ) {
                    console.log("changing password ..." );
                    var url =  HTTP_URL +
                        "/user-account-manager?service-command=set-user-password" +
                        "&mobile-number=" + toStandardMobileNumberFormat(userSession.getUserId()) +
                        "&password=" + userSession.getUserPassword() +
                        "&new-password=" + $('#new-password').val();

                    var promise = $.getJSON(url, function( data ) {
                        if (data.userAction == 'GRANT_ACCESS') {

                            console.log("password changed." );

                            userSession.setUserPassword( $('#new-password'));
                            $('#profile-notification-pane').css('display', 'block');
                            $('#profile-notification-pane').css('background-color', 'lightgreen');
                            $('#profile-notification').text('Password changed');
                            $('#new-password-pane').css('display', 'none');
                            $('#confirmation-password-pane').css('display', 'none');
                            $('#change-picture').css('margin-top', '15px');
                            $('#profile-mobile-number').css('margin-bottom', '50px');
                            $('#sign-out').text('Close');
                        } else {
                            console.log("password change failed. action: " + data.userAction  );
                        }
                    });
                }
            });
        });

        $('#delete-profile-button').click(function(){
            $('#change-password-button-pane').css('display', 'none');

            $('#new-password-pane').css('display', 'none');
            $('#new-password-pane').css('width', '110px;');
            $('#new-password').css('width', '108px;');

            $('#confirmation-password-pane').css('display', 'block');
            $('#confirmation-password-pane').css('width', '108px;');
            $('#confirmation-password-pane').css('padding-right', '4px;');
            $('#confirmation-password').css('width', '106px;');
            $('.a-input-text').css('padding-right', '0px');
            $('.gb_ga').css('margin-left', '0px');
            $('#change-picture').css('margin-top', '28px');
            $('#confirmation-password').focus();

            $('#sign-out').unbind();
            $('#sign-out').text('Cancel');
            $('#sign-out').click(function() {
                $('#new-password-pane').css('display', 'none');
                $('#confirmation-password-pane').css('display', 'none');
                $('#delete-profile-pane').css('display', 'block');
                $('#change-password-button-pane').css('display', 'block');
                $('#sign-out').text('Sign Out');
                $('#change-picture').css('margin-top', '15px');
                $('#profile-mobile-number').css('margin-bottom', '45px');
                controller.signOutEvenHandler();
            });

            $('#delete-profile-button').unbind();

            $('#delete-profile-button').click(function(){

                var hasErrors = false;

                if ( ! isValidPassword( $('#confirmation-password').val() ) ) {
                    hasErrors = true;
                    $('#confirmation-password').prop('placeholder', '* Current password *');
                    $('#confirmation-password').val('');
                }
                console.log("valid confirmation password " + hasErrors );
                console.log("entered password: " + $('#confirmation-password').val() + ", user password : " + userSession.getUserPassword() );
                if ( $('#confirmation-password').val() != userSession.getUserPassword() ) {
                    hasErrors = true;
                    $('#confirmation-password').prop('placeholder', '* Wrong password *');
                    $('#confirmation-password').val('');
                }

                if (! hasErrors ) {

                    console.log("deleting ..." );

                    var url =  HTTP_URL +
                        "/user-account-manager?service-command=deactivate-user" +
                        "&mobile-number=" + toStandardMobileNumberFormat(userSession.getUserId());

                    var promise = $.getJSON(url, function( data ) {
                        if (data == 'GRANT_ACCESS') {

                            console.log("Account deleted." );

                            userSession.setUserId( undefined );
                            userSession.setIsUserLoggedOn( false );
                            $('#profile-notification-pane').css('display', 'block');
                            $('#profile-notification').css('display', 'block');
                            $('#profile-notification-pane').css('background-color', 'lightgreen');
                            $('#profile-notification').text('Profile deleted.');
                            $('#new-password-pane').css('display', 'none');
                            $('#confirmation-password-pane').css('display', 'none');
                            $('#change-picture').css('margin-top', '15px');
                            $('#profile-mobile-number').css('margin-bottom', '50px');
                            $('#sign-out').text('Close');
                            $('#sign-out').unbind();
                            $('#sign-out').click(function(){
                                $('#content').css('opacity', '1.0');
                                viewer.init();
                            });
                        } else {
                            console.log("Account deletion failed. action: " + data.userAction  );
                        }
                    });
                }
            });
        });
    },

    registerUserIdEventsHandlers : function() {

        $('#register-command').click(function(){
            viewer.hideAll();

            viewer.registrationHowToPortlet( );
            controller.registerRegistrationHowToEventHandlers();
        });
    },

    registerRegistrationHowToEventHandlers : function() {

        $('#register-howto-cancel-button').unbind();

        $('#register-howto-cancel-button').click(function(){

            viewer.hideRegistrationHowToPortlet();
            viewer.hideWelcomeMessage();
            viewer.showUserIdEntry();
            viewer.hideRegistrationPortlet();
            viewer.maximizeDataBundlePortlet();
            viewer.maximizeAirtimeTransferPortlet();
            viewer.maximizeVoucherRechargePortlet();

            userSession.setIsUserLoggedOn( false );

            $('#loginButton').attr('value', 'Log In');
        });
    },

    userIdEventsHandlers : function() {

        $('#login-button').unbind();

//        $('#login-button').click(function(){
////            viewer.progressBarVisible(true, "Logging into Televas");
////            $('#progress-cancel-button').unbind();
////            $('#progress-cancel-button').click(function(){
////
////                viewer.progressBarVisible( false );
////            });
//            var user = new Object();
//            user.mobileNumber = "263733803480";
//            user.firstName = "David";
//            user.lastName = "Tekeshe";
//            user.emailAddress = "david@tekeshe.com";
//            user.securityQuestion = "Friend";
//            user.securityAnswer = "None";
//            user.notificationAgent = "email";
//            user.role = "user";
//            user.password = "changeit";
//            controller.userSession( user );
//            viewer.showCustomerCareAgentViews();
//        });

        $('#login-button').click(function() {

            controller.onLoginClick();
        });

        $(document).unbind();

        $(document).keypress(function(e) {
            if(e.which == 13) {
                controller.onLoginClick();
            }
        });

        $('#helper-button').unbind();
        $('#helper-button').click(function() {
            controller.registerSubscriber();
        });

        $('#auth-create-account-link').click(function(){
            viewer.registrationPortletVisible( true );
        });
    },

    onLoginClick : function() {
        if ( isValidMobileNumber($('#auth-email').val()) ) {

            var mobileNumber = toStandardMobileNumberFormat($('#auth-email').val());

            var url =  HTTP_URL +
                "/user-account-manager?service-command=validate-user-id&" +
                "mobile-number=" + mobileNumber;

            var promise = $.getJSON(url, function( data ) {

                userSession.userAction = data.userAction;

                if (userSession.userAction == "ENTER_PASSWORD") {

                    userSession.mobileNumber = mobileNumber;
                    userSession.setUserId( userSession.mobileNumber);
                    viewer.showPasswordEntry("Enter password");
                    viewer.disableUserIdEntry();
                    $('#auth-password').focus();
                    controller.registerPasswordValidationEventsHandlers();
//                    controller.forgotPasswordEventHandler();
                } else if ( userSession.userAction == "SET_PASSWORD") {
                    console.log("setting new password.");
                    userSession.mobileNumber = mobileNumber;
                    userSession.setUserId( userSession.mobileNumber);
//                        view.message(true, false, "Enter new password");
//                        view.inputMessageType("password");
//                        view.inputMessagePlaceholder("Enter new password here.");
//                        view.inputMessage("");
//                        view.leftButtonText("Cancel");
                    viewer.showPasswordEntry("Enter new password");
                    viewer.disableUserIdEntry();
                    controller.registerPasswordValidationEventsHandlers();
                } else if ( userSession.userAction == "ENTER_ACTIVATION_CODE") {
                    userSession.mobileNumber = mobileNumber;
                    userSession.setUserId( userSession.mobileNumber);
//                        view.message(true, false, controller.activationCodePromptMessage( data, false ));
//                        view.inputMessageType("text");
//                        view.inputMessagePlaceholder("Enter activation code here.");
//                        view.inputMessage("");
//                        view.leftButtonText("Re-New");
                    viewer.showPasswordEntry("Enter activation code");
                    viewer.disableUserIdEntry();
                    $('#auth-password').attr('type', 'text');
                    $('#auth-password').focus();
                    controller.registerActivationCodeEventHandlers();
                } else if ( userSession.userAction == "REGISTER_USER") {

                    $('#auth-email').prop('placeholder', 'Mobile not registered.');
                    $('#auth-email').val('');
                } else {
                    // Unknown response code
                }
            });

            promise.fail(function(xhr, status, error) {

                var message;
                if ( xhr.responseText == "") {
                    message = "Sorry our servers are offline. Administrator notified. Please in 15 minutes time.";
                } else {
                    message = "Mobile subscriber : " + mobileNumber + " failed to validate user id : " + xhr.responseText;
                }
                viewer.displayError($('.status-bar'), $('#system-error'), $('#status-bar-icon'), message );
                console.log( message );
            });
        } else {
            viewer.placeholder($('#auth-email'), 'Invalid mobile number.');
        }
    },

    registerPasswordValidationEventsHandlers : function() {

        $('#login-button').unbind();

        $('#login-button').click(function() {

            controller.onPasswordSubmit();
        });

        $(document).unbind();

        $(document).keypress(function(e) {
            if(e.which == 13) {
                controller.onPasswordSubmit();
            }
        });

        $('#forgot-password').unbind();
        $('#forgot-password').click(function(){
            var url =  HTTP_URL +
                "/user-account-manager?service-command=reset-password" +
                "&mobile-number=" + userSession.mobileNumber;

            var promise = $.getJSON(url, function( data ) {

                userSession.userAction = data.userAction;

                if (userSession.userAction == "ENTER_ACTIVATION_CODE") {
                    $('#auth-password').prop('placeholder', 'Enter activation code');
                    $('#auth-password').val('');
                    controller.registerActivationCodeEventHandlers();
                }
            });

            promise.fail(function(xhr, status, error) {

                view.message(true, false, "Login failed.<br/>Please try shortly.");
                console.log("Mobile subscriber : " + userSession.mobileNumber + " failed to set password : " + xhr.responseText );
            });
        });
    },

    onPasswordSubmit : function() {

        if ( isValidPassword( $('#auth-password').val()) ) {

            console.log("password validation. action : " + userSession.userAction );
            if (userSession.userAction == "ENTER_PASSWORD") {

                var password = $('#auth-password').val();

                var url =  HTTP_URL +
                    "/user-account-manager?service-command=authenticate-user" +
                    "&mobile-number=" + userSession.mobileNumber + "&" +
                    "password=" + password;

                var promise = $.getJSON(url, function( data ) {

                    userSession.userAction = data.userAction;
                    console.log("password response : " + userSession.userAction);
                    if (userSession.userAction == "GRANT_ACCESS") {
                        userSession.setUserPassword( $('#auth-password').val() );
                        controller.userSession( data );
                        if ( userSession.getUserRole().toLowerCase() == 'agent') {
                            viewer.showCustomerCareAgentViews();
                        } else {
                            viewer.showOrdinaryUserViews();
                        }
                    } else if (userSession.userAction == "INVALID_PASSWORD") {
                        userSession.userAction = "ENTER_PASSWORD";
                        viewer.showPasswordEntry("Wrong password.");
                    } else if (userSession.userAction == "ENTER_ACTIVATION_CODE") {
//                            view.message(true, false, controller.activationCodePromptMessage( data, false ));
//                            view.inputMessageType("text");
//                            view.inputMessagePlaceholder("Enter activation code here.");
//                            view.inputMessage("");
//                            view.leftButtonText("Re-New");
                        controller.registerActivationCodeEventHandlers();
                    }
                });

                promise.fail(function(xhr, status, error) {

                    view.message(true, false, "Login failed.<br/>Please try shortly.");
                    console.log("Mobile subscriber : " + userSession.mobileNumber + " failed to set password : " + xhr.responseText );
                });

            } else if ( userSession.userAction == "SET_PASSWORD") {
                console.log("prompting for password confirmation...");
                userSession.userAction = "CONFIRM_SET_PASSWORD";
                $('#auth-password').attr('type', 'password');
                userSession.setUserPassword($('#auth-password').val());
                console.log("password 1 : " + userSession.getUserPassword());
//                    view.message(true, false, "Re-enter new password");
//                    view.inputMessageType("password");
//                    view.inputMessagePlaceholder("Re-enter new password here.");
//                    view.inputMessage("");
//                    view.leftButtonText("Cancel");
                viewer.showPasswordEntry("Re-enter same password");
                controller.registerPasswordValidationEventsHandlers();

            } else if ( userSession.userAction == "CONFIRM_SET_PASSWORD") {
                console.log("password 2 : " + $('#auth-password').val() );
                if ( userSession.getUserPassword() == $('#auth-password').val() ) {

                    var url =  HTTP_URL +
                        "/user-account-manager?service-command=set-user-password" +
                        "&mobile-number=" + userSession.mobileNumber + "&" +
                        "new-password=" + $('#auth-password').val();

                    var promise = $.getJSON(url, function( data ) {

                        userSession.userAction = data.userAction;

                        if (userSession.userAction == "GRANT_ACCESS") {

                            userSession.setUserPassword( $('#auth-password').val() );
                            console.log("###### session password : " + userSession.getUserPassword());
                            userSession.setIsUserLoggedOn( true );

                            userSession.setFirstName( data.firstName);
                            userSession.setLastName( data.lastName );
                            userSession.setUserEmail( data.emailAddress );
                            userSession.setSecurityQuestion( data.securityQuestion );
                            userSession.setSecurityAnswer( data.securityAnswer );
                            userSession.setNotificationAgent( data.notificationAgent );
                            userSession.setUserRole( data.role );
//                            accountListing();
//                            homePage();
                            console.log("access granted ...........");
                            viewer.showWelcomeMessage();
                            if ( userSession.getUserRole().toLowerCase() == 'agent') {
                                console.log("access granted ........... showCustomerCareAgentViews");
                                viewer.showCustomerCareAgentViews();
                            } else {
                                console.log("access granted ...........viewer.showOrdinaryUserViews()");
                                viewer.showOrdinaryUserViews();
                            }
                        } else if (userSession.userAction == "SET_PASSWORD_RETRY") {
                            userSession.userAction = "SET_PASSWORD";

//                                view.message(true, false, "Password not accepted. <br/>Enter new password again.");
//                                view.inputMessageType("password");
//                                view.inputMessagePlaceholder("Enter new password here.");
//                                view.inputMessage("");
//                                view.leftButtonText("Cancel");
                            viewer.showPasswordEntry("Enter new password");
                            controller.registerPasswordValidationEventsHandlers();
                        }
                    });

                    promise.fail(function(xhr, status, error) {

//                            view.message(true, false, "Sorry password change failed.<br/>Please try shortly.");
                        viewer.showPasswordEntry("Enter new password");
                        controller.registerPasswordValidationEventsHandlers();
                        console.log("Mobile subscriber : " + userSession.mobileNumber + " failed to set password : " + xhr.responseText );
                    });

                } else {

                    userSession.userAction = "SET_PASSWORD";

//                        view.message(true, false, "Passwords mismatch.<br/>Enter new password again.");
//                        view.inputMessageType("password");
//                        view.inputMessagePlaceholder("Enter new password here.");
//                        view.inputMessage("");
//                        view.leftButtonText("Cancel");

                    viewer.showPasswordEntry("Mismatch. Enter password.");
                    controller.registerPasswordValidationEventsHandlers();
                }
            } else if (userSession.userAction == "GRANT_ACCESS") {

                console.log("access granted ");
                controller.userSession( data );
                if ( userSession.getUserRole().toLowerCase() == 'agent') {
                    viewer.showCustomerCareAgentViews();
                } else {
                    console.log("calling showOrdinaryUserViews ... ");
                    viewer.showOrdinaryUserViews();
                }
            } else if (    userSession.getServiceCommand() == SERVICE_COMMAND.DATA_BUNDLE_PURCHASE
                || userSession.getServiceCommand() == SERVICE_COMMAND.AIRTIME_TRANSFER
                || userSession.getServiceCommand() == SERVICE_COMMAND.VOUCHER_RECHARGE ) {


                if (userSession.getUserPassword() == $('#input-message').val() ) {
                    view.message(false, false, "Transaction confirmed.<br/>"+
                        "Please process.");
                } else {
                    view.message(true, false, "Wrong password entered.<br/>To confirm the transaction,<br/>Please enter your password.");
                    view.inputMessageType("password");
                    view.inputMessagePlaceholder("Enter confirmation password here.");
                    view.inputMessage("");
                    view.leftButtonText("Cancel");
                    registerPasswordValidationEventsHandlers();
                }

                // David 2
            } else {
                console.log("No user action specified");
            }
        } else {
            $('#auth-password').prop('placeholder', 'Invalid password. 6 char minimum');
            $('#auth-password').val('');
        }
    },

    forgotPasswordEventHandler : function() {

        viewer.hideStatus();
        $('#forgot-password-command').unbind();
        $('#forgot-password-command').click(function() {

            if ( isValidMobileNumber($('#email').val()) ) {

                var mobileNumber = toStandardMobileNumberFormat($('#email').val());

                var url =  HTTP_URL +
                    "/user-account-manager?service-command=reset-password&" +
                    "mobile-number=" + mobileNumber;

                var promise = $.getJSON(url, function( data ) {

                    userSession.userAction = data.userAction;

                    if ( userSession.userAction == "ENTER_ACTIVATION_CODE") {
                        userSession.mobileNumber = mobileNumber;
                        userSession.setUserId( userSession.mobileNumber);
//                        view.message(true, false, controller.activationCodePromptMessage( data, false ));
//                        view.inputMessageType("text");
//                        view.inputMessagePlaceholder("Enter activation code here.");
//                        view.inputMessage("");
//                        view.leftButtonText("Re-New");
                        viewer.showPasswordEntry("Enter activation code");
                        viewer.disableUserIdEntry();
                        $('#pass').attr('type', 'text');
                        $('#pass').focus();
                        controller.registerActivationCodeEventHandlers();
                    } else if ( userSession.userAction == "REGISTER_USER") {
                        controller.registerSubscriber();

                    } else {
                        // Unknown response code
                    }
                });

                promise.fail(function(xhr, status, error) {

                    var message;
                    if ( xhr.responseText == "") {
                        message = "Sorry our servers are offline. Administrator notified. Please in 15 minutes time.";
                    } else {
                        message = "Mobile subscriber : " + mobileNumber + " failed to validate user id : " + xhr.responseText;
                    }
                    viewer.displayError($('.status-bar'), $('#system-error'), $('#status-bar-icon'), message );
                    console.log( message );
                });
            } else {
                $('#email').attr('placeholder', 'Invalid mobile number.');
                $('#email').val('');
            }
        });

        $('#helper-button').unbind();
        $('#helper-button').click(function() {
            controller.registerSubscriber();
        });
    },

    registerLogoutEvent : function() {
        $('#loginButton').unbind();

        $('#loginButton').click(function(){
//            viewer.hideWelcomeMessage();
//            viewer.showUserIdEntry();
//            viewer.hideRegistrationPortlet();
//            viewer.hideSettingsPortlet();
//            viewer.maximizeDataBundlePortlet();
//            viewer.maximizeAirtimeTransferPortlet();
//            viewer.maximizeVoucherRechargePortlet();
            viewer.showDefaultViews();
            userSession.setIsUserLoggedOn( false );

            $('#loginButton').attr('value', 'Log In');
        });
    },

    registerSettingsEvent : function() {

        $('#settings-button-container').unbind();

        $('#settings-button-container').click(function() {
            viewer.hideRegistrationHowToPortlet();
            viewer.hideRegistrationPortlet();
            viewer.minimizeDataBundlePortlet();
            viewer.minimizeAirtimeTransferPortlet();
            viewer.minimizeVoucherRechargePortlet();

            viewer.settingsPortlet( $('#settings-portlet') );
        });
    },

    registerActivationCodeEventHandlers : function() {

        $('#login-button').unbind();

        $('#login-button').click(function() {

//            view.message(false, false, "*****");
            var securityToken = $('#auth-password').val();
            securityToken = securityToken.replace(/ /g, '');
            console.log("validating security-token: " + securityToken);
            if ( isValidPassword(securityToken) ) {

                var url =  HTTP_URL +
                    "/user-account-manager?service-command=activate-user" +
                    "&mobile-number=" + userSession.mobileNumber +
                    "&activation-code=" + $('#auth-password').val();

                var promise = $.getJSON(url, function( data ) {

                    userSession.userAction = data.userAction;

                    if ( userSession.userAction == "SET_PASSWORD") {
//                        view.message(true, false, "Enter new password");
//                        view.inputMessageType("password");
//                        view.inputMessagePlaceholder("Enter new password here.");
//                        view.inputMessage("");
//                        view.leftButtonText("Cancel");
                        $('#auth-password').attr('type', 'password');
                        $('#auth-password').attr('placeholder', 'Enter new password');
                        $('#auth-password').val('');
                        controller.registerPasswordValidationEventsHandlers();
                    } else if ( userSession.userAction == "ENTER_VALID_ACTIVATION_CODE") {
//                        view.message(true, false, controller.activationCodePromptMessage( data, true ));
                        $('#auth-password').attr('placeholder', 'Invalid activation code');
                        $('#auth-password').val('');
                        controller.registerActivationCodeEventHandlers();
                    } else if ( userSession.userAction == "REGISTER_USER") {
//                        view.inputMessageType("text");
//                        view.inputMessagePlaceholder("Enter mobile number here.");
//                        view.inputMessage("");
//                        view.message(true, false, controller.registrationMessage());
                        $('#auth-password').attr('placeholder', 'User not registered');
                        $('#auth-password').val('');
                        controller.registerActivationCodeEventHandlers();
                    } else {
                        // Unknown response code
                    }
                });

                promise.fail(function(xhr, status, error) {
                    $('#auth-password').attr('placeholder', 'Error. Please retry');
                    $('#auth-password').val('');
//                    view.message(true, false, "Sorry our servers are offline.<br/>Please try shortly.");
                    console.log("Mobile subscriber : " + mobileNumber + " failed to validate user id : " + xhr.responseText );
                });
            } else {
//                view.message(true, false, "Invalid Activation Code. Try again.");
                $('#pass').attr('placeholder', 'Invalid activation code');
                $('#pass').val('');
            }
        });
    },

    isValidRegistrationForm : function() {

        var isValid = true;
        $('.input').attr('placeholder', '');

        if (! isValidFirstName( $('#first-name').val())) {
            $('#first-name').attr('placeholder','* enter first name *');
            $('#first-name').val("");
            isValid = false;
        }

        if (! isValidSurname( $('#last-name').val())) {
            $('#last-name').attr('placeholder','* enter last name *');
            $('#last-name').val('');
            isValid = false;
        }

        if ( ! isValidMobileNumber ( $('#mobile-number').val() ) ) {

            $('#mobile-number').attr('placeholder', '* enter mobile number * ');
            $('#mobile-number').val('');
            isValid = false;
        }

        if ( $('#is-email-default').prop('checked') && ( ! isValidUserEmailAddress( $('#subscriber-email').val() ) ) ) {
            $('#subscriber-email').attr('placeholder', '* enter mobile number * ');
            $('#subscriber-email').val('');
            isValid = false;
        }

        if ( ( ! $('#sms-capable').prop('checked')) && ( ! isValidUserEmailAddress( $('#email-address').val() ) )  ) {

            $('#email-address').attr('placeholder', '* enter email address * ');
            $('#email-address').val('');
            isValid = false;
        }

        if ( ! isValidSecurityQuestion ( $('#security-question').val() ) ) {

            $('#security-question').attr('placeholder', '* enter security question * ');
            $('#security-question').val('');
            isValid = false;
        }

        if ( ! isValidSecurityAnswer ( $('#security-answer').val() ) ) {

            $('#security-answer').attr('placeholder', '* enter security answer * ');
            $('#security-answer').val('');
            isValid = false;
        }

//        if ( $('#new-password') ) {
//
//            if ( $('#new-password').val() != $('#repeat-new-password').val()  ) {
//                $('#new-password').prop('placeholder', '* mismatch. enter new password *');
//            }
//        }

        return isValid;
    },

    parseRegistrationForm : function() {

        if ( controller.isValidRegistrationForm() ) {

            var subscriber = {};
            subscriber["mobileNumber"] = toStandardMobileNumberFormat($('#mobile-number').val());
            subscriber["firstName"] = $('#first-name').val();
            subscriber["surname"] = $('#last-name').val();
            subscriber["emailAddress"] = $('#email-address').val();
            subscriber["notificationAgent"] = $('#sms-capable').prop('checked') ? 'SMS' : 'EMAIL';
            subscriber["securityQuestion"] = $('#security-question').val();
            subscriber["securityAnswer"] = $('#security-answer').val();
            subscriber["role"] = $('#role').val();

            if ($('#new-password').val()) {
                subscriber["password"] = $('#new-password').val();
            } else {
                subscriber["password"] = "*";
            }

            return JSON.stringify( subscriber ) ;
        }

        return null;
    },

    registerSubscriber : function( subscriber, serviceCommand ) {

        var url =  HTTP_URL +
            "/user-account-manager?service-command=" + serviceCommand.sServiceCommand +
            "&subscriberMobileNumber=" + subscriber.mobileNumber +
            "&subscriberEmailAddress=" + subscriber.emailAddress +
            "&subscriberFirstName=" + subscriber.firstName +
            "&subscriberSurname=" + subscriber.surname +
            "&subscriberSecurityQuestion=" + subscriber.securityQuestion +
            "&subscriberSecurityAnswer=" + subscriber.securityAnswer +
            "&communicationAgent=SMS" + //subscriber.notificationAgent +
            "&subscriberRole=USER" + //subscriber.role +
            "&password=" + subscriber.password;

        var promise = $.getJSON(url, function( data ) {

            if ( serviceCommand == SERVICE_COMMANDS.REGISTER_SUBSCRIBER ) {

                var endpoint = ( data.notificationAgent.toLowerCase() == "sms") ?
                                    data.mobileNumber
                                        : data.emailAddress;

                viewer.displayInfo(
                    $('.register-status'),
                    $('#register-status'),
                    $('#register-status-icon'),
                    data.firstName + " " + data.lastName + " created. Code sent to : " + endpoint );

                $('#register-process-button').css('display', 'none');
                $('#register-cancel-button').css('display', 'inline-block');
                $('#register-cancel-button').text('Close');

                $('#register-cancel-button').unbind();
                $('#register-cancel-button').click(function(){
                    viewer.hide();
                    $('#contentCol').css('background-color', 'white');
                    viewer.loginPortletVisible( true );
                });
                // controller.updateSubscriberList( data );
            } else {

                viewer.displayInfo(
                    $('.register-status'),
                    $('#register-status'),
                    $('#register-status-icon'),
                     data.firstName + " " + data.lastName + " modified.");

                $('#register-process-button').text('Close');
                $('#register-process-button').unbind();
                $('#register-process-button').click(function(){
                    viewer.registrationPortlet();
                    viewer.deselectSubscriber();
                });
            }
        });

        promise.fail(function(xhr, status, error) {

            viewer.displayError( $('.register-status'),
                $('#register-status'),
                $('#register-status-icon'),
                xhr.responseText != "" ?
                    xhr.responseText
                    : "TeleVas is offline. Please keep trying.");

            $('.register-status').css('display', 'block');
            $('#register-cancel-button').css('display', 'inline-block');
        });
    },

    initProperties : function() {
        SERVICE_COMMANDS = {
            REGISTER_SUBSCRIBER: {
                title : "Register Subscriber",
                buttonText : "Register",
                sServiceCommand: "register-subscriber",
                notificationMessage: "registered"
            },
            MODIFY_SUBSCRIBER: {
                title : "Edit Subscriber",
                buttonText : "Save",
                sServiceCommand: "modify-subscriber",
                notificationMessage: "updated"
            },
            DELETE_SUBSCRIBER:{
                title : "Delete Subscriber",
                buttonText : "Delete",
                sServiceCommand: "delete-user",
                notificationMessage: "deleted"
            }
        };

        MONTHS = ["January", "February", "March", "April", "May", "June",
                   "July", "August", "September", "October", "November", "December"]

    },

    registerEventHandlers : function() {

        $( "#register-process-button" ).click(function(){

            $('#register-cancel-button').css('display', 'inline-block');
            var subscriber = controller.parseRegistrationForm();

            if ( subscriber != null ) {
                controller.registerSubscriber( JSON.parse(subscriber), SERVICE_COMMANDS.REGISTER_SUBSCRIBER )
            }
        });

        $('#register-cancel-button').click(function(){

            viewer.registrationPortlet( );
        });

        $('#registration-portlet-resize-icon').click(function(){

            if ( $('#registration-pane').css('display') == 'none' ) {
                viewer.maximizeRegistrationPortlet();
            } else {
                viewer.minimizeRegistrationPortlet();
            }
        });
    },

    settingsEventHandlers : function() {

        $('#is-mobile-number-default').click(function() {
            if ( $(this).prop('checked') ) {
                $('#is-email-default').prop('checked', false);
            }
        });

        $('#is-email-default').click(function() {
            if ( $(this).prop('checked') ) {
                $('#is-mobile-number-default').prop('checked', false);
            }
        });

        $( "#settings-save-button" ).click(function(){

            if ( $('#new-password').val() ) {

                if ( userSession.userAction == "CHANGE_PASSWORD") {
                    if ( $('#new-password').val() == userSession.newPassword ) {
                        userSession.userAction = undefined;
                    } else {
                        $('#new-password').prop('placeholder','* Passwords mismatch. Try again *');
                        $('#new-password').val('');
                        userSession.userAction = undefined;
                        return;
                    }
                } else {
                    userSession.userAction = "CHANGE_PASSWORD";
                    userSession.newPassword = $('#new-password').val();
                    $('#new-password').prop('placeholder','repeat new password again.');
                    $('#new-password').val('');
                    return;
                }
            }

            $('#register-cancel-button').css('display', 'inline-block');
            var subscriber = controller.parseRegistrationForm();

            if ( subscriber != null ) {
                controller.registerSubscriber( JSON.parse(subscriber), SERVICE_COMMANDS.MODIFY_SUBSCRIBER )
            }
        });

        $('#register-cancel-button').click(function(){

            viewer.hideSettingsPortlet();

        });

//        $('#is-mobile-number-default').click( function() {
//            if ( $('#is-mobile-number-default').prop('checked')) {
//                $('#is-email-default').attr('checked', false);
//            } else {
//                $('#is-email-default').prop('checked', true);
//            }
//        });
//
//        $('#is-email-default').click( function() {
//            if ( $('#is-email-default').prop('checked')) {
//                $('#is-mobile-number-default').attr('checked', false);
//            } else {
//                $('#is-mobile-number-default').prop('checked', true);
//            }
//        });
    },

    accountListingEventHandlers : function() {

        $('#account-listing-portlet-resize-icon').click(function () {

            if ($('#account-listing').css('display') == 'none') {
                viewer.maximizeDataBundlePortlet();
            } else {
                viewer.minimizeDataBundlePortlet();
            }
        });
    },

    dataBundleEventHandlers : function() {

        $('#data-bundle-portlet-resize-icon').click(function(){

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            if ( $('#data-bundle-offers').css('display') == 'none' ) {
                viewer.maximizeDataBundlePortlet();
            } else {
                viewer.minimizeDataBundlePortlet();
            }
        });

        $(".radio-button").click(function(e){

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            var selected = $(this).hasClass('ticked-radio-button');
            $('.radio-button').removeClass('ticked-radio-button');
            if ( selected == false ) {
                $(this).addClass('ticked-radio-button');
            }

            if ($(this).hasClass("ticked-radio-button") ) {

                $('#payment-method').css('display', 'inline-block');
                var purchaseAmount = $(this).attr("amount");
                var productCode = $(this).attr("id");
                var productDescription = $(this).attr("description");

                $('#pay-by-telecash').attr("checked", false);
                $('#pay-by-airtime').attr("checked", false);

                $("input[name='payment-method']").change(function(){
                    userSession.setPaymentMethod($('#pay-by-telecash').is(':checked') ? "TELECASH" : "AIRTIME");
                    controller.processPaymentMethodSelection( purchaseAmount, productCode, productDescription );
                });
            } else {

                $('#payment-method').css('display', 'none');
            }
        });

        $('#data-bundle-purchase-button').unbind();

        $('#data-bundle-purchase-button').click(function() {

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            $('#data-bundle-purchase-status').val('');
            $('.data-bundle-purchase-status').css('display', 'none');

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible( true );

            } else if ( userSession.getProduct() == undefined ) {
//                $('#data-bundle-purchase-status').text('Please a select data bundle first.');
//                $('.data-bundle-purchase-status').css('display', 'block');
                viewer.displayError (
                    $('.data-bundle-purchase-status'),
                    $('#data-bundle-purchase-status'),
                    $('#data-bundle-purchase-status-icon'),
                    "Please a select data bundle first." );
            } else if ( ! isValidMobileNumber ( $('#data-bundle-beneficiary-mobile').val() ) ) {
//                $('#data-bundle-purchase-status').text('Enter a valid mobile number to topup.');
//                $('.data-bundle-purchase-status').css('display', 'block');

                viewer.displayError (
                    $('.data-bundle-purchase-status'),
                    $('#data-bundle-purchase-status'),
                    $('#data-bundle-purchase-status-icon'),
                        "Enter a valid mobile number to topup." );

            }  else if ( ! isValidPassword($('#data-bundle-confirmation-password').val())) {
                if ( $('.data-bundle-purchase-status').is('visible')) {
                    $('#data-bundle-purchase-status').text('Enter a valid confirmation password.');
                    $('.data-bundle-purchase-status').css('display', 'block');
                } else {

                    if (userSession.getPaymentMethod() == 'TELECASH' ) {

                        $('#data-bundle-confirmation-password').prop('placeholder',
                            'Dial *199# and enter password received here.');
                        $('.data-bundle-confirmation-password').css('display', 'block');
                        $('#data-bundle-confirmation-password').focus();

                    } else  {
                        $('#data-bundle-confirmation-password').prop('placeholder',
                            'Enter password here to purchase ' + userSession.productDescription);
                        $('#data-bundle-confirmation-password').text('');
                        $('.data-bundle-confirmation-password').css('display', 'block');

                        viewer.displayError(
                            $('.data-bundle-purchase-status'),
                            $('#data-bundle-purchase-status'),
                            $('#data-bundle-purchase-status-icon'),
                            "Enter password here to purchase " + userSession.productDescription);
                    }
                }

            }  else if ( userSession.getPaymentMethod() == 'AIRTIME' &&
                            (userSession.getUserPassword() != $('#data-bundle-confirmation-password').val() )) {
//                $('#data-bundle-purchase-status').text('Wrong password. Try again');
//                $('.data-bundle-purchase-status').css('display', 'block');

                viewer.displayError (
                    $('.data-bundle-purchase-status'),
                    $('#data-bundle-purchase-status'),
                    $('#data-bundle-purchase-status-icon'),
                    "Wrong password. Try again." );


            } else {
                $('#data-bundle-purchase-status').text('');
                $('.data-bundle-purchase-status').css('display', 'none');

                userSession.setBeneficiaryMobileNumber(
                    toStandardMobileNumberFormat( $('#data-bundle-beneficiary-mobile').val()) );

                userSession.setOneTimePassword( $('#data-bundle-confirmation-password').val() );
                viewer.progressBarVisible( true, "Purchasing " + userSession.productDescription );

                $('#progress-cancel-button').click(function(){

                    viewer.progressBarVisible( false );
//                    $('#data-bundle-purchase-status').text('Bundle purchase cancelled.');
//                    $('.data-bundle-purchase-status').css('display', 'block');
                    viewer.displayError (
                        $('.data-bundle-purchase-status'),
                        $('#data-bundle-purchase-status'),
                        $('#data-bundle-purchase-status-icon'),
                        "Bundle purchase cancelled." );
                });

                setTimeout(
                    function() {
                        controller.processDataBundleRequest( );
                    }, TEST_DELAY );
             }
        });
    },
    // David
    processPaymentMethodSelection : function( purchaseAmount, productCode, productDescription ) {

        if ( ( purchaseAmount > userSession.mainAccountBalance ) && ! $('#pay-by-telecash').is(':checked') ) {

            viewer.displayError (
                $('.data-bundle-purchase-status'),
                $('#data-bundle-purchase-status'),
                $('#data-bundle-purchase-status-icon'),
                "Insufficient credit for selected bundle, please top up airtime or subscribe to a smaller bundle." );

            $('.data-bundle-purchase-status').css('display', 'block');
            $('.data-bundle-beneficiary-mobile').css('display', 'none');
            $('.is-topping-up-own-phone').css('display', 'none');
            $('.data-bundle-confirmation-password').css('display', 'none');
            $('#payment-method').css('display', 'none');

        } else {

            userSession.setProduct( productCode );
            userSession.productDescription = productDescription;
            viewer.minimizeAirtimeTransferPortlet();
            viewer.minimizeVoucherRechargePortlet();

            $('#data-bundle-purchase-status').val('');
            $('.data-bundle-purchase-status').css('display', 'none');

            controller.processDataBundleSelectedEvent();
        }
    },

    processDataBundleRequest : function() {

        var url =  HTTP_URL +
            "/billing-platform?" +
            "service-command=data-bundle-purchase&mobile-number=" + userSession.getUserId() +
            "&product-code=" + userSession.getProduct() +
            "&beneficiary-id=" + userSession.getBeneficiaryMobileNumber() +
            "&payment-method=" + userSession.getPaymentMethod() +
            "&one-time-password=" + userSession.getOneTimePassword();

        var promise = $.getJSON(url, function( response ) {
            controller.processDataBundleResponse(response);
        });

        promise.fail( function( xmlHttpRequest, status, error ) {
            controller.processDataBundleResponse(
                "abort" == status ? "Data bundle purchase timed out." : xmlHttpRequest.responseText);
        });

        setTimeout( function() { promise.abort(); }, HTTP_TIMEOUT );
    },

    processDataBundleResponse : function( response ) {
//        $('#data-bundle-purchase-status').text( response );
//        $('.data-bundle-purchase-status').css('display', 'block');

        viewer.displayInfo (
            $('.data-bundle-purchase-status'),
            $('#data-bundle-purchase-status'),
            $('#data-bundle-purchase-status-icon'),
            response );

        $('#data-bundle-beneficiary-mobile').prop('placeholder','enter mobile number to topup');
        $('#data-bundle-beneficiary-mobile').val('');
        $('#data-bundle-beneficiary-mobile').prop('disabled', false);
        $('#data-bundle-beneficiary-mobile').css('display', 'none');
        $('.is-topping-up-own-phone').css('display', 'none');
        $('#data-bundle-beneficiary-mobile').css('background-color', 'white');
        $('#my-mobile-phone').css('color', 'grey');
        $('.data-bundle-confirmation-password').css('display', 'none');
        $('#data-bundle-confirmation-password').val('');

        viewer.accountListPortletVisible( true );

        $('#data-bundle-purchase-button').css('display', 'none');
        $('#cancel-button').text('Close');
        $('#pay-by-airtime').attr('checked', 'false');
        $('#pay-by-telecash').attr('checked', 'false');
        $('#payment-method').css('display', 'none');
        viewer.progressBarVisible( false );

    },

    displayResponse : function( successful, response, elem, elementClass, processButton, cancelButton ) {
        elem.text( response );
        elementClass.css('display', 'block');
        elementClass.css('color', ( successful == true ? 'green' : 'red'));
        if ( successful == true) {
            processButton.css('display', 'none');
            cancelButton.css('display', 'block');
            cancelButton.text('Close');
        }
        viewer.progressBarVisible( false );
    },

    processDataBundleSelectedEvent : function() {

        if ( ! userSession.isUserLoggedOn() ) {
            //TODO
//            userSession.setUserId("263733803480");
            $('#data-bundle-purchase-status').text('Please login first.');
            $('.data-bundle-purchase-status').css('display', 'block');
        }

        $('.data-bundle-beneficiary-mobile').css('display', 'block');
        $('#data-bundle-beneficiary-mobile').css('display', 'block');
        $('.is-topping-up-own-phone').css('display', 'block');
        $('#cancel-button').css('display', 'inline-block');

        $('#is-topping-up-own-phone').unbind();
        $('#is-topping-up-own-phone').click(function(){

            $('#data-bundle-purchase-status').val('');
            $('.data-bundle-purchase-status').css('display', 'none');

            if ($(this).prop('checked') ) {
                $('#data-bundle-beneficiary-mobile').val(toShortMobileNumberFormat( userSession.getUserId()));
                $('#data-bundle-beneficiary-mobile').prop('disabled', true);
                $('#data-bundle-beneficiary-mobile').css('background-color', 'lightgrey');
                $('#my-mobile-phone').css('color', 'white');
                $('#data-bundle-confirmation-password').prop('placeholder',
                        userSession.getPaymentMethod() == 'TELECASH' ?
                              'Dial *199# and enter password received here.'
                            : 'Enter password here to purchase ' + userSession.productDescription);
                $('.data-bundle-confirmation-password').css('display', 'block');
                $('#data-bundle-confirmation-password').focus();
            } else {
                $('#data-bundle-beneficiary-mobile').prop('placeholder','enter mobile number to topup');
                $('#data-bundle-beneficiary-mobile').val('');
                $('#data-bundle-beneficiary-mobile').prop('disabled', false);
                $('#data-bundle-beneficiary-mobile').css('background-color', 'white');
                $('#my-mobile-phone').css('color', 'grey');
                $('.data-bundle-confirmation-password').css('display', 'none');
                $('#data-bundle-confirmation-password').val('');
            }
        });

        $('#cancel-button').unbind();

        $('#cancel-button').click(function() {
            userSession.setProduct( undefined );
            $('.radio-button').removeClass('ticked-radio-button');
            $('.data-bundle-beneficiary-mobile').css('display', 'none');
            $('.is-topping-up-own-phone').css('display', 'none');
            $('#cancel-button').text('Cancel');
            $('#cancel-button').css('display', 'none');
            $('#data-bundle-purchase-button').css('display', 'inline-block');
            $('#data-bundle-beneficiary-mobile').prop('placeholder','enter mobile number to topup');
            $('#data-bundle-beneficiary-mobile').val('');
            $('#data-bundle-beneficiary-mobile').prop('disabled', false);
            $('#data-bundle-beneficiary-mobile').css('background-color', 'white');
            $('#my-mobile-phone').css('color', 'grey');
            $('#is-topping-up-own-phone').prop('checked', false);
            $('.data-bundle-confirmation-password').css('display', 'none');
            $('.is-topping-up-own-phone').css('display', 'none');
            $('#data-bundle-purchase-status').val('');
            $('.data-bundle-purchase-status').css('display', 'none');
            $('#pay-by-telecash').attr("checked", false);
            $('#pay-by-airtime').attr("checked", false);
            $('#payment-method').css('display', 'none');
            viewer.maximizeDataBundlePortlet();
            viewer.maximizeAirtimeTransferPortlet();
            viewer.maximizeVoucherRechargePortlet();
        });
    },

    airtimeTransferEventHandlers : function () {

        $('#transfer-amount').focusin(function(){
            viewer.minimizeVoucherRechargePortlet();
        });

        $('#transfer-beneficiary-mobile').focusin(function(){
            viewer.minimizeVoucherRechargePortlet();
        });

        $('#airtime-transfer-portlet-resize-icon').click(function(){

            if ( $('#airtime-transfer-pane').css('display') == 'none' ) {
                viewer.maximizeAirtimeTransferPortlet();
                viewer.minimizeVoucherRechargePortlet();
            } else {
                viewer.minimizeAirtimeTransferPortlet();
            }
        });

        $('#transfer-process-button').unbind();

        $('#transfer-process-button').click(function() {

            $('#airtime-transfer-status').text('');
            $('.airtime-transfer-status').css('display', 'none');

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            var amountToTransfer = $('#transfer-amount').val();
            console.log("Validating transfer amount");
            if ( $('#transfer-amount').val() ) {
                console.log("amount entered : " + $('#transfer-amount').val());
                try {
                    console.log("parsing amount");
                    amountToTransfer = parseMoney($('#transfer-amount').val());
                    console.log("parsed : " + amountToTransfer);
                } catch(e) {
                    console.log("error : " + e);
                    $('#airtime-transfer-status').text($('#transfer-amount').val() + ' is not valid amount.');
                    $('.airtime-transfer-status').css('display', 'block');

                    return;
                }
            } else {
                console.log("Amount not entered.");
                $('#airtime-transfer-status').text('Please enter amount to transfer');
                $('.airtime-transfer-status').css('display', 'block');

                return;
            }

            try {
                console.log("checking if airtime transfer is within range...");
                if ( controller.isAirtimeTransferAmountWithinRange( amountToTransfer ) ) {
                    console.log("setting amount");
                    userSession.setAmount(amountToTransfer);
                    console.log("amount set");

                    $('#airtime-transfer-status').text('');
                    $('.airtime-transfer-status').css('display', 'none');
                    console.log("ack");
                } else {
                    console.log("nack");
                    return;
                }
            } catch ( e ) {
                console.log("error : " + e);
                $('#airtime-transfer-status').text( e );
                $('.airtime-transfer-status').css('display', 'block');

                return;
            }

            console.log("checking beneficiary mobile");
            if ( $('#transfer-beneficiary-mobile').val() ) {

                if (isValidMobileNumber( $('#transfer-beneficiary-mobile').val() ) ) {

                    if (toStandardMobileNumberFormat( $('#transfer-beneficiary-mobile').val() ) ==
                            userSession.getUserId()) {

                        $('#airtime-transfer-status').text("You can not transfer into your own phone");
                        $('.airtime-transfer-status').css('display', 'block');

                        return;
                    }

                    console.log("ack");
                    userSession.setBeneficiaryMobileNumber(
                        toStandardMobileNumberFormat( $('#transfer-beneficiary-mobile').val()) )
                } else {
                    console.log("nack");
                    $('#airtime-transfer-status').text('Please enter a valid mobile number');
                    $('.airtime-transfer-status').css('display', 'block');

                    return;
                }
            } else {
                console.log("not entered");
                $('#airtime-transfer-status').text('Please enter mobile number to topup');
                $('.airtime-transfer-status').css('display', 'block');
                return;
            }

            console.log("is password visible?");
            if (!  $('#transfer-confirmation-password').is(':visible') ) {
                console.log("no. displaying now");
                viewer.minimizeVoucherRechargePortlet();
                $('.transfer-confirmation-password').css('display', 'block');
                $('#transfer-confirmation-password').focus();
                $('#transfer-cancel-button').text('Cancel');
                $('.transfer-cancel-button').css('display', 'block');

                $('#transfer-cancel-button').click(function(){
                    viewer.airtimeTransferPortlet();
                    viewer.maximizeVoucherRechargePortlet();
                });
                return;
            }

            console.log("is password entered?");
            if (! $('#transfer-confirmation-password') ) {
                console.log("nack");
                $('#airtime-transfer-status').text('Please enter password to confirm transfer.');
                $('.airtime-transfer-status').css('display', 'block');

                return;
            }

            console.log("is password valid?");
            console.log("password : " + userSession.getUserPassword() + ", entered : " +
                            $('#transfer-confirmation-password').val());
            if ( ! isValidPassword( $('#transfer-confirmation-password').val() ) ) {
                console.log("nack");
                $('#airtime-transfer-status').text('Please enter a valid password.');
                $('.airtime-transfer-status').css('display', 'block');

                return;

            } else if ( $('#transfer-confirmation-password').val() != userSession.getUserPassword()) {
                console.log("password wrong ");
                $('#airtime-transfer-status').text('Wrong password. Please enter password again.');
                $('#transfer-confirmation-password').val('');
                $('.airtime-transfer-status').css('display', 'block');
                $('#transfer-confirmation-password').focus();
                return;
            } else {
                console.log("recharging ... ");
                $('#airtime-transfer-status').text('');
                $('.airtime-transfer-status').css('display', 'none');

                viewer.progressBarVisible(true,
                        "Transferring airtime to " +
                        toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber()));

                $('#progress-cancel-button').click(function () {

                    viewer.progressBarVisible(false);
                    $('#airtime-transfer-status').text('Bundle purchase cancelled.');
                    $('.airtime-transfer-status').css('display', 'block');
                });

                controller.processAirtimeTransferRequest();
            }
        });
    },

    processAirtimeTransferRequest : function() {

        var url =  HTTP_URL +
            "/billing-platform?" +
            "service-command=airtime-transfer" +
            "&mobile-number=" + userSession.getUserId() +
            "&beneficiary-id=" + userSession.getBeneficiaryMobileNumber() +
            "&amount=" + userSession.getAmount();

        var promise = $.getJSON(url, function( response ) {
            controller.displayResponse (
                true,
                response,
                $('#airtime-transfer-status'), $('.airtime-transfer-status'),
                $('#transfer-process-button'), $('#transfer-cancel-button') );
        });

        promise.fail( function( xmlHttpRequest, status, error ) {
            // David1
            controller.displayResponse (
                    false,
                    "abort" == status ? "Airtime transfer timed out." : xmlHttpRequest.responseText,
                    $('#airtime-transfer-status'), $('.airtime-transfer-status'),
                    $('#transfer-process-button'), $('#transfer-cancel-button') );

        });

        setTimeout( function() { promise.abort(); }, HTTP_TIMEOUT );
    },

    processAirtimeTransferResponse : function( response ) {

        $('#data-bundle-purchase-status').text( response );
        $('.data-bundle-purchase-status').css('display', 'block');
        $('#data-bundle-beneficiary-mobile').prop('placeholder','enter mobile number to topup');
        $('#data-bundle-beneficiary-mobile').val('');
        $('#data-bundle-beneficiary-mobile').prop('disabled', false);
        $('#data-bundle-beneficiary-mobile').css('display', 'none');
        $('.is-topping-up-own-phone').css('display', 'none');
        $('#data-bundle-beneficiary-mobile').css('background-color', 'white');
        $('#my-mobile-phone').css('color', 'grey');
        $('.data-bundle-confirmation-password').css('display', 'none');
        $('#data-bundle-confirmation-password').val('');

        $('#data-bundle-purchase-button').css('display', 'none');
        $('#cancel-button').text('Close');
        viewer.progressBarVisible( false );
    },

    processVoucherRechargeRequest : function() {

        var url =  HTTP_URL +
            "/billing-platform?" +
            "service-command=voucher-recharge" +
            "&mobile-number=" + userSession.getUserId() +
            "&beneficiary-id=" + userSession.getBeneficiaryMobileNumber() +
            "&recharge-voucher=" + userSession.rechargeVoucher;

        var promise = $.getJSON(url, function( response ) {
            controller.displayResponse (
                true,
                response,
                $('#voucher-recharge-status'), $('.voucher-recharge-status'),
                $('#voucher-recharge-process-button'), $('#voucher-recharge-cancel-button') );
        });

        promise.fail( function( xmlHttpRequest, status, error ) {

            controller.displayResponse (
                false,
                    "abort" == status ? "Airtime transfer timed out." : xmlHttpRequest.responseText,
                $('#voucher-recharge-status'), $('.voucher-recharge-status'),
                $('#voucher-recharge-process-button'), $('#voucher-recharge-cancel-button') );

        });

        setTimeout( function() { promise.abort(); }, HTTP_TIMEOUT );
    },

    isAirtimeTransferAmountValid : function() {

        try {

                if ( ! $('#transfer-amount').val() ) {

                    $('#transfer-amount').attr('placeholder', "Enter amount");
                    $('#transfer-amount').val('');

                    $('#airtime-transfer-status').val('Please enter amount to transfer.');
                    $('.airtime-transfer-status').css('display', 'block');

                    return false;
                }

                if (    (! isBelowMinimum(amountToTransfer))
                    && (! isAboveMaximum(amountToTransfer))
                    && (! isMaximumLimitReached( amountToTransfer ))
                    && isValidMobileNumber( $('#mobileNumberToTransferTo').val() ) ) {



                    userSession.getServiceCommand().serviceCommand =
                        "&transferAmount=" + userSession.getAmount() +
                        "&airtimeTransferBeneficiary=" + userSession.getBeneficiaryMobileNumber();

                    userSession.setState("AIRTIME_TRANSFER_CONFIRMATION");
                    userSession.setProduct( userSession.getAmount() );
                    serviceCommandLauncher( "Confirm that you want to transfer " + formatMoney( userSession.getAmount() ) +
                        " to " + toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber()));
                }
            } catch(e) {

                $('#amountToTransfer').attr('placeholder', e.message);
                $('#amountToTransfer').val('');
            }

    },

    isAirtimeTransferAmountWithinRange : function ( amountToTransfer ) {

        return    ( ! controller.isAirtimeTransferAmountBelowMinimum( amountToTransfer ) )
             && ( ! controller.isAirtimeTransferAboveMaximum( amountToTransfer ) )
             && ( ! controller.isAirtimeTransferMaximumLimitReached( amountToTransfer ));
     },

    isAirtimeTransferAmountBelowMinimum : function  ( amountToTransfer ) {
        console.log("is amount below minimum : " + amountToTransfer);
        if ( parseFloat(amountToTransfer) < parseMoney("0.25") ) {
            throw new Error(
                    'Minimum transfer amount is ' +
                    formatMoney("0.25"));
        } else {
            return false;
        }
    },

    isAirtimeTransferAboveMaximum : function ( amountToTransfer ) {
        console.log("is amount above maximum : " + amountToTransfer);
        if ( parseFloat(amountToTransfer) > parseMoney("100.00") ) {
            throw new Error(
                    'Maximum transfer amount is ' +
                    formatMoney("100.00"));
        } else {
            return false;
        }
    },

    isAirtimeTransferMaximumLimitReached : function  ( amountToTransfer ) {
        console.log("is amount above transfer max : " + amountToTransfer + " main account : " + userSession.mainAccountBalance);
        if ( parseFloat(amountToTransfer) > parseMoney( userSession.mainAccountBalance) ) {
            throw new Error('Can not exceed ' + formatMoney(userSession.mainAccountBalance));
        } else {
            return false;
        }
    },

//    isAirtimeTransferBeneficiaryMobileNumberValid : function() {
//
//        if ( ! isValidMobileNumber( $('#mobileNumberToTransferTo').val() ) ) {
//            $('#airtime-transfer-header').text('Invalid mobile number.' );
//            $('#sub-transfer-header').css('background-color', 'red');
//            $('#airtime-transfer-header').css('color', 'white');
//        }
//    },

    voucherRechargeEventHandlers : function () {

        if ( ! userSession.isUserLoggedOn() ) {
            viewer.loginPortletVisible();
        }

        $('#recharge-voucher').focusin(function(){
            viewer.minimizeAirtimeTransferPortlet();
        });

        $('#voucher-recharge-beneficiary-mobile').focusin(function(){
            viewer.minimizeAirtimeTransferPortlet();
        });

        $('#voucher-recharge-portlet-resize-icon').click(function(){

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            if ( $('#voucher-recharge-pane').css('display') == 'none' ) {
                viewer.maximizeVoucherRechargePortlet();
                viewer.minimizeAirtimeTransferPortlet();
            } else {
                viewer.minimizeVoucherRechargePortlet();
            }
        });

        $('#voucher-recharge-process-button').unbind();

        $('#voucher-recharge-process-button').click(function() {

            $('#voucher-recharge-status').text('');
            $('.voucher-recharge-status').css('display', 'none');

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            var rechargeVoucher = $('#recharge-voucher').val();
            console.log("Validating recharge voucher");
            if ( ! $('#recharge-voucher').val() ) {
                console.log("not entered");
                $('#voucher-recharge-status').text('Please enter recharge voucher');
                $('.voucher-recharge-status').css('display', 'block');

                return;
            } else if ( ! isValidRechargeVoucher( rechargeVoucher )) {
                console.log("not valid");
                $('#voucher-recharge-status').text('Invalid voucher. Please enter again.');
                $('.voucher-recharge-status').css('display', 'block');
                $('#recharge-voucher').val('');

                return;
            } else {
                userSession.rechargeVoucher = rechargeVoucher;
            }

            console.log("checking beneficiary mobile");
            if ( $('#voucher-recharge-beneficiary-mobile').val() ) {

                if (isValidMobileNumber( $('#voucher-recharge-beneficiary-mobile').val() ) ) {
                    console.log("ack");
                    userSession.setBeneficiaryMobileNumber(
                        toStandardMobileNumberFormat( $('#voucher-recharge-beneficiary-mobile').val()) );
//                    controller.showConfirmationPassword();
//                    return;
                } else {
                    console.log("nack");
                    $('#voucher-recharge-status').text('Please enter a valid mobile number');
                    $('.voucher-recharge-status').css('display', 'block');

                    return;
                }
            } else {
                console.log("not entered. defaulting to own phone.");
                userSession.setBeneficiaryMobileNumber(userSession.getUserId());
                $('#voucher-recharge-beneficiary-mobile').val(
                    toShortMobileNumberFormat(userSession.getUserId()));
                controller.showConfirmationPassword();
                return;
            }

            console.log("is password entered?");
            if (! $('#voucher-recharge-confirmation-password').val() ) {
                console.log("nack");
                controller.showConfirmationPassword();
                $('#voucher-recharge-status').text('Please enter password to confirm transfer.');
                $('.voucher-recharge-status').css('display', 'block');

                return;
            }

            console.log("is password valid?");

            if ( ! isValidPassword( $('#voucher-recharge-confirmation-password').val() ) ) {
                console.log("nack");
                $('#voucher-recharge-status').text('Please enter a valid password.');
                $('.voucher-recharge-status').css('display', 'block');

                return;

            } else if ( $('#voucher-recharge-confirmation-password').val() != userSession.getUserPassword()) {
                console.log("password wrong ");
                $('#voucher-recharge-status').text('Wrong password. Please enter password again.');
                $('#voucher-recharge-confirmation-password').val('');
                $('.voucher-recharge-status').css('display', 'block');
                $('#voucher-recharge-confirmation-password').focus();
                return;
            } else {
                console.log("recharging ... ");
                $('#voucher-recharge-status').text('');
                $('.voucher-recharge-status').css('display', 'none');

                viewer.progressBarVisible(true,
                        "Recharging " +
                        toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber()));

                $('#progress-cancel-button').click(function () {

                    viewer.progressBarVisible(false);
                    $('#voucher-recharge-status').text('Voucher recharge cancelled.');
                    $('.voucher-recharge-status').css('display', 'block');
                });

                controller.processVoucherRechargeRequest();
            }
        });
    },

    showConfirmationPassword : function() {
        console.log("is password visible?");
        if (!  $('#voucher-recharge-confirmation-password').is(':visible') ) {
            console.log("no. displaying now");
            $('.voucher-recharge-confirmation-password').css('display', 'block');
            $('#voucher-recharge-confirmation-password').focus();

            $('#voucher-recharge-cancel-button').text('Cancel');
            $('#voucher-recharge-cancel-button').css('display', 'inline-block');
            $('#voucher-recharge-process-button').css('display', 'inline-block');
            $('#voucher-recharge-cancel-button').click(function(){
                viewer.voucherRechargePortlet();
                viewer.maximizeAirtimeTransferPortlet();
            });
            return;
        }
    },

    accountListing : function(startFrom, pageSize ) {

        var url =  HTTP_URL +
            "/billing-platform?" +
                "service-command=get-mobile-account-list&mobile-number=" + userSession.getUserId();

        $.getJSON(url, function( data ) {
            var dataSet = controller.toAccountsArray(data);
            controller.accountListingTable( dataSet );
        });
    },

    dataBundleOfferListing : function(startFrom, pageSize ) {

        var url =  HTTP_URL +
            "/billing-platform?" +
            "service-command=data-bundle-price-list";

        $.getJSON(url, function( data ) {
            var dataSet = controller.toDataBundleOfferArray(data);
            controller.dataBundleOfferListingTable( dataSet );
        });
    },

    accountListingTable : function( dataSet  ) {

//        var table = undefined;
//
//        var isInit = false;
//        try {
//            isInit = $.fn.dataTable.isDataTable('#account-list');
//        } catch(e) {
//            console.log("Not initialised yet.");
//        }
//
//        if ( isInit ) {
//            table = $('#account-list').dataTable();
//        } else {

            if (subscriberPackage != undefined ) {
                $('#wallet-header').text( subscriberPackage + ( dataSet.length > 1 ? ' WALLETS' : ' WALLET') );
            }

            $('#account-listing').empty();

            $('#account-listing')
             .append($('<table cellpadding="0" cellspacing="0" border="0" class="display" id="account-list"></table>'));
        var table = $('#account-list').dataTable({
                "destroy" : true,
                "data": dataSet,
                "aoColumns": [
                    { "sWidth": "50%" },
                    { "sWidth": "25%" },
                    { "sWidth": "25%" }
                ],
                "bSort": false,
                "bLengthChange": false,
                "bPaginate" : false,
                "info":     false,
                "bFilter": false,
                "columns": [
                    { "title": "Account Name" },
                    { "title": "Balance" },
                    { "title": "Expiry Date" }
                ],
                fnDrawCallback: function () {
                    $("#account-list thead").remove();
                    $(".dataTable").css('border-bottom', '0px solid #111111');
                    $('#account-list').css('width', 'calc(100% - 32px)');
                    $('#account-list').css('margin-left', '18px');
                    $('#account-list').css('margin-right', '14px');
                    $('#account-list_info').css('padding-left', '28px');
                    $('#account-list_paginate').css('padding-right', '14px');
                    $('#account-list tr td:nth-child(1)').css('color','#6a7480');
                    $('#account-list tr td:nth-child(3)').css('color','#6a7480');
                    $('#account-list tr td:nth-child(3)').css('text-align','right');
                }
            });

            var buttons = $('<ul class="standard-ul"></ul>')
                .append($('<li class="standard-li" style="padding-right: 10px;"><button id="account-listing-refresh-button" class="button" type="button">Refresh</button></li>'));

            $('#account-listing').append( $('<div class="button-pane clear-fix" style="border-left: 0px;border-right: 0px;">').append( buttons) );

            $('#account-listing-portlet-resize-icon').click(function(){

                if ( ! userSession.isUserLoggedOn() ) {
                    viewer.loginPortletVisible();
                }

                if ( $('#account-listing').css('display') == 'none' ) {
                    viewer.maximizeAccountListingPortlet();
                } else {
                    viewer.minimizeAccountListingPortlet();
                }
            });

            controller.accountListingRefreshButtonHandler();
//        }
        return table;
    },

    dataBundleOfferListingTable : function( dataSet  ) {

        var table = undefined;

        var isInit = false;
        try {
            isInit = $.fn.dataTable.isDataTable('#data-bundle-offer-listing')
        } catch(e) {
            console.log("Not initialised yet.");
        }

        if ( isInit ) {
            table = $('#data-bundle-offer-listing').dataTable();
        } else {

            var portlet = $('#data-bundle-offer-listing');
            portlet.empty();
//            portlet.append('<div class="data-bundle-purchase-status" style="padding-left: 14px;display: none;"><span id="data-bundle-purchase-status"></span></div>');
            portlet.append('<div class="data-bundle-purchase-status" style="display: none;"><img id="data-bundle-purchase-status-icon" src="../img/nack.jpeg" class="nack">&nbsp;<span id="data-bundle-purchase-status"></span></div>');

            portlet.append($('<table cellpadding="0" cellspacing="0" border="0" class="display" id="data-bundle-offer-list"></table>'));
            table = $('#data-bundle-offer-list').dataTable({
                "data": dataSet,
                "aoColumns": [
                    { "sWidth": "50%" },
                    { "sWidth": "20%" },
                    { "sWidth": "25%" },
                    { "sWidth": "5%" }
                ],
                "bSort": false,
                "bLengthChange": false,
                "bPaginate" : false,
                "info":     false,
                "bFilter": false,
                "columns": [
                    { "title": "Account Name" },
                    { "title": "Balance" },
                    { "title": "Expiry Date" },
                    { "title": "Select" }
                ],

                fnDrawCallback: function () {
                    $("#data-bundle-offer-list thead").remove();
                    $(".dataTable").css('border-bottom', '0px solid #111111');
                    $('#data-bundle-offer-list').css('width', 'calc(100% - 32px)');
                    $('#data-bundle-offer-list').css('margin-left', '18px');
                    $('#data-bundle-offer-list').css('margin-right', '14px');
                    $('#data-bundle-offer-list_info').css('padding-left', '28px');
                    $('#data-bundle-offer-list_paginate').css('padding-right', '14px');
                    $('#data-bundle-offer-list tr td:nth-child(1)').css('color','#6a7480');
                    $('#data-bundle-offer-list tr td:nth-child(3)').css('color','#6a7480');
                    $('#data-bundle-offer-list tr td:nth-child(3)').css('text-align','right');
                    $('#data-bundle-offer-list tr td').css('padding', '8px 10px 4px 10px');
                    $('#data-bundle-offer-list tr td:nth-child(4)').css('padding', '4px 10px 4px 10px');
                }
            });
            portlet.append('<div id="payment-method" style="width: 100%; display: none;align-content: center;padding-top: 8px;padding-bottom: 8px;padding-left: 30px; border-top: solid 0.1px;">Payment Method: <input id="pay-by-airtime" type="radio" name="payment-method" value="pay-by-airtime" style="margin-left: 10px;margin-right: 10px;">Convert Airtime<input id="pay-by-telecash" type="radio" name="payment-method" value="pay-by-telecash" style="margin-left: 40px;margin-right: 10px;">Pay by Telecash</div>');
            portlet.append('<div class="data-bundle-beneficiary-mobile" style="display: none;"><input id="data-bundle-beneficiary-mobile" type="text" placeholder="enter mobile number to topup" style="line-height: 19px;"></div>');
            portlet.append('<div class="is-topping-up-own-phone" style="display: none;"><input id="is-topping-up-own-phone" type="checkbox" style="top: -4px;">  <span id="my-mobile-phone">topup my phone</span></div>');
            portlet.append('<div class="data-bundle-confirmation-password" style="display: none;"><input id="data-bundle-confirmation-password" type="password" placeholder="enter your password to confirm"></div>');

            var buttons = $('<ul class="standard-ul"></ul>')
                .append($('<li class="standard-li"><button id="cancel-button" class="button" type="button" style="display: none;">Cancel</button></li>'))
                .append($('<li class="standard-li"><button id="data-bundle-purchase-button" class="button" type="button">Buy</button></li>'));

            portlet.append( $('<div class="button-pane clear-fix" style="border-left: 0px;border-right: 0px;">').append( buttons) );

            userSession.setProduct( undefined );
            controller.dataBundleEventHandlers();
        }

        $('#data-bundle-purchase-portlet-resize-icon').click(function(){

            if ( ! userSession.isUserLoggedOn() ) {
                viewer.loginPortletVisible();
            }

            if ( $('#data-bundle-offer-listing').css('display') == 'none' ) {
                viewer.maximizeDataBundlePortlet();
                viewer.minimizeSubscriberListingPortlet();
                viewer.minimizeAirtimeTransferPortlet();
                viewer.minimizeVoucherRechargePortlet();
            } else {
                viewer.minimizeDataBundlePortlet();
            }
        });

        return table;
    },

    accountListingRefreshButtonHandler : function () {

        $('#account-listing-refresh-button').unbind();

        $('#account-listing-refresh-button').click(function(){
            viewer.accountListPortletVisible( true );
        });
    },

    subscriberListing : function(startFrom, pageSize ) {

        var url =  HTTP_URL +
            "/user-account-manager?service-command=subscriber-listing&" +
            "start-from=" + startFrom +
            "&page-size=" + pageSize;

        $.getJSON(url, function( data ) {

//            var portlet = $('#subscriber-list-portlet');
//            portlet.css('padding-top', '8px');
//            portlet.empty();
//            portlet.append($('<div class="widget-head">')
//                .append($('<div id="registration-portlet-resize-icon"></div>'))
//                .append($('<h3 class="portlet-header">Subscribers</h3>')));
//
//            var listing = $('<div id="registration-pane" class="boxed-container">');
//            listing.append('<div id="register-listing"><table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table></div>');
//            portlet.append( listing );
//
//            alert("listing: " + listing.val());

//        content.append('<div class="register-status" style="display: none;"><img id="register-status-icon" src="../img/nack.jpeg" class="nack">&nbsp;<span id="register-status"></span></div>');


            controller.displaySubscriberList( data );
//            controller.subscriberMap( data );
            //        $('.service-command-panel').css('display','inline');
//            alert("### : " + $('#registration-pane').val());


        });
    },

    /*  ################################ Dynamically change display length
     var oTable;

     $(document).ready(function() {
     $('.something').click( function () {
     oTable.fnSetDisplayLength = 50;
     oTable.fnDraw();
     });

     oTable = $('#example').dataTable();
     });
     */
    registerTable : function( ) {

//        var table = undefined;
//
//        if ($.fn.dataTable.isDataTable('#subscriber-list')) {
//            table = $('#subscriber-list').dataTable();
//            table.fnReloadAjax( JSON.stringify( subscriberList) );
//        } else {

          var subscribersTable
             = $('#subscriber-list').dataTable({
                "destroy": true,
                "data": subscriberList,
                "aoColumns": [
                    { "sWidth": "20%" },
                    { "sWidth": "35%" },
                    { "sWidth": "25%" },
                    { "sWidth": "20%" }
                ],
                "bSort": false,
                "bLengthChange": false,
                "pagingType": "simple",
                "language": {
                    "info": "_START_ to _END_ of _TOTAL_",
                    "infoEmpty": "0 entries"
                },
                "bFilter": false,
                "columns": [
                    { "title": "Mobile Number" },
                    { "title": "Surname" },
                    { "title": "Device" },
                    { "title": "Status" }
                ],
                fnDrawCallback: function () {

                    $("#subscriber-list thead").remove();
                    $(".dataTable").css('border-bottom', '0px solid #111111');
                    $('#subscriber-list').css('width', '460px');
                    //                    $('#transaction-table').css('width', 'calc(100% - 32px)');
                    $('#subscriber-list').css('margin-left', '5px');
                    $('#subscriber-list').css('margin-right', '5px');
//                    $('#subscriber-list').css('height', '800px');
                    $('#subscriber-list_info').css('padding-left', '28px');
                    $('#subscriber-list_info').css('background-color', '#f2f2f2');
                    $('#subscriber-list_info').css('width', '50%');
                    $('#subscriber-list_info').css('height', '34px');
                    $('#subscriber-list_info').css('padding-top', '10px');
                    $('#subscriber-list_info').css('color', 'grey');
                    $('#subscriber-list_paginate').css('width', '50%');
                    $('#subscriber-list_paginate').css('padding-right', '14px');
                    $('#subscriber-list_paginate').css('background-color', '#f2f2f2');
                    $('#subscriber-list_paginate').css('color', 'grey');
                    var h = parseInt($('#contentCol').css('height'));
                    var sh = parseInt($('#subscriber-list-portlet').css('height'));
                    var dh = parseInt($('#data-bundle-purchase-portlet').css('height'));
                    var ah = parseInt($('#airtime-transfer-portlet').css('height'));

//                    $('#data-bundle-purchase-portlet').css('padding-top',
//                        $('#contentCol').css('height')
//                            - $('#subscriber-list-portlet').css('height')
//                            - $('#data-bundle-purchase-portlet').css('height')
//                            - $('#airtime-transfer-portlet').css('height')
//                            - $('#voucher-recharge-portlet').css('height') );
                }

                /*
                 fnDrawCallback: function () {
                 $("#transaction-table thead").remove();
                 $(".dataTable").css('border-bottom', '0px solid #111111');
                 $('#transaction-table').css('width', '460px');
                 //                    $('#transaction-table').css('width', 'calc(100% - 32px)');
                 $('#transaction-table').css('margin-left', '5px');
                 $('#transaction-table').css('margin-right', '5px');
                 $('#transaction-table_info').css('padding-left', '28px');
                 $('#transaction-table_info').css('background-color', '#f2f2f2');
                 $('#transaction-table_info').css('width', '50%');
                 $('#transaction-table_info').css('height', '34px');
                 $('#transaction-table_info').css('padding-top', '10px');
                 $('#transaction-table_info').css('color', 'grey');
                 $('#transaction-table_paginate').css('width', '50%');
                 $('#transaction-table_paginate').css('padding-right', '14px');
                 $('#transaction-table_paginate').css('background-color', '#f2f2f2');
                 $('#transaction-table_paginate').css('color', 'grey');
                 }
                 */

            });
//        }
        var selectedMobileNumber;

        $('#subscriber-list tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
            } else {
                subscribersTable.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                selectedMobileNumber = $(this).find("td:first").html();
//                alert( " mobile : " + selectedMobileNumber );
//                alert(" obj : " + subscribersMap[ selectedMobileNumber ] );
//                var sub = subscribersMap[ selectedMobileNumber ];
//                alert(" obj subscriber : " + sub.mobileNumber );

                viewer.displaySubscriber( subscribersMap[ toStandardMobileNumberFormat( selectedMobileNumber ) ] );
                viewer.maximizeRegistrationPortlet();
                viewer.minimizeAccountListingPortlet();
                viewer.transactionHistoryVisible( true, toStandardMobileNumberFormat( selectedMobileNumber )  );
            }
        } );

        $('#register-subscriber-button').click(function(){
            serviceCommand = SERVICE_COMMANDS.REGISTER_SUBSCRIBER;
            controller.registrationForm(undefined);
        });

        $('#edit-button').click(function(){
            serviceCommand = SERVICE_COMMANDS.EDIT_SUBSCRIBER;
            controller.registrationForm( selectedMobileNumber );
        });

        $('#delete-button').click( function () {
            serviceCommand = SERVICE_COMMANDS.DELETE_SUBSCRIBER;
            controller.registrationForm(selectedMobileNumber );
        } );

        $('#subscriber-list-portlet-resize-icon').click(function(){

            if ( $('#subscriber-list-pane').css('display') == 'none' ) {
                viewer.maximizeSubscriberListingPortlet();
                viewer.minimizeDataBundlePortlet();
                viewer.minimizeAirtimeTransferPortlet();
                viewer.minimizeVoucherRechargePortlet();
            } else {
                viewer.minimizeSubscriberListingPortlet();
            }
        });
//        return table;
    },

    editEventListener : function() {

        $('#first-name').focusin(function(){
            controller.inEditMode();
        });
        $('#last-name').focusin(function(){
            controller.inEditMode();
        });
        $('#email-address').focusin(function(){
            controller.inEditMode();
        });
        $('#security-question').focusin(function(){
            controller.inEditMode();
        });
        $('#security-answer').focusin(function(){
            controller.inEditMode();
        });
        $('#sms-capable').focusin(function(){
            controller.inEditMode();
        });
    },

    inEditMode : function() {
        $('#register-process-button').text('Save');
        $('#register-process-button').unbind();
        $('#register-process-button').click(function(){
            var subscriber = controller.parseRegistrationForm();
            if ( subscriber != null && controller.hasSubscriberInfoChanged( JSON.parse(subscriber) )) {
                controller.registerSubscriber( JSON.parse(subscriber), SERVICE_COMMANDS.MODIFY_SUBSCRIBER )
            } else {
                viewer.displayError(
                    $('.register-status'),
                    $('#register-status'),
                    $('#register-status-icon'),
                    "Subscriber information hasn't changed.");
            }
//            viewer.registrationPortlet();
        });

        $('#register-cancel-button').css('display', 'inline-block');
        $('#register-cancel-button').text('Cancel');
        $('#register-cancel-button').click(function(){
            viewer.registrationPortlet();
            viewer.deselectSubscriber();
        });
    },

    hasSubscriberInfoChanged : function( subscriber ) {
        var currentSubscriber = subscribersMap [ toStandardMobileNumberFormat ( subscriber.mobileNumber ) ];
        return ! (   currentSubscriber.firstName == subscriber.firstName
                  && currentSubscriber.lastName == subscriber.lastName
                  && currentSubscriber.emailAddress == subscriber.emailAddress
                  && currentSubscriber.securityQuestion == subscriber.securityQuestion
                  && currentSubscriber.securityAnswer == subscriber.securityAnswer
                )
    },

    saveRegistration : function() {
        var url =  HTTP_URL +
            "user-account-manager?service-command=modify-subscriber" +
            "&mobile-number=" + userSession.getUserId() +
            "&start-from=" + startFrom +
            "&page-size=" + pageSize;

        $.getJSON(url, function( data ) {
            $('#register-process-button').text('Close');
            $('#register-process-button').unbind();
            $('#register-process-button').click(function(){
                viewer.registrationPortlet();
            });
        });
    },

    deleteSubscriber : function( mobileNumber ) {

        var url =  HTTP_URL +
            "user-account-manager?service-command=deactivate-user" +
            "&mobile-number=" + mobileNumber;

        var promise = $.getJSON(url, function( data ) {

            viewer.displayInfo(
                $('.register-status'),
                $('#register-status'),
                $('#register-status-icon'),
                    data.firstName + " " + data.lastName + " de-activated.");

            $('#register-process-button').text('Close');
            $('#register-process-button').unbind();
            $('#register-process-button').click(function(){
                viewer.registrationPortlet();
                viewer.deselectSubscriber();
            });
        });

        promise.fail(function(xhr, status, error) {

            viewer.displayError( $('.register-status'),
                $('#register-status'),
                $('#register-status-icon'),
                    xhr.responseText != "" ?
                    xhr.responseText
                    : "TeleVas is offline. Please keep trying.");

            $('.register-status').css('display', 'block');
            $('#register-cancel-button').css('display', 'inline-block');
        });
    },

    resendActivationCode : function( mobileNumber ) {

        var url =  HTTP_URL +
            "user-account-manager?service-command=reset-password" +
            "&mobile-number=" + mobileNumber;

        var promise = $.getJSON(url, function( data ) {

            viewer.displayInfo(
                $('.register-status'),
                $('#register-status'),
                $('#register-status-icon'),
                    "Activate code sent to " +
                    ( data.notificationAgent.toLowerCase() == 'sms'
                           ? toShortMobileNumberFormat( data.mobileNumber ) : data.emailAddress ) + ".");

            $('#register-process-button').text('Close');
            $('#register-process-button').unbind();
            $('#register-process-button').click(function(){
                viewer.registrationPortlet();
                viewer.deselectSubscriber();
            });
        });

        promise.fail(function(xhr, status, error) {

            viewer.displayError( $('.register-status'),
                $('#register-status'),
                $('#register-status-icon'),
                    xhr.responseText != "" ?
                    xhr.responseText
                    : "TeleVas is offline. Please keep trying.");

            $('.register-status').css('display', 'block');
            $('#register-cancel-button').css('display', 'inline-block');
        });
    },

    transactionListing : function(startFrom, pageSize, sourceMobileNumber ) {

        var url =  HTTP_URL +
            "/billing-platform?service-command=transaction-history" +
            "&mobile-number=" + sourceMobileNumber +
//            "&source-mobile-number=" + sourceMobileNumber +
            "&start-from=" + startFrom +
            "&page-size=" + pageSize;

        $.getJSON(url, function( data ) {

//            var portlet = $('#subscriber-list-portlet');
//            portlet.css('padding-top', '8px');
//            portlet.empty();
//            portlet.append($('<div class="widget-head">')
//                .append($('<div id="registration-portlet-resize-icon"></div>'))
//                .append($('<h3 class="portlet-header">Subscribers</h3>')));
//
//            var listing = $('<div id="registration-pane" class="boxed-container">');
//            listing.append('<div id="register-listing"><table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table></div>');
//            portlet.append( listing );
//
//            alert("listing: " + listing.val());

//        content.append('<div class="register-status" style="display: none;"><img id="register-status-icon" src="../img/nack.jpeg" class="nack">&nbsp;<span id="register-status"></span></div>');


            var dataSet = controller.toTransactionHistoryArray( data );
//            controller.subscriberMap( data );
            //        $('.service-command-panel').css('display','inline');
//            alert("### : " + $('#registration-pane').val());

            controller.transactionTable( dataSet );
        });
    },

    transactionTable : function( dataSet  ) {


//        if ( $.fn.dataTable.isDataTable( '#transaction-table' ) ) {
//            table = $('#transaction-table').dataTable();
//        } else {

        var table = $('#transaction-table').dataTable({
                "destroy": true,
                "data": dataSet,
                "aoColumns": [
                    { "sWidth": "20%" },
                    { "sWidth": "40%" },
                    { "sWidth": "20%" },
                    { "sWidth": "20%" }
                ],
                "bLengthChange": false,
                "pagingType": "simple",
                "language": {
                    "info": "_START_ to _END_ of _TOTAL_",
                    "infoEmpty": "0 entries"
                },
                "bFilter": false,
                "pageLength" : 5,
                "columns": [
                    { "title": "Transaction Date" },
                    { "title": "Narrative" },
                    { "title": "Transaction Value" },
                    { "title": "Balance" }
                ],
                fnDrawCallback: function () {
                    $("#transaction-table thead").remove();
                    $(".dataTable").css('border-bottom', '0px solid #111111');
                    $('#transaction-table').css('width', '460px');
                    $('#transaction-table').css('margin-left', '5px');
                    $('#transaction-table').css('margin-right', '5px');
                    $('#transaction-table_info').css('padding-left', '28px');
                    $('#transaction-table_info').css('background-color', '#f2f2f2');
                    $('#transaction-table_info').css('width', '50%');
                    $('#transaction-table_info').css('height', '34px');
                    $('#transaction-table_info').css('padding-top', '10px');
                    $('#transaction-table_info').css('color', 'grey');
                    $('#transaction-table_paginate').css('width', '50%');
                    $('#transaction-table_paginate').css('padding-right', '14px');
                    $('#transaction-table_paginate').css('background-color', '#f2f2f2');
                    $('#transaction-table_paginate').css('color', 'grey');
                }
            });
//        }
        return table;
    },

    toAccountsArray : function( data ) {

        subscriberPackage = undefined;

        var dataSet = [];
        $.each(data, function(index, account) {

            dataSet.push([ account.balanceName.normalizedValue,
                           "expires " + account.expiryDate.dayOfMonth + " " + MONTHS [ account.expiryDate.month ] +
                               " " + account.expiryDate.year,
                           formatCurrencyOrData( account.balance, account.balanceName.unitOfMeasure )]);

            subscriberPackage = account.subscriberPackage;

            if (account.balanceName.systemValue == 'Core') {
                userSession.mainAccountBalance = account.balance;
            }
        });

        return dataSet;
    },

    toDataBundleOfferArray : function( data ) {

        var dataSet = [];
        $.each(data, function(index, dataBundle) {
            dataSet.push([ dataBundle.shortDescription,
                           "expires after " + dataBundle.windowSize + " days",
                           formatCurrencyOrData( dataBundle.debit, "money" ),
                           "<div id='" + index + "' description='" + dataBundle.shortDescription + "'" +
                            " amount='" + dataBundle.debit + "' class='radio-button'></div>"]);

            dataBundles[ dataBundle.bundleType ] = dataBundle.shortDescription;
        });

        return dataSet;
    },

    toTransactionHistoryArray : function( history ) {
        var dataSet = [];
        $.each(history, function(index, item) {

            var productDescription = "";
            if ( item.transactionType == "DataBundlePurchase" ) {
                productDescription = dataBundles[ item.productCode];
            } else if ( item.transactionType == "AirtimeTransfer" ) {
                productDescription = formatCurrencyOrData(item.amount, "money") + " Airtime Transfer";
            } else {
                productDescription = item.productCode;
            }

            var narrative = "";
            var balance = "";
            if ( item.sourceId != userSession.getUserId() ) {
                narrative = "From " + toShortMobileNumberFormat(item.sourceId);
                balance = item.beneficiaryBalance;
            } else if ( item.destinationId == userSession.getUserId()) {
                narrative = "For my phone";
                balance = item.sourceBalance;
            } else {
                "For " + toShortMobileNumberFormat(item.destinationId);
                balance = item.sourceBalance;
            }

            dataSet.push( [
                controller.shortDate (item.transactionDate ),
                productDescription,
                narrative,
                formatCurrencyOrData( balance, 'money' ) ] );
        });

        return dataSet;
    },

    displaySubscriberList : function( data ) {

        subscriberList = [];
        subscribersMap = new Object();

        $.each(data, function(index, user) {

            subscriberList.push( [ toShortMobileNumberFormat(user.mobileNumber),
                                   user.firstName + " " + user.surname,
                                   user.notificationAgent.toLowerCase(),
                                   user.status  ]);

            subscribersMap[ user.mobileNumber ] = user;
        });

        controller.registerTable( );
    },

    updateSubscriberList : function( subscriber ) {

        subscriberList.insert(0, [  toShortMobileNumberFormat(subscriber.mobileNumber),
                                    subscriber.fullName,
                                    subscriber.notificationAgent.toLowerCase(),
                                    "enterActivationCode"  ]);
        controller.registerTable( );
    },

    shortDate : function( date ) {
        // 2014-10-23 16:41:16
        // Oct 23, 2014 2:27:36 PM
//        console.log( date + " d " + date.substring(8, 10) + " m " + date.substring(5, 7) + " y " + date.substring(2, 4));

        return date.substring(4, 6) + " " + date.substring(0, 3)  + " " + date.substring(10, 12);
    },

    userSession : function ( user ) {

        userSession.setIsUserLoggedOn( true );
        userSession.setUserId( user.mobileNumber );
        userSession.setFirstName( user.firstName);
        userSession.setLastName( user.lastName );
        userSession.setUserEmail( user.emailAddress );
        userSession.setSecurityQuestion( user.securityQuestion );
        userSession.setSecurityAnswer( user.securityAnswer );
        userSession.setNotificationAgent( user.notificationAgent );
        userSession.setUserRole( user.role );
        userSession.setUserPassword( user.password );
    }
};