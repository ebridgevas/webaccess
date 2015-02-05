package zw.co.telecel.vas.services.util;

import com.google.inject.Inject;
import zw.co.telecel.dto.DataBundleDTO;

/**
 * @author david@tekeshe.com
 */
public class DataBundleConfigWrapper {

    @Inject
    private ConfigurationService configurationService;

    public DataBundleDTO dataBundle( String productCode ) {

        configurationService.config().getDataBundles();
        return null;
    }
}
