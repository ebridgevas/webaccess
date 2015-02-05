package zw.co.telecel.vas.services.impl;//package com.ebridgevas.services.impl;
//
//import com.ebridge.commons.dto.PduDto;
//import MTSM;
//import ServiceCommandProcessor;
//import com.ebridgevas.util.MobileNumberFormatter;
//
//import java.util.List;
//
//import static com.ebridgevas.services.ServerPDUEventListenerImpl.PREPAID_ACCOUNT_MANAGER;
//
///**
// * david@ebridgegevas.com
// *
// */
//public class VoucherRechargeServiceCommandProcessor implements ServiceCommandProcessor {
//
//    @Override
//    public List<MTSM> process(PduDto pdu) {
//        String[] tokens = pdu.getShortMessage().split("#");
//        return PREPAID_ACCOUNT_MANAGER.voucherRecharge(
//                pdu,
//                MobileNumberFormatter.format(tokens[1]),
//                tokens[0]);
//    }
//}
