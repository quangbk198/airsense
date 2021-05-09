package com.example.doan1.Controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import com.example.doan1.SplashActivity;

import java.util.Locale;

public class Libs {
    private static Locale myLocale;

    //Lưu ngôn ngữ đã cài đặt
    public static void saveLocale(String lang, Activity activity) {
        String langPref = "Language";
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putString(langPref, lang);
        editor.putBoolean("checked", true);
        editor.commit();
    }
    
    //Load lại ngôn ngữ và lưu thay đổi chúng
    public static void loadLocale(Activity activity) {
        String langPref = "Language";
        String language = SplashActivity.prefs.getString(langPref, "");
        SplashActivity.temp = language;
        changeLang(language, activity);
    }

    // method phục vụ cho việc thay đổi ngôn ngữ.
    public static void changeLang(String lang, Activity activity) {
        myLocale = new Locale(lang);
        saveLocale(lang, activity);

        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());
    }
}