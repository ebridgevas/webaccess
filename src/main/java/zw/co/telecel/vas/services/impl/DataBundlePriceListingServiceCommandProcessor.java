package zw.co.telecel.vas.services.impl;//package com.ebridgevas.services.impl;
//
//import com.ebridge.commons.dto.PduDto;
//import MTSM;
//import com.ebridgevas.model.PduType;
//import ServiceCommand;
//import UserSession;
//import ServiceCommandProcessor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.ebridgevas.services.ServerPDUEventListenerImpl.USER_SESSIONS;
//
///**
// * Created with IntelliJ IDEA.
// * User: David
// * Date: 2/24/13
// * Time: 10:35 AM
// * To change this template use File | Settings | File Templates.
// */
//public class DataBundlePriceListingServiceCommandProcessor
//        extends AbstractServiceCommandProcessor
//        implements ServiceCommandProcessor {
//
//    @Override
//    public List<MTSM> process(PduDto pdu) {
//
//        USER_SESSIONS.put(pdu.getSourceId(), new UserSession(PduType.SMS, ServiceCommand.DATA_BUNDLE_PURCHASE));
//
//        List<MTSM> result = new ArrayList<MTSM>();
//        result.add(new MTSM(
//                pdu.getSourceId(),
//                pdu.getDestinationId(),
//                getDataBundlePrices()));
//
//        return result;
//    }
//}
