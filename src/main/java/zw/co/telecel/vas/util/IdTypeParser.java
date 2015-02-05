package zw.co.telecel.vas.util;

import zw.co.telecel.vas.model.IdType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author david@ebridgevas.com
 *
 */
public class IdTypeParser {

    private final static Map<String, IdType> ID_TYPES;

    static {
        ID_TYPES = new HashMap<>();
        ID_TYPES.put("MOBILE_NUMBER", IdType.MOBILE_NUMBER);
        ID_TYPES.put("EMAIL_ADDRESS", IdType.EMAIL_ADDRESS);
        ID_TYPES.put("NATIONAL_ID",   IdType.NATIONAL_ID);
    }

    public static IdType parse(String input) {
        return ID_TYPES.get(input);
    }
}
