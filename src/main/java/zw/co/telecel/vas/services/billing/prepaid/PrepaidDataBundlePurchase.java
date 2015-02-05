package zw.co.telecel.vas.services.billing.prepaid;

import com.comverse_in.prepaid.ccws.BalanceCreditAccount;
import com.comverse_in.prepaid.ccws.ServiceSoap;
import com.ebridge.services.payment.telecash.ws.ObopayExternalWebServiceServiceStub;
import com.google.inject.Inject;
import com.google.inject.Provider;
import data.ws.obopay.com.ObopayWebServiceData;
import org.apache.axis2.client.ServiceClient;
import org.apache.log4j.Category;
import org.joda.time.DateTime;
import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.services.billing.DataBundlePurchase;
import zw.co.telecel.vas.services.billing.prepaid.processors.*;
import zw.co.telecel.vas.services.legacy.billing.prepaid.AccountBalanceFactory;
import zw.co.telecel.vas.services.payment.TelecashPaymentService;
import zw.co.telecel.vas.util.TransactionException;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import static zw.co.telecel.vas.services.util.Util.balance;
import static zw.co.telecel.vas.services.util.Util.dataBalance;

/**
 * @author david@tekeshe.com
 */
public class PrepaidDataBundlePurchase implements DataBundlePurchase {

    private final static String configFilename = System.getProperty("ebridge.conf.path") + "/" + "service.config";

    private Map<String, String> config;


    private static Category CAT = Category.getInstance(PrepaidDataBundlePurchase.class);

    @Inject
    @Named("prepaidServiceSoapProvider")
    private Provider<ServiceSoap> prepaidServiceSoapProvider;

//    @Inject
//    @Named("telecashSoapProvider")
//    private Provider<ServiceSoap> telecashSoapProvider;

    private TelecashPaymentService telecashPaymentService;

    @Inject
    @Named("prepaidAccountBalanceFactory")
    private AccountBalanceFactory prepaidAccountBalanceFactory;


    public PrepaidDataBundlePurchase() {

        telecashPaymentService = new TelecashPaymentService();
    }

    @Override
    public BalanceDTO[] dataBundlePurchase(
                String uuid, String sourceId, DataBundleDTO dataBundle, String beneficiaryId,
                String paymentMethod, String oneTimePassword)
            throws RemoteException, TransactionException {

        BalanceDTO[] result = new BalanceDTO[2];

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime( new DateTime(expirationDate.getTime()).plusDays( dataBundle.getWindowSize()).toDate() );

        CAT.info("{dataBundlePurchase : { mobileNumber : " + sourceId +
                  ", debitValue : " + dataBundle.getDebit() +
                    ", creditValue : " + dataBundle.getCredit() + ", expirationDate : " +
                    String.format("%1$td/%1$tm/%1$tY", expirationDate ) + "}}");

        if (paymentMethod.equals( "AIRTIME" ) ) {

            Boolean ownPhone = sourceId.equals(beneficiaryId);

            if ( ownPhone ) {

                DebitCreditRequestProcessor.process(prepaidAccountBalanceFactory, prepaidServiceSoapProvider,
                        uuid, sourceId, dataBundle, beneficiaryId, expirationDate,
                        result);
            } else {

                BalanceCreditAccount[] debitPayload
                        = PrepaidDebitRequestProcessor.process(
                                prepaidAccountBalanceFactory, prepaidServiceSoapProvider,
                                uuid, sourceId, dataBundle, beneficiaryId, expirationDate,
                                result);

                CreditRequestProcessor.process(
                                paymentMethod,
                                prepaidAccountBalanceFactory, prepaidServiceSoapProvider,
                                telecashPaymentService,
                                uuid, sourceId, dataBundle, beneficiaryId, expirationDate,
                                debitPayload, result);
            }

        } else {

            String errorCode = null;
            String narrative =
                    telecashPaymentService.purchase(
                            uuid, sourceId, oneTimePassword, dataBundle.getDebit());

            System.out.println("Error: mobile-number : " + sourceId + ", error: " + errorCode +
                    ", narrative : " + narrative);

            if ( ! narrative.equalsIgnoreCase("Success")) {
                throw new TransactionException( narrative );
            }

//            if ( debited ) {

                result[0] = new BalanceDTO();
                result[0].setMobileNumber( sourceId );
                result[0].setBalance(null);
                result[0].setExpiryDate( null );
                result[0].setSubscriberPackage("PREPAID");

                Boolean credited =
                        CreditRequestProcessor.process(
                                paymentMethod,
                                prepaidAccountBalanceFactory, prepaidServiceSoapProvider,
                                telecashPaymentService,
                                uuid, sourceId, dataBundle, beneficiaryId, expirationDate,
                                null, result);
//            } else {
//                System.out.println("Error: mobile-number : " + sourceId + ", error: " + errorCode +
//                        ", narrative : " + narrative);
//
//                throw new TransactionException( narrative );
//            }
//            if (! credited ) {
//                Boolean refunded =
//                        TelecashRefundProcessor.process(
//                                prepaidAccountBalanceFactory, prepaidServiceSoapProvider,
//                                uuid, sourceId, dataBundle, beneficiaryId, expirationDate );
//            }
        }

        /*
        Set<BalanceDTO> destinationBalances = null;

        BalanceCreditAccount[] payload = new BalanceCreditAccount[ownPhone ? 2 : 1];
        BalanceCreditAccount[] creditPayload = null;

        BalanceDTO beneficiaryDataBalance = null;

        // --- Initial source and beneficiary balances ---
        Set<BalanceDTO> sourceBalances = prepaidAccountBalanceFactory.balances( sourceId );
        BalanceDTO sourceCoreBalance = balance("Core", sourceBalances);
//        beneficiaryDataBalance = dataBalance( sourceBalances );
        payload[0] = new BalanceCreditAccount();
        payload[0].setBalanceName(sourceCoreBalance.getBalanceName().getSystemValue());
        payload[0].setCreditValue(0 - dataBundle.getDebit().doubleValue());
        payload[0].setExpirationDate( sourceCoreBalance.getExpiryDate() );

        CAT.debug("Debit : " + payload[0].getCreditValue() );
        System.out.println("Debit : " + payload[0].getCreditValue() );
        if ( ownPhone ) {
            beneficiaryDataBalance = dataBalance( sourceBalances );
            payload[1] = new BalanceCreditAccount();
            payload[1].setBalanceName(beneficiaryDataBalance.getBalanceName().getSystemValue());
            payload[1].setCreditValue(dataBundle.getCredit().doubleValue());
            payload[1].setExpirationDate(
                            expirationDate.after(beneficiaryDataBalance.getExpiryDate())
                                    ? expirationDate : beneficiaryDataBalance.getExpiryDate());

            CAT.debug("Credit : " + payload[1].getCreditValue() );
            System.out.println("Credit : " + payload[1].getCreditValue() );

        } else {
            destinationBalances = prepaidAccountBalanceFactory.balances( beneficiaryId );
            beneficiaryDataBalance = dataBalance( destinationBalances );
            creditPayload = new BalanceCreditAccount[1];
            creditPayload[0] = new BalanceCreditAccount();
            creditPayload[0].setBalanceName( beneficiaryDataBalance.getBalanceName().getSystemValue() );
            creditPayload[0].setCreditValue(dataBundle.getCredit().doubleValue());
            creditPayload[0].setExpirationDate(
                                expirationDate.after( beneficiaryDataBalance.getExpiryDate() )
                                        ? expirationDate : beneficiaryDataBalance.getExpiryDate());
        }

        // Debit source account

        CAT.debug("Parsing ...");
        prepaidServiceSoapProvider.get().creditAccount(
                sourceId.substring(3), null, payload, "", "VAS Gw: Data Bundle Purchase Ref: " + uuid);

        if ( ! ownPhone ) {
            CAT.debug("Crediting ...");
            try {
                prepaidServiceSoapProvider.get().creditAccount(
                        beneficiaryId.substring(3), null, creditPayload, "", "VAS Gw: Data Bundle Purchase Ref: " + uuid);
            } catch (Exception e) {
                e.printStackTrace();
                payload[0].setCreditValue( dataBundle.getDebit().doubleValue() );
                CAT.debug("Reversing ...");
                try {
                    prepaidServiceSoapProvider.get().creditAccount(
                            sourceId.substring(3), null, payload, "", "VAS Gw: Data Bundle Purchase Ref: " + uuid);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    throw new TransactionException("A system error occurred");
                    // TODO Fatal log reversal failure
                }
            }
        }
        CAT.debug("Billing interface responded.");



        result[0] = new BalanceDTO();
        result[0].setMobileNumber( sourceId );
        result[0].setBalance(sourceCoreBalance.getBalance()
                        .subtract(dataBundle.getDebit()).setScale(2, RoundingMode.HALF_UP));
        result[0].setExpiryDate( payload[0].getExpirationDate() );
        result[0].setSubscriberPackage("PREPAID");

        result[1] = new BalanceDTO();
        result[1].setMobileNumber( beneficiaryId );
        result[1].setBalance(( beneficiaryDataBalance.getBalance())
                                    .add((dataBundle.getCredit().divide(
                                            dataBundle.getConsumptionTariff(), 2, RoundingMode.HALF_UP))
                                            .setScale(2, RoundingMode.HALF_UP)));
        result[1].setExpiryDate( ownPhone ? payload[1].getExpirationDate()
                                            : creditPayload[0].getExpirationDate());
        result[1].setSubscriberPackage("PREPAID");
         */

        CAT.info("{result: " + result + "}");

        return result;
    }
}
