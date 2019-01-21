package com.imedical.mobiledoctor.activity.visit;

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
import com.imedical.mobiledoctor.entity.dateorder.DoctorSchedule;
import com.imedical.mobiledoctor.entity.dateorder.TimeRange;
import com.imedical.mobiledoctor.util.Validator;

import java.util.ArrayList;
import java.util.List;

public class UpdateDoctorScheduleActivity extends BaseActivity {
    public View tv_save;
    public DoctorSchedule doctorSchedule;
    public EditText et_regLimit;
    public TextView tv_timeRangeDesc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit_activity_updatedoctorschedule);
        doctorSchedule = (DoctorSchedule)getIntent().getSerializableExtra("doctorSchedule");
        setTitle("修改图文咨询排班限额");
        intiView();
    }

    public void intiView() {
        et_regLimit = findViewById(R.id.et_regLimit);
        et_regLimit.setText(doctorSchedule.regLimit);
        tv_timeRangeDesc = findViewById(R.id.tv_timeRangeDesc);
        tv_timeRangeDesc.setText(doctorSchedule.timeRangeDesc+"   "+doctorSchedule.startTime+"-"+doctorSchedule.endTime);
        tv_save = findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validator.isBlank(et_regLimit.getText().toString())) {
                    showToast("请正确填写号源数");
                } else {
                    try {
                        int num = Integer.parseInt(et_regLimit.getText().toString());
                        if (num >= 0) {
                            saveData(num);
                        } else {
                            showToast("请正确填写号源数,不能为负数");
                        }
                    } catch (Exception e) {
                        showToast("请正确填写号源数" + e.getMessage());
                    }
                }
            }
        });
    }


    public void saveData(final int num) {
        showProgress();
        new Thread() {
            String msg = "";

            @Override
            public void run() {
                super.run();
                try {
                    BaseBean baseBean = DateOrderManager.UpdateOneSchedule(Const.DeviceId, Const.loginInfo.userCode, doctorSchedule.scheduleId, num+"");
                    if (baseBean.getResultCode().equals("0")) {
                        msg = "修改成功";
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
}
