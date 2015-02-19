package zw.co.telecel.vas.services.legacy.billing;

import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.util.BalanceTransferReversalFailedException;
import zw.co.telecel.vas.util.TransactionException;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

/**
 * @author david@tekeshe.com
 */
public interface BillingPlatformInterface {

    public Map<String, DataBundleDTO> dataBundleList();

    public String subscribedPackage( String mobileNumber ) throws RemoteException;

    public Set<BalanceDTO> balances( String mobileNumber ) throws RemoteException;

    public BalanceDTO[] dataBundlePurchase( String uuid, String mobileNumber, String productCode, String beneficiaryId,
                                            String paymentMethod, String oneTimePassword,
                                            String dataBundleServiceCommand  )
               throws RemoteException, TransactionException;

    public BalanceDTO[] transfer( String uuid,
                                  String mobileNumber,
                                  String beneficiaryId,
                                  BigDecimal amount,
                                  String paymentMethod,
                                  String oneTimePassword )
            throws RemoteException, BalanceTransferReversalFailedException, TransactionException;


    public BalanceDTO recharge( String uuid, String beneficiaryId, String rechargeVoucher  )
            throws RemoteException, TransactionException;

    public void sendMessage( BigInteger uuid, String destinationAddress, String messagingAgent, String message) throws IOException;
}
