package com.imedical.im.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.imedical.im.Adapter.AdapterReservation;
import com.imedical.im.Adapter.Adapter_ReserDate;
import com.imedical.im.entity.AdmInfo;
import com.imedical.im.util.PopupUtil;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.ArcimItemManager;
import com.imedical.mobiledoctor.XMLservice.DocOrderService;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.ArcimItem;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.CanUseRes;
import com.imedical.mobiledoctor.entity.ServiceOrder;

import java.util.ArrayList;
import java.util.List;

public class ReservationListActivity extends BaseActivity implements View.OnClickListener {
    public String admId,callCode="0";
    public TextView tv_patientName, tv_patientAge, tv_patientCard, tv_doctorName, tv_departmentName, tv_submit,tv_save,tv_input;
    public ListView lv_data;
    public AdapterReservation mApdate;
    public Adapter_ReserDate mApdater_date;

    public LinearLayout ll_content;
    public List<ServiceOrder> list_data = new ArrayList<ServiceOrder>();
    public List<CanUseRes> list_date = new ArrayList<CanUseRes>();
    private AdmInfo temAI;
    private Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admId=this.getIntent().getStringExtra("admId");
        callCode=this.getIntent().getStringExtra("callCode")==null?"0":this.getIntent().getStringExtra("callCode");
        temAI=(AdmInfo)this.getIntent().getSerializableExtra("tempAI");
        this.setContentView(R.layout.activity_revervation_list);
        setTitle("检查预约申请");
        intiView();
        LoadData();
    }

    public void intiView() {
        lv_data=(ListView) findViewById(R.id.lv_data);
        tv_input=findViewById(R.id.tv_input);
        tv_submit=findViewById(R.id.tv_submit);
        ll_content=findViewById(R.id.ll_content);
        mApdate=new AdapterReservation(ReservationListActivity.this);
        lv_data.setAdapter(mApdate);

        //========================

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View holder = inflater.inflate(R.layout.reservation_time, null, false);
        View submit = holder.findViewById(R.id.tv_submit);
        View cancel = holder.findViewById(R.id.tv_cancel);
        ListView lv_date=(ListView)holder.findViewById(R.id.lv_date);
        mApdater_date=new Adapter_ReserDate(ReservationListActivity.this);
        lv_date.setAdapter(mApdater_date);
        lv_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mApdater_date.setCurrentItem(position);
                mApdater_date.notifyDataSetChanged();
            }
        });
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        if(dialog==null){
            dialog = PopupUtil.makePopup(ReservationListActivity.this, holder);
        }

    }

    public void LoadData() {
        list_data.clear();
        mApdate.notifyDataSetChanged();
        showProgress();
        new Thread() {
            List<ServiceOrder> list;
            @Override
            public void run() {
                super.run();
                try {
                    list = DocOrderService.getInstance().LoadServiceOrder(Const.loginInfo.userCode, temAI.admId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (list == null) {
                            showCustom("医嘱列表加载失败");
                        } else {
                            list_data.addAll(list);
                            ReList(list);
                            mApdate.notifyDataSetChanged();
                        }
                    }
                });

            }
        }.start();
    }


    public void LoadDateForReser() {
        list_date.clear();
        mApdater_date.notifyDataSetChanged();
        String ordItemId="";
        for(ServiceOrder so:list_data){
            if(so.IsChecked){
                ordItemId=ordItemId+"@"+so.orderRowId;
            }
        }
        if(ordItemId.length()>0){
            ordItemId=ordItemId.substring(1,ordItemId.length());
        }
        final String ordIdStrings =ordItemId;
        showProgress();
        new Thread() {
            List<CanUseRes> list;
            @Override
            public void run() {
                super.run();
                try {
                    list = DocOrderService.getInstance().LoadCanUseRes(Const.loginInfo.userCode, temAI.admId,ordIdStrings);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (list == null) {
                            showCustom("医嘱列表加载失败");
                        } else {
                            CanUseRes c=new CanUseRes("2019-04-02 11:10");
                            CanUseRes b=new CanUseRes("2019-04-03 10:11");
                            list.add(c);list.add(b);
                            list_date.addAll(list);
                            mApdater_date.notifyDataSetChanged();
                        }
                    }
                });

            }
        }.start();
    }
    @SuppressLint("InflateParams")
    public void showPop() {

        LoadDateForReser();


        if(!dialog.isShowing()) dialog.show();

    }

    private void ReList(List<ServiceOrder> list_temp){
        int p=0;
        for(ServiceOrder so:list_temp){
            p++;
            String nowAppType =so.appType;
            String nowServiceId=so.serviceId;
            ListGroup(nowAppType,nowServiceId,p);
        }
    }


    private void ListGroup(String nowAppType,String nowServiceId,int count){

            for(ServiceOrder so_2:list_data){
                if(nowAppType.equals(so_2.appType)&&nowServiceId.equals(so_2.serviceId)){
                    so_2.IsGroup=count;
                }
             }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_submit:
                if(list_date.size()>0){
                    String content=list_date.get(mApdater_date.getCurrentItem()).bookedDate;
                    showCustom("你选择了"+content);
                    dialog.dismiss();
                }
                break;
            case R.id.tv_cancel:
                    if(dialog!=null)dialog.dismiss();
                break;

        }
    }
}
