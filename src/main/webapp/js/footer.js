/**
 * Created by david on 4/11/14.
 */

function showPagerFooter() {

    var cancelButton = $('<input onclick="getMobileAccountList();" class="ebridge_send_button button-font" type="button" id="cancel-purchase-button" value="Cancel" style="display: none;"> ');
    var purchaseButton = $('<input onclick="purchase();" class="ebridge_send_button button-font" type="button" id="purchase-button" value="Process" style="display: none;margin-left: 15px;"> ');

    $('#page-footer').empty();
    $('#page-footer').append( cancelButton );
    $('#page-footer').append( purchaseButton );

    if ( userSession.getServiceCommand() == SERVICE_COMMAND.DATA_BUNDLE_PURCHASE ) {
        $('#purchase-button').attr('value','Buy Bundle');
    } else if ( userSession.getServiceCommand() == SERVICE_COMMAND.AIRTIME_TRANSFER ) {
        $('#purchase-button').attr('value','Transfer');
    }

    $('#page-footer').css('display', 'block');
}

function purchase() {

    /* Purchase data bundle handler. */

    if (userSession.getState() == "DATA_BUNDLE_PURCHASE_LISTING" ) {

        if( userSession.getProduct() == undefined) {

            $('.service-command-header-text').text(" - Select a bundle to purchase");
            $('#sub-header').css('background-color', 'red');
            $('.service-command-header-text').css('color', 'white');
            return;
        }

        if ( ! userSession.isBeneficiaryOwnPhone() ) {
            if (  ! isValidMobileNumber( $('#beneficiary-mobile-number').val() ) ) {
                $('#beneficiary-mobile-number').attr('placeholder','Invalid mobile');
                $('#beneficiary-mobile-number').val('');
                $('.service-command-header-text').text(" - Invalid beneficiary mobile number.");
                $('#sub-header').css('background-color', 'red');
                $('.service-command-header-text').css('color', 'white');
                return;
            } else {
                userSession.setBeneficiaryMobileNumber(
                    toStandardMobileNumberFormat(
                        $('#beneficiary-mobile-number').val()));
            }
        }

        userSession.setState("DATA_BUNDLE_PURCHASE_REQUEST");

        userSession.getServiceCommand().serviceCommand =
            "&beneficiaryMobileNumber=" + userSession.getBeneficiaryMobileNumber() +
                "&bundleId=" + userSession.getProduct().itemId;

        $('#data-bundle-portlet').css('display', 'none');
        showConfirmationScreen( "Confirm that you want to buy a " + userSession.getProduct().itemDescription +  " data bundle " +
            "for " + currencyFormat(userSession.getProduct().sellingPrice ) +
            " and top up " +
            ( userSession.isBeneficiaryOwnPhone() ? "your mobile number" : " mobile number " +
                toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber())));



    } else if ( userSession.getState() == "DATA_BUNDLE_PURCHASE_REQUEST" ) {
        makePurchase();
    } else if ( userSession.getState() == "AIRTIME_TRANSFER" ) {
        transfer();
    } else if ( userSession.getState() == "AIRTIME_TRANSFER_CONFIRMATION" ) {
        makePurchase();
    } else if ( userSession.getState() == "VOUCHER_RECHARGE_REQUEST" ) {
        voucherRecharge();
    } else if ( userSession.getState() == "VOUCHER_RECHARGE_CONFIRMATION" ) {
        makePurchase();
    }

}