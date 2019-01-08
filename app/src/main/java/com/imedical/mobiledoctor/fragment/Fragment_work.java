package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.WardRoundActivity;

public class Fragment_work extends Fragment implements View.OnClickListener {
    private Activity ctx;
    private String mInfo=null;
    View mView=null;
    View ll_ward;
    public void onAttach(Activity activity) {
        this.ctx = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_work, null);
        InitView();
        return mView;
    }

    private void InitView(){
        ll_ward=mView.findViewById(R.id.ll_ward);
        ll_ward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vid=v.getId();
        switch (vid){
            case R.id.ll_ward:
                Intent it =new Intent(ctx,WardRoundActivity.class);
                startActivity(it);
                break;
        }
    }
}
