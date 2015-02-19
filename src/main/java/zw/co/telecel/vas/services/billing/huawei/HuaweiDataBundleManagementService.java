package zw.co.telecel.vas.services.billing.huawei;

import com.comverse_in.prepaid.ccws.ServiceLocator;
import com.comverse_in.prepaid.ccws.ServiceSoap;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.telecel.wfb.services.TestFacebookSession;
import com.telecel.wfb.services.TestFacebookSessionServiceLocator;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.message.token.UsernameToken;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author david@tekeshe.com
 */

import com.comverse_in.prepaid.ccws.ServiceLocator;
        import com.comverse_in.prepaid.ccws.ServiceSoap;
        import com.google.inject.Provider;
        import com.google.inject.Singleton;
        import org.apache.axis.EngineConfiguration;
        import org.apache.axis.client.Stub;
        import org.apache.axis.configuration.FileProvider;
        import org.apache.ws.security.WSConstants;
        import org.apache.ws.security.handler.WSHandlerConstants;
        import org.apache.ws.security.message.token.UsernameToken;

        import javax.xml.rpc.ServiceException;
        import java.net.MalformedURLException;
        import java.net.URL;

/**
 * @author david@tekeshe.com
 *
 * TODO inject test ip address / port : 172.17.1.28:8080
 * TODO inject live ip address / port : 172.17.1.19:8080
 * TODO inject file provider
 * TODO Persistent connection
 *
 */
public class HuaweiDataBundleManagementService  {

    private TestFacebookSession service;

    public HuaweiDataBundleManagementService () {

        System.out.print("Initializing HuaweiDataBundleManagementService ...");
//        EngineConfiguration config = new FileProvider("/prod/ebridge/wsdd/client_deploy_v2.wsdd");
        TestFacebookSessionServiceLocator locator = new TestFacebookSessionServiceLocator();
//        ServiceSoap prepaidService = null;
        try {
            service = locator.getTestFacebookSessionPort();
//            Stub axisPort = (Stub) prepaidService;
//            axisPort._setProperty(WSHandlerConstants.ACTION, WSHandlerConstants.ENCRYPT);
//            axisPort._setProperty(UsernameToken.PASSWORD_TYPE, WSConstants.PASSWORD_TEXT);
//            axisPort._setProperty(WSHandlerConstants.USER, "zsmart2");
//            axisPort._setProperty(WSHandlerConstants.PW_CALLBACK_CLASS, "PasswordCallback");


        } catch (ServiceException e) {
            e.printStackTrace();
        }

        System.out.println("Done.");
    }


}