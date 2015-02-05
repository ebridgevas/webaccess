/**
 * david@ebridgevas.com
 */
var accounts;

function getMobileAccountList() {

    $.getJSON("/webaccess?service-command=get-mobile-account-list&sessionId=" +
        userSession.getSessionId(),

        function(data){

            // accounts = JSON.parse(data);
            accounts = data;

            accountMap();
            createAccountListing( );
            $('.portlet').css('display', 'none');
            $('#account-listing-portlet').css('display','block');

            $('#purchase-button').css('display', 'none');
            $('#cancel-purchase-button').css('display', 'none');
        }
    );
}

function accountMap() {

    var map = new Object();
    $.each(accounts, function(index, account) {
        map[account.walletId] = account;
    });

    userSession.setAccountMap( map );
}

function createAccountListing() {

    $("#accounts-list").empty();

    $('#account-type').text(( "POSTPAID" == userSession.accountType ? "Postpaid Account" : "Prepaid Account") +
                                " :: " + toShortMobileNumberFormat( userSession.mobileNumber));

    $.each(accounts, function(index, account) {
        createAccountRow(account);
    });
}

function createAccountRow( account ) {

    var expiryDate =  new XDate(account.expiryDate).toString("d MMM yy");

    if (account.walletDescription == "Internet browsing wallet") {
      account.walletDescription = "Data Bundles";  
    }

    $("#accounts-list").append(
        " <ul style='padding-left: 10px;'> " +
            "     <li style='min-width: 100px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;'><span style='font-weight: normal; font-size: 12px;'>" + account.walletDescription  + "<span></li> " +
            "     <li style='min-width: 50px;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;'><span style='font-weight: normal; font-size: 12px;'>" + expiryDate + "</scan></li> " +
            "     <li style='width: 70px;text-align: right;border-bottom: 1px solid lightgray;padding: 0px;margin: 0px;margin-left: -5px;'><span style='font-weight: normal; font-size: 12px;'>" + formatCurrencyOrData(account)  +  "</scan></li> " +
            " </ul> "
    );
}

function testAccountListing() {
    return [{'walletId':'1','walletDescription':'Main Wallet','balance':'120.50','expiryDate':'2014/02/18'},{'walletId':'2','walletDescription':'Data Bundles','balance':'120.50','expiryDate':'2014/02/18'},{'walletId':'3','walletDescription':'Data Bonus','balance':'120.50','expiryDate':'2014/02/18'}];

}

