package com.imedical.mobiledoctor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.PrefManager;
import com.imedical.mobiledoctor.util.PhoneUtil;

import java.io.File;


public class SplashActivity extends Activity{
	private final int SPLASH_DISPLAY_LENGHT = 1500; // 延迟秒数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.splash_activity);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(android.os.Build.VERSION.SDK_INT>=23){
			if (ContextCompat.checkSelfPermission(SplashActivity.this,
					Manifest.permission.READ_PHONE_STATE)
					!= PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},1004);
				return;
			}
			if (ContextCompat.checkSelfPermission(SplashActivity.this,
					Manifest.permission.RECORD_AUDIO)
					!= PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},1000);
				return;
			}
			if (ContextCompat.checkSelfPermission(SplashActivity.this,
					Manifest.permission.CAMERA)
					!= PackageManager.PERMISSION_GRANTED) {

				requestPermissions(new String[]{Manifest.permission.CAMERA},1003);
			}
			if (ContextCompat.checkSelfPermission(this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {

				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
				return;
			}
			if (ContextCompat.checkSelfPermission(this,
					Manifest.permission.READ_EXTERNAL_STORAGE)
					!= PackageManager.PERMISSION_GRANTED) {

				requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1002);
				return;
			}
		}
		Const.brand_name = PhoneUtil.getDeviceBrand();
		Const.brand_type = PhoneUtil.getSystemModel();
		Const.DeviceId = PhoneUtil.getMyStaticUUID(this);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashActivity.this,
						LoginHospitalActivity.class);
				startActivity(mainIntent);
				finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}
}
