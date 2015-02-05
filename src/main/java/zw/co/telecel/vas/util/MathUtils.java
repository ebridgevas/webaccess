package zw.co.telecel.vas.util;

import java.math.BigDecimal;

/**
 * david@ebridgevas.com
 *
 */
public class MathUtils {

    /**
     * BigDecimal factory.
     *
     * @param aDouble
     * @return
     */
    public static BigDecimal toBigDecimal(Double aDouble) {
        return new BigDecimal(aDouble);
    }
}
