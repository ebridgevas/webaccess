//package zw.co.telecel.vas.services.legacy.impl;
//
//import zw.co.telecel.vas.dao.UserDao;
//import zw.co.telecel.vas.dto.WebAccessCommand;
//import zw.co.telecel.vas.model.BalanceDTO;
//import zw.co.telecel.vas.model.UserSession;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.BillingPlatformInterface;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.SystemException;
//import zw.co.telecel.vas.util.legacy.DatabaseException;
//import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
//import zw.co.telecel.vas.util.legacy.UserNotFoundException;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//
///**
// * david@ebridgevas.com
// *
// */
//public class BalanceListingCommandProcessor implements WebAccessCommandProcessor {
//
//    private BillingPlatformInterface billingPlatformInterface;
//
//    public BalanceListingCommandProcessor( BillingPlatformInterface billingPlatformInterface )  {
//
//        this.billingPlatformInterface = billingPlatformInterface;
//    }
//
//    @Override
//    public void process( Map<String, String[]> parameters,
//                         WebAccessCommand webAccessCommand,
//                         Map<String, UserSession> userSessions,
//                         HttpServletResponse httpServletResponse) throws IOException {
//
//        try {
//            String mobileNumber = parameters.get("mobile-number")[0];
//            HttpResponseWriter.write(billingPlatformInterface.balances( mobileNumber ), httpServletResponse);
//        } catch ( Exception e ) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.getWriter().write(e.getMessage());
//            return;
//        }
//    }
//}
