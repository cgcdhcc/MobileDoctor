package com.imedical.mobiledoctor.activity.round;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.base.BaseRoundActivity;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;

public class PatientInfoActivity  extends BaseRoundActivity {
    private View layout_content;
    private TextView tv_condition;
    private TextView tv_careLevel;
    private TextView tv_inDays;
    private TextView tv_inDate;
    private TextView tv_departmentName;
    private TextView tv_allergies;
    private TextView tv_mainDoctor;
    private TextView tv_bloodType;
    private TextView tv_patName;
    private TextView tv_bedCode;
    private TextView tv_patSex;
    private TextView tv_patAge;
    private TextView tv_occupation;
    private TextView tv_wardDesc;
    private TextView tv_payType;
    private TextView tv_total;
    private TextView tv_deposit;
    private TextView tv_patShare,tv_patRegNo,tv_balance;
    PatientInfo P_info=null;
    LoginInfo mLogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page1_patientinfo_activity);
        InitViews();
        loadData();
        InitRecordListAndPatientList(PatientInfoActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SWITHC_CODE) {
            loadData();
        }
        setInfos(Const.curPat.patName,(Const.curPat.bedCode==null?"":Const.curPat.bedCode)+"("+(Const.curPat.patRegNo==null?"":Const.curPat.patRegNo)+")");//更新姓名，床号
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void OnPatientSelected(PatientInfo p) {
        Intent it0 =new Intent(PatientInfoActivity.this,PatientListActivity.class);
        startActivityForResult(it0, SWITHC_CODE);
    }

    @Override
    public void OnRecordSelected(SeeDoctorRecord sr) {
        loadData();
    }

    private void InitViews() {
        mLogin = Const.loginInfo;
        setTitle("患者信息");
        tv_balance= (TextView) findViewById(R.id.tv_balance);
        tv_bedCode = (TextView) findViewById(R.id.tv_bedCode);
        tv_patRegNo = (TextView) findViewById(R.id.tv_patRegNo);
        tv_patName = (TextView) findViewById(R.id.tv_patName);
        tv_patSex = (TextView) findViewById(R.id.tv_patSex);
        tv_patAge = (TextView) findViewById(R.id.tv_patAge);
        tv_bloodType = (TextView) findViewById(R.id.tv_bloodType);
        tv_occupation = (TextView) findViewById(R.id.tv_occupation);
        tv_allergies = (TextView) findViewById(R.id.tv_allergies);
        tv_departmentName = (TextView) findViewById(R.id.tv_departmentName);
        tv_mainDoctor = (TextView) findViewById(R.id.tv_mainDoctor);
        tv_inDate = (TextView) findViewById(R.id.tv_inDate);
        tv_inDays = (TextView) findViewById(R.id.tv_inDays);
        tv_careLevel = (TextView) findViewById(R.id.tv_careLevel);
        tv_condition = (TextView) findViewById(R.id.tv_condition);
        tv_wardDesc = (TextView) findViewById(R.id.tv_wardDesc);
        layout_content = findViewById(R.id.layout_content);
        tv_payType = (TextView) findViewById(R.id.tv_payType);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_patShare = (TextView) findViewById(R.id.tv_patShare);
    }

    private void initUIValue(PatientInfo bean) {

        if (bean == null) {
            Log.e("###error#####", "PatientInfo：" + bean);
            return;
        }
        tv_bedCode.setText(bean.bedCode);
        tv_patRegNo.setText(bean.patRegNo);
        tv_patName.setText(bean.patName);
        tv_patSex.setText(bean.patSex);
        tv_patAge.setText(bean.patAge);
        tv_bloodType.setText(bean.bloodType);
        tv_occupation.setText(bean.occupation);
        tv_allergies.setText(bean.allergies);
        tv_departmentName.setText(bean.departmentName);
        tv_mainDoctor.setText(bean.mainDoctor);
        tv_inDate.setText(bean.inDate);
        tv_inDays.setText(bean.inDays);
        tv_careLevel.setText(bean.careLevel);
        tv_condition.setText(bean.condition);
        tv_wardDesc.setText(bean.wardDesc);
        layout_content.setVisibility(View.VISIBLE);
        tv_payType.setText(bean.payType);
        tv_deposit.setText(bean.deposit);
        tv_total.setText(bean.total);
        tv_patShare.setText(bean.patShare);
//		mActivity.SetMyTitle(bean.patName);
    }

    private void loadData() {
     showProgress();
        new Thread() {
            public void run() {
                try {
                    if (Const.curPat != null) {
                        P_info= BusyManager.loadPatientInfo(mLogin.userCode, Const.curPat.admId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                myHandler.dispatchMessage(new Message());
            };
        }.start();
    }

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            dismissProgress();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initUIValue(P_info);
                }
            });
            super.handleMessage(msg);
        }

    };
}
