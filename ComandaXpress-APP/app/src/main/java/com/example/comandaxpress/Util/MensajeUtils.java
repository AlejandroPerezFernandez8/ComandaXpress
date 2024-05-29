package com.example.comandaxpress.Util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.comandaxpress.R;

public class MensajeUtils {

    /**
     * Clase para sacar mensajes personalidos tanto de error como informativos
     * */

    public static void mostrarError(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, null);
        ImageView imagen = layout.findViewById(R.id.toast_icon);
        imagen.setImageResource(R.drawable.error);
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        final Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        layout.startAnimation(animation);

        toast.show();
    }

    public static void mostrarErrorLargo(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, null);
        ImageView imagen = layout.findViewById(R.id.toast_icon);
        imagen.setImageResource(R.drawable.error);
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        final Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG + 1);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        layout.startAnimation(animation);

        toast.show();
    }

    public static void mostrarError(Activity activity, int messageResId) {
        String message = activity.getString(messageResId);
        mostrarError(activity, message);
    }

    public static void mostrarMensaje(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, null);
        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        ImageView imagen = layout.findViewById(R.id.toast_icon);
        imagen.setImageResource(R.drawable.ok);

        final Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
        layout.startAnimation(animation);

        toast.show();
    }

    public static void mostrarMensaje(Activity activity, int messageResId) {
        String message = activity.getString(messageResId);
        mostrarMensaje(activity, message); // Cambiado para llamar al método correcto
    }
}
