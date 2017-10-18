package com.bixing.tiannews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLES20;

import com.bixing.tiannews.bean.UserBean;

/**
 * Created by sjw on 2017/9/25.
 */

public class SpfManger {
    private static String SP_NAME = "tianjinnews";
    private static String USER = "user";
    private static String TAG = "SpfManger";
    private static String TOKEN = "token";

    public static void setStr(Context context, String key, String value) {
        SharedPreferences spf = getSpf(context);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveUser(Context context, UserBean userBean) {
        String userJson = GsonManager.toJson(userBean);
        DebugLog.d(TAG, "userJson " + userJson);
        getSpf(context).edit().putString(USER, userJson).commit();
    }

    public static UserBean getUser(Context context) {
        String userJson = getSpf(context).getString(USER, "{}");
        UserBean userBean = GsonManager.fromJsonStr(userJson, UserBean.class);
        return userBean;
    }

    public static void saveToken(Context context, String token) {
        getSpf(context).edit().putString(TOKEN, token).commit();
    }

    public static String getToken(Context context) {
        return getSpf(context).getString(TOKEN, "");
    }

    public static SharedPreferences getSpf(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static void clearAll(Context context) {
        getSpf(context).edit().clear().commit();
    }
}
