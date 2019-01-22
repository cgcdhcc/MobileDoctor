package com.imedical.mobiledoctor.activity.visit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.XMLservice.UserManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.Schedule;
import com.imedical.mobiledoctor.entity.dateorder.OrderDate;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.WeekDate;
import com.imedical.mobiledoctor.widget.WrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dashan on 2017/5/24.
 */

public class DoctorScheduleActivity extends BaseActivity {
    public List<Schedule> list_data=new ArrayList<Schedule>();
    public WrapContentHeightViewPager viewpager;
    public ScheduleAdapter sadapter;
    public String curDay= DateUtil.getDateToday(null);
    public PAdapter adapter;
    public ListView lv_data;
    public TextView tv_today;
    public HashMap<String,OrderDate> datamap=new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_doctor_schedule);
        this.setTitle("出诊信息");
        tv_today=(TextView)findViewById(R.id.tv_today);
        tv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curDay=DateUtil.getDateToday(null);
                viewpager.setCurrentItem(10);
                adapter.notifyDataSetChanged();
                loadData(curDay,curDay);
            }
        });
        viewpager=(WrapContentHeightViewPager) findViewById(R.id.viewpager);
        adapter=new PAdapter();
        viewpager.setAdapter(adapter);
        sadapter=new ScheduleAdapter();
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                list_data.clear();
                sadapter.notifyDataSetChanged();
                List<WeekDate> list= DateUtil.getOneWeek(i-10);
                loadDateData(list.get(0).date, list.get(6).date);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewpager.setCurrentItem(10);
        lv_data=(ListView)findViewById(R.id.lv_data);
        lv_data.setAdapter(sadapter);
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(DoctorScheduleActivity.this,PatientAppScheduleActivity.class);
                intent.putExtra("schedule",list_data.get(i));
                startActivity(intent);
            }
        });
        loadData(DateUtil.getDateToday(null),DateUtil.getDateToday(null));
    }


    public void loadData(final String startdate, final String enddate){
        showProgress();
        list_data.clear();
        new Thread(){
            @Override
            public void run() {
                super.run();

                try{
                    List<Schedule> list= UserManager.GetScheduleList(Const.loginInfo.userCode,startdate,enddate);
                    if(list!=null){
                        if(list.size()>0){
                            UserManager.GetDoctorAppList(Const.loginInfo.userCode,list.get(0).scheduleCode);
                        }
                        list_data.addAll(list);
                    }

                    Log.d("msg","list_data:"+list_data.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sadapter.notifyDataSetChanged();
                        dismissProgress();
                    }
                });
            }
        }.start();

    }
    public void loadDateData(final String startdate, final String enddate){
        showProgress();
        list_data.clear();
        new Thread(){
            @Override
            public void run() {
                super.run();

                try{
                    List<OrderDate> OrderDate_list = DateOrderManager.selectDate(Const.loginInfo.userCode, "", startdate,
                            enddate);
                    for(int i=0;i<OrderDate_list.size();i++){
                        datamap.put(OrderDate_list.get(i).regDate, OrderDate_list.get(i));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        dismissProgress();
                    }
                });
            }
        }.start();

    }
    public class PAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view=getLayoutInflater().inflate(R.layout.item_clenderdate, null);
            LinearLayout ll_date=(LinearLayout)view.findViewById(R.id.ll_date);
            TextView tv_month=(TextView)view.findViewById(R.id.tv_month);
            tv_month.setText("");

            List<WeekDate> list= DateUtil.getOneWeek(position-10);
            tv_month.setText(list.get(0).getDate().substring(0,7));
            ll_date.removeAllViews();
            for(int i=0;i<list.size();i++){
               View dview=getLayoutInflater().inflate(R.layout.item_date_view, null);
                TextView tv_week=(TextView)dview.findViewById(R.id.tv_week);
                tv_week.setText(list.get(i).getWeek());
                TextView tv_date=(TextView)dview.findViewById(R.id.tv_date);
                tv_date.setText(list.get(i).getShortdate());

                View view_status=dview.findViewById(R.id.view_status);
                ImageView iv_status=dview.findViewById(R.id.iv_status);


                if(list.get(i).getDate().equals(curDay)){
                    view_status.setBackgroundResource(R.drawable.bg_shape_blue);
                    tv_date.setTextColor(getResources().getColor(R.color.white));
                    if(datamap.containsKey(list.get(i).getDate())){//有排班
                        iv_status.setVisibility(View.VISIBLE);
                        iv_status.setImageResource(R.drawable.img_shape_white_dot);
                    }else{
                        iv_status.setVisibility(View.INVISIBLE);
                    }
                }else{
                    view_status.setBackgroundResource(R.color.white);
                    tv_date.setTextColor(getResources().getColor(R.color.text_base));
                    if(datamap.containsKey(list.get(i).getDate())){//有排班
                        iv_status.setVisibility(View.VISIBLE);
                        iv_status.setImageResource(R.drawable.img_shape_blue_dot);
                    }else{
                        iv_status.setVisibility(View.INVISIBLE);
                    }
                }
                if(list.get(i).getDate().equals(DateUtil.getDateToday(null))&&!curDay.equals(DateUtil.getDateToday(null))){//今日
                    tv_date.setTextColor(getResources().getColor(R.color.basecolor));
                }
                final String day=list.get(i).getDate();
                view_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        curDay=day;
                        loadData(day,day);
                        adapter.notifyDataSetChanged();
                    }
                });
                dview.setLayoutParams(new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                ll_date.addView(dview);
            }
            container.addView(view);
            return view;
        }
        @Override

        public int getItemPosition(Object object)   {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        @Override
        public int getCount()
        {
            return 21;
        }

        @Override
        public boolean isViewFromObject(View view, Object o)
        {
            return view == o;
        }
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
            tv_scheduleDate.setText(list_data.get(i).scheduleDate);

            TextView tv_sessionName=(TextView)view.findViewById(R.id.tv_sessionName);
            tv_sessionName.setText(list_data.get(i).sessionName);

            TextView tv_weekDay=(TextView)view.findViewById(R.id.tv_weekDay);
            tv_weekDay.setText(list_data.get(i).weekDay);

            TextView tv_departmentName=(TextView)view.findViewById(R.id.tv_departmentName);
            tv_departmentName.setText(list_data.get(i).departmentName);
            TextView tv_appedNum=(TextView)view.findViewById(R.id.tv_appedNum);
            tv_appedNum.setText(list_data.get(i).appedNum);
            TextView tv_availableNum=(TextView)view.findViewById(R.id.tv_availableNum);
            tv_availableNum.setText(list_data.get(i).availableNum);

            TextView tv_status=(TextView)view.findViewById(R.id.tv_status);
            if("0".equals(list_data.get(i).availableNum)){
                tv_status.setText("已约满");
                tv_status.setTextColor(getResources().getColor(R.color.orange));
            }else{
                tv_status.setText("已预约数/剩余可预约数");
                tv_status.setTextColor(getResources().getColor(R.color.text_grayblack));
            }


            return view;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }
    }

}
