package zw.co.telecel.vas.services.billing;

import zw.co.telecel.dto.BillingRequestDTO;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.util.BalanceTransferReversalFailedException;
import zw.co.telecel.vas.util.TransactionException;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * @author david@tekeshe.com
 */
public interface BalanceTransfer {

    public BalanceDTO[] transfer(   String uuid,
                                    String mobileNumber,
                                    String beneficiaryId,
                                    BigDecimal amount,
                                    String paymentMethod,
                                    String oneTimePassword )
            throws RemoteException, TransactionException, BalanceTransferReversalFailedException;
}
