package com.imedical.mobiledoctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.imedical.face.bean.AddPersonResponse;
import com.imedical.face.bean.DetectResponse;
import com.imedical.face.service.HttpFaceService;
import com.imedical.im.activity.ActivityPhtotoPop;
import com.imedical.im.activity.TalkMsgActivity;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.util.DownloadUtil;
import com.imedical.mobiledoctor.util.PhoneUtil;
import com.imedical.mobiledoctor.util.StatusBarUtils;
import com.imedical.mobiledoctor.util.Validator;
import com.umeng.commonsdk.debug.E;

import java.io.File;

import id.zelory.compressor.Compressor;

public class FaceLoadActivity extends ActivityPhtotoPop {
    public TextView tv_doctorName;
    public ImageView iv_doctor_head;
    public FrameLayout view_photo;
    public View view_choose_photo,view_refresh_photo,tv_upload,tv_gray;
    public String imgpath="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_face_upload);
        intiView();
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
    }
    public void intiView(){
        tv_doctorName=findViewById(R.id.tv_doctorName);
        tv_doctorName.setText(Const.loginInfo.userName+"医生，你好!");
        iv_doctor_head=findViewById(R.id.iv_doctor_head);
        view_photo=findViewById(R.id.view_photo);

        view_photo.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = view_photo.getLayoutParams();
                params.height=view_photo.getWidth();
                view_photo.setLayoutParams(params);
            }
        });
        view_choose_photo=findViewById(R.id.view_choose_photo);
        view_choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(FaceLoadActivity.this);
            }
        });
        view_refresh_photo=findViewById(R.id.view_refresh_photo);
        view_refresh_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(FaceLoadActivity.this);
            }
        });
        tv_upload=findViewById(R.id.tv_upload);
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        tv_gray=findViewById(R.id.tv_gray);
    }
    @Override
    protected void onCaptureComplete(File captureFile) {
        if (captureFile != null) {
            savefile(captureFile.getAbsolutePath());
        }

    }

    @Override
    protected void onGalleryComplete(String path) {
        if (path != null) {
            savefile(path);
        }
    }
    public void savefile(String filepath){
        if(Validator.isBlank(imgpath)){
            imgpath=filepath;
            view_choose_photo.setVisibility(View.GONE);
            view_refresh_photo.setVisibility(View.VISIBLE);
            tv_upload.setVisibility(View.VISIBLE);
            tv_gray.setVisibility(View.GONE);
        }
        DownloadUtil.loadImage(iv_doctor_head, "file://"+filepath,R.drawable.im_iconfont_tupian ,R.drawable.im_iconfont_tupian ,R.drawable.im_iconfont_tupian );
    }
    public void upload() {
            showProgress();


            new Thread() {
                DetectResponse response;
                File file=new File(imgpath);
                @Override
                public void run() {
                    super.run();
                    try{
                        file = new Compressor(FaceLoadActivity.this).compressToFile(file);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d("msg", "压缩后的文件长度"+file.length());
                    response = HttpFaceService.detect(file, "0");
                    Log.d("resultmsg", "检测结果");
                    if (response != null) {
                        if (response.code == 0 && response.data != null && response.data.face != null) {
                            if (response.data.face.size() > 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissProgress();
                                        showToast("检测到多张人脸，请重新上传个人照片");
                                    }
                                });

                            } else {
                                if (response.data.face.size() == 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dismissProgress();
                                            showToast("未检测到人脸，请重新上传个人照片");
                                        }
                                    });

                                } else {
                                    AddPersonResponse addPersonResponse = HttpFaceService.newperson(file, Const.loginInfo.userCode, Const.loginInfo.userName, PhoneUtil.getMyStaticUUID(FaceLoadActivity.this));
                                    if (addPersonResponse != null && addPersonResponse.code == 0) {
                                        runOnUiThread(new Runnable() {//1秒识别一次
                                            @Override
                                            public void run() {
                                                dismissProgress();
                                                showToast("上传成功！");
                                                Intent intent=new Intent(FaceLoadActivity.this,FaceLoadSuccessActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    } else {
                                        if (addPersonResponse == null) {
                                            runOnUiThread(new Runnable() {//1秒识别一次
                                                @Override
                                                public void run() {
                                                    dismissProgress();
                                                    showToast("服务器偷偷溜走了，请重新尝试");
                                                }
                                            });

                                        } else {
                                            final String message = addPersonResponse.message;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dismissProgress();
                                                    showToast("绑定人脸失败，错误信息：" + message);
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        } else {
                            if (response.code == -1101) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissProgress();
                                        showToast("未检测到人脸，请重新上传个人照片");
                                    }
                                });

                            }
                        }

                    } else {
                        runOnUiThread(new Runnable() {//1秒识别一次
                            @Override
                            public void run() {
                                dismissProgress();
                                showToast("服务器偷偷溜走了，请重新尝试");
                            }
                        });
                    }
                }
            }.start();
        }
}