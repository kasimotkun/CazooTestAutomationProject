package main.java.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptionAndDecryption {

    public static String decrypt(String decryptData){

        byte[] decodeBytes = Base64.getDecoder().decode(decryptData.getBytes(StandardCharsets.UTF_8));

        String result = new String(decodeBytes);

        return result;
    }

//     public static void encrypt(String encryptData){
//
//        byte[] decodeBytes = Base64.getEncoder().encode(encryptData.getBytes(StandardCharsets.UTF_8));
//
//        System.out.println("EncodedBytes ----> " + new String(decodeBytes));
//
//    }
}
