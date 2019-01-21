package com.imedical.mobiledoctor.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static long getNowTime() {
        Calendar cal1 = Calendar.getInstance();
        return cal1.getTimeInMillis() / 1000;
    }
    public static boolean isMax(String startDate, String endDate) {
        String dateStr1 = startDate;
        String dateStr2 = endDate;
        Calendar time1 = DateTimePickDialogUtil.getCalendarByInintData(dateStr1, "yyyy-MM-dd");
        Calendar time2 = DateTimePickDialogUtil.getCalendarByInintData(dateStr2, "yyyy-MM-dd");
        if (time2.compareTo(time1) < 0) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isBeforeToday(String date) {
        Calendar time1 = DateTimePickDialogUtil.getCalendarByInintData(date, "yyyy-MM-dd");
        Calendar time2 = DateTimePickDialogUtil.getCalendarByInintData(getDateToday(null), "yyyy-MM-dd");
        if (time1.compareTo(time2) < 0) {
            return true;
        } else {
            return false;
        }
    }
    public static int getDatediff(String dateStr1,String dateStr2) {
//        String dateStr1 = tv_startDate.getText().toString();
//        String dateStr2 = tv_endDate.getText().toString();
        long time1 = DateTimePickDialogUtil.getCalendarByInintData(dateStr1, "yyyy-MM-dd").getTimeInMillis();
        long time2 = DateTimePickDialogUtil.getCalendarByInintData(dateStr2, "yyyy-MM-dd").getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        int diff = 0;
        if (between_days >= 0 && between_days <= 7) {
            diff = (int) between_days;
        } else {
            diff = 7;
        }
        return diff;
    }

    public static String ConvertDateTime(String time) {
        String format = "yyyy-MM-dd HH:mm:ss";
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(time));
        Date currentTime = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);// "yyyyMMdd"
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getNowTimeMillis(){
        return System.currentTimeMillis()+"";
    }

    public static String getWeek(String strCurrDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCurr = null;
        try {
            dateCurr = sdf.parse(strCurrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }// 传入的日期

        SimpleDateFormat f = new SimpleDateFormat("E");

        return f.format(dateCurr.getTime());
    }

    public static String getNowDateTime(String format) {
        if (Validator.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);// "yyyyMMdd"
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static List<WeekDate> getOneWeek(int num) {
        List<WeekDate> oneweek = new ArrayList<WeekDate>();
        for (int i = -3; i < 4; i++) {
            WeekDate wd = new WeekDate();
            wd.setDate(getDateTodayBefore("yyyy-MM-dd", num * 7 + i));
            wd.setShortdate(getDateTodayBefore("M.d", num * 7 + i));
            wd.setWeek(getWeek(wd.getDate()));
            wd.setShortweek(wd.getWeek().substring(wd.getWeek().length() - 1));
            oneweek.add(wd);
        }
        return oneweek;

    }

    public static String getDateToday(String format) {
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String towDaysBefore = sdf.format(cal1.getTime());
        return towDaysBefore;
    }

    public static String getDateTodayBefore(String format, int plusDays) {
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, plusDays);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dayBefore = sdf.format(cal1.getTime());
        return dayBefore;
    }

    public static String getDateStr(String format, String strCurrDate, int plusDays) {
        String dayBefore = null;
        if (format == null) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date dateCurr = sdf.parse(strCurrDate);// 传入的日期
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(dateCurr);
            cal1.add(Calendar.DATE, plusDays);

            dayBefore = sdf.format(cal1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayBefore;
    }

    //"yyyy-MM-dd HH:mm"
    public static String getDateStr(String dateTime, String format) {
        String strDate = null;
        try {
            strDate = dateTime.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String getTime(String dateTime, String format) {
        String strTime = null;
        try {
//	         SimpleDateFormat   sdf   =   new SimpleDateFormat(format);  
//	         Date dateCurr = sdf.parse(dateTime);// 传入的日期
//	         Calendar   cal1   =   Calendar.getInstance();
//	         cal1.setTime(dateCurr);
//	         strTime = cal1.get(Calendar.HOUR_OF_DAY)+":"+cal1.get(Calendar.MINUTE);
            strTime = dateTime.substring(11);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strTime;
    }


    public static String getDateTimeStr(Date date, String format) {
        if (Validator.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String getDateTimeGregorian(Date localDate, String format) {
        if (Validator.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(localDate);
    }


    public static Date getDate(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {

            date = sdf.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 截取年 2012-01-01
     *
     * @param date
     * @return
     */
    public static String getYear(String date) {
        String year = null;
        try {
            year = date.substring(0, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return year;
    }

    //20140501
    public static String getMonth(String date) {
        String month = null;
        try {
            month = date.substring(5, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return month;
    }

    public static String getDay(String date) {
        String day = null;
        try {
            day = date.substring(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * @param birthDate
     * @return
     */
    public static CharSequence getAge(String birthDate) {
        // TODO Auto-generated method stub
        return null;
    }

    public static String getInterval(Date ltime) { //传入的时间格式必须类似于2012-8-21 17:53:20这样的格式
        String interval = null;


        Date d1 = ltime;
        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔  
        long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒  

        if (time / 1000 < 10 && time / 1000 >= 0) {
            //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
            interval = "刚刚";

        } else if (time / 3600000 < 24 && time / 3600000 >= 1) {
            //如果时间间隔小于24小时则显示多少小时前
            int h = (int) (time / 3600000);//得出的时间间隔的单位是小时
            interval = h + "小时前";

        } else if (time / 60000 < 60 && time / 60000 > 1) {
            //如果时间间隔小于60分钟则显示多少分钟前
            int m = (int) ((time % 3600000) / 60000);//得出的时间间隔的单位是分钟
            interval = m + "分钟前";

        } else if (time / 1000 < 60 && time / 1000 > 0) {
            //如果时间间隔小于60秒则显示多少秒前
            int se = (int) ((time % 60000) / 1000);
            interval = se + "秒前";

        } else {
            //大于24小时，则显示正常的时间，但是不显示秒  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Date d2 = ltime;
            interval = sdf.format(d2);
        }
        return interval;
    }
}
