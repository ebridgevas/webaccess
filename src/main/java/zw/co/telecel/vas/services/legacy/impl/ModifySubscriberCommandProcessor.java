package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.UserDao;
import zw.co.telecel.vas.dto.UserAuthenticationResponse;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.*;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.PasswordGenerator;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;
import zw.co.telecel.vas.util.Utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * david@ebridgevas.com
 *
 */
public class ModifySubscriberCommandProcessor implements WebAccessCommandProcessor {

    // TODO replace with getters in the ServiceCommand enum
    private static final Map<String, ServiceCommand> SERVICE_COMMANDS;
    static {
        SERVICE_COMMANDS = new HashMap<>();
        SERVICE_COMMANDS.put("register_user", ServiceCommand.REGISTER_USER);
        SERVICE_COMMANDS.put("activate_user", ServiceCommand.ACTIVATE_USER);
        SERVICE_COMMANDS.put("change_user_password", ServiceCommand.CHANGE_USER_PASSWORD);
        SERVICE_COMMANDS.put("reset_user_password", ServiceCommand.RESET_USER_PASSWORD);
        SERVICE_COMMANDS.put("deactivate-user", ServiceCommand.DEACTIVATE_USER);
        SERVICE_COMMANDS.put("delete-user", ServiceCommand.DELETE_USER);
    }

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse httpServletResponse) throws IOException {

        String mobileNumber = null;

        try {
            mobileNumber = parameters.get("mobile-number")[0];
        } catch(Exception e) {
            Utils.error("A system error occurred and will be fixed. Please try again shortly .", httpServletResponse);
            return;
        }

        try {

            ServiceCommand serviceCommand = SERVICE_COMMANDS.get(parameters.get("service-command")[0]);

            UserDao.update(
                    new ServiceCommandRequest(  serviceCommand,
                            mobileNumber,
                            IdType.MOBILE_NUMBER,
                            ""));

//            HttpResponseWriter.write(UserDao.findAll(0, 20, Boolean.TRUE), httpServletResponse);
            HttpResponseWriter.write("GRANT_ACCESS", httpServletResponse);

        } catch (DatabaseException e) {
            Utils.error("System error occurred. Please try again.", httpServletResponse);
        } /* catch (UserNotFoundException e) {
            Utils.error("Failed to register user: " + e.getMessage(), httpServletResponse);
        } */


        /*
        String sessionId = null;
        String password = null;

        try {
            sessionId = parameters.get("sessionId")[0];
            password = parameters.get("password")[0];
        } catch(Exception e) {
            Utils.error("A system error occurred and will be fixed. Please try again shortly .", httpServletResponse);
            return;
        }

        UserSession userSession = userSessions.get(sessionId);
        User user = null;
        String mobileNumber = null;
        if (userSession != null ) {
            String userId = userSession.getUserId();
            try {
                user = UserDao.findUser(userId);

                if (password == null || ! password.equals(user.getPassword())) {
                    HttpResponseWriter.write("Invalid user password.", httpServletResponse);
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
        } else {
            HttpResponseWriter.write("Invalid session id. Try again.", httpServletResponse);
            return;
        }

        try {

            ServiceCommand serviceCommand = SERVICE_COMMANDS.get(parameters.get("service-command")[0]);

            Boolean updated = UserDao.update(
                    new ServiceCommandRequest(  serviceCommand,
                                                mobileNumber,
                                                IdType.MOBILE_NUMBER,
                                                ""));

            UserAuthenticationResponse response = null;

            if ( updated ) {

                sessionId =
                        user.getMobileNumber() +
                                new PasswordGenerator().getRandomPassword();
                userSessions.put(sessionId, new UserSession(user.getMobileNumber(), sessionId));

                response = new UserAuthenticationResponse( UserAction.GRANT_ACCESS,
                                                           serviceCommand.getDescription() + " successful.",
                                                           sessionId);

                if ( serviceCommand == ServiceCommand.DEACTIVATE_USER ) {
                    response.setUserAction(UserAction.LOG_OUT_USER);
                }

            } else {

                response = new UserAuthenticationResponse(
                        UserAction.SET_PASSWORD_RETRY,
                        "Failed to modify user account. Please retry.","");
            }

            HttpResponseWriter.write(response, httpServletResponse);
        } catch (DatabaseException e) {
            Utils.error("System error occurred. Please try again.", httpServletResponse);
        }

        */
    }
}
