package com.imedical.im.activity;

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
import com.imedical.mobiledoctor.XMLservice.DocOrderService;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.util.Validator;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisListActivity extends BaseActivity {
    public String admId,callCode="0";
    public TextView tv_patientName, tv_patientAge, tv_patientCard, tv_doctorName, tv_departmentName, tv_submit,tv_save,tv_input;
    public EditText et_msgContent;
    public LinearLayout ll_content;
    public List<ArcimItem> list_data = new ArrayList<ArcimItem>();
    public boolean needLoad=false;
    private AdmInfo temAI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId=this.getIntent().getStringExtra("admId");
        callCode=this.getIntent().getStringExtra("callCode")==null?"0":this.getIntent().getStringExtra("callCode");
        temAI=(AdmInfo)this.getIntent().getSerializableExtra("tempAI");
        this.setContentView(R.layout.activity_diagnosis_list);
        setTitle("请录入医嘱");
        intiView();
    }

    public void intiView() {
        tv_input=findViewById(R.id.tv_input);
        tv_submit=findViewById(R.id.tv_submit);
        ll_content=findViewById(R.id.ll_content);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DiagnosisListActivity.this);
                builder.setMessage("确认后，将完成医嘱审核，检查项目将需要选择预约时间，是否确认？")
                        .setTitle("确认审核")
                        .setCancelable(false)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        veryfy();
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
        tv_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(DiagnosisListActivity.this,OETabsActivity.class);
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
                    Intent intent = new Intent(DiagnosisListActivity.this, DocOrderActivity.class);
                    intent.putExtra("showIndex", list_data.get(num).showIndex);
                    intent.putExtra("tempAI",temAI);
                    startActivity(intent);
                }
            });

            TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DiagnosisListActivity.this);
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
        showProgress();
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
                        dismissProgress();
                        if(b!=null&&b.getResultCode().equals("0")){
                            showCustom("医嘱审核成功");
                            list_data.clear();
                            ll_content.removeAllViews();
                            Intent it =new Intent(DiagnosisListActivity.this,ReservationListActivity.class);
                            startActivity(it);
                            finish();
                        }else {
                            showCustom(mInfo);
                        }
                    }
                });
            };
        }.start();

    }


}
