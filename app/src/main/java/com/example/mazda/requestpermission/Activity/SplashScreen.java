package com.example.mazda.requestpermission.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mazda.requestpermission.Constants;
import com.example.mazda.requestpermission.RequestBodyHelper;
import com.example.mazda.requestpermission.MethodCollection;
import com.example.mazda.requestpermission.Preferences;
import com.example.mazda.requestpermission.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FoldingCube;

import org.json.JSONException;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        checkConnection();
    }

    void init() {
        progressBar = findViewById(R.id.progress);
        Sprite folding = new FoldingCube();
        progressBar.setIndeterminateDrawable(folding);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void checkConnection() {
        if (MethodCollection.isConnected(this)) {
//            Connection available, do action here
            progressBar.setVisibility(View.VISIBLE);
            try {
                RequestBodyHelper.loginMobile(Constants.LOGIN_MOBILE_REQUEST, SplashScreen.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (Preferences.getPreference(this).sessioIsActive()) {
                new Handler().postDelayed(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finishAffinity();
                    }
                }, 3000);
            } else {
                //  Auto intent to ActivityPilihLayanan
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, ActivityPilihLayanan.class));
                        finish();
                    }
                }, 3000);
            }
        } else {
//            Connection unavailable, do action
            progressBar.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Notifikasi")
                    .setMessage(R.string.is_not_connected)
                    .setPositiveButton("dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setCancelable(false);
            AlertDialog koneksiGagal = builder.create();
            koneksiGagal.show();
        }
    }
}