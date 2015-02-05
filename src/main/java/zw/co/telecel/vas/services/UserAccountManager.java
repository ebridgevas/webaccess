package zw.co.telecel.vas.services;

import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.UserSession;
import zw.co.telecel.vas.util.legacy.WebAccessCommandParser;
import zw.co.telecel.vas.services.legacy.impl.*;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@javax.servlet.annotation.WebServlet(
    name = "userAccountManager",
    value = {"/userAccountManager"},
    asyncSupported = true,
    initParams = {
            @WebInitParam(name = "thread-pool-size", value = "3")
    }
)
public class UserAccountManager extends HttpServlet {

    public static final Map<String, UserSession> USER_SESSIONS;
    static {
        USER_SESSIONS = new HashMap<String, UserSession>();
    }

    private static final Map<WebAccessCommand, WebAccessCommandProcessor> PROCESSORS;

    static {

        PROCESSORS = new HashMap<>();

        PROCESSORS.put( WebAccessCommand.VALIDATE_USER_ID,
                new ValidateUserIdCommandProcessor(new DatabaseBackedSecurityTokenSender()));
        PROCESSORS.put(WebAccessCommand.GET_USER_ACCOUNT, new FindUserCommandProcessor());
        PROCESSORS.put(WebAccessCommand.AUTHENTICATE_USER,
                new AuthenticateUserCommandProcessor( new DatabaseBackedSecurityTokenSender() ));

        PROCESSORS.put(WebAccessCommand.ACTIVATE_USER,     new ActivateUserCommandProcessor());
        PROCESSORS.put(WebAccessCommand.SET_USER_PASSWORD, new SetUserPasswordCommandProcessor());


        PROCESSORS.put(WebAccessCommand.REGISTER_SUBSCRIBER,
                new RegisterCommandProcessor(new DatabaseBackedSecurityTokenSender()));

        PROCESSORS.put(WebAccessCommand.MODIFY_SUBSCRIBER,new RegisterCommandProcessor(null));
        PROCESSORS.put(WebAccessCommand.SUBSCRIBER_LISTING, new SubscriberListingCommandProcessor());
        PROCESSORS.put(WebAccessCommand.DEACTIVATE_USER, new ModifySubscriberCommandProcessor());
        PROCESSORS.put(WebAccessCommand.DELETE_USER, new ModifySubscriberCommandProcessor());
        PROCESSORS.put(WebAccessCommand.RESET_PASSWORD,
                            new ResetUserPasswordCommandProcessor(
                                    new DatabaseBackedSecurityTokenSender()));
        PROCESSORS.put(WebAccessCommand.SUBSCRIBER_FEEDBACK, new SubscriberFeedbackProcessor());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /* Get Command. */
        WebAccessCommand webAccessCommand = WebAccessCommandParser.parse(request.getParameter("service-command"));

        /* Write output. */
        PROCESSORS.get(webAccessCommand).process(request.getParameterMap(), webAccessCommand, USER_SESSIONS, response);
    }
}
