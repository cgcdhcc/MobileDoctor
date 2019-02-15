package com.imedical.mobiledoctor.activity.frg_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.im.entity.qrresponse;
import com.imedical.im.service.ImUserService;
import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.entity.QrCodeGenerateRequest;
import com.imedical.mobiledoctor.util.DownloadUtil;
import com.imedical.mobiledoctor.util.WxBitmapUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRActivity extends BaseActivity {
    private TextView tv_name, tv_alias, tv_depat, tv_department;
    private ImageView iv_qr, iv_share;
    private LoginInfo mLoginInfo;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx78bcac63fd76f052";
    private static final int THUMB_SIZE = 150;
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_activity);
        setTitle("我的二维码");
        mLoginInfo = Const.loginInfo;
        if (mLoginInfo != null) {
            InitViews();
            loadData();
            regToWx();
        } else {
            showCustom("登录数据丢失！请重新登录");
            finish();
        }

    }

    private void loadData() {
        showProgress();
        new Thread() {
            qrresponse qrresponse;

            public void run() {
                try {
                    qrresponse = ImUserService.getInstance().qrcodegenerate(new QrCodeGenerateRequest(Const.loginInfo.doctorCode, Const.loginInfo.userName));
                } catch (Exception e) {
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        if (qrresponse != null) {
                            DownloadUtil.loadImage(iv_qr,
                                    qrresponse.data.qr,
                                    R.drawable.icon,
                                    R.drawable.icon,
                                    R.drawable.icon);
                            findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    QrCodeShareDlg dlg = new QrCodeShareDlg(QRActivity.this);
                                    dlg.show();
                                }
                            });
                        }
                    }
                });
            }
        }.start();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    private void InitViews() {
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_alias = (TextView) this.findViewById(R.id.tv_alias);
        tv_depat = (TextView) this.findViewById(R.id.tv_depat);
        iv_qr = (ImageView) findViewById(R.id.iv_qr);
        tv_department = this.findViewById(R.id.tv_department);
        tv_name.setText(mLoginInfo.userName);
        //tv_alias.setText(mLoginInfo.userCode);
        tv_depat.setText(mLoginInfo.defaultDeptName);

    }

    public void shareToWx(int scene) {//分享到微信 //SendMessageToWX.Req.WXSceneSession;  朋友圈//WXSceneTimeline
        iv_qr.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(iv_qr.getDrawingCache());
        iv_qr.setDrawingCacheEnabled(false);
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.description = "扫描二维码，随时找我咨询，我在互联网医院等你";
        msg.messageExt = "扫描二维码，随时找我咨询，我在互联网医院等你";
        msg.title = "扫描二维码，随时找我咨询，我在互联网医院等你";
        msg.thumbData = WxBitmapUtil.bmpToByteArray(bitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    public void downloadImg() {//保存到本地
        iv_qr.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(iv_qr.getDrawingCache());
        iv_qr.setDrawingCacheEnabled(false);
        File filePic = null;
        try {
            filePic = new File(AppConfig.FILE_PATH + "/qrcode/" + Const.loginInfo.userCode + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace();
            e.printStackTrace();
        }
        showToast("图片保存到" + filePic.getAbsolutePath());
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        System.exit(0);
    }
}
