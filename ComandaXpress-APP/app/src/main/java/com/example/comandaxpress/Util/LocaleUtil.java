package com.example.comandaxpress.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleUtil {
    private static final String PREFS_NAME = "locale_prefs";
    private static final String KEY_LANGUAGE = "language";

    public static void setLocale(Context context, String language) {
        persistLanguage(context, language);

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static String getCurrentLocale(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = config.getLocales().get(0);
        } else {
            locale = config.locale;
        }
        return locale.getLanguage();
    }

    public static void persistLanguage(Context context, String language) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LANGUAGE, language);
        editor.apply();
    }

    public static void loadLocale(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String language = prefs.getString(KEY_LANGUAGE, "es");
        setLocale(context, language);
    }
}
