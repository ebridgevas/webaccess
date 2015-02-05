package zw.co.telecel.vas.services.billing;

import com.google.inject.Inject;
import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.model.OutboundMsg;
import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
import zw.co.telecel.vas.services.legacy.billing.BillingPlatformInterface;
import zw.co.telecel.vas.services.legacy.billing.prepaid.AccountBalanceFactory;
import zw.co.telecel.vas.services.util.ConfigurationService;
import zw.co.telecel.vas.services.util.SubscribedPackageIdentifier;
import zw.co.telecel.vas.util.BalanceTransferReversalFailedException;
import zw.co.telecel.vas.util.TransactionException;

import javax.inject.Named;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

/**
 * @author david@tekeshe.com
 */
public class BillingPlatformInterfaceImpl implements BillingPlatformInterface {

    @Inject
    private ConfigurationService configurationService;

    @Inject
    private SubscribedPackageIdentifier subscribedPackageIdentifier;

    @Inject
    @Named("prepaidAccountBalanceFactory")
    private AccountBalanceFactory prepaidAccountBalanceFactory;

    @Inject
    @Named("postpaidAccountBalanceFactory")
    private AccountBalanceFactory postpaidAccountBalanceFactory;

    @Inject
    @Named("prepaidDataBundlePurchase")
    private DataBundlePurchase prepaidDataBundlePurchase;

    @Inject
    @Named("postpaidDataBundlePurchase")
    private DataBundlePurchase postpaidDataBundlePurchase;

    @Inject
    @Named("prepaidBalanceTransfer")
    private BalanceTransfer prepaidBalanceTransfer;

    @Inject
    @Named("prepaidVoucherRecharge")
    private VoucherRecharge prepaidVoucherRecharge;

    @Inject
    @Named("messageSender")
    private SecurityTokenSender messageSender;

    @Override
    public Map<String, DataBundleDTO> dataBundleList() {
        return configurationService.config().getDataBundles();
    }

    @Override
    public String subscribedPackage( String mobileNumber ) throws RemoteException {
        return subscribedPackageIdentifier.subscribedPackage( mobileNumber );
    }

    @Override
    public Set<BalanceDTO> balances( String mobileNumber ) throws RemoteException {

        return "PREPAID".equalsIgnoreCase( subscribedPackage( mobileNumber ) ) ?
                    prepaidAccountBalanceFactory.balances( mobileNumber )
                        : postpaidAccountBalanceFactory.balances( mobileNumber );
    }

    @Override
    public BalanceDTO[] dataBundlePurchase( String uuid, String mobileNumber, String productCode, String beneficiaryId,
                                            String paymentMethod, String oneTimePassword)
            throws RemoteException, TransactionException  {

        DataBundleDTO dataBundle = configurationService.config().getDataBundles().get(productCode);

        return "PREPAID".equalsIgnoreCase( subscribedPackage( mobileNumber ) ) ?
                  prepaidDataBundlePurchase.dataBundlePurchase( uuid, mobileNumber, dataBundle, beneficiaryId,
                                                                paymentMethod, oneTimePassword)
                   : postpaidDataBundlePurchase.dataBundlePurchase( uuid, mobileNumber, dataBundle, mobileNumber,
                                                                    paymentMethod, oneTimePassword);
    }

    @Override
    public BalanceDTO[] transfer( String uuid, String mobileNumber, String beneficiaryId, BigDecimal amount )
            throws RemoteException, BalanceTransferReversalFailedException, TransactionException {

        if ( ! "PREPAID".equalsIgnoreCase( subscribedPackage(mobileNumber) ) )
            throw new TransactionException(
                    "Mobile number 0" + mobileNumber.substring(3) + " is not on prepaid package");

        if ( ! "PREPAID".equalsIgnoreCase( subscribedPackage(beneficiaryId) ) )
            throw new TransactionException(
                    "Mobile number 0" + beneficiaryId.substring(3) + " is not on prepaid package");

        return prepaidBalanceTransfer.transfer( uuid, mobileNumber, beneficiaryId, amount );
    }

    @Override
    public BalanceDTO recharge( String uuid, String beneficiaryId, String rechargeVoucher )
            throws RemoteException, TransactionException {

        if ( ! "PREPAID".equalsIgnoreCase( subscribedPackage(beneficiaryId) ) )
            throw new TransactionException(
                    "Mobile number 0" + beneficiaryId.substring(3) + " is not on prepaid package");

        return prepaidVoucherRecharge.recharge( uuid, beneficiaryId, rechargeVoucher );
    }

    @Override
    public void sendMessage(BigInteger uuid, String destinationAddress, String messagingAgent, String message)
            throws IOException {

        messageSender.send(
                new OutboundMsg(uuid, "QUEUED", messagingAgent, destinationAddress, "", message ) );
    }
}
