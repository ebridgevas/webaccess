/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 8/13/13
 * Time: 2:59 AM
 * To change this template use File | Settings | File Templates.
 */

function currencyFormat(raw) {

    if ( isNaN( parseFloat(raw) ) ) {
        throw new Error("Invalid amount");
    }

    return raw < 1 ? (raw * 100) + "c" : "$" + parseFloat(raw).toFixed(2);
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

function decimalCommas(nStr) {
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
}

/* Currency formatter or data. */
function formatCurrencyOrData(account) {
    if (account.walletId == 'Core') {
        return formatMoney(account.balance);
    } else {
        return account.balance + " MB"

    }
}

function isMoney(value) {
    value = value.toString().replace(/[^.0-9]/g,'');

    if (isMoneyInCents(value)) {
        return true;
    } else {
        var regex = /^\$?\-?([1-9]{1}[0-9]{0,2}(\,\d{3})*(\.\d{0,2})?|[1-9]{1}\d{0,}(\.\d{0,2})?|0(\.\d{0,2})?|(\.\d{1,2}))$|^\-?\$?([1-9]{1}\d{0,2}(\,\d{3})*(\.\d{0,2})?|[1-9]{1}\d{0,}(\.\d{0,2})?|0(\.\d{0,2})?|(\.\d{1,2}))$|^\(\$?([1-9]{1}\d{0,2}(\,\d{3})*(\.\d{0,2})?|[1-9]{1}\d{0,}(\.\d{0,2})?|0(\.\d{0,2})?|(\.\d{1,2}))\)$/;
        return regex.test(value);
    }
}

function isMoneyInCents(value) {
    var regex = /^[+-]?\d+(\.\d+)?([eE][+-]?\d+)?(c|\$)?$/;
    return regex.test(value);
}

function getUrlVars() {
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function isValidEmailAddress( emailAddress ) {
    var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regex.test( emailAddress );
}