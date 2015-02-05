//package zw.co.telecel.vas.services.legacy.impl;
//
//import zw.co.telecel.vas.dao.UserDao;
//import zw.co.telecel.vas.dto.PduDto;
//import zw.co.telecel.vas.dto.WebAccessCommand;
//import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.SystemException;
//import zw.co.telecel.vas.util.legacy.DatabaseException;
//import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
//import zw.co.telecel.vas.util.legacy.UserNotFoundException;
//import zw.co.telecel.vas.model.MTSM;
//import zw.co.telecel.vas.model.OutboundMsg;
//import zw.co.telecel.vas.model.User;
//import zw.co.telecel.vas.model.UserSession;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.List;
//import java.util.Map;
//
///**
// * david@ebridgevas.com
// *
// */
//public class AirtimeTransferCommandProcessor implements WebAccessCommandProcessor {
//
//    private AccountManager prepaidAccountManager;
//    private AccountManager postpaidAccountManager;
//    private AccountManagementUtils accountManagementUtils;
//    private SecurityTokenSender securityTokenSender;
//
//    public AirtimeTransferCommandProcessor(
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
//        // TODO validate userSession
//
//        String sessionId = parameters.get("sessionId")[0];
//
//        UserSession userSession = userSessions.get(sessionId);
//        User user = null;
//        String mobileNumber = null;
//        if (userSession != null ) {
//            String userId = userSession.getUserId();
//            try {
//                user = UserDao.findUser(userId);
//                mobileNumber = user.getMobileNumber();
//            } catch (UserNotFoundException e) {
//                HttpResponseWriter.write("Invalid user.", httpServletResponse);
//                return;
//            } catch (DatabaseException e) {
//                HttpResponseWriter.write("System error. Try again.", httpServletResponse );
//                return;
//            }
//        } else {
//            HttpResponseWriter.write("Invalid session id. Try again.", httpServletResponse );
//            return;
//        }
//
//        String amount = parameters.get("transferAmount")[0];
//        amount = amount.replace('$',' ');
//        amount = amount.replace('c',' ');
//        System.out.println("Airtime transfer amount : " + amount);
//        BigDecimal transferAmount =
//                new BigDecimal(amount.trim());
//        String beneficiaryMobileNumber = parameters.get("airtimeTransferBeneficiary")[0];
//
//        Boolean isPostpaid = null;
//        try {
//
//            isPostpaid = accountManagementUtils.isPostpaid( mobileNumber );
//        } catch (SystemException e) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.getWriter().write(e.getMessage());
//            return;
//        }
//
//        if ( isPostpaid ) {
//            HttpResponseWriter.write("Source mobile number must be on prepaid package.", httpServletResponse);
//            return;
//        }
//
//        try {
//
//            isPostpaid = accountManagementUtils.isPostpaid( beneficiaryMobileNumber );
//        } catch (SystemException e) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            httpServletResponse.getWriter().write(e.getMessage());
//            return;
//        }
//
//        if ( isPostpaid ) {
//            HttpResponseWriter.write("Destination mobile number must be on prepaid package.", httpServletResponse);
//            return;
//        }
//
//        final AccountManager accountManager = prepaidAccountManager;
//
//        PduDto pdu = new PduDto();
//        pdu.setUuid("" + System.currentTimeMillis());
//        pdu.setSourceId(mobileNumber);
//        pdu.setDestinationId("33350");
//
//        List<MTSM> result = accountManager.transfer(pdu, beneficiaryMobileNumber, transferAmount);
//
//        HttpResponseWriter.write(result.get(0).getShortMessage(), httpServletResponse);
//
//        /* Send sms to beneficiary. */
//        OutboundMsg outboundMsg =
//                new OutboundMsg(
//                        new BigInteger("" + System.currentTimeMillis()),
//                        "QUEUED",
//                        "SMS",
//                        result.get(1).getDestinationAddress(),
//                        "",
//                        result.get(1).getShortMessage() );
//            securityTokenSender.send( outboundMsg );
//
//        /* Send sms or email to source. */
//        outboundMsg =
//                new OutboundMsg(
//                        new BigInteger("" + System.currentTimeMillis()),
//                        "QUEUED",
//                        "SMS".equalsIgnoreCase(user.getNotificationAgent()) ? "SMS" : "EMAIL",
//                        "SMS".equalsIgnoreCase(user.getNotificationAgent()) ?
//                                user.getMobileNumber() :
//                                user.getEmailAddress(),
//                        "",
//                        result.get(0).getShortMessage() );
//
//        securityTokenSender.send( outboundMsg );
//    }
//}
