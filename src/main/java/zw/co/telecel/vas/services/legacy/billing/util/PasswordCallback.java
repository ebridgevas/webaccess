package zw.co.telecel.vas.services.legacy.billing.util;

/**
 * david@ebridgevas.com
 */

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * PWCallback for the Client
 */
public class PasswordCallback implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof WSPasswordCallback) {
                WSPasswordCallback pc =
                        (WSPasswordCallback)callbacks[i];

                if ("zsmart2".equals(pc.getIdentifer())) {
                    pc.setPassword("zsmart2");
                }
            }
            else {
                throw new UnsupportedCallbackException(callbacks[i],
                        "Unrecognized Callback");
            }
        }
    }
}
