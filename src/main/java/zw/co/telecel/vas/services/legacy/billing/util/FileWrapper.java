//package zw.co.telecel.vas.services.legacy.billing.util;
//
//import com.google.gson.Gson;
//import zw.co.telecel.vas.services.legacy.impl.util.ConfigDTO;
//import zw.co.telecel.vas.services.legacy.impl.util.LanguageDTO;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
///**
// * @author david@tekeshe.com
//// */
////public class FileWrapper {
////
////    public static String json(String filename ) {
////        try {
////            BufferedReader in = new BufferedReader( new FileReader( filename ) );
////            StringBuilder s = new StringBuilder();
////            String line = null;
////            while ( (line = in.readLine()) != null) {
////                s.append(line);
////            }
////
////            ConfigDTO config = new Gson().fromJson(s.toString(), ConfigDTO.class);
////            for ( String key : config.getBalances().keySet() ) {
////                LanguageDTO item = config.getBalances().get(key);
////                System.out.println(item.getIndex() + " - " + item.getNormalizedValue() );
////
////            }
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////}
