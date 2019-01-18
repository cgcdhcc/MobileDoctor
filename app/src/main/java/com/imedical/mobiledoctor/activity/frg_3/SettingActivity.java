package com.imedical.mobiledoctor.activity.frg_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;

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
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
			System.exit(0);
    }
}
