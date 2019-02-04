package com.example.mazda.requestpermission;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Preference name
    /*----------------------------------------------------------------------------------------------------------------------*/
    private static final String USER_PREFERENCES = "user_information";
    private static final String TOKEN_PREFERENCE = "token_information";
    private static final String DEVICE_PREFERENCE = "device_information";
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    User Information
    /*----------------------------------------------------------------------------------------------------------------------*/
    private static final String USER_FIRSTNAME = "first_name";
    private static final String USER_LASTNAME = "last_name";
    private static final String USER_POB = "place_of_birth";
    private static final String USER_DOB = "date_of_birth";
    private static final String USER_ADDRESS = "address";
    private static final String USER_PHONE_NUMBER = "phone_number";
    private static final String USER_EMAIL = "email";
    private static final String USER_STATUS = "status";
    private static final String USER_ACTIVATION_CODE = "activation_code";
    private static final String USER_CREATED_ON = "created_on";
    private static final String USER_IS_ACTIVATION = "is_activation";
    private static final String USER_ACTIVE = "active";
    private static final String USER_GENDER = "gender";
    private static Preferences mInstance;
    private static Context mContext;
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Device information
    /*----------------------------------------------------------------------------------------------------------------------*/
    private static final String IMEI_NUMBER = "imei_number";
    private static final String SUBSCRIBER_ID = "subscriber_id";
    private static final String SIM_SERIAL_NUMBER = "sim_serial_number";
    private static final String NETWORK_COUNTRY_ISO = "network_country_iso";
    private static final String SIM_COUNTRY_ISO = "sim_country_iso";
    private static final String SOFTWARE_VERSION = "software_version";
    private static final String VOICE_MAIL_NUMBER = "voice_mail_number";
    private static final String EXPIRED_TOKEN = "expired_token";
    private static final String VALID_TOKEN = "valid_token";
    /*----------------------------------------------------------------------------------------------------------------------*/
    // Buat instance activity
    /*----------------------------------------------------------------------------------------------------------------------*/
    public Preferences(Context context) {
        mContext = context;
    }
    public static synchronized Preferences getPreference(Context context) {
        if (mInstance == null) {
            mInstance = new Preferences(context);
        }
        return mInstance;
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Save device info into preference
    /*----------------------------------------------------------------------------------------------------------------------*/
    public static boolean deviceInfo(String imeiNumber, String subscriberId, String simSerial, String networkCountryIso, String simCountryIso, String softwareVersion, String voiceMailNumber) {
        SharedPreferences devicePreference = mContext.getSharedPreferences(DEVICE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor deviceEditor = devicePreference.edit();
        deviceEditor.putString(IMEI_NUMBER, imeiNumber);
        deviceEditor.putString(SUBSCRIBER_ID, subscriberId);
        deviceEditor.putString(SIM_SERIAL_NUMBER, simSerial);
        deviceEditor.putString(NETWORK_COUNTRY_ISO, networkCountryIso);
        deviceEditor.putString(SIM_COUNTRY_ISO, simCountryIso);
        deviceEditor.putString(SOFTWARE_VERSION, softwareVersion);
        deviceEditor.putString(VOICE_MAIL_NUMBER, voiceMailNumber);
        deviceEditor.apply();
        return true;
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Start session
    /*----------------------------------------------------------------------------------------------------------------------*/
    public boolean sessioIsActive() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        if (userPreference.getString(USER_EMAIL, null) != null) {
            return true;
        }
        return false;
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    End session
    /*----------------------------------------------------------------------------------------------------------------------*/
    public boolean logout() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor userEditor = userPreference.edit();
        userEditor.clear().apply();
        return true;
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Simpan token
    /*----------------------------------------------------------------------------------------------------------------------*/
    public void saveToken(String token, String expired) {
        SharedPreferences tokenPreference = mContext.getSharedPreferences(TOKEN_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor tokenEditor = tokenPreference.edit();
        tokenEditor.putString(VALID_TOKEN, token);
        tokenEditor.putString(EXPIRED_TOKEN, expired);
        tokenEditor.apply();
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Cek ketersediaan token
    /*----------------------------------------------------------------------------------------------------------------------*/
    public boolean checkToken() {
        SharedPreferences tokenPreference = mContext.getSharedPreferences(TOKEN_PREFERENCE, Context.MODE_PRIVATE);
        if (tokenPreference.getString(VALID_TOKEN, null) != null) {
            return true;
        }
        return false;
    }
    /*----------------------------------------------------------------------------------------------------------------------*/
    //    Simpan informasi user
    /*----------------------------------------------------------------------------------------------------------------------*/
    public void saveUserDetail(String firstName, String lastName, String placeOfBirth, String dateOfBirth, String address, String phoneNumber,
                               String email, String activationCode, String status, String createdOn, int isActivation, int active, int gender)
    {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor userEditor = userPreference.edit();
        userEditor.putString(USER_FIRSTNAME, firstName);
        userEditor.putString(USER_LASTNAME, lastName);
        userEditor.putString(USER_POB, placeOfBirth);
        userEditor.putString(USER_DOB, dateOfBirth);
        userEditor.putString(USER_ADDRESS, address);
        userEditor.putString(USER_PHONE_NUMBER, phoneNumber);
        userEditor.putString(USER_EMAIL, email);
        userEditor.putString(USER_STATUS, status);
        userEditor.putString(USER_ACTIVATION_CODE, activationCode);
        userEditor.putString(USER_CREATED_ON, createdOn);
        userEditor.putInt(USER_IS_ACTIVATION, isActivation);
        userEditor.putInt(USER_ACTIVE, active);
        userEditor.putInt(USER_GENDER, gender);
        userEditor.apply();
    }

//    public static String getImeiNumber() {
//        return devicePreference.getString(IMEI_NUMBER, null);
//    }

    public static String getValidToken() {
        SharedPreferences tokenPreference = mContext.getSharedPreferences(TOKEN_PREFERENCE, Context.MODE_PRIVATE);
        return tokenPreference.getString(VALID_TOKEN, null);
    }

    public static String getUserFirstname() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_FIRSTNAME, null);
    }

    public static String getUserLastname() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_LASTNAME, null);
    }

    public static String getUserPob() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_POB, null);
    }

    public static String getUserDob() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_DOB, null);
    }

    public static String getUserAddress() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_ADDRESS, null);
    }

    public static String getUserPhoneNumber() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_PHONE_NUMBER, null);
    }

    public static String getUserEmail() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor userEditor = userPreference.edit();
        return userPreference.getString(USER_EMAIL, null);
    }

    public static String getUserStatus() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_STATUS, null);
    }

    public static String getUserActivationCode() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_ACTIVATION_CODE, null);
    }

    public static String getUserCreatedOn() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_CREATED_ON, null);
    }

    public static String getUserIsActivation() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_IS_ACTIVATION, null);
    }

    public static String getUserActive() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_ACTIVE, null);
    }

    public static String getUserGender() {
        SharedPreferences userPreference = mContext.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        return userPreference.getString(USER_GENDER, null);
    }
}