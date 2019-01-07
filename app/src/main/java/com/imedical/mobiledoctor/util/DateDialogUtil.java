package com.imedical.mobiledoctor.util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.DatePicker;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateDialogUtil {
    /**
     * @param @param  activity
     * @param @param  myCallback
     * @param @return 设定文件
     * @return Dialog    返回类型
     * @throws
     * @Title: allDatePicKDialog
     * @Description:(选择所有时间的时间选择器)
     * @author lqz
     * @date 2015-8-13 下午4:39:49
     */
    public static Dialog allDatePicKDialog(Activity activity, final MyCallback<String> myCallback) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int day) {
                String date = year + "-" + (month + 1) + "-" + day;
                myCallback.onCallback(date);
            }
        };
        DatePickerDialog dialogDate = new DatePickerDialog(activity,
                dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialogDate.show();
        return dialogDate;
    }

    /**
     * @param @param  activity
     * @param @param  myCallback
     * @param @return 设定文件
     * @return Dialog    返回类型
     * @throws
     * @Title: beforeTodayDatePicKDialog
     * @Description:(今天之前日期选择器)
     * @author lqz
     * @date 2015-8-13 下午4:41:03
     */
    public static Dialog beforeTodayDatePicKDialog(Activity activity, final MyCallback<String> myCallback) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int day) {
                String date = year + "-" + (month + 1) + "-" + day;
                myCallback.onCallback(date);
            }
        };
        DatePickerDialog dialogDate = new DatePickerDialog(activity,
                dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialogDate.getDatePicker();
        datePicker.setMaxDate(calendar.getTimeInMillis());
        dialogDate.show();
        return dialogDate;
    }

    /**
     * @param @param  activity
     * @param @param  myCallback
     * @param @return 设定文件
     * @return Dialog    返回类型
     * @throws
     * @Title: afterTodayDatePicKDialog
     * @Description:(今天之后日期选择器)
     * @author lqz
     * @date 2015-8-13 下午4:41:32
     */
    public static Dialog afterTodayDatePicKDialog(Activity activity, final MyCallback<String> myCallback) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int day) {
                String date = year + "-" + (month + 1) + "-" + day;
                myCallback.onCallback(date);
            }
        };
        DatePickerDialog dialogDate = new DatePickerDialog(activity,
                dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialogDate.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
        dialogDate.show();
        return dialogDate;
    }


    /**
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getCurrentDate
     * @Description:(返回当前日期)
     * @author lqz
     * @date 2015-8-13 下午4:47:16
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        return formatter.format(curDate);
    }

    /**
     * @param @param activity
     * @param @param tvDate    设定文件
     * @return void    返回类型
     * @throws
     * @Title: allDateAutoSetTextView
     * @Description:(所有日期都可选，传递过TextView来，选择日期后直接放在TextView中)
     * @author lqz
     * @date 2015-8-13 下午5:23:52
     */
    public static void allDateAutoSetTextView(Activity activity, final TextView tvDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int day) {
                String date = year + "-" + (month + 1) + "-" + day;
                tvDate.setText(date);
            }
        };
        DatePickerDialog dialogDate = new DatePickerDialog(activity,
                dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialogDate.show();
    }

    /**
     * @param @param activity
     * @param @param tvDate    设定文件
     * @return void    返回类型
     * @throws
     * @Title: beforeTodayAutoSetTextView
     * @Description:(今天之前的日期，传递过TextView来，选择日期后直接放在TextView中)
     * @author lqz
     * @date 2015-8-13 下午5:24:48
     */
    public static void beforeTodayAutoSetTextView(Activity activity, final TextView tvDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int day) {
                String date = year + "-" + (month + 1) + "-" + day;
                tvDate.setText(date);
            }
        };
        DatePickerDialog dialogDate = new DatePickerDialog(activity,
                dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialogDate.getDatePicker();
        datePicker.setMaxDate(calendar.getTimeInMillis());
        dialogDate.show();
    }

    /**
     * @param @param activity
     * @param @param tvDate    设定文件
     * @return void    返回类型
     * @throws
     * @Title: afterTodayAutoSetTextView
     * @Description:(今天之后的日期，传递过TextView来，选择日期后直接放在TextView中)
     * @author lqz
     * @date 2015-8-13 下午5:25:02
     */
    public static void afterTodayAutoSetTextView(Activity activity, final TextView tvDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month,
                                  int day) {
                String date = year + "-" + (month + 1) + "-" + day;
                tvDate.setText(date);
            }
        };
        DatePickerDialog dialogDate = new DatePickerDialog(activity,
                dateListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = dialogDate.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
        dialogDate.show();
    }

}

