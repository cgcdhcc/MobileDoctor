package com.imedical.mobiledoctor.activity.visit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.dateorder.TimeRange;
import com.imedical.mobiledoctor.util.Validator;

import java.util.ArrayList;
import java.util.List;

public class SetDoctorScheduleActivity extends BaseActivity {
    public TimeRangeAdapter adapter;
    public ListView lv_data;
    public View tv_save;
    public List<TimeRange> data_list = new ArrayList<>();
    public String scheduleDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_activity_setdoctorschedule);
        scheduleDate=getIntent().getStringExtra("scheduleDate");
        setTitle("图文咨询排班");
        intiView();
        loadTimeRange();
    }

    public void intiView() {
        lv_data = findViewById(R.id.lv_data);
        adapter = new TimeRangeAdapter();
        lv_data.setAdapter(adapter);
        tv_save = findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public class TimeRangeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data_list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int position = i;
            view = getLayoutInflater().inflate(R.layout.visit_item_setdoctorschedule, null);
            CheckBox cb_status = view.findViewById(R.id.cb_status);
            final EditText et_regLimit = view.findViewById(R.id.et_regLimit);
            cb_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(data_list.get(position).isChecked != b){
                        data_list.get(position).isChecked = b;
                        notifyDataSetChanged();
                    }
                }
            });
            et_regLimit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(Validator.isBlank(et_regLimit.getText().toString())){
                        data_list.get(position).regLimit = "0";
                    }else{
                        try{
                            int num= Integer.parseInt(et_regLimit.getText().toString());
                            if(num>=0){
                                data_list.get(position).regLimit = et_regLimit.getText().toString();
                            }else{
                                showToast("请正确填写号源数,不能为负数");
                            }
                        }catch (Exception e){
                            showToast("请正确填写号源数"+e.getMessage());
                        }
                    }
                }
            });
            Log.d("msg","选中后刷新操作" );
            et_regLimit.setText(data_list.get(position).regLimit);
            cb_status.setChecked(data_list.get(position).isChecked);
            et_regLimit.setFocusable(data_list.get(position).isChecked);
            et_regLimit.setEnabled(data_list.get(position).isChecked);
            TextView tv_timeRangeDesc = view.findViewById(R.id.tv_timeRangeDesc);
            tv_timeRangeDesc.setText(data_list.get(position).timeRangeDesc);
            return view;
        }
    }

    public void saveData() {
        showProgress();
        new Thread() {
            int num=0;
            String msg="";
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < data_list.size(); i++) {
                    if (data_list.get(i).isChecked) {
                        try {
                            BaseBean baseBean = DateOrderManager.DoctorSchedule(Const.DeviceId, Const.loginInfo.userCode, data_list.get(i).timeRangeId, data_list.get(i).regLimit, "T", scheduleDate);
                            if(baseBean.getResultCode().equals("0")){
                                num++;
                                msg="设置成功";
                            }else {
                                msg=baseBean.getResultDesc();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            msg=e.getMessage();
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        showToast(msg);
                    }
                });
            }
        }.start();
    }

    public void loadTimeRange() {
        showProgress();
        new Thread() {
            List<TimeRange> list;
            String msg = "";

            @Override
            public void run() {
                super.run();
                try {
                    list = DateOrderManager.GetTimeRange(Const.loginInfo.userCode, Const.DeviceId);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        data_list.clear();
                        if (list != null) {
                            data_list.addAll(list);
                        } else {
                            showToast(msg);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }
}
