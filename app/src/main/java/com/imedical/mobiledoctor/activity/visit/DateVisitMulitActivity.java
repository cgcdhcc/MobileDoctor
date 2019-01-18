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
import com.imedical.mobiledoctor.entity.dateorder.DoctorSchedule;
import com.imedical.mobiledoctor.entity.dateorder.OrderRegData;
import com.imedical.mobiledoctor.entity.dateorder.TimeRange;
import com.imedical.mobiledoctor.util.DateUtil;

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
    private List<DoctorSchedule> list_data=new ArrayList<>();
    private ScheduleAdapter adapter;
    private ListView lv_data;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_activity_datevisitmulit);
        setTitle("图文咨询排班");
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
				if(nowDate.equals(DateUtil.getDateToday(null))){//今天
					lv_data.setVisibility(View.VISIBLE);
					GetDoctorSchedule();
				}else{
					lv_data.setVisibility(View.GONE);
				}
			}
		});
		adapter=new ScheduleAdapter();
		lv_data=findViewById(R.id.lv_data);
		lv_data.setAdapter(adapter);
		lv_data.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent=new Intent(DateVisitMulitActivity.this,UpdateDoctorScheduleActivity.class);
				intent.putExtra("doctorSchedule",list_data.get(i) );
				startActivity(intent);
			}
		});
        if(Const.loginInfo!=null)
        {
        	mCalendarView.init(manager);
        }
    }

	public void GetDoctorSchedule() {
		showProgress();
		new Thread() {
			List<DoctorSchedule> list;
			String msg = "";

			@Override
			public void run() {
				super.run();
				try {
					list = DateOrderManager.GetDoctorSchedule( Const.DeviceId,Const.loginInfo.userCode);
				} catch (Exception e) {
					e.printStackTrace();
					msg = e.getMessage();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgress();
						list_data.clear();
						if (list != null) {
							list_data.addAll(list);
						} else {
							showToast(msg);
						}
						adapter.notifyDataSetChanged();
					}
				});
			}
		}.start();
	}
	public class ScheduleAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return list_data.size();
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			view=getLayoutInflater().inflate(R.layout.item_schdule_view,null);
			TextView tv_scheduleDate=(TextView)view.findViewById(R.id.tv_scheduleDate);
			tv_scheduleDate.setText(list_data.get(i).timeRangeDesc);

			TextView tv_sessionName=(TextView)view.findViewById(R.id.tv_sessionName);
			tv_sessionName.setText(list_data.get(i).startTime+"-"+list_data.get(i).endTime);

			TextView tv_weekDay=(TextView)view.findViewById(R.id.tv_weekDay);
			tv_weekDay.setText("限额："+list_data.get(i).regLimit);

			TextView tv_departmentName=(TextView)view.findViewById(R.id.tv_departmentName);
			tv_departmentName.setText(list_data.get(i).doctorAlias);
			TextView tv_appedNum=(TextView)view.findViewById(R.id.tv_appedNum);
			tv_appedNum.setText(list_data.get(i).regNum);
			TextView tv_availableNum=(TextView)view.findViewById(R.id.tv_availableNum);
			tv_availableNum.setText(list_data.get(i).availableNum);

			TextView tv_status=(TextView)view.findViewById(R.id.tv_status);
			if("0".equals(list_data.get(i).availableNum)){
				tv_status.setText("已挂满");
				tv_status.setTextColor(getResources().getColor(R.color.orange));
			}else{
				tv_status.setText("已挂号数/剩余可挂号数");
				tv_status.setTextColor(getResources().getColor(R.color.text_grayblack));
			}
			return view;
		}

		@Override
		public Object getItem(int i) {
			return null;
		}
	}

	protected void onResume() {
		super.onResume();
	}

}