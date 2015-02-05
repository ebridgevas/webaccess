package zw.co.telecel.vas.services.legacy.billing.prepaid;

import zw.co.telecel.vas.model.BalanceDTO;

import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author david@tekeshe.com
 */
public interface AccountBalanceFactory {

    public Set<BalanceDTO> balances ( String mobileNumber ) throws RemoteException;
}
