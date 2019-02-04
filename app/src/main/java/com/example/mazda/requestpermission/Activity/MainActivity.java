package com.example.mazda.requestpermission.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mazda.requestpermission.Constants;
import com.example.mazda.requestpermission.MethodCollection;
import com.example.mazda.requestpermission.Preferences;
import com.example.mazda.requestpermission.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroupTrip;
    RadioButton radioButtonOneTrip, radioButtonRoundTrip;
    TextView totalPassenger;
    public static FloatingActionButton btnGetRoute;
    public static MaterialButton buttonTanggalBerangkat, buttonTanggalPulang, txtTerminalKeberangkatan, txtTerminalTujuan;
    private int limit = 1;
    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setRoundTrip();
    }

    private void init() {
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        radioGroupTrip = findViewById(R.id.radio_grup_trip);
        radioButtonOneTrip = findViewById(R.id.rb_one_trip);
        radioButtonRoundTrip = findViewById(R.id.rb_round_trip);
        totalPassenger = findViewById(R.id.jumlah_penumpang);
        buttonTanggalBerangkat = findViewById(R.id.tv_tanggal_berangkat);
        buttonTanggalPulang = findViewById(R.id.tv_tanggal_pulang);
        txtTerminalKeberangkatan = findViewById(R.id.tv_terminal_keberangkatan);
        txtTerminalTujuan = findViewById(R.id.tv_terminal_tujuan);
        btnGetRoute = findViewById(R.id.button_cari_jadwal);

        btnGetRoute.setEnabled(false);
        btnGetRoute.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        totalPassenger.setText(String.valueOf(limit));
        txtTerminalTujuan.setEnabled(false);
        buttonTanggalBerangkat.setEnabled(false);
        buttonTanggalPulang.setEnabled(false);

        setSupportActionBar(bottomAppBar);
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<limit; i++){

                }
            }
        });

    }

    private void setRoundTrip() {
        final LinearLayout tanggalPulang = findViewById(R.id.tanggal_pulang);
        radioButtonOneTrip.setChecked(true);
        radioButtonRoundTrip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tanggalPulang.setVisibility(View.VISIBLE);
                    tanggalPulang.setAlpha(0.0f);
                    tanggalPulang
                            .animate()
                            .setDuration(1000)
                            .alpha(1.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    tanggalPulang.animate().setListener(null);
                                }
                            });
                    radioButtonOneTrip.setChecked(false);
                } else {
                    tanggalPulang
                            .animate()
                            .setDuration(1000)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    tanggalPulang.setVisibility(View.GONE);
                                    tanggalPulang.animate().setListener(null);
                                }
                            });
                    radioButtonRoundTrip.setChecked(false);
                }
            }
        });
    }

    public void showDialogListTerminalKeberangkatan(View view) {
        MethodCollection.HttpRequestGet(this, Constants.URL_GET_TERMINAL_KEBERANGKATAN, Constants.GET_TERMINAL_ORIGIN);
    }

    public void showDialogListTerminalTujuan(View view) {
        String URL = Constants.URL_GET_TERMINAL_TUJUAN + "org=" + MethodCollection.orgCode;
        MethodCollection.HttpRequestGet(this, URL, Constants.GET_TERMINAL_DESTINATION);
    }

    public void showDialogDateBerangkat(View view) {
        MethodCollection.showDatePickerDialog(this, "TanggalBerangkat");
    }

    public void showDialogDatePulang(View view) {
        MethodCollection.showDatePickerDialog(this, "TanggalPulang");
    }

    public void addPassenger(View view) {
        if (limit < 4) {
            limit++;
            totalPassenger.setText(String.valueOf(limit));
        }
    }

    public void subPassenger(View view) {
        if (limit > 1) {
            limit--;
            Log.d("subPassenger: ", String.valueOf(limit));
            totalPassenger.setText(String.valueOf(limit));
        }
    }

    public void getRoute(View view) throws ParseException {
        String orgCode = MethodCollection.orgCode;
        String desCode = MethodCollection.desCode;
        String tanggalDipilih = buttonTanggalBerangkat.getText().toString().trim();
        DateFormat dateFormat = new SimpleDateFormat("EEE, d-MMM-yyyy");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String departDate = formatter.format(dateFormat.parse(tanggalDipilih));
        String URL = Constants.URL_GET_ROUTE + "org=" + orgCode + "&des=" + desCode + "&dep_date=" + departDate;

        Log.d("getRoute: ", URL);
        MethodCollection.HttpRequestGet(this, URL, "GetRoute");
    }

    public void switchRoute(View view) {
        String terminalKeberangkatan = txtTerminalKeberangkatan.getText().toString().trim();
        String terminalTujuan = txtTerminalTujuan.getText().toString().trim();
        txtTerminalKeberangkatan.setText(terminalTujuan);
        txtTerminalTujuan.setText(terminalKeberangkatan);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account_menu:
                Toast.makeText(MainActivity.this, "Account menu", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_logout:
                showDialogConfirmation();
                break;
            case R.id.menu_ticket:
                Toast.makeText(MainActivity.this, "Ticket menu", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notifikasi").setMessage("Apakah anda yakin akan keluar?").setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Preferences.getPreference(getApplicationContext()).logout();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog showDialog = builder.create();
        showDialog.show();
    }
}

