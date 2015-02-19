package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.UserDao;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.UserSession;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * david@tekeshe.com
 *
 */
public class FindUserCommandProcessor implements WebAccessCommandProcessor {

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse response) throws IOException {

        try {

            HttpResponseWriter.write(UserDao.findUser(parameters.get("mobile-number")[0]), response);

        } catch (UserNotFoundException e) {
            HttpResponseWriter.write(e.getMessage(), response);
        } catch (DatabaseException e) {
            HttpResponseWriter.write(e.getMessage(), response);
        }
    }
}
