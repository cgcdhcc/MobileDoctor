package com.imedical.mobiledoctor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.jpush.activity.MessageActivity;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.frg_3.SettingActivity;
import com.imedical.mobiledoctor.activity.visit.DateVisitMulitActivity;
import com.imedical.mobiledoctor.zxing.activity.CaptureActivity;

public class Fragment_mine extends Fragment implements View.OnClickListener {
    private MainActivity ctx;
    private String mInfo = null;
    View mView = null,ll_setting;
    TextView tv_name, tv_department, tv_title,tv_exit;
    private ImageView iv_scan;
    public void onAttach(Activity activity) {
        this.ctx =(MainActivity) activity;
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

    private void InitViews() {
        tv_exit=(TextView) mView.findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(this);
        ll_setting=mView.findViewById(R.id.ll_setting);
        ll_setting.setOnClickListener(this);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        iv_scan=(ImageView)mView.findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx,CaptureActivity.class);
                startActivityForResult(intent,100);
            }
        });
        tv_department = (TextView) mView.findViewById(R.id.tv_department);
        tv_title = (TextView) mView.findViewById(R.id.tv_title);
        //==========initData============
        tv_name.setText(Const.loginInfo.userName);
        tv_title.setText(Const.loginInfo.userCode);
        tv_department.setText(Const.loginInfo.defaultDeptName);
        mView.findViewById(R.id.view_menu_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DateVisitMulitActivity.class);
                startActivity(intent);
            }
        });
        mView.findViewById(R.id.iv_word).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == ctx.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Log.d("mark", "条形码扫描结果：" + scanResult);
            ctx.showCustom("条形码扫描结果：" + scanResult);
        }else  {
            ctx.showCustom("nothing to do!");
        }


    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_setting:
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_exit:
                    System.exit(0);
                    break;
            }
    }
}
