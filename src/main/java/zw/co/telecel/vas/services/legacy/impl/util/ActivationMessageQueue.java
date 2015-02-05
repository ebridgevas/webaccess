package zw.co.telecel.vas.services.legacy.impl.util;

import zw.co.telecel.vas.model.OutboundMsg;
import zw.co.telecel.vas.model.User;
import zw.co.telecel.vas.services.legacy.SecurityTokenSender;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author david@tekeshe.com
 *
 */
public class ActivationMessageQueue {

    private SecurityTokenSender securityTokenSender;

    public ActivationMessageQueue(SecurityTokenSender securityTokenSender) {
        this.securityTokenSender = securityTokenSender;
    }

    public void queue(User user) throws IOException {

        String payload = null;
        if ( "SMS".equalsIgnoreCase(user.getNotificationAgent()) ) {
            payload = "Your TeleVAS activation code is: " + user.getActivationCode() + "\n"+
                       "Visit http://vas.telecel.co.zw";
        } else {

//            String url =  "http://192.1.1.15/index.html?serviceCommand=activate&" +
//                    "userId=" + user.getEmailAddress() + "&" + "activationCode=" + user.getActivationCode();

            payload = "Hi " + user.getFirstName() + ",\n\n" +
                    " Your TeleVAS activation code is: " + user.getActivationCode() + "\n\n"+
                    " Please activate your account at http://vas.telecel.co.zw" +
                            "\n\nThanks\nTeleVAS";
        }

        OutboundMsg outboundMsg =
                new OutboundMsg(
                        new BigInteger("" + System.currentTimeMillis()),
                        "QUEUED",
                        "SMS".equalsIgnoreCase(user.getNotificationAgent()) ? "SMS" : "EMAIL",
                        "SMS".equalsIgnoreCase(user.getNotificationAgent()) ?
                                user.getMobileNumber() :
                                user.getEmailAddress(),
                        "",
                        payload );

        securityTokenSender.send( outboundMsg );
    }
}
