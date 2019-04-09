package com.imedical.im.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.ArcimItemManager;
import com.imedical.mobiledoctor.XMLservice.DateOrderManager;
import com.imedical.mobiledoctor.XMLservice.DocOrderService;
import com.imedical.mobiledoctor.XMLservice.OrderItemManager;
import com.imedical.mobiledoctor.activity.round.OrdersActivity;
import com.imedical.mobiledoctor.adapter.AdapterLisDetail;
import com.imedical.mobiledoctor.adapter.OrderItemAdapter;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.LabelValue;
import com.imedical.mobiledoctor.entity.LisReportItem;
import com.imedical.mobiledoctor.entity.OrderItem;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.Validator;
import com.imedical.mobiledoctor.widget.ListViewPull;
import com.imedical.mobiledoctor.widget.ListViewPullExp;

import java.util.ArrayList;
import java.util.List;

public class AddDiagnosisActivity extends BaseActivity {
    public String admId,callCode="0";
    public TextView tv_patientName, tv_patientAge, tv_patientCard, tv_doctorName, tv_departmentName, tv_doctorTitle,tv_save,tv_input,tv_goto;
    public EditText et_msgContent;
    public LinearLayout ll_content;
    public List<OrderItem> list_data = new ArrayList<OrderItem>();
    public boolean needLoad=false;
    private AdmInfo temAI;
    private String mConLoad = "";//本次请求的参数
    private ListViewPull mListView = null;
    private temOrderItemAdapter mAadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId=this.getIntent().getStringExtra("admId");
        callCode=this.getIntent().getStringExtra("callCode")==null?"0":this.getIntent().getStringExtra("callCode");
        this.setContentView(R.layout.activity_add_diagnosis_record);
        setTitle("诊疗方案");
        intiView();
        loadData();
    }

    public void intiView() {
        tv_input=findViewById(R.id.tv_input);
        tv_goto=findViewById(R.id.tv_goto);
        ll_content=findViewById(R.id.ll_content);
        et_msgContent=findViewById(R.id.et_msgContent);
        tv_patientName = findViewById(R.id.tv_patientName);
        tv_patientAge = findViewById(R.id.tv_patientAge);
        tv_patientCard = findViewById(R.id.tv_patientCard);
        tv_doctorName = findViewById(R.id.tv_doctorName);
        tv_departmentName = findViewById(R.id.tv_departmentName);
        tv_doctorTitle = findViewById(R.id.tv_doctorTitle);
        mListView = (ListViewPull) findViewById(R.id.elv_data);
        mAadapter = new temOrderItemAdapter();
        mListView.setAdapter(mAadapter);
        mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.setXListViewListener(new ListViewPull.IXListViewListener() {
            @Override
            public void onRefresh() { }
            @Override
            public void onClickLoadMore() {
                loadOrderitemTempThread(temAI.admId, mConLoad);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)//滚动到底部
                                && mConLoad != null) {//还有新的
                            loadOrderitemTempThread(temAI.admId, mConLoad);
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                }
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
        tv_save= findViewById(R.id.tv_save);
        if(callCode.equals("3")){  //视频结束后啥都不能干
            tv_save.setVisibility(View.GONE);
            et_msgContent.setEnabled(false);
            tv_input.setEnabled(false);
            tv_input.setClickable(false);
        }else if(callCode.equals("0")||callCode.equals("1")) { //视频未开始
            showCustom("请先与患者视频后，再下诊疗建议!");
            finish();
        }else {
            tv_save.setVisibility(View.VISIBLE);
            et_msgContent.setEnabled(true);
        }
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddDiagnosisActivity.this);
                builder.setMessage("提交后，你将不能修改【诊疗建议】内容，是否提交？")
                        .setTitle("确认提交")
                        .setCancelable(false)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        String msgContent=et_msgContent.getText()==null?"":et_msgContent.getText().toString();
                                        if(!Validator.isBlank(msgContent)){
                                            saveAdvice();
                                        }else{
                                            showCustom("请填写诊疗建议");
                                        }
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
                Intent it =new Intent(AddDiagnosisActivity.this,DiagnosisListActivity.class);
                it.putExtra("tempAI",temAI);
                startActivity(it);
            }
        });
        tv_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it =new Intent(AddDiagnosisActivity.this,ReservationListActivity.class);
                it.putExtra("tempAI",temAI);
                startActivity(it);
            }
        });
    }

    private void saveAdvice(){
        String msgContent=et_msgContent.getText()==null?"":et_msgContent.getText().toString();
        if(!Validator.isBlank(msgContent)){
            saveData(msgContent);
        }else{
            showCustom("请填写诊疗建议");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(needLoad){
                doQuery();
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
        String Content=admInfo.doctorContent==null?"":admInfo.doctorContent;
        if(Content.length()>0){
            tv_save.setVisibility(View.GONE);
            et_msgContent.setEnabled(false);
            tv_input.setTextColor(getResources().getColor(R.color.gray));
            tv_input.setEnabled(false);
            tv_input.setClickable(false);
        }
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
                            doQuery();
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
                        showCustom(msg);
                    }
                });
            }
        }.start();
    }

    private synchronized void loadOrderitemTempThread( final String admId, final String conLoad) {
        if( mConLoad==null || !mListView.isThereMore() ){
            mListView.endLoad(false);
            return ;
        }
        showProgress();
        final String startDate ="";
        final String endDate = "";
        new Thread() {
            public void run() {
                Message message = new Message();
                try {
                    List<OrderItem> list = OrderItemManager.listOrderItemTemp(
                            "Y", Const.loginInfo.userCode, admId, startDate, endDate,
                            Const.loginInfo.defaultGroupId, Const.loginInfo.defaultDeptId, conLoad);
                    if (list.size() > 0) {
                        for (int i = list.size() - 1; i >-1; i--) {
                            if (list.get(i).orderStatus==null||!("核实").equals(list.get(i).orderStatus)) {
                                list.remove(i);
                            }
                        }
                        list_data.addAll(list);
                        mConLoad = list.get(list.size() - 1).conLoad;
                    }else{
                        mConLoad=null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mConLoad=null;
                } finally {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           dismissProgress();
                           mAadapter.notifyDataSetChanged();
                           if(mConLoad==null){
                               mListView.endLoad(false);
                           }else{
                               mListView.endLoad(true);
                           }
                       }
                   });
                }
            }
        }.start();
    }

    private void doQuery() {
        resetData();
        loadOrderitemTempThread(temAI.admId, mConLoad);
    }

    private void resetData() {
        mConLoad = "";
        mListView.resetStatu();
        mListView.startLoading();
        list_data.clear();
        mAadapter.notifyDataSetChanged();
    }


    public class temOrderItemAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list_data.size();
        }

        @Override
        public Object getItem(int position) {
            return list_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderItem OI = list_data.get(position);
            ViewHolder holder_listDetail;
            int type = getItemViewType(position);
            if (convertView == null) {
                holder_listDetail = new ViewHolder();
                convertView = LayoutInflater.from(AddDiagnosisActivity.this).inflate(R.layout.item_temp_order_view_yes, null);
                holder_listDetail.tv_arcimDesc = (TextView) convertView.findViewById(R.id.tv_arcimDesc);
                convertView.setTag(holder_listDetail);
            } else {
                holder_listDetail = (ViewHolder) convertView.getTag();
            }
            holder_listDetail.tv_arcimDesc.setText(OI.itemDesc);
            return convertView;
        }

    }
    public class ViewHolder {
        public TextView tv_arcimDesc;

    }


}
