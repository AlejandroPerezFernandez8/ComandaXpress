package com.example.comandaxpress.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.comandaxpress.API.Clases.Usuario;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

    /**
     * Clase para la encriptacion y desencriptación de strings con AES
     * las claves son generadas a partir de una clave corta con el metodo KeyAESderivada
     * */
    private static Gson gson = new Gson();
    public static String transformarUsuarioToJson(Usuario usuario){
        return gson.toJson(usuario);
    }
    public static Usuario transformarJsonToUsuaro(String jsonUsuario){
        return gson.fromJson(jsonUsuario, Usuario.class);
    }
    public static String encriptar(String data, String keyString) throws Exception {
        SecretKeySpec key = KeyAesDerivada(keyString);;
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    public static String desencriptar(String data, String keyString) throws Exception {
        SecretKeySpec key = KeyAesDerivada(keyString);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
        return new String(decrypted);
    }

    public static SecretKeySpec KeyAesDerivada(String password) throws Exception {
        byte[] salt = new byte[]{(byte)0xa7, (byte)0x21, (byte)0x2b, (byte)0xc2, (byte)0x56, (byte)0xee, (byte)0x77, (byte)0x99};
        int iterations = 65536;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey secretKey = factory.generateSecret(spec);
        return new SecretKeySpec(secretKey.getEncoded(), "AES");
    }

    public static Bitmap decodeBase64ToBitmap(String base64String) {
        // Decodifica la cadena Base64 a un array de bytes
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        // Convierte el array de bytes a un Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        return bitmap;
    }




}
