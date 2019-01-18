package com.imedical.im.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;

public class AddDiagnosisActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_diagnosis_record);
        setTitle("诊疗方案");
    }
}
