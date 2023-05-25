package Utilities;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import org.apache.hc.client5.http.utils.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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
    public static String getRandomEmail(String email){
        String emailExpected="";
        String emailPattern = "random[a-z0-9._-]{0,15}@";
        Pattern pattern  = Pattern.compile(emailPattern);
        boolean found = pattern.matcher(email).find();
        if(found){
            Faker faker = new Faker();
            Name name = faker.name();
            Number number = faker.number();
            emailExpected = email.replace("random",name.firstName()+number.numberBetween(1,999));
        }else{
            emailExpected = email;
        }
        return emailExpected;
    }
}
