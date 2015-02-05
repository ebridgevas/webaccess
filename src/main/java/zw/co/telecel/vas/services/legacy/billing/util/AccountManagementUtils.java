//package zw.co.telecel.vas.services.legacy.billing.util;
//
//import com.comverse_in.prepaid.ccws.BalanceCreditAccount;
//import com.comverse_in.prepaid.ccws.BalanceEntity;
//import com.comverse_in.prepaid.ccws.ServiceSoap;
//import com.comverse_in.prepaid.ccws.SubscriberRetrieve;
//import zw.co.telecel.vas.model.BalanceDTO;
//import zw.co.telecel.vas.model.MobileAccount;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import org.joda.time.DateTime;
//
//import java.math.BigDecimal;
//import java.rmi.RemoteException;
//import java.util.*;
//
///**
// * david@ebridgevas.com
// *
// */
//public class AccountManagementUtils {
//
//    private AccountManager prepaidAccountManager;
//
//    private static final String TEL_COS_DATA_BALANCE = "Gprs_bundle";
//    private static final String GPRS_COS_DATA_BALANCE = "Gprs_modem";
//    private static final String CORE_BALANCE = "Core";
//
//    public AccountManagementUtils(AccountManager prepaidAccountManager) {
//        this.prepaidAccountManager = prepaidAccountManager;
//    }
//
//    public Boolean isPostpaid(String mobileNumber) throws SystemException {
//        try {
//            String cos = prepaidAccountManager.getClassOfService( mobileNumber );
//            return cos == null || "STAFF_COS".equalsIgnoreCase(cos);
//        } catch (Exception e) {
//            throw new SystemException("Billing system not available");
//        }
//    }
//
//    public static MobileAccount retrieveSubscriber( ServiceSoap prepaidService, String mobileNumber )
//            throws SubscriberNotActiveException, SystemException {
//
//        try {
//
//            SubscriberRetrieve subscriberRetrieve =
//                    prepaidService.retrieveSubscriberWithIdentityNoHistory(
//                            mobileNumber.substring(3), null, 1 );
//
//            if ( ! "Active".equalsIgnoreCase( subscriberRetrieve.getSubscriberData().getCurrentState() ) ) {
//                throw new SubscriberNotActiveException(
//                        "Subscriber account is " + subscriberRetrieve.getSubscriberData().getCurrentState());
//            } else {
//
//                String classOfService = subscriberRetrieve.getSubscriberData().getCOSName();
//                String dataBalanceName =
//                        "GPRS_COS".equalsIgnoreCase(classOfService) ? GPRS_COS_DATA_BALANCE : TEL_COS_DATA_BALANCE;
//
//                MobileAccount account =  new MobileAccount( mobileNumber, dataBalanceName );
//
//                /* Set balances. */
//                Map<String, BalanceDTO> balances = new HashMap<String, BalanceDTO>();
//                for (BalanceEntity balanceEntity : subscriberRetrieve.getSubscriberData().getBalances()) {
//                    if (CORE_BALANCE.equalsIgnoreCase(balanceEntity.getBalanceName()) ) {
//
//                        balances.put( CORE_BALANCE,
//                                        new BalanceDTO(CORE_BALANCE, null, null, null,
//                                                new BigDecimal(balanceEntity.getAvailableBalance()),
//                                                new DateTime(balanceEntity.getAccountExpiration().getTime())));
//
//                    } else if (dataBalanceName.equalsIgnoreCase(balanceEntity.getBalanceName())) {
//                        Calendar expiration = balanceEntity.getAccountExpiration();
//                        balances.put( dataBalanceName,
//                                new BalanceDTO(dataBalanceName, null, null, null,
//                                        new BigDecimal(balanceEntity.getAvailableBalance()),
//                                        expiration != null ?
//                                                new DateTime(expiration.getTime()) : null));
//                    }
//                }
//                account.setBalances(balances);
//
//                return account;
//            }
//        } catch (RemoteException e) {
//            throw new SystemException("Failed to retrieve subscriber : " + mobileNumber.substring(3));
//        }
//    }
//
//    public static BalanceCreditAccount[] toBalanceCreditAccountArray (List<BalanceCreditAccount> balanceList) {
//
//        BalanceCreditAccount[] balanceCreditAccounts = new BalanceCreditAccount[balanceList.size()];
//        int idx = 0;
//        for (BalanceCreditAccount item : balanceList) {
//            balanceCreditAccounts[idx] = item;
//            ++idx;
//        }
//
//        return balanceCreditAccounts;
//    }
//
//    public static String parseRemoteException( String errorMessage ) {
//        if ( (errorMessage != null) && (errorMessage.trim().toLowerCase().startsWith("errorcode")) ) {
//            errorMessage = errorMessage.split("=")[2].trim().substring(1);
//            errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//        }
//        return errorMessage;
//    }
//}
