package Utilities;

import org.apache.hc.client5.http.utils.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;


import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Base64Md5Encrypt {

    private static byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B,
            0x0D, 0x11 };

    public String EncryptText(String RawText)
    {
        String EncText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "TMA";
        byte[] toEncryptArray = null;

        try
        {

            toEncryptArray =  RawText.getBytes("UTF-8");
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if(temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++)
                {
                    keyArray[i] =  temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] encrypted = c.doFinal(toEncryptArray);
            EncText = Base64.encodeBase64String(encrypted);

        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx)
        {

            throw  new RuntimeException(NoEx);
        }

        return EncText;
    }

    public String DecryptText(String EncText)
    {

        String RawText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "TMA";
        byte[] toEncryptArray = null;

        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));

            if(temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++)
                {
                    keyArray[i] =  temporaryKey[index];
                }
            }

            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] decrypted = c.doFinal(Base64.decodeBase64(EncText));

            RawText = new String(decrypted, "UTF-8");
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx)
        {
            throw  new RuntimeException(NoEx);
        }

        return RawText;

    }
    public String DecryptTextWithoutKey(String EncText){
        byte[] valueDecoded = Base64.decodeBase64(EncText);
        String decryptString = new String(valueDecoded);
        return  decryptString;
    }



    /**
     * @param args
     */
    public static void main(String[] args) {
        String a  = "dGVzdFBhc3dvcmQxMjMh";
        Base64Md5Encrypt md5 = new Base64Md5Encrypt();
//        String mahoa = md5.EncryptText(a);
//        System.out.println(mahoa);
//        System.out.println(md5.DecryptText(mahoa));
        String decryptString = md5.DecryptTextWithoutKey(a);
        String encryptWithKey = md5.EncryptText(decryptString);
        System.out.println("them key");
        System.out.println(md5.DecryptText(encryptWithKey));

    }

}
