package zw.co.telecel.vas.util.legacy.smpp;//package com.ebridgevas.util.legacy.smpp;
//
//import com.ebridge.commons.model.ServiceCommand;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * david@ebridgevas.com
// *
// */
//public class SmppPduParserUtils {
//
//    public static final Map<String, ServiceCommand> SERVICE_COMMAND_MENU;
//
//    static {
//        SERVICE_COMMAND_MENU = new HashMap<String, ServiceCommand>();
//        SERVICE_COMMAND_MENU.put("1", ServiceCommand.SUBSCRIPTION_REQUEST);
//        SERVICE_COMMAND_MENU.put("2", ServiceCommand.BALANCE_ENQUIRY);
//        SERVICE_COMMAND_MENU.put("3", ServiceCommand.CANCEL_SUBSCRIPTION_REQUEST);
//    }
//
//    public static Boolean isInitialDial( String payload ) {
//        return payload.split(" ").length < 7;
//    }
//
//}
