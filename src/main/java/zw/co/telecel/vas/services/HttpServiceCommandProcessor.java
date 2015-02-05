//package zw.co.telecel.vas.services;
//
//import zw.co.telecel.vas.dto.WebAccessCommand;
//import zw.co.telecel.vas.services.legacy.billing.AccountManager;
//import zw.co.telecel.vas.services.legacy.billing.postpaid.PostpaidAccountManager;
//import zw.co.telecel.vas.services.legacy.billing.prepaid.PrepaidAccountManager;
//import zw.co.telecel.vas.services.legacy.billing.util.AccountManagementUtils;
//import zw.co.telecel.vas.util.legacy.WebAccessCommandParser;
//import com.google.gson.Gson;
//import zw.co.telecel.vas.services.legacy.impl.*;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.Writer;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * david@tekeshe.com
// *
// */
//@javax.servlet.annotation.WebServlet(
//
//        // servlet name
//        name = "webAccess",
//
//        // servlet url pattern
//        value = {"/webAccess"},
//
//        // asynchronous support needed
//        asyncSupported = true,
//
//        // servlet init params
//        initParams = {
//                @WebInitParam(name = "thread-pool-size", value = "3")
//        }
//)
//public class HttpServiceCommandProcessor extends HttpServlet {
//
//    private static final Map<WebAccessCommand, WebAccessCommandProcessor> PROCESSORS;
//
//    private static final AccountManager prepaidAccountManager;
//    private static final AccountManager postpaidAccountManager;
//
//    static {
//
//        // TODO implement dependent injection using Google guice
//
//        prepaidAccountManager = new PrepaidAccountManager();
//        postpaidAccountManager = new PostpaidAccountManager();
//        AccountManagementUtils accountManagementUtils = new AccountManagementUtils(prepaidAccountManager);
//
//        PROCESSORS = new HashMap<>();
//
//        PROCESSORS.put( WebAccessCommand.GET_MOBILE_ACCOUNT_LIST,
//                        new GetMobileAccountListCommandProcessor(
//                                prepaidAccountManager,
//                                postpaidAccountManager,
//                                accountManagementUtils
//                        ));
//        PROCESSORS.put( WebAccessCommand.DATA_BUNDLE_PURCHASE,
//                        new DataBundlePurchaseCommandProcessor(
//                                prepaidAccountManager,
//                                postpaidAccountManager,
//                                accountManagementUtils,
//                                new DatabaseBackedSecurityTokenSender()
//                        ));
//
//        PROCESSORS.put( WebAccessCommand.AIRTIME_TRANSFER,
//                new AirtimeTransferCommandProcessor(
//                        prepaidAccountManager,
//                        postpaidAccountManager,
//                        accountManagementUtils,
//                        new DatabaseBackedSecurityTokenSender()
//                ));
//
//        PROCESSORS.put( WebAccessCommand.VOUCHER_RECHARGE,
//                new VoucherRechargeCommandProcessor(
//                        prepaidAccountManager,
//                        postpaidAccountManager,
//                        accountManagementUtils,
//                        new DatabaseBackedSecurityTokenSender()
//                ));
//
//    }
//
//    /**
//     *
//     * Init resources
//     *
//     * @throws ServletException
//     */
//    public void init() throws ServletException {
//    }
//
//    /**
//     * Reclaim resources.
//     */
//    public void destroy() {
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        /* Get Command. */
//        WebAccessCommand webAccessCommand = WebAccessCommandParser.parse(request.getParameter("service-command"));
//
//        /* Write output. */
//        PROCESSORS.get(webAccessCommand).process( request.getParameterMap(),
//                                                  webAccessCommand,
//                                                  UserAccountManager.USER_SESSIONS,
//                                                  response );
//    }
//
//    protected void write(String json, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        Writer writer = null;
//        try {
//            writer = response.getWriter();
//            System.out.println("json : " + json);
//            writer.write(json);
//            writer.flush();
//        } finally {
//            writer.close();
//        }
//    }
//
//    /**
//     * Marshal object t to a JSon string.
//     * @param t
//     * @param <T>
//     * @return
//     */
//    protected <T> String toJson(T t) {
//        if (t == null) {
//            return "";
//        }
//        Gson gson = new Gson();
//        return gson.toJson(t);
//    }
//}
