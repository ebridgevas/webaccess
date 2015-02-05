/**
 * david@ebridgevas.com
 *
 */

function showVoucherRechargePage() {

    userSession.setServiceCommand( SERVICE_COMMAND.VOUCHER_RECHARGE );
    userSession.setState("VOUCHER_RECHARGE_REQUEST");

    userSession.setBeneficiaryMobileNumber( userSession.getUserId() );

    $('#voucher-recharge-header').text(' - ' + 'Main account: ' + formatMoney(userSession.getAccountMap()["Core"].balance) );
    $('#sub-recharge-header').css('background-color', 'white');
    $('#voucher-recharge-header').css('color', 'black');

    $('.portlet').css('display', 'none');
    $('#voucher-recharge-portlet').css('display', 'block');

    $('#purchase-button').css('display', 'inline-block');
    $('#narrative').css('display', 'inline-block');
    $('#purchase-button').attr('value','Recharge');

    $('#recharge-voucher').attr('placeholder', 'Voucher number');
    $('#recharge-voucher').val('');

    $('#voucher-recharge-beneficiary-number').attr('placeholder', 'Receiving mobile');
    $('#voucher-recharge-beneficiary-number').val('');

    $('#is-recharging-own-phone').prop('checked', true);

    addVoucherRechargeEventHandlers();

    return false;
}

function voucherRecharge() {

    try {
        if ( isValidRechargeVoucher($('#recharge-voucher').val())) {
            userSession.setProduct($('#recharge-voucher').val());
        }
    } catch(e) {
        $('#recharge-voucher').attr('placeholder', e.message);
        $('#recharge-voucher').val('');

        $('#voucher-recharge-header').text(' - ' + e.message );
        $('#sub-recharge-header').css('background-color', 'red');
        $('#voucher-recharge-header').css('color', 'white');

        return;
    }

    if ( ! userSession.isBeneficiaryOwnPhone() ) {

        try {
//            if ( isValidMobileNumber( $('#voucher-recharge-beneficiary-number').val() ) ) {
                userSession.setBeneficiaryMobileNumber(
                    toStandardMobileNumberFormat(
                        $('#voucher-recharge-beneficiary-number').val()));
//            }
        } catch(e) {
            $('#voucher-recharge-beneficiary-number').attr('placeholder', e.message);
            $('#voucher-recharge-beneficiary-number').val('');

            $('#voucher-recharge-header').text(' - ' + e.message );
            $('#sub-recharge-header').css('background-color', 'red');
            $('#voucher-recharge-header').css('color', 'white');
            return;
        }
    }

    userSession.setState("VOUCHER_RECHARGE_CONFIRMATION");

    userSession.getServiceCommand().serviceCommand =
        "&rechargeVoucher=" + userSession.getProduct() +
            "&beneficiaryMobileNumber=" + userSession.getBeneficiaryMobileNumber();

    showConfirmationScreen( "Confirm that you want to recharge " +
                ( userSession.isBeneficiaryOwnPhone() ? "your mobile number" : " mobile number " +
                    toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber())) +
                  " using recharge voucher : " +  userSession.getProduct());

}

function addVoucherRechargeEventHandlers() {

    $('#recharge-voucher').focusout(function(event){
        event.stopPropagation();

        if ($('#recharge-voucher').val() == '') {
            return;
        }

        try {
            isValidRechargeVoucher($('#recharge-voucher').val());
        } catch(e) {
            $('#recharge-voucher').attr('placeholder', e.message);
            $('#recharge-voucher').val('');

            $('#voucher-recharge-header').text(' - ' + e.message );
            $('#sub-recharge-header').css('background-color', 'red');
            $('#voucher-recharge-header').css('color', 'white');
        }
    });

    $('#voucher-recharge-beneficiary-number').focusout(function(event){
        event.stopPropagation();

        if ($('#voucher-recharge-beneficiary-number').val() == '') {

            $('#voucher-recharge-header').text(' - ' + e.message );
            $('#sub-recharge-header').css('background-color', 'red');
            $('#voucher-recharge-header').css('color', 'white');
            return;
        }

        try {
            $('#voucher-recharge-beneficiary-number').val(
                toShortMobileNumberFormat($('#voucher-recharge-beneficiary-number').val()));
        } catch(e) {
            $('#voucher-recharge-beneficiary-number').attr('placeholder', e.message);
            $('#voucher-recharge-beneficiary-number').val('');

            $('#voucher-recharge-header').text(' - ' + e.message );
            $('#sub-recharge-header').css('background-color', 'red');
            $('#voucher-recharge-header').css('color', 'white');
        }
    });

    /* Is recharging own phone */

    $('#is-recharging-own-phone').click(function() {

        $('#voucher-recharge-beneficiary-number').toggle(! this.checked);
        if (this.checked) {
            userSession.setBeneficiaryMobileNumber( userSession.getUserId() );

            $('#voucher-recharge-header').text(' - ' + 'Main account: ' + formatMoney(userSession.getAccountMap()["Core"].balance) );
            $('#sub-recharge-header').css('background-color', 'white');
            $('#voucher-recharge-header').css('color', 'black');

        } else {
            $('#voucher-recharge-beneficiary-number').val('');
            $('#voucher-recharge-beneficiary-number').attr('placeholder', 'Receiving mobile');
            userSession.setBeneficiaryMobileNumber(null);
//            $('#voucher-recharge-beneficiary-number').focus();

            $('#voucher-recharge-header').text(' - ' + 'Main account: ' + formatMoney(userSession.getAccountMap()["Core"].balance) );
            $('#sub-recharge-header').css('background-color', 'white');
            $('#voucher-recharge-header').css('color', 'black');
        }
    });

    /* Purchase data bundle handler. */
    $("#voucher-recharge-button").click(function(event) {

        event.stopPropagation();

        if( userSession.getProduct() == undefined) {
            $('#purchase-data-bundle-narrative').text("Select a bundle to purchase.");
            return;
        }

        if ( ! userSession.isBeneficiaryOwnPhone() ) {
            if (  ! isValidMobileNumber( $('#beneficiary-mobile-number').val() ) ) {
                $('#beneficiary-mobile-number').attr('placeholder','Invalid mobile');
                $('#beneficiary-mobile-number').val('');
                return;
            } else {
                userSession.setBeneficiaryMobileNumber(
                    toStandardMobileNumberFormat(
                        $('#beneficiary-mobile-number').val()));
            }
        }

        userSession.getServiceCommand().serviceCommand =
            "&mobileNumber=" + userSession.getUserId() +
            "&rechargeVoucher=" + userSession.getProduct() +
            "&beneficiaryMobileNumber=" + userSession.getBeneficiaryMobileNumber();

        confirm( "Confirm that you want to recharge " +
                    ( userSession.isBeneficiaryOwnPhone() ? "your mobile number" : " mobile number " +
                        toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber())) +
                    "using recharge voucher : " + userSession.getProduct());

    });


    $("#voucherRechargeButton").click(function(event) {
        event.stopPropagation();

        var voucherRechargeRequest = new Object();

        if ($('#isVoucherRechargingOwnPhone').hasClass('uncheckbox')) {
            /* Now own phone. */
            if ( (! $('#voucherRechargeBeneficiaryMobileNumber').val())
                || ( $.inArray($('#voucherRechargeBeneficiaryMobileNumber').val(),
                voucherRechargeBeneficiaryPlaceholders) != -1)) {
                $('#voucherRechargeBeneficiaryMobileNumber').val("Beneficiary number not entered. Try again.");
                return;
            } else {
                if (! isValidMobileNumber($('#voucherRechargeBeneficiaryMobileNumber').val())) {
                    $('#voucherRechargeBeneficiaryMobileNumber').val("Invalid mobile number entered. Try again.");
                    return;
                } else {
                    voucherRechargeRequest.beneficiaryMobileNumber
                            = toStandardMobileNumberFormat($('#voucherRechargeBeneficiaryMobileNumber').val());
                }
            }
        } else {
            voucherRechargeRequest.beneficiaryMobileNumber = user.mobileNumber;

        }

        if( ! $('#rechargeVoucher').val()) {
            $('#rechargeVoucher').val("Recharge voucher not entered. Try again.");
            return;
        } else {

            var rechargeVoucher = $('#rechargeVoucher').val();

            if ( ! validateRechargeVoucher(rechargeVoucher) ) {
                $('#rechargeVoucher').val("Invalid recharge voucher. Try again.");
                return;
            } else {
                // TODO verify that maximum account is not reached.
               voucherRechargeRequest.rechargeVoucher = $('#rechargeVoucher').val();
            }
        }

        createVoucherRechargeRequestRow(voucherRechargeRequest);
        $('#confirmationButton').css('display','block');
        $('#confirmationButtonLabel').text("Recharge");
        disableBackgroundPortlets();
        centerPopup();
        loadPopup();

    });
}

function createVoucherRechargeRequestRow(voucherRechargeRequest) {

    alert("Voucher Recharge");
    serviceCommand = "service-command=voucher-recharge&mobileNumber=" + mobileNumber +
        "&sessionId=" + sessionId +
        "&beneficiaryMobileNumber=" +  voucherRechargeRequest.beneficiaryMobileNumber +
        "&rechargeVoucher=" + voucherRechargeRequest.rechargeVoucher;

    $('#confirmationButton').prop('value','Recharge');
    $('#feedbackPanel').fadeTo('slow',0.2);
    var confirmationMessage = "Please confirm that you would like to redeem the following voucher:";

    $("#authorisationDetail").empty();

    $("#authorisationDetail").append(
        "<div class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 12px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 12px;font-weight: normal'>" + confirmationMessage + "</li> "+
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 2px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Mobile Number To Topup</li> "+
            "            <li class='_4_ug'>" + voucherRechargeRequest.beneficiaryMobileNumber +  "</li> " +
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 2px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Recharge Voucher</li> "+
            "            <li class='_4_ug'>" + voucherRechargeRequest.rechargeVoucher  +  "</li> " +
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div class='_59qb'> " +
            "<div class='uiTypeahead uiClearableTypeahead photoTypeahead fbHubsTypeahead' " +
            " id='confirmationPasswordContainer' style='width: 268px; margin-left: 36px;'>" +
            " <div class='wrap'> " +
            " <div class='innerWrap'> " +
            " <input id='confirmationPassword'" +
            " class='inputtext textInput DOMControl_placeholder'" +
            " placeholder='Enter your password here to confirm' " +
            " autocomplete='off' " +
            " aria-autocomplete='list' " +
            " aria-expanded='false' " +
            " aria-owns='typeahead_list_u_18_g' " +
            " role='combobox' " +
            " spellcheck='false' " +
            " value='Enter your password here to confirm' " +
            " aria-label='Enter your password here to confirm' " +
            " type='text'> " +
            " </div> " +
            "</div>" +
            "</div>"+
            "</div>"
    );

}

function isValidRechargeVoucher(voucher) {
    if ( voucher.length == 12 || voucher.length == 16 ) {
        return true;
    } else {
        throw new Error("Invalid voucher number");
    }
}

function addVoucherRechargeInputFieldEventHandlers () {

    $('#rechargeVoucher').focusin(function() {
        if (! validateRechargeVoucher($('#rechargeVoucher').val())) {
            $('#rechargeVoucher').val("");
        }
    });

    $('#rechargeVoucher').focusout(function() {
        if (! validateRechargeVoucher($('#rechargeVoucher').val())) {
            $('#rechargeVoucher').val("Invalid recharge voucher. Try again.");
        }
    });

    /* Beneficiary mobile number field events. */
    $('#voucherRechargeBeneficiaryMobileNumber').focusin(function() {
        if (! isValidMobileNumber($('#voucherRechargeBeneficiaryMobileNumber').val())) {
            $('#voucherRechargeBeneficiaryMobileNumber').val("");
        }
    });

    $('#voucherRechargeBeneficiaryMobileNumber').focusout(function() {
        validateVoucherRechargeBeneficiaryMobileNumber();
    });
}

function validateVoucherRechargeBeneficiaryMobileNumber(){

    var mobileNumber = $('#voucherRechargeBeneficiaryMobileNumber').val();

    if ( ! isValidMobileNumber(mobileNumber) ) {
        $('#voucherRechargeBeneficiaryMobileNumber').val("Invalid beneficiary mobile number. Try again.");
        return;
    }
}