package com.imedical.mobiledoctor.activity.visit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dhcc.calendar.CollapseCalendarView;
import com.dhcc.calendar.manager.CalendarManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.dateorder.OrderRegData;
import com.imedical.mobiledoctor.entity.dateorder.TimeRange;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DateVisitMulitActivity extends BaseActivity {
	private String startDate, endDate;
    private CollapseCalendarView mCalendarView;
	private LayoutInflater mInflater;
	private String nowDate;
	private TextView tv_newsche;
	private CalendarManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_activity_datevisitmulit);
        setTitle("日历预约");
		/*
		 * 新增排班
		 */
		tv_newsche=(TextView) findViewById(R.id.tv_newsche);
		tv_newsche.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i=new Intent(DateVisitMulitActivity.this, SetDoctorScheduleActivity.class);
				i.putExtra("scheduleDate", nowDate);
				startActivity(i);
			}
		});
        manager = new CalendarManager(LocalDate.now(), CalendarManager.State.MONTH, LocalDate.now().plusYears(-1), LocalDate.now().plusYears(1));
        mCalendarView = (CollapseCalendarView) findViewById(R.id.calendar);
		mCalendarView.setListener(new CollapseCalendarView.OnDateSelect() {

			@Override
			public void onMonthChange(LocalDate fromDate, LocalDate toDate) {
			}

			@Override
			public void onDateSelected(LocalDate date, Boolean isToday) {
				nowDate=date.toString();
				tv_newsche.setEnabled(true);
				tv_newsche.setBackgroundResource(R.drawable.button_shape_blue_gradient);
			}
		});
        if(Const.loginInfo!=null)
        {
        	mCalendarView.init(manager);
        }
    }


	protected void onResume() {
		super.onResume();
	}

}