package com.example.mazda.requestpermission;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mazda.requestpermission.Activity.BookingActivity;
import com.example.mazda.requestpermission.Activity.LoginActivity;
import com.example.mazda.requestpermission.Activity.MainActivity;
import com.example.mazda.requestpermission.Model.Rute;
import com.example.mazda.requestpermission.Model.Terminal;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_APP_EMAIL;

public class MethodCollection extends AppCompatActivity {
    public static String orgCode;
    public static String desCode;
    static AlertDialog dialog;

    /*----------------------------------------------------------------------------------------------------------------------*/
    //    get device info and store into preferences
    public static void getDeviceInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        String imei = tm.getDeviceId();
        String subcId = tm.getDeviceId();
        String serialNumber = tm.getSimSerialNumber();
        String networkCountryIso = tm.getNetworkCountryIso();
        String simCountryIso = tm.getSimCountryIso();
        String softwareVersion = tm.getDeviceSoftwareVersion();
        String voiceMailNumber = tm.getVoiceMailNumber();

//        getPreference(context).deviceInfo(
//                imei,
//                subcId,
//                serialNumber,
//                networkCountryIso,
//                simCountryIso,
//                softwareVersion,
//                voiceMailNumber
//        );
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    //    get connectivity info = availabe or unavailable
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = MethodCollection.getNetworkInfo(context);
        return (networkInfo != null && networkInfo.isConnected());
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Http request POST with volley
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void httpRequestPost(final Context context, final String URL, final JSONObject requestBody, final String mCaller, final int method) {
        showLoading(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        final String mRequestBody = requestBody.toString();
        StringRequest stringRequest = new StringRequest(method, URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            jsonParser(object, URL, requestBody, mCaller, context);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the reuest has either time out or there is no connection
                            error.printStackTrace();
                            Toast.makeText(context, "Connection time out, try again", Toast.LENGTH_SHORT).show();
                            hideLoading();
                        } else if (error instanceof AuthFailureError) {
                            // Error indicating that there was an Authentication Failure while performing the request
                            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            hideLoading();
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            Toast.makeText(context, "Server tidak merespon, coba lagi", Toast.LENGTH_SHORT).show();
                            hideLoading();
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            Toast.makeText(context, "Terjadi kesalahan pada jaringan", Toast.LENGTH_SHORT).show();
                            hideLoading();
                        } else if (error instanceof ParseError) {
                            // Indicates that the server response could not be parsed
                            Toast.makeText(context, "Gagal memproses data", Toast.LENGTH_SHORT).show();
                            hideLoading();
                        }
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                String caller = mCaller;
                Map<String, String> headerParams = new HashMap<>();
                headerParams.put("Content-Type", "application/json");
                switch (caller) {
                    case Constants.LOGIN_USER_REQUEST:
                    case Constants.RESET_PASSWORD_REQUEST:
                    case Constants.REGISTER_REQUEST:
                        headerParams.put("Authorization", "Bearer " + Preferences.getValidToken());
                        break;
                    case Constants.LOGIN_MOBILE_REQUEST:
                        break;
                    default:
                        break;
                }
                return headerParams;
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(5000, 3,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Http request GET with volley
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void HttpRequestGet(final Context context, final String URL, final String mCaller) {
        showLoading(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        final StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("onResponse: ", response);
                    JSONObject object = new JSONObject(response);
                    jsonParser(object, null, null, mCaller, context);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    error.printStackTrace();
                    Toast.makeText(context, "Permintaan gagal, periksai koneksi internet anda", Toast.LENGTH_SHORT).show();
                    hideLoading();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    hideLoading();
                } else if (error instanceof ServerError) {
                    Toast.makeText(context, "Terjadi kesalahan pada jaringan", Toast.LENGTH_SHORT).show();
                    hideLoading();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context, "Terjadi kesalahan pada jaringan", Toast.LENGTH_SHORT).show();
                    hideLoading();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context, "Gagal memproses data", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headerParams = new HashMap<>();
                headerParams.put("Authorization", "Bearer " + Preferences.getValidToken());
                return headerParams;
            }
        };
        request.setRetryPolicy(
                new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    // Parsing response result
    /*----------------------------------------------------------------------------------------------------------------------*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static void jsonParser(@Nullable JSONObject object, @Nullable String URL, @Nullable JSONObject requestBody, String mCaller, final Context context) throws JSONException {
        ArrayList<Terminal> terminals = new ArrayList<>();
        Log.d("jsonParser: ", String.valueOf(object));
        String message = object.getString("message");
        JSONObject data = null;
        final int code = object.getInt("code");

        if (object.getString("data") != "null") {
            data = object.getJSONObject("data");
        }

        switch (mCaller) {
            case Constants.GET_TERMINAL_ORIGIN:
            case Constants.GET_TERMINAL_DESTINATION:
                Iterator<?> keys = data.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    JSONArray terminal = data.getJSONArray(key);
                    for (int i = 0; i < terminal.length(); i++) {
                        JSONObject terminalData = terminal.getJSONObject(i);
                        if (mCaller == Constants.GET_TERMINAL_ORIGIN) {
                            int id = terminalData.getInt("id");
                            String terminalCode = terminalData.getString("terminal_code");
                            String terminalName = terminalData.getString("terminal_name");
                            String terminalCity = terminalData.getString("terminal_city");
                            String status = terminalData.getString("status");
                            String longitude = terminalData.getString("long");
                            String latitude = terminalData.getString("lat");

                            terminals.add(new Terminal(
                                    id, terminalCode, null, terminalName, terminalCity, status, longitude, latitude
                            ));
                        } else if (mCaller == Constants.GET_TERMINAL_DESTINATION) {
                            int id = terminalData.getInt("id");
                            String terminalCode = terminalData.getString("terminal_code");
                            String terminalName = terminalData.getString("terminal_name");
                            String terminalCity = terminalData.getString("terminal_city");
                            String status = terminalData.getString("status");

                            terminals.add(new Terminal(
                                    id, null, terminalCode, terminalName, terminalCity, status, null, null
                            ));
                        }
                    }
                }
                showDialogTerminalList(context, terminals, mCaller);
                break;

            case Constants.GET_ROUTE:
                ArrayList<Rute> rutes = new ArrayList<>();
                if (code == 0) {
                    String orgCode = data.getString("org_code");
                    String desCode = data.getString("des_code");
                    String orgName = data.getString("org_name");
                    String desName = data.getString("des_name");
                    String depDate = data.getString("dep_date");
                    String routeCode = data.getString("route_code");
                    String routeName = data.getString("route_name");
                    String routeInfo = data.getString("route_info");

                    JSONArray array = data.optJSONArray("bus");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject bus = array.getJSONObject(i);
                        String tipeBus = bus.getString("type");
                        String hargaTiket = bus.getString("price");
                        rutes.add(new Rute(orgCode, desCode, orgName, desName, tipeBus, hargaTiket, depDate, routeCode, routeName, routeInfo));
                        Log.d("Rute: ", rutes.toString());
                    }
                } else if (code == 108) {
                    RequestBodyHelper.loginMobile(Constants.LOGIN_MOBILE_REQUEST, context);
                    Preferences.getPreference(context).saveToken(
                            object.getJSONObject("data").getString("token"),
                            object.getJSONObject("data").getString("expired"));
                    httpRequestPost(context, URL, requestBody, Constants.LOGIN_USER_REQUEST, 1);
                    Toast.makeText(context, "Permintaan gagal, mohon coba lagi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                showDialogRoute(context, rutes);
                break;

            case Constants.LOGIN_MOBILE_REQUEST:
                Preferences.getPreference(context).saveToken(
                        object.getJSONObject("data").getString("token"),
                        object.getJSONObject("data").getString("expired"));
                break;

            case Constants.REGISTER_REQUEST:
                if (code == 0) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((Activity) (context)).finishAffinity();
                } else if (code == 108) {
                    RequestBodyHelper.loginMobile(Constants.LOGIN_MOBILE_REQUEST, context);
                    Preferences.getPreference(context).saveToken(
                            object.getJSONObject("data").getString("token"),
                            object.getJSONObject("data").getString("expired"));
                    httpRequestPost(context, URL, requestBody, Constants.LOGIN_USER_REQUEST, 1);
                    Toast.makeText(context, "Permintaan gagal, mohon coba lagi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                break;

            case Constants.LOGIN_USER_REQUEST:
                if (code == 0) {
                    Preferences.getPreference(context).saveUserDetail(
                            object.getJSONObject("data").getString("firstname"),
                            object.getJSONObject("data").getString("lastname"),
                            object.getJSONObject("data").getString("place_of_birth"),
                            object.getJSONObject("data").getString("date_of_birth"),
                            object.getJSONObject("data").getString("address"),
                            object.getJSONObject("data").getString("phone_number"),
                            object.getJSONObject("data").getString("email"),
                            object.getJSONObject("data").getString("activation_code"),
                            object.getJSONObject("data").getString("status"),
                            object.getJSONObject("data").getString("created_on"),
                            object.getJSONObject("data").getInt("is_activation"),
                            object.getJSONObject("data").getInt("active"),
                            object.getJSONObject("data").getInt("gender")
                    );
                    context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } else if (code == 108) {
                    RequestBodyHelper.loginMobile(Constants.LOGIN_MOBILE_REQUEST, context);
                    Preferences.getPreference(context).saveToken(
                            object.getJSONObject("data").getString("token"),
                            object.getJSONObject("data").getString("expired"));
                    httpRequestPost(context, URL, requestBody, Constants.LOGIN_USER_REQUEST, 1);
                    Toast.makeText(context, "Permintaan gagal, mohon coba lagi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                break;

            case "UpdateProfileActivity":

                break;

            case Constants.RESET_PASSWORD_REQUEST:
                if (code == 0) {
                    Snackbar.make(LoginActivity.snackbar, "Reset password telah dikirim ke email anda", Snackbar.LENGTH_LONG).setAction(
                            "Open Email", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent openMail = new Intent(ACTION_MAIN);
                                    openMail.addCategory(CATEGORY_APP_EMAIL);
                                    context.startActivity(openMail);
                                }
                            }
                    ).show();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Convert string ke uppercase MD5
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static String convertToMD5(String plain) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(plain.getBytes(), 0, plain.length());
        String encryptedPassword = new BigInteger(1, messageDigest.digest()).toString(16).toUpperCase();
        if (encryptedPassword.length() < 32) {
            encryptedPassword = 0 + encryptedPassword;
        }
//        Log.d("convertToMD5: ", encryptedPassword);
        return encryptedPassword;
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    //      Show datepicker dialog
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void showDatePickerDialog(Context context, final String caller) {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d-MMM-yyyy", Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String tanggalDipilih = dateFormat.format(newDate.getTime());
                switch (caller) {
                    case "TanggalBerangkat":
                        MainActivity.buttonTanggalBerangkat.setText(tanggalDipilih);
                        MainActivity.btnGetRoute.setEnabled(true);
                        MainActivity.btnGetRoute.getBackground().setColorFilter(null);
                        break;

                    case "TanggalPulang":
                        MainActivity.buttonTanggalPulang.setText(tanggalDipilih);
                        MainActivity.btnGetRoute.setEnabled(true);
                        MainActivity.btnGetRoute.getBackground().setColorFilter(null);

                        break;
                    default:
                        break;
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Show terminal dialog
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void showDialogTerminalList(Context context, final ArrayList<Terminal> terminals, final String mCaller) {
        View listTerminalKeberangkatan = LayoutInflater.from(context).inflate(R.layout.list_terminal, null);
        TerminalListAdapter terminalListAdapter = new TerminalListAdapter(context, terminals);
        ListView listViewTerminal = listTerminalKeberangkatan.findViewById(R.id.list_terminal);
        listViewTerminal.setAdapter(terminalListAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(listTerminalKeberangkatan).setCancelable(false);
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialogTerminalKeberangkatan = builder.create();
        dialogTerminalKeberangkatan.show();

        listViewTerminal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Terminal terminal = terminals.get(position);
                switch (mCaller) {
                    case Constants.GET_TERMINAL_ORIGIN:
                        orgCode = terminal.getOrgCode();
                        MainActivity.txtTerminalKeberangkatan.setText(terminal.getTerminalName());
                        MainActivity.txtTerminalTujuan.setEnabled(true);
                        dialogTerminalKeberangkatan.dismiss();
                        break;
                    case Constants.GET_TERMINAL_DESTINATION:
                        desCode = terminal.getDesCode();
                        MainActivity.txtTerminalTujuan.setText(terminal.getTerminalName());
                        MainActivity.buttonTanggalBerangkat.setEnabled(true);
                        MainActivity.buttonTanggalPulang.setEnabled(true);
                        dialogTerminalKeberangkatan.dismiss();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    // Show Dialog Rute
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void showDialogRoute(final Context context, final ArrayList<Rute> rutes) {
        final View listRute = LayoutInflater.from(context).inflate(R.layout.list_route, null);
        RouteListAdapter routeListAdapter = new RouteListAdapter(context, rutes);
        ListView listViewRoute = listRute.findViewById(R.id.listview_route);
        listViewRoute.setAdapter(routeListAdapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(listRute);

        AlertDialog routeDialog = builder.create();
        routeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        routeDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        routeDialog.show();

        listViewRoute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rute rute = rutes.get(position);
                String orgCode = rute.getOrgCode();
                String desCode = rute.getDesCode();
                String orgName = rute.getOrgName();
                String desName = rute.getDesName();
                String depDate = rute.getDepartureDate();
                String routeCode = rute.getRouteCode();
                String routeName = rute.getRouteName();
                String routeInfo = rute.getRouteInfo();

                Intent i = new Intent(context, BookingActivity.class);
                i.putExtra("desCode", desCode);
                i.putExtra("Destination name", desName);
                i.putExtra("Origin code", orgCode);
                i.putExtra("Origin name", orgName);
                i.putExtra("Departure date", depDate);
                i.putExtra("Route code", routeCode);
                i.putExtra("Route name", routeName);
                i.putExtra("Route info", routeInfo);

                final View InputName = LayoutInflater.from(context).inflate(R.layout.input_data_penumpang, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(InputName);

                AlertDialog routeDialog = builder.create();
                routeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                routeDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
                routeDialog.show();

//                context.startActivity(i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    /*----------------------------------------------------------------------------------------------------------------------*/
    // Method untuk menampilkan dan menyembunyikan custom loading
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static void showLoading(Context context) {
        final View customDialog = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(customDialog);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.show();
    }

    public static void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dialog.dismiss();
    }
}