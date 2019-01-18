package com.imedical.jpush.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.util.DialogUtil;
import com.imedical.mobiledoctor.util.MyCallback;

/**
 * Created by dashan on 2017/7/28.
 */

public class MsgOperateDlg extends Dialog {
    public MessageActivity activity;
    public MsgOperateDlg(MessageActivity activity) {
        super(activity);
        this.activity=activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //获得dialog的window窗口
        Window window = getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        this.setContentView(R.layout.jpush_msg_operate_dlg);
        findViewById(R.id.tv_reded).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                activity.updateallmsgstatus();
            }
        });
        findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showAlertYesOrNo(activity, "操作确认", "确认要删除全部消息？", new MyCallback<Boolean>() {
                    @Override
                    public void onCallback(Boolean value) {
                        if(value){
                            dismiss();
                            activity.deleteallmsg();
                        }
                    }
                });

            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
