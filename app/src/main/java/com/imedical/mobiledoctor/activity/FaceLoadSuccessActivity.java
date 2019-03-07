package com.imedical.mobiledoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.StatusBarUtils;

public class FaceLoadSuccessActivity extends BaseActivity {
    public TextView tv_doctorName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_face_upload_success);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        intiView();
        PreferManager.saveBooleanValue("hasface", true);
    }
    public void intiView(){
        tv_doctorName=findViewById(R.id.tv_doctorName);
        tv_doctorName.setText(Const.loginInfo.userName+"医生，您的信息已上传成功");
        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FaceLoadSuccessActivity.this, FaceHospitalActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
