package zw.co.telecel.vas.util.legacy;

import zw.co.telecel.vas.dto.WebAccessCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates web access command string to enum
 *
 * TODO Replace by constructors in WebAccessCommand enum
 */
public class WebAccessCommandParser {

    private static final Map<String, WebAccessCommand> COMMANDS;

    static {

        COMMANDS = new HashMap<String, WebAccessCommand>();

        COMMANDS.put("validate-user-id",        WebAccessCommand.VALIDATE_USER_ID);
        COMMANDS.put("authenticate-user",       WebAccessCommand.AUTHENTICATE_USER);
        COMMANDS.put("activate-user",           WebAccessCommand.ACTIVATE_USER);
        COMMANDS.put("deactivate-user",         WebAccessCommand.DEACTIVATE_USER);
        COMMANDS.put("delete-user",             WebAccessCommand.DELETE_USER);
        COMMANDS.put("set-user-password",       WebAccessCommand.SET_USER_PASSWORD);
        COMMANDS.put("reset-password",          WebAccessCommand.SET_USER_PASSWORD);
        COMMANDS.put("get-user-account",        WebAccessCommand.GET_USER_ACCOUNT);
        COMMANDS.put("get-mobile-account-list", WebAccessCommand.GET_MOBILE_ACCOUNT_LIST);
        COMMANDS.put("data-bundle-purchase",    WebAccessCommand.DATA_BUNDLE_PURCHASE);
        COMMANDS.put("data-bundle-price-list",  WebAccessCommand.DATA_BUNDLE_PRICE_LIST);
        COMMANDS.put("airtime-transfer",        WebAccessCommand.AIRTIME_TRANSFER);
        COMMANDS.put("voucher-recharge",        WebAccessCommand.VOUCHER_RECHARGE);
        COMMANDS.put("register-subscriber",     WebAccessCommand.REGISTER_SUBSCRIBER);
        COMMANDS.put("modify-subscriber",       WebAccessCommand.MODIFY_SUBSCRIBER);
        COMMANDS.put("subscriber-listing",      WebAccessCommand.SUBSCRIBER_LISTING);
        COMMANDS.put("reset-password",          WebAccessCommand.RESET_PASSWORD );
        COMMANDS.put("transaction-history",     WebAccessCommand.TRANSACTION_HISTORY );
        COMMANDS.put("subscriber-feedback",     WebAccessCommand.SUBSCRIBER_FEEDBACK);
    }

    public static WebAccessCommand parse(String command) {
        return COMMANDS.get(command);
    }
}
