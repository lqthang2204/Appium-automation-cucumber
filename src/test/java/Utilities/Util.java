package Utilities;

import org.apache.hc.client5.http.utils.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static boolean isNumber(String value){
       try {
           int result = Integer.parseInt(value);
           return true;
       }catch (Exception e){
           return false;
       }

    }

    public static boolean checkLength(String[] arr){
        try {
           String value =  arr[2];
           return true;
        }catch (Exception e){
            return false;
        }

    }
    public static String DecryptTextWithoutKey(String EncText){
        byte[] valueDecoded = Base64.decodeBase64(EncText);
        String decryptString = new String(valueDecoded);
        return  decryptString;
    }
    public static long convertDobToMiliseconds(Date dob) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        long miliSeconds = dob.getTime();
        return  miliSeconds;
    }
    public static String convertMilisecondsToDob(long miliseconds) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(miliseconds);
        return  sdf.format(date);
    }
    public static String removeBlank(String text){
        String result ="";
        result = text.replaceAll("\\s+", " ");
        return  result;
    }
}
