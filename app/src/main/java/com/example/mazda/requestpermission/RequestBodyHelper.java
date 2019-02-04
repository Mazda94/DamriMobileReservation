package com.example.mazda.requestpermission;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestBodyHelper {

    public static void loginMobile(String caller, Context context) throws JSONException {
        JSONObject loginMobile = new JSONObject();
        loginMobile.put("username", Constants.MOBILE_USERNAME);
        loginMobile.put("password", Constants.MOBILE_PASSWORD);
        MethodCollection.httpRequestPost(context, Constants.URL_LOGIN_MOBILE, loginMobile, caller, 1);
    }

    public static void loginUser(Context context, String email, String password, String caller) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        MethodCollection.httpRequestPost(context, Constants.URL_LOGIN_USER, requestBody, caller, 1);
    }

    public static void registerUser(String firstName, String lastName, String email, String password, String phoneNumber, int gender, String caller, Context context) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstname", firstName);
        requestBody.put("lastname", lastName);
        requestBody.put("gender", gender);
        requestBody.put("email", email);
        requestBody.put("phone_number", phoneNumber);
        requestBody.put("password", password);
        MethodCollection.httpRequestPost(context, Constants.URL_REGISTER_USER, requestBody, caller, 1);
    }

    public static void resetPassword(String email, String caller, Context context) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        MethodCollection.httpRequestPost(context, Constants.URL_FORGOT_PASSWORD, requestBody, caller, 1);
    }
}