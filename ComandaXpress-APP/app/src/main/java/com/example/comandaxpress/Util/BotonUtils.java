package com.example.comandaxpress.Util;

import java.util.Timer;
import java.util.TimerTask;
import android.widget.Button;

public class BotonUtils {

    public static void deshabilitarTemporalmente(final Button button) {
        button.setEnabled(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                button.post(new Runnable() {
                    @Override
                    public void run() {button.setEnabled(true);}
                });}}, 800);
    }
}
