/**
 * david@ebridgevas.com
 */

var selectedBundle;
var beneficiaryMobileNumber;
var dataBundleList;
var dataBundles;

//function getDataBundleOffers() {
//
//    $('.slicknav_btn').trigger('click');
//    userSession.setServiceCommand( SERVICE_COMMAND.DATA_BUNDLE_PURCHASE );
//
//    addMessage("I want to buy a data bundle", false);
//
////    loadInitDataFor();
//    addMessage("Select bundle offer below or type bundle number ...", true);
//    dataBundleList = {"1":{"itemId":"1","itemDescription":"5MB","duration":30,"sellingPrice":0.50,"unitOfMeasure":"megabyte","quantity":6},"2":{"itemId":"2","itemDescription":"10MB","duration":30,"sellingPrice":1.00,"unitOfMeasure":"megabyte","quantity":10},"3":{"itemId":"3","itemDescription":"80MB","duration":30,"sellingPrice":3.00,"unitOfMeasure":"megabyte","quantity":40},"4":{"itemId":"4","itemDescription":"150MB","duration":60,"sellingPrice":5.00,"unitOfMeasure":"megabyte","quantity":150},"5":{"itemId":"5","itemDescription":"320MB","duration":60,"sellingPrice":10.00,"unitOfMeasure":"megabyte","quantity":320},"6":{"itemId":"6","itemDescription":"800MB","duration":90,"sellingPrice":20.00,"unitOfMeasure":"megabyte","quantity":800},"7":{"itemId":"7","itemDescription":"1,000MB","duration":90,"sellingPrice":45.00,"unitOfMeasure":"megabyte","quantity":2000},"8":{"itemId":"8","itemDescription":"4,000MB","duration":120,"sellingPrice":75.00,"unitOfMeasure":"megabyte","quantity":4000}};
//
//    var dataBundleOffers = "";
//    $.each(dataBundleList, function(index, bundle) {
//        dataBundleOffers += createDataBundleRow(bundle.itemId, bundle.itemDescription, bundle.duration + " days", bundle.sellingPrice);
//    });
//
//    dataBundleOffers += (""+
//        "   <button id='cancel-bundle-purchase' style='margin-left: 20px;'>Cancel</button>");
//
//    addMessage(dataBundleOffers, true);
//
//    userSession.setBeneficiaryMobileNumber( userSession.getUserId() );
//    $('#message-composer').focus();
//
//    addDataBundleHandlers();
//}

function showDataBundleOffers() {
    userSession.setServiceCommand( SERVICE_COMMAND.DATA_BUNDLE_PURCHASE );
    userSession.setState("DATA_BUNDLE_PURCHASE_LISTING");
    $('.service-command-header-text').text('using main account: ' + formatMoney(userSession.getAccountMap()["Core"].balance) );
    $('#sub-header').css('background-color', 'white');
    $('.service-command-header-text').css('color', 'black');

    loadInitDataFor();
    $('.portlet').css('display', 'none');
    $('#data-bundle-portlet').css('display', 'block');

//    $('.menu-content').css('display', 'none');
//    ebridgenav.visibilityToggle($('.menu-content'), false);
    $('#purchase-button').css('display', 'inline-block');
    $('#narrative').css('display', 'inline-block');
    $('#purchase-button').attr('value','Buy Bundle');
}

function loadInitDataFor() {

    dataBundles = {"1":{"itemId":"1","itemDescription":"5MB","duration":30,"sellingPrice":0.50,"unitOfMeasure":"megabyte","quantity":6},"2":{"itemId":"2","itemDescription":"10MB","duration":30,"sellingPrice":1.00,"unitOfMeasure":"megabyte","quantity":10},"3":{"itemId":"3","itemDescription":"80MB","duration":30,"sellingPrice":3.00,"unitOfMeasure":"megabyte","quantity":40},"4":{"itemId":"4","itemDescription":"150MB","duration":60,"sellingPrice":5.00,"unitOfMeasure":"megabyte","quantity":150},"5":{"itemId":"5","itemDescription":"320MB","duration":60,"sellingPrice":10.00,"unitOfMeasure":"megabyte","quantity":320},"6":{"itemId":"6","itemDescription":"800MB","duration":90,"sellingPrice":20.00,"unitOfMeasure":"megabyte","quantity":800},"7":{"itemId":"7","itemDescription":"1,000MB","duration":90,"sellingPrice":45.00,"unitOfMeasure":"megabyte","quantity":2000},"8":{"itemId":"8","itemDescription":"4,000MB","duration":120,"sellingPrice":75.00,"unitOfMeasure":"megabyte","quantity":4000}};

    createDataBundleListing();
}

function createDataBundleListing(){
    $('#dataBundleListing').empty();
    $('#purchase-data-bundle-narrative').text('');

    $.each(dataBundles, function(index, bundle) {
        createDataBundleRow(bundle.itemId, bundle.itemDescription, bundle.duration + " days", bundle.sellingPrice);
    });

    /* Beneficiary. */
    $("#dataBundleListing").append(
        "   <ul> " +
            "     <li style='min-width: 30px;border-bottom: 0px solid lightgray;padding: 0px;margin: 0px;'><span style='font-weight: normal; font-size: 12px;'>" +
                  "For your phone? </li> " +
            "     <li style='width: 5px;text-align: right;border-bottom: 0px solid lightgray;padding-left: 5px;margin: 0px;'>" +
            "         <input id='is-topping-up-own-phone' type='checkbox' checked> " +
            "     </li> " +
            "     <input id='beneficiary-mobile-number' type='text' placeholder='Mobile number' style='display: none;width: 125px;margin-left: 10px;'> " +
            "</ul> "
    );

    userSession.setBeneficiaryMobileNumber( userSession.getUserId() );

    addDataBundleHandlers();
}

function createDataBundleRow(itemId, itemDescription, duration, sellingPrice) {

    $("#dataBundleListing").append(
        "   <ul style='padding-left: 20px;'> " +
            "     <li style='width: 5px;text-align: left;border-bottom: 0px solid lightgray;padding-right: 15px;margin: 0px'>" +
            "         <input class='dataBundle' type='radio' name='itemId' value='" + itemId + "'> " +
            "     </li> " +
            "     <li style='min-width: 130px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;'><span style='font-weight: normal; font-size: 12px;'>" + itemDescription + " data bundle<span></li> " +
            "     <li style='min-width: 50px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;'><span style='font-weight: normal; font-size: 12px;'>" + duration + "</scan></li> " +
            "     <li style='width: 40px;text-align: right;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;'><span style='font-weight: normal; font-size: 12px;'>" + currencyFormat(sellingPrice) +  "</scan></li> " +
            "</ul> "
    );
}

function addDataBundleHandlers() {

    /* Selection handler. */
    $(".dataBundle").click(function(e){

        userSession.setProduct ( dataBundles[ $(this).attr('value') ] );

        if (parseFloat(userSession.getProduct().sellingPrice) > parseFloat(userSession.getAccountMap()["Core"].balance)) {
            $('.service-command-header-text').text(
                ' - Can not exceed core balance : ' + formatMoney(userSession.getAccountMap()["Core"].balance));
            $('#sub-header').css('background-color', 'red');
            $('.service-command-header-text').css('color', 'white');
            $('.dataBundle').prop('checked', false);

            userSession.setProduct ( undefined );
        } else {
            $('.service-command-header-text').text("- " + userSession.getProduct().itemDescription +
                " - for " + formatMoney(userSession.getProduct().sellingPrice) );
            $('.service-command-header-text').css('color', 'white');
            $('#sub-header').css('background-color', 'green');
        }
    });

    /* Is topping up own phone? */
    $('#is-topping-up-own-phone').click(function(){

        if ( userSession.getProduct() == undefined ) {
            $('.service-command-header-text').text(" - select a data bundle");
            $('#sub-header').css('background-color', 'red');
            $('.service-command-header-text').css('color', 'white');

            return;
        }

        $('#beneficiary-mobile-number').toggle(! this.checked);
        if (this.checked) {
            userSession.setBeneficiaryMobileNumber( userSession.getUserId() );

            if ( userSession.getProduct() == undefined ) {

                $('.service-command-header-text').text(" - select a data bundle");
                $('#sub-header').css('background-color', 'red');
                $('.service-command-header-text').css('color', 'white');
            } else {

                $('.service-command-header-text').text("- " + userSession.getProduct().itemDescription +
                    " - for " + formatMoney(userSession.getProduct().sellingPrice) );
                $('#sub-header').css('background-color', 'green');
                $('.service-command-header-text').css('color', 'white');
            }
        } else {
            $('#beneficiary-mobile-number').val('');
            userSession.setBeneficiaryMobileNumber(null);
            $('#beneficiary-mobile-number').focus();
        }
    });


    $('#cancel-bundle-purchase').click(function(){
        userSession.setProduct( undefined );
        $('#message-composer').val('');
        $('#message-composer').off('keyup');
        $('#message-composer').off('keydown');
        createAccountListing();
    });


    /* Is bundle purchase for own phone */
    $("#isBundlePurchaseForOwnPhone").click(function() {
        $(this).toggleClass("uncheckbox");
        $('#beneficiaryMobilePhonePanel').css('display', $(this).hasClass('uncheckbox') ? 'block' : 'none');

        /* Beneficiary mobile number field events. */
        $('#beneficiaryMobilePhone').focusin(function() {
            if (! isValidMobileNumber($('#beneficiaryMobilePhone').val())) {
                $('#beneficiaryMobilePhone').val("");
                $('.service-command-header-text').text(" - Invalid beneficiary mobile number.");
                $('#sub-header').css('background-color', 'red');
                $('.service-command-header-text').css('color', 'white');
            }
        });

        $('#beneficiaryMobilePhone').focusout(function() {
            validateDataBundleBeneficiaryMobileNumber();
        });
    });

    $('#confirmationButton').css('display','block');
    $('#confirmationButton').val("Buy Bundle");
}

function processDataBundlePurchaseRequest() {

    if( ! userSession.getServiceCommand().isProductSelected ) {
        $('#message-composer').val('');
        $('#message-composer').focus();
        addMessage(
            "Select a data bundle from the offers above ...<br/>" +
            "or send bundle number or press cancel button.", true);
        return;
    } else {
        addMessage("I would like to buy a <br/>" + userSession.getProduct().itemDescription +
            " data bundle for " + formatMoney(userSession.getProduct().sellingPrice), false);

        addMessage("What is the mobile number to topup?<br/>" +
                   "<button id='beneficiary-is-own-phone'>Own phone</button>", true);

        $('#message-composer').val('');

        $('#beneficiary-is-own-phone').click(function() {
            userSession.setBeneficiaryMobileNumber( userSession.getUserId() );
            addMessage("Top up " +
                         toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber()) +
                        " with the data bundle.", false);
        });
    }

//    if ( ! userSession.isBeneficiaryOwnPhone() ) {
//        if (  ! isValidMobileNumber( $('#beneficiary-mobile-number').val() ) ) {
//            $('#beneficiary-mobile-number').attr('placeholder','Invalid mobile');
//            $('#beneficiary-mobile-number').val('');
//            return;
//        } else {
//            userSession.setBeneficiaryMobileNumber(
//                toStandardMobileNumberFormat(
//                    $('#beneficiary-mobile-number').val()));
//        }
//    }
//
//    userSession.getServiceCommand().serviceCommand =
//        "&beneficiaryMobileNumber=" + userSession.getBeneficiaryMobileNumber() +
//            "&bundleId=" + userSession.getProduct().itemId;
//
//    confirm( "Confirm that you want to buy a " + userSession.getProduct().itemDescription +  " data bundle " +
//        "for " + currencyFormat(userSession.getProduct().sellingPrice ) +
//        " and top up " +
//        ( userSession.isBeneficiaryOwnPhone() ? "your mobile number" : " mobile number " +
//            toShortMobileNumberFormat(userSession.getBeneficiaryMobileNumber())));

}

function validateDataBundleBeneficiaryMobileNumber(){

    var mobileNumber = $('#beneficiaryMobilePhone').val();

    if ( ! isValidMobileNumber(mobileNumber) ) {
        $('#beneficiaryMobilePhone').val("Invalid beneficiary mobile number.");
        $('.service-command-header-text').text(" - Invalid beneficiary mobile number.");
        $('#sub-header').css('background-color', 'red');
        $('.service-command-header-text').css('color', 'white');
        return;
    }
}

function bundlePurchaseNarration() {
    return "Buy " + selectedBundle.itemDescription + " bundle " +

      " for " + formatCurrency(selectedBundle.sellingPrice);
}

function isBundlePurchaseForOwnPhone() {
    return ! $("#isBundlePurchaseForOwnPhone").hasClass('uncheckbox');
}

function createAuthorisationRow() {

    serviceCommand = "service-command=data-bundle-purchase" +
        "&sessionId=" + sessionId +
        "&bundleId=" + selectedBundle.itemId +
        "&beneficiaryMobileNumber=" + toStandardMobileNumberFormat(beneficiaryMobileNumber);

    var authorisationWarning = "Please confirm that you would like to buy the following data bundle:";
    var expiryDate =  new XDate().addDays(selectedBundle.duration).toString("d MMMM yyyy");

    $("#authorisationDetail").empty();

    $("#authorisationDetail").append(
            "<div id='" + selectedBundle.itemId + "' class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 12px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 12px;font-weight: normal'>" + authorisationWarning + "</li> "+
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div id='" + selectedBundle.itemId+ "' class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 2px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Data Bundle Size</li> "+
            "            <li class='_4_ug'>" + selectedBundle.itemDescription +  "</li> " +
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div id='" + selectedBundle.itemId + "' class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 2px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Data Bundle Cost</li> "+
            "            <li class='_4_ug'>" + currencyFormat(selectedBundle.sellingPrice) +  "</li> " +
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div id='" + selectedBundle.itemId + "' class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 2px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Expiry Date</li> "+
            "            <li class='_4_ug'>" + expiryDate +  "</li> " +
            "         </ul> " +
            "      </div> " +
            "   </div> " +
            "</div>"
    );

    $("#authorisationDetail").append(
        "<div id='" + selectedBundle.itemId + "' class='dataBundle _59qe _5e1e' style='padding-bottom: 1px;padding-top: 2px;'> " +
            "   <div class='_6a _59ql' style='max-width: 458px;'> " +
            "      <div class='_59qg'> " +
            "         <ul class='uiList _1dsl _509- _4ki _6-h _6-j _6-i'> " +
            "            <li class='_4_ug' style='margin-left: 36px;min-width: 230px;font-weight: normal'>Mobile Phone To Top Up</li> "+
            "            <li class='_4_ug'>" + toShortMobileNumberFormat(beneficiaryMobileNumber) +  "</li> " +
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

//    $('#confirmationPassword').focusin(function() {
//        if (!isValidPassword() || !isPlaceHolder() ) {
//            $('#confirmationPassword').val("");
//            $('#confirmationPassword').attr('placeholder','');
//        }
//    });

    $('#confirmationPassword').one("keydown", (function() {
        $('#confirmationPassword').val("");
        $('#confirmationPassword').attr('placeholder','');
        changeInputType("password");
    }));

    $('#confirmationPassword').focusout(function() {
        validatePassword();
    });

}

function changeInputType(newType) {
    $('#confirmationPassword').attr('type',newType);
}

function isValidPassword() {
    return $('#confirmationPassword').val() && $('#confirmationPassword').val().length >= 6;
}

function validatePassword(){

    var confirmationPassword = $('#confirmationPassword').val();

    if ( (confirmationPassword != "Enter your password here" ) && !isValidPassword() ) {
        $('#confirmationPassword').val("Invalid password. Try again.");
        return;
    }

    if (isPlaceHolder()) {
        changeInputType("text");
    } else {
        changeInputType("password");
    }
}

function isPlaceHolder() {
    var confirmationPassword = $('#confirmationPassword').val();
    return ((confirmationPassword == "Enter your password here") ||
        (confirmationPassword == 'Invalid password. Try again.'));
}
