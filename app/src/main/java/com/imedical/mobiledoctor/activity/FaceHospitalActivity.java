package com.imedical.mobiledoctor.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.imedical.face.bean.IdentifyResponse;
import com.imedical.face.service.HttpFaceService;
import com.imedical.im.activity.TalkMsgActivity;
import com.imedical.mobiledoctor.AppConfig;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.UserManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.LoginInfo;
import com.imedical.mobiledoctor.fragment.MainActivity;
import com.imedical.mobiledoctor.util.PreferManager;
import com.imedical.mobiledoctor.util.StatusBarUtils;
import com.imedical.mobiledoctor.util.Validator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import id.zelory.compressor.Compressor;


public class FaceHospitalActivity extends BaseActivity implements SurfaceHolder.Callback {
    private SurfaceView view_camera;
    private SurfaceHolder holder;
    //安卓硬件相机
    private Camera mCamera;
    private SurfaceHolder mHolder;
    private boolean isShot = false;// 点击捕捉图像帧
    int previewWidth = 640;// 预览宽度
    int previewHeight = 480;// 预览高度
    int mFormat = ImageFormat.NV21;// 图像数据格式
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startTakephoto();
        }
    };
    public boolean isfront = false;
    public TextView tv_login;
    private TextView tv_status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.face_login);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isfront = true;
    }

    private void initViews() {
        this.view_camera = (SurfaceView) findViewById(R.id.view_camera);
        //获取SurfaceView的SurfaceHolder对象
        mHolder = view_camera.getHolder();
        //实现SurfaceHolder.Callback接口
        mHolder.addCallback(this);
        tv_login=findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FaceHospitalActivity.this,LoginHospitalActivity.class));
                finish();
            }
        });
        tv_status=findViewById(R.id.tv_status);
    }

    private void startTakephoto() {
        isShot = true;
    }


    // 预览数据帧回调
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
            if (isShot && isfront) {
                isShot = false;
                dealWithCameraData(paramArrayOfByte);
            }

        }
    };


    //保存拍照数据
    private void dealWithCameraData(final byte[] data) {
        Log.d("resultmsg", "开始处理拍照信息");
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Bitmap bitmap = getCameraBitmap(data);
                    int w = bitmap.getWidth(); // 得到图片的宽，高
                    int h = bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.reset();
                    matrix.postRotate(-90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
                            true);
                    int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
                    bitmap = Bitmap.createBitmap(bitmap, 0, (int)(wh*0.2), wh, wh, null,
                            false);
                    final File filePic = new File(AppConfig.FILE_PATH + "/temp.jpg");
                    if (!filePic.exists()) {
                        filePic.getParentFile().mkdirs();
                        filePic.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(filePic);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    bitmap.recycle();
                    fos.flush();
                    fos.close();
                    Log.d("resultmsg", "开始照片上传识别");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                File sfile=new Compressor(FaceHospitalActivity.this).setDestinationDirectoryPath(AppConfig.FILE_PATH  + File.separator + "images").compressToFile(filePic);
                                search(sfile);
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                    });
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void search(final File file) {
        tv_status.setVisibility(View.VISIBLE);
        new Thread() {
            IdentifyResponse iresponse;
            String result;

            @Override
            public void run() {
                super.run();
                result = HttpFaceService.identify(Const.face_groupid, "1", file);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        tv_status.setVisibility(View.GONE);
                        if (Validator.isBlank(result)) {
                            handler.postDelayed(runnable, 200);
                        } else {
                            if (result.contains("candidates")) {
                                iresponse = new Gson().fromJson(result, IdentifyResponse.class);
                                if (iresponse != null && iresponse.code == 0 && iresponse.data != null && iresponse.data.candidates != null && iresponse.data.candidates.size() > 0) {
                                    if (iresponse.data.candidates.get(0).confidence > 90) {//置信度大于90
                                        intiPatientData(iresponse.data.candidates.get(0).person_id);
                                    } else {
                                        if (isfront) {
                                            showToast("未识别到您的个人信息，请使用账号密码登陆后上传人脸照片");
                                            handler.postDelayed(runnable, 200);
                                        }
                                    }

                                } else {
                                    handler.postDelayed(runnable, 200);
                                }
                            } else {
                                handler.postDelayed(runnable, 200);
                            }
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //打开摄像头，获得Camera对象
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mCamera = Camera.open(i);
                break;
            }


        }
        if (mCamera == null) {
            showToast("找不到前置摄像头");
            return;
        }
        try {
            //设置显示
            mCamera.setPreviewDisplay(holder);
            handler.postDelayed(runnable, 500);
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //已经获得Surface的width和height，设置Camera的参数
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            //设置对焦
            parameters.setPreviewFormat(mFormat);//设置数据格式
            WindowManager wm1 = this.getWindowManager();
            float pwidth =(float) wm1.getDefaultDisplay().getWidth();
            float pheight =(float)  wm1.getDefaultDisplay().getHeight();
            previewWidth = parameters.getSupportedPreviewSizes().get(0).width;
            previewHeight = parameters.getSupportedPreviewSizes().get(0).height;
            for (int i = 0; i < parameters.getSupportedPreviewSizes().size(); i++) {
                Camera.Size s = parameters.getSupportedPreviewSizes().get(i);
                Log.d("msg", "SIZE:width" + s.width + "  height" + s.height);
                float dis;
                if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    dis=Math.abs((pwidth/s.height)-(pheight/s.width));
                    //Log.d("msg", "竖屏dis:"+dis);
                } else {
                    //如果是横屏
                    dis=Math.abs((pwidth/s.width)-(pheight/s.height));
                    //Log.d("msg", "横屏dis:"+dis);
                }

                if(dis<0.25){
                    if(dis==0){
                        previewWidth=s.width;
                        previewHeight=s.height;
                        break;
                    }else{
                        if(previewWidth<s.width){
                            previewWidth=s.width;
                            previewHeight=s.height;
                        }
                    }
                }
            }
            Log.d("msg", "pwidth:"+pwidth+" pheight:"+pheight+" previewWidth:"+previewWidth+" previewHeight:"+previewHeight);
            parameters.setPreviewSize(previewWidth, previewHeight);//设置预览宽和高

            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                mCamera.setDisplayOrientation(90);
            } else {
                //如果是横屏
                mCamera.setDisplayOrientation(0);
            }
            mCamera.setParameters(parameters);
            mCamera.setPreviewCallback(previewCallback);
            //开始预览
            mCamera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.holder = holder;
        if (null != mCamera) {
            handler.removeCallbacks(runnable);
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isfront=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mCamera) {
            handler.removeCallbacks(runnable);
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放相机资源
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * NV21数据转Bitmap
     *
     * @param cameraData
     * @return Bitmap
     */
    public Bitmap getCameraBitmap(byte[] cameraData) {
        if (null == cameraData)
            return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] rawImage = null;

        YuvImage yuv = new YuvImage(cameraData, ImageFormat.NV21, previewWidth,
                previewHeight, null);
        yuv.compressToJpeg(new Rect(0, 0, previewWidth, previewHeight), 100,
                baos);
        rawImage = baos.toByteArray();
        return BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);
    }

    public void intiPatientData(final String usercode){
        PreferManager.saveBooleanValue("hasface", true);
        showProgress();
            new Thread() {
                LoginInfo mLoginInfo;
                String mInfo="网络请求异常，请稍后再试";
                public void run() {
                    try {
                        mLoginInfo = UserManager.login(usercode);
                    } catch (Exception e) {
                        e.printStackTrace();
                        mInfo=e.getMessage();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissProgress();
                            if(mLoginInfo!=null){
                                Const.loginInfo = mLoginInfo;
                                Const.curPat = null;
                                intiJpush();
                                Intent intent=new Intent(FaceHospitalActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                showToast(mInfo);
                            }
                        }
                    });
                }
            }.start();
    }

    public void intiJpush(){
        if(Const.loginInfo!=null){
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
}