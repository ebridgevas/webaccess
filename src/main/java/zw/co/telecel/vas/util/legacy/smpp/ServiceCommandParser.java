package zw.co.telecel.vas.util.legacy.smpp;//package com.ebridgevas.util.legacy.smpp;
//
//
//import ServiceCommand;
//import ServiceCommandRequest;
//import UserSession;
//
//import java.util.Map;
//
///**
// * david@ebridgevas.com
// *
// */
//public class ServiceCommandParser {
//
//    private Map<String, UserSession> userSessions;
//
//    public ServiceCommandParser(Map<String, UserSession> userSessions) {
//        this.userSessions = userSessions;
//    }
//
//    public ServiceCommand parse(ServiceCommandRequest request) {
//
//        /* Is Initial dial? */
//        if ( SmppPduParserUtils.isInitialDial(request.getServiceCommand()) ) {
//            return ServiceCommand.MENU_LISTING;
//        }
//
////        UserSession userSession = userSessions.get(txn.getSourceId());
//
////        if (userSession == null) {
//            /* User is selecting a menu. */
////            return processRootMenu(txn, ussdAnswer);
////        }
//
////        processUSSDAnswer(txn, ussdAnswer, session);
//
//        return null;
//    }
//}
