package zw.co.telecel.vas.model;

/**
 * david@ebridgevas.com
 *
 */
public class UserSession {

    private final String userId;
    private final String sessionId;

    public UserSession( String userId,
                        String sessionId) {

        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
