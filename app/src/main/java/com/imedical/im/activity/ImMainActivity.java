package com.imedical.im.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.im.entity.AdmInfo;
import com.imedical.im.service.AdmManager;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.activity.LoginHospitalActivity;
import com.imedical.mobiledoctor.activity.WardRoundActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.widget.ListViewPull;
import com.imedical.trtcsdk.TRTCNewActivity;
import com.imedical.trtcsdk.bean.userSigResponse;
import com.imedical.trtcsdk.service.VideoService;

import java.util.ArrayList;
import java.util.List;

public class ImMainActivity extends BaseActivity implements View.OnClickListener {
    public TextView tv_imgtxt, tv_imgtxt_line, tv_videodept, tv_videodept_line, tv_startDate, tv_endDate, tv_hasfinish, tv_hasfinish_line, tv_isgoing, tv_isgoing_line;
    public ListViewPull lv_data;
    public int currentType = 0; //0图文资讯 1远程门诊
    public int currentStatus = 0;//0咨询中  1已完成
    public List<AdmInfo> list = new ArrayList<>();
    public MyAdapter adapter;
    private View ll_nodata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_main);
        intiView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doCheck();
    }

    private void resetData() {
        ll_nodata.setVisibility(View.VISIBLE);
        lv_data.setVisibility(View.GONE);
    }

    public void intiView() {
        ll_nodata=findViewById(R.id.ll_nodata);
        findViewById(R.id.iv_left).setOnClickListener(this);
        tv_imgtxt = (TextView) findViewById(R.id.tv_imgtxt);
        tv_imgtxt.setOnClickListener(this);

        tv_imgtxt_line = (TextView) findViewById(R.id.tv_imgtxt_line);

        tv_videodept = (TextView) findViewById(R.id.tv_videodept);
        tv_videodept.setOnClickListener(this);


        tv_videodept_line = (TextView) findViewById(R.id.tv_videodept_line);

        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setOnClickListener(this);
        tv_startDate.setText(DateUtil.getDateTodayBefore(null, -1));
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);
        tv_endDate.setText(DateUtil.getDateTodayBefore(null,7));

        tv_hasfinish = (TextView) findViewById(R.id.tv_hasfinish);
        tv_hasfinish.setOnClickListener(this);

        tv_hasfinish_line = (TextView) findViewById(R.id.tv_hasfinish_line);

        tv_isgoing = (TextView) findViewById(R.id.tv_isgoing);
        tv_isgoing.setOnClickListener(this);

        tv_isgoing_line = (TextView) findViewById(R.id.tv_isgoing_line);

        lv_data = (ListViewPull) findViewById(R.id.lv_data);
        lv_data.endLoad(false);
        lv_data.setXListViewListener(new ListViewPull.IXListViewListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onClickLoadMore() {
                loadData();
            }

        });
        adapter = new MyAdapter();
        lv_data.setAdapter(adapter);
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentType==0){
                    Intent intent=new Intent(ImMainActivity.this,TalkMsgActivity.class);
                    if(list.size()>position){
                        intent.putExtra("admInfo", list.get(position));
                        startActivity(intent);
                    }
                }else{
                    Intent intent=new Intent(ImMainActivity.this,AdmInfoActivity.class);
                    intent.putExtra("admId", list.get(position).admId);
                    intent.putExtra("Type", "VIDEO");
                    intent.putExtra("callCode",list.get(position).callCode);
                    startActivity(intent);
                }
            }
        });
    }

    public void doCheck() {
        if (currentType == 0) {
            tv_imgtxt.setTextColor(getResources().getColor(R.color.text_base));
            tv_imgtxt.getPaint().setFakeBoldText(true);
            tv_imgtxt_line.setVisibility(View.VISIBLE);

            tv_videodept.setTextColor(getResources().getColor(R.color.text_grayblack));
            tv_videodept.getPaint().setFakeBoldText(false);
            tv_videodept_line.setVisibility(View.INVISIBLE);
        } else {
            tv_videodept.setTextColor(getResources().getColor(R.color.text_base));
            tv_videodept.getPaint().setFakeBoldText(true);
            tv_videodept_line.setVisibility(View.VISIBLE);

            tv_imgtxt.setTextColor(getResources().getColor(R.color.text_grayblack));
            tv_imgtxt.getPaint().setFakeBoldText(false);
            tv_imgtxt_line.setVisibility(View.INVISIBLE);
        }

        if (currentStatus == 1) {
            tv_hasfinish.setTextColor(getResources().getColor(R.color.mobile_blue));
            tv_hasfinish_line.setVisibility(View.VISIBLE);

            tv_isgoing.setTextColor(getResources().getColor(R.color.text_grayblack));
            tv_isgoing_line.setVisibility(View.INVISIBLE);
        } else {
            tv_isgoing.setTextColor(getResources().getColor(R.color.mobile_blue));
            tv_isgoing_line.setVisibility(View.VISIBLE);

            tv_hasfinish.setTextColor(getResources().getColor(R.color.text_grayblack));
            tv_hasfinish_line.setVisibility(View.INVISIBLE);
        }
        resetData();
        loadData();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_imgtxt:
                if (currentType != 0) {
                    currentType = 0;
                    doCheck();
                }
                break;
            case R.id.tv_videodept:
                if (currentType != 1) {
                    currentType = 1;
                    doCheck();
                }
                break;
            case R.id.tv_hasfinish:
                if (currentStatus != 1) {
                    currentStatus = 1;
                    doCheck();
                }
                break;
            case R.id.tv_isgoing:
                if (currentStatus != 0) {
                    currentStatus = 0;
                    doCheck();
                }
                break;
            case R.id.tv_startDate:
                new DateTimePickDialogUtil(ImMainActivity.this).datePicKDialog(tv_startDate.getText().toString(), new MyCallback() {
                    @Override
                    public void onCallback(Object value) {
                        tv_startDate.setText((String)value);
                        loadData();
                    }
                });
                break;
            case R.id.tv_endDate:
                new DateTimePickDialogUtil(ImMainActivity.this).datePicKDialog(tv_endDate.getText().toString(), new MyCallback() {
                    @Override
                    public void onCallback(Object value) {
                        tv_endDate.setText((String)value);
                        loadData();
                    }
                });
                break;
        }
    }

    public void loadData() {
        showProgress();
        new Thread() {
            List<AdmInfo> templist;
            String msg="加载失败了";
            @Override
            public void run() {
                super.run();
                try{
                    templist= AdmManager.GetPatientList(Const.DeviceId, Const.loginInfo.userCode, Const.loginInfo.defaultDeptId, currentType==0?"T":"R",currentStatus==0?"N":"Y" ,tv_startDate.getText().toString() ,tv_endDate.getText().toString() );
                }catch (Exception e){
                    e.printStackTrace();
                    msg=e.getMessage();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.clear();
                            adapter.notifyDataSetChanged();
                            if(templist!=null){
                                list.addAll(templist);
                                if(list.size()>0){
                                    ll_nodata.setVisibility(View.GONE);
                                    lv_data.setVisibility(View.VISIBLE);
                                }
                                adapter.notifyDataSetChanged();
                                dismissProgress();
                            }else{
                                adapter.notifyDataSetChanged();
                                dismissProgress();
                                showToast(msg);
                            }
                        }
                    });
                }

            }
        }.start();
    }
    public void loadPatinfo(final String admId) {
        showProgress();
        new Thread() {
            PatientInfo patientInfo;

            public void run() {
                try {
                    patientInfo = BusyManager.loadPatientInfo(Const.loginInfo.userCode, admId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (patientInfo != null) {
                            Const.curPat = patientInfo;
                            Const.curSRecorder = null;
                            Const.SRecorderList = null;
                            Intent intent = new Intent(ImMainActivity.this, WardRoundActivity.class);
                            intent.putExtra("title", "患者资料");
                            startActivity(intent);
                        } else {
                            showToast("获取患者信息失败");
                        }
                    }
                });
            }

            ;
        }.start();
    }
    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.im_item_activity_main, null);
            TextView tv_patientName=convertView.findViewById(R.id.tv_patientName);
            View ll_enter=convertView.findViewById(R.id.ll_enter);
            View ll_end=convertView.findViewById(R.id.ll_end);
            View ll_dzbl=convertView.findViewById(R.id.ll_dzbl);
            View ll_suggest=convertView.findViewById(R.id.ll_suggest);
            tv_patientName.setText(list.get(position).patientName);
            TextView tv_registerDate=convertView.findViewById(R.id.tv_registerDate);
            tv_registerDate.setText(list.get(position).admitDate+" "+(list.get(position).admitTimeRange==null?"":list.get(position).admitTimeRange));
            TextView tv_patientContent=convertView.findViewById(R.id.tv_patientContent);
            tv_patientContent.setText(list.get(position).patientContent);
            ImageView iv_head=convertView.findViewById(R.id.iv_head);
            if("女".equals(list.get(position).patientSex)){
                iv_head.setImageResource(R.drawable.pat_famale);
            }else if("男".equals(list.get(position).patientSex)){
                iv_head.setImageResource(R.drawable.pat_male);
            }else{
                iv_head.setImageResource(R.drawable.icon_common_head);
            }
           View  ll_video=convertView.findViewById(R.id.ll_video);
            if(currentType==0){
                ll_video.setVisibility(View.GONE);
            }else {
                ll_video.setVisibility(View.VISIBLE);
                String code=list.get(position).callCode==null?"0":list.get(position).callCode;
                if(currentStatus==1){
                    if(code.equals("3")){
                        ll_suggest.setVisibility(View.VISIBLE);
                        ll_enter.setVisibility(View.GONE);
                        ll_end.setVisibility(View.INVISIBLE);
                    }else {
                        ll_suggest.setVisibility(View.GONE);
                        ll_enter.setVisibility(View.VISIBLE);
                        ll_end.setVisibility(View.VISIBLE);
                    }
                }else {
                    if(code.equals("2")){
                        ll_end.setVisibility(View.VISIBLE);
                    }else{
                        ll_end.setVisibility(View.INVISIBLE);
                    }
                }
                ll_enter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it=new Intent(ImMainActivity.this, TRTCNewActivity.class);
                        it.putExtra("roomNum",list.get(position).admId);
                        it.putExtra("patName",list.get(position).patientName);
                        it.putExtra("patDate",list.get(position).admitDate+" "+list.get(position).admitTimeRange);
                        it.putExtra("docName",Const.loginInfo.userCode);
                        it.putExtra("docRealName",Const.loginInfo.userName);
                        it.putExtra("AdmInfo",list.get(position));
                        startActivity(it);
                    }
                });
                ll_suggest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it=new Intent(ImMainActivity.this, AddDiagnosisActivity.class);
                        it.putExtra("admId", list.get(position).admId);
                        startActivity(it);
                    }
                });
                ll_dzbl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     loadPatinfo(list.get(position).admId);
                    }
                });
                ll_end.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new  AlertDialog.Builder(ImMainActivity.this)
                                .setTitle("结束视频服务")
                                .setMessage("您确定要结束吗？")
                                .setPositiveButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String admId= list.get(position).admId;
                                                String userId =Const.loginInfo.userCode;
                                                StopService(admId,userId);
                                            }
                                        })
                                .setNegativeButton("取消",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                    }
                });
            }

            return convertView;
        }

        private  void StopService(final String admId,final String userId){
            showProgress();
            new Thread() {
                String msg="加载失败了";
                userSigResponse response=new userSigResponse();
                @Override
                public void run() {
                    super.run();
                    try{
                        List<AdmInfo> templist= AdmManager.GetAdmInfo(Const.DeviceId, Const.loginInfo.userCode, admId);
                        if(templist.size()>0){
                            String content =templist.get(0).doctorContent==null?"":templist.get(0).doctorContent;
                            if(content.length()>0){
                                response = VideoService.StopVideo(admId,userId);
                            }else {
                                response.code=-1;
                                response.message="请先填写诊疗建议，再结束服务！";
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        msg=e.getMessage();
                    }finally {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissProgress();
                                if(response.code==20000){
                                    showCustom("结束视频成功");
                                    onResume();
                                }else  {
                                    showCustom("执行失败："+response.message);
                                }
                            }
                        });
                    }
                }
            }.start();
        }
    }
}
