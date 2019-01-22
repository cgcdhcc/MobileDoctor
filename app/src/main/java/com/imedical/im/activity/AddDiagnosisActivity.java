package com.imedical.im.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.util.Validator;

import java.util.List;

public class AddDiagnosisActivity extends BaseActivity {
    public String admId;
    public TextView tv_patientName, tv_patientAge, tv_patientCard, tv_patientId, tv_doctorName, tv_departmentName, tv_doctorTitle,tv_save;
    public EditText et_msgContent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId=this.getIntent().getStringExtra("admId");
        this.setContentView(R.layout.activity_add_diagnosis_record);
        setTitle("诊疗方案");
        intiView();
        loadData();
    }

    public void intiView() {
        et_msgContent=findViewById(R.id.et_msgContent);
        tv_patientName = findViewById(R.id.tv_patientName);
        tv_patientAge = findViewById(R.id.tv_patientAge);
        tv_patientCard = findViewById(R.id.tv_patientCard);
        tv_patientId = findViewById(R.id.tv_patientId);
        tv_doctorName = findViewById(R.id.tv_doctorName);
        tv_departmentName = findViewById(R.id.tv_departmentName);
        tv_doctorTitle = findViewById(R.id.tv_doctorTitle);
        tv_save= findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msgContent=et_msgContent.getText()==null?"":et_msgContent.getText().toString();
                if(!Validator.isBlank(msgContent)){
                    saveData(msgContent);
                }else{
                    showToast("请填写诊疗建议");
                }
            }
        });
    }

    public void intiData(AdmInfo admInfo) {
        et_msgContent.setText(admInfo.doctorContent);
        tv_patientName.setText(admInfo.patientName);
        tv_patientAge.setText(admInfo.patientAge + " | " + admInfo.patientSex);
        tv_patientCard.setText(admInfo.patientCard);
        tv_patientId.setText(admInfo.patientId);
        tv_doctorName.setText(admInfo.doctorName);
        tv_departmentName.setText(admInfo.departmentName);//
        tv_doctorTitle.setText(admInfo.doctorTitle);//
    }
    public void loadData() {
        showProgress();
        new Thread() {
            List<AdmInfo> templist;
            String msg = "加载失败了";

            @Override
            public void run() {
                super.run();
                try {
                    templist = AdmManager.GetAdmInfo(Const.DeviceId, Const.loginInfo.userCode, admId);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (templist != null && templist.size() > 0) {
                            intiData(templist.get(0));
                            dismissProgress();
                        } else {
                            dismissProgress();
                            showToast(msg);
                            finish();
                        }
                    }
                });
            }
        }.start();
    }

    public void saveData(final String msgContent) {
        showProgress();
        new Thread() {
            String msg = "";
            BaseBean baseBean;
            @Override
            public void run() {
                super.run();
                try {
                    baseBean = AdmManager.UpdateDoctorContentByAdm(Const.DeviceId, admId, msgContent);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (baseBean!=null&&baseBean.getResultCode().equals("0")) {
                            msg = "保存成功";
                            setResult(100,getIntent());
                            finish();
                        } else {
                            if(baseBean!=null){
                                msg = baseBean.getResultDesc();
                            }
                        }
                        showToast(msg);
                    }
                });
            }
        }.start();
    }

}
