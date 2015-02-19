package zw.co.telecel.vas.model;

/**
 * @author david@ebridgevas.com
 *
 */
public enum UserAction {

    ENTER_PASSWORD,
    ENTER_ACTIVATION_CODE,
    REGISTER_USER,
    SET_PASSWORD,
    SET_PASSWORD_RETRY,
    INVALID_PASSWORD,
    ACCOUNT_LOCKED,
    ENTER_VALID_ACTIVATION_CODE,
    GRANT_ACCESS,
    AUTHENTICATED,
    RETRY,
    SYSTEM_ERROR,
    LOG_OUT_USER;
}
