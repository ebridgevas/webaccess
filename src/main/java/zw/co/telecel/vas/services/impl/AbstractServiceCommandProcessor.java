package zw.co.telecel.vas.services.impl;//package com.ebridgevas.services.impl;
//
//import com.ebridge.commons.domain.TxnDto;
//import com.ebridge.commons.dto.PduDto;
//import com.ebridgevas.model.PduType;
//import UserSession;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.ebridgevas.model.PduType.*;
//
///**
// * david@ebridgevas.com
// *
// */
//public class AbstractServiceCommandProcessor {
//
//    public static final Long USSD_TIMEOUT = new Long(60000);
//    private static final Map<String, String> BUNDLE_TYPES;
//
//    static {
//        BUNDLE_TYPES = new HashMap<String, String>();
//        BUNDLE_TYPES.put("1","6 MB");
//        BUNDLE_TYPES.put("2","10 MB");
//        BUNDLE_TYPES.put("3","80 MB");
//        BUNDLE_TYPES.put("4","150 MB");
//        BUNDLE_TYPES.put("5","320 MB");
//        BUNDLE_TYPES.put("6","800 MB");
//        BUNDLE_TYPES.put("7","2 GB");
//        BUNDLE_TYPES.put("8","4 GB");
//    }
//
//    public static String getUssdMessagePrefix(Boolean terminateSession, Integer sessionId){
//        return ( terminateSession ? "81" : "72") + " " + sessionId + ( terminateSession ? "" : " " + USSD_TIMEOUT ) + " 0 ";
//    }
//
//    public Boolean isInitialDial( String payload ) {
//        return payload.split(" ").length < 7;
//    }
//
//    protected Integer getSessionId(PduDto pdu, PduType pduType) {
//        return pduType == PduType.USSD  ?
//                new Integer( pdu.getShortMessage().split(" ")[1] ) : new Integer(1);
//    }
//
//    protected static String getContent( String payload ) {
//        String[] tokens = payload.split(" ");
//        return tokens.length > 6 ? str(Arrays.copyOfRange(tokens, 7, tokens.length)) : "";
//    }
//
//    /**
//     * Determine if PduType is USSD or SMS.
//     *
//     * @param payload
//     * @return PduType
//     */
//    protected static PduType getPduTypeFrom(String payload) {
//        return payload.split(" ").length > 5 ? USSD : SMS;
//    }
//
//    /**
//     * String array to string convertor
//     * @param a
//     * @return
//     */
//    protected static String str(String[] a) {
//        StringBuilder sb = new StringBuilder();
//        for (String s : a) {
//            sb.append(s + " ");
//        }
//        return sb.toString().trim();
//    }
//
//    protected Boolean isTerminating(String payload){
//        return payload.startsWith("81");
//    }
//
//    protected String getDataBundlePrices() {
//        return  "Select bundle 1 to 8\n" +
//                "1= 65c for 6MB\n" +
//                "2= $1  for 10MB\n" +
//                "3= $3  for 80MB\n" +
//                "4= $5  for 150MB\n" +
//                "5= $10 for 320MB\n" +
//                "6= $20 for 800MB\n" +
//                "7= $45 for 2GB\n" +
//                "8= $75 for 4GB\n";
//    }
//
//    protected String getBundleSizeFor(String bundleType) {
//        return BUNDLE_TYPES.get(bundleType);
//    }
//
//    /* Create TxnDto from PduDto. */
//    // TODO Move to TxnDtoFactory - Factory Pattern
//    protected TxnDto createTxnDto(PduDto pdu) {
//        return new TxnDto(
//                    pdu.getUuid(),
//                    pdu.getChannel(),
//                    pdu.getSourceId(),
//                    pdu.getDestinationId());
//    }
//
//    /**
//     * Detertime if first service command from subscriber.
//     *
//     * @param userSession
//     * @return
//     */
//    protected Boolean isInitialDial(UserSession userSession) {
//        return userSession == null;
//    }
//}
