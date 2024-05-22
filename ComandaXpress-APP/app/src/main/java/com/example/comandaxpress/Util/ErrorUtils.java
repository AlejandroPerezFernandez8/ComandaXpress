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

import com.example.comandaxpress.R;

public class ErrorUtils {

    public static void mostrarMensaje(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, null);


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

    // Método sobrecargado para aceptar un recurso de string
    public static void mostrarMensaje(Activity activity, int messageResId) {
        String message = activity.getString(messageResId);
        mostrarMensaje(activity, message);
    }
}