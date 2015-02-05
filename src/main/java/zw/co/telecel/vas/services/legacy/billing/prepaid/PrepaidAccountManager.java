//package zw.co.telecel.vas.services.legacy.billing.prepaid;
//
//import com.comverse_in.prepaid.ccws.*;
//
//import com.google.gson.Gson;
//import zw.co.telecel.vas.dao.TxnDao;
//import zw.co.telecel.vas.dto.BalanceResponseDTO;
//import zw.co.telecel.vas.dto.PduDto;
//import zw.co.telecel.vas.dto.TxnDto;
//import zw.co.telecel.vas.services.legacy.billing.AbstractAccountManager;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagerUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.SubscriberNotActiveException;
//import zw.co.telecel.vas.services.legacy.billing.util.SystemException;
//import org.apache.axis.EngineConfiguration;
//import org.apache.axis.client.Stub;
//import org.apache.axis.configuration.FileProvider;
//import org.apache.ws.security.WSConstants;
//import org.apache.ws.security.handler.WSHandlerConstants;
//import org.apache.ws.security.message.token.UsernameToken;
//import org.joda.time.DateTime;
//import zw.co.telecel.vas.model.*;
//
//import javax.xml.rpc.ServiceException;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.rmi.RemoteException;
//import java.sql.Statement;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils.parseRemoteException;
//import static zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils.retrieveSubscriber;
//import static zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils.toBalanceCreditAccountArray;
//import static zw.co.telecel.vas.services.legacy.billing.util.AccountTransferUtils.getExpiryDate;
//
///**
// * david@ebridgevas.com
// *
// */
//public class PrepaidAccountManager extends AbstractAccountManager implements AccountManager {
//
//    private Boolean withPromotion = Boolean.TRUE;
//    private ServiceSoap prepaidService;
//
//
//    private static final String CORE_BALANCE = "Core";
//
//    private static final String BONUS_DATA_BALANCE = "Gprs_bonus";
//    //    private static final String BONUS_DATA_BALANCE = "Data_Bonus";
//    private static final String BONUS_SMS_BALANCE = "Gprs_sms";
//    private static final String TEL_COS_DATA_BALANCE = "Gprs_bundle";
//    private static final String GPRS_COS_DATA_BALANCE = "Gprs_modem";
//    private TxnDao txnDao;
////    private CreditAccountRequestDao requestDao;
////    private CreditAccountResponseDao responseDao;
//
//    public static final List<String> TEST_LINES;
//
//    static {
//        TEST_LINES = new ArrayList<String>();
//        TEST_LINES.add("263739538319");
//        TEST_LINES.add("263739759181");
//    }
//
//    private Statement stmt;
//
//    private AccountManagementUtils accountManagementUtils;
//
//    public PrepaidAccountManager(Boolean withPromotion) {
//        this();
//        this.withPromotion = Boolean.TRUE;
//        accountManagementUtils = new AccountManagementUtils(this);
//    }
//
//    public PrepaidAccountManager() {
//
//        try {
//
//            EngineConfiguration config = new FileProvider("/prod/ebridge/wsdd/client_deploy_v2.wsdd"); //prod/ebridge/conf/client.wsdd");
//            ServiceLocator locator = new ServiceLocator(config);
//            prepaidService = locator.getServiceSoap(
//                    new URL("http://172.17.1.19:8080/ocswebservices/services/zimbabweocsWebServices?wsdl"));
//            // Test
////            prepaidService = locator.getServiceSoap(
////                    new URL("http://172.17.1.28:8080/ocswebservices/services/zimbabweocsWebServices?wsdl"));
//            Stub axisPort = (Stub) prepaidService;
//            axisPort._setProperty(WSHandlerConstants.ACTION, WSHandlerConstants.ENCRYPT);
//            axisPort._setProperty(UsernameToken.PASSWORD_TYPE, WSConstants.PASSWORD_TEXT);
//            axisPort._setProperty(WSHandlerConstants.USER, "zsmart2");
//            axisPort._setProperty(WSHandlerConstants.PW_CALLBACK_CLASS, "PasswordCallback");
//
////            configDTO = new Gson().fromJson(
////                                new BufferedReader(new FileReader("/prod/ebridge/conf/web.json")),
////                                ConfigDTO.class);
//
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//    }
//
//    public void setTxnDao(TxnDao txnDao) {
//        this.txnDao = txnDao;
//    }
//
//    public TxnDao getTxnDao() {
//        return txnDao;
//    }
//
//    @Override
//    public String getClassOfService( String mobileNumber ) {
//        SubscriberRetrieve subscriberRetrieve = null;
//
//        try {
//            String formattedMobileNumber = formatMobileNumber( mobileNumber );
//            if (formattedMobileNumber == null) {
//                return "Invalid mobile number specified.";
//            }
//
//            subscriberRetrieve = prepaidService.retrieveSubscriberWithIdentityNoHistory(
//                    formattedMobileNumber.substring(3), null, 1 );
//
////            if ( ! "Active".equalsIgnoreCase( subscriberRetrieve.getSubscriberData().getCurrentState() ) ) {
////                return "Subscriber account is " + subscriberRetrieve.getSubscriberData().getCurrentState();
////            }
//        } catch (RemoteException e) {
////            e.printStackTrace();
//            System.out.println("### RemoteException : " + e.getMessage());
//            return null;
//        }
//        System.out.println("#### COS for " + mobileNumber + " : " + subscriberRetrieve.getSubscriberData().getCOSName());
//        return subscriberRetrieve.getSubscriberData().getCOSName();
//    }
//
//    @Override
//    public String getAccountBalance( String mobileNumber ) {
//
//        SubscriberRetrieve subscriberRetrieve = null;
//        String cos = null;
//        try {
//            String formattedMobileNumber = formatMobileNumber( mobileNumber );
//            if (formattedMobileNumber == null) {
//                return "Invalid mobile number specified.";
//            }
//
//            System.out.println( "Retrieving subscriber : " + formattedMobileNumber );
//            subscriberRetrieve = prepaidService.retrieveSubscriberWithIdentityNoHistory(
//                    formattedMobileNumber.substring(3), null, 1 );
//            cos = subscriberRetrieve.getSubscriberData().getCOSName();
////            if ( ! "Active".equalsIgnoreCase( subscriberRetrieve.getSubscriberData().getCurrentState() ) ) {
////                return "Subscriber account is " + subscriberRetrieve.getSubscriberData().getCurrentState();
////            }
//            System.out.println("Retrieved");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//            return "Failed to retrieve subscriber : " + mobileNumber.substring(3);  // + " - " + XMLParser.getError(e.getMessage()));
//        }
//
//        BigDecimal coreBalance = null;
//        BigDecimal dataBalance = null;
//        String dataExpiryDate = null;
//
//        BigDecimal bonusDataBalance = null;
//        BigDecimal bonusSmsBalance = null;
//        String bonusDataExpiryDate = null;
//        String bonusSmsExpiryDate = null;
//
//        String dataBalanceName = "GPRS_COS".equalsIgnoreCase(cos) ? GPRS_COS_DATA_BALANCE : TEL_COS_DATA_BALANCE;
//
//        for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
//            if (CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                coreBalance = new BigDecimal(balanceEntity.getAvailableBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
//            } else if (dataBalanceName.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                Double dataBalanceValue = balanceEntity.getAvailableBalance();
//                if (dataBalanceValue != null) {
//                    dataBalance = new BigDecimal(dataBalanceValue).divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP);
//                    dataBalance = dataBalance.setScale(2, RoundingMode.HALF_UP);
//                    dataExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
//                } else {
//                    dataBalance = BigDecimal.ZERO;
//                }
//            } else if ( withPromotion && BONUS_DATA_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                Double bonusDataBalanceValue = balanceEntity.getAvailableBalance();
//                System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
//                if (bonusDataBalanceValue != null) {
//                    bonusDataBalance = new BigDecimal(bonusDataBalanceValue).divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP);
//                    bonusDataBalance = bonusDataBalance.setScale(2, RoundingMode.HALF_UP);
//                    bonusDataExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
//                } else {
//                    dataBalance = BigDecimal.ZERO;
//                }
//            } if ( withPromotion && BONUS_SMS_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
//                bonusSmsBalance = new BigDecimal(balanceEntity.getAvailableBalance()).multiply(new BigDecimal(1000000)).setScale(0, BigDecimal.ROUND_HALF_UP);
//                bonusSmsExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
//            }
//        }
//        return "Airtime bal = " + coreBalance + "usd.\n " + (dataBalance != null ? ("Data Bundle= " + dataBalance + "mb exp on " + dataExpiryDate) : "Data Bundle=0.00") + "\n" +
//                ( withPromotion ?
//                        (bonusDataBalance != null ? ("Bonus Data= " + bonusDataBalance + "mb exp on " + bonusDataExpiryDate) : "") + "\n" +
//                                (bonusSmsBalance != null ? ( bonusSmsBalance + " Bonus SMSs exp on " + bonusSmsExpiryDate) : "") + "\n" : "");
//    }
//
//    private String formatDate(Date date) {
//        return date != null ? new SimpleDateFormat("dd MMM yy").format(date) : "";
////        return date != null ? String.format("%1$td/%1$tm/%1$tY", date) : null;
//    }
//
//    protected String formatExpiryDate(Date date) {
//        return date != null ? String.format("%1$tY-%1$tm-%1$td %1$tk:%1$tM:%1$t$") : null;
//    }
//
//    @Override
//    public String debitAccount(TxnDto txn) {
//        return purchaseDataBundle(txn, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
//    }
//
//    @Override
//    public String creditAccount(TxnDto txn) {
//        return purchaseDataBundle(txn, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
//    }
//
////    @Override
////    public String purchaseDataBundle(TxnDto txn){
////        return purchaseDataBundle(txn, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
////    }
//
//    public String purchaseDataBundle(TxnDto txn, Boolean creditOnly, Boolean debitOnly) {
//        return purchaseDataBundle(txn, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE );
//    }
//
//
//    @Override
//    public ServiceCommandResponse purchaseDataBundle(ServiceCommandRequest request) {
//
//        ServiceCommandResponse response = new ServiceCommandResponse( request );
//
//        MobileAccount sourceAccount = null;
//        MobileAccount destinationAccount = null;
//
//        try {
//
//            sourceAccount = retrieveSubscriber( prepaidService, request.getUserId() );
//            destinationAccount =
//                    request.isBeneficiaryOwnPhone() ? sourceAccount :
//                            retrieveSubscriber( prepaidService, request.getDestinationId() );
//
//        } catch ( SystemException e ) {
//            response.setResponseCode("096");
//            response.setNarrative(e.getMessage());
//            return response;
//        } catch ( SubscriberNotActiveException e ) {
//            response.setResponseCode("055");
//            response.setNarrative(e.getMessage());
//            return response;
//        }
//
//        DataBundlePrice dataBundle = AccountManagerUtils.getDataBundleByType(request.getProductCode());
//        DateTime purchasedDataBundleExpiryDate = new DateTime( getExpiryDate(dataBundle.getDebit()) );
//
//        DateTime currentDataBalanceExpiry = null;
//        try {
//            currentDataBalanceExpiry = destinationAccount.getBalances().get(
//                    destinationAccount.getDataBalanceName()).getCurrentExpiryDate();
//        } catch(Exception e) {
//        }
//
//        Date newDataBalanceExpiry = null;
//
//        if (currentDataBalanceExpiry != null) {
//
//            newDataBalanceExpiry = purchasedDataBundleExpiryDate.isAfter( currentDataBalanceExpiry ) ?
//                    purchasedDataBundleExpiryDate.toDate()
//                    : currentDataBalanceExpiry.toDate();
//        } else {
//            newDataBalanceExpiry = purchasedDataBundleExpiryDate.toDate();
//        }
//
//        GregorianCalendar dataBalanceExpiry = new GregorianCalendar();
//        dataBalanceExpiry.setTime( newDataBalanceExpiry );
//
//        List<BalanceCreditAccount> balanceList = new ArrayList<BalanceCreditAccount>();
//
//        /* Add debit request. */
//
//        BalanceCreditAccount debitAccountRequest = new BalanceCreditAccount();
//        debitAccountRequest.setBalanceName(CORE_BALANCE);
//        debitAccountRequest.setCreditValue(0 - dataBundle.getDebit().doubleValue());
//        debitAccountRequest.setExpirationDate(
//                sourceAccount.getBalances().get(CORE_BALANCE).getCurrentExpiryDate().toGregorianCalendar());
//
//        balanceList.add(debitAccountRequest);
//
//        if ( ! request.isBeneficiaryOwnPhone() ) {
//
//            try {
//                prepaidService.creditAccount(
//                        sourceAccount.getMobileNumber().substring(3),
//                        null,
//                        toBalanceCreditAccountArray(balanceList), "",
//                        "Data Bundle Debit - Ref: " + request.getSessionId());
//                balanceList.remove(0);
//            } catch (RemoteException e) {
//                response.setNarrative( "Error debiting source account : " +
//                        parseRemoteException(e.getMessage()) );
//                return response;
//            }
//        }
//
//        /* Add credit request */
//
//        BalanceCreditAccount creditAccountRequest = new BalanceCreditAccount();
//        creditAccountRequest.setBalanceName( destinationAccount.getDataBalanceName() );
//        creditAccountRequest.setCreditValue( dataBundle.getCredit().doubleValue());
//        creditAccountRequest.setExpirationDate(dataBalanceExpiry);
//
//        balanceList.add(creditAccountRequest);
//
//        if ( dataBundle.getBonusCredit() != null ) {
//
//            GregorianCalendar dataBonusExpiry =
//                    new DateTime().plusDays(30).withHourOfDay(23).withMinuteOfHour(59).toGregorianCalendar();
//
//            BalanceCreditAccount bonusDataCreditRequest = new BalanceCreditAccount();
//            bonusDataCreditRequest.setBalanceName( BONUS_DATA_BALANCE );
//            bonusDataCreditRequest.setCreditValue(  dataBundle.getBonusCredit().doubleValue() );
//            bonusDataCreditRequest.setExpirationDate( dataBonusExpiry );
//            balanceList.add( bonusDataCreditRequest );
//        }
//
//        try {
//            prepaidService.creditAccount(
//                    destinationAccount.getMobileNumber().substring(3),
//                    null,
//                    toBalanceCreditAccountArray(balanceList), "",
//                    "Data Bundle Credit - Ref: " + request.getSessionId());
//
//        } catch (RemoteException e) {
//            response.setNarrative( "Error debiting source account : " +
//                    parseRemoteException(e.getMessage()) );
//        }
//
//        BigDecimal bundleSize = dataBundle.getBundleSize().setScale(2, RoundingMode.HALF_UP);
//        BigDecimal sourceAccountCoreBalance =  null;
//        BigDecimal beneficiaryAccountDataBalance = null;
//
////        try {
////            sourceAccount = retrieveSubscriber( prepaidService, request.getSourceId() );
////            sourceAccountCoreBalance = sourceAccount.getBalances().get(CORE_BALANCE).getCurrentBalance();
////
////            destinationAccount =
////                    request.isBeneficiaryOwnPhone() ? sourceAccount :
////                            retrieveSubscriber( prepaidService, request.getDestinationId() );
////            beneficiaryAccountDataBalance =
////                    destinationAccount.getBalances().get(destinationAccount.getDataBalanceName()).getCurrentBalance();
////
////            newDataBalanceExpiry =
////                    destinationAccount.getBalances()
////                            .get(destinationAccount.getDataBalanceName()).getCurrentExpiryDate().toDate();
////
////        } catch ( SystemException e ) {
//        sourceAccountCoreBalance =  calculateSourceBalanceAfterTransaction(sourceAccount, dataBundle);
//        beneficiaryAccountDataBalance = calculateBeneficiaryBalanceAfterTransaction(destinationAccount, dataBundle);
////        } catch ( SubscriberNotActiveException e ) {
////            sourceAccountCoreBalance =  calculateSourceBalanceAfterTransaction(sourceAccount, dataBundle);
////            beneficiaryAccountDataBalance = calculateBeneficiaryBalanceAfterTransaction(destinationAccount, dataBundle);
////        }
//        response.setProductSize( bundleSize );
//        response.setSourceAccountCoreBalance( sourceAccountCoreBalance );
//        response.setBeneficiaryAccountDataBalance( beneficiaryAccountDataBalance ); //.divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP) );
//        response.setBeneficiaryAccountExpiryDate( newDataBalanceExpiry );
//
//        return response;
//    }
//
//    private BigDecimal calculateSourceBalanceAfterTransaction( MobileAccount sourceAccount,
//                                                               DataBundlePrice dataBundle) {
//        return  sourceAccount.getBalances().get(CORE_BALANCE).getCurrentBalance().
//                subtract(dataBundle.getDebit())
//                .setScale(2, RoundingMode.HALF_UP);
//    }
//
//    private BigDecimal calculateBeneficiaryBalanceAfterTransaction(MobileAccount destinationAccount,
//                                                                   DataBundlePrice dataBundle) {
//        try {
//            return destinationAccount.getBalances().get(destinationAccount.getDataBalanceName())
//                    .getCurrentBalance()
//                    .add((dataBundle.getCredit()).divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP))
//                    .add(dataBundle.getBonusCredit() != null ?
//                            dataBundle.getBonusCredit()
//                                    .divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP) :
//                            BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
//        } catch(Exception e) {
//            return BigDecimal.ZERO;
//        }
//    }
//
//    //    @Override
//    public String purchaseDataBundle(TxnDto txn, Boolean creditOnly, Boolean debitOnly, Boolean reversal) {
//        withPromotion = Boolean.TRUE;
//        System.out.println("purchaseDataBundle ...");
//        SubscriberRetrieve subscriberRetrieve = null;
//        String cos = null;
//        try {
//
//            subscriberRetrieve = prepaidService.retrieveSubscriberWithIdentityNoHistory(
//                    txn.getSourceId().substring(3), null, 1 );
//
//            cos = subscriberRetrieve.getSubscriberData().getCOSName();
////            if ( ! "Active".equalsIgnoreCase( subscriberRetrieve.getSubscriberData().getCurrentState() ) ) {
////                return "Subscriber account is " + subscriberRetrieve.getSubscriberData().getCurrentState();
////            }
//        } catch (RemoteException e) {
//            return "Failed to retrieve subscriber : " + txn.getSourceId().substring(3);
//        }
//        System.out.println("subscriberRetrieve : " + subscriberRetrieve.getSubscriberID() );
//        String dataBalanceName = "GPRS_COS".equalsIgnoreCase(cos) ? GPRS_COS_DATA_BALANCE : TEL_COS_DATA_BALANCE;
//
//        System.out.println("dataBalanceName : " + dataBalanceName );
//
//        DataBundlePrice dataBundle = AccountManagerUtils.getDataBundleByType(txn.getProductCode());
//        Double coreBalance = null;
//        Calendar coreBalanceExpiry = null;
//        Double currentDataBalance = null;
//        DateTime currentDataBalanceExpiry = null;
//        for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
//            if (CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName()) ) {
//                System.out.println("coreBalance ... ");
//                coreBalance = balanceEntity.getAvailableBalance();
//                coreBalanceExpiry = balanceEntity.getAccountExpiration();
//                System.out.println("coreBalance : " + coreBalance + ", coreBalanceExpiry : " + coreBalanceExpiry);
//                if (coreBalance < dataBundle.getDebit().doubleValue()) {
//                    System.out.println("Insufficient error detected." );
////                    txn.setTxnDate(new Date());
//                    txn.setStatus("051");
////                    txn.setTxnType("DataBundlePurchaseError");
////                    txn.setAmount(getDataBundleByType(txn.getProductCode()).getDebit());
//                    txn.setNarrative("Data bundle purchase failed - Insufficient credit.");
//                    txn.setShortMessage("Insufficient credit for selected bundle, please top up airtime or subscribe to a smaller bundle");
//                    System.out.println("Logging Insufficient error " );
//                    txnDao.persist(txn);
//                    return txn.getShortMessage();
//                }
//            } else if (dataBalanceName.equalsIgnoreCase(balanceEntity.getBalanceName()) && ! debitOnly) {
//                System.out.println("dataBalanceName ... " );
//                currentDataBalance = balanceEntity.getAvailableBalance();
//                if (currentDataBalance == null) {
//                    currentDataBalance = 0.00;
//                } else {
//                    currentDataBalanceExpiry = new DateTime(balanceEntity.getAccountExpiration().getTime());
//                }
//                System.out.println("currentDataBalance : " + currentDataBalance + ", currentDataBalanceExpiry : " + currentDataBalanceExpiry);
//            }
//        }
//        System.out.println("purchasedDataBundleExpiryDate ...");
//        DateTime purchasedDataBundleExpiryDate = new DateTime( getExpiryDate(dataBundle.getDebit()) );
//        System.out.println("purchasedDataBundleExpiryDate : " + purchasedDataBundleExpiryDate);
//
//        Date newDataBalanceExpiry = purchasedDataBundleExpiryDate.isAfter( currentDataBalanceExpiry ) ?
//                purchasedDataBundleExpiryDate.toDate() : currentDataBalanceExpiry.toDate();
//
//        System.out.println("newDataBalanceExpiry : " + newDataBalanceExpiry + ", withPromotion : " + withPromotion +
//                ", currentDataBalanceExpiry : " + currentDataBalanceExpiry + ", currentDataBalanceExpiry : " + currentDataBalanceExpiry);
//
////        withPromotion = withPromotion ?
////                currentDataBalanceExpiry == null || new DateTime().isBefore(currentDataBalanceExpiry) : false;
//        withPromotion = Boolean.TRUE;
//
////        System.out.println("withPromotion : " + withPromotion);
//
//        GregorianCalendar dataBalanceExpiry = new GregorianCalendar();
//        dataBalanceExpiry.setTime( newDataBalanceExpiry );
//
//
//        GregorianCalendar dataBonusExpiry = new GregorianCalendar();
//        GregorianCalendar smsBonusExpiry = new GregorianCalendar();
//
//        Double bonusSMCount = null;
//        BigDecimal bonusDataBundle = null;
//
//        if ( withPromotion && "5".equals(txn.getProductCode()) && ! debitOnly) {
//            bonusDataBundle = new BigDecimal(dataBundle.getCredit().doubleValue());
//            dataBonusExpiry.setTime( new DateTime().plusDays(30).withHourOfDay(23).withMinuteOfHour(59).toDate() );
//        } else  if ( (withPromotion && "6".equals(txn.getProductCode()) || "7".equals(txn.getProductCode()) || "8".equals(txn.getProductCode()))  && ! debitOnly) {
//            bonusDataBundle = new BigDecimal(dataBundle.getCredit().doubleValue());
//            dataBonusExpiry.setTime( new DateTime().plusDays(30).withHourOfDay(23).withMinuteOfHour(59).toDate() );
//        }
//        System.out.println("bonusDataBundle : " + bonusDataBundle);
//
//        if (currentDataBalance == null) {
//            currentDataBalance = new Double(0);
//        }
//
//        List<BalanceCreditAccount> balanceList = new ArrayList<BalanceCreditAccount>();
//
//        System.out.println("debitOnly : " + debitOnly);
//        if ( ! debitOnly) {
//            System.out.println("debitOnly ");
//            BalanceCreditAccount debitAccountRequest = new BalanceCreditAccount();
//            debitAccountRequest.setBalanceName(CORE_BALANCE);
//            debitAccountRequest.setCreditValue(0 - dataBundle.getDebit().doubleValue());
//            debitAccountRequest.setExpirationDate(coreBalanceExpiry);
//            balanceList.add(debitAccountRequest);
//            System.out.println("debitAccountRequest.getBalanceName() : " + debitAccountRequest.getBalanceName());
//        }
//
//        System.out.println("creditOnly : " + creditOnly);
//        if (! creditOnly ) {
//            System.out.println("creditOnly ");
//            BalanceCreditAccount creditAccountRequest = new BalanceCreditAccount();
//            creditAccountRequest.setBalanceName( dataBalanceName );
//            creditAccountRequest.setCreditValue( dataBundle.getCredit().doubleValue());
//            creditAccountRequest.setExpirationDate(dataBalanceExpiry);
//            balanceList.add(creditAccountRequest);
//            System.out.println("creditAccountRequest.getBalanceName() : " + creditAccountRequest.getBalanceName());
//        }
//
//
//        System.out.println("reversal : " + reversal);
//        if ( reversal ) {
//            withPromotion = Boolean.FALSE;
//            System.out.println("credit core balance ");
//            BalanceCreditAccount debitAccountRequest = new BalanceCreditAccount();
//            debitAccountRequest.setBalanceName(CORE_BALANCE);
//            debitAccountRequest.setCreditValue(dataBundle.getDebit().doubleValue());
//            debitAccountRequest.setExpirationDate(coreBalanceExpiry);
//            balanceList.add(debitAccountRequest);
//            System.out.println("debitAccountRequest.getBalanceName() : " + debitAccountRequest.getBalanceName());
//
//            System.out.println("debit data balance ");
//
//            BalanceCreditAccount creditAccountRequest = new BalanceCreditAccount();
//            creditAccountRequest.setBalanceName( dataBalanceName );
//            creditAccountRequest.setCreditValue( 0 - dataBundle.getCredit().doubleValue());
//            creditAccountRequest.setExpirationDate(dataBalanceExpiry);
//            balanceList.add(creditAccountRequest);
//            System.out.println("creditAccountRequest.getBalanceName() : " + creditAccountRequest.getBalanceName());
//        }
//
//        List<BalanceEntityBase> bonusList = new ArrayList<BalanceEntityBase>();
//        BalanceEntityBase bonusSMSRequest = null;
//        BalanceEntityBase bonusDataRequest = null;
//        if ( withPromotion && (bonusDataBundle != null) && ! debitOnly) {
//            BalanceCreditAccount bonusDataCreditRequest = new BalanceCreditAccount();
//            bonusDataCreditRequest.setBalanceName( BONUS_DATA_BALANCE );
//            bonusDataCreditRequest.setCreditValue(  bonusDataBundle.doubleValue() );
//            bonusDataCreditRequest.setExpirationDate( dataBonusExpiry );
//            balanceList.add( bonusDataCreditRequest );
//        }
//
//        System.out.println("transacting ... persisting ... : " + balanceList);
//        System.out.println("transaction size : " + balanceList.size());
//        BalanceCreditAccount[] balanceCreditAccounts = new BalanceCreditAccount[balanceList.size()];
//        int idx = 0;
////        System.out.println("requestDao : " + requestDao);
////        requestDao.begin();
//        for (BalanceCreditAccount item : balanceList) {
//            System.out.println("########## item : " + item);
//            balanceCreditAccounts[idx] = item;
//
//            System.out.println("########## CreditAccountRequest, msisdn : " + txn.getSourceId() + ", wallet : " + item.getBalanceName());
//
//            System.out.println(
//                    " row : txn.getUuid() : " + txn.getUuid() +
//                            " txn.getSourceId().substring(3) : " + txn.getSourceId().substring(3) +
//                            " item.getCreditValue() : " + item.getCreditValue() +
//                            " item.getBalanceName() : " + item.getBalanceName() +
//                            " item.getExpirationDate().getTime() : " + item.getExpirationDate().getTime());
//
////            requestDao.persist(
////                    new CreditAccountRequest(
////                            txn.getUuid(),
////                            txn.getSourceId().substring(3),
////                            item.getCreditValue(),
////                            item.getBalanceName(),
////                            item.getExpirationDate().getTime(),
////                            new Date()));
//            ++idx;
//        }
////        System.out.println("commit : ");
////        requestDao.commit();
////        System.out.println("commit : ");
//        try {
//            prepaidService.creditAccount(txn.getSourceId().substring(3), null, balanceCreditAccounts, "",
//                    "Data Bundle Purchase - USSD GW Id: " + txn.getSessionId());
////            TransactionsDAO.log(stmt, "" + sessionId, msisdn, msisdn, "DataBundlePurchase", dataBundle.getDebit(), "000" );
//
//        } catch (RemoteException e1) {
//            e1.printStackTrace();
//            System.out.println("########## Error " + e1.getMessage());
//
//            String errorMessage = e1.getMessage();
//            if ( (errorMessage != null) && (errorMessage.trim().toLowerCase().startsWith("errorcode")) ) {
//                errorMessage = errorMessage.split("=")[2].trim().substring(1);
//                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//            }
//
////            txn.setTxnDate(new Date());
//            txn.setStatus("096");
//            txn.setNarrative(errorMessage);
//            txn.setShortMessage(errorMessage);
//            txnDao.persist(txn);
//
//            return errorMessage;
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Purchase done.");
//
//        if ( withPromotion  && (bonusDataBundle != null) && (bonusDataBundle.compareTo(BigDecimal.ZERO) > 0 ) && ! debitOnly) {
//            String bonusSM = "Dear customer, you have subscribed to the " + dataBundle.getBundleSize().setScale(0, RoundingMode.HALF_UP) +
//                    "mb bundle which comes with free " + dataBundle.getBundleSize().setScale(0, RoundingMode.HALF_UP) +
//                    "mb " + // and " + (bonusSMCount.doubleValue() * 1000000) +
//                    /* "sms s. */ "Renewal within 30days gives you even more value for your money";
//
//            System.out.println("#### " + bonusSM);
//
//            Map<String, String> msg = new HashMap<String, String>();
//            msg.put("uuid", "" + System.currentTimeMillis());
//            msg.put("destinationId", "33350");
//            msg.put("shortCode", "33350");
//            msg.put("sourceId", txn.getSourceId());
//            msg.put("subscriberId", txn.getSourceId());
//            msg.put("messageType", "SMS");
//            msg.put("shortMessage", bonusSM);
//
////            jmsWriter.write(msg);
//
//            /* Log. */
////            txn.setTxnDate(new Date());
//            txn.setStatus("000");
//            txn.setNarrative("Data Bundle Bonus Awarded.");
//            txn.setShortMessage(bonusSM);
////            txnDao.persist(txn);
//
////            txn.setTxnType("DataBundleDataBonus");
////            txn.setAmount(bonusDataBundle);
//            txnDao.persist(txn);
//        }
//
//        String result = "You have bought the data bundle successfully.";
//        try {
//            if (!debitOnly) {
//                System.out.println("dataBundle.getBundleSize() = " + dataBundle.getBundleSize());
//                System.out.println("coreBalance = " + coreBalance);
//                System.out.println("dataBundle.getDebit() = " + dataBundle.getDebit());
//                System.out.println("dataBundle.getCredit() = " + dataBundle.getBundleSize());
//                System.out.println("newDataBalanceExpiry = " + newDataBalanceExpiry);
//            }
//            result =  !debitOnly ? "You have bought the " + dataBundle.getBundleSize().setScale(2, RoundingMode.HALF_UP) +
//                    "mb bundle. Your bal = " +
//                    new BigDecimal(coreBalance).subtract(dataBundle.getDebit()).setScale(2, RoundingMode.HALF_UP) +
//                    "usd. Data bundle= " +
//                    new BigDecimal(currentDataBalance).add(
//                            (dataBundle.getCredit()).divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP)).add(bonusDataBundle != null ? bonusDataBundle : BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP) +
//                    "mb exp on " + String.format("%1$td/%1$tm/%1$tY", newDataBalanceExpiry) : "0";
//
//        } catch (Exception e ) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    @Override
//    public List<BalanceDTO> getAccountBalances(String mobileNumber) {
//
//        return null; //balances(mobileNumber, prepaidService, configDTO);
//    }
//
//    @Override
//    public void balances(String mobileNumber, BalanceResponseDTO callback) throws RemoteException {
//
////        AccountBalanceFactory.balances(mobileNumber, prepaidService, configDTO, callback);
//    }
//
//    @Override
//    public List<MTSM> transfer(PduDto pdu, String beneficiaryMobileNumber, BigDecimal transferAmount) {
//
//
//        List<MTSM> result = new ArrayList<MTSM>();
//        String mobileNumber = pdu.getSourceId();
//
//        Boolean isTestLine = TEST_LINES.contains( mobileNumber );
//
//        SubscriberRetrieve subscriberRetrieve = null;
//
//        try {
//            subscriberRetrieve = prepaidService.retrieveSubscriberWithIdentityNoHistory( mobileNumber.substring(3), null, 1 );
//        } catch (RemoteException e) {
//            return getErrorMTSM(pdu, "Failed to retrieve source subscriber : " + mobileNumber.substring(3));
//        }
//
//        Double sourceBalance = null;
//        Calendar sourceBalanceExpiry = null;
//
//        System.out.println("Checking source balance ... ");
//        Boolean isSufficientBalance = Boolean.FALSE;
//        for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
//            if ( CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName()) ) {
//                sourceBalance = balanceEntity.getAvailableBalance();
//                sourceBalanceExpiry = balanceEntity.getAccountExpiration();
//                System.out.println("sourceBalance : " + sourceBalance);
//                System.out.println("transferAmount : " + transferAmount);
//                if (sourceBalance < (transferAmount.doubleValue() + (isTestLine ? 0.02 : 0.00)) ) {
//                    return getErrorMTSM(pdu, "Insufficient credit, please top up airtime and try again.");
//                } else {
//                    System.out.println("source balance ok... ");
//                    isSufficientBalance = Boolean.TRUE;
//                }
//                break;
//            }
//        }
//
//        if (! isSufficientBalance ) {
//            return getErrorMTSM(pdu, "Can not verify source balance. Please try again.");
//        }
//
//        try {
//            subscriberRetrieve =
//                    prepaidService.retrieveSubscriberWithIdentityNoHistory( beneficiaryMobileNumber.substring(3), null, 1 );
//        } catch (RemoteException e) {
//            return getErrorMTSM(pdu, "Failed to retrieve beneficiary : " + beneficiaryMobileNumber.substring(3));
//        }
//
//        Double beneficiaryBalance = null;
//        Double beneficiaryMaxBalance = null;
//        Calendar beneficiaryBalanceExpiry = null;
//
//        for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
//            if (CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                beneficiaryBalance = balanceEntity.getAvailableBalance();
//                beneficiaryBalanceExpiry = balanceEntity.getAccountExpiration();
//                beneficiaryMaxBalance = balanceEntity.getMaximumSpendingLimit();
//
//                System.out.println("beneficiaryBalance : " + beneficiaryBalance);
//                System.out.println("transferAmount : " + transferAmount);
//                System.out.println("beneficiaryMaxBalance : " + beneficiaryMaxBalance);
//                if ( ( beneficiaryMaxBalance != null ) &&  ( ( beneficiaryBalance + transferAmount.doubleValue()) > beneficiaryMaxBalance )) {
//                    return getErrorMTSM(pdu, "Beneficiary balance will be exceed. " +
//                            ((beneficiaryMaxBalance - beneficiaryBalance) > 0 ?
//                                    "Please try again with $" + (beneficiaryMaxBalance - beneficiaryBalance) :
//                                    "Please try again with small amount.\""));
//                }
//                break;
//            }
//        }
//
//        // debit source item
//        BalanceCreditAccount debitAccountRequest = new BalanceCreditAccount();
//        debitAccountRequest.setBalanceName( CORE_BALANCE );
//        debitAccountRequest.setCreditValue( 0 - transferAmount.doubleValue() - (isTestLine ? 0.02 : 0.00) );
//        debitAccountRequest.setExpirationDate( sourceBalanceExpiry );
//
//        // credit beneficiary item
//        BalanceCreditAccount creditAccountRequest = new BalanceCreditAccount();
//        creditAccountRequest.setBalanceName(CORE_BALANCE);
//        creditAccountRequest.setCreditValue(transferAmount.doubleValue());
//        creditAccountRequest.setExpirationDate(beneficiaryBalanceExpiry);
//
//        try {
//            // debit source
//            prepaidService.creditAccount(
//                    mobileNumber.substring(3),
//                    null,
//                    toArray(debitAccountRequest),
//                    "",
//                    "Transfer to : " + beneficiaryMobileNumber.substring(3) + ", ref : " + pdu.getUuid() );
//
//            // credit beneficiary
//            prepaidService.creditAccount(
//                    beneficiaryMobileNumber.substring(3),
//                    null,
//                    toArray(creditAccountRequest),
//                    "",
//                    "Transfer from : " +  mobileNumber.substring(3) + ", ref :" + pdu.getUuid() );
//
//        } catch (RemoteException e1) {
//            e1.printStackTrace();
//
//            System.out.println("########## Error " + e1.getMessage());
//            return getErrorMTSM(pdu, "Data bundle purchase failed. Please try again");
//        }
//
//        String sourceSms = "$" + transferAmount + " transfer to 0" + beneficiaryMobileNumber.substring(3) + " accepted. " +
//                "Your balance is now: $" + new BigDecimal( sourceBalance - transferAmount.doubleValue() ).setScale(2, RoundingMode.HALF_UP);
//        result.add(new MTSM(pdu.getSourceId(),pdu.getDestinationId(), sourceSms));
//
//
//        String beneficiarySms = "$" + transferAmount + " transfer from 0" + mobileNumber.substring(3) + " accepted. " +
//                "Your balance is now: $" + new BigDecimal( beneficiaryBalance + transferAmount.doubleValue() ).setScale(2, RoundingMode.HALF_UP);
//        result.add(new MTSM(beneficiaryMobileNumber, pdu.getDestinationId(), beneficiarySms));
//
//        return result;
//
//    }
//
//    public void balanceTransfer(String fromAccountNumber,
//                                String fromBalanceName,
//                                String toAccountNumber,
//                                String toBalanceName,
//                                BigDecimal amount) {
//
//        String fromMsisdn = formatMobileNumber(fromAccountNumber);
//        String toMsisdn = formatMobileNumber(toAccountNumber);
//
//        if (toMsisdn.equals(fromMsisdn)) {
//            BalanceCreditAccount[] request = new BalanceCreditAccount[2];
//            request[0] = new BalanceCreditAccount();
//            request[0].setBalanceName(fromBalanceName);
//            request[0].setCreditValue(BigDecimal.ZERO.subtract(amount).doubleValue());
//
//            request[1] = new BalanceCreditAccount();
//            request[1].setBalanceName(toBalanceName);
//            request[1].setCreditValue(amount.doubleValue());
//            try {
//                prepaidService.creditAccount(
//                        fromMsisdn.substring(3),
//                        null,
//                        request,
//                        "",
//                        "Transfer from : " + fromBalanceName + ", to : " + toBalanceName );
//            } catch (RemoteException e1) {
//                e1.printStackTrace();
//                System.out.println("########## Error " + e1.getMessage());
//            }
//        } else {
//            BalanceCreditAccount[] debitRequest = new BalanceCreditAccount[1];
//            debitRequest[0] = new BalanceCreditAccount();
//            debitRequest[0].setBalanceName(fromBalanceName);
//            debitRequest[0].setCreditValue(BigDecimal.ZERO.subtract(amount).doubleValue());
//
//            BalanceCreditAccount[] creditRequest = new BalanceCreditAccount[1];
//            creditRequest[0] = new BalanceCreditAccount();
//            creditRequest[0].setBalanceName(toBalanceName);
//            creditRequest[0].setCreditValue(amount.doubleValue());
//
//            try {
//                prepaidService.creditAccount(
//                        fromMsisdn.substring(3),
//                        null,
//                        debitRequest,
//                        "",
//                        "Transfer from : " + fromMsisdn + ", to : " + toMsisdn );
//
//                prepaidService.creditAccount(
//                        toMsisdn.substring(3),
//                        null,
//                        creditRequest,
//                        "",
//                        "Transfer from : " + fromMsisdn + ", to : " + toMsisdn );
//            } catch (RemoteException e1) {
//                e1.printStackTrace();
//                System.out.println("########## Error " + e1.getMessage());
//            }
//        }
//    }
//
//    protected List<MTSM> getErrorMTSM(PduDto pdu, String errorMsg) {
//        List<MTSM> result = new ArrayList<MTSM>();
//        result.add(new MTSM(pdu.getSourceId(), pdu.getDestinationId(), errorMsg));
//        return result;
//    }
//
//    @Override
//    public List<MTSM> voucherRecharge(PduDto pdu, String beneficiaryMsisdn, String rechargeVoucher) {
//
//        List<MTSM> result = new ArrayList<MTSM>();
//        String msisdn = pdu.getSourceId();
//        SubscriberRetrieve subscriberRetrieve = null;
//
//        try {
//            subscriberRetrieve =
//                    prepaidService.retrieveSubscriberWithIdentityNoHistory( beneficiaryMsisdn.substring(3), null, 1 );
//        } catch (RemoteException e) {
//            return getErrorMTSM(pdu, "Failed to retrieve subscriber : " + msisdn.substring(3));
//        }
//
//        Double coreBalance = null;
//        for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
//            if (CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                coreBalance = balanceEntity.getAvailableBalance();
//                break;
//            }
//        }
//
//        String sourceSms = null;
//        String beneficiarySms = null;
//        try {
//
//            /* Retrieve the voucher */
//            VoucherEntity voucherEntity = prepaidService.retrieveVoucherBySecretCode(rechargeVoucher);
//
//            String balanceChangeComments = "Voucher Recharge # " + rechargeVoucher;// + " for subscriber # " + beneficiaryMsisdn.substring(3);
//            DeltaBalance[] balancesAfterTxn = prepaidService.rechargeAccountBySubscriber(beneficiaryMsisdn.substring(3), null, rechargeVoucher, balanceChangeComments);
//
//            System.out.println(" balancesAfterTxns = " + balancesAfterTxn.length);
//            System.out.println(" balancesAfterTxn = " + balancesAfterTxn[0].getDelta());
//            Double newBalance = balancesAfterTxn[0].getDelta();
//            Double voucherFaceValue = newBalance - coreBalance;
//            if ( beneficiaryMsisdn.equals(msisdn) ) {
////                System.out.println("voucherEntity.getFaceValue() : " + voucherEntity.getFaceValue() + ", delta = " + voucherFaceValue );
//                sourceSms = "$" + new DecimalFormat("###,##.00").format(voucherFaceValue.doubleValue()) +
//                        " recharge voucher accepted. ";
////                        "Your balance is now " +
////                        new DecimalFormat("###,##.00").format(newBalance.doubleValue());
//            } else {
//                sourceSms = "$" + new DecimalFormat("###,##.00").format(voucherFaceValue.doubleValue()) +
//                        " recharge voucher for " + beneficiaryMsisdn + " accepted.";
//                beneficiarySms = "$" + new DecimalFormat("###,##.00").format(voucherFaceValue.doubleValue()) +
//                        " recharge voucher from" + msisdn + " accepted. Your balance is now " +
//                        new DecimalFormat("###,##.00").format(newBalance.doubleValue());
//            }
////            TransactionsDAO.log(stmt, "" + sessionId, msisdn, msisdn, "VoucherRecharge", voucherEntity.getFaceValue(), "000" );
//
//            result.add(new MTSM(pdu.getSourceId(), pdu.getDestinationId(), sourceSms));
//
//            if ( !beneficiaryMsisdn.equals(msisdn) ) {
//                result.add(new MTSM(beneficiaryMsisdn, pdu.getDestinationId(), beneficiarySms));
//            }
//
//            return result;
//        } catch (RemoteException e1) {
//            e1.printStackTrace();
//
//            String errorMessage = e1.getMessage();
//            if ( (errorMessage != null) && (errorMessage.trim().toLowerCase().startsWith("errorcode")) ) {
//                errorMessage = errorMessage.split("=")[2].trim().substring(1);
//                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//            }
//            return getErrorMTSM(pdu, errorMessage);
//        }
//    }
//
//    protected BalanceCreditAccount[] toArray(BalanceCreditAccount item) {
//        BalanceCreditAccount[] array = new BalanceCreditAccount[1];
//        array[0] = item;
//        return array;
//    }
//
////    public void awardSMSBonus(StoreAndForwardTxn txn) throws RemoteException {
////
////        List<BalanceCreditAccount> balanceList = new ArrayList<BalanceCreditAccount>();
////
////        Double bonusSMCount = new Double(0.000001);
////        List<BalanceEntityBase> bonusList = new ArrayList<BalanceEntityBase>();
////        BalanceEntityBase bonusSMSRequest = null;
////        BalanceCreditAccount bonusSMSCreditRequest = new BalanceCreditAccount();
////        bonusSMSCreditRequest.setBalanceName( BONUS_SMS_BALANCE );
////        bonusSMSCreditRequest.setCreditValue(  bonusSMCount.doubleValue() );
////        GregorianCalendar expiry = new GregorianCalendar();
////        expiry.setTime(txn.getExpiryDate());
////        bonusSMSCreditRequest.setExpirationDate( expiry );
////
////        BalanceCreditAccount[] balanceCreditAccounts = new BalanceCreditAccount[1];
////        balanceCreditAccounts[0] = bonusSMSCreditRequest;
////
////        prepaidService.creditAccount(txn.getMsisdn().substring(3), null, balanceCreditAccounts, "",
////                    "SMS Bonus award - Ref: " + txn.getUuid());
////    }
//
////    protected void sendSms(String msisdn, String sm) {
////        Map<String, String> msg = new HashMap<String, String>();
////        msg.put("uuid", "" + System.currentTimeMillis());
////        msg.put("destinationId", msisdn);
////        msg.put("shortCode", "23350");
////        msg.put("sourceId", "23350");
////        msg.put("subscriberId", msisdn);
////        msg.put("messageType", "SMS");
////        msg.put("shortMessage", sm);
////
//////        jmsWriter.write(msg);
////    }
//
//    private static void printBalancesFor(String mobileNumber, PrepaidAccountManager prepaid) {
//        mobileNumber = formatMobileNumber(mobileNumber);
//        List<BalanceDTO> balances = prepaid.getAccountBalances(mobileNumber);
//        for( BalanceDTO balance : balances ) {
//            // TODO fix
////            System.out.println( balance.getWalletDescription() + " : " +
////                    balance.getBalance() + " : " +
////                    balance.getExpiryDate() );
//        }
//    }
//
//    public static void printUsage() {
//        System.out.println( "Usage: ./pps bal mobileNumber or \n ./pps bundle bundleAmount mobileNumber or \n" +
//                " ./pps cos bundleAmount mobileNumber \n" +
//                "./pps cancel originalBundleAmount mobileNumber  ");
//    }
//
//    private static void printCosFor(String mobileNumber, PrepaidAccountManager prepaid) {
//        String cos = prepaid.getClassOfService(mobileNumber);
//        System.out.println(" COS for : " + mobileNumber + ", " + cos );
//    }
//
//    private static void balanceTransfer( PrepaidAccountManager prepaid,
//                                         String fromAccountNumber,
//                                         String fromBalanceName,
//                                         String toAccountNumber,
//                                         String toBalanceName,
//                                         BigDecimal amount ) {
//        prepaid.balanceTransfer(fromAccountNumber, fromBalanceName, toAccountNumber, toBalanceName, amount);
//    }
//
//
////    public static List<BalanceDTO> balances ( String mobileNumber,
////            ServiceSoap prepaidService,
////            ConfigDTO configDTO) throws RemoteException {
//    //        List<BalanceDTO> balances = new ArrayList<>();
////        SubscriberRetrieve subscriberRetrieve = null;
////        String cos = null;
////        try {
////            String formattedMobileNumber = formatMobileNumber( mobileNumber );
////            if (formattedMobileNumber == null) {
////                return balances;
////            }
////
////            System.out.println( "Retrieving subscriber : " + formattedMobileNumber );
////            subscriberRetrieve = prepaidService.retrieveSubscriberWithIdentityNoHistory(
////                    formattedMobileNumber.substring(3), null, 1 );
////            cos = subscriberRetrieve.getSubscriberData().getCOSName();
////
////            System.out.println("Retrieved");
////        } catch (RemoteException e) {
////            e.printStackTrace();
////            return balances;  // + " - " + XMLParser.getError(e.getMessage()));
////        }
////
////        String dataBalanceName = "GPRS_COS".equalsIgnoreCase(cos) ? GPRS_COS_DATA_BALANCE : TEL_COS_DATA_BALANCE;
////
////        BigDecimal coreBalance = null;
////        BigDecimal dataBalance = null;
////        BigDecimal bonusDataBalance = null;
////        BigDecimal bonusSmsBalance = null;
////
////        String coreExpiryDate = null;
////        String dataExpiryDate = null;
////        String bonusDataExpiryDate = null;
////        String bonusSmsExpiryDate = null;
////
////        for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
////
////            if (CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
////                try {
////                    coreBalance = new BigDecimal(balanceEntity.getAvailableBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
////                    coreExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
////                    balances.add( new BalanceDTO("Core", "Main account", coreBalance.toString(), coreExpiryDate));
////
////                    System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            } else if (dataBalanceName.equalsIgnoreCase(balanceEntity.getBalanceName())) {
////                if ( new DateTime(balanceEntity.getAccountExpiration().getTime()).isAfterNow() ) {
////                    try {
////                        Double dataBalanceValue = balanceEntity.getAvailableBalance();
////                        if (dataBalanceValue != null) {
////                            dataBalance = new BigDecimal(dataBalanceValue).divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP);
////                            dataBalance = dataBalance.setScale(2, RoundingMode.HALF_UP);
////                            dataExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
////                        } else {
////                            dataBalance = BigDecimal.ZERO;
////                        }
////                        balances.add( new BalanceDTO("Data", "Data Account", dataBalance.toString(), dataExpiryDate));
////
////                        System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            } else if (BONUS_DATA_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
////                if ( new DateTime(balanceEntity.getAccountExpiration().getTime()).isAfterNow() ) {
////                    try {
////                        Double bonusDataBalanceValue = balanceEntity.getAvailableBalance();
////                        System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
////
////                        if (bonusDataBalanceValue != null) {
////                            bonusDataBalance = new BigDecimal(bonusDataBalanceValue).divide(new BigDecimal(0.11), 2, RoundingMode.HALF_UP);
////                            bonusDataBalance = bonusDataBalance.setScale(2, RoundingMode.HALF_UP);
////                            bonusDataExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
////                            balances.add(new BalanceDTO("Bonus Data", "Data bonus wallet", bonusDataBalance.toString(), bonusDataExpiryDate));
////                        }
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                        balances.add(new BalanceDTO("Bonus Data", "Internet browsing bonus wallet", dataBalance.toString(), ""));
////                    }
////                }
////            } else if (BONUS_SMS_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName())) {
////                if ( new DateTime(balanceEntity.getAccountExpiration().getTime()).isAfterNow() ) {
////                    try {
////                        System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
////                        bonusSmsBalance = new BigDecimal(balanceEntity.getAvailableBalance()).multiply(new BigDecimal(1000000)).setScale(0, BigDecimal.ROUND_HALF_UP);
////                        bonusSmsExpiryDate = formatDate( balanceEntity.getAccountExpiration().getTime() );
////                        balances.add( new BalanceDTO("Bonus SMS", "Bonus SMS wallet", bonusSmsBalance.toString(), bonusSmsExpiryDate));
////                        System.out.println("#### " + balanceEntity.getBalanceName() + " : " + balanceEntity.getAvailableBalance() + " / " + balanceEntity.getBalance());
////                    } catch (Exception e) {
////                        balances.add(new BalanceDTO("Bonus SMS", "Bonus SMS wallet", "0.00", ""));
////                        e.printStackTrace();
////                    }
////                }
////            }
////        }
////        System.out.println("#### Bal count : " + balances.size());
////
////        return balances;
////}
//}
