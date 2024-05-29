package com.example.comandaxpress.Pantallas.PantallasSecundarias;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.comandaxpress.R;

/**
 * Pantalla para incluir un dialogo de carga
 * */
public class DialogoDeCarga {
    private Activity activity;
    private AlertDialog dialog;

    public DialogoDeCarga(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.pantalla_carga, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
