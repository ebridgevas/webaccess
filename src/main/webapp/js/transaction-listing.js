/**
 * david@ebridgevas.com
 */
var accounts;

function getTransactionList() {

//    $.getJSON("/webaccess?service-command=get-mobile-account-list&sessionId=" + sessionId, function(data){
//        accounts = data;
//        createAccountListing(data);
//    });
    accounts = testTransactionListing();
    createTransactionListing();
}

function createTransactionListing() {

    $("#transaction-list").empty();

    $.each(accounts, function(index, account) {
        createTransactionRow(account);
    });
}

function createTransactionRow( account ) {

    var expiryDate =  new XDate(account.expiryDate).toString("d MMM yy");

    $("#transaction-list").append(
        " <ul> " +
            "     <li style='min-width: 120px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;'><span style='font-weight: normal; font-size: 14px;'>" + account.walletDescription  + "<span></li> " +
            "     <li style='min-width: 70px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;'><span style='font-weight: normal; font-size: 14px;'>" + expiryDate + "</scan></li> " +
            "     <li style='width: 70px;text-align: right;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;'><span style='font-weight: normal; font-size: 14px;'>" + formatCurrencyOrData(account)  +  "</scan></li> " +
            " </ul> "
    );
}

function testTransactionListing() {
    return [{"walletId":"1","walletDescription":"Bundle purchase","balance":"120.50","expiryDate":"2014/02/18"},{"walletId":"2","walletDescription":"Transfer IN","balance":"120.50","expiryDate":"2014/02/18"},{"walletId":"3","walletDescription":"Transfer OUT","balance":"120.50","expiryDate":"2014/02/18"}];

}