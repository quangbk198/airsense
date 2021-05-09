package com.example.doan1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.doan1.Controller.Libs;
import com.example.doan1.Model.DataMqtt;

public class SplashActivity extends AppCompatActivity {
    public static SharedPreferences prefs;
    public static String temp;  //Biến dùng để kiểm tra, thay đổi ngôn ngữ cho title của tab layout trong danh sachs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);

        Libs.loadLocale(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if(!isConnected()){
//            showDialog();
//        }
//        else {
//            startService(new Intent(getBaseContext(), DataMqtt.class));
//        }
        final Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();



        if(!isConnected()){
            showDialog();
        }
        else {
            startService(new Intent(getBaseContext(), DataMqtt.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), DataMqtt.class));
//        finish();
//        System.exit(0);
    }

    public boolean isConnected() {
        boolean wifi = false;
        boolean mobileData = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI")) {
                if (info.isConnected()) {
                    wifi = true;
                }
            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (info.isConnected()) {
                    mobileData = true;
                }
            }
        }
        return wifi | mobileData;
    }

    public void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_error_network);
        Button dialogButton = dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                finish();
            }
        });
        dialog.show();
    }
}