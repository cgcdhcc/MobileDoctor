package com.imedical.mobiledoctor.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.imedical.im.entity.ImBaseResponse;
import com.imedical.im.entity.userregister;
import com.imedical.im.service.ImUserService;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.PrefManager;
import com.imedical.mobiledoctor.XMLservice.SettingManager;
import com.imedical.mobiledoctor.XMLservice.UserManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.base.MyApplication;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.fragment.MainActivity;
import com.imedical.mobiledoctor.util.DateUtil;
import com.imedical.mobiledoctor.util.PhoneUtil;
import com.imedical.mobiledoctor.util.Validator;
import com.imedical.mobiledoctor.widget.CustomProgressDialog;

import java.io.File;
import java.util.List;


public class LoginHospitalActivity extends BaseActivity implements
        OnClickListener, OnCheckedChangeListener {
    private TextView loginIV;
    private CustomProgressDialog progressDialog = null;
    private EditText et_username;
    private EditText et_pwd = null;
    private String terminalId = null;
    private LoginInfo mLoginInfo;
    private CheckBox iv_eyes;
    private Editable etable;
    private boolean isExit = false;
    private LinearLayout ll_switch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_dlg);
        this.terminalId = PhoneUtil.getImei(this);
        initView();
        ((TextView) findViewById(R.id.tv_my)).setText("账号登录");
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //两秒后isExit=flase;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    isExit = false;
            }
        }
    };

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(),
                 "完全退出", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            //完全退出程序
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
//			System.exit(0);
            finish();
        }
    }



    @SuppressLint("NewApi")
    private void initView() {
        loginIV = (TextView) this.findViewById(R.id.btn_login);
        loginIV.setOnClickListener(this);
        iv_eyes = (CheckBox) findViewById(R.id.iv_eyes);

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

        ll_switch = (LinearLayout) findViewById(R.id.ll_switch);

        et_username = (EditText) this.findViewById(R.id.et_username);
//		et_username.setHint("输入用户名.");
        et_username.setSingleLine();
        et_username.requestFocus();
        et_pwd = (EditText) this.findViewById(R.id.et_pwd);

//		et_pwd.setHint("输入登录密码.");
        et_pwd.setSingleLine();
        et_pwd.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_pwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        //输入光标一直在文本后面
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
        }
    }


    private void login(final String phoneNo, final String password, final String terminalId, final String hospitalId) {
//        showProgress();
        try {
            new Thread() {
                public void run() {
                    try {
                        mLoginInfo = UserManager.login(
                                LoginHospitalActivity.this, phoneNo, password,
                                terminalId);
                        userregister userregister=new userregister(mLoginInfo.userCode,mLoginInfo.userCode,mLoginInfo.userName);
                        ImBaseResponse imBaseResponse =ImUserService.getInstance().userRegister(userregister);
                        if(imBaseResponse!=null){
                            Log.d("msg",imBaseResponse.msg);
                        }
                        Message mess = new Message();
                        mess.what = 0;
                        myHandler.handleMessage(mess);
                    } catch (Exception e) {
                        e.printStackTrace();

                        Message mess = new Message();
                        mess.what = -1;
                        mess1 = e.getMessage();
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
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
            progressDialog = null;
        }

    }

    // /** 从当前Activity开启一个新的Activity */
    // private void startNewActivity() {
    // // Intent it = new Intent(this, MainActivity.class);
    // // it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    // // startActivity(it);
    // // this.finish();
    // }
//
//	/** 重新定义按键操作 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			finish();
//			return true;
//		}
//		if (keyCode == KeyEvent.KEYCODE_HOME) {
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

    private void toActivity() {
        Class classzz = getCallbackActivityClass();

        if (classzz != null) {
            Intent intent = new Intent(LoginHospitalActivity.this, classzz);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle b = getIntent().getExtras();
            if (b != null) {
                intent.putExtras(b);
            }
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginHospitalActivity.this, MainActivity.class);
            startActivity(intent);
        }

        finish();
    }

    String mess1 = "Login ";
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            Looper.prepare();
            switch (msg.what) {
                case 0:
                    Const.loginInfo = mLoginInfo;
                    Const.curPat = null;
                    toActivity();
                    break;
                case 2:
                    if (progressDialog != null) {
                        progressDialog.cancel();
                        progressDialog = null;
                    }
                    PrefManager.saveLastLoginDate(LoginHospitalActivity.this, DateUtil.getDateToday(null));
                    toActivity();
                    break;
                //密码错误
                case -1:
                  dismissProgress();
                    showCustom(mess1);
                    break;
                //没有获取病人的错误
                case -3:
                    dismissProgress();
                    toActivity();
                    break;
                //网络出现问题
                case -2:
                    dismissProgress();
                    showCustom("网络出现问题");
                    break;
                default:
                    break;

            }
            Looper.loop();
            super.handleMessage(msg);
        }
    };




    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean checked) {
        // 保存是否保存密码的状态
        Log.e("INFO", "--------arg1   " + checked);
        SettingManager.setRememberPsw4Hos(this, checked);
    }

    DialogInterface.OnKeyListener KeyListener = new DialogInterface.OnKeyListener() {

        public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
            if (arg1 == KeyEvent.KEYCODE_BACK) {

                if ((progressDialog != null) && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            return false;
        }
    };


    protected Class getCallbackActivityClass() {
        String target = getIntent().getStringExtra("target");
        Class targetAct = null;
        try {
            targetAct = Class.forName(target);
        } catch (Exception e) {
            e.printStackTrace();


            //lqz_need modify
//			targetAct = MainActivity.class;
        }
        return targetAct;
    }

}