//package zw.co.telecel.vas.services.legacy.billing;
//
//import zw.co.telecel.vas.dao.TxnDao;
//import zw.co.telecel.vas.dto.BalanceResponseDTO;
//import zw.co.telecel.vas.dto.PduDto;
//import zw.co.telecel.vas.dto.TxnDto;
//import zw.co.telecel.vas.model.BalanceDTO;
//import zw.co.telecel.vas.model.MTSM;
//import zw.co.telecel.vas.model.ServiceCommandRequest;
//import zw.co.telecel.vas.model.ServiceCommandResponse;
//
//import java.math.BigDecimal;
//import java.rmi.RemoteException;
//import java.util.List;
//
///**
// * david@ebridgevas.com
// *
// */
//public interface AccountManager {
//    String getClassOfService(String mobileNumber);
//    String getAccountBalance(String mobileNumber);
//    ServiceCommandResponse purchaseDataBundle(ServiceCommandRequest request);
//    String creditAccount(TxnDto txn);
//    String debitAccount(TxnDto txn);
//
//    List<BalanceDTO> getAccountBalances(String mobileNumber);
//    public void balances(String mobileNumber, BalanceResponseDTO callback) throws RemoteException;
//
//    List<MTSM> transfer(PduDto pdu, String beneficiaryMobileNumber, BigDecimal airtimeTransferAmount);
//    List<MTSM> voucherRecharge(PduDto pdu, String beneficiaryMobileNumber, String rechargeVoucher);
//
//    void setTxnDao(TxnDao txnDao);
//    TxnDao getTxnDao();
//}
