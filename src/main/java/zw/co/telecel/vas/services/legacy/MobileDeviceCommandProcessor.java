package zw.co.telecel.vas.services.legacy;

import java.util.Map;

/**
 * david@tekeshe.com
 *
 */
public interface MobileDeviceCommandProcessor<T> {

    T process(Map<String, String[]> command);
}
