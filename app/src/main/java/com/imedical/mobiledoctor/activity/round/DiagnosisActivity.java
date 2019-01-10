package com.imedical.mobiledoctor.activity.round;

import android.os.Bundle;
import android.view.Window;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;

public class DiagnosisActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.page2_diagnosis_activity);
        InitViews();
//        loadData();
    }

    private void InitViews() {
        setTitle("诊断记录");
    }
}
