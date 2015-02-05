package zw.co.telecel.vas.services.billing;

import zw.co.telecel.dto.BillingRequestDTO;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.util.TransactionException;

import java.rmi.RemoteException;

/**
 * @author david@tekeshe.com
 */
public interface VoucherRecharge {

    public BalanceDTO recharge( String uuid, String mobileNumber, String rechargeVoucher )
            throws RemoteException, TransactionException;

}
