package com.imedical.mobiledoctor.XMLservice;

import android.app.Activity;
import com.imedical.mobiledoctor.Const;
public class SettingManager {
    private static Activity ctx;

    public static void setServerURL(Activity ctx, String serverURL) {
        PrefManager.saveToPref(ctx, "serverURL", serverURL);
    }

    public static String getServerUrl() {
        return Const.SERVICEURL;
    }

    public static void setServerUrl(Activity ctx, String url) {
        //String url = "http://172.23.6.209/dtHealth/web/MHC.PublicService.cls";
        PrefManager.saveToPref(ctx, "serverURL", url);
    }

    public static void initContext(Activity context) {
        ctx = context;
    }

    public static void saveIp4Intranet(Activity context, String value) {
        PrefManager.saveToPref(context, "Ip4Intranet", value);
    }

    public static CharSequence getIp4Intranet(Activity context) {
        return PrefManager.getFromPref(context, "Ip4Intranet", "");
    }

    public static void saveIp4Internet(Activity context, String value) {
        PrefManager.saveToPref(context, "Ip4Internet", value);
    }

    public static CharSequence getIp4Internet(Activity context) {
        return PrefManager.getFromPref(context, "Ip4Internet", "");
    }

    public static void setIsIntranet(Activity context, boolean b) {
        PrefManager.saveBoolean(context, "isIntranet", b);
    }

    public static boolean isIntranet(Activity ctx) {
        return PrefManager.getBoolean(ctx, "isIntranet", true);
    }

    public static boolean is(Activity ctx) {
        return PrefManager.getBoolean(ctx, "isIntranet", true);
    }



    public static String getUsername4Hos(Activity context) {
        return PrefManager.getFromPref(context, "username4Hos");
    }

    public static void setUsername4Hos(Activity context, String userName) {
        PrefManager.saveToPref(context, "username4Hos", userName);
    }


    public static String getPassword4Hos(Activity context) {
        return PrefManager.getFromPref(context, "password4Hos");
    }

    public static void setPassword4Hos(Activity context, String psw) {
        PrefManager.saveToPref(context, "password4Hos", psw);
    }

    public static void setRememberPsw4Hos(Activity ctx, boolean checked) {
        PrefManager.saveBoolean(ctx, "isSavePwd_4hos", checked);
    }

    public static boolean isRememberUser4Hos(Activity ctx) {
        return PrefManager.getBoolean(ctx, "isSavePwd_4hos", false);
    }

    public static String getNetWorkType(Activity ctx) {
        boolean isIntra = isIntranet(ctx);//内网 ?
        String type;
        if (isIntra) {
            type = "Intranet";
        } else {
            type = "Internet";
        }
        return type;
    }
}
