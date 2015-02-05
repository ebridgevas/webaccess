package zw.co.telecel.vas.util.legacy;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 9/25/13
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponseWriter {

    /**
     * Write to HttpServletResponse
     * @param <T> t
     * @param response
     * @throws java.io.IOException
     */
    public static <T> void write(T t, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Writer writer = null;
        try {
            writer = response.getWriter();
            String payload = toJson ( t );
            System.out.println("#### " + payload );
            writer.write( payload );
            writer.flush();
        } finally {
            writer.close();
        }
    }


    /**
     * Marshal object t to a JSon string.
     * @param t
     * @param <T>
     * @return
     */
    private static <T> String toJson(T t) {
        if (t == null) {
            return "";
        }
        Gson gson = new Gson();
        return gson.toJson(t);
    }
}
