//package zw.co.telecel.vas.services.util;
//
//import com.ebridgevas.model.DataBundle;
//import com.google.gson.Gson;
//import zw.co.telecel.dto.ConfigDTO;
//import zw.co.telecel.dto.DataBundleDTO;
//import zw.co.telecel.dto.LanguageDTO;
//
//import java.io.*;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author david@tekeshe.com
// */
//public class Test {
//
//    public static void main(String[] args) {
//
//        Map<String, List<String>> cosBalances = new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("Core");
//        list.add("Gprs_bundle");
//        list.add("Data_Bonus");
//        cosBalances.put("TEL_COS", list);
//
//        list = new ArrayList<>();
//        list.add("Core");
//        list.add("Gprs_modem");
//        list.add("Gprs_bonus");
//        list.add("Gprs_sms");
//        cosBalances.put("GPRS_COS", list);
//
//        Map<String, LanguageDTO> balanceNames = new HashMap<>();
//        balanceNames.put("Core", new LanguageDTO("Core", "Main Account", "TEL_COS", 1));
//        balanceNames.put("Gprs_bundle", new LanguageDTO("Gprs_bundle", "Data Account","TEL_COS", 2));
//        balanceNames.put("Data_Bonus", new LanguageDTO("Data_Bonus", "Data Bonus", "TEL_COS", 3));
//        balanceNames.put("Gprs_modem", new LanguageDTO("Gprs_modem", "GPRS Account", "GPRS_COS", 4));
//        balanceNames.put("Gprs_bonus", new LanguageDTO("Gprs_bonus", "GPRS Bonus", "GPRS_COS", 5));
//        balanceNames.put("Gprs_sms", new LanguageDTO("Gprs_sms", "SMS Bonus", "GPRS_COS", 6));
//        balanceNames.put("PostpaidCore", new LanguageDTO("PostpaidCore", "Data Account", "POS_COS", 7));
//
//        List<String> postpaidCos = new ArrayList<>();
//        postpaidCos.add("Staff Spouse");
//        postpaidCos.add("Staff");
//        postpaidCos.add("POST_COS");
//        postpaidCos.add("VIP");
//
//            /*
//            1.       Modem lines now in a separate COS; GPRS_COS
//            2.       Subscribers in GPRS_COS, debit Core; credit Gprs_modem
//            3.       Subscribers in TEL_COS, debit Core; credit Gprs_bundle
//            4.       Test wallets: Gprs_modem; Gprs_bonus; Gprs_sms; Gprs_bundle
//            */
//
//        HashMap<String, DataBundleDTO> DATA_BUNDLES = new HashMap<>();
//        DATA_BUNDLES.put("1", new DataBundleDTO(   "1", "1= 50c    for 4.8MB", new BigDecimal("4.80"), new BigDecimal("0.108"), new BigDecimal("0.50"), new BigDecimal("0.576"), new BigDecimal("0.12"), 30));
//        DATA_BUNDLES.put("2", new DataBundleDTO(   "2", "2= $1     for 9.5MB", new BigDecimal("9.50"), new BigDecimal("0.10"), new BigDecimal("1.00"), new BigDecimal("1.14"), new BigDecimal("0.12"), 30));
//        DATA_BUNDLES.put("3", new DataBundleDTO(   "3", "3= $3.00  for 76MB", new BigDecimal("76.00"), new BigDecimal("0.08"), new BigDecimal("3.00"), new BigDecimal("9.12"),new BigDecimal("0.12"), 30));
//        DATA_BUNDLES.put("4", new DataBundleDTO(   "4", "5= $5.00  for 142.5MB", new BigDecimal("142.50"), new BigDecimal("0.08"), new BigDecimal("5.00"), new BigDecimal("17.10"), new BigDecimal("0.12"), 60));
//        DATA_BUNDLES.put("5", new DataBundleDTO(   "5", "5= $10.00 for 304MB", new BigDecimal("304.00"), new BigDecimal("0.06"), new BigDecimal("10.00"), new BigDecimal("36.48"), new BigDecimal("0.12"), 60));
//        DATA_BUNDLES.put("6", new DataBundleDTO(   "6", "6= $20.00 for 800MB", new BigDecimal("800.00"), new BigDecimal("0.05"), new BigDecimal("20.00"), new BigDecimal("96.00"), new BigDecimal("0.12"), 90));
//        DATA_BUNDLES.put("7", new DataBundleDTO(   "7", "8= $45.00 for 2000MB", new BigDecimal("2000.00"), new BigDecimal("0.05"), new BigDecimal("45.00"), new BigDecimal("240.00"), new BigDecimal("0.12"), 90));
//        DATA_BUNDLES.put("8", new DataBundleDTO(   "8", "8= $75.00 for 4000MB", new BigDecimal("4000.00"), new BigDecimal("0.04"), new BigDecimal("75.00"), new BigDecimal("480.00"), new BigDecimal("0.12"), 120));
//
//
//        try {
//            FileWriter out = new FileWriter(new File("/prod/ebridge/conf/web.json"));
//            ConfigDTO config = new ConfigDTO();
//            config.setBalances( balanceNames );
//            config.setCosBalances(cosBalances);
//            config.setPostpaidCos(postpaidCos);
//            config.setDataBundles(DATA_BUNDLES);
//            out.write( new Gson().toJson(config) );
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        try {
////            BufferedReader in = new BufferedReader(new FileReader("/prod/ebridge/conf/web.json"));
////            StringBuilder s = new StringBuilder();
////            String line = null;
////            while ( (line = in.readLine()) != null) {
////                s.append(line);
////            }
////
////            Map<String, List<LanguageDTO>> map = new HashMap<>();
////            map.put("OTHER", new ArrayList<LanguageDTO>());
////            map.put("GPRS_COS", new ArrayList<LanguageDTO>());
////            ConfigDTO config = new Gson().fromJson(in, ConfigDTO.class);
////            for ( String key : config.getBalances().keySet() ) {
////                LanguageDTO item = config.getBalances().get(key);
////                System.out.println(item.getIndex() + " - " + item.getNormalizedValue() + item.getClassOfService());
////                if ( "*".equals(item.getClassOfService())) {
////                    other = map.get("OTHER");
////                }
////            }
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//}
