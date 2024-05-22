package com.example.comandaxpress.Util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comandaxpress.R;

public class CustomToast {

    public static void showToast(Activity activity, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_personalizado, null);

        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        final Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        // Añadir animación al Toast
        TranslateAnimation animation = new TranslateAnimation(0, 0, -layout.getHeight(), 0);
        animation.setDuration(500);
        layout.startAnimation(animation);

        toast.show();
    }
}
