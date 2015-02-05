package zw.co.telecel.vas.services.impl;//package zw.co.telecel.vas.services.impl;
//
//import zw.co.telecel.vas.model.ServiceCommandRequest;
//import zw.co.telecel.vas.model.ServiceCommandResponse;
//import zw.co.telecel.vas.services.BillingService;
//import zw.co.telecel.vas.services.ServiceCommandProcessor;
//import com.google.inject.Inject;
//
///**
// * david@ebridgevas.com
// *
// */
//public class BalanceEnquiryCommandProcessor implements ServiceCommandProcessor {
//
//    private final BillingService billingService;
//
//    @Inject
//    public BalanceEnquiryCommandProcessor( BillingService billingService ) {
//        this.billingService = billingService;
//    }
//
//    @Override
//    public ServiceCommandResponse process(ServiceCommandRequest request) {
//        return billingService.balanceEnquiry(request);
//    }
//}
