package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.UserDao;
import com.ebridgevas.model.*;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.*;
import zw.co.telecel.vas.model.ServiceCommand;
import zw.co.telecel.vas.model.ServiceCommandRequest;
import zw.co.telecel.vas.model.UserSession;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author david@tekeshe.com
 *
 */
public class ActivateUserCommandProcessor implements WebAccessCommandProcessor {

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse httpServletResponse ) throws IOException {

        String userId = parameters.get("mobile-number")[0];
        String activationCode = parameters.get("activation-code")[0];

        UserAction userAction = null;
        User user = null;

        try {

            user = UserDao.findUser(userId);
            /*
            String userId,
                                  IdType idType,
                                  String payload
             */
            System.out.println("activation-request : { user-id : " + userId + ", supplied-code : "
                    + activationCode.replaceAll(" ", "") +
                    ", generated-code : " + user.getActivationCode().replaceAll(" ", "") + "}");

            if (activationCode.replaceAll(" ", "").equals(user.getActivationCode().replaceAll(" ", ""))) {

                System.out.println("activation-response : ACK");

                ServiceCommandRequest request
                        = new ServiceCommandRequest(
                                ServiceCommand.ACTIVATE_USER,
                                userId,
                                IdType.MOBILE_NUMBER,
                                "");
                UserDao.update(request);

                request
                        = new ServiceCommandRequest(
                        ServiceCommand.RESET_USER_PASSWORD,
                        userId,
                        IdType.MOBILE_NUMBER,
                        "");
                UserDao.update(request);

                userAction = UserAction.SET_PASSWORD;
            } else {
                System.out.println("activation-response : NACK");
                userAction = UserAction.ENTER_VALID_ACTIVATION_CODE;
            }
        } catch (UserNotFoundException e) {
            userAction = UserAction.REGISTER_USER;
        } catch (DatabaseException e) {
            throw new IOException( e.getMessage() );
        }

        UserIdValidationResponse response =
                new UserIdValidationResponse(userId, null, userAction);

        HttpResponseWriter.write(response, httpServletResponse);
    }
}
