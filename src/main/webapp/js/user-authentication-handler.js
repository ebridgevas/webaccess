/**
 * @author david@ebridgevas.com
 *
 */

var newPassword = null;
var command = null;
var userId = null;
var idType = null;

function userIdInputHandler(aUserId, userAction, payload) {

    $('#password').css('display','none');
    $('#recover-security-token').css('display','none');
    $('#loginButton').text('Log In');

    if (userAction == "ENTER_ACTIVATION_CODE") {
        $('#userId').val( aUserId );
        userId = aUserId;
        idType = parseIdType(aUserId);
        activationCodeVerification(payload, userId, "EMAIL_ADDRESS");

    }  else if (userAction == "CONFIRM_SET_PASSWORD") {
        $('#userId').val( aUserId );
        $('#userId').css('display','none');
        command = new Object();
        command.userAction = userAction;
        $('#recover-security-token').css('display','none');
        $('#register').css('display','none');
        showPasswordPrompt("Current Password Verification", "Enter Current Password", "Verify Password");
    } else if (userAction == "CONFIRM_USER_DEACTIVATION") {
        $('#userId').val( aUserId );
        command = new Object();
        command.userAction = userAction;
        $('#recover-security-token').css('display','none');
        $('#register').css('display','none');
        showPasswordPrompt("Confirm Deletion", "Enter Your Password", "Confirm");
    }

    $('#loginButton').click(function(e){

        loginButtonHandler();
        return false;
    });
}

function loginButtonHandler() {

    if ( userId == null) {
        userId = $('#userId').val();
        idType = parseIdType(userId);
    }

    if ( idType == null ) {
        $('#userId').attr('placeholder','Enter Valid Mobile Number or Email');
        return;
    }

    if ( command == null) {

        if (idType == "MOBILE_NUMBER") {
            userId = toStandardMobileNumberFormat( userId );
        }

        $.getJSON(
            "/webaccess?service-command=validate-user-id&user-id=" + userId + "&id-type=" + idType,
            function(data) {
                command = data;
                if ( command.userAction == "ENTER_PASSWORD" ) {
                    showPasswordPrompt("Log Into Web Portal", "Enter Password", "Log In");
                } else if ( command.userAction == "ENTER_ACTIVATION_CODE" ) {
                    showActivateCodePrompt("Enter Activation Code");
                } else if ( command.userAction == "REGISTER_USER" ) {
                    showUnRegisteredUserError();
                }
            });
    } else if (command.userAction == "CONFIRM_SET_PASSWORD") {

        var password = $('#password').val();

        $.getJSON("/webaccess?service-command=authenticate-user&user-id=" + userId + "&id-type=" +
            idType + "&password=" + password + "&is-in-session=yes", function(data) {

            if ( data.userAction == "GRANT_ACCESS" ) {
                command.userAction = "SET_PASSWORD";
                showPasswordPrompt("Set New Password", "New Password", "Set Password");
            } else if (  data.userAction == "SET_PASSWORD_RETRY" ) {
                $('#password').val('');
                $('#password').attr('placeholder',data.narrative);
            }
        });
    }  else if (command.userAction == "ENTER_PASSWORD") {

        var password = $('#password').val();

        $.getJSON("/webaccess?service-command=authenticate-user&user-id=" + userId + "&id-type=" +
            idType + "&password=" + password + "&is-in-session=no", function(data) {

            if ( data.userAction == "GRANT_ACCESS" ) {
                document.location.href="portal.html?sessionId=" + data.sessionId +
                    "&id-type=" + idType;
            } else if (  data.userAction == "SET_PASSWORD_RETRY" ) {
                $('#password').val('');
                $('#password').attr('placeholder',data.narrative);
            }
        });
    } else if (command.userAction == "ENTER_ACTIVATION_CODE") {

        /* user action was 'ENTER PASSWORD' when user clicked login button. */
        var activationCode = $('#password').val();

        activationCodeVerification(activationCode, userId, idType);

    } else if ( command.userAction == "SET_PASSWORD" ) {

        /* user action was 'SET PASSWORD' so we now need to prompt for re-entry */
        newPassword = $('#password').val();
        if ( isValidPassword(newPassword) ) {

            $('#password').val('');
            $('#security-panel-title').text('Confirm Password');
            $('#password').attr('placeholder','Enter password again');
            $('#loginButton').text('Confirm Password');
            command.userAction = 'RE_ENTER_PASSWORD';
        }
    } else if ( command.userAction == "RE_ENTER_PASSWORD") {

        if ($('#password').val() == newPassword) {

            $.getJSON(  "/webaccess?service-command=set-user-password" +
                "&user-id=" + userId +
                "&id-type=" + idType +
                "&new-password=" + newPassword, function(data) {

                command = data;

                if ( command.userAction == "GRANT_ACCESS" ) {
                    document.location.href="portal.html?sessionId=" + command.sessionId;
                } else if ( data.narrative == "SET_PASSWORD_RETRY" ) {
                    $('#password').attr('placeholder','Error occurred. Enter New Password.');
                    $('#password').val('');
                    command.userAction = 'SET_PASSWORD';
                }
            });
        } else {
            $('#password').attr('placeholder','Not matching. Enter New Password.');
            $('#password').val('');
            command.userAction = 'SET_PASSWORD';
        }
    } else if ( command.userAction == "CONFIRM_USER_DEACTIVATION") {

//        if ( isValidPassword( $('#password').val() ) ) {
            var userPassword = $('#password').val();
            $.getJSON(  "/webaccess?service-command=deactivate-user" +
                "&user-id=" + userId +
                "&id-type=" + idType +
                "&password=" + userPassword, function(data) {

                command = data;

                if ( command.userAction == "GRANT_ACCESS" ) {
                    document.location.href="index.html";
                } else if ( data.narrative == "SET_PASSWORD_RETRY" ) {
                    $('#password').attr('placeholder','Wrong password. Please retry.');
                    command.userAction = "CONFIRM_USER_DEACTIVATION";
                }
            });
//        } else {
//            $('#password').attr('placeholder','Invalid password. Please retry.');
//            command.userAction = 'CONFIRM_USER_DE_ACTIVATION';
//        }
    }
}

//function isValidPassword( password ) {
//
//    if (password.length < 6) {
//        $('#password').attr('placeholder','Enter 6 or more chars');
//        $('#password').val('');
//        return false;
//    }
//
//    if ( /^[A-Za-z0-9\d=!\-@._*]+$/.test( password ) ) {
//        return true;
//    } else {
//        $('#password').attr('placeholder','Invalid password. Retry');
//        $('#password').val('');
//        return false;
//    }
//}

function parseIdType(userId) {
    if ( isValidMobileNumber( userId ) ) {
        return"MOBILE_NUMBER";
    } else if ( isValidEmailAddress( userId ) ) {
        return "EMAIL_ADDRESS";
    } else {
        return null;
    }
}

function showPasswordPrompt(title, prompt, buttonText) {
    $('#security-panel-title').text(title);
    $('#password').attr('placeholder', prompt);
    $('#password').val('');
    $('#password').css('display','block');
    $('#password').attr('type', 'password');
    $('#recover-security-token').css('display','block');
    $('#loginButton').text( buttonText );
}

function activationCodeVerification(activationCode, userId, idType) {

    $.getJSON(  "/webaccess?service-command=activate-user" +
        "&user-id=" + userId +
        "&id-type=" + idType +
        "&activation-code=" + activationCode, function(data) {

        command = data;

        if ( command.userAction == "SET_PASSWORD" ) {
            $('#recover-security-token').css('display','none');
            $('#register').css('display','none');
            showPasswordPrompt("Set New Password", "Enter New Password", "Set Password");
        } else if ( command.userAction == "ENTER_VALID_ACTIVATION_CODE" ) {
            showActivateCodePrompt("Wrong Activation Code. Enter again.");
        } else if ( command.userAction == "REGISTER_USER" ) {
            showUnRegisteredUserError();
        }
    });
}

function showActivateCodePrompt(prompt) {

    $('#password').attr('placeholder', prompt);
    $('#password').attr('type', 'text');
    $('#password').css('display','block');
    $('#recover-security-token').text('Generate new activation code.');
    $('#recover-security-token').css('display','block');
    $('#loginButton').text('Activate');
    $('#security-panel-title').text('Activate Subscription');
}

function showUnRegisteredUserError() {
    $('#userId').val('');
    $('#userId').attr('placeholder', 'Enter a register mobile or email');
}