package com.imedical.im.activity;

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
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.DateTimePickDialogUtil;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.MyCallback;
import com.imedical.mobiledoctor.widget.ListViewPull;

import java.util.ArrayList;
import java.util.List;

public class ImMainActivity extends BaseActivity implements View.OnClickListener {
    public TextView tv_imgtxt, tv_imgtxt_line, tv_videodept, tv_videodept_line, tv_startDate, tv_endDate, tv_hasfinish, tv_hasfinish_line, tv_isgoing, tv_isgoing_line;
    public ListViewPull lv_data;
    public int currentType = 0; //0图文资讯 1远程门诊
    public int currentStatus = 0;//0咨询中  1已完成
    public List<AdmInfo> list = new ArrayList<>();
    public MyAdapter adapter;

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

    public void intiView() {
        findViewById(R.id.iv_left).setOnClickListener(this);
        tv_imgtxt = (TextView) findViewById(R.id.tv_imgtxt);
        tv_imgtxt.setOnClickListener(this);

        tv_imgtxt_line = (TextView) findViewById(R.id.tv_imgtxt_line);

        tv_videodept = (TextView) findViewById(R.id.tv_videodept);
        tv_videodept.setOnClickListener(this);


        tv_videodept_line = (TextView) findViewById(R.id.tv_videodept_line);

        tv_startDate = (TextView) findViewById(R.id.tv_startDate);
        tv_startDate.setOnClickListener(this);
        tv_startDate.setText(DateUtil.getDateToday(null));
        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);
        tv_endDate.setText(DateUtil.getDateTodayBefore(null, 7));

        tv_hasfinish = (TextView) findViewById(R.id.tv_hasfinish);
        tv_hasfinish.setOnClickListener(this);

        tv_hasfinish_line = (TextView) findViewById(R.id.tv_hasfinish_line);

        tv_isgoing = (TextView) findViewById(R.id.tv_isgoing);
        tv_isgoing.setOnClickListener(this);

        tv_isgoing_line = (TextView) findViewById(R.id.tv_isgoing_line);

        lv_data = (ListViewPull) findViewById(R.id.lv_data);

        adapter = new MyAdapter();
        lv_data.setAdapter(adapter);
        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ImMainActivity.this,TalkMsgActivity.class);
                if(list.size()>position){
                    intent.putExtra("admInfo", list.get(position));
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
                    templist= AdmManager.GetPatientList(Const.DeviceId, Const.loginInfo.userCode, Const.loginInfo.defaultDeptId, currentType==0?"T":"V",currentStatus==0?"N":"Y" ,tv_startDate.getText().toString() ,tv_endDate.getText().toString() );
                }catch (Exception e){
                    e.printStackTrace();
                    msg=e.getMessage();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        if(templist!=null){
                            list.addAll(templist);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.im_item_activity_main, null);
            TextView tv_patientName=convertView.findViewById(R.id.tv_patientName);
            tv_patientName.setText(list.get(position).patientName);
            TextView tv_registerDate=convertView.findViewById(R.id.tv_registerDate);
            tv_registerDate.setText(list.get(position).registerDate+"   "+(list.get(position).sessionName==null?"":list.get(position).sessionName));
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
            return convertView;
        }


    }
}
