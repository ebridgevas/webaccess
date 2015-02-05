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
public class SubscriberListingCommandProcessor implements WebAccessCommandProcessor {

    @Override
    public void process( Map<String, String[]> parameters,
                         WebAccessCommand webAccessCommand,
                         Map<String, UserSession> userSessions,
                         HttpServletResponse response) throws IOException {

        try {

            /*  Paging */
            Integer startFrom = Integer.parseInt(parameters.get("start-from")[0]);
            Integer pageSize = Integer.parseInt(parameters.get("page-size")[0]);

            /*
             * Sorting
             *  TODO implement sorting
             *  { order-by:
             *    { column : nationalId, direction, desc },
             *    { column : emailAddress, direction : asc },
             *    { column : mobileNumber, direction : desc },
             *    { column : role, direction : des } }
             */


            /*
             * Search
             *    { filter :
             *    { column : nationalId, direction, desc },
             *    { column : emailAddress, direction : asc },
             *    { column : mobileNumber, direction : desc },
             *    { column : role, direction : des } }
             */
            HttpResponseWriter.write(UserDao.findAll(startFrom, pageSize), response);
        } catch (UserNotFoundException e) {
            HttpResponseWriter.write(e.getMessage(), response);
        } catch (DatabaseException e) {
            HttpResponseWriter.write(e.getMessage(), response);
        }
    }
}
