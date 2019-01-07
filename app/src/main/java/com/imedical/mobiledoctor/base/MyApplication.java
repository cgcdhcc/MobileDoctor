package com.imedical.mobiledoctor.base;

import android.app.Application;


import com.imedical.mobiledoctor.util.PreferManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class MyApplication extends Application {


    @Override
    public void onCreate() {
// TODO Auto-generated method stub
        super.onCreate();
        PreferManager.inti(this);
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//                                               @Override
//                                               public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//                                               }
//
//                                               @Override
//                                               public void onActivityStarted(final Activity activity) {
//                                                   if (isFirst) {
//                                                       isFirst = false;
//                                                   } else {
//                                                       if (count == 0) {
//                                                           Date date = new Date();
//                                                           long returntime = date.getTime();
//                                                           long sub = returntime - time;
//                                                           Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
//                                                           //时间小于300秒或者启动的activity为首页则不执行操作
//                                                           if (sub < 300000 || activity.getClass().getName().equals("com.imedical.app.dhround.view.LoginHospitalActivity")) {
//                                                           } else {
//
//                                                               new AlertDialog.Builder(activity)
//                                                                       .setMessage("请重新登录")
//                                                                       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                                                           @Override
//                                                                           public void onClick(DialogInterface dialog, int which) {
//                                                                               String callbackActivity = activity.getClass().getName();
//                                                                               Intent i = new Intent(activity, LoginHospitalActivity.class);
//                                                                               i.putExtra("target", callbackActivity);
//                                                                               Bundle params = activity.getIntent().getExtras();
//                                                                               if (params != null) {
//                                                                                   i.putExtras(params);
//                                                                               }
//                                                                               activity.startActivity(i);
//                                                                               dialog.dismiss();
//                                                                           }
//                                                                       })
//                                                                       .setOnKeyListener(new DialogInterface.OnKeyListener() {
//                                                                           @Override
//                                                                           public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                                                                               if (keyCode == KeyEvent.KEYCODE_BACK) {
//                                                                                   return true;
//                                                                               } else {
//                                                                                   return false;
//                                                                               }
//
//                                                                           }
//                                                                       }).show();
//
//                                                           }
//                                                       }
//                                                   }
//                                                   count++;
//                                               }
//
//                                               @Override
//                                               public void onActivityResumed(Activity activity) {
//
//                                               }
//
//                                               @Override
//                                               public void onActivityPaused(Activity activity) {
//
//                                               }
//
//                                               @Override
//                                               public void onActivityStopped(Activity activity) {
//                                                   count--;
//                                                   if (count == 0) {
//                                                       Log.v("viclee", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
//                                                       Date date = new Date();
//                                                       time = date.getTime();
//                                                   }
//                                               }
//
//                                               @Override
//                                               public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//                                               }
//
//                                               @Override
//                                               public void onActivityDestroyed(Activity activity) {
//
//                                               }
//                                           }
//
//        );
//        if (AppConfig.isTestMode) {
//
//        } else {
//            MyCrashHandler crashHandler = MyCrashHandler.getInstance();
//            crashHandler.init(getApplicationContext());
//        }
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}
