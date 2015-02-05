package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.UserDao;
import zw.co.telecel.vas.dto.UserAuthenticationResponse;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.*;
import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
import zw.co.telecel.vas.services.legacy.impl.util.ActivationMessageQueue;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.PasswordGenerator;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static zw.co.telecel.vas.util.Utils.error;

/**
 * david@ebridgevas.com
 */
public class ResetUserPasswordCommandProcessor implements WebAccessCommandProcessor {

//    private AccountManager accountManager;
    private SecurityTokenSender securityTokenSender;

    public ResetUserPasswordCommandProcessor( /* AccountManager accountManager, */
            SecurityTokenSender securityTokenSender) {

//        this.accountManager = accountManager;
        this.securityTokenSender = securityTokenSender;
    }

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse httpServletResponse) throws IOException {

        String mobileNumber = null;
        String password = null;

        try {
            mobileNumber = parameters.get("mobile-number")[0];
        } catch(Exception e) {
            error("A system error occurred and will be fixed. Please try again shortly .", httpServletResponse);
            return;
        }

        UserAuthenticationResponse response = new UserAuthenticationResponse();

        User user = null;

        try {
            user = UserDao.findUser(mobileNumber);
            user.setActivationCode( PasswordGenerator.getRandomPassword() );
            ServiceCommandRequest request
                    = new ServiceCommandRequest(
                            ServiceCommand.GENERATE_ACTIVATION_CODE,
                            mobileNumber,
                            IdType.MOBILE_NUMBER,
                            user.getActivationCode() );

            UserDao.update( request );

            new ActivationMessageQueue( securityTokenSender ).queue( user );

            response.setUserAction( UserAction.ENTER_ACTIVATION_CODE );
            response.setFullName( user.getFirstName() + " " + user.getSurname() );
            response.setFirstName(user.getFirstName());
            response.setMobileNumber( user.getMobileNumber() );
            response.setEmailAddress( user.getEmailAddress() );
            response.setNotificationAgent(user.getNotificationAgent());

            HttpResponseWriter.write(response, httpServletResponse);
        } catch (UserNotFoundException e) {

            response.setUserAction(UserAction.REGISTER_USER);
        } catch (DatabaseException e) {

            error("Something went wrong. Please try again shortly.", httpServletResponse);
            return;
        }
    }

//    public Boolean isPostpaid(String msisdn) {
//
//
//        String cos = accountManager.getClassOfService(msisdn);
//
//        return      cos == null
//                || "STAFF_COS".equalsIgnoreCase(cos)
//                || "POST_COS".equalsIgnoreCase(cos)
//                || "VIP".equalsIgnoreCase(cos);
//    }
}
