package com.imedical.mobiledoctor.activity.frg_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.FaceHospitalActivity;
import com.imedical.mobiledoctor.activity.LoginHospitalActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.util.PreferManager;

public class SettingActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        setTitle("设置");
        findViewById(R.id.tv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

    }

    public void exit() {
        Intent intent = null;
        boolean hasface = PreferManager.getBooleanValue("hasface");
        if (hasface) {
            intent = new Intent(SettingActivity.this,
                    FaceHospitalActivity.class);
        } else {
            intent = new Intent(SettingActivity.this,
                    LoginHospitalActivity.class);
        }
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        System.exit(0);
    }
}
