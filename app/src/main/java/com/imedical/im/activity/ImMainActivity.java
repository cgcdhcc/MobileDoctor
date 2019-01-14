package com.imedical.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.widget.ListViewPull;

import java.util.ArrayList;
import java.util.List;

public class ImMainActivity extends BaseActivity implements View.OnClickListener {
    public TextView tv_imgtxt, tv_imgtxt_line, tv_videodept, tv_videodept_line, tv_startDate, tv_endDate, tv_hasfinish, tv_hasfinish_line, tv_isgoing, tv_isgoing_line;
    public ListViewPull lv_data;
    public int currentType = 0; //0图文资讯 1远程门诊
    public int currentStatus = 0;//0已完成  1咨询中
    public List<String> list = new ArrayList<>();
    public MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_main);
        intiView();
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

        tv_endDate = (TextView) findViewById(R.id.tv_endDate);
        tv_endDate.setOnClickListener(this);


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
                startActivity(intent);
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

        if (currentStatus == 0) {
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
                if (currentStatus != 0) {
                    currentStatus = 0;
                    doCheck();
                }
                break;
            case R.id.tv_isgoing:
                if (currentStatus != 1) {
                    currentStatus = 1;
                    doCheck();
                }
                break;
            case R.id.tv_startDate:
                break;
            case R.id.tv_endDate:
                break;
        }
    }

    public void loadData() {
        showProgress();
        list.clear();
        new Thread() {
            @Override
            public void run() {
                super.run();
                list.add("测试");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        adapter.notifyDataSetChanged();
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
            return convertView;
        }


    }
}
