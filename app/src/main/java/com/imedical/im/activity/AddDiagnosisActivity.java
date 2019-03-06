package com.imedical.im.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.ArcimItemManager;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.XMLservice.DocOrderService;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.util.Validator;

import java.util.ArrayList;
import java.util.List;

public class AddDiagnosisActivity extends BaseActivity {
    public String admId,callCode="0";
    public TextView tv_patientName, tv_patientAge, tv_patientCard, tv_doctorName, tv_departmentName, tv_doctorTitle,tv_save,tv_input;
    public EditText et_msgContent;
    public LinearLayout ll_content;
    public List<ArcimItem> list_data = new ArrayList<ArcimItem>();
    public boolean needLoad=false;
    private AdmInfo temAI;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId=this.getIntent().getStringExtra("admId");
        callCode=this.getIntent().getStringExtra("callCode");
        this.setContentView(R.layout.activity_add_diagnosis_record);
        setTitle("诊疗方案");
        intiView();
        loadData();
    }

    public void intiView() {
        tv_input=findViewById(R.id.tv_input);
        ll_content=findViewById(R.id.ll_content);
        et_msgContent=findViewById(R.id.et_msgContent);
        tv_patientName = findViewById(R.id.tv_patientName);
        tv_patientAge = findViewById(R.id.tv_patientAge);
        tv_patientCard = findViewById(R.id.tv_patientCard);
        tv_doctorName = findViewById(R.id.tv_doctorName);
        tv_departmentName = findViewById(R.id.tv_departmentName);
        tv_doctorTitle = findViewById(R.id.tv_doctorTitle);
        tv_save= findViewById(R.id.tv_save);
        if(callCode.equals("3")){
            tv_save.setVisibility(View.GONE);
            et_msgContent.setEnabled(false);
        }else {
            tv_save.setVisibility(View.VISIBLE);
            et_msgContent.setEnabled(true);
        }
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
        tv_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(AddDiagnosisActivity.this,OETabsActivity.class);
                it.putExtra("tempAI",temAI);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needLoad){
            loadArcimItemData();
        }else{
            needLoad=true;
        }

    }
    public void intiData(AdmInfo admInfo) {
        et_msgContent.setText(admInfo.doctorContent);
        tv_patientName.setText(admInfo.patientName);
        tv_patientAge.setText(admInfo.patientAge + " | " + admInfo.patientSex);
        tv_patientCard.setText(admInfo.patientId);
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
                            temAI=templist.get(0);
                            intiData(temAI);
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
                            if(list_data.size()>0){
                                veryfy();
                            }
//                            setResult(100,getIntent());
//                            finish();
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


    public void loadArcimItemData() {
        list_data.clear();
        showProgress();
        new Thread() {
            List<ArcimItem> list;

            @Override
            public void run() {
                super.run();
                try {
                    list = DocOrderService.getInstance().loadArcimItemListSaved(Const.loginInfo.userCode, temAI.admId);
                    for (int i = list.size() - 1; i >-1; i--) {
                        if (("核实").equals(list.get(i).ordState)) {
                            list.remove(i);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (list == null) {
                            showToast("医嘱列表加载失败");
                        } else {
                            list_data.addAll(list);
                            intiArcimItemView();
                        }
                    }
                });

            }
        }.start();
    }
    public void intiArcimItemView() {
        ll_content.removeAllViews();
        for (int i = 0; i < list_data.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.item_temp_order_view, null);
            TextView tv_arcimDesc = (TextView) view.findViewById(R.id.tv_arcimDesc);
            tv_arcimDesc.setText(list_data.get(i).arcimDesc);
            final int num = i;
            TextView tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddDiagnosisActivity.this, DocOrderActivity.class);
                    intent.putExtra("showIndex", list_data.get(num).showIndex);
                    intent.putExtra("tempAI",temAI);
                    startActivity(intent);
                }
            });

            TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDiagnosisActivity.this);
                    builder.setMessage("确定要删除此医嘱项吗？")
                            .setTitle("提示信息")
                            .setCancelable(false)
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            delArcimItemSyn(list_data.get(num).showIndex);
                                        }
                                    })
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                        }
                                    }).show();
                }
            });
            ll_content.addView(view);
        }
    }

    //删除医嘱项
    public void delArcimItemSyn(final String showIndex) {
        showProgress();
        new Thread() {
            private String mInfo;

            public void run() {
                try {
                    BaseBean bb = DocOrderService.delete(Const.loginInfo.userCode,
                            temAI.admId, showIndex);
                    mInfo = bb.getResultDesc();
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(mInfo);
                        dismissProgress();
                        loadArcimItemData();// 重新加载
                    }
                });
            }

            ;
        }.start();


    }

    //审核医嘱
    private void veryfy() {
        new Thread() {
            String mInfo="";
            BaseBean b;
            public void run() {
                try {
                    b = ArcimItemManager.verifyArcimItem(
                            Const.loginInfo.userCode,temAI.admId, Const.loginInfo.defaultDeptId);
                    if(b!=null){
                        mInfo = b.getResultDesc();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mInfo = e.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(b!=null&&b.getResultCode().equals("0")){
                            showToast("医嘱审核成功");
                        }else {
                            showToast(mInfo);
                        }
                    }
                });
            };
        }.start();

    }


}
