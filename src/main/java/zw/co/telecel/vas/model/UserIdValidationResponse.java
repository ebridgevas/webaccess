package zw.co.telecel.vas.model;

/**
 * @author david@ebridgevas.com
 *
 */
public class UserIdValidationResponse {

    private final String userId;
    private final IdType idType;
    private final UserAction userAction;

    public UserIdValidationResponse( String userId,
                                     IdType idType,
                                     UserAction userAction ) {
        this.userId = userId;
        this.idType = idType;
        this.userAction = userAction;
    }

    public String getUserId() {
        return userId;
    }

    public IdType getIdType() {
        return idType;
    }

    public UserAction getUserAction() {
        return userAction;
    }
}
