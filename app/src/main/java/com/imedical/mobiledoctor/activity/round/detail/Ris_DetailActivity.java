package com.imedical.mobiledoctor.activity.round.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.RisManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.RisReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ris_DetailActivity extends BaseActivity {
    private TextView tv_content_1,tv_content_2;
    private String studyId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.page5_ris_detail);
        Intent i = getIntent();
        studyId=i.getStringExtra("studyId");
        tv_content_1 = (TextView)findViewById(R.id.tv_content);
        tv_content_2 = (TextView)findViewById(R.id.tv_content2);
        View iv_left=findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 诊断状态
        loadData();

    }
    private void loadData() {
        final String netWorkType = SettingManager.getNetWorkType(this);
        new Thread() {
            String mInfo="未查询到检查结果";
            List<RisReport> list;
            public void run() {
                    Map map = new HashMap();
                    map.put("userCode", Const.loginInfo.userCode);
                    map.put("admId", Const.curPat.admId);
                    map.put("studyId", studyId);
                    map.put("netWorkType", netWorkType);
                    try {
                        list= RisManager.listRisReport(map);
                    } catch (Exception e) {
                        mInfo = e.getMessage();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(list!=null&&list.size()>0){
                                tv_content_1.setText(list.get(0).examDescEx==null?"":list.get(0).examDescEx);
                                tv_content_2.setText(list.get(0).resultDescEx==null?"":list.get(0).resultDescEx);
                            }else{
                                showToast(mInfo);
                            }
                        }
                    });
            }
        }.start();

    }

}

