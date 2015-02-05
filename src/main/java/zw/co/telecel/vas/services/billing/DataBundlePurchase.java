package zw.co.telecel.vas.services.billing;

import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.util.TransactionException;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author david@tekeshe.com
 */
public interface DataBundlePurchase {

    public BalanceDTO[] dataBundlePurchase(
                                String uuid, String mobileNumber, DataBundleDTO dataBundle, String beneficiaryId,
                                String paymentMethod, String oneTimePassword )
            throws RemoteException, TransactionException;

}
