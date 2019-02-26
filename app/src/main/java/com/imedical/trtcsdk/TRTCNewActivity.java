package com.imedical.trtcsdk;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.trtcsdk.bean.userSigResponse;
import com.imedical.trtcsdk.service.VideoService;

import java.util.ArrayList;
import java.util.List;

public class TRTCNewActivity extends BaseActivity {
    private final static int REQ_PERMISSION_CODE = 0x1000;

    private TRTCGetUserIDAndUserSig mUserInfoLoader;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);
        setTitle("网络诊间");
        TextView tv_date=findViewById(R.id.tv_date);
        TextView tv_patname=findViewById(R.id.tv_patname);
        String patDate=getIntent().getStringExtra("patDate");
        String patName=getIntent().getStringExtra("patName");
        String roomNum=getIntent().getStringExtra("roomNum");
        String docName=getIntent().getStringExtra("docName");
        tv_patname.setText(patName);tv_date.setText(patDate);
        final EditText etRoomId = (EditText)findViewById(R.id.et_room_name);
        etRoomId.setText(roomNum);
        final EditText etUserId = (EditText)findViewById(R.id.et_user_name);
        etUserId.setText(docName);
        TextView tvEnterRoom = (TextView)findViewById(R.id.tv_enter);
        tvEnterRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int roomId = 123;
                try{
                    roomId = Integer.valueOf(etRoomId.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getContext(), "请输入有效的房间号", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String userId = etUserId.getText().toString();
                final String userSig= etUserId.getTag().toString();

                if(TextUtils.isEmpty(userId)) {
                    Toast.makeText(getContext(), "请输入有效的用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                onJoinRoom(roomId, userId,userSig);
            }
        });
        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadUserRig();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     *  Function: 读取用户输入，并创建（或加入）音视频房间
     *
     *  此段示例代码最主要的作用是组装 TRTC SDK 进房所需的 TRTCParams
     *
     *  TRTCParams.sdkAppId => 可以在腾讯云实时音视频控制台（https://console.cloud.tencent.com/rav）获取
     *  TRTCParams.userId   => 此处即用户输入的用户名，它是一个字符串
     *  TRTCParams.roomId   => 此处即用户输入的音视频房间号，比如 125
     *  TRTCParams.userSig  => 此处示例代码展示了两种获取 usersig 的方式，一种是从【控制台】获取，一种是从【服务器】获取
     *
     * （1）控制台获取：可以获得几组已经生成好的 userid 和 usersig，他们会被放在一个 json 格式的配置文件中，仅适合调试使用
     * （2）服务器获取：直接在服务器端用我们提供的源代码，根据 userid 实时计算 usersig，这种方式安全可靠，适合线上使用
     *
     *  参考文档：https://cloud.tencent.com/document/product/647/17275
     */
    private void onJoinRoom(int roomId, final String userId,final String userSig) {
        final Intent intent = new Intent(getContext(), TRTCMainActivity.class);
        intent.putExtra("roomId", roomId);
        intent.putExtra("userId", userId);
        final int sdkAppId = 1400185867;
        if (sdkAppId > 0) {
            intent.putExtra("sdkAppId", sdkAppId);
            intent.putExtra("userSig", userSig);
            startActivity(intent);
        } else {
            mUserInfoLoader.getUserSigFromServer(1400185867, roomId, userId, "12345678", new TRTCGetUserIDAndUserSig.IGetUserSigListener() {
                @Override
                public void onComplete(String userSig, String errMsg) {
                    if (!TextUtils.isEmpty(userSig)) {
//                        intent.putExtra("sdkAppId", 1400037025);
                        intent.putExtra("sdkAppId", 1400185867);
                        intent.putExtra("userSig", userSig);
                        startActivity(intent);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "从服务器获取userSig失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private  void LoadUserRig(){
            showProgress();
       final String docName=getIntent().getStringExtra("docName");

        new Thread() {
                String msg="加载失败了";
                userSigResponse response=null;
                @Override
                public void run() {
                    super.run();
                    try{
                         response = VideoService.GetUserSign(docName);
                    }catch (Exception e){
                        e.printStackTrace();
                        msg=e.getMessage();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissProgress();
                            if(response.code==20000){
                                final EditText etUserId = (EditText)findViewById(R.id.et_user_name);
                                etUserId.setText(docName);
                                etUserId.setTag(response.data.userSig);
                                etUserId.setEnabled(false);
                            }else {
                                showCustom("配置文件获取失败："+response.message);
                            }

                        }
                    });
                }
            }.start();
    }

    private Context getContext(){
        return this;
    }


    //////////////////////////////////    动态权限申请   ////////////////////////////////////////

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(TRTCNewActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(getContext(), "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

}
