//package zw.co.telecel.vas.services.legacy.impl;
//
//import zw.co.telecel.vas.dao.UserDao;
//import zw.co.telecel.vas.dto.WebAccessCommand;
//import zw.co.telecel.vas.model.UserSession;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils;
//import zw.co.telecel.vas.services.legacy.billing.util.SystemException;
//import zw.co.telecel.vas.util.legacy.DatabaseException;
//import zw.co.telecel.vas.util.legacy.HttpResponseWriter;
//import zw.co.telecel.vas.util.legacy.UserNotFoundException;
//import com.google.gson.Gson;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.*;
//
///**
// * david@ebridgevas.com
// *
// */
//public class GetMobileAccountListCommandProcessor implements WebAccessCommandProcessor {
//
//    private AccountManager prepaidAccountManager;
//    private AccountManager postpaidAccountManager;
//    private AccountManagementUtils accountManagementUtils;
//
//    public GetMobileAccountListCommandProcessor(
//            AccountManager prepaidAccountManager,
//            AccountManager postpaidAccountManager,
//            AccountManagementUtils accountManagementUtils) {
//
//        this.prepaidAccountManager = prepaidAccountManager;
//        this.postpaidAccountManager = postpaidAccountManager;
//        this.accountManagementUtils = accountManagementUtils;
//    }
//
//    @Override
//    public void process( Map<String, String[]> parameters,
//                         WebAccessCommand webAccessCommand,
//                         Map<String, UserSession> userSessions,
//                         HttpServletResponse httpServletResponse) throws IOException {
//
//        // TODO validate userSession
//        String sessionId = parameters.get("sessionId")[0];
//
//        UserSession userSession = userSessions.get(sessionId);
//        if (userSession != null ) {
////                if (userSessions.get(mobileNumber).getSessionId().equals(sessionId) ) {
//            String userId = userSession.getUserId();
//            String mobileNumber = null;
//            try {
//                mobileNumber = UserDao.findUser(userId).getMobileNumber();
//                AccountManager accountManager =
//                        accountManagementUtils.isPostpaid(mobileNumber) ?
//                                postpaidAccountManager : prepaidAccountManager;
//
//                HttpResponseWriter.write(accountManager.getAccountBalances(mobileNumber), httpServletResponse);
////                  HttpResponseWriter.write(readAccountList(), httpServletResponse);
//            } catch (UserNotFoundException e) {
//                HttpResponseWriter.write("Invalid user.", httpServletResponse);
//            } catch (DatabaseException e) {
//                HttpResponseWriter.write("System error occurred. Please try.", httpServletResponse);
//            } catch (SystemException e) {
//                httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                httpServletResponse.getWriter().write(e.getMessage());
//                return;
//            }
////                }  else {
////                    HttpResponseWriter.write("You are not logged on.", response);
////                }
//
//
//
//        } else {
//            HttpResponseWriter.write("Invalid session.", httpServletResponse);
//        }
//    }
//
//    /*
//     * Test data.
//    protected String readAccountList() {
//        List<BalanceDTO> balances = new ArrayList<BalanceDTO>();
//        balances.add(new BalanceDTO("Core",        "Main Wallet", "20.50", new SimpleDateFormat("yyyy/MM/dd").format(new Date()) ));
//        balances.add(new BalanceDTO("Gprs_bundle", "Data Bundles", "40.50", new SimpleDateFormat("yyyy/MM/dd").format(new Date()) ));
//        balances.add(new BalanceDTO("Gprs_bonus",  "Data Bonus", "120.50", new SimpleDateFormat("yyyy/MM/dd").format(new Date()) ));
//
//        return new Gson().toJson(balances);
//    }
//    */
//
//}
