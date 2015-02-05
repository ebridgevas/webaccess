package zw.co.telecel.vas.services.billing.prepaid;

import com.comverse_in.prepaid.ccws.ServiceLocator;
import com.comverse_in.prepaid.ccws.ServiceSoap;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.log4j.Category;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.message.token.UsernameToken;

import javax.inject.Provider;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author david@tekeshe.com
 *
 * TODO - Critical - Exception handling
 * TODO - Important - Logging
 * TODO inject live endpoint: http://172.17.1.19:8080/ocswebservices/services/zimbabweocsWebServices?wsdl
 * TODO inject test endpoint http://172.17.1.28:8080/ocswebservices/services/zimbabweocsWebServices?wsdl
 * TODO inject USER_ID
 * TODO inject PASSWORD_CALLBACK
 */
public class ServiceSoapProvider implements Provider<ServiceSoap> {

    private static Category CAT = Category.getInstance(ServiceSoapProvider.class);

    private final static String CONFIG = "/prod/ebridge/wsdd/client_deploy.wsdd";
    private final static String ENDPOINT
                                    = "http://172.17.1.19:8080/ocswebservices/services/zimbabweocsWebServices?wsdl";
    private final static String USER_ID = "zsmart2";
    private final static String PASSWORD_CALLBACK = "com.ebridgevas.in.util.PasswordCallback";

    @Override
    public ServiceSoap get() {

        try {

            ServiceSoap prepaidService
                    = new ServiceLocator( new FileProvider(CONFIG)).getServiceSoap(new URL(ENDPOINT));

            Stub axisPort = (Stub) prepaidService;
            axisPort._setProperty(WSHandlerConstants.ACTION, WSHandlerConstants.ENCRYPT);
            axisPort._setProperty(UsernameToken.PASSWORD_TYPE, WSConstants.PASSWORD_TEXT);
            axisPort._setProperty(WSHandlerConstants.USER, USER_ID );
            axisPort._setProperty(WSHandlerConstants.PW_CALLBACK_CLASS, PASSWORD_CALLBACK );

            return prepaidService;
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
}
