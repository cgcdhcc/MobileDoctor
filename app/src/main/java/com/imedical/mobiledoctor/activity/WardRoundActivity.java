package com.imedical.mobiledoctor.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.imedical.mobiledoctor.Const;
import com.imedical.mobiledoctor.R;
import com.imedical.mobiledoctor.XMLservice.BusyManager;
import com.imedical.mobiledoctor.XMLservice.SysManager;
import com.imedical.mobiledoctor.adapter.HisRecordsAdapter;
import com.imedical.mobiledoctor.base.BaseActivity;
import com.imedical.mobiledoctor.entity.PatientInfo;
import com.imedical.mobiledoctor.entity.SeeDoctorRecord;
import com.imedical.mobiledoctor.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class WardRoundActivity extends BaseActivity {
    private TextView   tv_name,tv_department,tv_title,tv_hisdept,tv_hisrcd;
    public int selectPos = 0;
    private HisRecordsAdapter mHisRecordsAdapter;
    private CircleImageView re_civ_photo;
    private List<SeeDoctorRecord> list = new ArrayList<SeeDoctorRecord>();
    private List<SeeDoctorRecord> listtemp = null;
    private PatientInfo mPatientInfo;
    private ListView mListViewRecord;
    private PopupWindow hisRecordPopWin;
    private SeeDoctorRecord mCurrectRecord = new SeeDoctorRecord();
    private  View ll_switch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ward_round_activity);
        InitViews();
    }
    private void showWindow(View parent) {
        if (hisRecordPopWin != null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//            int xPos = windowManager.getDefaultDisplay().getWidth() / 2 - hisRecordPopWin.getWidth() / 2;
            int xPos = windowManager.getDefaultDisplay().getWidth()/2 - ll_switch.getWidth() / 2;

            hisRecordPopWin.showAsDropDown(parent, 0, 0);
        }
    }
    private void InitViews(){
        mPatientInfo = Const.curPat;
        re_civ_photo=(CircleImageView)findViewById(R.id.re_civ_photo);
        tv_name=(TextView) findViewById(R.id.tv_name);
        tv_hisrcd=(TextView) findViewById(R.id.tv_hisrcd);
        tv_hisdept=(TextView) findViewById(R.id.tv_hisdept);
        ll_switch=findViewById(R.id.ll_switch);
        ll_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow(v);
            }
        });
        tv_department=(TextView) findViewById(R.id.tv_department);
        tv_title=(TextView) findViewById(R.id.tv_title);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       final View view = layoutInflater.inflate(R.layout.his_record_list, null);
        mListViewRecord = (ListView) view.findViewById(R.id.lv_data_list);
        mHisRecordsAdapter = new HisRecordsAdapter(WardRoundActivity.this, list);
        mListViewRecord.setAdapter(mHisRecordsAdapter);
        mListViewRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                SeeDoctorRecord sr = list.get(position);
                if (hisRecordPopWin != null) {
                    hisRecordPopWin.dismiss();
                }
                mPatientInfo.inDate=sr.admDate;
                mPatientInfo.admId = sr.admId;
                mPatientInfo.admType= sr.admType;
                Const.curSRecorder=sr;
//               showProgress();
                if (position == 0) {
                    tv_hisrcd.setText("切换就诊记录" );
                    tv_hisdept.setText("更多");
                } else {
                    tv_hisrcd.setText(SysManager.getAdmTypeDesc(sr.admType) );
                    tv_hisdept.setText(sr.admDate);
                }
                selectPos = position;
                mHisRecordsAdapter.notifyDataSetChanged();
            }

        });
        mCurrectRecord.admDate = mPatientInfo.inDate;
        mCurrectRecord.admDept = mPatientInfo.departmentName;
        mCurrectRecord.admId = mPatientInfo.admId;
        mCurrectRecord.admType = mPatientInfo.admType;
        Const.curSRecorder=new SeeDoctorRecord();
        Const.curSRecorder.admId= mPatientInfo.admId;
        list.add(mCurrectRecord);
        LoadHisRecord();
        ll_switch.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                ll_switch.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width=ll_switch.getWidth();
                hisRecordPopWin = new PopupWindow(view, width, LinearLayout.LayoutParams.WRAP_CONTENT);
                hisRecordPopWin.setFocusable(true);
                hisRecordPopWin.setOutsideTouchable(true);
                hisRecordPopWin.setBackgroundDrawable(new BitmapDrawable());
            }
        });
        //==========initData============
        tv_name.setText(Const.curPat.patName);
        tv_title.setText(Const.curPat.patRegNo);
        if(Const.curPat.patSex.equals("女")){
            re_civ_photo.setImageDrawable(getDrawable(R.drawable.pat_famale));
        }else {
            re_civ_photo.setImageDrawable(getDrawable(R.drawable.pat_male));
        }
    }

    private void LoadHisRecord() {
        new Thread() {
            @Override
            public void run() {
                try {
                    listtemp = BusyManager.listSeeDoctorRecord(mPatientInfo.admId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (listtemp == null) {
                        listtemp = new ArrayList<SeeDoctorRecord>();
                    } else {
                        list.addAll(listtemp);
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //		mListData.clear();
                            mHisRecordsAdapter = new HisRecordsAdapter(WardRoundActivity.this, list);
                            mListViewRecord.setAdapter(mHisRecordsAdapter);
                            mHisRecordsAdapter.notifyDataSetChanged();
                        }

                    });
                }

            }
        }.start();
    }
}
