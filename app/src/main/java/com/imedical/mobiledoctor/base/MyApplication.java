package com.imedical.mobiledoctor.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.imedical.mobiledoctor.util.PreferManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.commonsdk.UMConfigure;

import org.litepal.LitePal;


public class MyApplication extends Application {


    @Override
    public void onCreate() {
// TODO Auto-generated method stub
        super.onCreate();
        PreferManager.inti(this);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5c472b35b465f5aeb1000909");
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();
        Log.d("msg", "数据库版本号：" + db.getVersion());
    }
}
