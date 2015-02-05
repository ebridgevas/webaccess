package zw.co.telecel.vas.services.billing.inject;

import com.comverse_in.prepaid.ccws.ServiceSoap;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.ztesoft.zsmart.bss.ws.customization.zimbabwe.WebServices;
import zw.co.telecel.vas.services.billing.BalanceTransfer;
import zw.co.telecel.vas.services.billing.BillingPlatformInterfaceImpl;
import zw.co.telecel.vas.services.billing.DataBundlePurchase;
import zw.co.telecel.vas.services.billing.VoucherRecharge;
import zw.co.telecel.vas.services.billing.inject.providers.PostpaidPlatformServiceSoapProvider;
import zw.co.telecel.vas.services.billing.inject.providers.PrepaidPlatformServiceSoapProvider;
import zw.co.telecel.vas.services.billing.postpaid.PostpaidAccountBalanceFactory;
import zw.co.telecel.vas.services.billing.postpaid.PostpaidDataBundlePurchase;
import zw.co.telecel.vas.services.billing.prepaid.PrepaidAccountBalanceFactory;
import zw.co.telecel.vas.services.billing.prepaid.PrepaidBalanceTransfer;
import zw.co.telecel.vas.services.billing.prepaid.PrepaidDataBundlePurchase;
import zw.co.telecel.vas.services.billing.prepaid.PrepaidVoucherRecharge;
import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
import zw.co.telecel.vas.services.legacy.billing.BillingPlatformInterface;
import zw.co.telecel.vas.services.legacy.billing.prepaid.AccountBalanceFactory;
import zw.co.telecel.vas.services.legacy.impl.DatabaseBackedSecurityTokenSender;
import zw.co.telecel.vas.services.util.ClassOfServiceBasedSubscribedPackageIdentifier;
import zw.co.telecel.vas.services.util.ConfigurationService;
import zw.co.telecel.vas.services.util.JsonConfigurationService;
import zw.co.telecel.vas.services.util.SubscribedPackageIdentifier;


/**
 * @author david@tekeshe.com
 */
public class BillingPlatformInterfaceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(ConfigurationService.class).to(JsonConfigurationService.class);

        bind(SubscribedPackageIdentifier.class).to(ClassOfServiceBasedSubscribedPackageIdentifier.class);

        bind(AccountBalanceFactory.class).annotatedWith(Names.named("prepaidAccountBalanceFactory"))
                .to(PrepaidAccountBalanceFactory.class);

        bind(AccountBalanceFactory.class).annotatedWith(Names.named("postpaidAccountBalanceFactory"))
                .to(PostpaidAccountBalanceFactory.class);

        bind(DataBundlePurchase.class).annotatedWith(Names.named("prepaidDataBundlePurchase"))
                .to(PrepaidDataBundlePurchase.class);

        bind(DataBundlePurchase.class).annotatedWith(Names.named("postpaidDataBundlePurchase"))
                .to(PostpaidDataBundlePurchase.class);

        bind(BalanceTransfer.class).annotatedWith(Names.named("prepaidBalanceTransfer"))
                .to(PrepaidBalanceTransfer.class);

        bind(VoucherRecharge.class).annotatedWith(Names.named("prepaidVoucherRecharge"))
                .to(PrepaidVoucherRecharge.class);

        bind(ServiceSoap.class).annotatedWith(Names.named("prepaidServiceSoapProvider"))
                .toProvider(PrepaidPlatformServiceSoapProvider.class);

        bind(WebServices.class).annotatedWith(Names.named("postpaidServiceSoapProvider"))
                .toProvider(PostpaidPlatformServiceSoapProvider.class);

        bind(SecurityTokenSender.class).annotatedWith(Names.named("messageSender"))
                .to(DatabaseBackedSecurityTokenSender.class);

        bind(BillingPlatformInterface.class).to(BillingPlatformInterfaceImpl.class);
    }
}