package com.imedical.mobiledoctor.activity.round.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;

public class Ris_DetailActivity extends BaseActivity {


    private TextView tv_content_1,tv_content_2;
    private String content_1=" ",content_2=" ";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.page5_ris_detail);
        Intent i = getIntent();
        content_1=i.getStringExtra("content_1");
        content_2=i.getStringExtra("content_2");
        tv_content_1 = (TextView)findViewById(R.id.tv_content);
        tv_content_2 = (TextView)findViewById(R.id.tv_content2);
        if(content_1==null){
            content_1="暂无数据";
        }
        if(content_2==null){
            content_2="暂无数据";
        }
        tv_content_1.setText(content_1);
        tv_content_2.setText(content_2);
        View iv_left=findViewById(R.id.iv_left);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 诊断状态


    }

}

