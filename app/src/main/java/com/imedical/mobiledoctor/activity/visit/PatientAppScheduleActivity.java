package com.imedical.mobiledoctor.activity.visit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.UserManager;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.OPRegisterInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.Schedule;
import com.imedical.mobiledoctor.entity.homegrid.funcState;
import com.imedical.mobiledoctor.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dashan on 2017/5/24.
 */

public class PatientAppScheduleActivity extends BaseActivity {
    public List<OPRegisterInfo> list_data=new ArrayList<OPRegisterInfo>();
    public RegisterAdapter sadapter;
    public ListView lv_data;
    public Schedule schedule;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_patientapp_schedule);
        schedule=(Schedule)getIntent().getSerializableExtra("schedule");
        this.setTitle(schedule.scheduleDate +"  "+schedule.sessionName);

        lv_data=(ListView)findViewById(R.id.lv_data);
        sadapter=new RegisterAdapter();
        lv_data.setAdapter(sadapter);
        loadData();
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(Validator.isBlank(list_data.get(position).admId)){
                   return;
                }
                PatientInfo patientInfo=new PatientInfo();
                patientInfo.admId=list_data.get(position).admId;
                patientInfo.admType="O";
                patientInfo.patName=list_data.get(position).patientName;
                patientInfo.inDate=list_data.get(position).admitDate;
                Const.curPat=patientInfo;
                Const.SRecorderList=null;
                Const.curSRecorder=null;
                Intent intent = new Intent(PatientAppScheduleActivity.this, WardRoundActivity.class);
                startActivity(intent);
            }
        });
    }
    public void loadData(){
        showProgress();
        list_data.clear();
        new Thread(){
            @Override
            public void run() {
                super.run();

                try{
                    List<OPRegisterInfo> list= UserManager.GetDoctorAppList(Const.loginInfo.userCode,schedule.scheduleCode);
                    if(list!=null){
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


    public class RegisterAdapter extends BaseAdapter {
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
            view=getLayoutInflater().inflate(R.layout.item_registerinfo_view,null);
            TextView tv_patientName=(TextView)view.findViewById(R.id.tv_patientName);
            tv_patientName.setText(list_data.get(i).patientName);

            TextView tv_age=(TextView)view.findViewById(R.id.tv_age);
            tv_age.setText(list_data.get(i).age+"岁");

            TextView tv_admitTimeRange=(TextView)view.findViewById(R.id.tv_admitTimeRange);
            tv_admitTimeRange.setText(list_data.get(i).admitTimeRange);

            TextView tv_seqCode=(TextView)view.findViewById(R.id.tv_seqCode);
            tv_seqCode.setText(list_data.get(i).seqCode);

            ImageView iv_sex=(ImageView)view.findViewById(R.id.iv_sex);
            if(list_data.get(i).sex.equals("女")){
                iv_sex.setImageResource(R.drawable.all_famale);
            }else{
                iv_sex.setImageResource(R.drawable.all_male);
            }
            View view_recorder=view.findViewById(R.id.view_recorder);
            if(Validator.isBlank(list_data.get(i).admId)){
                view_recorder.setVisibility(View.INVISIBLE);
            }else{
                view_recorder.setVisibility(View.VISIBLE);
            }
            return view;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }
    }

}
