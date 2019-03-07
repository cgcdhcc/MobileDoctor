package com.imedical.mobiledoctor.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.imedical.jpush.bean.MessageNoRead;
import com.imedical.jpush.service.MessageService;
import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.activity.round.PatientListActivity;
import com.imedical.mobiledoctor.base.MyApplication;
import com.imedical.mobiledoctor.util.StatusBarUtils;
import com.imedical.mobiledoctor.widget.CustomProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements View.OnClickListener {
	SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ImageView btn_work,btn_patient,btn_mine;
    TextView tv_work,tv_patient,tv_mine;
    View ll_work,ll_patient,ll_mine;
    Fragment_Patient frg_Patient=null;
    private CustomProgressDialog progressDialog = null;
    public static final String ARG_SECTION_NUMBER = "section_number";
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.mobile_blue_top);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        InitViews();
	}

	private void InitViews(){
        ll_work=this.findViewById(R.id.ll_work);
        ll_work.setOnClickListener(this);
        ll_patient=this.findViewById(R.id.ll_patient);
        ll_patient.setOnClickListener(this);
        ll_mine=this.findViewById(R.id.ll_mine);
        ll_mine.setOnClickListener(this);
        mViewPager=(ViewPager)this.findViewById(R.id.pager);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                btnSelect(position+1,false);
            }
        });
        btn_work =(ImageView) findViewById(R.id.btn_work);
        btn_patient =(ImageView) findViewById(R.id.btn_patient);
        btn_mine =(ImageView) findViewById(R.id.btn_mine);
        tv_work=(TextView)findViewById(R.id.tv_work);
        tv_patient=(TextView)findViewById(R.id.tv_patient);
        tv_mine=(TextView)findViewById(R.id.tv_mine);
        btn_work.setSelected(true);
    }

    public void btnSelect(int position,boolean IsClick){
        btn_work.setSelected(false);tv_work.setTextColor(getResources().getColor(R.color.mobile_gray));
        btn_patient.setSelected(false);tv_patient.setTextColor(getResources().getColor(R.color.mobile_gray));
        btn_mine.setSelected(false);tv_mine.setTextColor(getResources().getColor(R.color.mobile_gray));
        switch (position){
            case 1:btn_work.setSelected(true);tv_work.setTextColor(getResources().getColor(R.color.mobile_blue));

                break;
            case 2:btn_patient.setSelected(true);tv_patient.setTextColor(getResources().getColor(R.color.mobile_blue));

                break;
            case 3:btn_mine.setSelected(true);tv_mine.setTextColor(getResources().getColor(R.color.mobile_blue));

                break;

            default:break;
        }
        if(IsClick)mViewPager.setCurrentItem(position-1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_work:
                btnSelect(1, true);
                break;
            case R.id.ll_patient:
                btnSelect(2, true);
                break;
            case R.id.ll_mine:
                btnSelect(3, true);
                break;
            default:
                break;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		public Fragment currentFragment;
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}
		
		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}
		@Override
		public Fragment getItem(int position) {
            Fragment fragment;
			switch(position){
			case 0:fragment = new Fragment_work();break;
            case 1:fragment = new Fragment_Patient();
                    frg_Patient=(Fragment_Patient)fragment;
                    break;
            case 2:fragment = new Fragment_mine();break;
			default:
				fragment = new Fragment_work();
			}
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			this.currentFragment=fragment;
			return fragment;

        }

		@Override
		public int getCount() {
			return 3;
		}

	}

	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
//				exit();
				return true;
			} else {
				return super.onKeyDown(keyCode, event);
			}
		}


    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);

    }

    public final static int REQ_PERMISSION_CODE = 0x1000;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Log.d("mark", "条形码扫描结果：" + scanResult);
            btnSelect(2,true);
              if(frg_Patient!=null){
                  frg_Patient.loadQrData("", scanResult);// mLogin.userCode
                  frg_Patient.resetUI();
                  frg_Patient.loadSecondLevelData();
              }
        }
    }

    /** 动态权限申请 */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        showCustom( "用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }
    public void showCustom(String content) {
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom,(ViewGroup)this.findViewById(R.id.llToast));
        TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
        TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
        text.setText(content==null?"网络错误！":content);
        Toast toast= new Toast(this.getApplicationContext());
        toast.setGravity(Gravity.CENTER , 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public void showProgress() {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            return;
        } else {
            progressDialog = CustomProgressDialog.createDialog(this);
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
                    if (arg1 == KeyEvent.KEYCODE_BACK) {
                        if ((progressDialog != null) && progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                    }
                    return false;
                }
            });
            progressDialog.show();
        }
    }

    public void dismissProgress() {
        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    public void  showNoPatDialog(final Activity ctx, final Bundle bundle, final Class classTarget){
        new AlertDialog.Builder(ctx)
                .setTitle("提示信息")
                .setMessage("请先选择病人")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i =  new Intent(ctx,PatientListActivity.class);
                        String target= classTarget.getName();
                        i.putExtra("target",target);
                        if(bundle!=null) {
                            i.putExtras(bundle);
                        }
                        startActivity(i);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
