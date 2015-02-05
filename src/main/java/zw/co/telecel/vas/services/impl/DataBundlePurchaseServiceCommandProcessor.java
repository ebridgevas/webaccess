package zw.co.telecel.vas.services.impl;//package com.ebridgevas.services.impl;
//
//import com.ebridge.commons.domain.TxnDto;
//import com.ebridge.commons.dto.PduDto;
//import MTSM;
//import com.ebridgevas.model.PduType;
//import UserSession;
//import ServiceCommandProcessor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.ebridgevas.services.ServerPDUEventListenerImpl.PREPAID_ACCOUNT_MANAGER;
//import static com.ebridgevas.services.ServerPDUEventListenerImpl.USER_SESSIONS;
//
///**
// * Created with IntelliJ IDEA.
// * User: David
// * Date: 2/24/13
// * Time: 10:35 AM
// * To change this template use File | Settings | File Templates.
// */
//public class DataBundlePurchaseServiceCommandProcessor
//        extends AbstractServiceCommandProcessor
//        implements ServiceCommandProcessor {
//
//    @Override
//    public List<MTSM> process(PduDto pdu) {
//
//        List<MTSM> result = new ArrayList<MTSM>();
//
//        /* Get current user session. */
//        UserSession userSession = USER_SESSIONS.get(pdu.getSourceId());
//        Boolean isInitialDial = isInitialDial(userSession);
//
//        /* Is it USSD or SMS. */
//        PduType pduType = isInitialDial ? getPduTypeFrom(pdu.getShortMessage()) : userSession.getPduType();
//
//        /* Create TxnDto. */
//        TxnDto txn = createTxnDto(pdu);
//        txn.setSessionId(getSessionId(pdu, pduType));
//        txn.setProductCode(pdu.getShortMessage().trim());
//
//        System.out.println("########### txn.getProductCode() : " + txn.getProductCode());
//
//        /* Purchase data bundle. */
//        result.add(new MTSM(
//                pdu.getSourceId(),
//                pdu.getDestinationId(),
//                PREPAID_ACCOUNT_MANAGER.purchaseDataBundle(
//                    txn, Boolean.FALSE, Boolean.FALSE
//                )));
//
//        /* Remove User Session. */
//        USER_SESSIONS.remove(pdu.getSourceId());
//
//        return result;
//    }
//
////    public static void main(String[] args) {
////        DataBundlePurchaseServiceCommandProcessor processor = new DataBundlePurchaseServiceCommandProcessor();
////        PduDto pdu = new PduDto();
////        pdu.setSourceId("263733661588");
////        pdu.setShortMessage("2");
////        UserSession session = new UserSession(PduType.SMS, ServiceCommand.DATA_BUNDLE_PURCHASE);
////        USER_SESSIONS.put(pdu.getSourceId(), session);
////        processor.process(pdu);
////    }
//}
