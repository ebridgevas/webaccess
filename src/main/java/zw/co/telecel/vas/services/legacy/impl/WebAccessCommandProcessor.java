package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.UserSession;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * david@ebridgevas.com
 *
 */
public interface WebAccessCommandProcessor {

    void process(
            Map<String, String[]> parameters,
            WebAccessCommand webAccessCommand,
            Map<String, UserSession> userSessions,
            HttpServletResponse response
    ) throws IOException;
}
