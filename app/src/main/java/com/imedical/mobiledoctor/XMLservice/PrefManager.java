package com.imedical.mobiledoctor.XMLservice;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.imedical.mobiledoctor.util.DateUtil;

import java.sql.Date;
import java.util.Calendar;


/**
 * 本地数据存维护（用户信息在SysManager中） SharedPreferences中的 健值对 尽量放在这个类中维护，不要出现在其它文件中，
 * 其它类通过 setter getter 来调用，以避免拼写key时出现错误。
 *
 * @author Administrator
 */
public class PrefManager {

    public static final String now_date_yyyyMMdd = DateUtil.getNowDateTime("yyyyMMdd");
    public static final String KEY_DEFAULT_HOS_ID = "KEY_DEFAULT_HOS_ID";
    public static final String KEY_DEFAULT_HOS_NAME = "KEY_DEFAULT_HOS_NAME";

    public static SharedPreferences getSharedPreferences_PARAM(Context ctx) {
        return ctx.getSharedPreferences("_PARAM_", 0);
    }

    public static SharedPreferences getSharedPreferences_PACKAGE(Activity ctx) {
        return ctx.getSharedPreferences(ctx.getPackageName(), 0);
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////
    //
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////

    public static void clear(Context ctx) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.clear();
        ed.commit();
    }

    /**
     * 手机号
     *
     * @param ctx
     * @return
     */
    public static void saveUsername(Activity ctx, String phoneNum) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.putString("key_user_name", phoneNum);
        ed.commit();
    }

    /**
     * 获取手机号
     *
     * @param ctx
     * @return
     */
    public static String getUsername(Activity ctx) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getString("key_user_name", "");//
    }

    public static void savePwd(Activity ctx, String psw) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.putString("key_user_psw", psw);
        ed.commit();
    }

    public static String getPwd(Activity ctx) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getString("key_user_psw", "");//
    }

    public static boolean isFirstTime(Activity ctx) {
        SharedPreferences s = getSharedPreferences_PACKAGE(ctx);
        boolean is = s.getBoolean("_firstTime_", true);
        // 立刻设置为false
        Editor ed = s.edit();
        ed.putBoolean("_firstTime_", false);
        ed.commit();
        // TODO
        return is;
    }

    public static void saveToPref(Context ctx, String key, String value) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public static void saveIntToPref(Context ctx, String key, int value) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.putInt(key, value);
        ed.commit();
    }

    public static String getFromPref(Context ctx, String key) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getString(key, "");
    }

    public static String getFromPref(Context ctx, String key, String defaultValue) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getString(key, defaultValue);
    }

    public static int getIntFromPref(Context ctx, String key) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getInt(key, 0);
    }


    public static void setIsIntra(Activity ctx, boolean b) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.putBoolean("isIntra", b);
        ed.commit();
    }

    /**
     * 是否内网
     *
     * @param ctx
     * @return
     */
    public static boolean isIntra(Context ctx) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getBoolean("isIntra", false);
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //
    //
    /////////////////////////////////////////////////////////////////////////////////////
    public static String getHospitalIdDefault(Activity ctx) {
        String hospitalId = PrefManager.getFromPref(ctx, KEY_DEFAULT_HOS_ID);
        return hospitalId;
    }

    public static String getHospitalNameDefault(Activity ctx) {
        String name = PrefManager.getFromPref(ctx, KEY_DEFAULT_HOS_NAME);
        return name;
    }


    public static void saveBoolean(Activity ctx, String key, boolean b) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        Editor ed = share.edit();
        ed.putBoolean(key, b);
        ed.commit();

    }

    public static boolean getBoolean(Activity ctx, String key, boolean defaultValue) {
        SharedPreferences share = getSharedPreferences_PARAM(ctx);
        return share.getBoolean(key, defaultValue);
    }

    /**
     * @param @param context
     * @param @param value    设定文件
     * @return void    返回类型
     * @throws
     * @Title: saveUrl4Inter
     * @Description: TODO(存储外网)
     * @author lqz
     * @date 2015-11-24 下午2:27:07
     */
    public static void saveUrl4Inter(Activity context, String value) {
        PrefManager.saveToPref(context, "Ip4Internet", value);
    }

    /**
     * @param @param context
     * @param @param value    设定文件
     * @return void    返回类型
     * @throws
     * @Title: saveUrl4Intra
     * @Description: TODO(存储内网)
     * @author lqz
     * @date 2015-11-24 下午2:26:56
     */
    public static void saveUrl4Intra(Activity context, String value) {
        PrefManager.saveToPref(context, "url4Intranet", value);
    }

    public static void saveLastLoginDate(Context ctx, String lastDate) {

        PrefManager.saveToPref(ctx, "last_login_date", lastDate);
    }

    public static String getLastLoginDate(Context ctx) {
        String lastDate = PrefManager.getFromPref(ctx, "last_login_date", "2016-01-01");
        return lastDate;
    }

    /**
     * 日历预约相关信息
     */
    public static SharedPreferences getSharedPreferences_ORDER(Context ctx) {
        return ctx.getSharedPreferences("_ORDER_", 0);
    }

    public static void clearOrder(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.clear();
        ed.commit();
    }

    /*
     * 当前选择日期
     */
    public static void saveDate(Context ctx, String nowDate) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.putString("now_date", nowDate);
        ed.commit();
    }

    public static String getDate(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Calendar cal = Calendar.getInstance();
        String today = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DATE);
        return share.getString("now_date", today);
    }

    /*
     * 当前排班资源
     */
    public static void saveScheduleItemCode(Context ctx, String ScheduleItemCode) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.putString("ScheduleItemCode", ScheduleItemCode);
        ed.commit();
    }

    public static String getScheduleItemCode(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        return share.getString("ScheduleItemCode", "");
    }

    /*
     * 当前号别
     */
    public static void saveMark(Context ctx, String mark) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.putString("now_mark", mark);
        ed.commit();
    }

    public static String getMark(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        return share.getString("now_mark", "");
    }

    /*
     * 当前时间范围
     */
    public static void saveTimeRange(Context ctx, String timeRange) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.putString("now_timerange", timeRange);
        ed.commit();
    }

    public static String getTimeRange(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        return share.getString("now_timerange", "");
    }

    /*
     * 当前科室ID
     */
    public static void saveDepartmentId(Context ctx, String departmentId) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.putString("now_departmentid", departmentId);
        ed.commit();
    }

    public static String getDepartmentId(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        return share.getString("now_departmentid", "");
    }

    /*
     * 当前科室名称
     */
    public static void saveDepartmentName(Context ctx, String departmentName) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        Editor ed = share.edit();
        ed.putString("now_departmentname", departmentName);
        ed.commit();
    }

    public static String getDepartmentName(Context ctx) {
        SharedPreferences share = getSharedPreferences_ORDER(ctx);
        return share.getString("now_departmentname", "");
    }

}
