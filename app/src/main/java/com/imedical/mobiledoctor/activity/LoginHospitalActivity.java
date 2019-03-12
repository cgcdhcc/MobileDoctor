package com.imedical.mobiledoctor.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.imedical.face.service.HttpFaceService;
import com.imedical.im.entity.ImBaseResponse;
import com.imedical.im.entity.userregister;
import com.imedical.im.service.ImUserService;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.XMLservice.UserManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.MainReturn;
import com.imedical.mobiledoctor.entity.QrCodeGenerateRequest;
import com.imedical.mobiledoctor.fragment.MainActivity;
import com.imedical.mobiledoctor.util.LogMe;
import com.imedical.mobiledoctor.util.PhoneUtil;
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.Validator;

import java.util.Set;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class LoginHospitalActivity extends BaseActivity implements
        OnClickListener, OnCheckedChangeListener {
    private TextView loginIV,tv_face_login,tv_num;
    private EditText et_username;
    private EditText et_pwd = null;
    private String terminalId = null;
    private LoginInfo mLoginInfo;
    private CheckBox iv_eyes;
    private Editable etable;
    private boolean isExit = false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_dlg);
        this.terminalId = PhoneUtil.getImei(this);
        initView();
        ((TextView) findViewById(R.id.tv_my)).setText("账号登录");
    }

    @SuppressLint("NewApi")
    private void initView() {
        loginIV = (TextView) this.findViewById(R.id.btn_login);
        tv_num=(TextView) this.findViewById(R.id.tv_num);
        String v=String.valueOf(getVersionName(LoginHospitalActivity.this));
        tv_num.setText(v);
        loginIV.setOnClickListener(this);
        tv_face_login = (TextView) this.findViewById(R.id.tv_face_login);
        tv_face_login.setOnClickListener(this);
        iv_eyes = (CheckBox) findViewById(R.id.iv_eyes);
        SettingManager.setIsIntranet(LoginHospitalActivity.this, false);
        iv_eyes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etable = et_pwd.getText();
                    Drawable img_eyes = ContextCompat.getDrawable(LoginHospitalActivity.this, R.drawable.eyes_normal);
                    img_eyes.setColorFilter(getResources().getColor(R.color.bg_commom), PorterDuff.Mode.MULTIPLY);
                    iv_eyes.setBackground(img_eyes);
                    Selection.setSelection(etable, etable.length());

                } else {
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etable = et_pwd.getText();
                    Drawable img_eyes = ContextCompat.getDrawable(LoginHospitalActivity.this, R.drawable.eyes_normal);
                    img_eyes.clearColorFilter();
                    iv_eyes.setBackground(img_eyes);
                    Selection.setSelection(etable, etable.length());
                }
            }
        });


        et_username = (EditText) this.findViewById(R.id.et_username);
        et_username.setSingleLine();
        et_username.requestFocus();
        et_username.setText(PreferManager.getValue("phoneNo"));
        et_pwd = (EditText) this.findViewById(R.id.et_pwd);
        et_pwd.setSingleLine();
        et_pwd.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_pwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        et_pwd.setText(PreferManager.getValue("password"));
    }

    /**
     * 记住账号和密码
     */

    public void onClick(View view) {
        if (view == loginIV) {
            String username = et_username.getText().toString().trim();
            if (Validator.isBlank(username)) {
                Toast.makeText(LoginHospitalActivity.this, "登录账号不能为空，请输入。",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String pwd = et_pwd.getText().toString().trim();
            if (Validator.isBlank(pwd)) {
                Toast.makeText(LoginHospitalActivity.this, "登录密码不能为空，请输入。",
                        Toast.LENGTH_LONG).show();
                return;
            }
            login(username, pwd, terminalId, "");
        }else if(view==tv_face_login){
            Intent intent=new Intent(LoginHospitalActivity.this,FaceHospitalActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void login(final String phoneNo, final String password, final String terminalId, final String hospitalId) {
        showProgress();
        try {
            new Thread() {
                public void run() {
                    try {
                        mLoginInfo = UserManager.login(
                                LoginHospitalActivity.this, phoneNo, password,
                                terminalId);
                        Message mess = new Message();
                        mess.what = 0;
                        myHandler.handleMessage(mess);
                        PreferManager.saveValue("phoneNo", phoneNo);
                        PreferManager.saveValue("password", password);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message mess = new Message();
                        mess.what = -1;
                        mess.obj=e.getMessage();
                        myHandler.handleMessage(mess);
                    }
                }
            }.start();
        } catch (Exception e) {
            Log.e("ERROR", "---------getMessage--------1-" + e.toString());
            Message mess = new Message();
            mess.what = -2;
            myHandler.handleMessage(mess);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }


    public void intiJpush(){
        if(Const.loginInfo!=null){
            //JPushInterface.setDebugMode(true);
            JPushInterface.init(this);
            JPushInterface.setAlias(this,100,"reg_"+Const.loginInfo.userCode);
            CustomPushNotificationBuilder builder = new
                    CustomPushNotificationBuilder(this,
                    R.layout.jpush_customer_notitfication_layout,
                    R.id.icon,
                    R.id.title,
                    R.id.text);
            // 指定定制的 Notification Layout
            builder.statusBarDrawable = R.drawable.icon;
            // 指定最顶层状态栏小图标
            builder.layoutIconDrawable = R.drawable.icon;
            // 指定下拉状态栏时显示的通知图标
            JPushInterface.setPushNotificationBuilder(2, builder);
        }
    }

    public void checkNeedFace(){
        showProgress();
        new Thread(){
            boolean hasface=false;
            @Override
            public void run() {
                super.run();
                //HttpFaceService.face_delperson(Const.loginInfo.userCode);//删除人脸个体，测试用
                hasface= HttpFaceService.face_getfaceids(Const.loginInfo.userCode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PreferManager.saveBooleanValue("hasface", hasface);
                                if(hasface){
                                    Intent intent=new Intent(LoginHospitalActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Intent intent=new Intent(LoginHospitalActivity.this, FaceLoadActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                });
            }
        }.start();
    }


    Handler myHandler = new Handler() {
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            dismissProgress();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (msg.what) {
                        case 0:
                            Const.loginInfo = mLoginInfo;
                            Const.curPat = null;
                            intiJpush();
                            checkNeedFace();
                            break;

                        //密码错误
                        case -1:
                            Log.d("msg", msg.obj.toString());
                            showCustom(msg.obj.toString());
                            break;

                        case -2:
                            showCustom("网络出现问题");
                            break;
                        default:
                            break;

                    }
                }
            });

        }
    };


    public  String getVersionName(Context context)//获取版本号
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "V-1.0";
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean checked) {
        // 保存是否保存密码的状态
        Log.e("INFO", "--------arg1   " + checked);
        SettingManager.setRememberPsw4Hos(this, checked);
    }

}