package com.imedical.mobiledoctor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//本地轻量级存储SharedPreferences工具类
public class PreferManager {
    public static final String preName = "dhcc_mobile_doctor";

    public static Context context;

    public static void inti(Context ct) {
        context = ct;
    }

    public static String getValue(String key) {
        SharedPreferences sp = context.getSharedPreferences(preName, 0);
        return sp.getString(key, "");
    }


    public static void saveValue(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(preName, 0);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }


}
