package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.UserDao;
import zw.co.telecel.vas.dto.UserAuthenticationResponse;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.*;
import static zw.co.telecel.vas.util.Utils.error;

import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.PasswordGenerator;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author david@ebridgevas.com
 *
 */
public class SetUserPasswordCommandProcessor implements WebAccessCommandProcessor {

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse httpServletResponse) throws IOException {

        String sessionId = null;
        String password = null;
        String newPassword = null;

        String mobileNumber = null;

        try {
//            if ( parameters.get("sessionId") != null ) {
//                sessionId = parameters.get("sessionId")[0];
//            }
            if ( parameters.get("mobile-number") != null ) {
                mobileNumber = parameters.get("mobile-number")[0];
            }
            if ( parameters.get("password") != null ) {
                password = parameters.get("password")[0];
            }
            newPassword = parameters.get("new-password")[0];
        } catch(Exception e) {
            error("A system error occurred and will be fixed. Please try again shortly .", httpServletResponse);
            return;
        }

        User user = null;
//
//        if ( sessionId != null ) {
//            UserSession userSession = userSessions.get( sessionId );
//            if (userSession != null ) {
//                mobileNumber = userSession.getUserId();
//            } else {
//                HttpResponseWriter.write("Invalid session id. Try again.", httpServletResponse);
//                return;
//            }
//        }

        try {
            user = UserDao.findUser(mobileNumber);

            /* Validate password. */
            if ( password != null && ! password.equals(user.getPassword()) ) {
                HttpResponseWriter.write("Invalid user password.", httpServletResponse );
                return;
            }
            mobileNumber = user.getMobileNumber();
        } catch (UserNotFoundException e) {
            HttpResponseWriter.write("Invalid user.", httpServletResponse );
            return;
        } catch (DatabaseException e) {
            HttpResponseWriter.write("System error. Try again.", httpServletResponse );
            return;
        }

        try {

            Boolean updated = UserDao.update(
                    new ServiceCommandRequest( ServiceCommand.CHANGE_USER_PASSWORD,
                                               mobileNumber,
                                               IdType.MOBILE_NUMBER,
                                               newPassword));

            UserAuthenticationResponse response = null;

            if ( updated ) {

                /* Create a user session. */
                sessionId =
                        mobileNumber +
                        new PasswordGenerator().getRandomPassword();
                userSessions.put(sessionId, new UserSession(mobileNumber, sessionId));

                response = new UserAuthenticationResponse( UserAction.GRANT_ACCESS, "Password successfully changed.", sessionId);
                response.setFullName( user.getFirstName() + " " + user.getSurname() );
                response.setFirstName ( user.getFirstName() );
                response.setMobileNumber( user.getMobileNumber() );
                response.setAccountType(
                        isPostpaid(user.getMobileNumber()) ?
                                AccountTye.POSTPAID : AccountTye.PREPAID );

                response.setLastName( user.getSurname() );
                response.setEmailAddress( user.getEmailAddress() );
                response.setRole( user.getRole() );
                response.setSecurityQuestion( user.getSecurityQuestion());
                response.setSecurityAnswer( user.getSecurityAnswer() );
                response.setNotificationAgent( user.getNotificationAgent() );
                response.setPassword( user.getPassword() );

            } else {

                // TODO figure out what happened and advice user accordingly.
                // TODO Alert Customer Care to follow up with a call after resolution.
                response = new UserAuthenticationResponse(
                                    UserAction.SET_PASSWORD_RETRY,
                                    "Wrong password. Please retry.",
                                    "");
            }

            HttpResponseWriter.write(response, httpServletResponse);
        } catch (DatabaseException e) {
            error("System error occurred. Please try again.", httpServletResponse);
        }
    }

    public Boolean isPostpaid(String msisdn) {

        // TODO get class of service

        String cos = null; // accountManager.getClassOfService(msisdn);

        return      cos == null
                || "STAFF_COS".equalsIgnoreCase(cos)
                || "POST_COS".equalsIgnoreCase(cos)
                || "VIP".equalsIgnoreCase(cos);
    }
}
