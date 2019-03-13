package com.imedical.mobiledoctor.activity.visit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.dateorder.TimeRange;
import com.imedical.mobiledoctor.util.Validator;
import com.imedical.mobiledoctor.widget.LCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class SetDoctorScheduleActivity extends BaseActivity {
    public LinearLayout ll_data;
    public View tv_save;
    public List<TimeRange> data_list = new ArrayList<>();
    public String scheduleDate;
    public int currentChecked = -1;
    private LCalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private boolean isMuiltSeleced=false;//是多选日期吗
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_activity_setdoctorschedule);
        scheduleDate = getIntent().getStringExtra("scheduleDate");
        isMuiltSeleced=getIntent().getBooleanExtra("isMuiltSeleced",false);
        setTitle("图文咨询排班");
        intiView();
        loadTimeRange();
    }


    public void intiView() {
        calendar = (LCalendarView)findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选

        ll_data = findViewById(R.id.ll_data);
        tv_save = findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentChecked > -1) {
                    saveData();
                } else {
                    showToast("请先选择一个排班时间段");
                }
            }
        });
    }

    public void intiTimeRangeView(){
        ll_data.removeAllViews();
        for(int i=0;i<data_list.size();i++){
            final int position = i;
            View view = getLayoutInflater().inflate(R.layout.visit_item_setdoctorschedule, null);
            CheckBox cb_status = view.findViewById(R.id.cb_status);
            final EditText et_regLimit = view.findViewById(R.id.et_regLimit);
            cb_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.d("msg", "选中设置可用");
                        CheckBox oldCb=null;
                        if(currentChecked>-1){
                            oldCb = ll_data.getChildAt(currentChecked).findViewById(R.id.cb_status);
                        }
                        currentChecked = position;
                        et_regLimit.setEnabled(true);
                        et_regLimit.setFocusable(true);
                        et_regLimit.setFocusableInTouchMode(true);
                        et_regLimit.requestFocus();
                        if(oldCb!=null){
                            oldCb.setChecked(false);
                        }
                    }else{
                        Log.d("msg", "取消选中设置不可用");
                        et_regLimit.setText("0");
                        et_regLimit.setFocusable(false);
                        et_regLimit.setEnabled(false);
                        if(currentChecked == position){
                            currentChecked =-1;
                        }
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
                    if (Validator.isBlank(et_regLimit.getText().toString())) {
                        data_list.get(position).regLimit = "0";
                    } else {
                        try {
                            int num = Integer.parseInt(et_regLimit.getText().toString());
                            if (num >= 0) {
                                data_list.get(position).regLimit = et_regLimit.getText().toString();
                            } else {
                                showToast("请正确填写号源数,不能为负数");
                            }
                        } catch (Exception e) {
                            showToast("请正确填写号源数" + e.getMessage());
                        }
                    }
                }
            });
            et_regLimit.setText(data_list.get(position).regLimit);
            et_regLimit.setEnabled(false);
            et_regLimit.setFocusable(false);
            TextView tv_timeRangeDesc = view.findViewById(R.id.tv_timeRangeDesc);
            tv_timeRangeDesc.setText(data_list.get(position).timeRangeDesc+"    "+data_list.get(position).startTime+"-"+data_list.get(position).endTime);
            ll_data.addView(view);
        }
    }

    public void saveData() {
        showProgress();
        new Thread() {
            String msg = "";

            @Override
            public void run() {
                super.run();
                BaseBean baseBean=null;
                try {
                    if(!isMuiltSeleced){//单选
                         baseBean = DateOrderManager.DoctorSchedule(Const.DeviceId, Const.loginInfo.userCode, data_list.get(currentChecked).timeRangeId, data_list.get(currentChecked).regLimit, "T", scheduleDate);
                    }else {
                         baseBean = DateOrderManager.DoctorScheduleMuiltSelect(Const.DeviceId, Const.loginInfo.userCode, data_list.get(currentChecked).timeRangeId, data_list.get(currentChecked).regLimit, "T", scheduleDate);
                    }
                    if (baseBean.getResultCode().equals("0")) {
                        msg = "设置成功";
                    } else {
                        msg = baseBean.getResultDesc();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        showToast(msg);
                        setResult(100,getIntent() );
                        finish();
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
                        intiTimeRangeView();
                    }
                });
            }
        }.start();
    }
}
