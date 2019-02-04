package com.example.mazda.requestpermission.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mazda.requestpermission.R;

import androidx.appcompat.app.AppCompatActivity;


public class ActivityPilihLayanan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_layanan);
    }

    public void moveToLogin(View view) {
        startActivity(new Intent(ActivityPilihLayanan.this, LoginActivity.class));
    }

    public void moveToAkap(View view) {
        startActivity(new Intent(ActivityPilihLayanan.this, LayananAntarKotaActivity.class));
    }
}
