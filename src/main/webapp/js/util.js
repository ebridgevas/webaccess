/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/14
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */

Array.prototype.insert = function (index, item) {
    this.splice(index, 0, item);
};

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
    var result;
    if ( (raw < 1) && (raw > 0) ) {
        var normalized =  (raw * 100).toString();
        //result = $.number(normalized,0);
        return parseFloat(normalized).toFixed(0) + "c";
    } else {
        var normalized = raw.toString();
        return "$" + parseFloat(normalized).toFixed(2); // ($.number(normalized,2));
    }
}

function formatMoney( raw ) {

    var money = parseMoney( raw );

    return money < 1 ? (money * 100) + "c" : "$" + parseFloat(money).toFixed(2);
}

function parseMoney( raw ) {

    var amountIsInCents = false;

    if (raw.toString().match(/[c]/)) {
        amountIsInCents = true;
    }
    var result = raw.toString().replace(/[^.0-9]/g, '');
    result = parseFloat(result);

    if (result == NaN) {
        throw new Error ("Invalid amount");
    }

    if ( amountIsInCents ) {
        result = result / 100;
    }

    return result.toFixed(2);
}

/* Currency formatter or data. */
function formatCurrencyOrData(account) {
    if (account.unitOfMeasure == 'money') {
        return formatCurrency(account.accountBalance);
    } else if (account.unitOfMeasure == 'megabyte') {
        return account.accountBalance + " MB";
    } else {
        return account.accountBalance;

    }
}

function formatCurrencyOrData(amount, unitOfMeasure) {
    if (unitOfMeasure == 'money') {
        return formatCurrency(amount);
    } else if (unitOfMeasure == 'megabyte') {
        return amount + " MB";
    } else {
        return amount;

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

function isValidRechargeVoucher( rechargeVoucher ) {

    return (rechargeVoucher.length == 12 ) || ( rechargeVoucher.length == 16 );
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

    if (password.length < 4) {
        $('#password').attr('placeholder','Enter 4 or more chars');
        $('#password').val('');
        return false;
    }

    return true;
//    if ( /^[A-Za-z0-9\d=!\-@._*]+$/.test( password ) ) {
//        return true;
//    } else {
//        $('#password').attr('placeholder','Invalid password. Retry');
//        $('#password').val('');
//        return false;
//    }
}

function isValidFirstName( firstName ) {
    return ( firstName != undefined ) && (firstName.length > 0);
}

function isValidSurname( surname ) {
    return ( surname != undefined ) && (surname.length > 0);
}

function isValidNationalId( nationalId ) {
    return ( nationalId != undefined ) && (nationalId.length > 0);
}

function isValidSecurityQuestion( securityQuestion ) {
    return ( securityQuestion != undefined ) && (securityQuestion.length > 0);
}

function isValidSecurityAnswer( securityAnswer ) {
    return ( securityAnswer != undefined ) && (securityAnswer.length > 0);
}

function isValidUserEmailAddress( emailAddress ) {
    var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regex.test( emailAddress );
}

function parseMoney( raw ) {

    var result = "";

    try {

        var amountIsInCents = false;

        if (raw.toString().match(/[c]/)) {
            amountIsInCents = true;
        }

        result = raw.toString().replace(/[^.0-9]/g, '');
        result = parseFloat(result);
        if (result == NaN) {
            throw new Error ("Invalid amount");
        }

        if ( amountIsInCents ) {
            result = result / 100;
        }
        result = result.toFixed(2);

    } catch (error) {

    }
    return result;
}

function formatMoney( raw ) {

    var money = parseMoney( raw );

    return money < 1 ? (money * 100) + "c" : "$" + parseFloat(money).toFixed(2);
}