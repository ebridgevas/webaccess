package zw.co.telecel.vas.services.legacy.billing.prepaid;//package zw.co.telecel.vas.services.legacy.billing.prepaid;
//
//import com.ebridgevas.model.*;
//import zw.co.telecel.vas.services.legacy.billing.AbstractAccountManager;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.model.*;
//
//import java.util.List;
//
///**
// * david@ebridgevas.com
// *
// */
//public class PrepaidAccountManagerTest extends AbstractAccountManager {
//
//    public static void main(String[] args) {
//
//        String serviceCommand = null;
//
//        if ( args.length > 1 ) {
//             serviceCommand = args[0];
//        } else {
//            printUsage();
//            System.exit(1);
//        }
//
//        if ("bal".equalsIgnoreCase(serviceCommand) ) {
//            printBalancesFor(args[1]);
//        } else if ( "bundle".equalsIgnoreCase(serviceCommand) ) {
//
//            purchaseDataBundle( args );
//        } else {
//            printUsage();
//            System.exit(1);
//        }
//    }
//
//    private static void printBalancesFor(String mobileNumber) {
//
//        AccountManager prepaidAccountManager = new PrepaidAccountManager();
//
//        mobileNumber = formatMobileNumber( mobileNumber );
//        List<BalanceDTO> balances = prepaidAccountManager.getAccountBalances(mobileNumber);
//        for( BalanceDTO balance : balances ) {
//            System.out.println( balance.getWalletDescription() + " : " +
//                    balance.getBalance() + " : " +
//                    balance.getExpiryDate() );
//        }
//    }
//
//    private static void purchaseDataBundle(String[] args) {
//
//        if ( args.length > 3 ) {
//
//            AccountManager prepaidAccountManager = new PrepaidAccountManager();
//
//            String mobileNumber = args[1];
//            String beneficiaryMobileNumber = args[2];
//            String bundleId = DATA_BUNDLES_AMOUNT_TO_TYPE.get(args[3]);
//
//            final ServiceCommandRequest request =
//                    new ServiceCommandRequest(
//                            ServiceCommand.DATA_BUNDLE_PURCHASE,
//                            mobileNumber,
//                            IdType.MOBILE_NUMBER,
//                            beneficiaryMobileNumber,
//                            bundleId,
//                            "" + (System.currentTimeMillis() + 1),
//                            "");
//
//            ServiceCommandResponse response = prepaidAccountManager.purchaseDataBundle( request );
//
//            System.out.println("ProductSize : " + response.getProductSize());
//            System.out.println("SourceAccountCoreBalance : " + response.getSourceAccountCoreBalance());
//
//            System.out.println("BeneficiaryAccountDataBalance : " + response.getBeneficiaryAccountDataBalance());
//            System.out.println("BeneficiaryAccountExpiryDate : " + response.getBeneficiaryAccountExpiryDate());
//        } else {
//            printUsage();
//        }
//    }
//
//    public static void printUsage() {
//        System.out.println( "Usage: ./pps bal mobileNumber or \n ./pps bundle bundleAmount mobileNumber or \n" +
//                "./pps cancel originalBundleAmount mobileNumber  ");
//    }
//
//    public static String formatMobileNumber( String mobileNumber ) {
//
//        try {
//            mobileNumber = mobileNumber.replaceAll(" ", "");
//        } catch (Exception e) {
//        }
//        byte len = (byte) mobileNumber.length();
//        switch (len) {
//            case 8:
//                if (mobileNumber.startsWith("23")) {
//                    break;
//                }
//            case 9:
//                if (mobileNumber.startsWith("023") || mobileNumber.startsWith("73")) {
//                    break;
//                }
//            case 10:
//                if (mobileNumber.startsWith("073")) {
//                    break;
//                }
//            case 11:
//                if (mobileNumber.startsWith("26323")) {
//                    break;
//                }
//            case 12:
//                if (mobileNumber.startsWith("26373")) {
//                    break;
//                }
//            default:
//                return null;
//        }
//        if (mobileNumber.startsWith("26373")) {
//            return mobileNumber;
//        } else {
//            return "26373" + mobileNumber.substring(len - 7);
//        }
//    }
//}
