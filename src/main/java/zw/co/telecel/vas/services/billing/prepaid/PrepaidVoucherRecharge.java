package zw.co.telecel.vas.services.billing.prepaid;

import com.comverse_in.prepaid.ccws.ServiceSoap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.log4j.Category;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.services.billing.VoucherRecharge;
import zw.co.telecel.vas.services.legacy.billing.prepaid.AccountBalanceFactory;
import zw.co.telecel.vas.util.TransactionException;

import javax.inject.Named;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import static zw.co.telecel.vas.services.util.Util.balance;

/**
 * @author david@tekeshe.com
 */
public class PrepaidVoucherRecharge implements VoucherRecharge {

    @Inject
    @Named("prepaidServiceSoapProvider")
    private Provider<ServiceSoap> prepaidServiceSoapProvider;

    @Inject
    @Named("prepaidAccountBalanceFactory")
    private AccountBalanceFactory prepaidAccountBalanceFactory;

    private static final String CORE_BALANCE = "Core";

    private static Category CAT = Category.getInstance(PrepaidBalanceTransfer.class);

    @Override
    public BalanceDTO recharge( String uuid, String beneficiaryId, String rechargeVoucher )
            throws RemoteException, TransactionException {
        BalanceDTO balanceBeforeTxn = balance(
                CORE_BALANCE,
                prepaidAccountBalanceFactory.balances(beneficiaryId));
        prepaidServiceSoapProvider.get()
                .rechargeAccountBySubscriber(
                        beneficiaryId.substring(3),
                        null,
                        rechargeVoucher, uuid); //"Voucher recharge # " + uuid);

        BalanceDTO balanceAfterTxn = balance(
                CORE_BALANCE,
                prepaidAccountBalanceFactory.balances(beneficiaryId));
        balanceAfterTxn.setMobileNumber( beneficiaryId );
        balanceAfterTxn.setOtherAmount( balanceAfterTxn.getBalance().subtract( balanceBeforeTxn.getBalance()) );

        CAT.info( "{ result : " + balanceAfterTxn + "}" );

        return balanceAfterTxn;
    }
}
