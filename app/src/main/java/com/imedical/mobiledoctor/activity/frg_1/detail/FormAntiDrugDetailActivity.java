package com.imedical.mobiledoctor.activity.frg_1.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.AntAppData;
import com.imedical.mobiledoctor.entity.AntAppInfo;
import com.imedical.mobiledoctor.entity.BaseBean;


public class FormAntiDrugDetailActivity extends BaseActivity {

    public AntAppInfo ci;
    public String mInfo = "";
    public AntAppData antAppData;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_drug_detail);
        setTitle("抗生素申请详情");
        antAppData = (AntAppData) getIntent().getExtras().getSerializable("antAppData");
        loadConsultDetail();
    }

    public void initView() {

        TextView tv_arcimDesc = (TextView) findViewById(R.id.tv_arcimDesc);
        tv_arcimDesc.setText(ci.arcimDesc.replaceAll("] \\[", "]\n["));

        TextView tv_appDocName = (TextView) findViewById(R.id.tv_appDocName);
        tv_appDocName.setText(ci.appDocName);


        TextView tv_bodyDesc = (TextView) findViewById(R.id.tv_bodyDesc);
        tv_bodyDesc.setText(ci.bodyDesc);


        TextView tv_submiteFlag = (TextView) findViewById(R.id.tv_submiteFlag);
        tv_submiteFlag.setText(ci.submiteFlag);


        TextView tv_reasonDesc = (TextView) findViewById(R.id.tv_reasonDesc);
        tv_reasonDesc.setText(ci.reasonDesc);


        TextView tv_indicateDesc = (TextView) findViewById(R.id.tv_indicateDesc);
        tv_indicateDesc.setText(ci.indicateDesc);


        TextView tv_otherCause = (TextView) findViewById(R.id.tv_otherCause);
        tv_otherCause.setText(ci.otherCause);

        TextView tv_opTimeDesc = (TextView) findViewById(R.id.tv_opTimeDesc);
        tv_opTimeDesc.setText(ci.opTimeDesc);
        View view_operation = findViewById(R.id.view_operation);
        if ("申请".equals(antAppData.appStatus)) {
            view_operation.setVisibility(View.VISIBLE);
        } else {
            view_operation.setVisibility(View.GONE);
        }
        View tv_agree = findViewById(R.id.tv_agree);
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify("U");
            }
        });

        View tv_refuse = findViewById(R.id.tv_agree);
        tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify("R");
            }
        });


    }

    // 加载会诊详情
    private void loadConsultDetail() {
        showProgress();
        new Thread() {
            @Override
            public void run() {
                try {
                    ci = BusyManager.loadAntAppInfo(
                            Const.loginInfo.userCode,
                            Const.loginInfo.defaultDeptId, antAppData.antAppId);
                    // 对应的二级菜单集合
                } catch (Exception e) {
                    mInfo = e.getMessage();
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissProgress();
                            if (ci != null) {
                                initView();
                            } else {
                                showToast(mInfo);
                            }
                        }
                    });
                }
            }
        }.start();
    }

    private void verify(final String appStatus) {
        showProgress();
        new Thread() {
            BaseBean b = null;

            public void run() {
                try {
                    String antAppIds = antAppData.antAppId;
                    b = BusyManager.verifyApplyAntAppData(Const.loginInfo.userCode,
                            Const.loginInfo.defaultDeptId, antAppIds, appStatus);
                    mInfo = b.getResultDesc();
                } catch (Exception e) {
                    mInfo = "操作失败!\n" + e.getMessage();
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();

                        if (b != null) {
                            if ("0".equals(b.getResultCode())) {
                                showToast("操作成功!");
                                finish();
                            } else {
                                showToast(mInfo);
                            }
                        } else {
                            showToast(mInfo);
                        }
                    }
                });
            }

            ;
        }.start();
    }
}


