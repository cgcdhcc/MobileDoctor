package com.imedical.mobiledoctor.activity.frg_3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.im.entity.qrresponse;
import com.imedical.im.service.ImUserService;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BrowseManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.activity.round.CaseActivity;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.QrCodeGenerateRequest;
import com.imedical.mobiledoctor.util.DownloadUtil;

public class QRActivity extends BaseActivity {
    private TextView tv_name,tv_alias,tv_depat,tv_department;
    private ImageView iv_qr;
    private LoginInfo mLoginInfo;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_activity);
        setTitle("我的二维码");
        mLoginInfo=Const.loginInfo;
        if(mLoginInfo!=null){
            InitViews();
            loadData();
        }else {
            showCustom("登录数据丢失！请重新登录");
            finish();
        }

    }
    private void loadData() {
        showProgress();
        new Thread(){
            qrresponse qrresponse;
            public void run(){
                try {
                    qrresponse = ImUserService.getInstance().qrcodegenerate(new QrCodeGenerateRequest(Const.loginInfo.userCode,Const.loginInfo.userName));
                } catch (Exception e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if(qrresponse!=null){
                            DownloadUtil.loadImage(iv_qr,
                                    qrresponse.data.qr,
                                    R.drawable.icon,
                                    R.drawable.icon,
                                    R.drawable.icon);
                        }
                    }
                });
            }
        }.start();
    }
    private void InitViews() {
        tv_name=(TextView)this.findViewById(R.id.tv_name);
        tv_alias=(TextView)this.findViewById(R.id.tv_alias);
        tv_depat=(TextView)this.findViewById(R.id.tv_depat);
        iv_qr=(ImageView)findViewById(R.id.iv_qr);
        tv_department=this.findViewById(R.id.tv_department);
        tv_name.setText(mLoginInfo.userName);
        tv_alias.setText(mLoginInfo.userCode);
        tv_depat.setText(mLoginInfo.defaultDeptName);
    }

    public void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        System.exit(0);
    }
}
