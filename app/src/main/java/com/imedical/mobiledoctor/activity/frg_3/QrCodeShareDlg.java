package com.imedical.mobiledoctor.activity.frg_3;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.imedical.mobiledoctor.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.List;


/**
 * Created by dashan on 2017/7/28.
 */

public class QrCodeShareDlg extends Dialog {
    public QRActivity activity;
    public View view_timeline,view_wx,view_download;
    public QrCodeShareDlg(QRActivity activity) {
        super(activity, R.style.quick_option_dialog);
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
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        this.setContentView(R.layout.dlg_share_view);
        view_timeline=findViewById(R.id.view_timeline);
        view_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.shareToWx(SendMessageToWX.Req.WXSceneTimeline);
                dismiss();
            }
        });
        view_wx=findViewById(R.id.view_wx);
        view_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.shareToWx(SendMessageToWX.Req.WXSceneSession);
                dismiss();
            }
        });
        view_download=findViewById(R.id.view_download);
        view_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.downloadImg();
                dismiss();
            }
        });
    }
}
