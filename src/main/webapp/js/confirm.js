/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/24/14
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */

function showConfirmationScreen ( confirmationMessage ) {

    $('.portlet').css('display', 'none');

    $('#confirmation-header').text( userSession.getServiceCommand().confirmationMessageHeaderText );
    $('#confirmation-narration').text( confirmationMessage );
    $('#service-command-button-text').text(userSession.getServiceCommand().serviceCommandButtonText);

    $('#close-purchase-container').css('display','none');

    $('#confirmation-portlet').css('display', 'block');

    $('#confirmation-password').val('');
    $('#confirmation-password').css('display', 'inline-block');

    $('#cancel-purchase-container').css('display', 'inline-block');
    $('#confirm-purchase-container').css('display','inline-block');

    $('#narrative').css('display', 'none');
    $('#cancel-purchase-button').css('display', 'inline-block');

//    $('#confirmation-password').focus();
}

function cancelPurchase() {

    $('#confirmation-portlet').css('display', 'none');
    $('#data-bundle-portlet').css('display','block');
    $('#account-listing-portlet').css('display','block');

}

function closePurchase() {
    $('.portlet').css('display','none');
    $('#account-listing-portlet').css('display','block');
    getMobileAccountList();
}

function makePurchase() {

    if ( isConfirmationPasswordValid($('#confirmation-password').val()) ) {

        if ( userSession.getUserPassword() == $('#confirmation-password').val()) {

            $.getJSON("/webaccess?" +
                            "service-command=" + userSession.getServiceCommand().name +
                            "&sessionId=" + userSession.getSessionId() +
                            "&password=" + userSession.getUserPassword() +
                            userSession.getServiceCommand().serviceCommand, function(data){

                $('#confirmation-header').text(userSession.getServiceCommand().successMessage);
                $('#confirmation-narration').text( data );
                $('#confirmation-password').css('display', 'none');
                $('#cancel-purchase-container').css('display', 'none');
                $('#confirm-purchase-container').css('display','none');
                $('#close-purchase-container').css('display','block');
            }).fail( function( xmlHttpRequest, status, error ) {
                $('#confirmation-header').text(userSession.getServiceCommand().failureMessage);
                $('#confirmation-narration').text( xmlHttpRequest.responseText  );
                $('#confirmation-password').css('display', 'none');
                $('#cancel-purchase-button').attr('value', 'Close');
                $('#purchase-button').css('display','none');
            });

//            $('#confirmation-header').text(userSession.getServiceCommand().successMessage);
//            $('#confirmation-narration').text("You successfully purchase a 40MB data bundle. ");
//            $('#confirmation-password').css('display', 'none');
//            $('#cancel-purchase-container').css('display', 'none');
//            $('#confirm-purchase-container').css('display','none');
//            $('#close-purchase-container').css('display','block');
       } else {
            $('#confirmation-password').val('');
            $('#confirmation-password').attr('placeholder','Wrong password');
//            $('#confirmation-password').focus();
        }
//    } else {
//        $('#confirmation-password').val('');
//        $('#confirmation-password').attr('placeholder','Invalid password');
//        $('#confirmation-password').focus();
    } else {
        $('#confirmation-password').val('');
        $('#confirmation-password').attr('placeholder','Invalid password');
    }
}

function isConfirmationPasswordValid( password ) {
    if (password.length < 6) {;
//        addMessage("The password you sent is invalid password<br/>" +
//            "A password can not be less that 6 characters.<br/>" +
//            "Please try again.", true);

        $('#confirmation-password').val('');
        $('#confirmation-password').attr('placeholder','A password can not be less that 6 characters. ');
        return false;
    }

    if ( /^[A-Za-z0-9\d=!\-@._*]+$/.test( password ) ) {
        return true;
    } else {
        $('#confirmation-password').val('');
        $('#confirmation-password').attr('placeholder','Invalid password.');
        return false;
    }
}