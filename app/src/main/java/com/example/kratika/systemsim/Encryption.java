package com.example.kratika.systemsim;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;


public class Encryption
{

    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            Log.d("ivarr", iv.getIV().toString());
            Log.d("ivarrkey", Integer.toString(key.getBytes().length));
            Log.d("ivarr", Integer.toString(iv.getIV().length));

            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Log.d("encrypted", "instantiating");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            Cipher cipher = Cipher.getInstance("AES");
            Log.d("encrypted", "init");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            Log.d("encrypted", "initiated");
            byte[] encrypted = cipher.doFinal(value.getBytes());

            Log.d("encrypted", new String(encrypted));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
//            System.out.println("encrypted string: "
//                    + Base64.encodeToString(encrypted, Base64.DEFAULT));

        } catch (InvalidKeyException ex) {
            Log.d("Invalid Key", "Sale hai");
            Log.d("Invalid Key", ex.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
