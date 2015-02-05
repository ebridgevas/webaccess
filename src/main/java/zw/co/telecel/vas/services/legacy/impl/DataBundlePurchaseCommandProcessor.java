//package zw.co.telecel.vas.services.legacy.impl;
//
//import zw.co.telecel.vas.dao.UserDao;
//import zw.co.telecel.vas.dto.WebAccessCommand;
//import zw.co.telecel.vas.model.*;
//import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.SystemException;
//import zw.co.telecel.vas.util.legacy.DatabaseException;
//import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
//import zw.co.telecel.vas.util.legacy.UserNotFoundException;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigInteger;
//import java.math.RoundingMode;
//import java.util.Map;
//
///**
// * david@tekeshe.com
// *
// */
//public class DataBundlePurchaseCommandProcessor implements WebAccessCommandProcessor {
//
//    private AccountManager prepaidAccountManager;
//    private AccountManager postpaidAccountManager;
//    private AccountManagementUtils accountManagementUtils;
//
//    private SecurityTokenSender securityTokenSender;
//
//    public DataBundlePurchaseCommandProcessor(
//            AccountManager prepaidAccountManager,
//            AccountManager postpaidAccountManager,
//            AccountManagementUtils accountManagementUtils,
//            SecurityTokenSender securityTokenSender) {
//
//        this.prepaidAccountManager = prepaidAccountManager;
//        this.postpaidAccountManager = postpaidAccountManager;
//        this.accountManagementUtils = accountManagementUtils;
//        this.securityTokenSender = securityTokenSender;
//    }
//
//    @Override
//    public void process( Map<String, String[]> parameters,
//                         WebAccessCommand webAccessCommand,
//                         Map<String, UserSession> userSessions,
//                         HttpServletResponse httpServletResponse) throws IOException {
//
//        // TODO validate userSession. Reject request if mobileNumber / sessionId doesn't match.
//
//        String sessionId = parameters.get("sessionId")[0];
//        String password = parameters.get("password")[0];
//        UserSession userSession = userSessions.get(sessionId);
//        User user = null;
//        String mobileNumber = null;
//        if (userSession != null ) {
//            String userId = userSession.getUserId();
//            try {
//                user = UserDao.findUser(userId);
//                /* Validate password. */
//                if (password == null || ! password.equals(user.getPassword())) {
//                    HttpResponseWriter.write("Invalid user password.", httpServletResponse );
//                    return;
//                }
//                mobileNumber = user.getMobileNumber();
//            } catch (UserNotFoundException e) {
//                HttpResponseWriter.write("Invalid user.", httpServletResponse );
//                return;
//            } catch (DatabaseException e) {
//                HttpResponseWriter.write("System error. Try again.", httpServletResponse );
//                return;
//            }
//        } else {
//            HttpResponseWriter.write("Invalid session id. Try again.", httpServletResponse );
//            return;
//        }
//        final ServiceCommandRequest request =
//                new ServiceCommandRequest(
//                        ServiceCommand.DATA_BUNDLE_PURCHASE,
//                        mobileNumber,
//                        IdType.MOBILE_NUMBER,
//                        parameters.get("beneficiaryMobileNumber")[0],
//                        parameters.get("product")[0],
//                        "" + (System.currentTimeMillis() + 1),
//                        "");
//
//        Boolean isPostpaid = null;
//        try {
//
//            isPostpaid = accountManagementUtils.isPostpaid( request.getUserId() );
//        } catch (SystemException e) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.getWriter().write(e.getMessage());
//            return;
//        }
//
//        final AccountManager accountManager =
//                isPostpaid  ?
//                        postpaidAccountManager : prepaidAccountManager;
//
//        ServiceCommandResponse response = accountManager.purchaseDataBundle( request );
//
//        String narrative = null;
//        if ( request.isBeneficiaryOwnPhone()) {
//            narrative = "You have bought the " + response.getProductSize() + "mb bundle. " +
//                    " Your bal = " + response.getSourceAccountCoreBalance().setScale(2, RoundingMode.HALF_UP) + "usd. ";
//            response.setNarrative(narrative);
//
//            OutboundMsg outboundMsg =
//                    new OutboundMsg(
//                            new BigInteger("" + System.currentTimeMillis()),
//                            "QUEUED",
//                            "SMS".equalsIgnoreCase(user.getNotificationAgent()) ? "SMS" : "EMAIL",
//                            "SMS".equalsIgnoreCase(user.getNotificationAgent()) ?
//                                    user.getMobileNumber() :
//                                    user.getEmailAddress(),
//                            "",
//                            narrative );
//
//            securityTokenSender.send( outboundMsg );
//        } else {
//            narrative = "You have bought the " + response.getProductSize() + "mb bundle " +
//                    " for " + request.getDestinationId() +
//                    " Your bal = " + response.getSourceAccountCoreBalance() + "usd. ";
//            response.setNarrative( narrative );
//
//            OutboundMsg outboundMsg =
//                    new OutboundMsg(
//                            new BigInteger("" + System.currentTimeMillis()),
//                            "QUEUED",
//                            "SMS",
//                            request.getDestinationId(),
//                            "",
//                            narrative );
//
//            securityTokenSender.send( outboundMsg );
//        }
//
//        HttpResponseWriter.write(response.getNarrative(), httpServletResponse );
//    }
//}