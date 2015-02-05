package zw.co.telecel.vas.services.billing.prepaid.processors;

import com.comverse_in.prepaid.ccws.BalanceCreditAccount;
import com.comverse_in.prepaid.ccws.ServiceSoap;
import com.google.inject.Provider;
import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.vas.services.legacy.billing.prepaid.AccountBalanceFactory;
import zw.co.telecel.vas.util.TransactionException;

import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * @author david@tekeshe.com
 */
public class TelecashRefundProcessor {

    public static Boolean process(
            AccountBalanceFactory prepaidAccountBalanceFactory,
            Provider<ServiceSoap> prepaidServiceSoapProvider,
            String uuid, String sourceId, DataBundleDTO dataBundle, String beneficiaryId,
            Calendar expirationDate)
            throws RemoteException, TransactionException {

        return null;
    }
}
