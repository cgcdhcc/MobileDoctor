package com.imedical.im.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.imedical.mobiledoctor.R;

public class ShowMemoDlg extends Dialog {
    public Activity activity;
    public TextView tv_save;
    public CheckBox cb_noshow;
    public MyCallBack myCallBack;
    public ShowMemoDlg(Activity activity,MyCallBack myCallBack){
        super(activity,R.style.VersionDlg);
        this.myCallBack=myCallBack;
        this.activity=activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //获得dialog的window窗口
        Window window = getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width=dm.widthPixels-100;
        lp.height= WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        setContentView(R.layout.dlg_show_memo);
        tv_save=findViewById(R.id.tv_save);
        cb_noshow=findViewById(R.id.cb_noshow);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               myCallBack.callback(cb_noshow.isChecked());
               dismiss();
            }
        });
    }

    public interface MyCallBack{
        void callback(boolean noshow);
    }


}
