//package zw.co.telecel.vas.services.legacy.billing.postpaid;
//
//import zw.co.telecel.vas.dao.TxnDao;
//import zw.co.telecel.vas.dto.PduDto;
//import zw.co.telecel.vas.dto.TxnDto;
//import zw.co.telecel.vas.model.*;
//import zw.co.telecel.vas.services.legacy.billing.AbstractAccountManager;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountTransferUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagerUtils;
//import com.ztesoft.zsmart.bss.ws.customization.zimbabwe.*;
//import org.joda.time.DateTime;
//
//import javax.xml.rpc.ServiceException;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.rmi.RemoteException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Logger;
//
//
///**
// * david@ebridgevas.com
// *
// */
//public class PostpaidAccountManager extends AbstractAccountManager implements AccountManager {
//
//    private WebServices postpaidService;
//
//    private Logger log = Logger.getLogger(PostpaidAccountManager.class.getName());
//
//    private TxnDao txnDao;
//
//    private String errorMessage;
//
//
//    public PostpaidAccountManager() {
//        WebServicesService locator = new WebServicesServiceLocator();
//        try {
//            log.fine("Postpaid web service init...");
//            postpaidService = locator.getWebServices();
//            log.fine("Postpaid web service initialized.");
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public String getClassOfService( String mobileNumber ) {
//        return null;
//    }
//
//    @Override
//    public String getAccountBalance( String mobileNumber ) {
//        QueryAcmBalReqDto bal = new QueryAcmBalReqDto();
//        bal.setMSISDN(mobileNumber);
//        String plan = "166";
//        bal.setPricePlanID(plan);
//        QueryAcmBalRetDto balResponse = null;
//        try {
//            balResponse = postpaidService.queryAcmBal(bal);
//            Long balanceMb = ( Long.parseLong(balResponse.getBalance()) / 1048576 );
//            String expiryDate = balResponse.getExpDate();
//
////            DBAdapter.log(stmt, uuid, msisdn, "", "balanceEnquiry", null, "V=" + voiceBalance + "|D=" + dataBalance );
//
////            if (dataBalance == null){
////                dataBalance = BigDecimal.ZERO;
////            } else {
////                expiryDate = getExpiryDate(balances.get(dataBalanceName).getExpiryDate());
////            }
//
//            String result = "Your GPRS bal= " + balanceMb + "mb exp on " + expiryDate;
//
//            log.fine("{ balance : mobileNumber : " + mobileNumber + ", payload : " + result + "}");
//
//            return result;
//        } catch (RemoteException e) {
//            String errorMessage = e.getMessage();
//            if ( (errorMessage != null) && (errorMessage.trim().toLowerCase().startsWith("errorcode")) ) {
//                errorMessage = errorMessage.split("=")[2].trim().substring(1);
//                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//            }
//            log.info("{txnType : bundle-purchase, mobileNumber : " + mobileNumber + ", error-message : " + errorMessage + "}");
//            return errorMessage;
//        }
//    }
//
//    @Override
//    public String debitAccount(TxnDto txn) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String creditAccount(TxnDto txn) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ServiceCommandResponse purchaseDataBundle( ServiceCommandRequest request ) {
//
//        ServiceCommandResponse response = new ServiceCommandResponse( request );
//
//        try {
//
//            Date expiry = AccountTransferUtils.getExpiryDateByType(request.getProductCode());
//
//            AddUserAcctByIndiPricePlanSubsReqDto postpaidRequest = new AddUserAcctByIndiPricePlanSubsReqDto();
//            postpaidRequest.setBundleType(request.getProductCode());
//            postpaidRequest.setEffDate(new Date());
//            postpaidRequest.setExpDate(expiry);
//            postpaidRequest.setMSISDN(request.getUserId());
//            postpaidRequest.setPricePlanID("166");
//
//            String plan = "166";
//
//            try {
//
//                QueryAcmBalReqDto bal = new QueryAcmBalReqDto();
//                bal.setMSISDN(request.getUserId());
//                bal.setPricePlanID(plan);  // 166 for all balance queries    :String
//                QueryAcmBalRetDto balResponse = null;
//                Date expiryDate = null;
//                try {
//
//                    balResponse = postpaidService.queryAcmBal(bal);
//                    expiryDate  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(balResponse.getExpDate());
//
//                    if (new DateTime(
//                            postpaidRequest.getExpDate())
//                                .isBefore(
//                                        new DateTime(expiryDate))) {
//                        postpaidRequest.setExpDate(expiryDate);
//                    }
//                } catch(Exception e) {
//                    /* Postpaid system might throw an error if new data balance. */
//                    /* Just ignore*/
//                }
//
//                postpaidService.addUserAcctByIndiPricePlanSubs(postpaidRequest);
//
//                balResponse = postpaidService.queryAcmBal(bal);
//                Long balanceMb = ( Long.parseLong(balResponse.getBalance()) / 1048576 );
//                expiryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(balResponse.getExpDate());
//
////                String result = "Your GPRS bal= " + balanceMb + "mb exp on " + expiryDate;
////
////                payload = "" +
////                        "You have bought the " +
////                        getBundleSizeFor(txn.getProductCode()) +
////                        "mb bundle. Your data bale= " +
////                        balanceMb +
////                        "mb exp on " + expiryDate;
////                log.fine("{ balance : mobileNumber : " + txn.getSourceId() + ", payload : " + payload + "}");
//
//                DataBundlePrice dataBundle = AccountManagerUtils.getDataBundleByType(request.getProductCode());
//
//                BigDecimal bundleSize = dataBundle.getBundleSize().setScale(2, RoundingMode.HALF_UP);
//                response.setProductSize( bundleSize );
//                response.setBeneficiaryAccountDataBalance( new BigDecimal(balanceMb) );
//                response.setBeneficiaryAccountExpiryDate( expiryDate );
//
//                return response;
//
//            } catch (RemoteException e) {
//                response.setResponseCode("096");
//                response.setNarrative(e.getMessage());
//                return response;
//            }
//        } catch(Exception e ) {
//            response.setResponseCode("096");
//            response.setNarrative(e.getMessage());
//            return response;
//        }
//    }
//
//    @Override
//    public List<BalanceDTO> getAccountBalances(String mobileNumber) {
//
//        try {
//
////            Date expiry = AccountTransferUtils.getExpiryDateByType(bundleType);
//
////            AddUserAcctByIndiPricePlanSubsReqDto request = new AddUserAcctByIndiPricePlanSubsReqDto();
////            request.setBundleType(bundleType);
////            request.setEffDate(new Date());
////            request.setExpDate(expiry);
////            request.setMSISDN(msisdn); //263733474747       :String
////            request.setPricePlanID("166");
//
//            QueryAcctBalReqDto request = new QueryAcctBalReqDto();
//            request.setMSISDN( mobileNumber );
//            QueryAcctBalRetDto result = postpaidService.queryAcctBal(request);
//            List<BalanceDTO> balances = new ArrayList<BalanceDTO>();
//            for ( BalDto bal : result.getBalDtoList() ){
//
//                //String walletId, String walletDescription, String balance, String
//                if ( "USD".equalsIgnoreCase(bal.getAcctResName())) {
//                    Date expDate = (bal.getExpDate() != null ? bal.getExpDate() : new DateTime().plus(30).toDate());
//                    String bal2 = bal.getBalance();
//                    String balance = "$0.00";
//                    if (bal2 != null) {
//                        balance = "$" + new DecimalFormat("###,####.##").format(new BigDecimal(bal2).divide(new BigDecimal("10000"), 2, RoundingMode.HALF_UP).doubleValue());
//                    }
//                    balances.add(new BalanceDTO("Core", "Main account ", balance,
//                            String.format("%1$tY-%1$tm-%1$td", expDate )
//                    ));
//
//                    System.out.println("getAcctResID : " + bal.getAcctResID());
//                    System.out.println( "getBalType : " +  bal.getBalType() );
//                    System.out.println( "getAcctResName : " +  bal.getAcctResName() );
//                    System.out.println( "getBalance : " +  bal.getBalance() );
//                    System.out.println( "getExpDate : " +  bal.getExpDate() );
//                    System.out.println( "getBalID : " + bal.getBalID() );
//                    break;
//                }
//            }
//
//            QueryAcmBalReqDto bal = new QueryAcmBalReqDto();
//            bal.setMSISDN( mobileNumber );
//            String plan = "166";
//            bal.setPricePlanID(plan);
//            QueryAcmBalRetDto balResponse = null;
//            try {
//                balResponse = postpaidService.queryAcmBal(bal);
//
//                Double balanceMb = ( Double.parseDouble(balResponse.getBalance()) / 1048576 );
//                String dataBalance = "0.00 MB";
//                if (balanceMb != null) {
//                    dataBalance = new DecimalFormat("###,###.##").format(balanceMb) + " MB";
//                }
//                String expiryDate = balResponse.getExpDate();
//
////            DBAdapter.log(stmt, uuid, msisdn, "", "balanceEnquiry", null, "V=" + voiceBalance + "|D=" + dataBalance );
//
////            if (dataBalance == null){
////                dataBalance = BigDecimal.ZERO;
////            } else {
////                expiryDate = getExpiryDate(balances.get(dataBalanceName).getExpiryDate());
////            }
//
////                result = "Your GPRS bal= " + balanceMb + "mb exp on " + expiryDate;
//
////                Date expDate = (expiryDate != null ? expiryDate : new DateTime().plus(30).toDate());
//                log.fine("{ balance : mobileNumber : " + mobileNumber + ", payload : " + result + "}");
//
//                if (expiryDate != null) {
//                    expiryDate = expiryDate.substring(0,11);
//                } else {
//                    expiryDate = String.format("%1$td/%1$tm/%1$tY", new DateTime().plus(30).toDate());
//                }
//                balances.add(new BalanceDTO("Data", "Data Account ", dataBalance,
//                        expiryDate
//                ));
//
//            } catch (RemoteException e) {
//                String errorMessage = e.getMessage();
//                if ( (errorMessage != null) && (errorMessage.trim().toLowerCase().startsWith("errorcode")) ) {
//                    errorMessage = errorMessage.split("=")[2].trim().substring(1);
//                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//                }
//                log.info("{txnType : bundle-purchase, mobileNumber : " + mobileNumber + ", error-message : " + errorMessage + "}");
//            }
//            return balances;
//        } catch(Exception e ) {
//            e.printStackTrace();
//            errorMessage = e.getMessage();
//            return null;
//        }
//    }
//
//    @Override
//    public List<MTSM> transfer(PduDto pdu, String beneficiaryMobileNumber, BigDecimal airtimeTransferAmount) {
//        return null;
//    }
//
//    @Override
//    public List<MTSM> voucherRecharge(PduDto pdu, String beneficiaryMobileNumber, String rechargeVoucher) {
//        return null;
//    }
//
//    @Override
//    public void setTxnDao(TxnDao txnDao) {
//       this.txnDao = txnDao;
//    }
//
//    @Override
//    public TxnDao getTxnDao() {
//        return txnDao;
//    }
//}
