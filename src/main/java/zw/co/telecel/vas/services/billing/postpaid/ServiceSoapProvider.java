package zw.co.telecel.vas.services.billing.postpaid;

import com.ztesoft.zsmart.bss.ws.customization.zimbabwe.WebServices;
import com.ztesoft.zsmart.bss.ws.customization.zimbabwe.WebServicesServiceLocator;
import org.apache.log4j.Category;

import javax.inject.Provider;
import javax.xml.rpc.ServiceException;

/**
 * @author david@tekeshe.com
 */
public class ServiceSoapProvider implements Provider<WebServices> {

    private static Category CAT = Category.getInstance(ServiceSoapProvider.class);

    @Override
    public WebServices get() {
        try {
            return new WebServicesServiceLocator().getWebServices();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
}
