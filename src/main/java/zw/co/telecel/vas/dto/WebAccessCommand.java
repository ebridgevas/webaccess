package zw.co.telecel.vas.dto;

/**
 * david@ebridgevas.com
 *
 * Commands that are raised by Web Access user actions.
 */
public enum WebAccessCommand {

    VALIDATE_USER_ID,
    AUTHENTICATE_USER,
    ACTIVATE_USER,
    DEACTIVATE_USER,
    DELETE_USER,
    SET_USER_PASSWORD,
    RESET_PASSWORD,
    GET_USER_ACCOUNT,
    GET_MOBILE_ACCOUNT_LIST,
    DATA_BUNDLE_PURCHASE,
    DATA_BUNDLE_PRICE_LIST,
    AIRTIME_TRANSFER,
    VOUCHER_RECHARGE,
    REGISTER_SUBSCRIBER,
    MODIFY_SUBSCRIBER,
    SUBSCRIBER_LISTING,
    TRANSACTION_HISTORY,
    SUBSCRIBER_FEEDBACK;
}
