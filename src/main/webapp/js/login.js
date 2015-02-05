/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/14
 * Time: 8:19 AM
 * To change this template use File | Settings | File Templates.
 */

var command;

function login() {

    if (! userSession.isUserIdSet() ) {

        if (isValidMobileNumber($('#user-id').val())){

            $.getJSON(

                "/webaccess?service-command=validate-user-id&user-id=" +
                    toStandardMobileNumberFormat($('#user-id').val()) +
                    "&id-type=MOBILE_NUMBER",

                function(data) {
                    command = data;
                    if ( command.userAction == "ENTER_PASSWORD" ) {
                        userSession.setUserId(command.userId);
                        $('#password').css('display', 'block');

                    } else if ( command.userAction == "ENTER_ACTIVATION_CODE" ) {
                        addMessage("Send us back the activation code<br/>" +
                                   "that we sent to your mobile phone via sms.", true);
                    } else if ( command.userAction == "REGISTER_USER" ) {
                        addMessage(toStandardMobileNumberFormat("Mobile number " + $('#message-composer').val()) +
                            "is not registered.<br/>Please visit Telecel offices.", true);
                    }
                });
        } else {
//            $('#user-id').attr('placeholder', 'Enter valid mobile number');
//            $('#user-id').val('');
            addMessage("You sent an invalid mobile number.<br/>Please try again.", true);
        }
    } else {

        if ( command.userAction == "ENTER_PASSWORD" ) {

            if (isPasswordValid($('#password').val())) {

                userSession.setUserPassword( $('#password').val() );

                $.getJSON("/webaccess?service-command=authenticate-user&user-id=" + userSession.getUserId() + "&id-type=MOBILE_NUMBER" +
                    "&password=" + userSession.getUserPassword() + "&is-in-session=no", function(data) {

                    if ( data.userAction == "GRANT_ACCESS" ) {
//                            document.location.href="portal.html?sessionId=" + data.sessionId +
//                                "&id-type=" + idType;
                        userSession.setSessionId( data.sessionId );
                        userSession.setIsUserLoggedOn( true );
                        userSession.firstName = data.firstName;
                        userSession.mobileNumber = data.mobileNumber;
                        userSession.accountType = data.accountType;

                        $('#page-header-text').text("Telecel Web Portal");
                        $('#sub-page-header-text')
                            .text(
                                "for " + userSession.firstName + ", " +
                                         toShortMobileNumberFormat(userSession.mobileNumber ) );

                        getMobileAccountList();
                    } else if (  data.userAction == "SET_PASSWORD_RETRY" ) {
                        $('#password').val('');
                        addMessage(data.narrative, true);
                    }
                });
            } else {
                addMessage("password invalid", true);
                $('#message-composer').val('');
                return false;
            }
        }
    }
}

function isPasswordValid( password ) {
    if (password.length < 6) {;
        addMessage("The password you sent is invalid password<br/>" +
                   "A password can not be less that 6 characters.<br/>" +
                   "Please try again.", true);
        return false;
    }

    if ( /^[A-Za-z0-9\d=!\-@._*]+$/.test( password ) ) {
        return true;
    } else {
        addMessage("The password you sent is invalid password<br/>" +
                   "Please try again.", true);
        return false;
    }
}