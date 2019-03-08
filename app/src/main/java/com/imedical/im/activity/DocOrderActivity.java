package com.imedical.im.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.imedical.im.dialog.DepartmentOperateDlg;
import com.imedical.im.dialog.LabSpecDlg;
import com.imedical.im.dialog.PriorityOperateDlg;
import com.imedical.im.entity.AdmInfo;
import com.imedical.im.entity.LabSpec;
import com.imedical.im.util.DiaLogUtil;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.ArcimItemManager;
import com.imedical.mobiledoctor.XMLservice.DocOrderService;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.DeptInfo;
import com.imedical.mobiledoctor.entity.FormArcimItem;
import com.imedical.mobiledoctor.entity.PopMessage;
import com.imedical.mobiledoctor.entity.Priority;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.util.Validator;

import java.util.List;

public class DocOrderActivity extends BaseActivity {
    public TextView tv_addorupdate;
    public ArcimItem arcimItem;
    public TextView tv_arcimDesc, tv_priorDesc, tv_itemPrice, tv_locDesc, tv_packQty, tv_startDate, tv_labSpec;
    public EditText tv_memo;
    public boolean isNewAdd = true;
    private AdmInfo tempAI;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.docorder_creat_activity);
        tempAI=(AdmInfo)this.getIntent().getSerializableExtra("tempAI");
        setTitle(tempAI.patientName + "-医嘱申请");
        tv_addorupdate = (TextView) findViewById(R.id.tv_addorupdate);
        tv_addorupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormArcimItem f = fillForm();
                checkAndLoadPopMessage(f);
            }
        });

        tv_arcimDesc = (TextView) findViewById(R.id.tv_arcimDesc);
        tv_priorDesc = (TextView) findViewById(R.id.tv_priorDesc);

        tv_itemPrice = (TextView) findViewById(R.id.tv_itemPrice);
        tv_locDesc = (TextView) findViewById(R.id.tv_locDesc);

        tv_packQty = (TextView) findViewById(R.id.tv_packQty);
        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_labSpec = (TextView) findViewById(R.id.tv_labSpec);
        tv_memo = (EditText) findViewById(R.id.tv_memo);
        tv_memo.clearFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();

        String showIndex = getIntent().getStringExtra("showIndex");//更新
        if (!Validator.isBlank(showIndex)) {
            loadSelectedArcimItem(showIndex);
            isNewAdd = false;
            tv_addorupdate.setText("修改");
        }
        String arcItemId = getIntent().getStringExtra("arcItemId");
        if (!Validator.isBlank(arcItemId)) {//新增
            loadData(arcItemId);
            isNewAdd = true;
            tv_addorupdate.setText("保存");
        }

    }

    public void intiUi() {
        tv_arcimDesc.setText(arcimItem.arcimDesc);
        tv_priorDesc.setText(arcimItem.priorDesc);
        tv_arcimDesc.setHint(arcimItem.arcimCode);
        tv_priorDesc.setHint(arcimItem.getPriorId());
        tv_itemPrice.setText(arcimItem.itemPrice);
        if (arcimItem.labSpecList != null && arcimItem.labSpecList.size() > 0) {
            for(int i=0;i<arcimItem.labSpecList.size();i++){
                if(arcimItem.labSpecList.get(i).defaultFlag.equals("Y")){
                    tv_labSpec.setText(arcimItem.labSpecList.get(i).labSpec == null ? "" : arcimItem.labSpecList.get(i).labSpec);
                    tv_labSpec.setHint(arcimItem.labSpecList.get(i).labSpecCode == null ? "" : arcimItem.labSpecList.get(i).labSpecCode);
                }
            }
            tv_labSpec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LabSpecDlg dlg = new LabSpecDlg(DocOrderActivity.this, arcimItem.labSpecList);
                    dlg.show();
                }
            });
        }
        DeptInfo d = arcimItem.getDeptDefaultSelected();
        if (d != null) {
            tv_locDesc.setText(d.locDesc);
            tv_locDesc.setHint(d.locId);
        } else {
            tv_locDesc.setText("");
            tv_locDesc.setHint("");
        }

        if (Validator.isBlank(arcimItem.packQty)) {
            tv_packQty.setText("1");
        } else {
            tv_packQty.setText(arcimItem.packQty);
        }
        tv_startDate.setText(Validator.isBlank(arcimItem.getOrdStartDate()) ? DateUtil.getNowDateTime("yyyy-MM-dd HH:mm") : (arcimItem.getOrdStartDate() + " " + arcimItem.getOrdStartTime()));
        tv_memo.setText(arcimItem.ordNote == null ? "" : arcimItem.ordNote);
        tv_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DateTimePickDialogUtil(DocOrderActivity.this)
                        .dateTimePicKDialog(tv_startDate.getText().toString(), new MyCallback() {
                            @Override
                            public void onCallback(Object value) {
                                String dateTime = value.toString();
                                tv_startDate.setText(dateTime);
                            }
                        });
            }
        });

        tv_locDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arcimItem != null) {
                    DepartmentOperateDlg dlg = new DepartmentOperateDlg(DocOrderActivity.this, arcimItem.recLocList);
                    dlg.show();
                }

            }
        });
        tv_priorDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arcimItem != null) {
                    loadPriority();
                }

            }
        });

    }


    public void loadData(final String arcItemId) {
        showProgress();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    arcimItem = DocOrderService.getInstance().loadArcimItemById(Const.loginInfo.userCode, tempAI.admId, arcItemId,Const.loginInfo.defaultDeptId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (arcimItem == null) {
                            showToast("医嘱模板信息加载失败");
                        } else {
                            if (Validator.isBlank(arcimItem.arcimId)) {
                                showToast("医嘱模板信息加载失败");
                            } else {
                                intiUi();
                            }

                        }
                    }
                });

            }
        }.start();
    }

    //增加和更新前进行校验
    public void checkAndLoadPopMessage(final FormArcimItem f) {
        showProgress();
        new Thread() {
            List<PopMessage> list;

            public void run() {
                try {
                    list = ArcimItemManager.checkAndloadPopMessage(f);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dealPopWins(0, list);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    public void dealPopWins(final int index, final List<PopMessage> mListDialog) {
        if (mListDialog == null || mListDialog.size() == index) {//最后一项的时候提交
            final FormArcimItem f = fillForm();
            if (f == null) {
                dismissProgress();
                showToast("请选择医嘱名称");
                return;
            }
            submitDataToServer(f, isNewAdd);
            return;
        }
        dismissProgress();
        final PopMessage msg = mListDialog.get(index);
        if ("A".equalsIgnoreCase(msg.popType)) {
            DiaLogUtil.showAlert(DocOrderActivity.this, msg.popContent, new DiaLogUtil.MyCallback() {
                @Override
                public void onCallback(Object value) {
                    dealPopWins(index + 1, mListDialog);
                }
            });
        } else if ("C".equalsIgnoreCase(msg.popType)) {
            DiaLogUtil.showAlert(DocOrderActivity.this, msg.popContent, null);
            return;
        } else {
            DiaLogUtil.showAlertYesOrNoOnUIThread(DocOrderActivity.this, msg.popContent, new DiaLogUtil.MyCallback() {
                @Override
                public void onCallback(Object value) {
                    Boolean ok = (Boolean) value;
                    if (ok) {
                        dealPopWins(index + 1, mListDialog);
                    } else {
                        clearForm();
                    }
                }
            });
        }
    }

    //新增或更新医嘱
    private void submitDataToServer(final FormArcimItem f, boolean isNewAdd) {
        if (isNewAdd) {
            f.showIndex = "";
        }
        new Thread() {
            private BaseBean b = null;
            String mInfo = "";

            public void run() {
                try {
                    b = ArcimItemManager.saveArcimItem(f);
                    mInfo = b.getResultDesc();
                } catch (Exception e) {
                    mInfo = "操作失败！" + e.getMessage();
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (b != null && "0".equals(b.getResultCode())) {
                            clearForm();
                            showToast(mInfo);
                            finish();
                        } else {
                            showToast(mInfo);
                        }

                    }
                });
            }

            ;
        }.start();

    }

    //清空表单
    private void clearForm() {
        arcimItem = null;
        tv_arcimDesc.setText("");
        tv_arcimDesc.setHint("");
        tv_priorDesc.setText("");
        tv_priorDesc.setHint("");
        tv_itemPrice.setText("");
        tv_locDesc.setText("");
        tv_locDesc.setHint("");
        tv_packQty.setText("");
        tv_startDate.setText("");
        tv_memo.setText("");
        tv_labSpec.setText("");
        tv_labSpec.setHint("");
    }

    //获取选中的医嘱信息详情进行更新操作
    public void loadSelectedArcimItem(final String showIndex) {
        new Thread() {
            public void run() {
                try {
                    arcimItem = ArcimItemManager
                            .loadArcimItemByShowIndex(Const.loginInfo.userCode, tempAI.admId,
                                    showIndex, Const.loginInfo.defaultDeptId);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intiUi();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }

            }

            ;
        }.start();
    }

    //组装医嘱信息
    private FormArcimItem fillForm() {
        if (arcimItem == null) {
            showToast("请先选择医嘱");
            return null;
        }
        final FormArcimItem f = new FormArcimItem();
        try {
            f.admId = tempAI.admId;
            if (isNewAdd == false) {// 更新
                f.showIndex = arcimItem.showIndex;//后台更新是根据这个来判断

            } else {
                f.showIndex = ""; // 新增
            }
            f.antAppId = arcimItem.antAppId;//抗生素申请id
            f.arcItemId = arcimItem.arcimId;
            f.userReasonId = arcimItem.userReasonId;
            f.recLocId = tv_locDesc.getHint().toString();//接收科室
            f.departmentId = Const.loginInfo.defaultDeptId;//登陆科室
            //检验标本
            f.labSpecCode=tv_labSpec.getHint()==null?"":tv_labSpec.getHint().toString();
            String packQty = tv_packQty.getText().toString();//数量
            if (!Validator.isBlank(packQty)) {
                f.packQty = packQty;
            }

            f.ordNote = tv_memo.getText().toString();//备注
            String dateTime = tv_startDate.getText().toString();

            if (!Validator.isBlank(dateTime)) {
                f.ordStartDate = DateUtil.getDateStr(dateTime, "yyyy-MM-dd HH:mm");
                f.ordStartTime = DateUtil.getTime(dateTime, "yyyy-MM-dd HH:mm");
            }

//            String labSpec = tv_labSpec.getText().toString();//样本


            f.skinActId = "";//皮试
            f.skinTest = "N";


            //配液
            f.dispense = "N";


            //是否加急
            f.urgentFlag = "N";


            //是否医保
            f.mianIns = "N";


            f.userCode = Const.loginInfo.userCode;
            if (Validator.isBlank(f.arcItemId)) {
                showToast("医嘱项不能为空!");
                return null;
            }


            f.priorId = tv_priorDesc.getHint().toString();


        } catch (Exception e) {
            e.printStackTrace();
            DiaLogUtil.showAlert(this, "组装数据时发生异常：" + e.getMessage(), null);
            return null;
        }
        return f;
    }


    // 获取医嘱优先级
    private void loadPriority() {
        new Thread() {
            List<Priority> tempList;

            public void run() {
                try {
                    tempList = ArcimItemManager.loadPriority(Const.loginInfo.userCode);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (tempList != null && tempList.size() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PriorityOperateDlg op = new PriorityOperateDlg(DocOrderActivity.this, tempList);
                                op.show();
                            }
                        });
                    }
                }

            }

        }.start();

    }

    public void setPriority(Priority p) {
        tv_priorDesc.setText(p.oecprDesc);
        tv_priorDesc.setHint(p.oecprId);
    }

    public void setDepartment(DeptInfo p) {
        tv_locDesc.setText(p.locDesc);
        tv_locDesc.setHint(p.locId);
    }

    public void setLabSpec(LabSpec p) {
        tv_labSpec.setText(p.labSpec);
        tv_labSpec.setHint(p.labSpecCode);
    }
}
