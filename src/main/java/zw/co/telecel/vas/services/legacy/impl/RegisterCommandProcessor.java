package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.UserDao;

import zw.co.telecel.vas.dto.UserAuthenticationResponse;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.User;
import zw.co.telecel.vas.model.UserSession;
import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
import zw.co.telecel.vas.services.legacy.billing.util.SystemException;
import zw.co.telecel.vas.services.legacy.impl.util.ActivationMessageQueue;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.PasswordGenerator;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * david@ebridgevas.com
 *
 */
public class RegisterCommandProcessor implements WebAccessCommandProcessor {

    private SecurityTokenSender securityTokenSender;

    public RegisterCommandProcessor( SecurityTokenSender securityTokenSender ) {
        this.securityTokenSender = securityTokenSender;
    }

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse httpServletResponse ) throws IOException {

        User user = null;
        try {
            user = new User(

                    parameters.get("subscriberEmailAddress")[0],
                    parameters.get("subscriberMobileNumber")[0],
                    null,
                    parameters.get("subscriberFirstName")[0],
                    parameters.get("subscriberSurname")[0],
                    (parameters.get("subscriberRole") != null ? parameters.get("subscriberRole")[0] : "USER" ),
                    parameters.get("communicationAgent")[0],
                    "enterActivationCode",
                    "",
                    (webAccessCommand == WebAccessCommand.REGISTER_SUBSCRIBER ?
                            PasswordGenerator.getRandomPassword() : null),
                    parameters.get("subscriberSecurityQuestion")[0],
                    parameters.get("subscriberSecurityAnswer")[0]
                );
            user.setPassword( parameters.get("password") != null ? parameters.get("password")[0] : "*" );
        } catch (Exception e) {

            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("Failed to create user. Please try after 15 minutes.");

            return;
        }

        try {

            if ( webAccessCommand == WebAccessCommand.REGISTER_SUBSCRIBER ) {
                UserDao.persist(user);
                new ActivationMessageQueue(securityTokenSender).queue(user);
            } else {
                UserDao.update(user);
            }
            user = UserDao.findUser( user.getMobileNumber() );

            UserAuthenticationResponse response = new UserAuthenticationResponse();
            response.setFullName( user.getFirstName() + " " + user.getSurname() );
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getSurname());
            response.setMobileNumber(user.getMobileNumber());
            response.setEmailAddress(user.getEmailAddress());
            response.setRole(user.getRole());
            response.setSecurityQuestion(user.getSecurityQuestion());
            response.setSecurityAnswer(user.getSecurityAnswer());
            response.setNotificationAgent(user.getNotificationAgent());
            response.setPassword(user.getPassword());

            HttpResponseWriter.write(response, httpServletResponse);

        } catch (DatabaseException e ) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write(e.getMessage());
        } catch ( Exception e ) {
            // TODO Write to database
            e.printStackTrace();
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("A system error occurred. Please try after 15 minutes.");
        }
    }
}
