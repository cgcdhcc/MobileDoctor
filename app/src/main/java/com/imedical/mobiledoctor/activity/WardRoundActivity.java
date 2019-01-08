package com.imedical.mobiledoctor.activity;

import android.os.Bundle;
import android.view.Window;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;

public class WardRoundActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ward_round_activity);


    }
}
