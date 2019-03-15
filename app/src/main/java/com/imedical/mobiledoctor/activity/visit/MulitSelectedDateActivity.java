package com.imedical.mobiledoctor.activity.visit;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.widget.CalendarView;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MulitSelectedDateActivity extends BaseActivity {

    private static final String TAG = MulitSelectedDateActivity.class.getSimpleName();
    private CalendarView mCalendarView;
    private TextView mTxtDate,tv_newsche;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<String> DateList;
    private boolean isMuiltSeleced=false;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muiltselectdate_activity);
        setTitle("图文咨询排班");
        mTxtDate = (TextView) findViewById(R.id.txt_date);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setDateFormatPattern("yyyy-MM-dd");

        // 设置已选的日期
        mCalendarView.setSelectDate(initData());

        // 指定显示的日期, 如当前月的下个月
        Calendar calendar = mCalendarView.getCalendar();

        mCalendarView.setCalendar(calendar);

        // 设置字体
        mCalendarView.setTypeface(Typeface.SERIF);

        // 设置日期状态改变监听
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, boolean select, int year, int month, int day) {
                Log.e(TAG, "select: " + select);
                Log.e(TAG, "year: " + year);
                Log.e(TAG, "month,: " + (month + 1));
                Log.e(TAG, "day: " + day);
                Calendar cal1 = Calendar.getInstance();
                cal1.set(year,month,day);
                String outString=sdf.format(cal1.getTime());
                Date dt =new Date();
                String td=sdf.format(dt);
                if(select){
                    DateList.add(outString);
                }else{
                    DateList.remove(outString);
//                    Toast.makeText(getApplicationContext()取消选中了：" + year + "年" + (month + 1) + "月" + day + "日/"+outString, Toast.LENGTH_SHORT).show();
                }
                tv_newsche.setVisibility(View.VISIBLE);
                for(String date :DateList){
                    try {
                        Date today = sdf.parse(td);
                        Date target = sdf.parse(date);
                        boolean before = target.before(today);
                        if(before){
                            tv_newsche.setVisibility(View.INVISIBLE);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        showCustom(e.toString());
                    }

                }

            }
        });
        // 设置是否能够改变日期状态
        mCalendarView.setChangeDateStatus(true);

        // 设置日期点击监听
        mCalendarView.setOnDataClickListener(new CalendarView.OnDataClickListener() {
            @Override
            public void onDataClick(@NonNull CalendarView view, int year, int month, int day) {
                Log.e(TAG, "year: " + year);
                Log.e(TAG, "month,: " + (month + 1));
                Log.e(TAG, "day: " + day);
            }
        });
        // 设置是否能够点击
        mCalendarView.setClickable(true);

        setCurDate();

        DateList=initData();
        tv_newsche=(TextView) findViewById(R.id.tv_newsche);
        tv_newsche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scheduleDate="";
                boolean canSumbit=false;
                if(DateList.size()>0){
                    for (String s :DateList){
                        scheduleDate=scheduleDate+";"+s;
                    }
                    scheduleDate= scheduleDate.substring(1,scheduleDate.length());
                }else {
                    showCustom("至少选择一天排班");
                    return;
                }
                if(DateList.size()>1){
                    isMuiltSeleced=true;
                }
                else {
                    isMuiltSeleced=false;
                }
                    Intent intent=new Intent(MulitSelectedDateActivity.this, SetDoctorScheduleActivity.class);
                    intent.putExtra("scheduleDate", scheduleDate);
                    intent.putExtra("isMuiltSeleced",isMuiltSeleced);
                    startActivityForResult(intent,101);
            }
        });
    }

    private List<String> initData() {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat sdf = new SimpleDateFormat(mCalendarView.getDateFormatPattern(), Locale.CHINA);
        sdf.format(calendar.getTime());
        dates.add(sdf.format(calendar.getTime()));
        return dates;
    }

    public void next(View v){
        mCalendarView.nextMonth();
        setCurDate();
    }

    public void last(View v){
        mCalendarView.lastMonth();
        setCurDate();
    }

    private void setCurDate(){
        mTxtDate.setText(mCalendarView.getYear() + "年" + (mCalendarView.getMonth() + 1) + "月");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(100==resultCode){
            setResult(100,getIntent() );
            finish();
        }
    }

}
