package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;

public class Fragment_mine extends Fragment {
    private Activity ctx;
    private String mInfo=null;
    View mView=null;
    TextView tv_name,tv_department,tv_title;

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
        mView = inflater.inflate(R.layout.fragment_mine, null);
        InitViews();
        return mView;
    }

    private void InitViews(){
        tv_name=(TextView) mView.findViewById(R.id.tv_name);
        tv_department=(TextView) mView.findViewById(R.id.tv_department);
        tv_title=(TextView) mView.findViewById(R.id.tv_title);
        //==========initData============
        tv_name.setText(Const.loginInfo.userName);
        tv_title.setText(Const.loginInfo.userCode);
        tv_department.setText(Const.loginInfo.defaultDeptName);
    }
}
