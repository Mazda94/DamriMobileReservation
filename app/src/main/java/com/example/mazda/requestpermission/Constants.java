package com.example.mazda.requestpermission;

import org.json.JSONObject;

public class Constants {
    public static final String MOBILE_USERNAME = "mobile";
    public static final String MOBILE_PASSWORD = "0192023a7bbd73250516f069df18b500";

    private static final String BASE_URL = "http://119.110.87.74/git/damri_api/v4";
    public static final String URL_LOGIN_MOBILE = BASE_URL+"/auth/mobile";
    public static final String URL_REGISTER_USER = BASE_URL+"/member/register";
    public static final String URL_LOGIN_USER = BASE_URL+"/member/login";
    public static final String URL_ACTIVATION_USER = BASE_URL+"/member/activation";
    public static final String URL_UPDATE_PROFILE_USER = BASE_URL+"/member/updateProfile";
    public static final String URL_FORGOT_PASSWORD = BASE_URL+"/member/forgotPassword";
    public static final String URL_RESET_PASSWORD = BASE_URL+"/member/resetPassword";
    public static final String URL_GET_TERMINAL_KEBERANGKATAN = BASE_URL+"/terminal/getTerminal";
    public static final String URL_GET_TERMINAL_TUJUAN = BASE_URL+"/terminal/getDestination?";
    public static final String URL_GET_ROUTE = BASE_URL +"/schedule/getRoute?";

    public static final String GET_TERMINAL_ORIGIN = "GetTerminalOrigin";
    public static final String GET_TERMINAL_DESTINATION = "GetTerminalDestinantion";
    public static final String GET_ROUTE = "GetRoute";
    public static final String LOGIN_MOBILE_REQUEST = "RequestLoginMobile";
    public static final String LOGIN_USER_REQUEST = "RequestLoginUser";
    public static final String REGISTER_REQUEST = "RequestRegister";
    public static final String RESET_PASSWORD_REQUEST = "RequestResetPassword";
}

