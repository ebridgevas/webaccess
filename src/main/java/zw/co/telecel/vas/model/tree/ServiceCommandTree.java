package zw.co.telecel.vas.model.tree;//package zw.co.telecel.vas.model.tree;
//
//import zw.co.telecel.vas.services.ServiceCommandProcessor;
//import com.google.inject.Inject;
//
//import static zw.co.telecel.vas.util.MathUtils.toBigDecimal;
//
///**
// * david@ebridgevas.com
// *
// */
//
///* ShortCode. */
//
//    /* -- Menu. */
//
//    /* ---- Products. */
//
//    /* ----- Processor. */
//
//    /*
//
//        Message Flow Specification:
//
//        2013
//            1. Subscription Request
//            2. Balance
//            3. Cancel Subscription
//                1.
//                    1. $0.50
//                        1. <DataBundlePurchase, $0.50>
//                    2. $1.00
//                        2. <DataBundlePurchase, $0.50>
//                2. <BalanceEnquiry>
//                3. <CancelSubscription>
//        144
//
//        Data Model:
//
//        ussdMenus
//
//        nodelId,    parentNodeId,   productId,  nodeDescription,            childType,  serviceCommandProcessClassName
//        2013,       0,              0,          Millennium service,         SUB_NODE,   null
//        144,        0,              0,          Data Bundle Purchase,       SUB_NODE,   null
//        1000,       2013,           0,          Subscription Request,       SUB_NODE,   null
//        1001,       2013,           0,          Balance,                    PROCESSOR,  com.ebridge.vasgw.command.BalanceEnquiryCommandProcessor
//        1002,       2013,           0,          Cancel Subscription,        PROCESSOR,  com.ebridge.vasgw.command.CancelSubscriptionCommandProcessor
//        1003,       1000            1,          null,                       PRODUCT,    com.ebridge.vasgw.command.VoiceBundlePurchaseCommandProcessor,2000
//        1004,       1000,           2,          null,                       PRODUCT,    com.ebridge.vasgw.command.VoiceBundlePurchaseCommandProcessor,2001
//        1005,       144,            1,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2002
//        1006,       144,            2,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2003
//        1007,       144,            3,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2004
//        1008,       144,            4,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2005
//        1009,       144,            5,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2006
//        1010,       144,            6,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2007
//        1011,       144,            7,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2008
//        1012,       144,            8,          null,                       PRODUCT,    com.ebridge.vasgw.command.DataBundlePurchaseCommandProcessor,2009
//
//        product
//        serviceId,  parentNodeId,     productId,  productDescription, debit,  credit
//        2013,       1000,               1,          $0.50,              0.50,   0.50
//        2013,       1000,               2,          $1.00,              1.00,   1.00
//        144,        144,                1,          6MB Data Bundle,    0.65,   0.66
//        144,        144,                2,          10MB Data Bundle,   0.65,   0.66
//        144,        144,                3,          40MB Data Bundle,   0.65,   0.66
//        144,        144,                4,          150MB Data Bundle,  0.65,   0.66
//        144,        144,                5,          320MB Data Bundle,  0.65,   0.66
//        144,        144,                6,          800MB Data Bundle,  0.65,   0.66
//        144,        144,                7,          2GB Data Bundle,    0.65,   0.66
//        144,        144,                8,          4GBB Data Bundle,   0.65,   0.66
//     */
//
//public class ServiceCommandTree {
//
//    @Inject
//    private ServiceCommandProcessor balanceEnquiryProcessor;
//
//    @Inject
//    private ServiceCommandProcessor dataBundlePurchaseProcessor;
//
//    private final TreeNode<Content> root;
//
//    public ServiceCommandTree() {
//
//        /* Root. */
//        root = new TreeNode<Content>( new Content(null, null, null, null) );
//
//        /* Data Bundle Service Tree. */
//        Content dataBundle = new Content("144", null, null, "Data Bundle Purchase Service");
//
//        TreeNode<Content> dataBundleNode = root.addChild(dataBundle);
//
//        /* Data Bundle Purchase Command. */
//        Content purchaseOption = new Content("144", null, null, "Buy a bundle");
//
//        TreeNode<Content> purchaseOptionNode = dataBundleNode.addChild(purchaseOption);
//
//        purchaseOptionNode.addChild(
//                new Content(
//                        "144",
//                        new Product("1",
//                                "10MB Data Bundle",
//                                toBigDecimal(0.65),
//                                toBigDecimal(0.65),
//                                toBigDecimal(0.66),
//                                "MONEY",
//                                "DATA_OCTETS"
//                                ),
//                        dataBundlePurchaseProcessor,
//                        "65c for 10MB Data Bundle" ));
//
//        purchaseOptionNode.addChild(
//                new Content(
//                        "144",
//                        new Product("2",
//                                "40MB Data Bundle",
//                                toBigDecimal(1.00),
//                                toBigDecimal(1.00),
//                                toBigDecimal(1.11),
//                                "MONEY",
//                                "DATA_OCTETS"
//                        ),
//                        dataBundlePurchaseProcessor,
//                        "$1.00 for 40MB Data Bundle" ));
//
//        /* Balance Enquiry Command. */
//        dataBundleNode.addChild(new Content("144", null, balanceEnquiryProcessor, "Balance Enquiry"));
//
//    }
//
//    public TreeNode<Content> getRoot() {
//        return root;
//    }
//}
