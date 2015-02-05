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

//            String mobileNumber = parameters.get("mobileNumber")[0];
            String sessionId = parameters.get("sessionId")[0];
            System.out.println("session id requested : " + sessionId);
            System.out.println("sessions count : " + userSessions.size());
            System.out.println("sessions  : " + userSessions);
            System.out.println("session  : " + userSessions.get(sessionId));
            UserSession userSession = userSessions.get(sessionId);
            if (userSession != null ) {
//                if (userSessions.get(mobileNumber).getSessionId().equals(sessionId) ) {
                  String userId = userSession.getUserId();
                    HttpResponseWriter.write(UserDao.findUser(userId), response);
//                }  else {
//                    HttpResponseWriter.write("You are not logged on.", response);
//                }
            } else {
                HttpResponseWriter.write("Invalid session.", response);
            }
        } catch (UserNotFoundException e) {
            HttpResponseWriter.write(e.getMessage(), response);
        } catch (DatabaseException e) {
            HttpResponseWriter.write(e.getMessage(), response);
        }
    }
}
