package com.example.comandaxpress.Util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static String encodeImageViewToBase64(ImageView imageView) {
        // Obtener el bitmap del ImageView
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        // Convertir bitmap a ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // Puedes elegir JPEG o PNG dependiendo de tu necesidad
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // Convertir byteArray a base64 String
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
