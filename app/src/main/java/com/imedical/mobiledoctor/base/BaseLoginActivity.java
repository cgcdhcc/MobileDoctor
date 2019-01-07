package com.imedical.mobiledoctor.base;

import android.os.Bundle;

public abstract class BaseLoginActivity extends BaseActivity {

    public boolean isAdd = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toLoginHospitalActivity(String callbackActivity) {
//        Intent i = new Intent(this, LoginHospitalActivity.class);
//        i.putExtra("target", callbackActivity);
//        Bundle params = getIntent().getExtras();
//        if (params != null) {
//            i.putExtras(params);
//        }
//        startActivity(i);
//        finish();
    }


}