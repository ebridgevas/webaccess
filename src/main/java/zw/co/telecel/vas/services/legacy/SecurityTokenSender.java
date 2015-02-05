package zw.co.telecel.vas.services.legacy;

import zw.co.telecel.vas.model.OutboundMsg;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * david@ebridgevas.com
 *
 */
public interface SecurityTokenSender {

    public void send(OutboundMsg outboundMsg) throws IOException;
}
