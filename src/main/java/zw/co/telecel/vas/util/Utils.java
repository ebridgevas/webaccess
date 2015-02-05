package zw.co.telecel.vas.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author david@tekeshe.com
 */
public class Utils {

    public static void error(String message, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpServletResponse.getWriter().write(message);
    }
}
