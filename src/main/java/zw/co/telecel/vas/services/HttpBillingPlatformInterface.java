package zw.co.telecel.vas.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.vas.dao.UserDao;
import zw.co.telecel.vas.dto.WebAccessCommand;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.model.User;
import zw.co.telecel.vas.services.billing.inject.BillingPlatformInterfaceModule;
import zw.co.telecel.vas.services.legacy.billing.BillingPlatformInterface;
import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
import zw.co.telecel.vas.util.legacy.WebAccessCommandParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import static java.util.UUID.randomUUID;

import static zw.co.telecel.vas.services.util.Util.*;

/**
 * david@tekeshe.com
 */

@javax.servlet.annotation.WebServlet ( name = "webAccess", value = {"/billing-platform"}, asyncSupported = true,
                                        initParams = { @WebInitParam(name = "thread-pool-size", value = "3") } )
public class HttpBillingPlatformInterface extends HttpServlet {

    private BillingPlatformInterface billingPlatformInterface;

    /**
     *
     * Init resources
     *
     * @throws javax.servlet.ServletException
     */
    public void init() throws ServletException {

        Injector injector = Guice.createInjector(new BillingPlatformInterfaceModule());
        billingPlatformInterface = injector.getInstance(BillingPlatformInterface.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            WebAccessCommand webAccessCommand = WebAccessCommandParser.parse(request.getParameter("service-command"));
            Map<String, String[]> params = request.getParameterMap();
            String mobileNumber = params.get("mobile-number") != null ? params.get("mobile-number")[0] : null;

            switch (webAccessCommand) {

                case GET_MOBILE_ACCOUNT_LIST:
                    HttpResponseWriter.write( billingPlatformInterface.balances( mobileNumber ), response );
                    break;

                case DATA_BUNDLE_PRICE_LIST:
                    HttpResponseWriter.write( billingPlatformInterface.dataBundleList(), response );
                    break;

                case DATA_BUNDLE_PURCHASE:
                    String uuid = randomUUID().toString().replaceAll("-","").toUpperCase();
                    String productCode = params.get("product-code")[0];
                    String beneficiaryId = params.get("beneficiary-id")[0];
                    String paymentMethod = params.get("payment-method")[0];
                    String oneTimePassword = params.get("one-time-password")[0];
                    BalanceDTO[] dataBundlePurchaseResult
                            = billingPlatformInterface.dataBundlePurchase(
                                                            uuid, mobileNumber, productCode, beneficiaryId,
                                                            paymentMethod, oneTimePassword);
                    DataBundleDTO dataBundle = billingPlatformInterface.dataBundleList().get(productCode);
                    String[] result = dataBundleResponse (dataBundlePurchaseResult, dataBundle, paymentMethod );
                    HttpResponseWriter.write(result[0], response );

                    User source = UserDao.findUser( mobileNumber );

                    // Notify source device
                    billingPlatformInterface.sendMessage(
                            new BigInteger("" + (System.currentTimeMillis() + 1)),
                            "SMS".equalsIgnoreCase(source.getNotificationAgent())
                                    ? source.getMobileNumber() : source.getEmailAddress(),
                            source.getNotificationAgent(), result[0] );

                    User beneficiary = null;

                    if (! mobileNumber.equals( beneficiaryId ) ) {

                        try {
                            beneficiary = UserDao.findUser(beneficiaryId);
                        } catch (Exception e) {
                        }

                        // Notify beneficiary device
                        billingPlatformInterface.sendMessage(
                                new BigInteger("" + (System.currentTimeMillis() + 2)),
                                beneficiary != null && !"SMS".equalsIgnoreCase(beneficiary.getNotificationAgent())
                                        ? beneficiary.getEmailAddress() : beneficiaryId,
                                beneficiary == null ? "SMS" : source.getNotificationAgent(), result[1]);
                    }

                    persistDataBundleResponse(uuid, dataBundlePurchaseResult, dataBundle);

                    break;

                case AIRTIME_TRANSFER:

                    uuid = randomUUID().toString().replaceAll("-","").toUpperCase();
                    beneficiaryId = params.get("beneficiary-id")[0];
                    BigDecimal amount = new BigDecimal(params.get("amount")[0]);

                    BalanceDTO[] balances =
                            billingPlatformInterface.transfer(uuid, mobileNumber, beneficiaryId, amount);
                    // Call prepaid platform
                    result = balanceTransferResponse( balances, amount, uuid );

                    // Notify web user
                    HttpResponseWriter.write( result[0], response );

                    source = UserDao.findUser( mobileNumber );
                    beneficiary = null;
                    try {
                        beneficiary = UserDao.findUser(beneficiaryId);
                    }catch(Exception e ){
                    }

                    // Notify source device
                    billingPlatformInterface.sendMessage(
                            new BigInteger("" + (System.currentTimeMillis() + 1)),
                            "SMS".equalsIgnoreCase(source.getNotificationAgent())
                                    ? source.getMobileNumber() : source.getEmailAddress(),
                            source.getNotificationAgent(), result[0] );

                    // Notify beneficiary device
                    billingPlatformInterface.sendMessage(
                            new BigInteger("" + System.currentTimeMillis() ),
                            beneficiary != null && !"SMS".equalsIgnoreCase( beneficiary.getNotificationAgent())
                                    ? beneficiary.getEmailAddress() : beneficiaryId,
                            beneficiary == null ? "SMS" : source.getNotificationAgent(), result[1] );

                    persistBalanceTransferResponse(  balances, amount, uuid );

                    break;

                case VOUCHER_RECHARGE:
                    uuid = randomUUID().toString().replaceAll("-","").toUpperCase();
                    beneficiaryId = params.get("beneficiary-id")[0];
                    String rechargeVoucher = params.get("recharge-voucher")[0];

                    BalanceDTO rechargeResult = billingPlatformInterface.recharge(uuid, beneficiaryId, rechargeVoucher);

                    String[] rechargeResponse = voucherRechargeResponse(uuid, mobileNumber, rechargeResult);

                    // Notify web user
                    HttpResponseWriter.write(rechargeResponse[0], response );

                    source = UserDao.findUser( mobileNumber );

//                    Notify source device
                    billingPlatformInterface.sendMessage(
                            new BigInteger("" + (System.currentTimeMillis() + 1)),
                            "SMS".equalsIgnoreCase(source.getNotificationAgent())
                                    ? source.getMobileNumber() : source.getEmailAddress(),
                            source.getNotificationAgent(), rechargeResponse[0] );

                    // Notify beneficiary if different from source
                    if (rechargeResponse.length == 2) {
                        try {
                            beneficiary = UserDao.findUser(beneficiaryId);
                        } catch(Exception e ){
                            beneficiary = null;
                        }

                        billingPlatformInterface.sendMessage(
                                new BigInteger("" + (System.currentTimeMillis() + 2)) ,
                                beneficiary != null && !"SMS".equalsIgnoreCase( beneficiary.getNotificationAgent())
                                        ? beneficiary.getEmailAddress() : beneficiaryId,
                                beneficiary == null ? "SMS" : source.getNotificationAgent(), rechargeResponse[1] );
                    }

                    persistVoucherRechargeResponse(uuid, mobileNumber, rechargeResult);
                    break;

                case TRANSACTION_HISTORY:
                    HttpResponseWriter.write( transactionHistory( mobileNumber ), response );
                    break;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            HttpResponseWriter.write(
                    e != null ? e.getMessage() : "System error occurred. Please retry in a few minutes", response );
        }
    }

    /**
     * Reclaim resources.
     */
    public void destroy() {
    }
}
