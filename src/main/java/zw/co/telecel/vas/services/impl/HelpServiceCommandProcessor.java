package zw.co.telecel.vas.services.impl;//package com.ebridgevas.services.impl;
//
//import PduDto;
//import MTSM;
//import com.ebridgevas.model.Message;
//import ServiceCommandProcessor;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * david@ebridgevas.com
// *
// */
//public class HelpServiceCommandProcessor implements ServiceCommandProcessor {
//
//    /* TODO Add context sensitive help. */
//    @Override
//    public List<MTSM> process(PduDto pdu) {
//        ArrayList<MTSM> result = new ArrayList<MTSM>();
//        result.add(new MTSM(pdu.getSourceId(), pdu.getDestinationId(), Message.HELP));
//        result.add(new MTSM(pdu.getSourceId(), pdu.getDestinationId(), Message.HELP2));
//        return result;
//    }
//}
