package zw.co.telecel.vas.services.legacy.billing.util;

import zw.co.telecel.vas.model.DataBundlePrice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * david@ebridgevas.com
 *
 */
public class AccountManagerUtils {

    public static final Map<String, DataBundlePrice> DATA_BUNDLES;

    public static final Long USSD_TIMEOUT = new Long(30000);

    private static final Map<String, String> BUNDLE_TYPES;

    static {
        BUNDLE_TYPES = new HashMap<String, String>();

        BUNDLE_TYPES.put("1","5 MB");
        BUNDLE_TYPES.put("2","10 MB");
        BUNDLE_TYPES.put("3","80 MB");
        BUNDLE_TYPES.put("4","150 MB");
        BUNDLE_TYPES.put("5","160 MB");
        BUNDLE_TYPES.put("6","400 MB");
        BUNDLE_TYPES.put("7","1000 MB");
        BUNDLE_TYPES.put("8","2000 MB");
    }

    static {
        DATA_BUNDLES = new HashMap<String, DataBundlePrice>();
        DATA_BUNDLES.put("1", new DataBundlePrice(   "1", "1= 50c    for 5MB", new BigDecimal("5.00"), new BigDecimal("0.108"), new BigDecimal("0.50"), new BigDecimal("0.55"), null, new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("2", new DataBundlePrice(   "2", "2= $1     for 10MB", new BigDecimal("10.00"), new BigDecimal("0.10"), new BigDecimal("1.00"), new BigDecimal("1.10"), null, new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("3", new DataBundlePrice(   "3", "3= $3.00  for 80MB", new BigDecimal("80.00"), new BigDecimal("0.08"), new BigDecimal("3.00"), new BigDecimal("8.80"), null, new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("4", new DataBundlePrice(   "4", "5= $5.00  for 150MB", new BigDecimal("150.00"), new BigDecimal("0.08"), new BigDecimal("5.00"), new BigDecimal("16.50"), null, new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("5", new DataBundlePrice(   "5", "5= $10.00 for 160MB", new BigDecimal("160.00"), new BigDecimal("0.06"), new BigDecimal("10.00"), new BigDecimal("17.60"), new BigDecimal("17.60"), new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("6", new DataBundlePrice(   "6", "6= $20.00 for 400MB", new BigDecimal("400.00"), new BigDecimal("0.05"), new BigDecimal("20.00"), new BigDecimal("44.00"), new BigDecimal("44.00"), new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("7", new DataBundlePrice(   "7", "8= $45.00 for 1000MB", new BigDecimal("1000.00"), new BigDecimal("0.05"), new BigDecimal("45.00"), new BigDecimal("110.00"), new BigDecimal("110.00"), new BigDecimal("0.11"), 30));
        DATA_BUNDLES.put("8", new DataBundlePrice(   "8", "8= $75.00 for 2000MB", new BigDecimal("2000.00"), new BigDecimal("0.04"), new BigDecimal("75.00"), new BigDecimal("220.00"), new BigDecimal("220.00"), new BigDecimal("0.11"), 30));
    }

    public static BigDecimal getBundleSizeFor(String productCode) {

        // TODO implement
        return null;
    }

    public static DataBundlePrice getDataBundleByType(String productCode) {
        return DATA_BUNDLES.get( productCode );
    }

}
