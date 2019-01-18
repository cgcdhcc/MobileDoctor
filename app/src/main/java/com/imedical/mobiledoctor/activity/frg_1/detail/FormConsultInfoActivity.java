

package com.imedical.mobiledoctor.activity.frg_1.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.BaseBean;
import com.imedical.mobiledoctor.entity.ConsultData;
import com.imedical.mobiledoctor.entity.ConsultInfo;
import com.imedical.mobiledoctor.util.DialogUtil;

public class FormConsultInfoActivity extends BaseActivity {
	private ConsultData mConsultData;
	private ConsultInfo mConsultInfo;
	private String mInfo = "error!";
	private TextView tv_patName;
	private TextView tv_patBed;
	private TextView tv_appDateTime;
	private TextView tv_type;
	private TextView tv_inOut;
	private EditText et_conAttitude ;
	private TextView tv_appArcim ;
	private TextView tv_intent;
	private TextView tv_appDoc,tv_appLoc;
	private TextView tv_mainDiag;
	private CheckBox cb_agree,cb_refuse;
	private String docAttitude="";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_form_consult_info);
		if(Const.loginInfo==null){
			return;
		}
		mConsultData = (ConsultData)getIntent().getSerializableExtra("ConsultData");
		setTitle("会诊详情");
		initUI();
		loadData(Const.loginInfo.userCode,Const.loginInfo.defaultDeptId,mConsultData.conId);
	}

	private void loadData(final String userCode, final String departmentId,final String conId) {
		DialogUtil.showProgress(FormConsultInfoActivity.this);
		new Thread(){
			public void run() {
				
				try {
					mConsultInfo = BusyManager.loadConsultInfo(userCode, departmentId, conId);
				} catch (Exception e) {
					mInfo = e.getMessage();
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						DialogUtil.dismissProgress();
						if(mConsultInfo!=null){
							setDefaultUIValues(mConsultInfo);
						}else{
							DialogUtil.showToasMsg(FormConsultInfoActivity.this, mInfo);
						}
						
					}
				});
			};
		}.start();
		
	}

	private void initUI() {
		tv_appArcim = (TextView) findViewById(R.id.tv_appArcim);
		tv_appDateTime = (TextView)findViewById(R.id.tv_appDateTime);
		tv_appDoc	  = (TextView)findViewById(R.id.tv_appDoc);
		et_conAttitude = (EditText) findViewById(R.id.et_conAttitude);
		tv_appLoc	  = (TextView)findViewById(R.id.tv_appLoc);
		tv_patName = (TextView)findViewById(R.id.tv_patName);
		tv_patBed = (TextView)findViewById(R.id.tv_patBed);
		tv_type = (TextView)findViewById(R.id.tv_type);
		tv_inOut = (TextView)findViewById(R.id.tv_inOut);
		tv_intent = (TextView)findViewById(R.id.tv_intent);
		tv_mainDiag = (TextView)findViewById(R.id.tv_mainDiag);
		cb_agree=(CheckBox) findViewById(R.id.cb_agree);
		cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					cb_refuse.setChecked(false);
				}
			}
		});
		cb_refuse=(CheckBox) findViewById(R.id.cb_refuse);
		cb_refuse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					cb_agree.setChecked(false);
				}
			}
		});
	}
	private void setDefaultUIValues(ConsultInfo c) {
		if (c != null) {
			tv_appArcim.setText(c.appArcim);
			tv_appDateTime.setText(c.appDateTime);
			tv_appDoc.setText(c.appDoc);
		    et_conAttitude.setText(c.conAttitude);
			tv_appLoc.setText(mConsultData.appLoc);
			tv_inOut.setText(c.inOut);
			tv_intent.setText(c.intent);
			tv_mainDiag.setText(c.mainDiag);
			tv_patName.setText(c.patName);
			tv_patBed.setText(c.patBed);
			tv_type.setText(c.type);
			Log.d("当前状态",mConsultData.status);
			if("申请".equals(mConsultData.status)){
				View btn_doCharge = findViewById(R.id.btn_doCharge);
				btn_doCharge.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						doCharge();
					}

				});
				View btn_update = findViewById(R.id.btn_update);
				btn_update.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						updateData();
					}

				});
				btn_update.setVisibility(View.VISIBLE);
				btn_doCharge.setVisibility(View.VISIBLE);
			}else{
				et_conAttitude.setFocusable(false);
				et_conAttitude.setFocusableInTouchMode(false);
				if(("同意").equals(c.docAttitude)){
					cb_agree.setChecked(true);
					cb_refuse.setVisibility(View.GONE);
				}else if(("拒绝").equals(c.docAttitude)){
					cb_refuse.setChecked(true);
					cb_agree.setVisibility(View.GONE);
				}else{
					cb_refuse.setVisibility(View.GONE);
					cb_agree.setVisibility(View.GONE);
				}
			}
		}
	}
	
	private void updateData() {
		showProgress();
		final String conAttitude = et_conAttitude.getText().toString();
		docAttitude="";
		if(cb_agree.isChecked()){
			docAttitude="1";
		}
		if(cb_agree.isChecked()){
			docAttitude="0";
		}
		new Thread(){
			BaseBean b = null;
			public void run() {
				try {
					 b = BusyManager.updateConsultInfo(Const.loginInfo.userCode, Const.loginInfo.defaultDeptId, mConsultInfo.conId, conAttitude, docAttitude);
					 if(b!=null){
							mInfo = b.getResultDesc();
					 }
				} catch (Exception e) {
					mInfo = "更新失败！\n"+e.getMessage();
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgress();
						showToast(mInfo);
					}
				});
			};
		}.start();
	}
	
	private void doCharge() {
		showProgress();
		new Thread(){
			BaseBean b = null;
			public void run() {
				try {
					 b = BusyManager.doConsultFee(Const.loginInfo.userCode, Const.loginInfo.defaultDeptId, mConsultInfo.conId);
					 if(b!=null){
						mInfo = b.getResultDesc();
					 }
				} catch (Exception e) {
					mInfo = "执行会诊记录计费！\n"+e.getMessage();
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgress();
						showToast(mInfo);
					}
				});
			};
		}.start();
		
	}



}
