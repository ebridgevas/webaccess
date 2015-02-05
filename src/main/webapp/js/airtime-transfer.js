/**
 * david@ebridgevas.com
 */

function showAirtimeTransferPage() {
    $('.portlet').css('display', 'none');
    $('#airtime-transfer-portlet').css('display', 'block');

    $('#airtime-transfer-header').text('from main account: ' + formatMoney(userSession.getAccountMap()["Core"].balance) );

    userSession.setServiceCommand( SERVICE_COMMAND.AIRTIME_TRANSFER );
    userSession.setState("AIRTIME_TRANSFER");

    $('#purchase-button').css('display', 'inline-block');
    $('#narrative').css('display', 'inline-block');
    $('#purchase-button').attr('value','Transfer');

    $('#amountToTransfer').attr('placeholder', 'Amount to transfer');
    $('#amountToTransfer').val('');

    $('#mobileNumberToTransferTo').attr('placeholder', 'Receiving mobile number');
    $('#mobileNumberToTransferTo').val('');

    addAirtimeTransferEventsHandlers();
}

function transfer() {

    try {

        var amountToTransfer = null;

        if ( ! $('#amountToTransfer').val() ) {

            $('#amountToTransfer').attr('placeholder', "Invalid transfer amount");
            $('#amountToTransfer').val('');

            $('#airtime-transfer-header').text('Invalid transfer amount' );
            $('#sub-transfer-header').css('background-color', 'red');
            $('#airtime-transfer-header').css('color', 'white');

            return;
        } else {
            amountToTransfer = parseMoney( $('#amountToTransfer').val() );
        }

        if ( ! isValidMobileNumber( $('#mobileNumberToTransferTo').val() ) ) {
            $('#airtime-transfer-header').text('Invalid mobile number.' );
            $('#sub-transfer-header').css('background-color', 'red');
            $('#airtime-transfer-header').css('color', 'white');
        }

        if (    (! isBelowMinimum(amountToTransfer))
             && (! isAboveMaximum(amountToTransfer))
             && (! isMaximumLimitReached( amountToTransfer ))
             && isValidMobileNumber( $('#mobileNumberToTransferTo').val() ) ) {

            userSession.setAmount(amountToTransfer);
            userSession.setBeneficiaryMobileNumber(toStandardMobileNumberFormat($('#mobileNumberToTransferTo').val()));

            userSession.getServiceCommand().serviceCommand =
                "&transferAmount=" + userSession.getAmount() +
                "&airtimeTransferBeneficiary=" + userSession.getBeneficiaryMobileNumber();

            userSession.setState("AIRTIME_TRANSFER_CONFIRMATION");
            userSession.setProduct( userSession.getAmount() );
            showConfirmationScreen( "Confirm that you want to transfer " + formatMoney( userSession.getAmount() ) +
                        " to " + toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber()));
        }
    } catch(e) {

        $('#amountToTransfer').attr('placeholder', e.message);
        $('#amountToTransfer').val('');
    }
}

var airtimeTransferBeneficiaryPlaceholders
    = new Array(
        "Enter your friend's mobile number");

function addAirtimeTransferEventsHandlers() {

    $('#amountToTransfer').focusout(function(event){
        event.stopPropagation();

        if ($('#amountToTransfer').val() == '') {
            return;
        }

        try {

            var amountToTransfer = parseMoney( $('#amountToTransfer').val() );

            if (       (! isBelowMinimum(amountToTransfer) )
                    && (! isAboveMaximum(amountToTransfer))
                    && (! isMaximumLimitReached( amountToTransfer ) ) ) {


                if ( amountToTransfer == 'NaN' ) {
                    $('#airtime-transfer-header').text(' - Invalid transfer amount' );
                    $('#sub-transfer-header').css('background-color', 'red');
                    $('#airtime-transfer-header').css('color', 'white');

                    return;
                }

                $('#amountToTransfer').val( formatMoney( amountToTransfer ) );
//                $('#mobileNumberToTransferTo').focus();
                $('#airtime-transfer-header').text(' - of ' + formatMoney( amountToTransfer ) );
                $('#sub-transfer-header').css('background-color', 'white');
                $('#airtime-transfer-header').css('color', 'black');
            }

        } catch(e) {
            $('#amountToTransfer').attr('placeholder', e.message);
            $('#amountToTransfer').val('');

            $('#airtime-transfer-header').text(' - ' +  e.message );
            $('#sub-transfer-header').css('background-color', 'red');
            $('#airtime-transfer-header').css('color', 'white');
        }
    });

    $('#mobileNumberToTransferTo').focusout(function(event){
        event.stopPropagation();

        if ($('#mobileNumberToTransferTo').val() == '') {
            return;
        }

        try {
            $('#mobileNumberToTransferTo').val(toShortMobileNumberFormat($('#mobileNumberToTransferTo').val()));

            if ( toStandardMobileNumberFormat($('#mobileNumberToTransferTo').val())
                == toStandardMobileNumberFormat(userSession.getUserId())) {
                throw new Error("Can not be own phone");
            }

        } catch(e) {
            $('#mobileNumberToTransferTo').attr('placeholder', e.message);
            $('#mobileNumberToTransferTo').val('');

            $('#airtime-transfer-header').text(' - ' +  e.message );
            $('#sub-transfer-header').css('background-color', 'red');
            $('#airtime-transfer-header').css('color', 'white');
        }
    });

    addInputFieldEventHandlers();

    /* Purchase data bundle handler. */
    $("#airtimeTransferRequestButton").click(function(event) {
        event.stopPropagation();

        if( ! $('#airtimeTransferAmount').val()) {
            $('#airtimeTransferAmount').val("Transfer amount not entered. Try again.");
            $('#airtimeTransferAmount').click(function(){
                $('#airtimeTransferAmount').val("");
            });
            return;
        } else {

            var airtimeTransferAmount = $('#airtimeTransferAmount').val();
            if ( ! isMoney(airtimeTransferAmount) ) {
                $('#airtimeTransferAmount').val("Invalid transfer amount. Try again.");
                $('#airtimeTransferAmount').click(function(){
                    $('#airtimeTransferAmount').val("");
                });
                return;
            }

            try {

                var correctedAirtimeTransferAmount = airtimeTransferAmount;
                if ( airtimeTransferAmount.charAt(airtimeTransferAmount.length-1) == "c" ) {
                    correctedAirtimeTransferAmount = airtimeTransferAmount / 100;
                }

                if ( accounts[1].balance < parseFloat(correctedAirtimeTransferAmount) ) {
                    $('#airtimeTransferAmount').val("Amount too high. Enter amount below " + formatCurrencyOrData(accounts[1]));
                    $('#airtimeTransferAmount').click(function(){
                        $('#airtimeTransferAmount').val("");
                    });
                    return;
                }
            } catch(e){
                $('#airtimeTransferAmount').val("Invalid transfer amount. Try again.");
                $('#airtimeTransferAmount').click(function(){
                    $('#airtimeTransferAmount').val("");
                });
                return;
            }
        }

        if ( (! $('#airtimeTransferBeneficiaryMobileNumber').val())
                    || ( $.inArray($('#airtimeTransferBeneficiaryMobileNumber').val(),
                                        airtimeTransferBeneficiaryPlaceholders) != -1)) {
            $('#airtimeTransferBeneficiaryMobileNumber').val("Beneficiary number not entered. Try again.");
            $('#airtimeTransferBeneficiaryMobileNumber').click(function(){
                $('#airtimeTransferBeneficiaryMobileNumber').val("");
            });
            return;
        } else {
            if (! isValidMobileNumber($('#airtimeTransferBeneficiaryMobileNumber').val())) {
                $('#airtimeTransferBeneficiaryMobileNumber').val("Invalid mobile number entered. Try again.");
                $('#airtimeTransferBeneficiaryMobileNumber').click(function(){
                    $('#airtimeTransferBeneficiaryMobileNumber').val("");
                });
                return;
            } else {
                disableBackgroundPortlets();
//                $('#feedbackPanel').css('display','none');
                createAirtimeTransferRequestRow(
                    currencyFormat($('#airtimeTransferAmount').val()),
                    $('#airtimeTransferBeneficiaryMobileNumber').val());
                $('#confirmationButton').css('display','block');
                $('#confirmationButtonLabel').text("Transfer");
//                addConfirmationHandler();

                centerPopup();
                loadPopup();
            }
        }
    });
}

function isBelowMinimum ( amountToTransfer ) {
    if ( parseFloat(amountToTransfer) < parseMoney(userSession.getServiceCommand().minimumAmount) ) {
            throw new Error(
                        'Minimum amount is ' +
                            formatMoney(userSession.getServiceCommand().minimumAmount));
    } else {
        return false;
    }
}

function isAboveMaximum ( amountToTransfer ) {
    if ( parseFloat(amountToTransfer) > parseMoney(userSession.getServiceCommand().maximumAmount) ) {
            throw new Error(
                'Maximum amount is ' +
                    formatMoney(userSession.getServiceCommand().maximumAmount));
    } else {
        return false;
    }
}

function isMaximumLimitReached ( amountToTransfer ) {

    if ( parseFloat(amountToTransfer) > parseMoney( userSession.getAccountMap()["Core"].balance) ) {
        throw new Error('Can not exceed ' + formatMoney(userSession.getAccountMap()["Core"].balance));
    } else {
        return false;
    }
}

function createAirtimeTransferRequestRow ( transferAmount, airtimeTransferBeneficiary) {

    var correctedAirtimeTransferAmount = transferAmount;
    if ( transferAmount.charAt(transferAmount.length-1) == "c" ) {
        correctedAirtimeTransferAmount = parseFloat(transferAmount) / 100;
    }

    serviceCommand = "service-command=airtime-transfer" +
        "&sessionId=" + sessionId +
        "&airtimeTransferBeneficiary=" + airtimeTransferBeneficiary +
        "&transferAmount=" + correctedAirtimeTransferAmount;

    $('#confirmationButton').prop('value','Transfer Airtime');
    $('#feedbackPanel').fadeTo('slow',0.2);
    var confirmationMessage = "Please confirm that you would like to make the following transfer:";

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
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Amount To Transfer</li> "+
            "            <li class='_4_ug'>" + transferAmount +  "</li> " +
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
            "            <li class='_4_ug'>" + airtimeTransferBeneficiary  +  "</li> " +
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

function addInputFieldEventHandlers() {

    $('#airtimeTransferAmount').val("Enter amount to transfer here");
    $('#airtimeTransferAmount').css("color","#777777");

    $('#airtimeTransferAmount').focusin(function() {
        if (! isMoney($('#airtimeTransferAmount').val()) ) {
            $('#airtimeTransferAmount').val("");
        }
    });

    $('#airtimeTransferAmount').focusout(function() {
        validateAirtimeTransferAmount();
    });

    /* Beneficiary mobile number field events. */
    $('#airtimeTransferBeneficiaryMobileNumber').focusin(function() {
        if (! isValidMobileNumber($('#airtimeTransferBeneficiaryMobileNumber').val())) {
            $('#airtimeTransferBeneficiaryMobileNumber').val("");
        }
    });

    $('#airtimeTransferBeneficiaryMobileNumber').focusout(function() {
       validateBeneficiaryMobileNumber();
    });
}

function validateAirtimeTransferAmount(){

    var airtimeTransferAmount = $('#airtimeTransferAmount').val();

    if ( ! isMoney(airtimeTransferAmount) ) {
        $('#airtimeTransferAmount').val("Invalid transfer amount. Try again.");
        return;
    }
    try {
//        if ( accounts[1].accountBalance < parseFloat(airtimeTransferAmount) ) {
//            $('#airtimeTransferAmount').val("Amount too high. Enter amount below " + formatCurrencyOrData(accounts[1]));
//        } else {
//            $('#airtimeTransferAmount').val(currencyFormat( airtimeTransferAmount ));
//        }
        $('#airtimeTransferAmount').val(currencyFormat( airtimeTransferAmount ));
    } catch(e){
        alert("Error : " + e);
        $('#airtimeTransferAmount').val("Invalid transfer amount. Try again.");
    }

}

function validateBeneficiaryMobileNumber(){

    var mobileNumber = $('#airtimeTransferBeneficiaryMobileNumber').val();

    if ( ! isValidMobileNumber(mobileNumber) ) {
        $('#airtimeTransferBeneficiaryMobileNumber').val("Invalid beneficiary mobile number. Try again.");
        return;
    }
}

//function addConfirmationHandler() {
//    $('#confirmationButton').click(function(event){
//        event.stopPropagation();
//        alert("Transferring ...");
//    });
//}
