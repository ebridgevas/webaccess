/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/14
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */

function UrlParser() {

    this.parse = function() {
        var params = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            params.push(hash[0]);
            params[hash[0]] = hash[1];
        }
        return params;
    }
}

/* Currency formatter. */
function formatCurrency(raw) {
    if ( (raw < 1) && (raw > 0) ) {
        return ($.number((raw * 100).toString(),0)) + "c";
    } else {
        return "$" + ($.number(raw.toString(),2));
    }
}

/* Currency formatter or data. */
function formatCurrencyOrData(account) {
    if (account.unitOfMeasure == 'money') {
        return formatCurrency(account.accountBalance);
    } else {
        return account.accountBalance + " MB"

    }
}


/* Validate mobile number. */
function isValidMobileNumber(mobileNumber) {
    /*
     - 263733661588
     - 0733661588
     - 733661588
     */
    if ( ( mobileNumber.length != 12 ) && ( mobileNumber.length != 10 ) && ( mobileNumber.length != 9 ) )  {
        return false;
    }

    switch( mobileNumber.length) {
        case 9:
            return mobileNumber.substring(0,2) == "73";
        case 10:
            return mobileNumber.substring(0,3) == "073";
        case 12:
            return mobileNumber.substring(0,5) == "26373";
        default :
            return false;
    }
}

function toStandardMobileNumberFormat(mobileNumber) {

    if (! isValidMobileNumber(mobileNumber)) {
        throw new Error("Invalid mobile number");
    }
    switch( mobileNumber.length) {
        case 9:
            return "263" + mobileNumber;
        case 10:
            return "263" + mobileNumber.substring(1);
        case 12:
            return mobileNumber;
        default :
            throw new Error("Invalid mobile number");
    }
}

/* To short mobile number format */
function toShortMobileNumberFormat(mobileNumber) {

    if ( mobileNumber == null || ! isValidMobileNumber( mobileNumber ) ) {
        throw new Error("Invalid mobile number");
    }
    return "0" + toStandardMobileNumberFormat(mobileNumber).substring(3);
}

function isValidPassword( password ) {

    if (password.length < 6) {
        $('#password').attr('placeholder','Enter 6 or more chars');
        $('#password').val('');
        return false;
    }

    if ( /^[A-Za-z0-9\d=!\-@._*]+$/.test( password ) ) {
        return true;
    } else {
        $('#password').attr('placeholder','Invalid password. Retry');
        $('#password').val('');
        return false;
    }
}

//function hideAddressBar() {
//
//    if(!window.location.hash)
//    {
//        if(document.height < window.outerHeight)
//        {
//            document.body.style.height = (window.outerHeight + 50) + 'px';
//        }
//
//        setTimeout( function(){ window.scrollTo(0, 1); }, 50 );
//    }
//}

function isValidEmailAddress( emailAddress ) {

    return emailAddress.match("^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$");
}