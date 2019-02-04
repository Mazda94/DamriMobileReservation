package com.example.mazda.requestpermission.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mazda.requestpermission.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class BookingActivity extends AppCompatActivity {

    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bottomAppBar = findViewById(R.id.bottom_app_bar_booking);
        Intent i = getIntent();
        String desCode = i.getStringExtra("desCode");
        Log.d("onCreate: ", desCode);
    }
}